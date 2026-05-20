package net.network.server;

import net.util.Util;
import net.world.instance.ItemInstance;

public class S_InventoryDelete extends S_BasePacket {
  private String log_item;
  
  private String log_invid;
  
  public S_InventoryDelete(ItemInstance item) {
    this.log_item = item.toString();
    this.log_invid = String.valueOf(item.getInvID());
    writeC(23);
    writeD(item.getInvID());
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.log_item);
      sb.append(" , ");
      sb.append(this.log_invid);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
