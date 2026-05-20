package net.world.ai;

import java.util.ArrayList;
import java.util.List;
import net.world.function.SummonSystem;
import net.world.instance.MonsterInstance;
import net.world.instance.SummonInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonAi implements Runnable {
  final Logger log = LoggerFactory.getLogger(MonAi.class);
  
  private List<MonsterInstance> list;
  
  private List<MonsterInstance> add_list;
  
  private List<MonsterInstance> remove_list;
  
  private int SleepTime;
  
  private static class Holder {
    static MonAi instance = new MonAi();
  }
  
  public static MonAi getInstance() {
    return Holder.instance;
  }
  
  private MonAi() {
    this.SleepTime = 30;
    this.list = new ArrayList<MonsterInstance>();
    this.add_list = new ArrayList<MonsterInstance>();
    this.remove_list = new ArrayList<MonsterInstance>();
  }
  
  public void start() {
    (new Thread(getInstance())).start();
  }
  
  public void run() {
    long time = System.currentTimeMillis();
    MonsterInstance temp = null;
    while (true) {
      try {
        if (this.add_list.size() > 0)
          synchronized (this.add_list) {
            for (MonsterInstance mon : this.add_list) {
              if (!this.list.contains(mon))
                this.list.add(mon); 
            } 
            this.add_list.clear();
          }  
        if (this.remove_list.size() > 0)
          synchronized (this.remove_list) {
            for (MonsterInstance mon : this.remove_list)
              this.list.remove(mon); 
            this.remove_list.clear();
          }  
        for (MonsterInstance mon : this.list) {
          temp = mon;
          if (mon.isAi(time)) {
            mon.isStatus(time);
            if (mon.isDead()) {
              mon.toDead(time);
              continue;
            } 
            if (mon.isFight() && !mon.isRecess()) {
              if (mon.isEscape()) {
                mon.toEscape(time);
                continue;
              } 
              mon.toFight(time);
              continue;
            } 
            if (mon.isRecess()) {
              mon.toRecess(time);
              continue;
            } 
            if (mon.isItemFind()) {
              mon.toItem(time);
              continue;
            } 
            mon.toWalk(time);
          } 
        } 
        Thread.sleep(this.SleepTime);
        time = System.currentTimeMillis();
      } catch (Exception e) {
        this.log.error(e.getLocalizedMessage(), e);
        try {
          if (temp != null) {
            removeMon(temp);
            temp.toDelete();
            if (temp instanceof SummonInstance)
              SummonSystem.getInstance().remove((SummonInstance)temp); 
            this.log.error(String.valueOf(getClass().toString()) + " run() " + e.getLocalizedMessage() + e + " monster:" + 
                temp.getMon().getUid());
          } 
        } catch (Exception exception) {
          this.log.error(exception.getLocalizedMessage(), exception);
        } 
      } 
    } 
  }
  
  public void addMon(MonsterInstance mon) {
    if (mon == null)
      return; 
    synchronized (this.add_list) {
      this.add_list.add(mon);
    } 
  }
  
  public void removeMon(MonsterInstance mon) {
    if (mon == null)
      return; 
    synchronized (this.remove_list) {
      this.remove_list.add(mon);
    } 
  }
}
