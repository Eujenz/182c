package net.world.instance.skill.function;

import net.Config;
import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAdd;
import net.network.server.S_ObjectAttackMagic;
import net.network.server.S_ObjectPoison;
import net.network.server.S_ObjectRemove;
import net.util.Util;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;

public final class FreezeCockatrice extends Magic {
  public FreezeCockatrice(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    if (HpMpCheck() && ConsumeCount()) {
      L1Object o = this.operator.getObject(id);
      if (o != null) {
        if (this.operator.isInvis())
          Detection.invis((L1Object)this.operator); 
        if (isFreeze(o) && !o.isLock()) {
          BuffTimerInstance.getInstance().remove(o, 21);
          BuffTimerInstance.getInstance().remove(o, this);
          BuffTimerInstance.getInstance().add(o, this);
        } 
        this.operator.calcheading(o.getX(), o.getY());
        this.operator.SendPacket((S_BasePacket)new S_ObjectAttackMagic((L1Object)this.operator, o, MagicAction, 0, getSkill().getCastGfx()), true);
      } 
    } 
  }
  
  private boolean isFreeze(L1Object o) {
    if (o.isDead())
      return false; 
    if (o instanceof Character) {
      Character c = (Character)o;
      if (c.getMr() >= 50)
        return (Util.rand(0, 100) < 1); 
      return (Util.rand(0, this.operator.getLevel() + this.operator.getDynamicInt() * 2) > Util.rand(0, c.getLevel() + c.getMr()));
    } 
    return (Util.rand(0, 100) < 50);
  }
  
  public void isTimerRun(L1Object o) {
    o.Freeze = new L1Object();
    o.Freeze.setObjectId(Config.getObjectID_ETC());
    o.Freeze.setGfx(176);
    o.Freeze.toTeleport(o.getX(), o.getY(), o.getMap());
    o.SendPacket((S_BasePacket)new S_ObjectAdd(o.Freeze), true);
    o.setLock(true);
    o.SendPacket((S_BasePacket)new S_ObjectPoison(o.getObjectId(), o.isPoison(), o.isLock()), true);
    o.SendPacket((S_BasePacket)new S_ObjectPoison(12));
  }
  
  public void isTimerStop(L1Object o) {
    o.setLock(false);
    o.SendPacket((S_BasePacket)new S_ObjectPoison(o.getObjectId(), o.isPoison(), o.isLock()), true);
    o.SendPacket((S_BasePacket)new S_ObjectPoison(13));
    if (o.Freeze != null) {
      o.SendPacket((S_BasePacket)new S_ObjectRemove(o.Freeze), true);
      o.Freeze.toDelete();
      o.Freeze = null;
    } 
  }
}
