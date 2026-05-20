package net.world.instance.inventory.function;

import net.database.ItemsTable;
import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.instance.ItemInstance;
import net.world.object.Character;

public class ShuromItem_04 extends ItemInstance {
  private static final int[][] itemdb_list = new int[][] { 
      { 109, 1, 1 }, { 110, 1, 1 }, { 253, 1, 20 }, { 56, 1, 20 }, { 100, 1, 10 }, { 99, 1, 20 }, { 254, 1, 10 }, { 206, 1, 20 }, { 285, 1, 20 }, { 332, 1, 5 }, 
      { 333, 1, 5 }, { 334, 1, 5 }, { 335, 1, 5 }, { 336, 1, 1 }, { 337, 1, 1 }, { 338, 1, 1 }, { 339, 1, 1 }, { 140, 1, 20 } };
  
  public ShuromItem_04(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    int itemdb_count = itemdb_list.length - 1;
    int idx = Util.rand(0, itemdb_count);
    int id = itemdb_list[idx][0];
    int count = Util.rand(itemdb_list[idx][1], itemdb_list[idx][2]);
    int bless = 1;
    ItemInstance item = null;
    switch (id) {
      case 345:
        if (cha.getInventory().getItemDbId(id) == null) {
          item = ItemsTable.getInstance().newItem(id, false, true);
          cha.SendPacket((S_BasePacket)new S_ServerMessage(143, getName(), item.toString()));
          cha.getInventory().insert(item, count);
        } 
        return;
    } 
    item = ItemsTable.getInstance().newItem(id, false, true);
    if (id == 99)
      bless = 0; 
    if (id == 109 || id == 110)
      bless = Util.rand(0, 2); 
    item.setCount(count);
    item.setBless(bless);
    cha.SendPacket((S_BasePacket)new S_ServerMessage(143, getName(), item.toString()));
    cha.getInventory().insert(item, count);
  }
}
