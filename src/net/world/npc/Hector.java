package net.world.npc;

import java.util.ArrayList;
import java.util.List;
import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.CraftInstance;
import net.world.instance.PcInstance;
import net.world.instance.bean.Craft;

public class Hector extends CraftInstance {
  private List<Craft> craft_item_1;
  
  private List<Craft> craft_item_2;
  
  private List<Craft> craft_item_3;
  
  private List<Craft> craft_item_4;
  
  private List<Craft> craft_item_5;
  
  public Hector(int npcId) {
    super(npcId);
    this.craft_item_1 = new ArrayList<Craft>();
    this.craft_item_1.add(new Craft("手套", 330, 1));
    this.craft_item_1.add(new Craft("金属块", 1037, 150));
    this.craft_item_1.add(new Craft("金币", 4, 25000));
    this.craft_item_2 = new ArrayList<Craft>();
    this.craft_item_2.add(new Craft("騎士面甲", 414, 1));
    this.craft_item_2.add(new Craft("金属块", 1037, 120));
    this.craft_item_2.add(new Craft("金币", 4, 16500));
    this.craft_item_3 = new ArrayList<Craft>();
    this.craft_item_3.add(new Craft("塔盾", 429, 1));
    this.craft_item_3.add(new Craft("金属块", 1037, 200));
    this.craft_item_3.add(new Craft("金币", 4, 16000));
    this.craft_item_4 = new ArrayList<Craft>();
    this.craft_item_4.add(new Craft("长靴", 329, 1));
    this.craft_item_4.add(new Craft("金属块", 1037, 160));
    this.craft_item_4.add(new Craft("金币", 4, 8000));
    this.craft_item_5 = new ArrayList<Craft>();
    this.craft_item_5.add(new Craft("金属盔甲", 152, 1));
    this.craft_item_5.add(new Craft("金属块", 1037, 450));
    this.craft_item_5.add(new Craft("金币", 4, 30000));
  }
  
  public void Talk(PcInstance pc) {
    if (pc.getLawful() < 65536) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "hector2"));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "hector1"));
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("request iron gloves")) {
      CraftItems(pc, this.craft_item_1, 315, 1);
    } else if (text1.equalsIgnoreCase("request iron visor")) {
      CraftItems(pc, this.craft_item_2, 316, 1);
    } else if (text1.equalsIgnoreCase("request iron shield")) {
      CraftItems(pc, this.craft_item_3, 317, 1);
    } else if (text1.equalsIgnoreCase("request iron boots")) {
      CraftItems(pc, this.craft_item_4, 318, 1);
    } else {
      CraftItems(pc, this.craft_item_5, 319, 1);
    } 
  }
}
