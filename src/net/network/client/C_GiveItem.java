package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.instance.PcInstance;

public class C_GiveItem extends C_BasePacket {
  private int t_obj;
  
  private int etc;
  
  private int inv_id;
  
  private int count;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.t_obj = readD();
    this.etc = readD();
    this.inv_id = readD();
    this.count = readD();
    pc.toGiveItem(pc.getObject(this.t_obj), this.inv_id, this.count);
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.t_obj);
      sb.append(" , ");
      sb.append(this.etc);
      sb.append(" , ");
      sb.append(this.inv_id);
      sb.append(" , ");
      sb.append(this.count);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
