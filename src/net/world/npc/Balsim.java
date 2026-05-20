package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.instance.ShopInstance;
import net.world.kingdom.KingdomWindawood;

public class Balsim extends ShopInstance {
  public Balsim(int npc_id) {
    super(npc_id);
  }
  
  public void Talk(PcInstance pc) {
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "balsim"));
  }
  
  public int getTax() {
    return KingdomWindawood.getInstance().getTax();
  }
  
  public void addTaxTotal(int aden) {
    KingdomWindawood.getInstance().setTaxTotal(KingdomWindawood.getInstance().getTaxTotal() + aden);
  }
}
