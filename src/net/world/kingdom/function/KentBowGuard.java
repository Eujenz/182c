package net.world.kingdom.function;

import net.database.bean.Npc;
import net.world.kingdom.Kingdom;

public class KentBowGuard extends KentGuard {
  public KentBowGuard(Kingdom k, Npc npc) {
    super(k, npc);
    this.Areaatk = 8;
  }
  
  public void toWalk(long time) {
    this.ai_start_time = time;
    this.ai_time = getNpc().getModespeed(getGfxMode());
    serarchChaoticPlayer();
    if (this.attackList.size() > 0)
      setFight(true); 
  }
}
