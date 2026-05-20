package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.instance.ShopInstance;
import net.world.kingdom.KingdomKent;

public class Verita extends ShopInstance {
  public Verita(int npc_id) {
    super(npc_id);
  }
  
  public void Talk(PcInstance pc) {
    if (pc.getLawful() < 65536) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "verita2"));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "verita"));
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("verita3")) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), text1, KingdomKent.getInstance().getTax()));
    } else {
      super.Talk(pc, text1, text2);
    } 
  }
  
  public int getTax() {
    return KingdomKent.getInstance().getTax();
  }
  
  public void addTaxTotal(int aden) {
    KingdomKent.getInstance().setTaxTotal(KingdomKent.getInstance().getTaxTotal() + aden);
  }
}
