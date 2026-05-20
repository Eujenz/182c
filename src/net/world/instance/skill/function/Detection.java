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

public class Detection extends Magic {
  public Detection(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    if (HpMpCheck() && ConsumeCount()) {
      this.operator.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)this.operator, getSkill().getCastGfx()), true);
      if (this.operator.isInvis())
        invis((L1Object)this.operator); 
      byte b;
      int i;
      L1Object[] arrayOfL1Object;
      for (i = (arrayOfL1Object = this.operator.getObjectList()).length, b = 0; b < i; ) {
        L1Object o = arrayOfL1Object[b];
        o.MagicalAttackEncounters(this.operator);
        if (o.isInvis() && !o.isGm())
          invis(o); 
        b++;
      } 
    } 
  }
  
  public static void invis(L1Object o) {
    synchronized (o) {
      BuffTimerInstance.getInstance().remove(o, 39);
      o.setInvis(false);
      o.SendPacket((S_BasePacket)new S_ObjectInvis(o.getObjectId(), o.isInvis()), true);
    } 
  }
}
