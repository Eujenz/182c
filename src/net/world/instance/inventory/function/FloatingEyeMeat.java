package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.world.object.Character;
import net.world.time.ItemTimerInstance;

public class FloatingEyeMeat extends Meat {
  private static final int firstTime = 3600;
  
  public FloatingEyeMeat(Item i) {
    super(i);
    setTime(3600);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    super.clickItem(cha, bp);
    ItemTimerInstance.getInstance().remove(cha, getName());
    ItemTimerInstance.getInstance().add(cha, this);
  }
  
  public void isTimerRun(Character cha) {
    cha.SendPacket((S_BasePacket)new S_ServerMessage(152));
  }
  
  public void isSetting() {
    setTime(3600);
  }
  
  public int getFirstTime() {
    return 3600;
  }
}
