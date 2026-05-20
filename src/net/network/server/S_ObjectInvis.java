package net.network.server;

public class S_ObjectInvis extends S_BasePacket {
  public S_ObjectInvis(int id, boolean ck) {
    writeC(52);
    writeD(id);
    writeH(ck ? 1 : 0);
  }
}
