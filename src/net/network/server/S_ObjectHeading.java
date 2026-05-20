package net.network.server;

import net.world.object.L1Object;

public class S_ObjectHeading extends S_BasePacket {
  public S_ObjectHeading(L1Object o) {
    writeC(28);
    writeD(o.getObjectId());
    writeC(o.getHeading());
  }
}
