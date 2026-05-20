package net.world.npc.elf;

import java.util.ArrayList;
import java.util.List;
import net.database.bean.Npc;
import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.instance.bean.Craft;
import net.world.object.L1Object;

public class FairyQueen extends Fairy {
  private List<Craft> craft_item_1;
  
  public FairyQueen(Npc n) {
    super(n);
    this.craft_item_1 = new ArrayList<Craft>();
    this.craft_item_1.add(new Craft("纯粹的米索莉块", 767, 10));
  }
  
  public void Talk(PcInstance cha) {
    super.Talk(cha);
    if (cha.getClassType() == 2) {
      cha.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "fairyqe1"));
    } else {
      cha.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "fairyqm1"));
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("request oriharukon")) {
      CraftItems2(pc, this.craft_item_1, 216, 1);
    } else {
      super.Talk(pc, text1, text2);
    } 
  }
  
  public void toAttack(L1Object target, int type) {
    if (target.getInventory().getSlot(11) != null)
      super.toAttack(target, type); 
  }
}
