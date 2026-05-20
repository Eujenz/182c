package net.world.npc;

import java.util.ArrayList;
import java.util.List;
import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.CraftInstance;
import net.world.instance.PcInstance;
import net.world.instance.bean.Craft;

public class Moria extends CraftInstance {
  private List<Craft> craft_item_1;
  
  private List<Craft> craft_item_2;
  
  public Moria(int npcId) {
    super(npcId);
    this.craft_item_1 = new ArrayList<Craft>();
    this.craft_item_1.add(new Craft("高品質 藍寶石", 800514, 1));
    this.craft_item_1.add(new Craft("魔法寶石", 508, 25));
    this.craft_item_1.add(new Craft("白色布料", 1200, 2));
    this.craft_item_1.add(new Craft("藍色布料", 1197, 4));
    this.craft_item_2 = new ArrayList<Craft>();
    this.craft_item_2.add(new Craft("品質 綠寶石", 799515, 2));
    this.craft_item_2.add(new Craft("魔法寶石", 508, 20));
    this.craft_item_2.add(new Craft("白色布料", 1200, 1));
    this.craft_item_2.add(new Craft("紅色布料", 1199, 1));
    this.craft_item_2.add(new Craft("藍色布料", 1197, 1));
  }
  
  public void Talk(PcInstance pc) {
    if (pc.getClassType() != 3) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "moria4"));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "moria1"));
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("request magician dress")) {
      CraftItems(pc, this.craft_item_1, 314, 1);
    } else if (text1.equalsIgnoreCase("request magician cap")) {
      CraftItems(pc, this.craft_item_2, 313, 1);
    } else {
      super.Talk(pc, text1, text2);
    } 
  }
}
