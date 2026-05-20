package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_BuffBlind;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ServerMessage;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.pc.L1PinkName;
import net.world.time.BuffTimerInstance;
import net.world.time.ItemTimerInstance;

public class CurseBlind extends Magic {
  public CurseBlind(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    if (HpMpCheck() && ConsumeCount()) {
      L1Object o = this.operator.getObject(id);
      if (o != null || id == this.operator.getObjectId()) {
        Character character = null;
        L1PinkName.execute((L1Object)this.operator, o);
        if (id == this.operator.getObjectId())
          character = this.operator; 
        if (this.operator.isInvis())
          Detection.invis((L1Object)this.operator); 
        boolean isBuff = BuffTimerInstance.getInstance().contains((L1Object)character, 14);
        if (!isBuff && Figure((L1Object)character) && character instanceof Character) {
          character.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)character, getSkill().getCastGfx()), true);
          ItemTimerInstance.getInstance().remove(character, "$239");
          BuffTimerInstance.getInstance().remove((L1Object)character, this);
          BuffTimerInstance.getInstance().add((L1Object)character, this);
        } else {
          this.operator.SendPacket((S_BasePacket)new S_ServerMessage(280));
        } 
      } 
    } 
  }
  
  public void isTimerRun(L1Object o) {
    if (ItemTimerInstance.getInstance().contains((Character)o, "$6 $23 ")) {
      o.SendPacket((S_BasePacket)new S_BuffBlind(2));
    } else {
      o.SendPacket((S_BasePacket)new S_BuffBlind(1));
    } 
  }
  
  public void isTimerStop(L1Object o) {
    o.SendPacket((S_BasePacket)new S_BuffBlind(0));
  }
}
