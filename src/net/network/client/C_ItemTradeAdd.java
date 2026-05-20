package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.function.TradeSystem;
import net.world.instance.PcInstance;

public class C_ItemTradeAdd extends C_BasePacket {
  private int inv_id;
  
  private long count;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.inv_id = readD();
    this.count = readD();
    TradeSystem.getInstance().addItem(pc, this.inv_id, this.count);
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.inv_id);
      sb.append(" , ");
      sb.append(this.count);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
