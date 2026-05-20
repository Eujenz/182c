package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.instance.ShopInstance;
import net.world.kingdom.KingdomWindawood;

public class Momo extends ShopInstance {
  public Momo(int npcId) {
    super(npcId);
  }
  
  public void Talk(PcInstance pc) {
    if (pc.getLawful() < 65536) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "Momo5"));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "Momo1"));
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("Momo6")) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), text1, KingdomWindawood.getInstance().getTax()));
    } else {
      super.Talk(pc, text1, text2);
    } 
  }
  
  public int getTax() {
    return KingdomWindawood.getInstance().getTax();
  }
  
  public void addTaxTotal(int aden) {
    KingdomWindawood.getInstance().setTaxTotal(KingdomWindawood.getInstance().getTaxTotal() + aden);
  }
}
