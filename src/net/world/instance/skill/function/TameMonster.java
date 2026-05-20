package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ServerMessage;
import net.world.function.SummonSystem;
import net.world.instance.MonsterInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;

public class TameMonster extends Magic {
  public TameMonster(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    if (HpMpCheck() && ConsumeCount()) {
      L1Object o = this.operator.getObject(id);
      if (o != null && o instanceof MonsterInstance) {
        MonsterInstance mon = (MonsterInstance)o;
        if (!mon.isSummon() && mon.getMon().isTameable() && mon.isTame(false)) {
          SummonSystem.getInstance().addTameMonster(this.operator, mon, getSkill().getSkill_id(), getSkill().getCastGfx(), getSkill().getBuffDuration());
        } else {
          this.operator.SendPacket((S_BasePacket)new S_ServerMessage(280));
        } 
      } else {
        this.operator.SendPacket((S_BasePacket)new S_ServerMessage(280));
      } 
    } 
  }
}
