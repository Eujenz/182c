package net.world.time;

import java.util.Timer;
import java.util.TimerTask;
import net.ClientController;
import net.LineageClient;

public final class RemoveCharacterDeleteTimer extends TimerTask {
  private static Timer timer;
  
  private static class Holder {
    static RemoveCharacterDeleteTimer instance = new RemoveCharacterDeleteTimer();
  }
  
  public static RemoveCharacterDeleteTimer getInstance() {
    return Holder.instance;
  }
  
  private RemoveCharacterDeleteTimer() {}
  
  public void start() {
    timer = new Timer(false);
    timer.schedule(getInstance(), 0L, 8000L);
  }
  
  public void run() {
    setTime();
  }
  
  private void setTime() {
    LineageClient[] list = ClientController.getInstance().getList();
    byte b;
    int i;
    LineageClient[] arrayOfLineageClient1;
    for (i = (arrayOfLineageClient1 = list).length, b = 0; b < i; ) {
      LineageClient lc = arrayOfLineageClient1[b];
      if (lc != null)
        lc.setDeleteCharCount(0); 
      b++;
    } 
  }
}
