package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.instance.PcInstance;

public class C_Attack extends C_BasePacket {
  private int objid;
  
  private int locx;
  
  private int locy;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    if (pc.isLockFreeze())
      return; 
    this.objid = readD();
    this.locx = readH();
    this.locy = readH();
    pc.Attack(pc.getObject(this.objid), this.locx, this.locy, 1, 0);
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.objid);
      sb.append(" , ");
      sb.append(this.locx);
      sb.append(" , ");
      sb.append(this.locy);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
