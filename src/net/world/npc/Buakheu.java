package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.instance.ShopInstance;

public class Buakheu extends ShopInstance {
  public Buakheu(int npcId) {
    super(npcId);
  }
  
  public void Talk(PcInstance pc) {
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "buakheu1"));
  }
}
