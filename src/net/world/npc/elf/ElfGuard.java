package net.world.npc.elf;

import java.util.ArrayList;
import java.util.List;
import net.database.ItemsTable;
import net.database.bean.Npc;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectChatting;
import net.network.server.S_ObjectHeading;
import net.network.server.S_ServerMessage;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.instance.bean.Craft;
import net.world.instance.inventory.Inventory;
import net.world.npc.Guard;
import net.world.object.Character;
import net.world.object.L1Object;

public class ElfGuard extends Guard {
  private List<ItemInstance> addItemList;
  
  private boolean process;
  
  private L1Object warning;
  
  private int RecessCount;
  
  public ElfGuard(Npc n) {
    super(n);
    setInventory(new Inventory());
    this.Areaatk = 1;
    this.addItemList = new ArrayList<ItemInstance>();
  }
  
  public void toGiveMeItem(Character cha, ItemInstance item, long count) {
    if (getInventory().insert(item, count))
      item.setCount(cha, item.getCount()); 
  }
  
  public void Talk(PcInstance pc) {
    setRecess(true);
    calcheading(pc.getX(), pc.getY());
    SendPacket((S_BasePacket)new S_ObjectHeading((L1Object)this), true);
  }
  
  public void toWalk(long time) {
    this.ai_start_time = time;
    this.ai_time = getNpc().getModespeed(getGfxMode());
    if (this.RecessCount <= 0) {
      serarchChaoticPlayer();
      searchClassPlayer();
      if (this.attackList.size() > 0)
        setFight(true); 
      RandomWalk();
    } else {
      this.RecessCount--;
      if (this.RecessCount <= 0 && this.warning != null)
        if (getObject(this.warning.getObjectId()) != null) {
          addAttackList(this.warning);
          setFight(true);
        } else {
          this.warning = null;
        }  
    } 
  }
  
  private void searchClassPlayer() {
    byte b;
    int i;
    L1Object[] arrayOfL1Object;
    for (i = (arrayOfL1Object = getObjectList()).length, b = 0; b < i; ) {
      L1Object o = arrayOfL1Object[b];
      if (o instanceof PcInstance) {
        PcInstance pc = (PcInstance)o;
        if (pc.getClassType() != 2 && !pc.isDead() && !pc.isInvis()) {
          this.warning = (L1Object)pc;
          this.RecessCount = 5;
          SendPacket((S_BasePacket)new S_ObjectChatting((L1Object)this, "$804", true), true);
        } 
      } 
      b++;
    } 
  }
  
  protected synchronized void CraftItems(PcInstance pc, List<Craft> list, int itemid, int count) {
    this.process = true;
    this.addItemList.clear();
    for (Craft c : list) {
      List<ItemInstance> item_list = getInventory().getItemNameId(c.getItemnameId());
      if (item_list != null) {
        int have_count = 0;
        for (ItemInstance item : item_list) {
          if (!item.isEquipped())
            have_count = (int)(have_count + item.getCount()); 
        } 
        if (have_count < c.getCount())
          this.process = false; 
        continue;
      } 
      this.process = false;
    } 
    if (this.process) {
      ItemInstance new_item = ItemsTable.getInstance().newItem(itemid, false, true);
      new_item.setCount(count);
      ItemInstance temp = pc.getInventory().isItem(new_item);
      if (new_item != null) {
        if (pc.getInventory().getCount() >= 180 && temp == null) {
          pc.SendPacket((S_BasePacket)new S_ServerMessage(263));
          new_item = null;
          return;
        } 
        if (!pc.getInventory().isWeight((int)(new_item.getItem().getWeight() * new_item.getCount()))) {
          pc.SendPacket((S_BasePacket)new S_ServerMessage(82));
          new_item = null;
          return;
        } 
        if (new_item.getItem().isPiles()) {
          ItemInstance list_temp = getItem(new_item.getItem().get_nameidN());
          if (list_temp == null) {
            this.addItemList.add(new_item);
          } else {
            list_temp.setCount(list_temp.getCount() + new_item.getCount());
            new_item = null;
          } 
        } else {
          this.addItemList.add(new_item);
        } 
        for (Craft c : list) {
          List<ItemInstance> item_list = getInventory().getItemNameId(c.getItemnameId());
          int cc = c.getCount();
          for (ItemInstance item : item_list) {
            if (cc <= 0)
              break; 
            if (!item.isEquipped()) {
              if (item.getCount() > cc) {
                item.setCount(item.getCount() - cc);
                cc = 0;
                continue;
              } 
              cc = (int)(cc - item.getCount());
              getInventory().remove(item);
            } 
          } 
        } 
      } 
    } 
    for (ItemInstance new_item : this.addItemList) {
      ItemInstance temp = pc.getInventory().isItem(new_item);
      if (temp == null) {
        pc.getInventory().add(new_item);
      } else {
        temp.setCount((Character)pc, temp.getCount() + new_item.getCount());
      } 
      pc.SendPacket((S_BasePacket)new S_ServerMessage(143, getName(), new_item.toString()));
    } 
  }
  
