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

public final class EarthSkin extends Magic {
  public EarthSkin(Character cha, Skill skill) {
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
        if (character instanceof net.world.instance.PcInstance) {
          character.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)character, getSkill().getCastGfx()), true);
          BuffTimerInstance.getInstance().remove((L1Object)character, 3);
          BuffTimerInstance.getInstance().remove((L1Object)character, 159);
          BuffTimerInstance.getInstance().remove((L1Object)character, 168);
          BuffTimerInstance.getInstance().remove((L1Object)character, this);
          BuffTimerInstance.getInstance().add((L1Object)character, this);
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
