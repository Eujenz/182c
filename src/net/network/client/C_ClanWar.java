package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.function.ClanSystem;
import net.world.instance.PcInstance;

public class C_ClanWar extends C_BasePacket {
  private String clan_name;
  
  private int type;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.type = readC();
    this.clan_name = readS();
    switch (this.type) {
      case 0:
        if (pc.getClanName().equalsIgnoreCase(this.clan_name)) {
          pc.Message("你不能对自已宣战。");
          break;
        } 
        if (ClanSystem.getInstance().getClanName(this.clan_name) == null) {
          pc.Message("没有这个血盟。");
          break;
        } 
        ClanSystem.getInstance().War(pc, this.clan_name);
        break;
      case 2:
        ClanSystem.getInstance().WarSubmission(pc, this.clan_name);
        break;
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
      sb.append(this.type);
      sb.append(", ");
      sb.append(this.clan_name);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
