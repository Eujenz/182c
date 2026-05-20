package net.network.server;

import net.util.Util;

public class S_BuffBlind extends S_BasePacket {
  private String log_blind;
  
  public S_BuffBlind(int blind) {
    this.log_blind = String.valueOf(blind);
    writeC(10);
    writeC(blind);
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.log_blind);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
