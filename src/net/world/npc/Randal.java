package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.instance.ShopInstance;
import net.world.kingdom.KingdomKent;

public class Randal extends ShopInstance {
  public Randal(int npc_id) {
    super(npc_id);
  }
  
  public void Talk(PcInstance pc) {
    if (pc.getLawful() < 65536) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "randal2"));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "randal1"));
    } 
  }
  
  public int getTax() {
    return KingdomKent.getInstance().getTax();
  }
  
  public void addTaxTotal(int aden) {
    KingdomKent.getInstance().setTaxTotal(KingdomKent.getInstance().getTaxTotal() + aden);
  }
}
