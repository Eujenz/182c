package net.world.monster;

import net.database.SkillTable;
import net.database.bean.Monster;
import net.util.Util;
import net.world.instance.MonsterInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;

public class Ghoul extends MonsterInstance {
  private static final int SKILLID_1 = 21;
  
  public Ghoul(Monster m) {
    super(m);
    this.Areamagic = 1;
    setDynamicInt(10);
    getSkill().add(SkillTable.getInstance().getTemplate((Character)this, 21));
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
            if (getDistance(o.getX(), o.getY(), o.getMap(), this.Areamagic)) {
              if (getCurrentMp() >= getSkill().get(21).getSkill().getMpConsume() && !BuffTimerInstance.getInstance().contains(o, 21) && 
                Util.rand(0, 100) < 10) {
                getSkill().get(21).toMagic(o.getObjectId());
                this.ai_time = getMon().getModespeed(Magic.MagicAction2);
              } else {
                Attack(o, o.getX(), o.getY(), getGfxMode() + 1, 0);
                this.ai_time = getMon().getModespeed(getGfxMode() + 1);
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
