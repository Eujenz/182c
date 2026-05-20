package net.world.time;

import java.util.Timer;
import java.util.TimerTask;
import net.database.BanListTable;

public final class BanListTimer extends TimerTask {
  private static Timer timer;
  
  private static class Holder {
    static BanListTimer instance = new BanListTimer();
  }
  
  public static BanListTimer getInstance() {
    return Holder.instance;
  }
  
  private BanListTimer() {}
  
  public void start() {
    timer = new Timer(false);
    timer.schedule(getInstance(), 0L, 60000L);
  }
  
  public void run() {
    BanListTable.getInstance().load();
  }
}
