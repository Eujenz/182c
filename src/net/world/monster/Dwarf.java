package net.world.monster;

import net.database.bean.Monster;
import net.world.instance.MonsterInstance;

public class Dwarf extends MonsterInstance {
  private static final int[] item_list = new int[] { -1 };
  
  public Dwarf(Monster m) {
    super(m);
  }
  
  public void toWalk(long time) {
    super.toWalk(time);
    if (isFight() || 
      SearchItem(item_list));
  }
}
