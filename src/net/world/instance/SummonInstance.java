package net.world.instance;

import java.util.ArrayList;
import java.util.List;
import net.database.bean.Monster;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ObjectHitratio;
import net.network.server.S_ObjectPet;
import net.world.ai.MonAi;
import net.world.ai.NpcMove;
import net.world.function.SummonSystem;
import net.world.object.Character;
import net.world.object.L1Object;

public class SummonInstance extends MonsterInstance {
  protected Character owner;
  
  protected int status;
  
  private int skill_id;
  
  private long SummonStartTime;
  
  private long duration;
  
  private List<L1Object> pinkNameList;
  
  private NpcMove _npcMove = null;
  
  public SummonInstance(Monster mon, Character cha, int skill_id, int duration) {
    super(mon);
    this.owner = cha;
    this.skill_id = skill_id;
    this.pinkNameList = new ArrayList<L1Object>();
    setSummon(true);
    set_Status(0);
    this.SummonStartTime = System.currentTimeMillis();
    this.duration = (duration * 1000);
    this._npcMove = new NpcMove(this);
  }
  
  public void isStatus(long time) {
    if (System.currentTimeMillis() - this.SummonStartTime >= this.duration)
      SummonSystem.getInstance().remove(this.owner, this.skill_id); 
  }
  
  public int getSkillId() {
    return this.skill_id;
  }
  
  public int get_Status() {
    return this.status;
  }
  
  public void set_Status(int status) {
    setRecess((status == 0));
    this.status = status;
  }
  
  public Character getOwn() {
    return this.owner;
  }
  
  public void setRecess(boolean recess) {
    super.setRecess(recess);
    if (recess)
      clearFightList(); 
  }
  
  public void Talk(PcInstance pc) {
    if (!isDead() && !isDelete()) {
      pc.SendPacket((S_BasePacket)new S_ObjectPet(this));
      pc.setSummon(this);
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (!isDead() && !isDelete()) {
      SummonSystem.getInstance().Commander(this, text1);
      this.owner.SendPacket((S_BasePacket)new S_ObjectPet(this));
    } 
  }
  
  public String getOwnName() {
    return this.owner.getName();
  }
  
  public void toAttack(L1Object target, int type) {
    super.toAttack(target, type);
    if (target instanceof SummonInstance) {
      SummonInstance s = (SummonInstance)target;
      if (s.getOwn().getObjectId() != this.owner.getObjectId())
        SummonSystem.getInstance().toAttack(this.owner, target); 
    } else if (target.getObjectId() != this.owner.getObjectId()) {
      SummonSystem.getInstance().toAttack(this.owner, target);
    } 
  }
  
  public void toWalk(long time) {
    this.ai_start_time = time;
    this.ai_time = getMon().getModespeed(getGfxMode());
    if (this.owner.isDelete() && get_Status() != 4)
      set_Status(4); 
    if ((get_Status() == 1 || get_Status() == 2) && !getDistance(this.owner.getX(), this.owner.getY(), this.owner.getMap(), 2) && !this.owner.isDead() && !this.owner.isDelete())
      StartMove(this.owner.getX(), this.owner.getY()); 
    if (get_Status() == 3)
      if (this.owner != null && getDistance(this.owner.getX(), this.owner.getY(), this.owner.getMap(), 5)) {
        int dir = this._npcMove.targetReverseDirection(this.owner.getX(), this.owner.getY());
        dir = this._npcMove.checkObject(dir);
        this._npcMove.setDirectionMove(dir);
      } else {
        set_Status(0);
      }  
  }
  
  public void addObject(L1Object o) {
    super.addObject(o);
    if (this.owner.getObjectId() == o.getObjectId())
      this.owner = (Character)o; 
  }
  
  public void toTeleport(int x, int y, int map) {
    SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)this, 169), true);
    super.toTeleport(x, y, map);
  }
  
  public void toDead(long time) {
    super.toDead(time);
    if (isDelete())
      SummonSystem.getInstance().remove(this); 
  }
  
  public void setCurrentHp(int currentHp) {
    super.setCurrentHp(currentHp);
    this.owner.SendPacket((S_BasePacket)new S_ObjectHitratio((L1Object)this, true));
  }
  
  public void toRecess(long time) {
    this.ai_start_time = time;
    this.ai_time = 1000;
  }
  
  public void Dismiss() {
    MonAi.getInstance().removeMon(this);
    toDelete();
    toSave(true);
  }
  
  public void NameUpdate(String name) {}
  
  public void Attack(L1Object target, int x, int y, int action, int effectId) {
    if (target.isWarZone() || isWarZone()) {
      clearFightList();
      return;
    } 
    if (pinkNameStatus(target))
      return; 
    super.Attack(target, x, y, action, effectId);
  }
  
  public void addAttackList(L1Object o) {
    if (o.isWarZone() || isWarZone() || get_Status() == 2) {
      clearFightList();
      return;
    } 
    super.addAttackList(o);
    if (o instanceof PcInstance) {
      PcInstance pc = (PcInstance)o;
      if (pc.isPinkName())
        addPinkNameList(pc); 
    } 
  }
  
  public boolean StartMove(int tx, int ty) {
    if (!getOwn().isWarZone())
      return super.StartMove(tx, ty); 
    return false;
  }
  
  private boolean pinkNameStatus(L1Object target) {
    if (target instanceof PcInstance) {
      PcInstance pc = (PcInstance)target;
      if (!pc.isPinkName() && isPinkNameList(pc)) {
        removePinkNameList(pc);
        if (this.attackList.contains(target))
          this.attackList.remove(target); 
        return true;
      } 
    } 
    return false;
  }
  
  private boolean isPinkNameList(PcInstance pc) {
    return this.pinkNameList.contains(pc);
  }
  
  private void addPinkNameList(PcInstance pc) {
    if (!isPinkNameList(pc))
      this.pinkNameList.add(pc); 
  }
  
  private void removePinkNameList(PcInstance pc) {
    if (isPinkNameList(pc))
      this.pinkNameList.remove(pc); 
  }
  
  public void clearFightList() {
    super.clearFightList();
    this.pinkNameList.clear();
  }
  
  public void drop() {}
}
