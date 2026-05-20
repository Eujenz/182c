package net.world.monster;

import net.database.MonsterSpawnTable;
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

public final class DeathKnight extends MonsterInstance {
  private static final int SKILLID_1 = 1001;
  
  public DeathKnight(Monster mon) {
    super(mon);
    setFood(29);
    setDynamicTicMp(200);
    setDynamicTicHp(1200);
    HpMpTimer.getInstance().add((Character)this);
    this.Areamagic = 2;
    getSkill().add(SkillTable.getInstance().getTemplate((Character)this, 1001));
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
              if (getCurrentMp() >= getSkill().get(1001).getSkill().getMpConsume() && !BuffTimerInstance.getInstance().contains(o, 1001) && 
                Util.rand(0, 100) < 30) {
                getSkill().get(1001).toMagic(o.getObjectId());
                this.ai_time = getMon().getModespeed(Magic.MagicAction2);
              } else if (Util.rand(0, 100) < 8) {
                SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this, Magic.MagicAction2), true);
                this.ai_time = getMon().getModespeed(Magic.MagicAction2);
                int count = Util.rand(5, 8);
                for (int i = 0; i < count; i++) {
                  MonsterInstance mon = MonsterSpawnTable.getInstance().newMonster(19);
                  mon.toTeleport(Util.rand(getX() - 3, getX() + 3), Util.rand(getY() - 3, getY() + 3), getMap());
                  mon.setGfxMode(4);
                  SendPacket((S_BasePacket)new S_ObjectAction((L1Object)mon), true);
                  mon.setGfxMode(0);
                  SendPacket((S_BasePacket)new S_ObjectMode((L1Object)mon), true);
                } 
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
