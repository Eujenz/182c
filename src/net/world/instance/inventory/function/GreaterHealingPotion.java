package net.world.instance.inventory.function;

import net.database.bean.Item;

public class GreaterHealingPotion extends HealingPotion {
  public GreaterHealingPotion(Item i) {
    super(i);
    this.MIN_HP = 25;
    this.MAX_HP = 60;
  }
}
