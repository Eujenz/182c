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
import net.world.object.Character;
import net.world.object.L1Object;

public class Pan extends ElfGuard {
  private List<Craft> craft_item_1;
  
  private List<Craft> craft_item_2;
  
  private List<Craft> craft_item_3;
  
  private int collect_item_1_max;
  
  private int collect_item_1;
  
  private long collect_time;
  
  private Character magicflute;
  
  public Pan(Npc n) {
    super(n);
    this.collect_item_1_max = 60;
    this.craft_item_1 = new ArrayList<Craft>();
    this.craft_item_1.add(new Craft("纯粹的米索莉块", 767, 50));
    this.craft_item_1.add(new Craft("芮克妮的蛻皮", 774, 1));
    this.craft_item_2 = new ArrayList<Craft>();
    this.craft_item_2.add(new Craft("奧里哈鲁根", 771, 30));
    this.craft_item_2.add(new Craft("芮克妮的蛻皮", 774, 1));
    this.craft_item_3 = new ArrayList<Craft>();
    this.craft_item_3.add(new Craft("魔法笛子", 777, 1));
  }
  
  public void Talk(PcInstance cha) {
    super.Talk(cha);
    if (cha.getClassType() == 2) {
      cha.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "pane1"));
    } else {
      cha.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "panm1"));
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("request mithril plate")) {
      CraftItems2(pc, this.craft_item_1, 224, 1);
    } else if (text1.equalsIgnoreCase("request oriharukon plate")) {
      CraftItems2(pc, this.craft_item_2, 225, 1);
    } else if (text1.equalsIgnoreCase("request pan's horn")) {
      CraftItems2(pc, this.craft_item_3, 228, 1);
    } else {
      super.Talk(pc, text1, text2);
    } 
  }
  
  public void toWalk(long time) {
    if (!isMagicFlute(time))
      super.toWalk(time); 
    checkCollectTime(time);
  }
  
  public void toRecess(long time) {
    if (!isMagicFlute(time))
      super.toRecess(time); 
    checkCollectTime(time);
  }
  
  public synchronized void toAttack(L1Object target, int type) {
    if (target instanceof PcInstance) {
      if (target.getInventory().getSlot(11) == null || target.getInventory().getSlot(11).getItem().get_nameidN() == 1 || 
        target.getInventory().getSlot(11).getItem().get_nameidN() == 70) {
        if (this.collect_item_1_max <= this.collect_item_1) {
          SendPacket((S_BasePacket)new S_ObjectChatting((L1Object)this, "$824", true), true);
        } else if (Util.rand(0, 100) < 30) {
          ItemInstance item = ItemsTable.getInstance().newItem(205, false, true);
          item.setCount(6L);
          target.SendPacket((S_BasePacket)new S_ServerMessage(143, getName(), item.toString()));
          target.getInventory().insert(item, item.getCount());
          this.collect_item_1 += 6;
        } 
      } else {
        super.toAttack(target, type);
      } 
    } else {
      super.toAttack(target, type);
    } 
  }
  
  private void checkCollectTime(long time) {
    if (this.collect_item_1_max <= this.collect_item_1) {
      if (this.collect_time == 0L)
        this.collect_time = System.currentTimeMillis(); 
      if (time - this.collect_time >= 600000L)
        CollectClean(); 
    } 
  }
  
  private void CollectClean() {
    this.collect_time = 0L;
    this.collect_item_1 = 0;
  }
  
  private boolean isMagicFlute(long time) {
    if (getMagicflute() != null && !getMagicflute().isDead() && !getMagicflute().isDelete() && !getMagicflute().isInvis()) {
      this.ai_start_time = time;
      this.ai_time = getNpc().getModespeed(getGfxMode());
      if (getDistance(getMagicflute().getX(), getMagicflute().getY(), getMagicflute().getMap(), this.Areaatk)) {
        getMagicflute().SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "panEv1"));
        setRecess(true);
        setMagicflute((Character)null);
      } else {
        StartMove(getMagicflute().getX(), getMagicflute().getY());
      } 
      return true;
    } 
    setMagicflute((Character)null);
    return false;
  }
  
  public Character getMagicflute() {
    return this.magicflute;
  }
  
  public void setMagicflute(Character magicflute) {
    this.magicflute = magicflute;
  }
}
