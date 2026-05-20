package net.world.npc;

import java.util.ArrayList;
import java.util.List;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectHeading;
import net.network.server.S_ShowHtml;
import net.world.instance.CraftInstance;
import net.world.instance.PcInstance;
import net.world.instance.bean.Craft;
import net.world.object.L1Object;

public class Pin extends CraftInstance {
  private List<Craft> craft_item_1;
  
  private List<Craft> craft_item_2;
  
  private List<Craft> craft_item_3;
  
  private List<Craft> craft_item_4;
  
  public Pin(int npcId) {
    super(npcId);
    this.craft_item_1 = new ArrayList<Craft>();
    this.craft_item_1.add(new Craft("皮帽子", 1021, 1));
    this.craft_item_1.add(new Craft("金属块", 1037, 10));
    this.craft_item_1.add(new Craft("高级皮革", 1039, 2));
    this.craft_item_2 = new ArrayList<Craft>();
    this.craft_item_2.add(new Craft("皮涼鞋", 1025, 1));
    this.craft_item_2.add(new Craft("金属块", 1037, 11));
    this.craft_item_2.add(new Craft("高级皮革", 1039, 3));
    this.craft_item_3 = new ArrayList<Craft>();
    this.craft_item_3.add(new Craft("皮背心", 1031, 1));
    this.craft_item_3.add(new Craft("金属块", 1037, 10));
    this.craft_item_3.add(new Craft("高级皮革", 1039, 2));
    this.craft_item_4 = new ArrayList<Craft>();
    this.craft_item_4.add(new Craft("皮盾牌", 1028, 1));
    this.craft_item_4.add(new Craft("金属块", 1037, 20));
    this.craft_item_4.add(new Craft("高级皮革", 1039, 5));
  }
  
  public void Talk(PcInstance pc) {
    calcheading(pc.getX(), pc.getY());
    SendPacket((S_BasePacket)new S_ObjectHeading((L1Object)this));
    if (pc.getLawful() < 65536) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "farlinC1"));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "farlin1"));
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("request studded leather cap")) {
      CraftItems(pc, this.craft_item_1, 270, 1);
    } else if (text1.equalsIgnoreCase("request studded leather sandal")) {
      CraftItems(pc, this.craft_item_2, 274, 1);
    } else if (text1.equalsIgnoreCase("request studded leather vest")) {
      CraftItems(pc, this.craft_item_3, 281, 1);
    } else if (text1.equalsIgnoreCase("request studded leather shield")) {
      CraftItems(pc, this.craft_item_4, 277, 1);
    } else {
      super.Talk(pc, text1, text2);
    } 
  }
}
