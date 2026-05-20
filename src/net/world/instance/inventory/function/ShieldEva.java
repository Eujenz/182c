package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.server.S_BasePacket;
import net.network.server.S_BuffSpeed;
import net.world.instance.ItemArmorInstance;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;

public class ShieldEva extends ItemArmorInstance {
  public ShieldEva(Item i) {
    super(i);
  }
  
  public void itemOption(Character cha) {
    super.itemOption(cha);
    if (isEquipped()) {
      if (cha.isSlow()) {
        BuffTimerInstance.getInstance().remove((L1Object)cha, 20);
      } else {
        cha.setSpeed(true);
        cha.SendPacket((S_BasePacket)new S_BuffSpeed((L1Object)cha, 0, 1, -1), true);
      } 
    } else {
      cha.setSpeed(false);
      cha.SendPacket((S_BasePacket)new S_BuffSpeed((L1Object)cha, 0, 0, 0), true);
    } 
  }
}
