package net.network.server;

import net.util.Util;

public class S_AmountCharacter extends S_BasePacket {
  private String log_count;
  
  public S_AmountCharacter(int count) {
    writeC(3);
    writeC(count);
    writeC(4);
    this.log_count = String.valueOf(count);
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.log_count);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
