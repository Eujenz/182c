package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.DwarfInstance;
import net.world.instance.PcInstance;

public class El extends DwarfInstance {
  public void Talk(PcInstance pc) {
    if (pc.getLawful() < 65536) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "elCE1"));
    } else if (pc.getClassType() == 2) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "elE1"));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "elM1"));
    } 
  }
}
