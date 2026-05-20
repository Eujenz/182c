package net.world.instance.inventory.function;

import net.database.bean.Item;

public class LesserHealingPotion extends HealingPotion {
  public LesserHealingPotion(Item i) {
    super(i);
    this.MIN_HP = 5;
    this.MAX_HP = 15;
  }
}
