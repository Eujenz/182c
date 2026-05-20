package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.world.instance.ItemArmorInstance;
import net.world.object.Character;

public class RingPolymorphControl extends ItemArmorInstance {
  public RingPolymorphControl(Item _item) {
    super(_item);
  }
  
  public void itemOption(Character cha) {
    super.itemOption(cha);
  }
}
