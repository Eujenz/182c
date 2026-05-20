package net.world.monster;

import net.database.bean.Monster;
import net.util.Util;
import net.world.instance.MonsterInstance;
import net.world.object.L1Object;

public class Bugbear extends MonsterInstance {
  public Bugbear(Monster m) {
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
          } else {
            this.ai_start_time = time;
            if (getDistance(o.getX(), o.getY(), o.getMap(), this.Areaatk)) {
              switch (Util.rand(0, 10)) {
                case 0:
                case 1:
                case 2:
                  Attack(o, o.getX(), o.getY(), getGfxMode() + 17, 0);
                  o.setCurrentHp(o.getCurrentHp() - Util.rand(0, 30));
                  this.ai_time = getMon().getModespeed(getGfxMode() + 17);
                  break;
                case 3:
                case 4:
                case 5:
                case 6:
                  Attack(o, o.getX(), o.getY(), getGfxMode() + 30, 0);
                  o.setCurrentHp(o.getCurrentHp() - Util.rand(0, 10));
                  this.ai_time = getMon().getModespeed(getGfxMode() + 30);
                  break;
                default:
                  Attack(o, o.getX(), o.getY(), getGfxMode() + 1, 0);
                  this.ai_time = getMon().getModespeed(getGfxMode() + 1);
                  break;
              } 
            } else {
              StartMove(o.getX(), o.getY());
              this.ai_time = getMon().getModespeed(getGfxMode());
            } 
          } 
        } else {
          this.attackList.remove(o);
        } 
      } else {
        clearFightList();
      } 
    } 
  }
}
