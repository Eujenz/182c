package net.util;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemTimer extends TimerTask {
  final Logger logger = LoggerFactory.getLogger(SystemTimer.class);
  
  private static Timer timer;
  
  private static class Holder {
    static SystemTimer instance = new SystemTimer();
  }
  
  public static SystemTimer getInstance() {
    return Holder.instance;
  }
  
  private SystemTimer() {
    try {
      File f = new File("log");
      if (!f.isDirectory())
        f.mkdir(); 
    } catch (Exception exception) {}
  }
  
  public void start() {
    timer = new Timer(false);
    timer.schedule(getInstance(), 0L, 10000L);
  }
  
  public void run() {}
  
  public void addSystem(String msg) {
    this.logger.debug(msg);
  }
}
