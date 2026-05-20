package net.network.server;

import net.util.Util;

public class S_ServerMessageYesNo extends S_BasePacket {
  private String log_type;
  
  public S_ServerMessageYesNo(int type, String[] msg) {
    this.log_type = String.valueOf(type);
    writeC(36);
    writeH(type);
    if (msg != null) {
      byte b;
      int i;
      String[] arrayOfString;
      for (i = (arrayOfString = msg).length, b = 0; b < i; ) {
        String d = arrayOfString[b];
        writeS(d);
        b++;
      } 
    } 
  }
  
  public S_ServerMessageYesNo(int type, String msg) {
    this.log_type = String.valueOf(type);
    writeC(36);
    writeH(type);
    writeS(msg);
  }
  
  public S_ServerMessageYesNo(int type) {
    this.log_type = String.valueOf(type);
    writeC(36);
    writeH(type);
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
