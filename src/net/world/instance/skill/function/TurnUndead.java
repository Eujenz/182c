package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ServerMessage;
import net.world.instance.MonsterInstance;
import net.world.instance.PcInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;

public class TurnUndead extends Magic {
  public TurnUndead(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    if (HpMpCheck() && ConsumeCount()) {
      L1Object o = this.operator.getObject(id);
      if (o != null) {
        if (this.operator.isInvis())
          Detection.invis((L1Object)this.operator); 
        if (o instanceof MonsterInstance) {
          MonsterInstance mon = (MonsterInstance)o;
          if (mon.getMon().isUndead()) {
            if (Figure(o) && this.operator.LongAttackCK((L1Object)mon, 12)) {
              this.operator._dmg = mon.getCurrentHp();
              mon.toAttack((L1Object)this.operator, 3);
              mon.setCurrentHp(0);
              mon.SendPacket((S_BasePacket)new S_ObjectEffect(mon.getX(), mon.getY(), getSkill().getCastGfx()), true);
              if (this.operator instanceof PcInstance)
                mon.toQuest((PcInstance)this.operator, this); 
            } else {
              this.operator.SendPacket((S_BasePacket)new S_ServerMessage(280));
            } 
          } else {
            this.operator.SendPacket((S_BasePacket)new S_ServerMessage(281));
          } 
        } else {
          this.operator.SendPacket((S_BasePacket)new S_ServerMessage(281));
        } 
      } 
    } 
  }
}
