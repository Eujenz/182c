package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.instance.ShopInstance;

public class HarborMaster extends ShopInstance {
  public HarborMaster(int npc_id) {
    super(npc_id);
  }
  
  public void Talk(PcInstance pc) {
    if (getMap() == 0) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "shipEvI3"));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "shipEvM1"));
    } 
  }
}
