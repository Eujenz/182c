package net.world.instance;

import net.database.PolymorphTable;
import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_CharacterStat;
import net.network.server.S_InventoryEquipped;
import net.network.server.S_ServerMessage;
import net.world.object.Character;

public class ItemArmorInstance extends ItemInstance {
  public ItemArmorInstance(Item _item) {
    super(_item);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    if (itemLvCheck(cha) && PolymorphTable.getInstance().polyEquipped(cha, this, null) && ClassCheck(cha) && EquippedCheck(cha))
      if (isEquipped()) {
        if (getBless() == 2) {
          cha.SendPacket((S_BasePacket)new S_ServerMessage(150));
        } else {
          absoluteEquipped(cha, false);
        } 
      } else {
        absoluteEquipped(cha, true);
      }  
  }
  
  public void Equipped(Character cha) {
    switch (getItem().getType()) {
      case 10:
        cha.getInventory().setSlot(13, isEquipped() ? this : null);
        break;
      case 16:
        cha.getInventory().setSlot(4, isEquipped() ? this : null);
        break;
      case 17:
        cha.getInventory().setSlot(5, isEquipped() ? this : null);
        break;
      case 20:
        cha.getInventory().setSlot(9, isEquipped() ? this : null);
        break;
      case 12:
        cha.getInventory().setSlot(0, isEquipped() ? this : null);
        break;
      case 18:
        if (isEquipped()) {
          if (cha.getInventory().getSlot(7) == null) {
            cha.getInventory().setSlot(7, this);
            break;
          } 
          cha.getInventory().setSlot(6, this);
          break;
        } 
        if (cha.getInventory().getSlot(7) != null && cha.getInventory().getSlot(7).getInvID() == getInvID()) {
          cha.getInventory().setSlot(7, null);
          break;
        } 
        if (cha.getInventory().getSlot(6) != null && cha.getInventory().getSlot(6).getInvID() == getInvID()) {
          cha.getInventory().setSlot(6, null);
          break;
        } 
        cha.getInventory().setSlot(7, null);
        cha.getInventory().setSlot(6, null);
        break;
      case 15:
        cha.getInventory().setSlot(3, isEquipped() ? this : null);
        break;
      case 21:
        cha.getInventory().setSlot(10, isEquipped() ? this : null);
        break;
      case 23:
        cha.getInventory().setSlot(12, isEquipped() ? this : null);
        break;
      case 13:
        cha.getInventory().setSlot(1, isEquipped() ? this : null);
        break;
      case 19:
        cha.getInventory().setSlot(8, isEquipped() ? this : null);
        break;
      case 14:
        cha.getInventory().setSlot(2, isEquipped() ? this : null);
        break;
    } 
    if (getBless() == 2 && isEquipped())
      cha.SendPacket((S_BasePacket)new S_ServerMessage(149, getName())); 
    cha.SendPacket((S_BasePacket)new S_InventoryEquipped(this));
  }
  
  public void isEnchant(Character cha, boolean en, short rnd) {
    if (en) {
      if (isEquipped() && ((rnd < 0) ? (getTotalAc() >= 0) : (getTotalAc() > 0))) {
        cha.setAc(cha.getAc() + rnd);
        cha.SendPacket((S_BasePacket)new S_CharacterStat(cha));
      } 
    } else {
      if (isEquipped()) {
        setEquipped(false);
        Equipped(cha);
        cha.setAc(cha.getAc() - getTotalAc());
        itemOption(cha);
      } 
      cha.getInventory().remove(this);
    } 
  }
  
  private int getTotalAc() {
    int ac = getItem().get_ac();
    if (isBuffBlessedArmor())
      ac += 3; 
    if (getEnLevel() >= 0)
      return ac + getEnLevel(); 
    ac += getEnLevel();
    if (ac < 0)
      return 0; 
    return ac;
  }
  
  public void absoluteEquipped(Character cha, boolean equipped) {
    if (equipped) {
      setEquipped(true);
      Equipped(cha);
      cha.setAc(cha.getAc() + getTotalAc());
      itemOption(cha);
    } else {
      setEquipped(false);
      Equipped(cha);
      cha.setAc(cha.getAc() - getTotalAc());
      itemOption(cha);
    } 
  }
}
