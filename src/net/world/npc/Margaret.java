package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.instance.ShopInstance;
import net.world.kingdom.KingdomWindawood;

public class Margaret extends ShopInstance {
  public Margaret(int npc_id) {
    super(npc_id);
  }
  
  public void Talk(PcInstance pc) {
    if (pc.getLawful() < 65536) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "margaret2"));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "margaret1"));
    } 
  }
  
  public int getTax() {
    return KingdomWindawood.getInstance().getTax();
  }
  
  public void addTaxTotal(int aden) {
    KingdomWindawood.getInstance().setTaxTotal(KingdomWindawood.getInstance().getTaxTotal() + aden);
  }
}
