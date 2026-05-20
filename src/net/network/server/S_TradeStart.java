package net.network.server;

import net.util.Util;

public class S_TradeStart extends S_BasePacket {
  public S_TradeStart(String name) {
    writeC(60);
    writeS(name);
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
