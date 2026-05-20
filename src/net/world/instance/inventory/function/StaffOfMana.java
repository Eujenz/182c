package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.util.Util;
import net.world.instance.ItemWeaponInstance;
import net.world.object.L1Object;
import net.world.pc.L1PinkName;

public class StaffOfMana extends ItemWeaponInstance {
  public StaffOfMana(Item i) {
    super(i);
  }
  
  public int toAttackEffect(L1Object cha, L1Object target) {
    if (target.getCurrentMp() > 0) {
      int steal_mp = Util.rand(1, 3);
      steal_mp += getEnLevel();
      if (getEnLevel() > 6)
        steal_mp += getEnLevel() - 6; 
      if (target.getCurrentMp() < steal_mp)
        steal_mp = target.getCurrentMp(); 
      target.setCurrentMp(target.getCurrentMp() - steal_mp);
      L1PinkName.execute(cha, target);
      cha.setCurrentMp(cha.getCurrentMp() + steal_mp);
    } 
    return 0;
  }
}
