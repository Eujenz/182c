package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.CraftInstance;
import net.world.instance.PcInstance;

public class Jason extends CraftInstance {
  public Jason(int npcId) {
    super(npcId);
  }
  
  public void Talk(PcInstance pc) {
    if (pc.getLawful() < 65536) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "jason2"));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "jason1"));
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {}
}
