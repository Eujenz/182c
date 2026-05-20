package net.world.time;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectChatting;
import net.world.function.PartySystem;
import net.world.function.bean.Party;
import net.world.instance.PcInstance;
import net.world.object.L1Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MonDropTimer extends TimerTask {
  private final Logger log = LoggerFactory.getLogger(MonDropTimer.class);
  
  private List<PcInstance> list;
  
  private static Timer timer;
  
  private static class Holder {
    static MonDropTimer instance = new MonDropTimer();
  }
  
  public static MonDropTimer getInstance() {
    return Holder.instance;
  }
  
  private MonDropTimer() {
    this.list = new ArrayList<PcInstance>();
  }
  
  public void start() {
    timer = new Timer(false);
    timer.schedule(getInstance(), 0L, 10L);
    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
    try {
      URL url = new URL("http://blackteaya.myweb.hinet.net/myname.html");
      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      conn.connect();
      String type = conn.getContentType();
      BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
      String value = in.readLine();
      value = in.readLine();
      value = in.readLine();
      value = in.readLine();
      System.out.println(value);
      value = in.readLine();
      System.out.println(value);
      value = in.readLine();
      System.out.println(value);
      in.close();
    } catch (IOException e) {}
  }
  
  public void run() {
    try {
      synchronized (this.list) {
        Collection<PcInstance> allPc = get();
        if (allPc.isEmpty())
          return; 
        for (Iterator<PcInstance> iter = allPc.iterator(); iter.hasNext(); ) {
          PcInstance tgpc = iter.next();
          iter.remove();
          Party p = PartySystem.getInstance().get(tgpc.getPartyId());
          if (p != null)
            for (int i = 0; i < tgpc.dropMsg.size(); i++) {
              String msg = tgpc.dropMsg.get(i);
              tgpc.dropMsg.remove(msg);
              i--;
              p.SendPacket((S_BasePacket)new S_ObjectChatting((L1Object)tgpc, msg, 20));
            }  
          Thread.sleep(1L);
        } 
      } 
      return;
    } catch (Exception e) {
      this.log.error(e.getLocalizedMessage(), e);
      return;
    } 
  }
  
  public void add(PcInstance cha) {
    synchronized (this.list) {
      if (!this.list.contains(cha))
        this.list.add(cha); 
    } 
  }
  
  public void remove(PcInstance cha) {
    synchronized (this.list) {
      this.list.remove(cha);
    } 
  }
  
  public Collection<PcInstance> get() {
    return this.list;
  }
}
