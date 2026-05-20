package net.world.kingdom.function;

import net.database.bean.Npc;
import net.world.kingdom.Kingdom;

public class KentGuard extends KingdomGuard {
  private final Npc _npc;
  
  public KentGuard(Kingdom k, Npc npc) {
    super(k, npc);
    this._npc = npc;
    switch (npc.get_npcId()) {
      case 39:
      case 517:
        this.Areaatk = 2;
        break;
      case 516:
        this.Areaatk = 18;
        break;
    } 
  }
  
  protected boolean StartMove(int tx, int ty) {
    if (this._npc.get_npcId() == 516)
      return false; 
    return super.StartMove(tx, ty);
  }
}
