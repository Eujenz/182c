package net.world.npc;

import net.Config;
import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.function.QuestSystem;
import net.world.instance.PcInstance;
import net.world.instance.inventory.Inventory;
import net.world.object.L1Object;

public class Gereng extends L1Object {
  public void Talk(PcInstance pc) {
    Inventory inv = pc.getInventory();
    if (inv == null) {
      pc.Message("什么都没带不能学魔法。");
      return;
    } 
    if (!inv.isSkillCheckHelmMagic(pc))
      return; 
    switch (pc.getClassType()) {
      case 0:
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "gerengp1"));
        break;
      case 1:
        if (pc.getLevel() >= 50) {
          pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "gerengEv1"));
          break;
        } 
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "gerengk1"));
        break;
      case 2:
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "gerenge1"));
        break;
      case 3:
        if (pc.getLevel() >= Config.QUEST_WIZARD_MANA_LEVEL) {
          if (pc.getQuest().isEnd(100)) {
            if (pc.getSkill().isGerengMagic()) {
              pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "gerengEv1"));
              break;
            } 
            pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "gerengTe6"));
            break;
          } 
          pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "gerengw1"));
          break;
        } 
        if (pc.getSkill().isGerengMagic()) {
          pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "gerengEv1"));
          break;
        } 
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "gerengEv3"));
        break;
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("gerengtest"))
      QuestSystem.getInstance().Quest(pc, this); 
  }
}
