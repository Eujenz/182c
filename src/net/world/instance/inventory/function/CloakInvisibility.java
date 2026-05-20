package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectInvis;
import net.world.instance.ItemArmorInstance;
import net.world.object.Character;

public class CloakInvisibility extends ItemArmorInstance {
  public CloakInvisibility(Item _item) {
    super(_item);
  }
  
  public void itemOption(Character cha) {
    super.itemOption(cha);
    cha.setInvis(isEquipped());
    cha.SendPacket((S_BasePacket)new S_ObjectInvis(cha.getObjectId(), cha.isInvis()), true);
  }
}
