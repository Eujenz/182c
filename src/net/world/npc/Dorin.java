package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.DwarfInstance;
import net.world.instance.PcInstance;

public class Dorin extends DwarfInstance {
  public void Talk(PcInstance pc) {
    if (CheckLev(pc.getLevel())) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "dorin"));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "dorinl"));
    } 
  }
}
