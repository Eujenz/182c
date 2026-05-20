package net.world.npc;

import net.database.bean.Npc;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectHeading;
import net.network.server.S_ShowHtml;
import net.world.instance.NpcInstance;
import net.world.instance.PcInstance;
import net.world.object.L1Object;

public class Lengo extends NpcInstance {
  public Lengo(Npc npc) {
    super(npc);
  }
  
  public void Talk(PcInstance pc) {
    if (pc == null)
      return; 
    setRecess(true);
    calcheading(pc.getX(), pc.getY());
    SendPacket((S_BasePacket)new S_ObjectHeading((L1Object)this), true);
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "Lengo1"));
  }
}
