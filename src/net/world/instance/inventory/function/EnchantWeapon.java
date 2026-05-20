package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.object.Character;

public class EnchantWeapon extends Enchant {
  public EnchantWeapon(Item _item) {
    super(_item);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    ItemInstance weapon = cha.getInventory().getItemInvId(bp.readD());
    if (weapon != null && weapon.getBless() >= 0 && weapon.getBless() < 128 && weapon.getItem().isEnchant() && weapon instanceof net.world.instance.ItemWeaponInstance) {
      setCount(cha, getCount() - 1L);
      weapon.isEnchant(cha, enchant((PcInstance)cha, weapon), this.rnd);
    } else {
      cha.SendPacket((S_BasePacket)new S_ServerMessage(79));
    } 
  }
}
