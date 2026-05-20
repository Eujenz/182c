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
import net.world.time.BuffTimerInstance;
import net.world.time.ItemTimerInstance;

public class HastePotion extends ItemInstance {
  protected int firstTime = 300;
  
  public static final String name = "$234";
  
  public HastePotion(Item i) {
    super(i);
    setTime(this.firstTime);
    setName("$234");
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    if (cha instanceof net.world.instance.PcInstance)
      setCount(cha, getCount() - 1L); 
    cha.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)cha, getItem().get_EffectID()), true);
    if (cha.isSlow()) {
      BuffTimerInstance.getInstance().remove((L1Object)cha, 20);
    } else {
      if (cha.isSpeed()) {
        cha.SendPacket((S_BasePacket)new S_ServerMessage(183));
      } else {
        cha.SendPacket((S_BasePacket)new S_ServerMessage(184));
      } 
      BuffTimerInstance.getInstance().remove((L1Object)cha, 28);
      ItemTimerInstance.getInstance().remove(cha, "$234");
      ItemTimerInstance.getInstance().remove(cha, "$1652 $234");
      ItemTimerInstance.getInstance().add(cha, this);
    } 
  }
  
  public void isTimerRun(Character cha) {
    cha.setSpeed(true);
    if (getInvID() == 0) {
      cha.SendPacket((S_BasePacket)new S_BuffSpeed((L1Object)cha, 0, 1, getTime()), true);
    } else {
      cha.SendPacket((S_BasePacket)new S_BuffSpeed((L1Object)cha, 0, 1, this.firstTime), true);
    } 
  }
  
  public void isTimerStop(Character cha) {
    cha.setSpeed(false);
    cha.SendPacket((S_BasePacket)new S_BuffSpeed((L1Object)cha, 0, 0, 0), true);
  }
  
  public void isSetting() {
    setTime(this.firstTime);
  }
  
  public int getFirstTime() {
    return this.firstTime;
  }
}
