package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.server.S_BasePacket;
import net.network.server.S_CharacterStat;
import net.world.instance.ItemArmorInstance;
import net.world.instance.inventory.PcInventory;
import net.world.object.Character;

public final class TrollBelt extends ItemArmorInstance {
  public TrollBelt(Item _item) {
    super(_item);
  }
  
  public void itemOption(Character cha) {
    super.itemOption(cha);
    PcInventory inv = (PcInventory)cha.getInventory();
    double weight = inv.getMaxWeight() * 0.1D;
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
