package net.world.instance.skill.function.elf;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_BuffShield;
import net.network.server.S_CharacterStat;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ServerMessage;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;

public final class BlessOfEarth extends Magic {
  public BlessOfEarth(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    if (!this.gm_buff)
      this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true); 
    if (HpMpCheck() && ConsumeCount()) {
      this.operator.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)this.operator, getSkill().getCastGfx()), true);
      BuffTimerInstance.getInstance().remove((L1Object)this.operator, 3);
      BuffTimerInstance.getInstance().remove((L1Object)this.operator, 151);
      BuffTimerInstance.getInstance().remove((L1Object)this.operator, 168);
      BuffTimerInstance.getInstance().remove((L1Object)this.operator, this);
      BuffTimerInstance.getInstance().add((L1Object)this.operator, this);
      for (L1Object o : this.operator.getObjectList()) {
        if (!o.isDead() && !o.isDelete() && this.operator.getDistance(o.getX(), o.getY(), o.getMap(), getSkill().getRange()))
          if (o instanceof net.world.instance.PcInstance && o.getPartyId() == this.operator.getPartyId()) {
            o.SendPacket((S_BasePacket)new S_ObjectEffect(o, getSkill().getCastGfx()), true);
            BuffTimerInstance.getInstance().remove(o, 3);
            BuffTimerInstance.getInstance().remove(o, 151);
            BuffTimerInstance.getInstance().remove(o, 168);
            BuffTimerInstance.getInstance().remove(o, this);
            BuffTimerInstance.getInstance().add(o, this);
          }  
      } 
    } 
  }
  
  public void isTimerRun(L1Object o) {
    ((Character)o).setDynamicAc(((Character)o).getDynamicAc() + getSkill().getMaxdmg());
    o.SendPacket((S_BasePacket)new S_CharacterStat((Character)o));
    o.SendPacket((S_BasePacket)new S_BuffShield(getTime(), getSkill().getMaxdmg()));
  }
  
  public void isTimerStop(L1Object o) {
    ((Character)o).setDynamicAc(((Character)o).getDynamicAc() - getSkill().getMaxdmg());
    o.SendPacket((S_BasePacket)new S_CharacterStat((Character)o));
    o.SendPacket((S_BasePacket)new S_BuffShield(0, 0));
    o.SendPacket((S_BasePacket)new S_ServerMessage(725));
  }
}
