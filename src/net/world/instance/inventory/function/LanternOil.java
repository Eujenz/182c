package net.world.instance.inventory.function;

import java.util.List;
import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.world.instance.ItemInstance;
import net.world.object.Character;

public class LanternOil extends ItemInstance {
  public LanternOil(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    List<ItemInstance> list = cha.getInventory().getItemNameId(326);
    if (list != null)
      for (ItemInstance lantern : list) {
        if (lantern != null)
          lantern.setTime(3600); 
      }  
    setCount(cha, getCount() - 1L);
  }
}
