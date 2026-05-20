package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.world.instance.PcInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;

public class ChattingClose extends Magic {
  public ChattingClose(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(L1Object o, int id) {
    if (o != null) {
      setTime(id * 60);
      BuffTimerInstance.getInstance().remove(o, this);
      BuffTimerInstance.getInstance().add(o, this);
      o.SendPacket((S_BasePacket)new S_ServerMessage(286, String.valueOf(id)));
      this.operator.SendPacket((S_BasePacket)new S_ServerMessage(287, o.getName()));
    } else {
      this.operator.Message("사용자를 찾을 수 없습니다.");
    } 
  }
  
  public void isTimerRun(L1Object o) {
    if (o instanceof PcInstance) {
      PcInstance pc = (PcInstance)o;
      pc.setCloseChat(true);
    } 
  }
  
  public void isTimerStop(L1Object o) {
    o.SendPacket((S_BasePacket)new S_ServerMessage(288));
    if (o instanceof PcInstance) {
      PcInstance pc = (PcInstance)o;
      pc.setCloseChat(false);
    } 
  }
}
