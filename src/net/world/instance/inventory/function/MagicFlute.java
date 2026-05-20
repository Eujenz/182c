package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ServerMessage;
import net.world.instance.ItemInstance;
import net.world.instance.MonsterInstance;
import net.world.npc.elf.Pan;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.ItemTimerInstance;

public class MagicFlute extends ItemInstance {
  private boolean status = false;
  
  private static final int firstTime = 4;
  
  public MagicFlute(Item i) {
    super(i);
    setTime(4);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    if (!this.status) {
      this.status = true;
      ItemTimerInstance.getInstance().remove(cha, getName());
      ItemTimerInstance.getInstance().add(cha, this);
    } else {
      cha.SendPacket((S_BasePacket)new S_ServerMessage(343));
    } 
  }
  
  public void isTimerRun(Character cha) {
    cha.SendPacket((S_BasePacket)new S_ObjectEffect(getItem().get_EffectID()), true);
    byte b;
    int i;
    L1Object[] arrayOfL1Object;
    for (i = (arrayOfL1Object = cha.getObjectList()).length, b = 0; b < i; ) {
      L1Object o = arrayOfL1Object[b];
      if (o instanceof Pan) {
        Pan p = (Pan)o;
        p.setMagicflute(cha);
        break;
      } 
      if (o instanceof MonsterInstance && !(o instanceof net.world.instance.SummonInstance)) {
        MonsterInstance mon = (MonsterInstance)o;
        mon.setFight(true);
        mon.addAttackList((L1Object)cha);
      } 
      b++;
    } 
  }
  
  public void isTimerStop(Character cha) {
    this.status = false;
  }
  
  public void isSetting() {
    setTime(4);
  }
  
  public int getFirstTime() {
    return 4;
  }
}
