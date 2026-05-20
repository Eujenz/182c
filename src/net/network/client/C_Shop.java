package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.instance.PcInstance;
import net.world.object.L1Object;

public class C_Shop extends C_BasePacket {
  private int obj_id;
  
  private int type;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.obj_id = readD();
    this.type = readC();
    if (!pc.isDead()) {
      L1Object o = pc.getObject(this.obj_id);
      if (o != null)
        switch (this.type) {
          case 0:
            o.ShopBuy(this, pc);
            break;
          case 1:
            o.ShopSell(this, pc);
            break;
          case 2:
            o.WareHousePut(this, pc);
            break;
          case 3:
            o.WareHouseGet(this, pc);
            break;
          case 4:
            o.ClanWareHousePut(this, pc);
            break;
          case 5:
            o.ClanWareHouseGet(this, pc);
            break;
          case 8:
            o.ElfWareHousePut(this, pc);
            break;
          case 9:
            o.ElfWareHouseGet(this, pc);
            break;
          case 12:
            o.PetGet(this, pc);
            break;
        }  
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
      sb.append(this.obj_id);
      sb.append(" , ");
      sb.append(this.type);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
