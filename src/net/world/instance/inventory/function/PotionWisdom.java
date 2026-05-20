package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_BuffSpeed;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ServerMessage;
import net.world.instance.ItemInstance;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.ItemTimerInstance;

public class PotionWisdom extends ItemInstance {
  private static final int firstTime = 300;
  
  public static final String name = "$944";
  
  public PotionWisdom(Item i) {
    super(i);
    setTime(300);
    setName("$944");
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    if (cha.getClassType() == 3) {
      cha.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)cha, getItem().get_EffectID()), true);
      ItemTimerInstance.getInstance().remove(cha, "$944");
      ItemTimerInstance.getInstance().add(cha, this);
    } else {
      cha.SendPacket((S_BasePacket)new S_ServerMessage(79));
    } 
  }
  
  public void isTimerRun(Character cha) {
    cha.setDynamicSp(cha.getDynamicSp() + 2);
    if (getInvID() == 0) {
      cha.SendPacket((S_BasePacket)new S_BuffSpeed((L1Object)cha, 1, 2, getTime()), true);
    } else {
      cha.SendPacket((S_BasePacket)new S_BuffSpeed((L1Object)cha, 1, 2, 300));
    } 
  }
  
  public void isTimerStop(Character cha) {
    cha.setDynamicSp(cha.getDynamicSp() - 2);
    cha.SendPacket((S_BasePacket)new S_BuffSpeed((L1Object)cha, 1, 0, 0));
  }
  
  public void isSetting() {
    setTime(300);
  }
  
  public int getFirstTime() {
    return 300;
  }
}
