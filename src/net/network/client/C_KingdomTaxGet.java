package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.function.ClanSystem;
import net.world.instance.PcInstance;
import net.world.kingdom.Kingdom;

public class C_KingdomTaxGet extends C_BasePacket {
  private int kingdom_type;
  
  private long count;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.kingdom_type = readD();
    this.count = readD();
    Kingdom k = ClanSystem.getInstance().getKingdom(this.kingdom_type);
    if (k != null)
      k.removeTax(pc, this.count); 
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.kingdom_type);
      sb.append(" , ");
      sb.append(this.count);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
