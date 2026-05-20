package net.network.server;

import net.util.Util;
import net.world.instance.ItemInstance;
import net.world.instance.inventory.function.DogCollar;

public class S_InventoryStatus extends S_Inventory {
  private String log_item;
  
  public S_InventoryStatus(ItemInstance items) {
    this.log_item = items.toString();
    this.enLevel = items.getEnLevel();
    this.durability = items.getDurability();
    writeC(111);
    writeD(items.getInvID());
    switch (items.getItem().getType1()) {
      case 1:
        this.TohandSword = items.getItem().isTohand();
        writeS(getName(items));
        writeD((int)items.getCount());
        if (!items.isDefinite()) {
          writeC(0);
        } else {
          weapon(items);
        } 
        return;
      case 2:
        writeS(getName(items));
        writeD((int)items.getCount());
        if (!items.isDefinite()) {
          writeC(0);
        } else {
          armor(items);
        } 
        return;
    } 
    this.Weight = items.getItem().getWeight();
    this.Weight *= (int)items.getCount();
    writeS(getName(items));
    writeD((int)items.getCount());
    if (!items.isDefinite()) {
      writeC(0);
    } else if (items instanceof DogCollar) {
      DogCollar pet = (DogCollar)items;
      writeC(15);
      writeC(25);
      writeH(pet.getPetClassId());
      writeC(26);
      writeH(pet.getPetLevel());
      writeC(31);
      writeH(pet.getPetMxhp());
      writeC(23);
      writeC(pet.getItem().get_material());
      writeH(this.Weight);
    } else {
      writeC(6);
      writeC(23);
      writeC(items.getItem().get_material());
      writeH(this.Weight);
      writeH(0);
    } 
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.log_item);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
