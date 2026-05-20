package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;

public class HealPledge extends Magic {
  public HealPledge(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    if (!this.gm_buff)
      this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true); 
    if (HpMpCheck() && ConsumeCount()) {
      this.operator.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)this.operator, getSkill().getCastGfx()), true);
      int dmg = 0;
      byte b;
      int i;
      L1Object[] arrayOfL1Object;
      for (i = (arrayOfL1Object = this.operator.getObjectList()).length, b = 0; b < i; ) {
        L1Object o = arrayOfL1Object[b];
        if (isClan(o) || isSummon(o)) {
          dmg = Damage(o, false);
          if (dmg > 0) {
            dmg = lawfulDamage((L1Object)this.operator, dmg);
            o.setCurrentHp(o.getCurrentHp() + dmg);
          } 
        } 
        b++;
      } 
    } 
  }
  
  protected double getHealAmount() {
    double amount = super.getHealAmount();
    int inter = this.operator.getTotalInt();
    if (inter >= 15 && inter <= 17) {
      amount += 9.0D;
    } else if (inter == 18) {
      amount += 18.0D;
    } else if (inter == 19) {
      amount += 27.0D;
    } else if (inter == 20) {
      amount += 36.0D;
    } else if (inter == 21) {
      amount += 45.0D;
    } else if (inter == 22) {
      amount += 54.0D;
    } else if (inter == 23) {
      amount += 66.0D;
    } else if (inter == 24) {
      amount += 72.0D;
    } else if (inter >= 25) {
      amount += 81.0D;
    } 
    return amount;
  }
}
