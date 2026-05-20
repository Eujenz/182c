package net.world.instance.inventory.function;

import net.database.bean.Item;

public class ConcentratedPotionGreaterHealing extends HealingPotion {
  public ConcentratedPotionGreaterHealing(Item i) {
    super(i);
    this.MIN_HP = 29;
    this.MAX_HP = 44;
  }
}
