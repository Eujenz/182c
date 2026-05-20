package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.instance.ShopInstance;
import net.world.kingdom.KingdomAbyss;

public final class Bius extends ShopInstance {
  public Bius(int npcId) {
    super(npcId);
  }
  
  public void Talk(PcInstance pc) {
    if (pc.getLawful() < 65536) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "bius2"));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "bius"));
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("bius3")) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), text1, KingdomAbyss.getInstance().getTax()));
    } else {
      super.Talk(pc, text1, text2);
    } 
  }
  
  public int getTax() {
    return KingdomAbyss.getInstance().getTax();
  }
  
  public void addTaxTotal(int aden) {
    KingdomAbyss.getInstance().setTaxTotal(KingdomAbyss.getInstance().getTaxTotal() + aden);
  }
}
