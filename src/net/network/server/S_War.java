package net.network.server;

public class S_War extends S_BasePacket {
  public S_War(int type, String name1, String name2) {
    writeC(54);
    writeC(type);
    writeS(name1);
    writeS(name2);
  }
  
  public S_War(int status, int kingdom) {
    writeC(123);
    writeC(status);
    writeC(kingdom);
    writeD(0);
  }
}
