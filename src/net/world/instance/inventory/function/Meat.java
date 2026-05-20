package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.world.instance.ItemInstance;
import net.world.object.Character;

public class Meat extends ItemInstance {
  public Meat(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    cha.SendPacket((S_BasePacket)new S_ServerMessage(76, toString()));
    setCount(cha, getCount() - 1L);
    if (cha.getFood() == 7 || cha.getFood() == 12 || cha.getFood() == 16 || cha.getFood() == 21 || cha.getFood() == 25) {
      cha.setFood(cha.getFood() + 2);
    } else {
      cha.setFood(cha.getFood() + 1);
    } 
  }
}
