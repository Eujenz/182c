package net.online;

import java.util.Timer;
import java.util.TimerTask;
import net.Config;
import net.world.WorldInstance;
import net.world.instance.PcInstance;

public final class WriteHTMLOnlinePc extends TimerTask {
  private static Timer timer;
  
  private String url = Config.HTML_ONLINE_URL_PC;
  
  private long time = Config.HTML_ONLINE_TIME_PC;
  
  private static class Holder {
    static WriteHTMLOnlinePc instance = new WriteHTMLOnlinePc();
  }
  
  public static WriteHTMLOnlinePc getInstance() {
    return Holder.instance;
  }
  
  public void start() {
    timer = new Timer(false);
    timer.schedule(getInstance(), 0L, this.time * 1000L);
  }
  
  public void run() {
    write();
  }
  
  private void write() {
    WriteHTML html = new WriteHTML();
    html.setUrl(this.url);
    html.setContent("当前没有任何人在线上");
    String newName = null;
    byte b;
    int i;
    PcInstance[] arrayOfPcInstance;
    for (i = (arrayOfPcInstance = WorldInstance.getInstance().getPc()).length, b = 0; b < i; ) {
      PcInstance pc = arrayOfPcInstance[b];
      if (pc != null) {
        if (html.getContentWrite().equalsIgnoreCase("当前没有任何人在线上")) {
          newName = html.getContentWrite().replace("当前没有任何人在线上", "");
        } else {
          newName = html.getContentWrite();
        } 
        html.setContent(String.valueOf(newName) + pc.getName() + "<br> \r\n");
      } 
      b++;
    } 
    html.Write();
  }
  
  private WriteHTMLOnlinePc() {}
}
