package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAbility;
import net.world.instance.ItemArmorInstance;
import net.world.object.Character;

public class RingTeleportControl extends ItemArmorInstance {
  public RingTeleportControl(Item _item) {
    super(_item);
  }
  
  public void itemOption(Character cha) {
    super.itemOption(cha);
    cha.SendPacket((S_BasePacket)new S_ObjectAbility(1, isEquipped()));
  }
}
