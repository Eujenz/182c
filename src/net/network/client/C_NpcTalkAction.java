package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.instance.PcInstance;
import net.world.object.L1Object;

public class C_NpcTalkAction extends C_BasePacket {
  private int obj_id;
  
  private String text1;
  
  private String text2;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.obj_id = readD();
    this.text1 = readS();
    this.text2 = readS();
    if (!pc.isDead()) {
      L1Object o = pc.getObject(this.obj_id);
      if (o != null)
        o.Talk(pc, this.text1, this.text2); 
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
      sb.append(" , ");
      sb.append(this.text1);
      sb.append(" , ");
      sb.append(this.text2);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
