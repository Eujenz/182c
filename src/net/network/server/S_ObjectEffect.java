package net.network.server;

import net.world.object.L1Object;

public class S_ObjectEffect extends S_BasePacket {
  public S_ObjectEffect(L1Object o, int id) {
    writeC(55);
    writeD(o.getObjectId());
    writeH(id);
  }
  
  public S_ObjectEffect(int _x, int _y, int id) {
    writeC(83);
    writeH(_x);
    writeH(_y);
    writeH(id);
    writeC(6);
  }
  
  public S_ObjectEffect(int id) {
    writeC(74);
    writeC(0);
    writeH(id);
  }
}
