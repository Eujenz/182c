package net.network.server;

import net.world.object.L1Object;

public final class S_PinkName extends S_BasePacket {
  public S_PinkName(L1Object obj) {
    this(obj.getObjectId(), obj.getPinkNameTime());
  }
  
  public S_PinkName(int objId, int time) {
    writeC(106);
    writeD(objId);
    writeH(time);
  }
}
