package net.network.server;

import net.world.object.L1Object;

public class S_ObjectHitratio extends S_BasePacket {
  public S_ObjectHitratio(L1Object cha, boolean ck) {
    writeC(104);
    writeD(cha.getObjectId());
    if (ck) {
      double nowhp = cha.getCurrentHp();
      double maxhp = cha.getTotalHp();
      writeC((int)(nowhp / maxhp * 100.0D));
    } else {
      writeC(255);
    } 
  }
}
