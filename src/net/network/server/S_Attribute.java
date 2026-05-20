package net.network.server;

import net.util.Util;
import net.world.object.L1Object;

public class S_Attribute extends S_BasePacket {
  private String log_x;
  
  private String log_y;
  
  private String log_h;
  
  private String log_moving;
  
  public S_Attribute(L1Object door) {
    writeC(34);
    switch (door.getHeading()) {
      case 4:
        writeH(door.getX());
        writeH(door.getY() + 1);
        writeC(0);
        this.log_x = String.valueOf(door.getX());
        this.log_y = String.valueOf(door.getY() + 1);
        this.log_h = String.valueOf(0);
        break;
      case 6:
        writeH(door.getX() - 1);
        writeH(door.getY());
        writeC(1);
        this.log_x = String.valueOf(door.getX() - 1);
        this.log_y = String.valueOf(door.getY());
        this.log_h = String.valueOf(1);
        break;
    } 
    if (door.getGfxMode() == 28) {
      writeC(0);
      this.log_moving = "true";
    } else {
      writeC(65);
      this.log_moving = "false";
    } 
  }
  
  public S_Attribute(int x, int y, int heading, boolean move) {
    writeC(34);
    writeH(x);
    writeH(y);
    if (heading == 4) {
      writeC(0);
    } else {
      writeC(1);
    } 
    if (move) {
      writeC(0);
      this.log_moving = "true";
    } else {
      writeC(65);
      this.log_moving = "false";
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
      sb.append(this.log_x);
      sb.append(" , ");
      sb.append(this.log_y);
      sb.append(" , ");
      sb.append(this.log_h);
      sb.append(" , ");
      sb.append(this.log_moving);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
