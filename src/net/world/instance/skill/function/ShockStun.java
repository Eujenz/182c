package net.world.instance.skill.function;

import net.Config;
import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAdd;
import net.network.server.S_ObjectPoison;
import net.network.server.S_ObjectRemove;
import net.util.Util;
import net.world.instance.ItemInstance;
import net.world.instance.MonsterInstance;
import net.world.instance.PcInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;

public class ShockStun extends Magic {
  private MonsterInstance mon;
  
  public ShockStun(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    boolean istwohand = false;
    PcInstance pc = (PcInstance)this.operator;
    ItemInstance weapon = pc.getInventory().getWeapon();
    if (weapon != null && 
      weapon.getItem().isTohand())
      istwohand = true; 
    if (HpMpCheck() && ConsumeCount() && istwohand) {
      L1Object o = this.operator.getObject(id);
      if (o != null) {
        if (this.operator.isInvis())
          Detection.invis((L1Object)this.operator); 
        if (rnd((L1Object)this.operator, o)) {
          BuffTimerInstance.getInstance().remove(o, this);
          BuffTimerInstance.getInstance().add(o, this);
        } 
        this.operator.calcheading(o.getX(), o.getY());
      } 
    } 
  }
  
  public boolean rnd(L1Object pc, L1Object o) {
    int dice = Util.rand(1, 100);
    if (pc.getLevel() < o.getLevel()) {
      if (dice <= 20)
        return true; 
    } else if (pc.getLevel() == o.getLevel()) {
      if (dice <= 40)
        return true; 
    } else if (pc.getLevel() > o.getLevel() && 
      dice <= 60) {
      return true;
    } 
    return false;
  }
  
  public void isTimerRun(L1Object o) {
    if (!o.isLock()) {
      o.Freeze = new L1Object();
      o.Freeze.setObjectId(Config.getObjectID_ETC());
      o.Freeze.setGfx(4183);
      o.Freeze.toTeleport(o.getX(), o.getY(), o.getMap());
      o.SendPacket((S_BasePacket)new S_ObjectAdd(o.Freeze), true);
      o.setLock(true);
      o.SendPacket((S_BasePacket)new S_ObjectPoison(o.getObjectId(), o.isPoison(), o.isLock()), true);
      o.SendPacket((S_BasePacket)new S_ObjectPoison(2));
    } 
  }
  
  public void isTimerStop(L1Object o) {
    if (o.isLock()) {
      o.setLock(false);
      o.SendPacket((S_BasePacket)new S_ObjectPoison(o.getObjectId(), o.isPoison(), o.isLock()), true);
      o.SendPacket((S_BasePacket)new S_ObjectPoison(0));
      if (o.Freeze != null) {
        o.SendPacket((S_BasePacket)new S_ObjectRemove(o.Freeze), true);
        o.Freeze.toDelete();
        o.Freeze = null;
      } 
    } 
  }
}
