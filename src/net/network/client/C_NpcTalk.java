package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.instance.PcInstance;
import net.world.object.L1Object;

public class C_NpcTalk extends C_BasePacket {
  private int obj_id;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.obj_id = readD();
    if (!pc.isDead()) {
      L1Object o = pc.getObject(this.obj_id);
      if (o != null)
        o.Talk(pc); 
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
      sb.append(this.obj_id);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
