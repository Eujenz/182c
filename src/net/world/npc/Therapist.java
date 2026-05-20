package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.object.L1Object;

public final class Therapist extends L1Object {
  public void Talk(PcInstance pc) {
    if (pc == null)
      return; 
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "treno"));
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (pc == null)
      return; 
    if (text1.equalsIgnoreCase("fullheal")) {
      pc.setCurrentHp(pc.getMaxHp());
      pc.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)pc, 744), true);
    } 
  }
}
