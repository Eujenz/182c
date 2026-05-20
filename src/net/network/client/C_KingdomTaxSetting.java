package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.instance.PcInstance;
import net.world.kingdom.function.Chamberlain;
import net.world.object.L1Object;

public class C_KingdomTaxSetting extends C_BasePacket {
  private int obj_id;
  
  private int tax;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.obj_id = readD();
    this.tax = readC();
    L1Object o = pc.getObject(this.obj_id);
    if (o != null && o instanceof Chamberlain)
      ((Chamberlain)o).setTax(pc, this.tax); 
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
      sb.append(this.tax);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
