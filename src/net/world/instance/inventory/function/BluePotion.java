package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectEffect;
import net.world.instance.ItemInstance;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.ItemTimerInstance;

public class BluePotion extends ItemInstance {
  private static final int firstTime = 600;
  
  private static final int TIC_MP = 10;
  
  public static final String name = "$232";
  
  public BluePotion(Item i) {
    super(i);
    setTime(600);
    setName("$232");
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    cha.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)cha, getItem().get_EffectID()), true);
    ItemTimerInstance.getInstance().remove(cha, "$232");
    ItemTimerInstance.getInstance().add(cha, this);
  }
  
  public void isTimerRun(Character cha) {
    cha.setDynamicTicMp(cha.getDynamicTicMp() + 10);
  }
  
  public void isTimerStop(Character cha) {
    cha.setDynamicTicMp(cha.getDynamicTicMp() - 10);
  }
  
  public void isSetting() {
    setTime(600);
  }
  
  public int getFirstTime() {
    return 600;
  }
}
