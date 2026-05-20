package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.DwarfInstance;
import net.world.instance.PcInstance;
import net.world.kingdom.KingdomKent;

public class Bahof extends DwarfInstance {
  public void Talk(PcInstance pc) {
    if (KingdomKent.getInstance().getClanID() == pc.getClanId()) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "bahof"));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "bahofop"));
    } 
  }
}
