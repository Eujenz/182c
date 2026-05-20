package net.network.server;

import net.util.Util;
import net.world.object.L1Object;

public class S_BuffSpeed extends S_BasePacket {
  private String log_object;
  
  private int log_type;
  
  private int log_speed;
  
  private int log_time;
  
  public S_BuffSpeed(L1Object cha, int type, int speed, int time) {
    this.log_object = cha.getName();
    this.log_type = type;
    this.log_speed = speed;
    this.log_time = time;
    switch (type) {
      case 0:
        writeC(41);
        break;
      case 1:
        writeC(98);
        break;
    } 
    writeD(cha.getObjectId());
    writeC(speed);
    writeH(time);
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    sb.append(this.log_object);
    sb.append(" , ");
    sb.append(this.log_type);
    sb.append(" , ");
    sb.append(this.log_speed);
    sb.append(" , ");
    sb.append(this.log_time);
    return sb.toString();
  }
}
