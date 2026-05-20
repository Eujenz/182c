package net.world.monster;

import net.database.bean.Monster;
import net.world.instance.MonsterInstance;

public class Werewolf extends MonsterInstance {
  private static final int[] item_list = new int[] { 0, 20, 69, 74, 206, 778, 163, 159 };
  
  public Werewolf(Monster m) {
    super(m);
  }
  
  public void toWalk(long time) {
    super.toWalk(time);
    if (isFight() || 
      SearchItem(item_list));
  }
}
