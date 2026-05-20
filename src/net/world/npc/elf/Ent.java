package net.world.npc.elf;

import java.util.ArrayList;
import java.util.List;
import net.database.ItemsTable;
import net.database.bean.Npc;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectChatting;
import net.network.server.S_ServerMessage;
import net.network.server.S_ShowHtml;
import net.util.Util;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.instance.bean.Craft;
import net.world.object.L1Object;

public class Ent extends ElfGuard {
  private List<Craft> craft_item_1;
  
  private int collect_item_1_max;
  
  private int collect_item_2_max;
  
  private int collect_item_1;
  
  private int collect_item_2;
  
  private long collect_time;
  
  public Ent(Npc n) {
    super(n);
    this.collect_item_1_max = 50;
    this.collect_item_2_max = 1;
    this.craft_item_1 = new ArrayList<Craft>();
    this.craft_item_1.add(new Craft("蘑菇汁", 764, 1));
  }
  
  public void Talk(PcInstance cha) {
    super.Talk(cha);
    if (cha.getClassType() == 2) {
      cha.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "ente1"));
    } else {
      cha.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "entm1"));
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    super.Talk(pc, text1, text2);
  }
  
  public void toWalk(long time) {
    super.toWalk(time);
    checkCollectTime(time);
  }
  
  public void toRecess(long time) {
    super.toRecess(time);
    checkCollectTime(time);
  }
  
  public void toAttack(L1Object target, int type) {
    if (target instanceof PcInstance) {
      if (target.getInventory().getSlot(11) == null || target.getInventory().getSlot(11).getItem().get_nameidN() == 1 || 
        target.getInventory().getSlot(11).getItem().get_nameidN() == 70) {
        if (this.collect_item_1_max <= this.collect_item_1 && this.collect_item_2_max <= this.collect_item_2) {
          SendPacket((S_BasePacket)new S_ObjectChatting((L1Object)this, "$822", true), true);
        } else if (Util.rand(0, 100) < 30) {
          ItemInstance item = null;
          if (this.collect_item_1_max > this.collect_item_1) {
            CraftItems((PcInstance)target, this.craft_item_1, 215, 1);
            item = ItemsTable.getInstance().newItem(208, false, true);
            item.setCount(5L);
            target.SendPacket((S_BasePacket)new S_ServerMessage(143, getName(), item.toString()));
            target.getInventory().insert(item, item.getCount());
            this.collect_item_1 += 5;
          } 
          if (this.collect_item_2_max > this.collect_item_2 && Util.rand(0, 100) < 30) {
            item = ItemsTable.getInstance().newItem(239, false, true);
            item.setCount(1L);
            target.SendPacket((S_BasePacket)new S_ServerMessage(143, getName(), item.toString()));
            target.getInventory().insert(item, item.getCount());
            this.collect_item_2++;
          } 
        } 
      } else {
        super.toAttack(target, type);
      } 
    } else {
      super.toAttack(target, type);
    } 
  }
  
  private void checkCollectTime(long time) {
    if (this.collect_item_1_max <= this.collect_item_1 && this.collect_item_2_max <= this.collect_item_2) {
      if (this.collect_time == 0L)
        this.collect_time = System.currentTimeMillis(); 
      if (time - this.collect_time >= 600000L)
        CollectClean(); 
    } 
  }
  
  private void CollectClean() {
    this.collect_time = 0L;
    this.collect_item_1 = 0;
    this.collect_item_2 = 0;
  }
}
