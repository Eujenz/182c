package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectEffect;
import net.world.instance.ItemInstance;
import net.world.object.Character;

public class DogWhistle extends ItemInstance {
  public DogWhistle(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    cha.SendPacket((S_BasePacket)new S_ObjectEffect(getItem().get_EffectID()), true);
  }
}
