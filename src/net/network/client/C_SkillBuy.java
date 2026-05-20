package net.network.client;

import net.LineageClient;
import net.network.server.S_BasePacket;
import net.network.server.S_SkillBuyList;
import net.util.Util;
import net.world.instance.PcInstance;
import net.world.instance.inventory.Inventory;

public class C_SkillBuy extends C_BasePacket {
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    Inventory inv = pc.getInventory();
    if (inv == null) {
      pc.Message("什么都没带不能学魔法。");
      return;
    } 
    if (!inv.isSkillCheckHelmMagic(pc))
      return; 
    pc.SendPacket((S_BasePacket)new S_SkillBuyList(pc));
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
