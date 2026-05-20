package net.network.client;

import net.LineageClient;
import net.database.AccountTable;
import net.util.Util;

public class C_LoginsOut extends C_BasePacket {
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    AccountTable.getInstance().LoginsOut(lc);
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
