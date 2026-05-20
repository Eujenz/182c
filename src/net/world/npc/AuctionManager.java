package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ObjectHeading;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.object.L1Object;

public class AuctionManager extends L1Object {
  public void Talk(PcInstance pc) {
    calcheading(pc.getX(), pc.getY());
    SendPacket((S_BasePacket)new S_ObjectHeading(this), true);
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "auction1"));
  }
}
