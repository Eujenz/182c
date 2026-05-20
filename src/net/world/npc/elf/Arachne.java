package net.world.npc.elf;

import java.util.ArrayList;
import java.util.List;
import net.database.bean.Npc;
import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.util.Util;
import net.world.instance.PcInstance;
import net.world.instance.bean.Craft;
import net.world.object.L1Object;

public class Arachne extends ElfGuard {
  private List<Craft> craft_item_1;
  
  private List<Craft> craft_item_2;
  
  private List<Craft> craft_item_3;
  
  private List<Craft> craft_item_4;
  
  private List<Craft> craft_item_5;
  
  public Arachne(Npc n) {
    super(n);
    this.craft_item_1 = new ArrayList<Craft>();
    this.craft_item_1.add(new Craft("安特的树枝", 763, 2));
    this.craft_item_2 = new ArrayList<Craft>();
    this.craft_item_2.add(new Craft("安特的树皮", 770, 3));
    this.craft_item_3 = new ArrayList<Craft>();
    this.craft_item_3.add(new Craft("潘的鬃毛", 760, 1));
    this.craft_item_4 = new ArrayList<Craft>();
    this.craft_item_4.add(new Craft("纯粹的米索莉块", 767, 5));
    this.craft_item_4.add(new Craft("线", 766, 1));
    this.craft_item_5 = new ArrayList<Craft>();
    this.craft_item_5.add(new Craft("安特的树皮", 770, 3));
  }
  
  public void Talk(PcInstance pc) {
    super.Talk(pc);
    if (pc.getClassType() == 2) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "arachnee1"));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "arachnem1"));
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("request thread")) {
      CraftItems2(pc, this.craft_item_3, 211, 1);
    } else if (text1.equalsIgnoreCase("request mithril thread")) {
      CraftItems2(pc, this.craft_item_4, 217, 1);
    } else if (text1.equalsIgnoreCase("request ecdysis of arachne")) {
      CraftItems2(pc, this.craft_item_5, 219, 1);
    } 
  }
  
  public synchronized void toAttack(L1Object target, int type) {
    if (target instanceof PcInstance) {
      if (target.getInventory().getSlot(11) == null) {
        if (Util.rand(0, 100) < 30) {
          CraftItems((PcInstance)target, this.craft_item_1, 214, 1);
          CraftItems((PcInstance)target, this.craft_item_2, 219, 1);
        } 
      } else {
        super.toAttack(target, type);
      } 
    } else {
      super.toAttack(target, type);
    } 
  }
}
