package net.world.monster;

import net.database.SkillTable;
import net.database.bean.Monster;
import net.util.Util;
import net.world.instance.MonsterInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;
import net.world.time.HpMpTimer;

public final class Succubus extends MonsterInstance {
  private static final int SKILLID_1 = 1011;
  
  public Succubus(Monster mon) {
    super(mon);
    setFood(29);
    setDynamicTicMp(20);
    setDynamicTicHp(40);
    HpMpTimer.getInstance().add((Character)this);
    this.Areamagic = 5;
    getSkill().add(SkillTable.getInstance().getTemplate((Character)this, 1011));
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
              if (getCurrentMp() >= getSkill().get(1011).getSkill().getMpConsume() && !BuffTimerInstance.getInstance().contains(o, 1011) && 
                Util.rand(0, 100) < 30) {
                getSkill().get(1011).toMagic(o.getObjectId());
                this.ai_time = getMon().getModespeed(Magic.MagicAction);
              } else if (getDistance(o.getX(), o.getY(), o.getMap(), this.Areaatk)) {
                Attack(o, o.getX(), o.getY(), getGfxMode() + 1, 0);
                this.ai_time = getMon().getModespeed(getGfxMode() + 1);
              } else {
                StartMove(o.getX(), o.getY());
                this.ai_time = getMon().getModespeed(getGfxMode());
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
