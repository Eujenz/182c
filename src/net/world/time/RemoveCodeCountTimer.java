package net.world.time;

import java.util.Timer;
import java.util.TimerTask;
import net.ClientController;
import net.LineageClient;

public final class RemoveCodeCountTimer extends TimerTask {
  private static Timer timer;
  
  private static class Holder {
    static RemoveCodeCountTimer instance = new RemoveCodeCountTimer();
  }
  
  public static RemoveCodeCountTimer getInstance() {
    return Holder.instance;
  }
  
  private RemoveCodeCountTimer() {}
  
  public void start() {
    timer = new Timer(false);
    timer.schedule(getInstance(), 0L, 1000L);
  }
  
  public void run() {
    setCodeTime();
  }
  
  private void setCodeTime() {
    LineageClient[] list = ClientController.getInstance().getList();
    byte b;
    int i;
    LineageClient[] arrayOfLineageClient1;
    for (i = (arrayOfLineageClient1 = list).length, b = 0; b < i; ) {
      LineageClient lc = arrayOfLineageClient1[b];
      if (lc != null) {
        lc.setCodeCount(0);
        lc.setWorldJoinCount(0);
        lc.setEllyonneCount(0);
      } 
      b++;
    } 
  }
}
