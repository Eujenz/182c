package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.object.Character;

public class EnchantArmor extends Enchant {
  public EnchantArmor(Item _item) {
    super(_item);
  }
  
  public synchronized void clickItem(Character cha, C_BasePacket bp) {
    ItemInstance armor = cha.getInventory().getItemInvId(bp.readD());
    if (armor != null && armor.getBless() >= 0 && armor.getBless() < 128 && armor instanceof net.world.instance.ItemArmorInstance && armor.getItem().isEnchant() && 
      cha instanceof PcInstance) {
      setCount(cha, getCount() - 1L);
      armor.isEnchant(cha, enchant((PcInstance)cha, armor), this.rnd);
    } else {
      cha.SendPacket((S_BasePacket)new S_ServerMessage(79));
    } 
  }
}
