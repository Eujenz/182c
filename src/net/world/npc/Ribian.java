package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.instance.TeleportInstance;

public class Ribian extends TeleportInstance {
  public Ribian(int npcId) {
    super(npcId);
  }
  
  public void Talk(PcInstance pc) {
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "ribian3"));
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("teleportURL")) {
      if (pc.getLevel() > 12) {
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), ""));
      } else {
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "ribian4"));
      } 
    } else {
      ActionCheck(pc, text1);
    } 
  }
}
