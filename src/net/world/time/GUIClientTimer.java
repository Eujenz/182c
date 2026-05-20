package net.world.time;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.GeneralThreadPool;
import net.LineageClient;
import net.network.server.S_BasePacket;
import net.network.server.UI_S_GlobalChat;
import net.world.WorldInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GUIClientTimer implements Runnable {
  private final Logger log = LoggerFactory.getLogger(GUIClientTimer.class);
  
  protected Queue<String> queue;
  
  private static class Holder {
    static GUIClientTimer instance = new GUIClientTimer();
  }
  
  public static GUIClientTimer getInstance() {
    return Holder.instance;
  }
  
  private GUIClientTimer() {
    this.queue = new ConcurrentLinkedQueue<String>();
    GeneralThreadPool.getInstance().schedule(this, 0L);
  }
  
  public void run() {
    try {
      while (true) {
        startTimer();
        Thread.sleep(10L);
      } 
    } catch (Exception e) {
      this.log.error(e.getLocalizedMessage(), e);
    } finally {
      close();
    } 
  }
  
  private void startTimer() throws InterruptedException {
    for (Iterator<String> iter = this.queue.iterator(); iter.hasNext(); ) {
      String data = iter.next();
      iter.remove();
      execute(data);
      Thread.sleep(1L);
    } 
  }
  
  private void execute(String data) {
    byte b;
    int i;
    LineageClient[] arrayOfLineageClient;
    for (i = (arrayOfLineageClient = WorldInstance.getInstance().getUIClient()).length, b = 0; b < i; ) {
      LineageClient lc = arrayOfLineageClient[b];
      if (lc != null)
        lc.SendPacket((S_BasePacket)new UI_S_GlobalChat(data)); 
      b++;
    } 
  }
  
  public void executeQueue(String data) {
    this.queue.offer(data);
  }
  
  public void close() {
    this.queue.clear();
  }
}
