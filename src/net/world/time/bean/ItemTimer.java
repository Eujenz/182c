package net.world.time.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.world.instance.ItemInstance;
import net.world.object.Character;

public class ItemTimer {
  private Character cha;
  
  private List<ItemInstance> list;
  
  private Map<Integer, Integer> time;
  
  public ItemTimer(Character cha, ItemInstance item) {
    this.list = new ArrayList<ItemInstance>();
    this.time = new HashMap<Integer, Integer>();
    this.cha = cha;
    add(item);
  }
  
  public Character getCha() {
    return this.cha;
  }
  
  public void setCha(Character cha) {
    this.cha = cha;
  }
  
  public void add(ItemInstance item) {
    synchronized (this.list) {
      if (!this.list.contains(item)) {
        this.list.add(item);
        if (item.getInvID() == 0) {
          this.time.put(Integer.valueOf(item.getItem().getItemId()), Integer.valueOf(item.getTime()));
        } else {
          this.time.put(Integer.valueOf(item.getItem().getItemId()), Integer.valueOf(item.getFirstTime()));
        } 
      } 
    } 
  }
  
  public void remove(ItemInstance item) {
    synchronized (this.list) {
      this.list.remove(item);
      Integer t = this.time.get(Integer.valueOf(item.getItem().getItemId()));
      if (t != null) {
        item.setTime(t.intValue());
        this.time.remove(Integer.valueOf(item.getItem().getItemId()));
      } 
    } 
  }
  
  public boolean contains(ItemInstance item) {
    synchronized (this.list) {
      return this.list.contains(item);
    } 
  }
  
  public boolean contains(String nameid) {
    byte b;
    int i;
    ItemInstance[] arrayOfItemInstance;
    for (i = (arrayOfItemInstance = getList()).length, b = 0; b < i; ) {
      ItemInstance item = arrayOfItemInstance[b];
      if (item.getName().equalsIgnoreCase(nameid))
        return true; 
      b++;
    } 
    return false;
  }
  
  public ItemInstance[] getList() {
    synchronized (this.list) {
      return this.list.<ItemInstance>toArray(new ItemInstance[this.list.size()]);
    } 
  }
  
  public int getCount() {
    return this.list.size();
  }
  
  public boolean isTime(ItemInstance item) {
    synchronized (this.list) {
      Integer t = this.time.get(Integer.valueOf(item.getItem().getItemId()));
      if (t != null) {
        t = Integer.valueOf(t.intValue() - 1);
        item.setTime(t.intValue());
        this.time.put(Integer.valueOf(item.getItem().getItemId()), t);
        return (t.intValue() > 0);
      } 
      return false;
    } 
  }
  
  public void clear() {
    byte b;
    int i;
    ItemInstance[] arrayOfItemInstance;
    for (i = (arrayOfItemInstance = getList()).length, b = 0; b < i; ) {
      ItemInstance item = arrayOfItemInstance[b];
      item.isTimerStop(getCha());
      Integer t = this.time.get(Integer.valueOf(item.getItem().getItemId()));
      if (t != null)
        item.setTime(t.intValue()); 
      b++;
    } 
    this.time.clear();
    this.list.clear();
    this.list = null;
    this.time = null;
    this.cha = null;
  }
}
