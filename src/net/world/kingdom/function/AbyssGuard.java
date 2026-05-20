package net.world.kingdom.function;

import net.database.bean.Npc;
import net.world.kingdom.Kingdom;
import net.world.kingdom.KingdomAbyss;

public class AbyssGuard extends KingdomGuard {
  public AbyssGuard(Npc n) {
    super((Kingdom)KingdomAbyss.getInstance(), n);
  }
  
  public AbyssGuard(Kingdom k, Npc n) {
    super(k, n);
  }
}
