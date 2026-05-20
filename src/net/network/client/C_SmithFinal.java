package net.network.client;

import net.LineageClient;
import net.network.server.S_BasePacket;
import net.network.server.S_InventoryStatus;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;

public class C_SmithFinal extends C_BasePacket {
  private int invid;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.invid = readD();
    ItemInstance weapon = pc.getInventory().getItemInvId(this.invid);
    if (weapon != null && weapon instanceof net.world.instance.ItemWeaponInstance)
      if (pc.getInventory().Aden((100 * weapon.getDurability()), true)) {
        weapon.setDurability(0);
        lc.SendPacket((S_BasePacket)new S_InventoryStatus(weapon));
        lc.SendPacket((S_BasePacket)new S_ServerMessage(464, weapon.toString()));
      } else {
        lc.SendPacket((S_BasePacket)new S_ServerMessage(189));
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
      sb.append(this.invid);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
