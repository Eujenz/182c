package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ServerMessage;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;

public class ImmuneToHarm extends Magic {
  public ImmuneToHarm(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    if (HpMpCheck() && ConsumeCount()) {
      L1Object o = this.operator.getObject(id);
      if (o != null || id == this.operator.getObjectId()) {
        Character character = null;
        if (id == this.operator.getObjectId())
          character = this.operator; 
        if (character instanceof Character && isClan((L1Object)character)) {
          character.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)character, getSkill().getCastGfx()), true);
          BuffTimerInstance.getInstance().remove((L1Object)character, this);
          BuffTimerInstance.getInstance().add((L1Object)character, this);
        } else {
          this.operator.SendPacket((S_BasePacket)new S_ServerMessage(280));
        } 
      } 
    } 
  }
  
  public void isTimerRun(L1Object o) {
    o.setBuffImmuneToHarm(true);
    o.Message("你感覺到有神秘的力量在保護你。");
  }
  
  public void isTimerStop(L1Object o) {
    o.setBuffImmuneToHarm(false);
    o.Message("神秘的力量消失了。");
  }
}
