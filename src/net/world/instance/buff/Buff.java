package net.world.instance.buff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.database.DatabaseConnection;
import net.database.ItemsTable;
import net.database.SkillTable;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;
import net.world.time.ItemTimerInstance;
import net.world.time.bean.BuffTimer;
import net.world.time.bean.ItemTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Buff {
  final Logger log = LoggerFactory.getLogger(Buff.class);
  
  private PcInstance pc;
  
  public Buff(PcInstance pc) {
    this.pc = pc;
  }
  
  public void delete() {
    this.pc = null;
  }
  
  public void save() {
    Connection con = null;
    PreparedStatement pstm = null;
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("DELETE FROM characters_buffs WHERE char_id='");
      sb.append(this.pc.getObjectId());
      sb.append("'");
      DatabaseConnection.getInstance().query_delete(sb.toString());
      con = DatabaseConnection.getInstance().getConnection();
      pstm = con.prepareStatement(String.format("INSERT INTO characters_buffs SET %s, %s, %s, %s ", new Object[] { "char_id=?", "type=?", "tid=?", "ttime=?" }));
      ItemTimer it = ItemTimerInstance.getInstance().get((Character)this.pc);
      if (it != null)
        for (ItemInstance item : it.getList()) {
          if (item.getItem().get_nameidN() == 27 || item.getItem().get_nameidN() == 110 || item.getItem().get_nameidN() == 232 || item.getItem().get_nameidN() == 507 || item.getItem().get_nameidN() == 234 || item.getItem().get_nameidN() == 264 || item.getItem().get_nameidN() == 239 || item.getItem().get_nameidN() == 943 || item.getItem().get_nameidN() == 944 || item.getItem().get_nameidN() == 971 || item.getItem().get_nameidN() == 1507 || item.getItem().get_nameidN() == 1508 || item.getItem().get_nameidN() == 1652234 || item.getItem().get_name().startsWith("經驗藥水") || item.getItem().get_name().startsWith("商城變卷")) {
            pstm.setInt(1, this.pc.getObjectId());
            pstm.setString(2, "item");
            pstm.setInt(3, item.getItem().getItemId());
            pstm.setInt(4, item.getTime());
            pstm.execute();
          } 
        }  
      BuffTimer bt = BuffTimerInstance.getInstance().get((L1Object)this.pc);
      if (bt != null)
        for (Magic m : bt.getList()) {
          if (m.getSkill().getSkill_id() == 2 || m.getSkill().getSkill_id() == 3 || m.getSkill().getSkill_id() == 8 || m.getSkill().getSkill_id() == 14 || m.getSkill().getSkill_id() == 17 || m.getSkill().getSkill_id() == 21 || m.getSkill().getSkill_id() == 27 || m.getSkill().getSkill_id() == 28 || m.getSkill().getSkill_id() == 43 || m.getSkill().getSkill_id() == 44 || m.getSkill().getSkill_id() == 151 || m.getSkill().getSkill_id() == 10000) {
            pstm.setInt(1, this.pc.getObjectId());
            pstm.setString(2, "skill");
            pstm.setInt(3, m.getSkill().getSkill_id());
            pstm.setInt(4, bt.getTime(m));
            pstm.execute();
          } 
        }  
    } catch (Exception e) {
      e.printStackTrace();
      this.log.error(e.getLocalizedMessage(), e);
    } finally {
      DatabaseConnection.getInstance().close(con, pstm);
    } 
  }
  
  public void read() {
    ItemInstance item = null;
    Magic m = null;
    Connection con = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      pstm = con.prepareStatement("select * from characters_buffs where char_id='" + this.pc.getObjectId() + "'");
      rs = pstm.executeQuery();
      while (rs.next()) {
        String type = rs.getString("type");
        int id = rs.getInt("tid");
        int time = rs.getInt("ttime");
        if ("item".equalsIgnoreCase(type)) {
          item = ItemsTable.getInstance().newItem(id, false, false);
          if (item != null) {
            item.setTime(time);
            ItemTimerInstance.getInstance().add((Character)this.pc, item);
          } 
          continue;
        } 
        m = SkillTable.getInstance().getTemplate((Character)this.pc, id);
        if (m != null) {
          m.setTime(time);
          BuffTimerInstance.getInstance().add((L1Object)this.pc, m);
        } 
      } 
    } catch (Exception localException) {
      this.log.error(localException.getLocalizedMessage(), localException);
    } finally {
      DatabaseConnection.getInstance().close(con, pstm, rs);
    } 
  }
}
