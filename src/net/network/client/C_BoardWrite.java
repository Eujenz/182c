package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.instance.BoardInstance;
import net.world.instance.PcInstance;
import net.world.object.L1Object;

public class C_BoardWrite extends C_BasePacket {
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    int obj_id = readD();
    L1Object o = pc.getObject(obj_id);
    if (o != null && o instanceof BoardInstance)
      ((BoardInstance)o).WriteDB(pc, readS(), readS()); 
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
