package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.world.instance.ItemInstance;
import net.world.object.Character;
import net.world.time.ItemTimerInstance;

public class ItemExpDouble extends ItemInstance {
  private static final int firstTime = 300;
  
  public ItemExpDouble(Item i) {
    super(i);
    setTime(300);
    setName(getName());
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    if (cha.isExpDouble()) {
      cha.Message("請勿重複使用經驗藥水。");
      return;
    } 
    setCount(cha, getCount() - 1L);
    ItemTimerInstance.getInstance().remove(cha, this);
    ItemTimerInstance.getInstance().add(cha, this);
  }
  
  public void isTimerRun(Character cha) {
    String name = getItem().get_name().replaceAll("%", "");
    double rate = Double.valueOf(name.substring(4)).doubleValue() / 100.0D;
    cha.setExpDouble(true);
    cha.set_exprate(rate);
    cha.Message("使用了 " + getItem().get_name() + " 狩獵經驗" + rate + "倍。");
  }
  
  public void isTimerStop(Character cha) {
    cha.setExpDouble(false);
    cha.set_exprate(0.0D);
    cha.Message(getItem().get_name() + " 效果結束 狩獵經驗恢復正常。");
  }
  
  public void isSetting() {
    setTime(300);
  }
  
  public int getFirstTime() {
    return 300;
  }
}
