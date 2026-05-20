package net.world.instance.skill.function;

import net.Config;
import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectAdd;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ObjectHeading;
import net.network.server.S_ObjectRemove;
import net.world.instance.PcInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;

public class TripleArrow extends Magic {
  private L1Object fire;
  
  private L1Object mon;
  
  public TripleArrow(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    isTimerStop((L1Object)null);
    if (HpMpCheck() && ConsumeCount()) {
      L1Object o = this.operator.getObject(id);
      if (o != null) {
        this.mon = o;
        if (this.operator.isInvis())
          Detection.invis((L1Object)this.operator); 
        this.operator.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)this.operator, getSkill().getCastGfx()), true);
        int oldhp = o.getCurrentHp();
        this.operator.AttackBow(o, this.operator.getX(), this.operator.getY(), 21, 66, true);
        int newhp = o.getCurrentHp();
        BuffTimerInstance.getInstance().remove((L1Object)this.operator, this);
        BuffTimerInstance.getInstance().add((L1Object)this.operator, this);
        for (int i = 0; i < 2; i++) {
          PcInstance pc = (PcInstance)this.operator;
          int dmg = oldhp - newhp;
          if (dmg > 0 && pc.getInventory().Arrow(true)) {
            o.setCurrentHp(o.getCurrentHp() - dmg);
            this.operator.SendPacket((S_BasePacket)new S_ObjectAction(o, 2), true);
          } 
        } 
      } 
    } 
  }
  
  public void isTimer(L1Object o) {
    if (this.fire != null) {
      for (int i = 0; i < 3; i++)
        this.operator.SendPacket((S_BasePacket)new S_ObjectAction(this.operator.fire, 21), true); 
      BuffTimerInstance.getInstance().remove((L1Object)this.operator, this);
    } 
  }
  
  public void isTimerRun(L1Object o) {
    this.operator.fire = new L1Object();
    this.operator.fire.setObjectId(Config.getObjectID_ETC());
    this.operator.fire.setGfx(this.operator.getGfx());
    this.operator.fire.setGfxMode(this.operator.getGfxMode());
    this.operator.fire.setHeading(this.operator.calcheading(this.mon.getX(), this.mon.getY()));
    this.operator.SendPacket((S_BasePacket)new S_ObjectHeading(this.operator.fire), true);
    this.operator.fire.toTeleport(this.operator.getX(), this.operator.getY(), this.operator.getMap());
    this.operator.fire.setHeading(this.operator.calcheading(this.mon.getX(), this.mon.getY()));
    this.operator.SendPacket((S_BasePacket)new S_ObjectHeading(this.operator.fire), true);
    this.operator.SendPacket((S_BasePacket)new S_ObjectAdd(this.operator.fire), true);
    this.fire = this.operator.fire;
  }
  
  public void isTimerStop(L1Object o) {
    if (this.fire != null) {
      this.operator.SendPacket((S_BasePacket)new S_ObjectRemove(this.fire), true);
      this.fire.toDelete();
      this.fire = null;
      this.operator.fire = null;
    } 
  }
}
