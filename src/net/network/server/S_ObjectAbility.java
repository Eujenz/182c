package net.network.server;

import net.util.Util;

public class S_ObjectAbility extends S_BasePacket {
  private String log_type;
  
  private String log_ck;
  
  public S_ObjectAbility(int type, boolean ck) {
    this.log_type = String.valueOf(type);
    this.log_ck = String.valueOf(ck);
    writeC(38);
    writeC(type);
    if (ck) {
      writeC(1);
    } else {
      writeC(0);
    } 
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
      sb.append(" , ");
      sb.append(this.log_ck);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
