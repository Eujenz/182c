package net.network.server;

import net.util.Util;

public class S_LoginsOk extends S_BasePacket {
  public S_LoginsOk(boolean first) {
    writeC(123);
    writeC(46);
    if (first) {
      writeD(0);
      writeD(0);
      writeD(0);
      writeH(69);
      writeH(1);
    } else {
      writeC(8);
      writeC(131);
      writeC(67);
      writeC(235);
      writeC(10);
      writeC(73);
      writeC(24);
      writeC(67);
      writeC(35);
      writeC(171);
      writeC(232);
      writeC(208);
      writeC(129);
      writeC(131);
      writeC(143);
      writeC(151);
      writeC(49);
      writeC(0);
      writeC(79);
      writeC(128);
      writeC(93);
      writeC(128);
    } 
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    return sb.toString();
  }
}
