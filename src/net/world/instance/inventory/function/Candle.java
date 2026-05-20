package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_InventoryEquipped;
import net.network.server.S_ObjectLight;
import net.world.instance.ItemInstance;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.ItemTimerInstance;

public class Candle extends ItemInstance {
  public static final int light = 8;
  
  public static final int firstTime = 1800;
  
  public Candle(Item i) {
    super(i);
    setTime(1800);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    if (isEquipped()) {
      ItemTimerInstance.getInstance().remove(cha, this);
    } else {
      ItemTimerInstance.getInstance().add(cha, this);
    } 
  }
  
  public void isTimerRun(Character cha) {
    setEquipped(true);
    if (cha != null) {
      cha.SendPacket((S_BasePacket)new S_InventoryEquipped(this));
      if (cha.getLight() <= 8) {
        cha.setLight(8);
        cha.SendPacket((S_BasePacket)new S_ObjectLight((L1Object)cha), true);
      } 
    } 
  }
  
  public void isTimerStop(Character cha) {
    setEquipped(false);
    if (cha != null) {
      cha.SendPacket((S_BasePacket)new S_InventoryEquipped(this));
      if (cha.getLight() <= 8 && !ItemTimerInstance.getInstance().contains(cha, getName())) {
        cha.setLight(0);
        cha.SendPacket((S_BasePacket)new S_ObjectLight((L1Object)cha), true);
      } 
    } 
  }
  
  public void isTimerEnd(Character cha) {
    if (cha != null) {
      cha.getInventory().remove(this);
    } else {
      toDelete();
    } 
  }
  
  public void isSetting() {
    setEquipped(false);
  }
  
  public void toPickup(Character cha) {
    if (isEquipped()) {
      ItemTimerInstance.getInstance().removeItem(null, this);
      ItemTimerInstance.getInstance().add(cha, this);
    } 
    setLight(0);
  }
  
  public void toDrop(Character cha) {
    if (isEquipped()) {
      setLight(8);
      ItemTimerInstance.getInstance().removeItem(cha, this);
      ItemTimerInstance.getInstance().add(null, this);
      if (cha.getLight() <= 8 && !ItemTimerInstance.getInstance().contains(cha, getName())) {
        cha.setLight(0);
        cha.SendPacket((S_BasePacket)new S_ObjectLight((L1Object)cha), true);
      } 
    } 
  }
  
  public int getFirstTime() {
    return 1800;
  }
}
