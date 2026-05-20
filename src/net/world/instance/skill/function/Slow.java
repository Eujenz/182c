package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_BuffSpeed;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ServerMessage;
import net.world.instance.MonsterInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.pc.L1PinkName;
import net.world.time.BuffTimerInstance;
import net.world.time.ItemTimerInstance;

public class Slow extends Magic {
  public Slow(Character cha, Skill skill) {
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
        if (Figure((L1Object)character) && character instanceof Character) {
          if (character instanceof MonsterInstance) {
            MonsterInstance mon = (MonsterInstance)character;
            if (mon.isBoss())
              return; 
          } 
          character.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)character, getSkill().getCastGfx()), true);
          if (character.isBrave()) {
            ItemTimerInstance.getInstance().remove(character, "$943");
            ItemTimerInstance.getInstance().remove(character, "$110");
          } else if (character.isSpeed()) {
            ItemTimerInstance.getInstance().remove(character, "$234");
          } else {
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
    o.setSlow(true);
    o.SendPacket((S_BasePacket)new S_BuffSpeed(o, 0, 2, getTime()), true);
  }
  
  public void isTimerStop(L1Object o) {
    o.setSlow(false);
    o.SendPacket((S_BasePacket)new S_BuffSpeed(o, 0, 0, 0), true);
  }
}
