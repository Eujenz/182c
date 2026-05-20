package net.network.server;

import net.util.Util;
import net.world.instance.ItemInstance;

public class S_InventoryAdd extends S_Inventory {
  public S_InventoryAdd(ItemInstance items) {
    writeC(22);
    this.enLevel = items.getEnLevel();
    this.durability = items.getDurability();
    switch (items.getItem().getType1()) {
      case 0:
        etc(items);
        break;
      case 1:
        this.TohandSword = items.getItem().isTohand();
        writeD(items.getInvID());
        writeH(items.getItem().getType2());
        writeH(items.getItem().get_gfxid());
        writeC(items.getBless());
        writeD((int)items.getCount());
        writeC(items.isDefinite() ? 1 : 0);
        if (!items.isDefinite()) {
          writeS(getName(items));
          writeC(0);
          break;
        } 
        writeS(getName(items));
        weapon(items);
        break;
      case 2:
        writeD(items.getInvID());
        writeH(items.getItem().getType2());
        writeH(items.getItem().get_gfxid());
        writeC(items.getBless());
        writeD((int)items.getCount());
        writeC(items.isDefinite() ? 1 : 0);
        if (!items.isDefinite()) {
          writeS(getName(items));
          writeC(0);
          break;
        } 
        writeS(getName(items));
        armor(items);
        break;
      case 3:
        etc(items);
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
    return sb.toString();
  }
}
