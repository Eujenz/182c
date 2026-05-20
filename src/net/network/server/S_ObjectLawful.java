package net.network.server;

import net.world.object.L1Object;

public class S_ObjectLawful extends S_BasePacket {
  public S_ObjectLawful(L1Object o) {
    writeC(89);
    writeD(o.getObjectId());
    writeD(o.getLawful());
  }
}
