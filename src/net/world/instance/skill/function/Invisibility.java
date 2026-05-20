package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ObjectInvis;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;

public class Invisibility extends Magic {
  public Invisibility(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    if (HpMpCheck() && ConsumeCount()) {
      BuffTimerInstance.getInstance().remove((L1Object)this.operator, this);
      BuffTimerInstance.getInstance().add((L1Object)this.operator, this);
      this.operator.SendPacket((S_BasePacket)new S_ObjectEffect(getSkill().getCastGfx()), true);
    } 
  }
  
  public void isTimerRun(L1Object o) {
    o.setInvis(true);
    o.SendPacket((S_BasePacket)new S_ObjectInvis(o.getObjectId(), o.isInvis()), true);
  }
  
  public void isTimerStop(L1Object o) {
    if (o.isInvis()) {
      o.setInvis(false);
      o.SendPacket((S_BasePacket)new S_ObjectInvis(o.getObjectId(), o.isInvis()), true);
    } 
  }
}
