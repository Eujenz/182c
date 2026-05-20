package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.function.ClanSystem;
import net.world.instance.PcInstance;

public class C_ClanMarkDownload extends C_BasePacket {
  private int clan_id;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.clan_id = readD();
    ClanSystem.getInstance().IconRead(pc, this.clan_id);
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
