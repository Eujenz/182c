package net.network.server;

import net.util.Util;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;

public class S_InventoryList extends S_Inventory {
  public S_InventoryList(PcInstance cha) {
    writeC(65);
    writeC(cha.getInventory().getCount());
    byte b;
    int i;
    ItemInstance[] arrayOfItemInstance;
    for (i = (arrayOfItemInstance = cha.getInventory().getAll()).length, b = 0; b < i; ) {
      ItemInstance temp = arrayOfItemInstance[b];
      this.enLevel = temp.getEnLevel();
      this.durability = temp.getDurability();
      switch (temp.getItem().getType2()) {
        case 0:
          etc(temp);
          break;
        case 1:
          this.TohandSword = temp.getItem().isTohand();
          writeD(temp.getInvID());
          if (temp.getItem().getType1() == 0) {
            writeH(22);
          } else {
            writeH(temp.getItem().getType2());
          } 
          writeH(temp.getItem().get_gfxid());
          writeC(temp.getBless());
          writeD((int)temp.getCount());
          if (!temp.isDefinite()) {
            writeC(0);
            writeS(getName(temp));
            writeC(0);
            break;
          } 
          writeC(1);
          writeS(getName(temp));
          weapon(temp);
          break;
        case 2:
          writeD(temp.getInvID());
          writeH(temp.getItem().getType1());
          writeH(temp.getItem().get_gfxid());
          writeC(temp.getBless());
          writeD((int)temp.getCount());
          if (!temp.isDefinite()) {
            writeC(0);
            writeS(getName(temp));
            writeC(0);
            break;
          } 
          writeC(1);
          writeS(getName(temp));
          armor(temp);
          break;
        case 3:
          etc(temp);
          break;
      } 
      b++;
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
