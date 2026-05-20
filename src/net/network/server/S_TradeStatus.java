package net.network.server;

import net.util.Util;

public class S_TradeStatus extends S_BasePacket {
  public S_TradeStatus(boolean status) {
    writeC(62);
    writeC(status ? 0 : 1);
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
