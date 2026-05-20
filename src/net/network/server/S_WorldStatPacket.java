package net.network.server;

import net.Config;
import net.util.Util;

public class S_WorldStatPacket extends S_BasePacket {
  public S_WorldStatPacket(int type) {
    writeC(51);
    writeC(type);
  }
  
  public S_WorldStatPacket(int type, int objID) {
    writeC(71);
    writeC(type);
    writeD(objID);
  }
  
  public S_WorldStatPacket() {
    writeC(33);
    writeD(Config.WORLDTIME);
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    return sb.toString();
  }
}
