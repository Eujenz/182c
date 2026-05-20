package net.network.server;

import net.util.Util;

public class S_CharacterRemove extends S_BasePacket {
  public static final int CHAR_DELETE_1 = 5;
  
  private String log_type;
  
  public S_CharacterRemove(int type) {
    this.log_type = String.valueOf(type);
    writeC(6);
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
