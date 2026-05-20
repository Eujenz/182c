package net.network.server;

import net.world.object.L1Object;

public class S_ObjectRestore extends S_BasePacket {
  public S_ObjectRestore(L1Object cha, L1Object target) {
    writeC(17);
    writeD(target.getObjectId());
    writeC(target.getGfxMode());
    if (cha == null) {
      writeD(target.getObjectId());
    } else {
      writeD(cha.getObjectId());
    } 
    writeH(target.getGfx());
  }
}
