package net.network.server;

public class S_ObjectPoison extends S_BasePacket {
  public S_ObjectPoison(int objId, boolean poison, boolean lock) {
    writeC(50);
    writeD(objId);
    writeC(poison ? 1 : 0);
  }
  
  public S_ObjectPoison(int type) {
    writeC(37);
    writeC(type);
  }
}
