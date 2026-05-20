package net.world.monster;

import java.util.List;
import net.database.ItemsTable;
import net.database.bean.Monster;
import net.world.instance.ItemInstance;
import net.world.instance.MonsterInstance;

public class Blob extends MonsterInstance {
  private static final int[] item_list = new int[] { -1 };
  
  public Blob(Monster m) {
    super(m);
  }
  
  public void toWalk(long time) {
    super.toWalk(time);
    if (isFight() || 
      SearchItem(item_list));
  }
  
  public void toDead() {
    CraftItem_5();
    CraftItem_4();
    CraftItem_3();
    CraftItem_2();
    CraftItem_1();
    super.toDead();
  }
  
  private void CraftItem_5() {
    int[][] need_item_list = { { 783, 4 }, { 771, 80 }, { 513, 3 } };
    int newItem_id = 237;
    List<ItemInstance> item_1 = getInventory().getItemNameId(need_item_list[0][0]);
    List<ItemInstance> item_2 = getInventory().getItemNameId(need_item_list[1][0]);
    List<ItemInstance> item_3 = getInventory().getItemNameId(need_item_list[2][0]);
    if (item_1 != null && item_1.size() > 0 && item_2 != null && item_2.size() > 0 && item_3 != null && item_3.size() > 0) {
      int item_1_count = 0;
      int item_2_count = 0;
      int item_3_count = 0;
      for (ItemInstance item : item_1)
        item_1_count = (int)(item_1_count + item.getCount()); 
      for (ItemInstance item : item_2)
        item_2_count = (int)(item_2_count + item.getCount()); 
      for (ItemInstance item : item_3)
        item_3_count = (int)(item_3_count + item.getCount()); 
      while (item_1_count >= need_item_list[0][1] && item_2_count >= need_item_list[1][1] && item_3_count >= need_item_list[2][1]) {
        item_1_count -= need_item_list[0][1];
        item_2_count -= need_item_list[1][1];
        item_3_count -= need_item_list[2][1];
        int c = need_item_list[0][1];
        while (c > 0) {
          ItemInstance itemInstance = item_1.get(0);
          c = (int)(c - itemInstance.getCount());
          itemInstance.setCount(itemInstance.getCount() - need_item_list[0][1]);
          if (itemInstance.getCount() <= 0L) {
            item_1.remove(0);
            getInventory().remove(itemInstance);
          } 
        } 
        c = need_item_list[1][1];
        while (c > 0) {
          ItemInstance itemInstance = item_2.get(0);
          c = (int)(c - itemInstance.getCount());
          itemInstance.setCount(itemInstance.getCount() - need_item_list[1][1]);
          if (itemInstance.getCount() <= 0L) {
            item_2.remove(0);
            getInventory().remove(itemInstance);
          } 
        } 
        c = need_item_list[2][1];
        while (c > 0) {
          ItemInstance itemInstance = item_3.get(0);
          c = (int)(c - itemInstance.getCount());
          itemInstance.setCount(itemInstance.getCount() - need_item_list[2][1]);
          if (itemInstance.getCount() <= 0L) {
            item_3.remove(0);
            getInventory().remove(itemInstance);
          } 
        } 
        ItemInstance item = ItemsTable.getInstance().newItem(newItem_id, false, true);
        getInventory().insert(item, item.getCount());
      } 
    } 
  }
  
  private void CraftItem_4() {
    int[][] need_item_list = { { 783, 2 }, { 767, 80 } };
    int newItem_id = 236;
    List<ItemInstance> item_1 = getInventory().getItemNameId(need_item_list[0][0]);
    List<ItemInstance> item_2 = getInventory().getItemNameId(need_item_list[1][0]);
    if (item_1 != null && item_1.size() > 0 && item_2 != null && item_2.size() > 0) {
      int item_1_count = 0;
      int item_2_count = 0;
      for (ItemInstance item : item_1)
        item_1_count = (int)(item_1_count + item.getCount()); 
      for (ItemInstance item : item_2)
        item_2_count = (int)(item_2_count + item.getCount()); 
      while (item_1_count >= need_item_list[0][1] && item_2_count >= need_item_list[1][1]) {
        item_1_count -= need_item_list[0][1];
        item_2_count -= need_item_list[1][1];
        int c = need_item_list[0][1];
        while (c > 0) {
          ItemInstance itemInstance = item_1.get(0);
          c = (int)(c - itemInstance.getCount());
          itemInstance.setCount(itemInstance.getCount() - need_item_list[0][1]);
          if (itemInstance.getCount() <= 0L) {
            item_1.remove(0);
            getInventory().remove(itemInstance);
          } 
        } 
        c = need_item_list[1][1];
        while (c > 0) {
          ItemInstance itemInstance = item_2.get(0);
          c = (int)(c - itemInstance.getCount());
          itemInstance.setCount(itemInstance.getCount() - need_item_list[1][1]);
          if (itemInstance.getCount() <= 0L) {
            item_2.remove(0);
            getInventory().remove(itemInstance);
          } 
        } 
        ItemInstance item = ItemsTable.getInstance().newItem(newItem_id, false, true);
        getInventory().insert(item, item.getCount());
      } 
    } 
  }
  
