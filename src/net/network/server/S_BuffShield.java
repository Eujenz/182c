package net.network.server;

public final class S_BuffShield extends S_BasePacket {
  public S_BuffShield(int time) {
    this(time, 15);
  }
  
  public S_BuffShield(int time, int type) {
    writeC(109);
    writeH(time);
    writeC(type);
  }
}
