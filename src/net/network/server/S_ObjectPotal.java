package net.network.server;

public class S_ObjectPotal extends S_BasePacket {
  public S_ObjectPotal(int map, int targetMap) {
    writeC(85);
    writeH(targetMap);
    switch (map) {
      case 0:
        writeC(252);
        writeC(253);
        writeC(96);
        writeC(0);
        return;
      case 3:
        writeC(70);
        writeC(255);
        writeC(46);
        writeC(0);
        return;
      case 4:
        writeC(244);
        writeC(9);
        writeC(155);
        writeC(253);
        return;
      case 63:
        writeC(76);
        writeC(0);
        writeC(100);
        writeC(0);
        return;
      case 75:
        writeC(246);
        writeC(255);
        writeC(134);
        writeC(0);
        return;
    } 
    writeC(244);
    writeC(9);
    writeC(155);
    writeC(253);
  }
}
