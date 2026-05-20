package net.network.server;

import net.util.Util;

public class S_WorldJoin extends S_BasePacket {
  public S_WorldJoin() {
    writeC(7);
    writeC(3);
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
