package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.server.S_BasePacket;
import net.network.server.S_CharacterStat;
import net.world.instance.ItemArmorInstance;
import net.world.instance.inventory.PcInventory;
import net.world.object.Character;

public final class OgreBelt extends ItemArmorInstance {
  public OgreBelt(Item _item) {
    super(_item);
  }
  
  public void itemOption(Character cha) {
    super.itemOption(cha);
    PcInventory inv = (PcInventory)cha.getInventory();
    double weight = inv.getMaxWeight() * 0.2D;
    double value = weight + inv.getMaxWeightForAdd();
    double value2 = weight - inv.getMaxWeightForAdd();
    if (isEquipped()) {
      inv.addMaxWeight(value);
    } else {
      inv.addMaxWeight(value2);
    } 
    cha.SendPacket((S_BasePacket)new S_CharacterStat(cha));
  }
}
