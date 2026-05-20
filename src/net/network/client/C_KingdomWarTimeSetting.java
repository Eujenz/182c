package net.network.client;

import net.LineageClient;
import net.network.server.S_BasePacket;
import net.network.server.S_KingdomWarTime;
import net.util.Util;
import net.world.function.ClanSystem;
import net.world.instance.PcInstance;

public class C_KingdomWarTimeSetting extends C_BasePacket {
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    if (pc.getClanId() == 0)
      return; 
    if (ClanSystem.getInstance().getClan(pc.getClanId()).getKingdom() != null && pc.getClassType() == 0 && 
      ClanSystem.getInstance().getClan(pc.getClanId()).getKingdom().isWarTimeSetting())
      pc.SendPacket((S_BasePacket)new S_KingdomWarTime(pc)); 
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
