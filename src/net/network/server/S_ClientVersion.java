package net.network.server;

import net.Config;
import net.util.Util;

public class S_ClientVersion extends S_BasePacket {
  public S_ClientVersion() {
    writeC(0);
    writeC(0);
    writeC(22);
    writeD(70313);
    writeD(60307);
    writeD(660130);
    writeD(70320);
    writeD(1174437951);
    writeC(0);
    writeC(0);
    writeC(Config.CLIENT_LANGUAGE);
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
