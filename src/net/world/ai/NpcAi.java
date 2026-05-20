package net.world.ai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.world.instance.NpcInstance;

public class NpcAi implements Runnable {
  private List<NpcInstance> list;
  
  private int SleepTime;
  
  private static class Holder {
    static NpcAi instance = new NpcAi();
  }
  
  public static NpcAi getInstance() {
    return Holder.instance;
  }
  
  private NpcAi() {
    this.SleepTime = 20;
    this.list = new ArrayList<NpcInstance>();
  }
  
  public void start() {
    (new Thread(getInstance())).start();
  }
  
  public void run() {
    long time = System.currentTimeMillis();
    NpcInstance temp = null;
    while (true) {
      try {
        for (Iterator<NpcInstance> iterator = this.list.iterator(); iterator.hasNext(); ) {
          NpcInstance npc = iterator.next();
          temp = npc;
          if (npc.isAi(time)) {
            if (npc.isDead()) {
              npc.toDead(time);
              continue;
            } 
            if (npc.isFight() && !npc.isRecess()) {
              if (npc.isEscape()) {
                npc.toEscape(time);
                continue;
              } 
              npc.toFight(time);
              continue;
            } 
            if (!npc.isFight() || npc.isRecess()) {
              if (npc.isRecess()) {
                npc.toRecess(time);
                continue;
              } 
              if (npc.isItemFind()) {
                npc.toItem(time);
                continue;
              } 
              npc.toWalk(time);
            } 
          } 
        } 
        Thread.sleep(this.SleepTime);
        time = System.currentTimeMillis();
      } catch (Exception e) {
        removeNpc(temp);
      } 
    } 
  }
  
  public void addNpc(NpcInstance npc) {
    if (npc == null)
      return; 
    if (!this.list.contains(npc))
      this.list.add(npc); 
  }
  
  public void removeNpc(NpcInstance npc) {
    if (npc == null)
      return; 
    this.list.remove(npc);
  }
}
