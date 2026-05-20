package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.function.ClanSystem;
import net.world.function.bean.Clan;
import net.world.instance.PcInstance;

public class C_QuitGame extends C_BasePacket {
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    if (pc.isUseClanWareHouse()) {
      Clan clan = ClanSystem.getInstance().getClan(pc.getClanId());
      if (clan != null)
        clan.setLockWarehouse(false); 
    } 
    lc.close();
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
