package net.world.instance.inventory.function;

import java.util.Random;
import net.database.ItemsTable;
import net.database.RandomGift;
import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.world.instance.ItemInstance;
import net.world.object.Character;

public class RandomBox extends ItemInstance {
  public RandomBox(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    int id = getItem().getItemId();
    String[] randomlist = null;
    String[] itemidlist = null;
    String[] countlist = null;
    for (int i = 0; i < RandomGift.boxidlist.size(); i++) {
      if (((String)RandomGift.boxidlist.get(i)).equalsIgnoreCase("" + id)) {
        randomlist = ((String)RandomGift.randomlist.get(i)).split(",");
        itemidlist = ((String)RandomGift.itemidlist.get(i)).split(",");
        countlist = ((String)RandomGift.countlist.get(i)).split(",");
        break;
      } 
    } 
    Random rnd = new Random();
    boolean hasgift = false;
    do {
      for (int j = 0; j < randomlist.length; j++) {
        if (rnd.nextInt(100) + 1 <= Integer.valueOf(randomlist[j]).intValue()) {
          ItemInstance item = ItemsTable.getInstance().newItem(Integer.valueOf(itemidlist[j]).intValue(), false, true);
          int realcount = rnd.nextInt(Integer.valueOf(countlist[j]).intValue()) + 1;
          item.setCount(realcount);
          cha.SendPacket((S_BasePacket)new S_ServerMessage(143, getName(), item.toString()));
          cha.getInventory().insert(item, realcount);
          hasgift = true;
          break;
        } 
      } 
    } while (!hasgift);
  }
}
