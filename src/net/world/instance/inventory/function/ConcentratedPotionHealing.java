package net.world.instance.inventory.function;

import net.database.bean.Item;

public class ConcentratedPotionHealing extends HealingPotion {
  public ConcentratedPotionHealing(Item i) {
    super(i);
    this.MIN_HP = 4;
    this.MAX_HP = 12;
  }
}
