package net.network.server;

import net.world.object.L1Object;

public class S_ObjectPoly extends S_BasePacket {
  public S_ObjectPoly(L1Object cha) {
    writeC(39);
    writeD(cha.getObjectId());
    writeH(cha.getGfx());
    writeC(cha.getGfxMode());
    writeC(255);
    writeC(255);
  }
  
  public S_ObjectPoly(int time) {
    writeC(123);
    writeC(35);
    writeH(time);
  }
}
