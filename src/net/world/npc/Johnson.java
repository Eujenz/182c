package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.instance.PetShopInstance;

public class Johnson extends PetShopInstance {
  public Johnson(String html) {
    super(html);
  }
  
  public void Talk(PcInstance pc) {
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), String.valueOf(this.html) + "1", String.valueOf(80)));
  }
}
