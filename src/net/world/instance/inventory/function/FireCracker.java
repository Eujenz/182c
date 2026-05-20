package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectEffect;
import net.world.instance.ItemInstance;
import net.world.object.Character;
import net.world.object.L1Object;

public class FireCracker extends ItemInstance {
  public FireCracker(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    cha.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)cha, getItem().get_EffectID()), true);
  }
}
