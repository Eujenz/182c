package net.network.server;

import java.util.List;
import net.util.Util;
import net.world.instance.ItemInstance;

public class S_SmithList extends S_BasePacket {
  public S_SmithList(List<ItemInstance> _list) {
    writeC(73);
    writeC(100);
    writeC(0);
    writeC(0);
    writeC(0);
    writeH(_list.size());
    for (ItemInstance item : _list) {
      writeD(item.getInvID());
      writeC(item.getDurability());
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
