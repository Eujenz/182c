package net.world.instance.inventory.function;

import net.database.ItemsTable;
import net.database.SkillTable;
import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.instance.inventory.Inventory;
import net.world.object.Character;

public class EmptyScroll extends ItemInstance {
  public EmptyScroll(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    PcInstance pc = (PcInstance)cha;
    boolean test = false;
    if (test) {
      pc.Message(" 封包:" + bp.readD());
      return;
    } 
    int skillid = bp.readD();
    if (skillid == -1)
      return; 
    if (SkillTable.getInstance().getTemplate((Character)pc, skillid + 1) == null)
      return; 
    int itemId = getItem().getItemId();
    Inventory inv = pc.getInventory();
    ItemInstance useitem = null;
    ItemInstance temp = null;
    ItemInstance new_item = null;
    switch (itemId) {
      case 501:
        if (skillid >= 0 && skillid <= 7) {
          new_item = ItemsTable.getInstance().newItem(600 + skillid, true, true);
          new_item.setCount(1L);
          temp = pc.getInventory().isItem(new_item);
          break;
        } 
        pc.Message("空的魔法卷軸級別不足。");
        return;
      case 502:
        if (skillid >= 0 && skillid <= 15) {
          new_item = ItemsTable.getInstance().newItem(600 + skillid, true, true);
          new_item.setCount(1L);
          temp = pc.getInventory().isItem(new_item);
        } else {
          pc.Message("空的魔法卷軸級別不足。");
          return;
        } 
      case 503:
        if (skillid >= 0 && skillid <= 23) {
          new_item = ItemsTable.getInstance().newItem(600 + skillid, true, true);
          new_item.setCount(1L);
          temp = pc.getInventory().isItem(new_item);
        } else {
          pc.Message("空的魔法卷軸級別不足。");
          return;
        } 
      case 504:
        if (skillid >= 0 && skillid <= 31) {
          new_item = ItemsTable.getInstance().newItem(600 + skillid, true, true);
          new_item.setCount(1L);
          temp = pc.getInventory().isItem(new_item);
        } else {
          pc.Message("空的魔法卷軸級別不足。");
          return;
        } 
      case 505:
        if (skillid >= 0 && skillid <= 39) {
          new_item = ItemsTable.getInstance().newItem(600 + skillid, true, true);
          new_item.setCount(1L);
          temp = pc.getInventory().isItem(new_item);
          break;
        } 
        pc.Message("空的魔法卷軸級別不足。");
        return;
    } 
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
      if (getCount() == 1L) {
        pc.getInventory().remove(this);
      } else {
        setCount((Character)pc, getCount() - 1L);
      } 
      if (temp == null) {
        pc.getInventory().add(new_item);
      } else {
        temp.setCount((Character)pc, temp.getCount() + 1L);
      } 
      pc.Message("獲得 " + new_item.toString());
    } 
  }
}
