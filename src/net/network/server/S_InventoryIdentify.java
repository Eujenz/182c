package net.network.server;

import net.util.Util;
import net.world.instance.ItemInstance;

public class S_InventoryIdentify extends S_BasePacket {
  private String log_item;
  
  private String log_type;
  
  private String log_action;
  
  public S_InventoryIdentify(ItemInstance items, String dType, int action) {
    this.log_item = items.toString();
    this.log_type = dType;
    this.log_action = String.valueOf(action);
    writeC(63);
    writeH(action);
    if ("Weapon".equalsIgnoreCase(dType)) {
      writeH(134);
      writeC(5);
      writeS(items.getName());
      writeS(Integer.toString(items.getItem().get_dmgsmall()));
      writeS(Integer.toString(items.getItem().get_dmglarge()));
      writeS(Integer.toString(items.getDurability()));
      writeS(Integer.toString(items.getItem().getWeight()));
    } else if ("Armor".equalsIgnoreCase(dType)) {
      writeH(135);
      writeC(4);
      writeS(items.getName());
      writeS(Integer.toString(items.getItem().get_ac()));
      writeS(Integer.toString(items.getItem().getWeight()));
      writeS(Integer.toString(items.getDurability()));
    } else if ("Food".equalsIgnoreCase(dType)) {
      writeH(136);
      writeC(3);
      writeS(items.getName());
      writeS(Integer.toString(3));
      writeS(Integer.toString(items.getItem().getWeight()));
    } else if ("Have".equalsIgnoreCase(dType)) {
      writeH(137);
      writeC(3);
      writeS(items.getName());
      writeS(Integer.toString(items.getHaveCount()));
      writeS(Integer.toString(items.getItem().getWeight()));
    } else {
      writeH(138);
      writeC(2);
      writeS(items.getName());
      writeS(Integer.toString(items.getItem().getWeight()));
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
      sb.append(this.log_item);
      sb.append(" , ");
      sb.append(this.log_type);
      sb.append(" , ");
      sb.append(this.log_action);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
