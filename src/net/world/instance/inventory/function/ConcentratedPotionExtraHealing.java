package net.world.instance.inventory.function;

import net.database.bean.Item;

public class ConcentratedPotionExtraHealing extends HealingPotion {
  public ConcentratedPotionExtraHealing(Item i) {
    super(i);
    this.MIN_HP = 18;
    this.MAX_HP = 31;
  }
}
