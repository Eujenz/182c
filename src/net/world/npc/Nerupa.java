package net.world.npc;

import java.util.ArrayList;
import java.util.List;
import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.CraftInstance;
import net.world.instance.PcInstance;
import net.world.instance.bean.Craft;

public class Nerupa extends CraftInstance {
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
  
  private List<Craft> craft_item_11;
  
  private List<Craft> craft_item_12;
  
  private List<Craft> craft_item_13;
  
  private List<Craft> craft_item_14;
  
  private List<Craft> craft_item_15;
  
  private List<Craft> craft_item_16;
  
  private List<Craft> craft_item_17;
  
  private List<Craft> craft_item_18;
  
  private List<Craft> craft_item_19;
  
  private List<Craft> craft_item_20;
  
  private List<Craft> craft_item_21;
  
  private List<Craft> craft_item_22;
  
  private List<Craft> craft_item_23;
  
  private List<Craft> craft_item_24;
  
  private List<Craft> craft_item_25;
  
  private List<Craft> craft_item_26;
  
  private List<Craft> craft_item_27;
  
  private List<Craft> craft_item_28;
  
  private List<Craft> craft_item_29;
  
  private List<Craft> craft_item_30;
  
  public Nerupa(int npcId) {
    super(npcId);
    this.craft_item_1 = new ArrayList<Craft>();
    this.craft_item_1.add(new Craft("元素石", 762, 1));
    this.craft_item_2 = new ArrayList<Craft>();
    this.craft_item_2.add(new Craft("安特的树枝", 763, 1));
    this.craft_item_3 = new ArrayList<Craft>();
    this.craft_item_3.add(new Craft("纯粹的米索莉块", 767, 1));
    this.craft_item_3.add(new Craft("安特的树枝", 763, 1));
    this.craft_item_4 = new ArrayList<Craft>();
    this.craft_item_4.add(new Craft("安特的树枝", 763, 1));
    this.craft_item_4.add(new Craft("线", 766, 5));
    this.craft_item_5 = new ArrayList<Craft>();
    this.craft_item_5.add(new Craft("安特的树枝", 763, 10));
    this.craft_item_5.add(new Craft("纯粹的米索莉块", 767, 10));
    this.craft_item_5.add(new Craft("线", 766, 2));
    this.craft_item_5.add(new Craft("芮克妮的蛻皮", 774, 2));
    this.craft_item_6 = new ArrayList<Craft>();
    this.craft_item_6.add(new Craft("精灵羽翼", 776, 8));
    this.craft_item_6.add(new Craft("奧里哈魯根金属板", 780, 3));
    this.craft_item_6.add(new Craft("米索莉线", 772, 20));
    this.craft_item_6.add(new Craft("芮克妮的网", 769, 30));
    this.craft_item_7 = new ArrayList<Craft>();
    this.craft_item_7.add(new Craft("覆上奧里哈魯根的角", 792, 1));
    this.craft_item_7.add(new Craft("奧里哈魯根金属板", 780, 6));
    this.craft_item_7.add(new Craft("米索莉线", 772, 40));
    this.craft_item_7.add(new Craft("品质绿宝石", 799515, 2));
    this.craft_item_7.add(new Craft("品质钻石", 799512, 1));
    this.craft_item_7.add(new Craft("芮克妮的蛻皮", 774, 5));
    this.craft_item_8 = new ArrayList<Craft>();
    this.craft_item_8.add(new Craft("安特的树枝", 763, 5));
    this.craft_item_8.add(new Craft("纯粹的米索莉块", 767, 20));
    this.craft_item_9 = new ArrayList<Craft>();
    this.craft_item_9.add(new Craft("覆上米索莉的角", 791, 1));
    this.craft_item_9.add(new Craft("安特的树枝", 763, 10));
    this.craft_item_9.add(new Craft("短剑的剑身", 785, 1));
    this.craft_item_9.add(new Craft("钻石", 512, 1));
    this.craft_item_9.add(new Craft("芮克妮的网", 769, 50));
    this.craft_item_10 = new ArrayList<Craft>();
    this.craft_item_10.add(new Craft("長剑的剑身", 786, 1));
    this.craft_item_10.add(new Craft("安特的树枝", 763, 5));
    this.craft_item_10.add(new Craft("纯粹的米索莉块", 767, 150));
    this.craft_item_10.add(new Craft("芮克妮的网", 769, 50));
    this.craft_item_11 = new ArrayList<Craft>();
    this.craft_item_11.add(new Craft("奧里哈魯根的剑身", 787, 1));
    this.craft_item_11.add(new Craft("精灵羽翼", 776, 2));
    this.craft_item_11.add(new Craft("奧里哈魯根", 771, 50));
    this.craft_item_11.add(new Craft("品质紅宝石", 799513, 1));
    this.craft_item_11.add(new Craft("芮克妮的网", 769, 25));
    this.craft_item_12 = new ArrayList<Craft>();
    this.craft_item_12.add(new Craft("安特的树枝", 763, 10));
    this.craft_item_12.add(new Craft("芮克妮的网", 769, 5));
    this.craft_item_13 = new ArrayList<Craft>();
    this.craft_item_13.add(new Craft("短剑的剑身", 785, 1));
    this.craft_item_13.add(new Craft("芮克妮的网", 769, 5));
    this.craft_item_13.add(new Craft("安特的树枝", 763, 10));
    this.craft_item_13.add(new Craft("纯粹的米索莉块", 767, 60));
    this.craft_item_14 = new ArrayList<Craft>();
    this.craft_item_14.add(new Craft("短剑的剑身", 785, 1));
    this.craft_item_14.add(new Craft("芮克妮的网", 769, 10));
    this.craft_item_14.add(new Craft("安特的树枝", 763, 10));
    this.craft_item_14.add(new Craft("纯粹的米索莉块", 767, 90));
    this.craft_item_15 = new ArrayList<Craft>();
    this.craft_item_15.add(new Craft("覆上米索莉的角", 791, 1));
    this.craft_item_15.add(new Craft("芮克妮的网", 769, 30));
    this.craft_item_15.add(new Craft("安特的树枝", 763, 10));
    this.craft_item_16 = new ArrayList<Craft>();
    this.craft_item_16.add(new Craft("精灵的矛", 95, 1));
    this.craft_item_16.add(new Craft("覆上奧里哈魯根的角", 792, 1));
    this.craft_item_16.add(new Craft("芮克妮的网", 769, 50));
    this.craft_item_16.add(new Craft("奧里哈魯根", 771, 60));
    this.craft_item_16.add(new Craft("品质紅宝石", 799513, 1));
    this.craft_item_17 = new ArrayList<Craft>();
    this.craft_item_17.add(new Craft("安特的树枝", 763, 10));
    this.craft_item_17.add(new Craft("线", 766, 6));
    this.craft_item_18 = new ArrayList<Craft>();
    this.craft_item_18.add(new Craft("安特的树皮", 770, 2));
    this.craft_item_18.add(new Craft("潘的鬃毛", 760, 5));
    this.craft_item_19 = new ArrayList<Craft>();
    this.craft_item_19.add(new Craft("芮克妮的蛻皮", 774, 2));
    this.craft_item_19.add(new Craft("线", 766, 10));
    this.craft_item_20 = new ArrayList<Craft>();
    this.craft_item_20.add(new Craft("米索莉金属板", 779, 4));
    this.craft_item_20.add(new Craft("米索莉线", 772, 80));
    this.craft_item_21 = new ArrayList<Craft>();
    this.craft_item_21.add(new Craft("奧里哈魯根金属板", 780, 8));
    this.craft_item_21.add(new Craft("米索莉线", 772, 20));
    this.craft_item_21.add(new Craft("高品质钻石", 800512, 1));
    this.craft_item_22 = new ArrayList<Craft>();
    this.craft_item_22.add(new Craft("安特的树皮", 770, 1));
    this.craft_item_22.add(new Craft("芮克妮的网", 769, 5));
    this.craft_item_22.add(new Craft("安特的树枝", 763, 5));
    this.craft_item_23 = new ArrayList<Craft>();
    this.craft_item_23.add(new Craft("木盾", 778, 1));
    this.craft_item_23.add(new Craft("米索莉金属板", 779, 2));
    this.craft_item_23.add(new Craft("芮克妮的网", 769, 5));
    this.craft_item_24 = new ArrayList<Craft>();
    this.craft_item_24.add(new Craft("精灵羽翼", 776, 1));
    this.craft_item_24.add(new Craft("安特的树皮", 770, 2));
    this.craft_item_24.add(new Craft("潘的鬃毛", 760, 10));
    this.craft_item_24.add(new Craft("芮克妮的网", 769, 20));
    this.craft_item_25 = new ArrayList<Craft>();
    this.craft_item_25.add(new Craft("精灵皮盔", 118, 1));
    this.craft_item_25.add(new Craft("品质钻石", 799512, 1));
    this.craft_item_25.add(new Craft("品质蓝宝石", 799514, 1));
    this.craft_item_25.add(new Craft("品质绿宝石", 799515, 1));
    this.craft_item_25.add(new Craft("魔法宝石", 508, 5));
    this.craft_item_25.add(new Craft("奧里哈魯根金属板", 780, 3));
    this.craft_item_25.add(new Craft("米索莉线", 772, 150));
    this.craft_item_26 = new ArrayList<Craft>();
    this.craft_item_26.add(new Craft("米索莉线", 772, 20));
    this.craft_item_26.add(new Craft("安特的树皮", 770, 3));
    this.craft_item_27 = new ArrayList<Craft>();
    this.craft_item_27.add(new Craft("芮克妮的蛻皮", 774, 5));
    this.craft_item_27.add(new Craft("米索莉线", 772, 20));
    this.craft_item_27.add(new Craft("食人巨魔的血", 765, 1));
    this.craft_item_27.add(new Craft("品质钻石", 799512, 1));
    this.craft_item_28 = new ArrayList<Craft>();
    this.craft_item_28.add(new Craft("魔法宝石", 508, 2));
    this.craft_item_28.add(new Craft("精灵粉末", 768, 120));
    this.craft_item_28.add(new Craft("米索莉线", 772, 10));
    this.craft_item_29 = new ArrayList<Craft>();
    this.craft_item_29.add(new Craft("安特的树皮", 770, 2));
    this.craft_item_29.add(new Craft("线", 766, 4));
    this.craft_item_30 = new ArrayList<Craft>();
    this.craft_item_30.add(new Craft("芮克尼的蛻皮", 774, 2));
    this.craft_item_30.add(new Craft("线", 766, 10));
  }
  
