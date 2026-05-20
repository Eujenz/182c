package net.world.instance.skill.function.elf;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_CharacterStat;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.world.instance.PcInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;

public final class ClearMind extends Magic {
  public ClearMind(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    if (!this.gm_buff)
      this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true); 
    if (HpMpCheck() && ConsumeCount()) {
      this.operator.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)this.operator, getSkill().getCastGfx()), true);
      BuffTimerInstance.getInstance().remove((L1Object)this.operator, this);
      BuffTimerInstance.getInstance().add((L1Object)this.operator, this);
    } 
  }
  
  public void isTimerRun(L1Object o) {
    if (o instanceof PcInstance) {
      PcInstance pc = (PcInstance)o;
      pc.setDynamicWis(pc.getDynamicWis() + getSkill().getMaxdmg());
      pc.SendPacket((S_BasePacket)new S_CharacterStat((Character)pc));
    } 
  }
  
  public void isTimerStop(L1Object o) {
    if (o instanceof PcInstance) {
      PcInstance pc = (PcInstance)o;
      pc.setDynamicWis(pc.getDynamicWis() - getSkill().getMaxdmg());
      pc.SendPacket((S_BasePacket)new S_CharacterStat((Character)pc));
    } 
  }
}
