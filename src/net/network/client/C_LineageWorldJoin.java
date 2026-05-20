package net.network.client;

import net.LineageClient;
import net.database.CharacterTable;
import net.util.Util;

public class C_LineageWorldJoin extends C_BasePacket {
  private String name;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    this.name = readS();
    CharacterTable.getInstance().CharacterWorldJoin(lc, this.name);
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.name);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
