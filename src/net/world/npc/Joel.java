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

public class Joel extends CraftInstance {
  private List<Craft> craft_item_1;
  
  private List<Craft> craft_item_2;
  
  private List<Craft> craft_item_3;
  
  public Joel(int npcId) {
    super(npcId);
    this.craft_item_1 = new ArrayList<Craft>();
    this.craft_item_1.add(new Craft("皮头盔", 1023, 1));
    this.craft_item_1.add(new Craft("骨头碎片", 1040, 10));
    this.craft_item_1.add(new Craft("金币", 4, 800));
    this.craft_item_2 = new ArrayList<Craft>();
    this.craft_item_2.add(new Craft("硬皮背心", 1034, 1));
    this.craft_item_2.add(new Craft("骨头碎片", 1040, 20));
    this.craft_item_2.add(new Craft("金币", 4, 500));
    this.craft_item_3 = new ArrayList<Craft>();
    this.craft_item_3.add(new Craft("银釘皮盾", 1029, 1));
    this.craft_item_3.add(new Craft("骨头碎片", 1040, 15));
    this.craft_item_3.add(new Craft("金币", 4, 800));
  }
  
  public void Talk(PcInstance pc) {
    calcheading(pc.getX(), pc.getY());
    SendPacket((S_BasePacket)new S_ObjectHeading((L1Object)this), true);
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "lien1"));
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("request skull helmet")) {
      CraftItems(pc, this.craft_item_1, 272, 1);
    } else if (text1.equalsIgnoreCase("request bone armor")) {
      CraftItems(pc, this.craft_item_2, 283, 1);
    } else if (text1.equalsIgnoreCase("request bone shield")) {
      CraftItems(pc, this.craft_item_3, 278, 1);
    } else {
      super.Talk(pc, text1, text2);
    } 
  }
}
