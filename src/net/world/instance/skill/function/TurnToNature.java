package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.world.instance.SummonInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;

public class TurnToNature extends Magic {
  public TurnToNature(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    if (HpMpCheck() && ConsumeCount()) {
      L1Object o = this.operator.getObject(id);
      if (o != null) {
        if (this.operator.isInvis())
          Detection.invis((L1Object)this.operator); 
        if (o instanceof SummonInstance) {
          SummonInstance sum = (SummonInstance)o;
          sum.Dismiss();
          o.SendPacket((S_BasePacket)new S_ObjectEffect(o, getSkill().getCastGfx()), true);
        } 
      } 
    } 
  }
}
