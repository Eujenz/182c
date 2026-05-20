package net.world.monster;

import net.database.bean.Monster;
import net.util.Util;
import net.world.instance.MonsterInstance;
import net.world.object.L1Object;

public class BombFlower extends MonsterInstance {
  private static final int attackEffect = 1991;
  
  public BombFlower(Monster m) {
    super(m);
  }
  
  public void toFight(long time) {
    synchronized (this.attackList) {
      if (this.attackList.size() > 0) {
        L1Object o = this.attackList.get(0);
        if (Util.rand(0, 100) <= 10 && this.attackList.size() > 1) {
          int idx = Util.rand(1, this.attackList.size() - 1);
          o = this.attackList.set(idx, this.attackList.get(0));
          this.attackList.set(0, o);
        } 
        if (o != null) {
          if (o.isDelete() || o.isDead() || !getDistance(o.getX(), o.getY(), o.getMap(), 17)) {
            this.attackList.remove(o);
          } else if (getDistance(o.getX(), o.getY(), o.getMap(), this.Areaatk)) {
            AttackBow(o, o.getX(), o.getY(), 18, 1991, true);
            this.ai_start_time = time;
            this.ai_time = getMon().getModespeed(18);
          } 
        } else {
          this.attackList.remove(o);
        } 
      } else {
        clearFightList();
      } 
    } 
  }
  
  public void toWalk(long time) {
    if (getMon().isAttack() && FightStart())
      return; 
    this.ai_start_time = time;
    this.ai_time = getMon().getModespeed(getGfxMode());
  }
}
