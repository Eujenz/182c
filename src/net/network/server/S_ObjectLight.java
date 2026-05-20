package net.network.server;

import net.world.object.L1Object;

public class S_ObjectLight extends S_BasePacket {
  public S_ObjectLight(L1Object obj) {
    writeC(27);
    writeD(obj.getObjectId());
    writeC(obj.getLight());
  }
}
