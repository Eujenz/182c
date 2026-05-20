package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.instance.TeleportInstance;

public class Steve extends TeleportInstance {
  public Steve(int npcId) {
    super(npcId);
  }
  
  public void Talk(PcInstance pc) {
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "telegludin1"));
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("teleportURL")) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "telegludin2", AdenCheck()));
    } else {
      ActionCheck(pc, text1);
    } 
  }
}
