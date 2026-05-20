package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.world.function.SummonSystem;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;

public class SummonLesserElemental extends Magic {
  public SummonLesserElemental(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    if (this.operator.isWarZone()) {
      this.operator.Message("攻城戰期間在此無法召喚怪物。");
      return;
    } 
    if (this.operator.getElfAttr() == 0)
      return; 
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    if (HpMpCheck() && ConsumeCount())
      SummonSystem.getInstance().addElfSummonMonster(this.operator, getSkill().getSkill_id(), getSkill().getBuffDuration(), 1); 
  }
}
