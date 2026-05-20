package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.util.Util;
import net.world.instance.ItemInstance;
import net.world.object.Character;

public class ShuromItem_02 extends ItemInstance {
  private int time_count = 0;
  
  public ShuromItem_02(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    cha.setLawful(cha.getLawful() + 10000);
  }
  
  public void isShuromItemOption() {
    if (++this.time_count > 9) {
      this.time_count = 0;
      if (getCha() != null && getCha().getLevel() > 9)
        if (getCha().getLevel() < 20) {
          getCha().addExp(Util.rand(500, 1000));
        } else if (getCha().getLevel() < 30) {
          getCha().addExp(Util.rand(1000, 2000));
        } else if (getCha().getLevel() < 40) {
          getCha().addExp(Util.rand(2000, 4000));
        } else {
          getCha().addExp(Util.rand(4000, 8000));
        }  
    } 
  }
}
