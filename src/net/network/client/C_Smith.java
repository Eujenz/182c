package net.network.client;

import java.util.ArrayList;
import java.util.List;
import net.LineageClient;
import net.network.server.S_BasePacket;
import net.network.server.S_SmithList;
import net.util.Util;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;

public class C_Smith extends C_BasePacket {
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    List<ItemInstance> _list = new ArrayList<ItemInstance>();
    byte b;
    int i;
    ItemInstance[] arrayOfItemInstance;
    for (i = (arrayOfItemInstance = pc.getInventory().getAll()).length, b = 0; b < i; ) {
      ItemInstance item = arrayOfItemInstance[b];
      if (item instanceof net.world.instance.ItemWeaponInstance && item.getDurability() > 0)
        _list.add(item); 
      b++;
    } 
    lc.SendPacket((S_BasePacket)new S_SmithList(_list));
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
