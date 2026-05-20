package net.network.server;

import net.util.Util;

public class S_WorldMap extends S_BasePacket {
  public S_WorldMap(int map) {
    writeC(40);
    writeH(map);
    switch (map) {
      case 63:
      case 65:
        writeC(1);
        return;
    } 
    writeC(0);
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
