package net.network.server;

import net.world.object.L1Object;

public class S_ObjectRemove extends S_BasePacket {
  public S_ObjectRemove(L1Object o) {
    writeC(21);
    writeD(o.getObjectId());
  }
}
