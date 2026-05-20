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

public class Julie extends CraftInstance {
  private List<Craft> craft_item_1;
  
  private List<Craft> craft_item_2;
  
  private List<Craft> craft_item_3;
  
  private List<Craft> craft_item_4;
  
  private List<Craft> craft_item_5;
  
  private List<Craft> craft_item_6;
  
  private List<Craft> craft_item_7;
  
  private List<Craft> craft_item_8;
  
  private List<Craft> craft_item_9;
  
  private List<Craft> craft_item_10;
  
  public Julie(int npcId) {
    super(npcId);
    this.craft_item_1 = new ArrayList<Craft>();
    this.craft_item_1.add(new Craft("皮革", 1036, 20));
    this.craft_item_2 = new ArrayList<Craft>();
    this.craft_item_2.add(new Craft("皮革", 1036, 5));
    this.craft_item_2.add(new Craft("金属块", 1037, 1));
    this.craft_item_3 = new ArrayList<Craft>();
    this.craft_item_3.add(new Craft("皮革", 1036, 6));
    this.craft_item_3.add(new Craft("金属块", 1037, 2));
    this.craft_item_4 = new ArrayList<Craft>();
    this.craft_item_4.add(new Craft("皮革", 1036, 10));
    this.craft_item_5 = new ArrayList<Craft>();
    this.craft_item_5.add(new Craft("皮革", 1036, 7));
    this.craft_item_6 = new ArrayList<Craft>();
    this.craft_item_6.add(new Craft("皮涼鞋", 1025, 1));
    this.craft_item_6.add(new Craft("高级皮革", 1039, 10));
    this.craft_item_6.add(new Craft("皮革", 1036, 10));
    this.craft_item_6.add(new Craft("金幣", 4, 300));
    this.craft_item_7 = new ArrayList<Craft>();
    this.craft_item_7.add(new Craft("钢盔", 128, 1));
    this.craft_item_7.add(new Craft("皮帽子", 1021, 1));
    this.craft_item_7.add(new Craft("高级皮革", 1039, 15));
    this.craft_item_7.add(new Craft("金属块", 1037, 15));
    this.craft_item_8 = new ArrayList<Craft>();
    this.craft_item_8.add(new Craft("皮背心", 1031, 1));
    this.craft_item_8.add(new Craft("高级皮革", 1039, 15));
    this.craft_item_8.add(new Craft("金属块", 1037, 15));
    this.craft_item_9 = new ArrayList<Craft>();
    this.craft_item_9.add(new Craft("皮背心", 1031, 1));
    this.craft_item_9.add(new Craft("皮带", 1038, 1));
    this.craft_item_10 = new ArrayList<Craft>();
    this.craft_item_10.add(new Craft("高级皮革", 1039, 5));
    this.craft_item_10.add(new Craft("金属块", 1037, 2));
  }
  
  public void Talk(PcInstance pc) {
    calcheading(pc.getX(), pc.getY());
    SendPacket((S_BasePacket)new S_ObjectHeading((L1Object)this), true);
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "ladar1"));
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("request hard leather")) {
      CraftItems(pc, this.craft_item_1, 287, 1);
    } else if (text1.equalsIgnoreCase("request leather cap")) {
      CraftItems(pc, this.craft_item_2, 269, 1);
    } else if (text1.equalsIgnoreCase("request leather sandal")) {
      CraftItems(pc, this.craft_item_3, 273, 1);
    } else if (text1.equalsIgnoreCase("request leather vest")) {
      CraftItems(pc, this.craft_item_4, 279, 1);
    } else if (text1.equalsIgnoreCase("request leather shield")) {
      CraftItems(pc, this.craft_item_5, 276, 1);
    } else if (text1.equalsIgnoreCase("request leather boots")) {
      CraftItems(pc, this.craft_item_6, 275, 1);
    } else if (text1.equalsIgnoreCase("request leather helmet")) {
      CraftItems(pc, this.craft_item_7, 271, 1);
    } else if (text1.equalsIgnoreCase("request hard leather vest")) {
      CraftItems(pc, this.craft_item_8, 282, 1);
    } else if (text1.equalsIgnoreCase("request leather vest with belt")) {
      CraftItems(pc, this.craft_item_9, 280, 1);
    } else if (text1.equalsIgnoreCase("request belt")) {
      CraftItems(pc, this.craft_item_10, 286, 1);
    } else {
      super.Talk(pc, text1, text2);
    } 
  }
}
