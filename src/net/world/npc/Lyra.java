package net.world.npc;

import java.util.ArrayList;
import java.util.List;
import net.database.ItemsTable;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.network.server.S_ShowHtml;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.object.L1Object;

public class Lyra extends L1Object {
  private List<ItemInstance> remove_list = new ArrayList<ItemInstance>();
  
  public void Talk(PcInstance pc) {
    if (pc.isTotem()) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "lyraEv3"));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "lyraEv1"));
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("contract1")) {
      pc.setTotem(true);
    } else if (text1.equalsIgnoreCase("contract1yes")) {
      TotemPrice(pc);
    } else if (text1.equalsIgnoreCase("contract1no")) {
      TotemPrice(pc);
      pc.setTotem(false);
    } 
  }
  
  private synchronized void TotemPrice(PcInstance pc) {
    this.remove_list.clear();
    int needAden = 0;
    byte b;
    int i;
    ItemInstance[] arrayOfItemInstance;
    for (i = (arrayOfItemInstance = pc.getInventory().getAll()).length, b = 0; b < i; ) {
      ItemInstance item = arrayOfItemInstance[b];
      if (item instanceof net.world.instance.inventory.function.TotemAtuba) {
        needAden = (int)(needAden + 200L * item.getCount());
        this.remove_list.add(item);
      } else if (item instanceof net.world.instance.inventory.function.TotemNeruga) {
        needAden = (int)(needAden + 100L * item.getCount());
        this.remove_list.add(item);
      } else if (item instanceof net.world.instance.inventory.function.TotemGandi) {
        needAden = (int)(needAden + 30L * item.getCount());
        this.remove_list.add(item);
      } else if (item instanceof net.world.instance.inventory.function.TotemRova || item instanceof net.world.instance.inventory.function.TotemDudaMari) {
        needAden = (int)(needAden + 50L * item.getCount());
        this.remove_list.add(item);
      } 
      b++;
    } 
    if (this.remove_list.size() > 0) {
      for (ItemInstance item : this.remove_list)
        pc.getInventory().remove(item); 
      if (needAden > 0) {
        ItemInstance aden = ItemsTable.getInstance().newItem(5, false, true);
        aden.setCount(needAden);
        pc.SendPacket((S_BasePacket)new S_ServerMessage(143, getName(), aden.toString()));
        pc.getInventory().insert(aden, needAden);
      } 
    } else {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(329, "图腾"));
    } 
  }
}
