package net.world.npc;

import java.util.ArrayList;
import java.util.List;
import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.CraftInstance;
import net.world.instance.PcInstance;
import net.world.instance.bean.Craft;

public class Ivelviin extends CraftInstance {
  private List<Craft> craft_item_1;
  
  private List<Craft> craft_item_2;
  
  private List<Craft> craft_item_3;
  
  private List<Craft> craft_item_4;
  
  private List<Craft> craft_item_5;
  
  public Ivelviin(int npcId) {
    super(npcId);
    this.craft_item_1 = new ArrayList<Craft>();
    this.craft_item_1.add(new Craft("奧里哈魯根", 771, 500));
    this.craft_item_1.add(new Craft("高品质钻石", 800512, 5));
    this.craft_item_1.add(new Craft("高品质绿宝石", 800515, 5));
    this.craft_item_1.add(new Craft("高品质蓝宝石", 800514, 5));
    this.craft_item_1.add(new Craft("高品质红宝石", 800513, 5));
    this.craft_item_1.add(new Craft("阿西塔基奧的灰烬", 1608, 30));
    this.craft_item_2 = new ArrayList<Craft>();
    this.craft_item_2.add(new Craft("火龙鳞", 145, 15));
    this.craft_item_2.add(new Craft("奧里哈魯根", 771, 1000));
    this.craft_item_2.add(new Craft("米索莉线", 772, 500));
    this.craft_item_2.add(new Craft("阿西塔基奧的灰烬", 1608, 10));
    this.craft_item_2.add(new Craft("红宝石", 800513, 5));
    this.craft_item_3 = new ArrayList<Craft>();
    this.craft_item_3.add(new Craft("水龙鳞", 149, 15));
    this.craft_item_3.add(new Craft("奧里哈魯根", 771, 1000));
    this.craft_item_3.add(new Craft("米索莉线", 772, 500));
    this.craft_item_3.add(new Craft("阿西塔基奧的灰烬", 1608, 10));
    this.craft_item_3.add(new Craft("绿宝石", 800515, 5));
    this.craft_item_4 = new ArrayList<Craft>();
    this.craft_item_4.add(new Craft("風龙鳞", 146, 15));
    this.craft_item_4.add(new Craft("奧里哈魯根", 771, 1000));
    this.craft_item_4.add(new Craft("米索莉线", 772, 500));
    this.craft_item_4.add(new Craft("阿西塔基奧的灰烬", 1608, 10));
    this.craft_item_4.add(new Craft("蓝宝石", 800514, 5));
    this.craft_item_5 = new ArrayList<Craft>();
    this.craft_item_5.add(new Craft("地龙鳞", 150, 15));
    this.craft_item_5.add(new Craft("奧里哈魯根", 771, 1000));
    this.craft_item_5.add(new Craft("米索莉线", 772, 500));
    this.craft_item_5.add(new Craft("阿西塔基奧的灰烬", 1608, 10));
    this.craft_item_5.add(new Craft("钻石", 800512, 5));
  }
  
  public void Talk(PcInstance pc) {
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "ivelviin1"));
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("request tsurugi")) {
      CraftItems(pc, this.craft_item_1, 10, 1);
    } else if (text1.equalsIgnoreCase("request red dragon armor")) {
      CraftItems(pc, this.craft_item_2, 62, 1);
    } else if (text1.equalsIgnoreCase("request blue dragon armor")) {
      CraftItems(pc, this.craft_item_3, 64, 1);
    } else if (text1.equalsIgnoreCase("request azure dragon armor")) {
      CraftItems(pc, this.craft_item_4, 63, 1);
    } else if (text1.equalsIgnoreCase("request green dragon armor")) {
      CraftItems(pc, this.craft_item_5, 65, 1);
    } else {
      super.Talk(pc, text1, text2);
    } 
  }
}
