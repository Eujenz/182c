package net.world.npc.elf;

import java.util.ArrayList;
import java.util.List;
import net.database.bean.Npc;
import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.instance.bean.Craft;
import net.world.object.L1Object;

public class Fairy extends ElfGuard {
  private List<Craft> craft_item_1;
  
  private List<Craft> craft_item_2;
  
  public Fairy(Npc n) {
    super(n);
    this.craft_item_1 = new ArrayList<Craft>();
    this.craft_item_1.add(new Craft("粗糙的米索莉块", 761, 1));
    this.craft_item_2 = new ArrayList<Craft>();
    this.craft_item_2.add(new Craft("米索莉线", 772, 5));
    this.craft_item_2.add(new Craft("精灵粉末", 768, 40));
  }
  
  public void Talk(PcInstance cha) {
    super.Talk(cha);
    if (cha.getClassType() == 2) {
      cha.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "fairye1"));
    } else {
      cha.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "fairym1"));
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("request lump of pure mithril")) {
      CraftItems2(pc, this.craft_item_1, 212, 20);
    } else if (text1.equalsIgnoreCase("request ala of fairy")) {
      CraftItems2(pc, this.craft_item_2, 221, 1);
    } else {
      super.Talk(pc, text1, text2);
    } 
  }
  
  public synchronized void toAttack(L1Object target, int type) {
    if (target instanceof PcInstance) {
      if (target.getInventory().getSlot(11) != null)
        super.toAttack(target, type); 
    } else {
      super.toAttack(target, type);
    } 
  }
}
