package net.world.instance.inventory.function;

import net.database.PolymorphTable;
import net.database.bean.Item;
import net.database.bean.Poly;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectPoly;
import net.world.instance.ItemInstance;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;
import net.world.time.ItemTimerInstance;

public class ScrollPolymorphTO extends ItemInstance {
  private static final int firstTime = 1200;
  
  public ScrollPolymorphTO(Item i) {
    super(i);
    setTime(1200);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    ItemTimerInstance.getInstance().remove(cha, "$971");
    BuffTimerInstance.getInstance().remove((L1Object)cha, 43);
    ItemTimerInstance.getInstance().remove(cha, "$260");
    ItemTimerInstance.getInstance().remove(cha, this);
    ItemTimerInstance.getInstance().add(cha, this);
    int name = Integer.valueOf(getItem().get_name().substring(4)).intValue();
    Poly p = PolymorphTable.getInstance().getPoly(name);
    PolymorphTable.getInstance().polyEquipped((L1Object)cha, p);
    cha.setGfx(p.getPolyid());
    cha.SendPacket((S_BasePacket)new S_ObjectPoly((L1Object)cha), true);
  }
  
  public void isTimerStop(Character cha) {
    cha.setGfx(cha.getClassGfx());
    cha.SendPacket((S_BasePacket)new S_ObjectPoly((L1Object)cha), true);
  }
  
  public void isSetting() {
    setTime(1200);
  }
  
  public int getFirstTime() {
    return 1200;
  }
}
