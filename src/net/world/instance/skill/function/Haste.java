package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_BuffSpeed;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ServerMessage;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;
import net.world.time.ItemTimerInstance;

public class Haste extends Magic {
  public Haste(Character cha, Skill skill) {
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
        if (character instanceof Character) {
          character.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)character, getSkill().getCastGfx()), true);
          if (character.isSlow()) {
            BuffTimerInstance.getInstance().remove((L1Object)character, 20);
          } else {
            if (character.isSpeed()) {
              character.SendPacket((S_BasePacket)new S_ServerMessage(183));
            } else {
              character.SendPacket((S_BasePacket)new S_ServerMessage(184));
            } 
            ItemTimerInstance.getInstance().remove(character, "$234");
            ItemTimerInstance.getInstance().remove(character, "$1652 $234");
            BuffTimerInstance.getInstance().remove((L1Object)character, this);
            BuffTimerInstance.getInstance().add((L1Object)character, this);
          } 
        } else {
          this.operator.SendPacket((S_BasePacket)new S_ServerMessage(280));
        } 
      } 
    } 
  }
  
  public void isTimerRun(L1Object o) {
    o.setSpeed(true);
    o.SendPacket((S_BasePacket)new S_BuffSpeed(o, 0, 1, getTime()), true);
  }
  
  public void isTimerStop(L1Object o) {
    o.setSpeed(false);
    o.SendPacket((S_BasePacket)new S_BuffSpeed(o, 0, 0, 0), true);
  }
}
