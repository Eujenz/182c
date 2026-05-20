package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_InventoryBress;
import net.world.instance.ItemInstance;
import net.world.object.Character;

public class ScrollLabeledPRATYAVAYAH extends ItemInstance {
  public ScrollLabeledPRATYAVAYAH(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    if (getBless() == 0) {
      ItemInstance item = cha.getInventory().getItemInvId(bp.readD());
      if (item != null && item.getBless() == 2) {
        item.setBless(1);
        cha.SendPacket((S_BasePacket)new S_InventoryBress(item));
      } 
    } else {
      byte b;
      int i;
      ItemInstance[] arrayOfItemInstance;
      for (i = (arrayOfItemInstance = cha.getInventory().getAll()).length, b = 0; b < i; ) {
        ItemInstance temp = arrayOfItemInstance[b];
        if (temp.isEquipped() && temp.getBless() == 2) {
          temp.setBless(1);
          cha.SendPacket((S_BasePacket)new S_InventoryBress(temp));
        } 
        b++;
      } 
    } 
  }
}
