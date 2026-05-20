package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_BuffBlind;
import net.world.instance.ItemInstance;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;
import net.world.time.ItemTimerInstance;

public class BlindingPotion extends ItemInstance {
  private static final int firstTime = 600;
  
  public static final String name = "$239";
  
  public BlindingPotion(Item i) {
    super(i);
    setTime(600);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    BuffTimerInstance.getInstance().remove((L1Object)cha, 14);
    ItemTimerInstance.getInstance().remove(cha, "$239");
    ItemTimerInstance.getInstance().add(cha, this);
  }
  
  public void isTimerRun(Character cha) {
    if (ItemTimerInstance.getInstance().contains(cha, "$6 $23 ")) {
      cha.SendPacket((S_BasePacket)new S_BuffBlind(2));
    } else {
      cha.SendPacket((S_BasePacket)new S_BuffBlind(1));
    } 
  }
  
  public void isTimerEnd(Character cha) {
    cha.SendPacket((S_BasePacket)new S_BuffBlind(0));
  }
  
  public void isSetting() {
    setTime(600);
  }
  
  public int getFirstTime() {
    return 600;
  }
}
