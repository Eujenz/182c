package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.DwarfInstance;
import net.world.instance.PcInstance;

public class Sauram extends DwarfInstance {
  public void Talk(PcInstance pc) {
    if (CheckLev(pc.getLevel())) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "sauram"));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "sauraml"));
    } 
  }
}
