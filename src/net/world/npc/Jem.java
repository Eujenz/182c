package net.world.npc;

import java.util.List;
import net.database.ItemsTable;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.network.server.S_ShowHtml;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.object.L1Object;

public class Jem extends L1Object {
  public void Talk(PcInstance pc) {
    switch (pc.getClassType()) {
      case 0:
      case 1:
      case 2:
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "jem2"));
        break;
      case 3:
        if (pc.getLevel() >= 15) {
          if (pc.getQuest().isEnd(1)) {
            pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "jem6"));
            break;
          } 
          pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "jem1"));
          break;
        } 
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "jem2"));
        break;
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if ("request cursed spellbook".equals(text1)) {
      if (pc.getClassType() == 3 && pc.getQuest().get_step(1) == 0) {
        List<ItemInstance> item1920 = pc.getInventory().getItemDbId(40538);
        List<ItemInstance> item1921 = pc.getInventory().getItemDbId(40539);
        if (item1920 != null && item1921 != null) {
          pc.getInventory().remove(item1920.get(0));
          pc.getInventory().remove(item1921.get(0));
          ItemInstance temp = ItemsTable.getInstance().newItem(40591, false, true);
          pc.getInventory().add(temp);
          pc.SendPacket((S_BasePacket)new S_ServerMessage(143, getName(), temp.toString()));
          pc.getQuest().set_step(1, 1);
        } 
      } 
    } else if ("request book of magical powers".equals(text1) && 
      pc.getClassType() == 3 && pc.getQuest().get_step(1) == 1) {
      List<ItemInstance> item1 = pc.getInventory().getItemDbId(40591);
      List<ItemInstance> item2 = pc.getInventory().getItemDbId(40605);
      if (item1 != null && item2 != null) {
        pc.getInventory().remove(item1.get(0));
        pc.getInventory().remove(item2.get(0));
        ItemInstance temp = ItemsTable.getInstance().newItem(20226, false, true);
        pc.getInventory().add(temp);
        pc.SendPacket((S_BasePacket)new S_ServerMessage(143, getName(), temp.toString()));
        pc.getQuest().set_step(1, 255);
      } 
    } 
  }
}
