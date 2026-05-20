package net.network.server;

public class S_ObjectSpMr extends S_BasePacket {
  public S_ObjectSpMr(int sp, int mr) {
    writeC(86);
    writeC(sp);
    writeC(mr);
  }
}
