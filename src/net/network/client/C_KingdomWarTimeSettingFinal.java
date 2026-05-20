package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.function.ClanSystem;
import net.world.function.bean.Clan;
import net.world.instance.PcInstance;
import net.world.kingdom.Kingdom;

public class C_KingdomWarTimeSettingFinal extends C_BasePacket {
  private int idx;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.idx = readD();
    Clan clan = ClanSystem.getInstance().getClan(pc.getClanId());
    if (clan != null) {
      Kingdom k = clan.getKingdom();
      if (k != null)
        k.setWarTimeSetting(pc, this.idx); 
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
      sb.append(this.idx);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
