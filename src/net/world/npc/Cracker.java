package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ObjectHeading;
import net.util.Util;
import net.world.object.Character;
import net.world.object.L1Object;

public class Cracker extends L1Object {
  public void toAttack(L1Object target, int type) {
    if (target instanceof Character) {
      Character c = (Character)target;
      if (target.getLevel() < 5)
        c.addExp(c.Exp(this, Util.rand(1, 2))); 
    } 
    setHeading(getHeading() + 1);
    if (getHeading() > 3)
      setHeading(0); 
    SendPacket((S_BasePacket)new S_ObjectHeading(this), true);
  }
}
