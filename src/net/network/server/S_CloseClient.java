package net.network.server;

import net.util.Util;

public class S_CloseClient extends S_BasePacket {
  private String log_type;
  
  public S_CloseClient(int type) {
    this.log_type = String.valueOf(type);
    writeC(102);
    writeC(type);
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.log_type);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
