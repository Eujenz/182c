package net.world.npc;

import java.util.ArrayList;
import java.util.List;
import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.CraftInstance;
import net.world.instance.PcInstance;
import net.world.instance.bean.Craft;

public class Detecter extends CraftInstance {
  private List<Craft> craft_item_1;
  
  private List<Craft> craft_item_2;
  
  private List<Craft> craft_item_3;
  
  private List<Craft> craft_item_4;
  
  private List<Craft> craft_item_5;
  
  private List<Craft> craft_item_6;
  
  private List<Craft> craft_item_7;
  
  public Detecter(int npcId) {
    super(npcId);
    this.craft_item_1 = new ArrayList<Craft>();
    this.craft_item_1.add(new Craft("受封印 被遗忘的巨剑", 19581938, 1));
    this.craft_item_1.add(new Craft("古老的卷轴", 941, 1));
    this.craft_item_2 = new ArrayList<Craft>();
    this.craft_item_2.add(new Craft("受封印 被遗忘的剑", 19581939, 1));
    this.craft_item_2.add(new Craft("古老的卷轴", 941, 1));
    this.craft_item_3 = new ArrayList<Craft>();
    this.craft_item_3.add(new Craft("受封印 被遗忘的弩枪", 19581940, 1));
    this.craft_item_3.add(new Craft("古老的卷轴", 941, 1));
    this.craft_item_4 = new ArrayList<Craft>();
    this.craft_item_4.add(new Craft("被遗忘的金属盔甲", 1933, 1));
    this.craft_item_4.add(new Craft("古老的卷轴", 941, 1));
    this.craft_item_5 = new ArrayList<Craft>();
    this.craft_item_5.add(new Craft("被遗忘的皮盔甲", 1934, 1));
    this.craft_item_5.add(new Craft("古老的卷轴", 941, 1));
    this.craft_item_6 = new ArrayList<Craft>();
    this.craft_item_6.add(new Craft("被遗忘的长袍", 1935, 1));
    this.craft_item_6.add(new Craft("古老的卷轴", 941, 1));
    this.craft_item_7 = new ArrayList<Craft>();
    this.craft_item_7.add(new Craft("被遗忘的鳞甲", 1936, 1));
    this.craft_item_7.add(new Craft("古老的卷轴", 941, 1));
  }
  
  public void Talk(PcInstance pc) {
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "detecter1"));
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("request ancient greatsword")) {
      CraftItems(pc, this.craft_item_1, 390, 1);
    } else if (text1.equalsIgnoreCase("request ancient sword")) {
      CraftItems(pc, this.craft_item_2, 391, 1);
    } else if (text1.equalsIgnoreCase("request ancient bowgun")) {
      CraftItems(pc, this.craft_item_3, 392, 1);
    } else if (text1.equalsIgnoreCase("request ancient plate mail")) {
      CraftItems(pc, this.craft_item_4, 386, 1);
    } else if (text1.equalsIgnoreCase("request ancient leather armor")) {
      CraftItems(pc, this.craft_item_5, 383, 1);
    } else if (text1.equalsIgnoreCase("request ancient robe")) {
      CraftItems(pc, this.craft_item_6, 384, 1);
    } else if (text1.equalsIgnoreCase("request ancient scale mail")) {
      CraftItems(pc, this.craft_item_7, 385, 1);
    } else {
      super.Talk(pc, text1, text2);
    } 
  }
}
