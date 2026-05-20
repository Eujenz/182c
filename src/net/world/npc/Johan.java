package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.instance.ShopInstance;

public class Johan extends ShopInstance {
  public Johan(int npc_id) {
    super(npc_id);
  }
  
  public void Talk(PcInstance pc) {
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "johan"));
  }
}
