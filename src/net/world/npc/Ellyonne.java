package net.world.npc;

import net.LineageClient;
import net.database.BanListTable;
import net.database.CharacterTable;
import net.database.SkillTable;
import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_PacketBox;
import net.network.server.S_ShowHtml;
import net.world.function.GmCommand;
import net.world.instance.PcInstance;
import net.world.object.L1Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Ellyonne extends L1Object {
  private static final Logger log = LoggerFactory.getLogger(Ellyonne.class);
  
  private static final String SKILL_LOG = "非法(艾利?) IP[%s] ??[%s]  角色[%s]  等?[%s]  ??[%s]    [%s]";
  
  public void Talk(PcInstance pc) {
    if (pc.getClassType() != 2) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "ellyonne9"));
      return;
    } 
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "ellyonne"));
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    LineageClient client = pc.getClient();
    if (client == null)
      return; 
    client.setEllyonneCount(client.getEllyonneCount() + 1);
    int count = client.getEllyonneCount();
    if (count > 1) {
      String ip = client.getIP();
      if (ip == null) {
        log.info(String.format("非法(艾利?) IP[%s] ??[%s]  角色[%s]  等?[%s]  ??[%s]    [%s]", new Object[] { pc.getClient().getIP(), pc.getClient().getID(), pc.getName(), Integer.valueOf(pc.getLevel()), Integer.valueOf(pc.getClassType()), "空IP" }));
        client.close();
        return;
      } 
      if (!BanListTable.getInstance().isBanList(ip)) {
        GmCommand.getInstance().BanTable("1", ip);
        log.info(String.format("非法(艾利?) IP[%s] ??[%s]  角色[%s]  等?[%s]  ??[%s]    [%s]", new Object[] { pc.getClient().getIP(), pc.getClient().getID(), pc.getName(), Integer.valueOf(pc.getLevel()), Integer.valueOf(pc.getClassType()), "速度?快(1秒??到2次及以上) 已封?IP" }));
      } 
      client.close();
      return;
    } 
    if (pc.getClassType() != 2)
      return; 
    boolean flag = false;
    if (text1.equalsIgnoreCase("init")) {
      if (pc.getElfAttr() == 0)
        return; 
      for (int skill_id = 129; skill_id <= 168; skill_id++) {
        Skill skill = SkillTable.getInstance().getTemplate(skill_id);
        int skill_attr = skill.getAttr();
        if (skill_attr != 0)
          pc.getSkill().remove(skill.getSkill_id()); 
      } 
      pc.setElfAttr(0);
      CharacterTable.getInstance().elfAttrUpdate(pc, 0);
      pc.getSkill().save();
      pc.SendPacket((S_BasePacket)new S_PacketBox(15, 0));
      pc.SendPacket((S_BasePacket)new S_ShowHtml(pc.getObjectId(), ""));
    } else if (text1.equalsIgnoreCase("fire")) {
      if (pc.getElfAttr() != 0)
        return; 
      pc.setElfAttr(2);
      CharacterTable.getInstance().elfAttrUpdate(pc, 2);
      pc.SendPacket((S_BasePacket)new S_PacketBox(15, 1));
      pc.SendPacket((S_BasePacket)new S_ShowHtml(pc.getObjectId(), ""));
    } else if (text1.equalsIgnoreCase("water")) {
      if (pc.getElfAttr() != 0)
        return; 
      pc.setElfAttr(4);
      CharacterTable.getInstance().elfAttrUpdate(pc, 4);
      pc.SendPacket((S_BasePacket)new S_PacketBox(15, 2));
      pc.SendPacket((S_BasePacket)new S_ShowHtml(pc.getObjectId(), ""));
    } else if (text1.equalsIgnoreCase("air")) {
      if (pc.getElfAttr() != 0)
        return; 
      pc.setElfAttr(8);
      CharacterTable.getInstance().elfAttrUpdate(pc, 8);
      pc.SendPacket((S_BasePacket)new S_PacketBox(15, 3));
      pc.SendPacket((S_BasePacket)new S_ShowHtml(pc.getObjectId(), ""));
    } else if (text1.equalsIgnoreCase("earth")) {
      if (pc.getElfAttr() != 0)
        return; 
      pc.setElfAttr(1);
      CharacterTable.getInstance().elfAttrUpdate(pc, 1);
      pc.SendPacket((S_BasePacket)new S_PacketBox(15, 4));
      pc.SendPacket((S_BasePacket)new S_ShowHtml(pc.getObjectId(), ""));
    } 
    if (flag)
      pc.Message("功能正在??中。"); 
  }
}
