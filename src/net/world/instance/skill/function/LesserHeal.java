package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.world.function.SummonSystem;
import net.world.instance.MonsterInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;

public class LesserHeal extends Magic {
  public LesserHeal(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    if (!this.gm_buff)
      this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true); 
    if (HpMpCheck() && ConsumeCount()) {
      L1Object o = this.operator.getObject(id);
      if (o != null || id == this.operator.getObjectId()) {
        Character character = null;
        if (id == this.operator.getObjectId())
          character = this.operator; 
        character.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)character, getSkill().getCastGfx()), true);
        int dmg = Damage((L1Object)character, character instanceof net.world.instance.NpcInstance);
        if (dmg > 0) {
          dmg = lawfulDamage((L1Object)this.operator, dmg);
          if (character instanceof MonsterInstance) {
            MonsterInstance mon = (MonsterInstance)character;
            if (mon.getMon().isUndead() && !(mon instanceof net.world.instance.SummonInstance)) {
              this.operator._dmg = dmg;
              mon.toAttack((L1Object)this.operator, 3);
              mon.setCurrentHp(mon.getCurrentHp() - dmg);
              mon.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)mon, 2), true);
              SummonSystem.getInstance().toAttack(this.operator, (L1Object)character);
              return;
            } 
          } 
          character.setCurrentHp(character.getCurrentHp() + dmg);
        } 
      } 
    } 
  }
  
  protected double getHealAmount() {
    double amount = super.getHealAmount();
    int inter = this.operator.getTotalInt();
    if (inter >= 15 && inter <= 17) {
      amount += 5.0D;
    } else if (inter == 18) {
      amount += 10.0D;
    } else if (inter == 19) {
      amount += 15.0D;
    } else if (inter == 20) {
      amount += 20.0D;
    } else if (inter == 21) {
      amount += 25.0D;
    } else if (inter == 22) {
      amount += 30.0D;
    } else if (inter == 23) {
      amount += 35.0D;
    } else if (inter == 24) {
      amount += 40.0D;
    } else if (inter >= 25) {
      amount += 45.0D;
    } 
    return amount;
  }
}
