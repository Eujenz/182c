package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;
import net.world.time.ItemTimerInstance;

public class RemoveCurse extends Magic {
  public RemoveCurse(Character cha, Skill skill) {
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
        character.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)character, getSkill().getCastGfx()), true);
        if (character instanceof Character) {
          Character cha = character;
          ItemTimerInstance.getInstance().remove(cha, "$239");
        } 
        BuffTimerInstance.getInstance().remove((L1Object)character, 8);
        BuffTimerInstance.getInstance().remove((L1Object)character, 14);
        BuffTimerInstance.getInstance().remove((L1Object)character, 21);
      } 
    } 
  }
}
