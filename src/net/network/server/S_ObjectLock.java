package net.network.server;

public class S_ObjectLock extends S_BasePacket {
  public S_ObjectLock() {
    writeC(37);
    writeC(9);
    writeC(75);
    writeC(0);
    writeC(148);
    writeC(163);
    writeC(253);
    writeC(146);
  }
  
  public S_ObjectLock(boolean stop) {
    if (stop) {
      writeC(57);
      writeC(15);
      writeC(46);
      writeC(44);
      writeC(1);
      writeC(205);
      writeC(107);
      writeC(44);
    } else {
      writeC(45);
      writeC(96);
      writeC(119);
      writeC(20);
      writeC(213);
      writeC(200);
      writeC(3);
      writeC(75);
    } 
  }
}
