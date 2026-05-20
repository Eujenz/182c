package net.world.instance;

import net.database.PolymorphTable;
import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_InventoryEquipped;
import net.network.server.S_ObjectMode;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.object.Character;
import net.world.object.L1Object;

public class ItemWeaponInstance extends ItemInstance {
  public ItemWeaponInstance(Item _item) {
    super(_item);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    if (getItem().getType() == 1) {
      super.clickItem(cha, bp);
    } else if (itemLvCheck(cha)) {
      if (bp != null && !PolymorphTable.getInstance().polyEquipped(cha, this, null))
        return; 
      if (ClassCheck(cha)) {
        if (isEquipped()) {
          if (getBless() == 2) {
            cha.SendPacket((S_BasePacket)new S_ServerMessage(150));
            return;
          } 
          absoluteEquipped(cha, false);
        } else {
          if (getItem().isTohand() && 
            cha.getInventory().getSlot(10) != null) {
            cha.SendPacket((S_BasePacket)new S_ServerMessage(128));
            return;
          } 
          if (cha.getInventory().getSlot(11) != null) {
            cha.SendPacket((S_BasePacket)new S_ServerMessage(124));
            return;
          } 
          absoluteEquipped(cha, true);
        } 
      } else {
        cha.SendPacket((S_BasePacket)new S_ServerMessage(264));
      } 
    } 
  }
  
  public void Equipped(Character cha) {
    if (isEquipped()) {
      cha.getInventory().setSlot(11, this);
      cha.setGfxMode(getItem().getGfxmode());
      if (getBless() == 2)
        cha.SendPacket((S_BasePacket)new S_ServerMessage(149, getName())); 
    } else {
      cha.getInventory().setSlot(11, null);
      cha.setGfxMode(0);
    } 
    cha.SendPacket((S_BasePacket)new S_InventoryEquipped(this));
    cha.SendPacket((S_BasePacket)new S_ObjectMode((L1Object)cha), true);
  }
  
  public void isEnchant(Character cha, boolean en, short rnd) {
    if (en) {
      isEquipped();
    } else {
      if (isEquipped()) {
        setEquipped(false);
        Equipped(cha);
        itemOption(cha);
      } 
      cha.getInventory().remove(this);
    } 
  }
  
  public int toAttackEffect(L1Object cha, L1Object target) {
    int dmg = 0;
    if (getItem().get_nameidN() == 416 && 
      Util.rand(0, 99) < 5) {
      setEquipped(false);
      Equipped((Character)cha);
      itemOption((Character)cha);
      ((Character)cha).getInventory().remove(this);
      dmg = (int)(dmg + target.getCurrentHp() * 0.65D);
    } 
    return dmg;
  }
  
  public void absoluteEquipped(Character cha, boolean equipped) {
    if (equipped) {
      setEquipped(true);
      Equipped(cha);
      itemOption(cha);
    } else {
      setEquipped(false);
      Equipped(cha);
      itemOption(cha);
    } 
  }
}
