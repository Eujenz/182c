package net.world.monster;

import net.database.bean.Monster;
import net.util.Util;
import net.world.instance.MonsterInstance;
import net.world.object.L1Object;

public class Gremlin extends MonsterInstance {
  private static final int[] item_list = new int[] { -1 };
  
  public Gremlin(Monster m) {
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
            setEscape(true);
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
    super.toWalk(time);
    if (isFight() || 
      SearchItem(item_list));
  }
  
  public void toEscape(long time) {
    if (this.attackList.size() > 0) {
      L1Object o = this.attackList.get(0);
      if (containsObject(o) && !o.isDead() && !o.isDelete()) {
        oppositionHeading(o);
        int x = getX();
        int y = getY();
        switch (getHeading()) {
          case 0:
            y -= 3;
            break;
          case 1:
            x += 3;
            y -= 3;
            break;
          case 2:
            x += 3;
            break;
          case 3:
            x += 3;
            y += 3;
            break;
          case 4:
            y += 3;
            break;
          case 5:
            x -= 3;
            y += 3;
            break;
          case 6:
            x -= 3;
            break;
          case 7:
            x -= 3;
            y -= 3;
            break;
        } 
        StartMove(x, y);
        this.ai_start_time = time;
        this.ai_time = getMon().getModespeed(getGfxMode());
      } else {
        this.attackList.remove(o);
        setEscape(false);
      } 
    } else {
      setEscape(false);
    } 
  }
}
