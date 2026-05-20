package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.function.TradeSystem;
import net.world.instance.PcInstance;

public class C_ItemTradeOk extends C_BasePacket {
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    TradeSystem.getInstance().tradeOk(pc);
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
