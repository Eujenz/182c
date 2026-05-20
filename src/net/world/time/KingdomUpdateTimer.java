package net.world.time;

import java.util.Timer;
import java.util.TimerTask;
import net.world.function.ClanSystem;
import net.world.kingdom.Kingdom;

public final class KingdomUpdateTimer extends TimerTask {
  private static Timer timer;
  
  private static class Holder {
    static KingdomUpdateTimer instance = new KingdomUpdateTimer();
  }
  
  public static KingdomUpdateTimer getInstance() {
    return Holder.instance;
  }
  
  public void start() {
    timer = new Timer(false);
    timer.schedule(getInstance(), 0L, 60000L);
  }
  
  public void run() {
    save();
  }
  
  public void save() {
    for (int i = 1; i < 7; i++) {
      Kingdom k = ClanSystem.getInstance().getKingdom(i);
      k.updateDB();
    } 
  }
}
