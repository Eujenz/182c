package net.network.server;

import net.util.Util;
import net.world.instance.ItemInstance;

public class S_TradeAddItem extends S_Inventory {
  public S_TradeAddItem(ItemInstance item, int type) {
    writeC(61);
    writeC(type);
    writeH(item.getItem().get_gfxid());
    if (item.isDefinite()) {
      switch (item.getItem().getType1()) {
        case 1:
          writeS(getName(item));
          writeC(item.getBless());
          this.TohandSword = item.getItem().isTohand();
          weapon(item);
          break;
        case 2:
          writeS(getName(item));
          writeC(item.getBless());
          armor(item);
          break;
        case 0:
        case 3:
          writeS(getName(item));
          writeC(item.getBless());
          writeC(6);
          writeC(23);
          writeC(item.getItem().get_material());
          writeD(item.getItem().getWeight() * (int)item.getCount());
          break;
      } 
    } else {
      writeS(getName(item));
      writeC(3);
      writeC(0);
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
