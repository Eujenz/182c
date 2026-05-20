package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectEffect;
import net.world.instance.ItemInstance;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.ItemTimerInstance;

public class BlessEva extends ItemInstance {
  private static final int firstTime = 300;
  
  public static final String name = "$1507";
  
  public BlessEva(Item i) {
    super(i);
    setTime(300);
    setName("$1507");
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    cha.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)cha, getItem().get_EffectID()), true);
    ItemTimerInstance.getInstance().remove(cha, "$1507");
    ItemTimerInstance.getInstance().add(cha, this);
  }
  
  public void isTimerRun(Character cha) {
    getInvID();
  }
  
  public void isSetting() {
    setTime(300);
  }
  
  public int getFirstTime() {
    return 300;
  }
}
