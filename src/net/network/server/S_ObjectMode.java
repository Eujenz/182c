package net.network.server;

import net.world.object.L1Object;

public class S_ObjectMode extends S_BasePacket {
  public S_ObjectMode(L1Object obj) {
    writeC(29);
    writeD(obj.getObjectId());
    writeC(obj.getGfxMode());
    writeC(255);
    writeC(255);
  }
  
  public S_ObjectMode(L1Object obj, int mode) {
    writeC(29);
    writeD(obj.getObjectId());
    writeC(mode);
    writeC(255);
    writeC(255);
  }
}
