package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.function.PartySystem;
import net.world.instance.PcInstance;

public final class C_PartyBan extends C_BasePacket {
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    String name = readS();
    PartySystem.getInstance().banParty(pc, name);
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
