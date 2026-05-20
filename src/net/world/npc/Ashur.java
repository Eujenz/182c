package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.instance.ShopInstance;

public class Ashur extends ShopInstance {
  public Ashur(int npcId) {
    super(npcId);
  }
  
  public void Talk(PcInstance pc) {
    if (pc.getLawful() < 65536) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "ashur2"));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "ashur"));
    } 
  }
}
