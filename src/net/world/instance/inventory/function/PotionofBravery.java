package net.world.instance.inventory.function;

import net.Config;
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

public class PotionofBravery extends ItemInstance {
  private static final int firstTime = 300;
  
  public static final String name = "$943";
  
  public PotionofBravery(Item i) {
    super(i);
    setTime(300);
    setName("$943");
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    boolean check = false;
    if ((cha.getClassType() == 2 && getItem().get_elf() == 1) || (cha.getClassType() == 1 && getItem().get_knight() == 1) || (cha.getClassType() == 0 && getItem().get_royal() == 1) || (cha.getClassType() == 3 && getItem().get_mage() == 1))
      check = true; 
    if (check) {
      cha.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)cha, getItem().get_EffectID()), true);
      if (cha.isSlow()) {
        BuffTimerInstance.getInstance().remove((L1Object)cha, 20);
      } else {
        ItemTimerInstance.getInstance().remove(cha, "$943");
        ItemTimerInstance.getInstance().add(cha, this);
      } 
    } else {
      cha.SendPacket((S_BasePacket)new S_ServerMessage(79));
    } 
  }
  
  public void isTimerRun(Character cha) {
    cha.setStatus(cha.getStatus() + Config.STATUS);
    cha.setBrave(true);
    if (getInvID() == 0) {
      cha.SendPacket((S_BasePacket)new S_BuffSpeed((L1Object)cha, 1, 1, getTime()), true);
    } else {
      cha.SendPacket((S_BasePacket)new S_BuffSpeed((L1Object)cha, 1, 1, 300), true);
    } 
  }
  
  public void isTimerStop(Character cha) {
    cha.setStatus(cha.getStatus() - Config.STATUS);
    cha.setBrave(false);
    cha.SendPacket((S_BasePacket)new S_BuffSpeed((L1Object)cha, 1, 0, 0), true);
  }
  
  public void isSetting() {
    setTime(300);
  }
  
  public int getFirstTime() {
    return 300;
  }
}
