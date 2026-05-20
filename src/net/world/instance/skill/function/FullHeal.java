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

public final class FullHeal extends Magic {
  public FullHeal(Character cha, Skill skill) {
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
      amount += 13.0D;
    } else if (inter == 18) {
      amount += 26.0D;
    } else if (inter == 19) {
      amount += 39.0D;
    } else if (inter == 20) {
      amount += 52.0D;
    } else if (inter == 21) {
      amount += 65.0D;
    } else if (inter == 22) {
      amount += 78.0D;
    } else if (inter == 23) {
      amount += 91.0D;
    } else if (inter == 24) {
      amount += 104.0D;
    } else if (inter >= 25) {
      amount += 117.0D;
    } 
    return amount;
  }
}
