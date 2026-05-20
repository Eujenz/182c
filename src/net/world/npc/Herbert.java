package net.world.npc;

import java.util.ArrayList;
import java.util.List;
import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.CraftInstance;
import net.world.instance.PcInstance;
import net.world.instance.bean.Craft;

public class Herbert extends CraftInstance {
  private List<Craft> craft_item_1;
  
  private List<Craft> craft_item_2;
  
  private List<Craft> craft_item_3;
  
  public Herbert(int npcId) {
    super(npcId);
    this.craft_item_1 = new ArrayList<Craft>();
    this.craft_item_1.add(new Craft("红色布料", 1199, 3));
    this.craft_item_1.add(new Craft("蓝色布料", 1197, 2));
    this.craft_item_1.add(new Craft("白色布料", 1200, 10));
    this.craft_item_1.add(new Craft("金币", 4, 30000));
    this.craft_item_2 = new ArrayList<Craft>();
    this.craft_item_2.add(new Craft("红色布料", 1199, 10));
    this.craft_item_2.add(new Craft("蓝色布料", 1197, 2));
    this.craft_item_2.add(new Craft("白色布料", 1200, 1));
    this.craft_item_2.add(new Craft("金币", 4, 1000));
    this.craft_item_3 = new ArrayList<Craft>();
    this.craft_item_3.add(new Craft("红色布料", 1199, 5));
    this.craft_item_3.add(new Craft("蓝色布料", 1197, 5));
    this.craft_item_3.add(new Craft("白色布料", 1200, 10));
    this.craft_item_3.add(new Craft("金币", 4, 20000));
  }
  
  public void Talk(PcInstance pc) {
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "herbert1"));
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("request t-shirt")) {
      CraftItems(pc, this.craft_item_1, 82, 1);
    } else if (text1.equalsIgnoreCase("request cloak of magic resistance")) {
      CraftItems(pc, this.craft_item_2, 89, 1);
    } else if (text1.equalsIgnoreCase("request cloak of protection")) {
      CraftItems(pc, this.craft_item_3, 87, 1);
    } else {
      super.Talk(pc, text1, text2);
    } 
  }
}