  protected synchronized void CraftItems2(PcInstance pc, List<Craft> list, int itemid, int count) {
    this.process = true;
    this.addItemList.clear();
    boolean first = true;
    do {
      for (Craft c : list) {
        List<ItemInstance> item_list = pc.getInventory().getItemNameId(c.getItemnameId());
        if (item_list != null) {
          int have_count = 0;
          for (ItemInstance item : item_list) {
            if (!item.isEquipped())
              have_count = (int)(have_count + item.getCount()); 
          } 
          if (have_count < c.getCount()) {
            if (first)
              pc.SendPacket((S_BasePacket)new S_ServerMessage(337, c.toString(c.getCount() - have_count))); 
            this.process = false;
          } 
          continue;
        } 
        if (first)
          pc.SendPacket((S_BasePacket)new S_ServerMessage(337, c.toString(c.getCount()))); 
        this.process = false;
      } 
      if (!this.process)
        continue; 
      first = false;
      ItemInstance new_item = ItemsTable.getInstance().newItem(itemid, false, true);
      new_item.setCount(count);
      ItemInstance temp = pc.getInventory().isItem(new_item);
      if (new_item == null)
        continue; 
      if (pc.getInventory().getCount() >= 180 && temp == null) {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(263));
        new_item = null;
        return;
      } 
      if (!pc.getInventory().isWeight((int)(new_item.getItem().getWeight() * new_item.getCount()))) {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(82));
        new_item = null;
        return;
      } 
      if (new_item.getItem().isPiles()) {
        ItemInstance list_temp = getItem(new_item.getItem().get_nameidN());
        if (list_temp == null) {
          this.addItemList.add(new_item);
        } else {
          list_temp.setCount(list_temp.getCount() + new_item.getCount());
          new_item = null;
        } 
      } else {
        this.addItemList.add(new_item);
      } 
      for (Craft c : list) {
        List<ItemInstance> item_list = pc.getInventory().getItemNameId(c.getItemnameId());
        int cc = c.getCount();
        for (ItemInstance item : item_list) {
          if (cc <= 0)
            break; 
          if (!item.isEquipped()) {
            if (item.getCount() > cc) {
              item.setCount((Character)pc, item.getCount() - cc);
              cc = 0;
              continue;
            } 
            cc = (int)(cc - item.getCount());
            pc.getInventory().remove(item);
          } 
        } 
      } 
    } while (this.process);
    for (ItemInstance new_item : this.addItemList) {
      ItemInstance temp = pc.getInventory().isItem(new_item);
      if (temp == null) {
        pc.getInventory().add(new_item);
      } else {
        temp.setCount((Character)pc, temp.getCount() + new_item.getCount());
      } 
      pc.SendPacket((S_BasePacket)new S_ServerMessage(143, getName(), new_item.toString()));
    } 
  }
  
  private ItemInstance getItem(int nameidN) {
    for (ItemInstance item : this.addItemList) {
      if (item.getItem().get_nameidN() == nameidN)
        return item; 
    } 
    return null;
  }
}
