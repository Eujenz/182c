package net.world.npc;

import java.util.List;
import net.database.ItemsTable;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.network.server.S_ShowHtml;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.object.L1Object;

public class Gunter extends L1Object {
  public void Talk(PcInstance pc) {
    String htmlid = "";
    switch (pc.getClassType()) {
      case 0:
        if (pc.getLevel() >= 15) {
          int lv15_step = pc.getQuest().get_step(1);
          if (lv15_step == 2 || lv15_step == 255) {
            htmlid = "gunterp11";
            break;
          } 
          htmlid = "gunterp1";
          break;
        } 
        htmlid = "gunterp12";
        break;
      case 1:
        if (pc.getLawful() < 65536) {
          htmlid = "gunterkev4";
          break;
        } 
        if (pc.getQuest().isEnd(1)) {
          htmlid = "gunterkev3";
          break;
        } 
        htmlid = "gunterk1";
        break;
      case 2:
        htmlid = "guntere1";
        break;
      case 3:
        htmlid = "gunterw1";
        break;
    } 
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), htmlid));
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    String htmlid = "";
    if ("guntertest".equals(text1))
      switch (pc.getClassType()) {
        case 0:
          if (pc.getQuest().get_step(1) == 0) {
            int count = 0;
            byte b;
            int i;
            L1Object[] arrayOfL1Object;
            for (i = (arrayOfL1Object = getObjectList()).length, b = 0; b < i; ) {
              L1Object obj = arrayOfL1Object[b];
              if (obj instanceof PcInstance && getDistance(obj, 2) && ((PcInstance)obj).getClanId() == pc.getClanId())
                count++; 
              b++;
            } 
            if (count >= 6) {
              ItemInstance temp = ItemsTable.getInstance().newItem(118, false, true);
              pc.getInventory().add(temp);
              pc.SendPacket((S_BasePacket)new S_ServerMessage(143, getName(), temp.toString()));
              pc.getQuest().set_step(1, 255);
              break;
            } 
            htmlid = "gunterp12";
            break;
          } 
          if (pc.getQuest().isEnd(1))
            htmlid = "gunterp11"; 
          break;
        case 1:
          if (pc.getLevel() >= 15) {
            int step = pc.getQuest().get_step(1);
            if (step == 255) {
              htmlid = "gunterkev3";
              break;
            } 
            List<ItemInstance> item1 = pc.getInventory().getItemDbId(117);
            if (item1 != null) {
              pc.getInventory().remove(item1.get(0));
              ItemInstance temp = ItemsTable.getInstance().newItem(119, false, true);
              pc.getInventory().add(temp);
              pc.SendPacket((S_BasePacket)new S_ServerMessage(143, getName(), temp.toString()));
              pc.getQuest().set_step(1, 255);
              htmlid = "gunterkev3";
              break;
            } 
            if (step == 1) {
              htmlid = "gunterkev5";
              break;
            } 
            pc.getQuest().set_step(1, 1);
            htmlid = "gunterkev1";
            break;
          } 
          htmlid = "gunterkev2";
          break;
        case 2:
          htmlid = "guntereev1";
          break;
        case 3:
          htmlid = "gunterw1";
          break;
      }  
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), htmlid));
  }
}