  private void CraftItem_3() {
    int[][] need_item_list = { { 776, 3 }, { 771, 150 }, { 513, 3 } };
    int newItem_id = 232;
    List<ItemInstance> item_1 = getInventory().getItemNameId(need_item_list[0][0]);
    List<ItemInstance> item_2 = getInventory().getItemNameId(need_item_list[1][0]);
    List<ItemInstance> item_3 = getInventory().getItemNameId(need_item_list[2][0]);
    if (item_1 != null && item_1.size() > 0 && item_2 != null && item_2.size() > 0 && item_3 != null && item_3.size() > 0) {
      int item_1_count = 0;
      int item_2_count = 0;
      int item_3_count = 0;
      for (ItemInstance item : item_1)
        item_1_count = (int)(item_1_count + item.getCount()); 
      for (ItemInstance item : item_2)
        item_2_count = (int)(item_2_count + item.getCount()); 
      for (ItemInstance item : item_3)
        item_3_count = (int)(item_3_count + item.getCount()); 
      while (item_1_count >= need_item_list[0][1] && item_2_count >= need_item_list[1][1] && item_3_count >= need_item_list[2][1]) {
        item_1_count -= need_item_list[0][1];
        item_2_count -= need_item_list[1][1];
        item_3_count -= need_item_list[2][1];
        int c = need_item_list[0][1];
        while (c > 0) {
          ItemInstance itemInstance = item_1.get(0);
          c = (int)(c - itemInstance.getCount());
          itemInstance.setCount(itemInstance.getCount() - need_item_list[0][1]);
          if (itemInstance.getCount() <= 0L) {
            item_1.remove(0);
            getInventory().remove(itemInstance);
          } 
        } 
        c = need_item_list[1][1];
        while (c > 0) {
          ItemInstance itemInstance = item_2.get(0);
          c = (int)(c - itemInstance.getCount());
          itemInstance.setCount(itemInstance.getCount() - need_item_list[1][1]);
          if (itemInstance.getCount() <= 0L) {
            item_2.remove(0);
            getInventory().remove(itemInstance);
          } 
        } 
        c = need_item_list[2][1];
        while (c > 0) {
          ItemInstance itemInstance = item_3.get(0);
          c = (int)(c - itemInstance.getCount());
          itemInstance.setCount(itemInstance.getCount() - need_item_list[2][1]);
          if (itemInstance.getCount() <= 0L) {
            item_3.remove(0);
            getInventory().remove(itemInstance);
          } 
        } 
        ItemInstance item = ItemsTable.getInstance().newItem(newItem_id, false, true);
        getInventory().insert(item, item.getCount());
      } 
    } 
  }
  
  private void CraftItem_2() {
    int[][] need_item_list = { { 776, 3 }, { 767, 150 } };
    int newItem_id = 231;
    List<ItemInstance> item_1 = getInventory().getItemNameId(need_item_list[0][0]);
    List<ItemInstance> item_2 = getInventory().getItemNameId(need_item_list[1][0]);
    if (item_1 != null && item_1.size() > 0 && item_2 != null && item_2.size() > 0) {
      int item_1_count = 0;
      int item_2_count = 0;
      for (ItemInstance item : item_1)
        item_1_count = (int)(item_1_count + item.getCount()); 
      for (ItemInstance item : item_2)
        item_2_count = (int)(item_2_count + item.getCount()); 
      while (item_1_count >= need_item_list[0][1] && item_2_count >= need_item_list[1][1]) {
        item_1_count -= need_item_list[0][1];
        item_2_count -= need_item_list[1][1];
        int c = need_item_list[0][1];
        while (c > 0) {
          ItemInstance itemInstance = item_1.get(0);
          c = (int)(c - itemInstance.getCount());
          itemInstance.setCount(itemInstance.getCount() - need_item_list[0][1]);
          if (itemInstance.getCount() <= 0L) {
            item_1.remove(0);
            getInventory().remove(itemInstance);
          } 
        } 
        c = need_item_list[1][1];
        while (c > 0) {
          ItemInstance itemInstance = item_2.get(0);
          c = (int)(c - itemInstance.getCount());
          itemInstance.setCount(itemInstance.getCount() - need_item_list[1][1]);
          if (itemInstance.getCount() <= 0L) {
            item_2.remove(0);
            getInventory().remove(itemInstance);
          } 
        } 
        ItemInstance item = ItemsTable.getInstance().newItem(newItem_id, false, true);
        getInventory().insert(item, item.getCount());
      } 
    } 
  }
  
  private void CraftItem_1() {
    int[][] need_item_list = { { 776, 1 }, { 767, 50 } };
    int newItem_id = 230;
    List<ItemInstance> item_1 = getInventory().getItemNameId(need_item_list[0][0]);
    List<ItemInstance> item_2 = getInventory().getItemNameId(need_item_list[1][0]);
    if (item_1 != null && item_1.size() > 0 && item_2 != null && item_2.size() > 0) {
      int item_1_count = 0;
      int item_2_count = 0;
      for (ItemInstance item : item_1)
        item_1_count = (int)(item_1_count + item.getCount()); 
      for (ItemInstance item : item_2)
        item_2_count = (int)(item_2_count + item.getCount()); 
      while (item_1_count >= need_item_list[0][1] && item_2_count >= need_item_list[1][1]) {
        item_1_count -= need_item_list[0][1];
        item_2_count -= need_item_list[1][1];
        int c = need_item_list[0][1];
        while (c > 0) {
          ItemInstance itemInstance = item_1.get(0);
          c = (int)(c - itemInstance.getCount());
          itemInstance.setCount(itemInstance.getCount() - need_item_list[0][1]);
          if (itemInstance.getCount() <= 0L) {
            item_1.remove(0);
            getInventory().remove(itemInstance);
          } 
        } 
        c = need_item_list[1][1];
        while (c > 0) {
          ItemInstance itemInstance = item_2.get(0);
          c = (int)(c - itemInstance.getCount());
          itemInstance.setCount(itemInstance.getCount() - need_item_list[1][1]);
          if (itemInstance.getCount() <= 0L) {
            item_2.remove(0);
            getInventory().remove(itemInstance);
          } 
        } 
        ItemInstance item = ItemsTable.getInstance().newItem(newItem_id, false, true);
        getInventory().insert(item, item.getCount());
      } 
    } 
  }
}
