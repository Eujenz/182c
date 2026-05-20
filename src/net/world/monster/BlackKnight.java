package net.world.monster;

import net.database.bean.Monster;
import net.world.instance.MonsterInstance;
import net.world.instance.PcInstance;
import net.world.object.L1Object;

public class BlackKnight extends MonsterInstance {
  public BlackKnight(Monster m) {
    super(m);
    setClassGfxMode(24);
    setGfxMode(24);
  }
  
  public void toWalk(long time) {
    byte b;
    int i;
    L1Object[] arrayOfL1Object;
    for (i = (arrayOfL1Object = getObjectList()).length, b = 0; b < i; ) {
      L1Object o = arrayOfL1Object[b];
      if (o instanceof PcInstance) {
        PcInstance pc = (PcInstance)o;
        if (pc.getClassType() == 0) {
          setFight(true);
          addAttackList(o);
          return;
        } 
      } 
      b++;
    } 
    super.toWalk(time);
  }
}
