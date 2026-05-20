package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.world.instance.ItemArmorInstance;

public class ElvenShield extends ItemArmorInstance {
  public ElvenShield(Item _item) {
    super(_item);
  }
  
  public void isSetting() {
    if (getCha() != null && getCha().getClassType() == 2)
      setDynamicMr(5); 
  }
}
