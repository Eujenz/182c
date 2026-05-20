package net.network.server;

import net.util.Util;
import net.world.object.L1Object;

public class S_ObjectTitle extends S_BasePacket {
  private String log_object;
  
  private String log_title;
  
  public S_ObjectTitle(L1Object obj) {
    this.log_object = obj.toString();
    this.log_title = obj.getTitle();
    writeC(47);
    writeD(obj.getObjectId());
    writeS(obj.getTitle());
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.log_object);
      sb.append(" , ");
      sb.append(this.log_title);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
