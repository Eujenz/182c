package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ObjectLight;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;
import net.world.time.ItemTimerInstance;

public class Light extends Magic {
  public Light(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    if (!this.gm_buff)
      this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true); 
    if (HpMpCheck() && ConsumeCount()) {
      this.operator.SendPacket((S_BasePacket)new S_ObjectEffect(145), true);
      BuffTimerInstance.getInstance().remove((L1Object)this.operator, this);
      BuffTimerInstance.getInstance().add((L1Object)this.operator, this);
    } 
  }
  
  public void isTimerRun(L1Object o) {
    o.setLight(getSkill().getMaxdmg());
    o.SendPacket((S_BasePacket)new S_ObjectLight(o), true);
  }
  
  public void isTimerStop(L1Object o) {
    if (o.getLight() <= getSkill().getMaxdmg()) {
      if (ItemTimerInstance.getInstance().contains((Character)o, "$326 ")) {
        o.setLight(12);
      } else if (ItemTimerInstance.getInstance().contains((Character)o, "$2 ")) {
        o.setLight(10);
      } else if (ItemTimerInstance.getInstance().contains((Character)o, "$67 ")) {
        o.setLight(8);
      } else {
        o.setLight(0);
      } 
      o.SendPacket((S_BasePacket)new S_ObjectLight(o), true);
    } 
  }
}
