package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.DwarfInstance;
import net.world.instance.PcInstance;
import net.world.kingdom.KingdomWindawood;

public class Borgin extends DwarfInstance {
  public void Talk(PcInstance pc) {
    if (KingdomWindawood.getInstance().getClanID() == pc.getClanId()) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "borgin"));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "borginop"));
    } 
  }
}
