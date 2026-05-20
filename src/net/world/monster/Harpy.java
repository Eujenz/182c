package net.world.monster;

import net.database.SkillTable;
import net.database.bean.Monster;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectMode;
import net.util.Util;
import net.world.instance.MonsterInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;
import net.world.time.HpMpTimer;

public class Harpy extends MonsterInstance {
  private static final int SKILLID_1 = 19;
  
  private static final int[] item_list = new int[] { 
      232, 233, 234, 235, 237, 238, 239, 764, 943, 944, 
      1251, 1252, 1253, 1507 };
  
  public Harpy(Monster m) {
    super(m);
    setFood(29);
    setDynamicTicMp(10);
    setDynamicTicHp(10);
    HpMpTimer.getInstance().add((Character)this);
    this.Areamagic = 2;
    getSkill().add(SkillTable.getInstance().getTemplate((Character)this, 19));
  }
  
  public void toFight(long time) {
    if (getCurrentHp() <= getMaxHp() * 0.3D && Util.rand(0, 100) < 10) {
      setRecess(true);
      this.ai_start_time = time;
      this.ai_time = getMon().getModespeed(44);
      return;
    } 
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
              if (Util.rand(0, 100) < 20 && getCurrentMp() >= getSkill().get(19).getSkill().getMpConsume() && !BuffTimerInstance.getInstance().contains(o, 19)) {
                getSkill().get(19).toMagic(o.getObjectId());
                this.ai_time = getMon().getModespeed(Magic.MagicAction2);
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
  
  public void toWalk(long time) {
    super.toWalk(time);
    if (isFight() || 
      SearchItem(item_list));
  }
  
  public void toRecess(long time) {
    this.ai_start_time = time;
    this.ai_time = 400;
    if (getCurrentHp() > getMaxHp() * 0.5D) {
      L1Object o = SearchPlayer();
      if (o != null) {
        setRecess(false);
        addAttackList(o);
        setFight(true);
        this.ai_time = getMon().getModespeed(45);
        return;
      } 
    } 
    if (SearchItem(item_list)) {
      setRecess(false);
      this.ai_time = getMon().getModespeed(45);
      return;
    } 
  }
  
  public void setRecess(boolean recess) {
    if (isRecess() && !recess) {
      setGfxMode(45);
      SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this), true);
      setGfxMode(0);
      SendPacket((S_BasePacket)new S_ObjectMode((L1Object)this), true);
    } else {
      setGfxMode(44);
      SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this), true);
      setGfxMode(4);
      SendPacket((S_BasePacket)new S_ObjectMode((L1Object)this), true);
    } 
    super.setRecess(recess);
  }
}
