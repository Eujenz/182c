package net.world.npc;

import java.util.ArrayList;
import java.util.List;
import net.database.bean.Npc;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ObjectHeading;
import net.world.instance.NpcInstance;
import net.world.instance.PcInstance;
import net.world.object.L1Object;

public class Guard extends NpcInstance {
  protected List<L1Object> attackList = new ArrayList<L1Object>();
  
  public Guard(Npc npc) {
    super(npc);
    switch (npc.get_npcId()) {
      case 494:
        this.Areaatk = 2;
        break;
      case 495:
        this.Areaatk = 8;
        break;
    } 
  }
  
  public void toFight(long time) {
    this.ai_start_time = time;
    if (this.attackList.size() > 0) {
      L1Object o = this.attackList.get(0);
      if (o.isInvis() || o.isDelete() || o.isDead() || !getDistance(o.getX(), o.getY(), o.getMap(), 17)) {
        this.attackList.remove(o);
      } else if (getDistance(o.getX(), o.getY(), o.getMap(), this.Areaatk)) {
        if (this.Areaatk > 2) {
          AttackBow(o, o.getX(), o.getY(), getGfxMode() + 1, 66, true);
        } else {
          Attack(o, o.getX(), o.getY(), getGfxMode() + 1, 0);
        } 
        this.ai_time = getNpc().getModespeed(getGfxMode() + 1);
      } else {
        StartMove(o.getX(), o.getY());
        this.ai_time = getNpc().getModespeed(getGfxMode());
      } 
    } else {
      clearFightList();
    } 
  }
  
  public void toWalk(long time) {
    this.ai_start_time = time;
    this.ai_time = getNpc().getModespeed(getGfxMode());
    CheckHomeLocation();
    serarchChaoticPlayer();
    if (this.attackList.size() > 0)
      setFight(true); 
  }
  
  public void toAttack(L1Object target, int type) {
    addAttackList(target);
    byte b;
    int i;
    L1Object[] arrayOfL1Object;
    for (i = (arrayOfL1Object = getObjectList()).length, b = 0; b < i; ) {
      L1Object o = arrayOfL1Object[b];
      if (o instanceof Guard)
        ((Guard)o).addAttackList(target); 
      b++;
    } 
  }
  
  public void addAttackList(L1Object o) {
    if (!this.attackList.contains(o))
      this.attackList.add(o); 
  }
  
  public void clearFightList() {
    setFight(false);
    this.attackList.clear();
  }
  
  protected void CheckHomeLocation() {
    if (getX() != getHomeX() || getY() != getHomeY()) {
      if (getDistance(getHomeX(), getHomeY(), getMap(), 30)) {
        StartMove(getHomeX(), getHomeY());
      } else {
        toTeleport(getHomeX(), getHomeY(), getMap());
      } 
    } else if (getHeading() != getHomeHeading()) {
      setHeading(getHomeHeading());
      SendPacket((S_BasePacket)new S_ObjectHeading((L1Object)this), true);
    } 
  }
  
  protected void serarchChaoticPlayer() {
    long time = System.currentTimeMillis();
    byte b;
    int i;
    L1Object[] arrayOfL1Object;
    for (i = (arrayOfL1Object = getObjectList()).length, b = 0; b < i; ) {
      L1Object o = arrayOfL1Object[b];
      if ((o.getClanId() <= 0 || getClanId() != o.getClanId()) && 
        o instanceof PcInstance) {
        PcInstance pc = (PcInstance)o;
        if (!pc.isDead() && !pc.isDelete() && !pc.isInvis() && pc.getPkTime() > 0L && time - pc.getPkTime() <= 86400000L)
          addAttackList((L1Object)pc); 
      } 
      b++;
    } 
  }
  
  public void toTeleport(int x, int y, int map) {
    SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)this, 169), true);
    super.toTeleport(x, y, map);
  }
}
