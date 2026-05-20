package net.world.instance;

import java.util.ArrayList;
import java.util.List;
import net.database.ItemsTable;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.world.instance.bean.Craft;

public class CraftInstance extends ShopInstance {
  private List<ItemInstance> addItemList;
  
  private boolean process;
  
  public CraftInstance(int npcId) {
    super(npcId);
    this.addItemList = new ArrayList<ItemInstance>();
  }
  
  protected synchronized void CraftItems(PcInstance pc, List<Craft> list, int itemid, int count) {
    this.process = true;
    this.addItemList.clear();
    boolean first = true;
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
    if (this.process) {
      first = false;
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
          List<ItemInstance> item_list = pc.getInventory().getItemNameId(c.getItemnameId());
          int cc = c.getCount();
          for (ItemInstance item : item_list) {
            if (cc <= 0)
              break; 
            if (!item.isEquipped()) {
              if (item.getCount() > cc) {
                item.setCount(pc, item.getCount() - cc);
                cc = 0;
                continue;
              } 
              cc = (int)(cc - item.getCount());
              item.setCount(pc, 0L);
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
        temp.setCount(pc, temp.getCount() + new_item.getCount());
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
