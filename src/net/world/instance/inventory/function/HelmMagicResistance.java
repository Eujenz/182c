package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.server.S_BasePacket;
import net.network.server.S_InventoryStatus;
import net.network.server.S_ObjectSpMr;
import net.world.instance.ItemArmorInstance;
import net.world.instance.ItemInstance;
import net.world.object.Character;

public class HelmMagicResistance extends ItemArmorInstance {
  public HelmMagicResistance(Item _item) {
    super(_item);
  }
  
  public void isEnchant(Character cha, boolean en, short rnd) {
    super.isEnchant(cha, en, rnd);
    if (en) {
      if (isEquipped()) {
        cha.setDynamicMr(cha.getDynamicMr() - getDynamicMr() + getEnLevel());
        cha.SendPacket((S_BasePacket)new S_ObjectSpMr(cha.getSp(true), cha.getDynamicMr()));
      } 
      setDynamicMr(getEnLevel());
      cha.SendPacket((S_BasePacket)new S_InventoryStatus((ItemInstance)this));
    } 
  }
  
  public void isSetting() {
    setDynamicMr(getEnLevel());
  }
}
