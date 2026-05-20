package net.network.server;

import net.util.Util;

public class S_BuffAqua extends S_BasePacket {
  private String log_objid;
  
  private String log_time;
  
  public S_BuffAqua(int objId, int time) {
    this.log_objid = String.valueOf(objId);
    this.log_time = String.valueOf(time);
    writeC(119);
    writeD(objId);
    writeH(time);
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.log_objid);
      sb.append(" , ");
      sb.append(this.log_time);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
