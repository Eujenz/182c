package net.world.function;

import net.Config;
import net.database.ItemsTable;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.network.server.S_ShowHtml;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.object.L1Object;

public class QuestSystem {
  private static class Holder {
    static QuestSystem instance = new QuestSystem();
  }
  
  public static QuestSystem getInstance() {
    return Holder.instance;
  }
  
  public void Quest(PcInstance pc, L1Object npc) {
    switch (pc.getClassType()) {
      case 3:
        WizardQuestGraduation(pc, npc);
        break;
    } 
  }
  
  private void WizardQuestGraduation(PcInstance pc, L1Object npc) {
    if (pc.getLevel() >= Config.QUEST_WIZARD_MANA_LEVEL && npc instanceof net.world.npc.Gereng) {
      switch (pc.getQuest().get_step(100)) {
        case 0:
          pc.getQuest().set_step(100, 1);
          pc.SendPacket((S_BasePacket)new S_ShowHtml(npc.getObjectId(), "gerengTe1"));
          return;
        case 1:
          pc.SendPacket((S_BasePacket)new S_ShowHtml(npc.getObjectId(), "gerengTe2"));
          return;
        case 2:
          if (pc.getLawful() < 65536) {
            pc.SendPacket((S_BasePacket)new S_ShowHtml(npc.getObjectId(), "gerengTe4"));
          } else {
            ItemInstance temp = pc.getInventory().getItemNameId("$1097");
            if (temp != null) {
              pc.SendPacket((S_BasePacket)new S_ShowHtml(npc.getObjectId(), "gerengTe3"));
              pc.getInventory().remove(temp);
              temp = ItemsTable.getInstance().newItem(290, false, true);
              pc.getInventory().add(temp);
              pc.SendPacket((S_BasePacket)new S_ServerMessage(143, npc.getName(), temp.toString()));
              pc.getQuest().set_step(100, 255);
            } else {
              pc.getQuest().set_step(100, 1);
              pc.SendPacket((S_BasePacket)new S_ShowHtml(npc.getObjectId(), "gerengTe5"));
            } 
          } 
          return;
      } 
      if (pc.getSkill().isGerengMagic()) {
        pc.SendPacket((S_BasePacket)new S_ShowHtml(npc.getObjectId(), "gerengEv1"));
      } else {
        pc.SendPacket((S_BasePacket)new S_ShowHtml(npc.getObjectId(), "gerengTe6"));
      } 
    } 
  }
}