  public void Talk(PcInstance pc) {
    if (pc.getClassType() == 2) {
      if (pc.getLawful() < 65536) {
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "nerupaCE1"));
      } else {
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "nerupaE1"));
      } 
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "nerupaM1"));
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("request fairydust")) {
      CraftItems(pc, this.craft_item_1, 213, 20);
    } else if (text1.equalsIgnoreCase("request arrow")) {
      CraftItems(pc, this.craft_item_2, 19, 1);
    } else if (text1.equalsIgnoreCase("request mithril arrow")) {
      CraftItems(pc, this.craft_item_3, 218, 1);
    } else if (text1.equalsIgnoreCase("request bow")) {
      CraftItems(pc, this.craft_item_4, 13, 1);
    } else if (text1.equalsIgnoreCase("request elven bow")) {
      CraftItems(pc, this.craft_item_5, 22, 1);
    } else if (text1.equalsIgnoreCase("request crossbow")) {
      CraftItems(pc, this.craft_item_6, 229, 1);
    } else if (text1.equalsIgnoreCase("request yumi")) {
      CraftItems(pc, this.craft_item_7, 53, 1);
    } else if (text1.equalsIgnoreCase("request elven dagger")) {
      CraftItems(pc, this.craft_item_8, 6, 1);
    } else if (text1.equalsIgnoreCase("request mail breaker")) {
      CraftItems(pc, this.craft_item_9, 238, 1);
    } else if (text1.equalsIgnoreCase("request elven short sword")) {
      CraftItems(pc, this.craft_item_10, 49, 1);
    } else if (text1.equalsIgnoreCase("request rapier")) {
      CraftItems(pc, this.craft_item_11, 234, 1);
    } else if (text1.equalsIgnoreCase("request club")) {
      CraftItems(pc, this.craft_item_12, 27, 1);
    } else if (text1.equalsIgnoreCase("request battle axe")) {
      CraftItems(pc, this.craft_item_13, 18, 1);
    } else if (text1.equalsIgnoreCase("request guisarme")) {
      CraftItems(pc, this.craft_item_14, 34, 1);
    } else if (text1.equalsIgnoreCase("request elven spear")) {
      CraftItems(pc, this.craft_item_15, 46, 1);
    } else if (text1.equalsIgnoreCase("request fauchard")) {
      CraftItems(pc, this.craft_item_16, 31, 1);
    } else if (text1.equalsIgnoreCase("request wooden jacket")) {
      CraftItems(pc, this.craft_item_17, 243, 1);
    } else if (text1.equalsIgnoreCase("request wooden armor")) {
      CraftItems(pc, this.craft_item_18, 226, 1);
    } else if (text1.equalsIgnoreCase("request elven breast plate")) {
      CraftItems(pc, this.craft_item_19, 227, 1);
    } else if (text1.equalsIgnoreCase("request elven chain mail")) {
      CraftItems(pc, this.craft_item_20, 241, 1);
    } else if (text1.equalsIgnoreCase("request elven plate mail")) {
      CraftItems(pc, this.craft_item_21, 233, 1);
    } else if (text1.equalsIgnoreCase("request wooden shield")) {
      CraftItems(pc, this.craft_item_22, 223, 1);
    } else if (text1.equalsIgnoreCase("request elven shield")) {
      CraftItems(pc, this.craft_item_23, 92, 1);
    } else if (text1.equalsIgnoreCase("request elven leather helm")) {
      CraftItems(pc, this.craft_item_24, 58, 1);
    } else if (text1.equalsIgnoreCase("request bless of elm")) {
      CraftItems(pc, this.craft_item_25, 235, 1);
    } else if (text1.equalsIgnoreCase("request bracer")) {
      CraftItems(pc, this.craft_item_26, 220, 1);
    } else if (text1.equalsIgnoreCase("request power gloves")) {
      CraftItems(pc, this.craft_item_27, 240, 1);
    } else if (text1.equalsIgnoreCase("request elven cloak")) {
      CraftItems(pc, this.craft_item_28, 83, 1);
    } else if (text1.equalsIgnoreCase("request low boots")) {
      CraftItems(pc, this.craft_item_29, 98, 1);
    } else if (text1.equalsIgnoreCase("request boots")) {
      CraftItems(pc, this.craft_item_30, 124, 1);
    } else {
      super.Talk(pc, text1, text2);
    } 
  }
}
