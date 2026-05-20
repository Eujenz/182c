package net.world.instance.skill.function;

import net.Config;
import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAdd;
import net.network.server.S_ObjectAttackMagic;
import net.network.server.S_ObjectPoison;
import net.network.server.S_ObjectRemove;
import net.world.function.SummonSystem;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;

public class Freeze extends Magic {
  public Freeze(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    if (HpMpCheck() && ConsumeCount()) {
      L1Object o = this.operator.getObject(id);
      if (o != null) {
        if (this.operator.isInvis())
          Detection.invis((L1Object)this.operator); 
        int dmg = Damage(o, true);
        if (dmg > 0) {
          this.operator._dmg = dmg;
          o.toAttack((L1Object)this.operator, 3);
          o.setCurrentHp(o.getCurrentHp() - dmg);
          SummonSystem.getInstance().toAttack(this.operator, o);
          if (Figure(o)) {
            BuffTimerInstance.getInstance().remove(o, 21);
            BuffTimerInstance.getInstance().remove(o, this);
            BuffTimerInstance.getInstance().add(o, this);
          } 
        } 
        this.operator.calcheading(o.getX(), o.getY());
        this.operator.SendPacket((S_BasePacket)new S_ObjectAttackMagic((L1Object)this.operator, o, MagicAction, dmg, getSkill().getCastGfx()), true);
      } 
    } 
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
