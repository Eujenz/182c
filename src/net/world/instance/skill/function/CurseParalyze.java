package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ObjectHeading;
import net.network.server.S_ObjectPoison;
import net.network.server.S_ServerMessage;
import net.world.instance.MonsterInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.pc.L1PinkName;
import net.world.time.BuffTimerInstance;

public class CurseParalyze extends Magic {
  private final int DELAYCOUNT = 5;
  
  private int counting = 0;
  
  public CurseParalyze(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    L1Object o = null;
    if (this.operator instanceof MonsterInstance) {
      o = this.operator.getObject(id);
      if (o != null) {
        if (o instanceof MonsterInstance) {
          MonsterInstance mon = (MonsterInstance)o;
          if (mon.isBoss())
            return; 
        } 
        this.operator.calcheading(o.getX(), o.getY());
        this.operator.SendPacket((S_BasePacket)new S_ObjectHeading((L1Object)this.operator), true);
      } 
    } 
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    if (HpMpCheck() && ConsumeCount() && (o != null || id == this.operator.getObjectId())) {
      Character character = null;
      if (this.operator instanceof net.world.instance.PcInstance)
        L1PinkName.execute((L1Object)this.operator, o); 
      if (id == this.operator.getObjectId())
        character = this.operator; 
      if (this.operator.isInvis())
        Detection.invis((L1Object)this.operator); 
      if (!character.isLock() && Figure((L1Object)character) && character instanceof Character) {
        character.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)character, getSkill().getCastGfx()), true);
        BuffTimerInstance.getInstance().remove((L1Object)character, 32);
        BuffTimerInstance.getInstance().remove((L1Object)character, this);
        BuffTimerInstance.getInstance().add((L1Object)character, this);
      } else {
        this.operator.SendPacket((S_BasePacket)new S_ServerMessage(280));
      } 
    } 
  }
  
  public void isTimerRun(L1Object o) {
    this.counting = 0;
    o.SendPacket((S_BasePacket)new S_ServerMessage(212));
  }
  
  public void isTimer(L1Object o) {
    if (!o.isLock() && ++this.counting > 5) {
      o.setLock(true);
      o.SendPacket((S_BasePacket)new S_ObjectPoison(o.getObjectId(), o.isPoison(), o.isLock()), true);
      o.SendPacket((S_BasePacket)new S_ObjectPoison(2));
    } 
  }
  
  public void isTimerStop(L1Object o) {
    o.setLock(false);
    o.SendPacket((S_BasePacket)new S_ObjectPoison(o.getObjectId(), o.isPoison(), o.isLock()), true);
    o.SendPacket((S_BasePacket)new S_ObjectPoison(0));
  }
}
