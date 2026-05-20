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

public class CreateZombie extends Magic {
  public CreateZombie(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    if (HpMpCheck() && ConsumeCount()) {
      L1Object o = this.operator.getObject(id);
      if (o != null && o.isDead() && o instanceof MonsterInstance && Figure(o)) {
        MonsterInstance mon = (MonsterInstance)o;
        SummonSystem.getInstance().addCreateZombie(this.operator, mon, getSkill().getSkill_id(), getSkill().getCastGfx(), getSkill().getBuffDuration());
      } else {
        this.operator.SendPacket((S_BasePacket)new S_ServerMessage(280));
      } 
    } 
  }
}
