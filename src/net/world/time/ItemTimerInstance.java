package net.world.time;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import net.world.instance.ItemInstance;
import net.world.object.Character;
import net.world.time.bean.ItemTimer;

public class ItemTimerInstance extends TimerTask {
  private static Timer timer;
  
  private List<ItemTimer> list;
  
  private static class Holder {
    static ItemTimerInstance instance = new ItemTimerInstance();
  }
  
  public static ItemTimerInstance getInstance() {
    return Holder.instance;
  }
  
  private ItemTimerInstance() {
    this.list = new ArrayList<ItemTimer>();
  }
  
  public void start() {
    timer = new Timer(false);
    timer.schedule(getInstance(), 0L, 1000L);
  }
  
  public void run() {
    try {
      synchronized (this.list) {
        byte b;
        int i;
        ItemTimer[] arrayOfItemTimer;
        for (i = (arrayOfItemTimer = this.list.<ItemTimer>toArray(new ItemTimer[this.list.size()])).length, b = 0; b < i; ) {
          ItemTimer it = arrayOfItemTimer[b];
          byte b1;
          int j;
          ItemInstance[] arrayOfItemInstance;
          for (j = (arrayOfItemInstance = it.getList()).length, b1 = 0; b1 < j; ) {
            ItemInstance item = arrayOfItemInstance[b1];
            Character cha = it.getCha();
            item.isTimer(cha);
            if (!it.isTime(item))
              remove(cha, item); 
            polyMessage(cha, item);
            b1++;
          } 
          b++;
        } 
      } 
    } catch (Exception exception) {}
  }
  
  public void add(Character cha, ItemInstance item) {
    ItemTimer it = get(cha);
    if (it == null) {
      it = new ItemTimer(cha, item);
      item.isTimerRun(cha);
      this.list.add(it);
    } else if (!it.contains(item)) {
      it.add(item);
      item.isTimerRun(cha);
    } 
  }
  
  public void remove(Character cha) {
    ItemTimer it = get(cha);
    if (it != null) {
      it.clear();
      this.list.remove(it);
    } 
  }
  
  public void remove(Character cha, ItemInstance item) {
    ItemTimer it = get(cha);
    if (it != null) {
      if (!it.isTime(item))
        item.isTimerEnd(cha); 
      it.remove(item);
      if (it.getCount() <= 0) {
        it.clear();
        this.list.remove(it);
      } 
    } 
    item.isTimerStop(cha);
  }
  
  public void remove(Character cha, String nameid) {
    ItemTimer it = get(cha);
    if (it != null) {
      byte b;
      int i;
      ItemInstance[] arrayOfItemInstance;
      for (i = (arrayOfItemInstance = it.getList()).length, b = 0; b < i; ) {
        ItemInstance item = arrayOfItemInstance[b];
        if (item.getName().equalsIgnoreCase(nameid))
          remove(cha, item); 
        b++;
      } 
    } 
  }
  
  public ItemTimer get(Character cha) {
    synchronized (this.list) {
      byte b;
      int i;
      ItemTimer[] arrayOfItemTimer;
      for (i = (arrayOfItemTimer = this.list.<ItemTimer>toArray(new ItemTimer[this.list.size()])).length, b = 0; b < i; ) {
        ItemTimer it = arrayOfItemTimer[b];
        if (cha == null && it.getCha() == null)
          return it; 
        if (cha != null && it.getCha() != null && it.getCha().getObjectId() == cha.getObjectId())
          return it; 
        b++;
      } 
      return null;
    } 
  }
  
  public boolean contains(Character cha, ItemInstance item) {
    ItemTimer it = get(cha);
    if (it != null)
      return it.contains(item); 
    return false;
  }
  
  public boolean contains(Character cha, String nameid) {
    ItemTimer it = get(cha);
    if (it != null)
      return it.contains(nameid); 
    return false;
  }
  
  public void removeItem(Character cha, ItemInstance item) {
    ItemTimer it = get(cha);
    if (it != null) {
      it.remove(item);
      if (it.getCount() <= 0) {
        it.clear();
        this.list.remove(it);
      } 
    } 
  }
  
  private void polyMessage(Character cha, ItemInstance item) {
    String name = item.getName();
    if ("$260".equalsIgnoreCase(name) || "$971".equalsIgnoreCase(name)) {
      int time = item.getTime();
      boolean flag = false;
      switch (time) {
        case 1:
        case 2:
        case 4:
        case 6:
        case 8:
        case 15:
        case 30:
        case 60:
          flag = true;
          break;
      } 
      if (flag)
        sendMessage(cha, time); 
    } 
  }
  
  private void sendMessage(Character cha, int msg) {
    StringBuilder sb = new StringBuilder();
    sb.append("\\fU->>变身时间剩余：");
    sb.append(msg);
    sb.append(" 秒。");
    cha.Message(sb.toString());
  }
}
