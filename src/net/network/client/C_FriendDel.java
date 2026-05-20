package net.network.client;

import net.LineageClient;
import net.database.FriendTable;
import net.util.Util;
import net.world.instance.PcInstance;

public class C_FriendDel extends C_BasePacket {
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    String name = readS();
    if (FriendTable.friendCheck(pc.getName(), name))
      FriendTable.friendDel(pc.getName(), name); 
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
