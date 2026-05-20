package net.world.instance.skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.Config;
import net.check.CheckSpeed;
import net.database.DatabaseConnection;
import net.database.SkillTable;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectLock;
import net.network.server.S_ServerMessage;
import net.network.server.S_SkillAdd;
import net.network.server.S_SkillDelete;
import net.world.instance.PcInstance;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.pc.L1PinkName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PcSkill extends Skills {
  final Logger log = LoggerFactory.getLogger(PcSkill.class);
  
  private static final String SKILL_LOG = "?除技能  IP[%s]  ??[%s]  角色[%s]  等?[%s]  ??[%s]  技能[%s]  [%s]";
  
  private PcInstance pc;
  
  private int[] lv;
  
  private List<Integer> tempSkill = new ArrayList<Integer>();
  
  public PcSkill(PcInstance pc) {
    this.pc = pc;
    this.lv = new int[28];
    read();
  }
  
  public void save() {
    Connection con = null;
    PreparedStatement pstm = null;
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("DELETE FROM characters_skills WHERE char_id='");
      sb.append(this.pc.getObjectId());
      sb.append("'");
      DatabaseConnection.getInstance().query_delete(sb.toString());
      con = DatabaseConnection.getInstance().getConnection();
      pstm = con.prepareStatement(String.format("INSERT INTO characters_skills SET %s, %s, %s ", new Object[] { "char_id=?", "skill_id=?", "skill_name=?" }));
      if (!this.tempSkill.isEmpty())
        this.tempSkill.clear(); 
      for (Magic m : getAllOfHelmMagic())
        this.tempSkill.add(Integer.valueOf(m.getSkill().getSkill_id())); 
      for (Magic m : getAll()) {
        int skillId = m.getSkill().getSkill_id();
        boolean flag = true;
        for (Iterator<Integer> localIterator = this.tempSkill.iterator(); localIterator.hasNext(); ) {
          int id = ((Integer)localIterator.next()).intValue();
          if (id == skillId)
            flag = false; 
        } 
        if (flag) {
          pstm.setInt(1, this.pc.getObjectId());
          pstm.setInt(2, skillId);
          pstm.setString(3, m.getSkill().getName());
          pstm.execute();
        } 
      } 
    } catch (Exception e) {
      this.log.error(e.getLocalizedMessage(), e);
    } finally {
      DatabaseConnection.getInstance().close(con, pstm);
    } 
  }
  
  public void read() {
    Magic m = null;
    Connection con = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      pstm = con.prepareStatement("select * from characters_skills where char_id='" + this.pc.getObjectId() + "'");
      rs = pstm.executeQuery();
      while (rs.next()) {
        int skill_id = rs.getInt("skill_id");
        m = SkillTable.getInstance().getTemplate((Character)this.pc, skill_id);
        if (m != null)
          add(m); 
      } 
    } catch (Exception localException) {
      this.log.error(localException.getLocalizedMessage(), localException);
    } finally {
      DatabaseConnection.getInstance().close(con, pstm, rs);
    } 
  }
  
  public void sendList() {
    for (int i = 0; i < this.lv.length; i++)
      this.lv[i] = 0; 
    for (Magic m : getAll())
      this.lv[m.getSkill().getSkill_level() - 1] = this.lv[m.getSkill().getSkill_level() - 1] + m.getSkill().getId(); 
    this.pc.SendPacket((S_BasePacket)new S_SkillAdd(this.lv[0], this.lv[1], this.lv[2], this.lv[3], this.lv[4], this.lv[5], this.lv[6], this.lv[7], this.lv[8], this.lv[9], this.lv[16], this.lv[17], this.lv[18], this.lv[19], this.lv[20], this.lv[14], this.lv[10], this.lv[11]));
  }
  
  public void delete() {
    this.lv = null;
    this.pc = null;
    super.delete();
  }
  
  public void toMagic(int lv, int no, int id, int x, int y) {
    if (Config.CHECK_SPEED_TYPE == 1)
      this.pc.magic.check(); 
    if (this.pc.getInventory().getWeight() < 24) {
      for (Magic m : getAll()) {
        if (m.getSkill().getSkill_level() == lv && m.getSkill().getSkill_no() == no) {
          if (m.skill.getReuseDelay() > 0 && 
            this.pc.checkSkillDelay(m.getSkill().getSkill_id()))
            return; 
          if (lv == 15 && no == 0) {
            m.toMagic(x, y, id);
          } else if (lv == 8 && no == 1) {
            m.toMagic(x, y);
          } else {
            m.toMagic(id);
          } 
          if (m.getSkill().getType().equalsIgnoreCase("attack")) {
            L1Object o = this.pc.getObject(id);
            L1PinkName.execute((L1Object)this.pc, o);
          } 
          if (Config.CHECK_SPEED_TYPE != 2)
            break; 
          if (m.getSkill().getType().equalsIgnoreCase("attack")) {
            this.pc.getCheckSped().checkInterval(CheckSpeed.ACT_TYPE.SPELL_DIR);
            break;
          } 
          if (!m.getSkill().getType().equalsIgnoreCase("buff"))
            break; 
          this.pc.getCheckSped().checkInterval(CheckSpeed.ACT_TYPE.SPELL_NODIR);
          break;
        } 
      } 
    } else {
      this.pc.SendPacket((S_BasePacket)new S_ServerMessage(316));
      if (lv == 1 && no == 4)
        this.pc.SendPacket((S_BasePacket)new S_ObjectLock()); 
    } 
  }
  
  public void remove(int skill_id) {
    Magic m = get(skill_id);
    if (m != null) {
      super.remove(skill_id);
      for (int i = 0; i < this.lv.length; i++)
        this.lv[i] = 0; 
      this.lv[m.getSkill().getSkill_level() - 1] = this.lv[m.getSkill().getSkill_level() - 1] + m.getSkill().getId();
      this.pc.SendPacket((S_BasePacket)new S_SkillDelete(this.lv[0], this.lv[1], this.lv[2], this.lv[3], this.lv[4], this.lv[5], this.lv[6], this.lv[7], this.lv[8], this.lv[9], this.lv[16], this.lv[17], this.lv[18]));
      this.log.info(String.format("?除技能  IP[%s]  ??[%s]  角色[%s]  等?[%s]  ??[%s]  技能[%s]  [%s]", new Object[] { this.pc.getClient().getIP(), this.pc.getClient().getID(), this.pc.getName(), Integer.valueOf(this.pc.getLevel()), this.pc.getClassTypeName(), m.getSkill().getName(), "成功" }));
    } else {
      this.log.info(String.format("?除技能  IP[%s]  ??[%s]  角色[%s]  等?[%s]  ??[%s]  技能[%s]  [%s]", new Object[] { this.pc.getClient().getIP(), this.pc.getClient().getID(), this.pc.getName(), Integer.valueOf(this.pc.getLevel()), this.pc.getClassTypeName(), this.pc.getSkillIdName(skill_id), "失? ?有找到?技能" }));
    } 
  }
  
  public boolean isGerengMagic() {
    int max = (this.pc.getLevel() <= 4) ? 5 : ((this.pc.getLevel() <= 8) ? 10 : 15);
    int c = 0;
    for (Magic m : getAll()) {
      if (m.getSkill().getSkill_id() <= 15)
        c++; 
    } 
    return (c < max);
  }
}
