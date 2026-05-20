package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAttackMagic;
import net.world.function.SummonSystem;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;

public class ChillTouch extends Magic {
  public ChillTouch(Character cha, Skill skill) {
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
          this.operator.setCurrentHp(this.operator.getCurrentHp() + dmg);
          this.operator._dmg = dmg;
          o.toAttack((L1Object)this.operator, 3);
          o.setCurrentHp(o.getCurrentHp() - dmg);
          SummonSystem.getInstance().toAttack(this.operator, o);
        } 
        this.operator.calcheading(o.getX(), o.getY());
        this.operator.SendPacket((S_BasePacket)new S_ObjectAttackMagic((L1Object)this.operator, o, MagicAction, dmg, getSkill().getCastGfx()), true);
      } 
    } 
  }
}
