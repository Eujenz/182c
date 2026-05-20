package net.network.server;

public final class S_PacketBox extends S_BasePacket {
  public static final int MSG_ELF = 15;
  
  public S_PacketBox(int subCode, int value) {
    writeC(123);
    writeC(subCode);
    switch (subCode) {
      case 15:
        writeC(value);
        writeC(0);
        break;
    } 
  }
}
