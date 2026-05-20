package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ObjectPoison;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.function.SummonSystem;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.pc.L1PinkName;
import net.world.time.BuffTimerInstance;

public class CursePoison extends Magic {
  public CursePoison(Character cha, Skill skill) {
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
        if (!character.isLock() && Figure((L1Object)character) && character instanceof Character) {
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
    o.setPoison(true);
    o.SendPacket((S_BasePacket)new S_ObjectPoison(o.getObjectId(), o.isPoison(), o.isLock()), true);
  }
  
  public void isTimerStop(L1Object o) {
    o.setPoison(false);
    o.SendPacket((S_BasePacket)new S_ObjectPoison(o.getObjectId(), o.isPoison(), o.isLock()), true);
  }
  
  public void isTimer(L1Object o) {
    if (o.isDead()) {
      BuffTimerInstance.getInstance().remove(o, this);
    } else if (o instanceof Character) {
      int dmg = Util.rand(1, 3);
      if (!this.operator.isDelete()) {
        this.operator._dmg = dmg;
        o.toAttack((L1Object)this.operator, 3);
        o.setCurrentHp(o.getCurrentHp() - dmg);
        SummonSystem.getInstance().toAttack(this.operator, o);
      } 
    } 
  }
}
