package net.online;

import java.util.Timer;
import java.util.TimerTask;
import net.Config;
import net.world.WorldInstance;

public final class WriteHTMLOnline extends TimerTask {
  private static Timer timer;
  
  private String url = Config.HTML_ONLINE_URL;
  
  private long time = Config.HTML_ONLINE_TIME;
  
  private static final String TEXT = "document.write(\"目前游戏在线人数: %s\");";
  
  private static class Holder {
    static WriteHTMLOnline instance = new WriteHTMLOnline();
  }
  
  public static WriteHTMLOnline getInstance() {
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
    html.setContent(String.format("document.write(\"目前游戏在线人数: %s\");", new Object[] { Integer.valueOf(WorldInstance.getInstance().getPcSize()) }));
    html.Write();
  }
  
  private WriteHTMLOnline() {}
}
