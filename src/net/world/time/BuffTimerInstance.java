package net.world.time;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import net.world.instance.ItemInstance;
import net.world.instance.skill.Magic;
import net.world.object.L1Object;
import net.world.time.bean.BuffTimer;

public class BuffTimerInstance extends TimerTask {
  private static Timer timer;
  
  private List<BuffTimer> list;
  
  private static class Holder {
    static BuffTimerInstance instance = new BuffTimerInstance();
  }
  
  public static BuffTimerInstance getInstance() {
    return Holder.instance;
  }
  
  private BuffTimerInstance() {
    this.list = new ArrayList<BuffTimer>();
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
        BuffTimer[] arrayOfBuffTimer;
        for (i = (arrayOfBuffTimer = this.list.<BuffTimer>toArray(new BuffTimer[this.list.size()])).length, b = 0; b < i; ) {
          BuffTimer bt = arrayOfBuffTimer[b];
          byte b1;
          int j;
          Magic[] arrayOfMagic;
          for (j = (arrayOfMagic = bt.getList()).length, b1 = 0; b1 < j; ) {
            Magic m = arrayOfMagic[b1];
            m.isTimer(bt.getObject());
            if (bt.getTime(m) <= 0)
              remove(bt.getObject(), m); 
            b1++;
          } 
          b++;
        } 
      } 
    } catch (Exception exception) {}
  }
  
  public void add(L1Object o, Magic m) {
    BuffTimer bt = get(o);
    if (bt == null) {
      bt = new BuffTimer(o, m);
      this.list.add(bt);
      m.isTimerRun(o);
    } else if (!bt.contains(m)) {
      bt.add(m);
      m.isTimerRun(o);
    } 
  }
  
  public void remove(L1Object o) {
    BuffTimer bt = get(o);
    if (bt != null)
      if (o.isCloseChat()) {
        byte b;
        int i;
        Magic[] arrayOfMagic;
        for (i = (arrayOfMagic = bt.getList()).length, b = 0; b < i; ) {
          Magic m = arrayOfMagic[b];
          if (!(m instanceof net.world.instance.skill.function.ChattingClose))
            m.isTimerStop(bt.getObject()); 
          b++;
        } 
      } else {
        bt.clear();
        this.list.remove(bt);
      }  
    if (o instanceof net.world.object.Character && o.getInventory() != null) {
      byte b;
      int i;
      ItemInstance[] arrayOfItemInstance;
      for (i = (arrayOfItemInstance = o.getInventory().getAll()).length, b = 0; b < i; ) {
        ItemInstance item = arrayOfItemInstance[b];
        remove((L1Object)item);
        b++;
      } 
    } 
  }
  
  public void remove(L1Object o, Magic m) {
    try {
      BuffTimer bt = get(o);
      if (bt != null) {
        if (bt.contains(m)) {
          if (bt.getTime(m) <= 0 && !o.isDelete())
            m.isTimerEnd(o); 
          if (!o.isDelete())
            m.isTimerStop(o); 
          bt.remove(m);
        } 
        if (bt.getCount() <= 0) {
          bt.clear();
          this.list.remove(bt);
        } 
      } 
    } catch (Exception e) {
      remove(o, m.getSkill().getSkill_id());
    } 
  }
  
  public void remove(L1Object o, int skillID) {
    BuffTimer bt = get(o);
    if (bt != null) {
      byte b;
      int i;
      Magic[] arrayOfMagic;
      for (i = (arrayOfMagic = bt.getList()).length, b = 0; b < i; ) {
        Magic m = arrayOfMagic[b];
        if (m.getSkill().getSkill_id() == skillID) {
          try {
            m.isTimerStop(o);
          } catch (Exception exception) {}
          bt.remove(m);
          break;
        } 
        b++;
      } 
      if (bt.getCount() <= 0) {
        bt.clear();
        this.list.remove(bt);
      } 
    } 
  }
  
  public BuffTimer get(L1Object o) {
    synchronized (this.list) {
      byte b;
      int i;
      BuffTimer[] arrayOfBuffTimer;
      for (i = (arrayOfBuffTimer = this.list.<BuffTimer>toArray(new BuffTimer[this.list.size()])).length, b = 0; b < i; ) {
        BuffTimer bt = arrayOfBuffTimer[b];
        if (o == null && bt.getObject() == null)
          return bt; 
        if (o != null && bt.getObject() != null && bt.getObject().getObjectId() == o.getObjectId())
          return bt; 
        b++;
      } 
      return null;
    } 
  }
  
  public boolean contains(L1Object o, int id) {
    BuffTimer bt = get(o);
    if (bt != null) {
      byte b;
      int i;
      Magic[] arrayOfMagic;
      for (i = (arrayOfMagic = bt.getList()).length, b = 0; b < i; ) {
        Magic m = arrayOfMagic[b];
        if (m.getSkill().getSkill_id() == id)
          return true; 
        b++;
      } 
    } 
    return false;
  }
  
  public int getBuffTime(L1Object o, int id) {
    BuffTimer bt = get(o);
    if (bt != null) {
      byte b;
      int i;
      Magic[] arrayOfMagic;
      for (i = (arrayOfMagic = bt.getList()).length, b = 0; b < i; ) {
        Magic m = arrayOfMagic[b];
        if (m.getSkill().getSkill_id() == id)
          return bt.getTime(m); 
        b++;
      } 
    } 
    return 0;
  }
}
