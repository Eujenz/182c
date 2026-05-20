package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.instance.TeleportInstance;

public class Zeno extends TeleportInstance {
  public Zeno(int npcId) {
    super(npcId);
  }
  
  public void Talk(PcInstance pc) {
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "zeno"));
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("teleportURL")) {
      if (pc.getLevel() > 12) {
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "zeno1"));
      } else {
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "zeno2"));
      } 
    } else {
      ActionCheck(pc, text1);
    } 
  }
}
