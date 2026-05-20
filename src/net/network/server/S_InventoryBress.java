package net.network.server;

import net.util.Util;
import net.world.instance.ItemInstance;

public class S_InventoryBress extends S_BasePacket {
  private String log_item;
  
  private String log_invid;
  
  private String log_bress;
  
  public S_InventoryBress(ItemInstance item) {
    this.log_item = item.toString();
    this.log_invid = String.valueOf(item.getInvID());
    this.log_bress = String.valueOf(item.getBless());
    writeC(14);
    writeD(item.getInvID());
    writeC(item.getBless());
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
      sb.append(" , ");
      sb.append(this.log_bress);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
