package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_InventoryStatus;
import net.network.server.S_ServerMessage;
import net.world.instance.ItemInstance;
import net.world.object.Character;

public class Whetstone extends ItemInstance {
  public Whetstone(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    ItemInstance weapon = cha.getInventory().getItemInvId(bp.readD());
    if (weapon != null && weapon instanceof net.world.instance.ItemWeaponInstance) {
      weapon.setDurability(weapon.getDurability() - 1);
      cha.SendPacket((S_BasePacket)new S_InventoryStatus(weapon));
      if (weapon.getDurability() != 0) {
        cha.SendPacket((S_BasePacket)new S_ServerMessage(463, weapon.toString()));
      } else {
        cha.SendPacket((S_BasePacket)new S_ServerMessage(464, weapon.toString()));
      } 
    } else {
      cha.SendPacket((S_BasePacket)new S_ServerMessage(79));
    } 
  }
}
