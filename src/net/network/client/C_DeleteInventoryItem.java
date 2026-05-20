package net.network.client;

import net.LineageClient;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;

public class C_DeleteInventoryItem extends C_BasePacket {
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    int itemObjectId = readD();
    ItemInstance item = pc.getInventory().getItemInvId(itemObjectId);
    if (item == null)
      return; 
    if (!item.getItem().isCanDrop()) {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(125));
      return;
    } 
    if (item.isEquipped()) {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(125));
      return;
    } 
    pc.getInventory().remove(item);
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
