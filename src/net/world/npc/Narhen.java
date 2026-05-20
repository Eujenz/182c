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

public class Narhen extends CraftInstance {
  private List<Craft> craft_item_1;
  
  public Narhen(int npcId) {
    super(npcId);
    this.craft_item_1 = new ArrayList<Craft>();
    this.craft_item_1.add(new Craft("奧里哈魯根", 771, 10));
    this.craft_item_1.add(new Craft("安特的树皮", 770, 1));
  }
  
  public void Talk(PcInstance pc) {
    calcheading(pc.getX(), pc.getY());
    SendPacket((S_BasePacket)new S_ObjectHeading((L1Object)this));
    if (pc.getClassType() == 2) {
      if (pc.getLawful() < 65536) {
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "narhenCE1"));
      } else {
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "narhenE1"));
      } 
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "narhenM1"));
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("request magic flute"))
      CraftItems(pc, this.craft_item_1, 222, 1); 
  }
}
