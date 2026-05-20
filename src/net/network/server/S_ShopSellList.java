package net.network.server;

import java.io.ByteArrayOutputStream;
import net.util.Util;
import net.world.instance.ShopInstance;
import net.world.slimerace.SlimeraceShopInstance;

public class S_ShopSellList extends S_BasePacket {
  public S_ShopSellList(SlimeraceShopInstance npc, ByteArrayOutputStream _bao, int count) {
    writeC(44);
    writeD(npc.getObjectId());
    writeH(count);
    writeB(_bao.toByteArray(), _bao.size());
  }
  
  public S_ShopSellList(ShopInstance npc, ByteArrayOutputStream _bao, int count) {
    writeC(44);
    writeD(npc.getObjectId());
    writeH(count);
    writeB(_bao.toByteArray(), _bao.size());
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
