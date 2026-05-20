package net.world.kingdom.function;

import net.database.bean.Npc;
import net.world.kingdom.Kingdom;

public class WindawoodGuard extends KingdomGuard {
  public WindawoodGuard(Kingdom k, Npc npc) {
    super(k, npc);
    switch (npc.get_npcId()) {
      case 39:
      case 589:
        this.Areaatk = 2;
        break;
      case 588:
        this.Areaatk = 8;
        break;
    } 
  }
}
