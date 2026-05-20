package net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectChatting;
import net.world.WorldInstance;
import net.world.instance.PcInstance;

public class AnnouncementsCycle {
  private static Timer timer;
  
  class AnnouncementsCycleTask extends TimerTask {
    public void run() {
      AnnouncementsCycle.this.scanfile();
      Iterator<String> iterator = AnnouncementsCycle.this.list.listIterator();
      if (iterator.hasNext()) {
        AnnouncementsCycle.this.round %= AnnouncementsCycle.this.list.size();
        AnnouncementsCycle.this.ShowAnnouncementsCycle(AnnouncementsCycle.this.list.get(AnnouncementsCycle.this.round));
        AnnouncementsCycle.this.round++;
      } 
    }
  }
  
  private static final Logger LOG = Logger.getLogger(AnnouncementsCycle.class.getName());
  
  private static class Holder {
    static AnnouncementsCycle instance = new AnnouncementsCycle();
  }
  
  public static AnnouncementsCycle getInstance() {
    return Holder.instance;
  }
  
  int round = 0;
  
  private String line = null;
  
  private boolean firstboot = true;
  
  private final StringBuffer sb = new StringBuffer();
  
  private BufferedReader buf;
  
  private static File dir = new File("data/announceCycle.txt");
  
  static long lastmodify = dir.lastModified();
  
  final boolean AnnounceTimeDisplay = false;
  
  List<String> list = new ArrayList<String>();
  
  private AnnouncementsCycle() {
    cycle();
  }
  
  private void cycle() {
    timer = new Timer(false);
    timer.schedule(new AnnouncementsCycleTask(), 10000L, 600000L);
  }
  
  private void fileEnsure() throws IOException {
    if (!dir.exists())
      try {
        dir.createNewFile();
      } catch (Exception e1) {
        LOG.log(Level.SEVERE, e1.getLocalizedMessage(), e1);
      }  
  }
  
  void scanfile() {
    try {
      fileEnsure();
      if (dir.lastModified() > lastmodify || this.firstboot) {
        this.list.clear();
        this.buf = new BufferedReader(new InputStreamReader(new FileInputStream(dir)));
        while ((this.line = this.buf.readLine()) != null) {
          if (this.line.startsWith("#") || this.line.isEmpty())
            continue; 
          this.sb.delete(0, this.sb.length());
          this.list.add(this.line);
        } 
        lastmodify = dir.lastModified();
      } 
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        this.buf.close();
        this.firstboot = false;
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  void ShowAnnouncementsCycle(String announcement) {
    byte b;
    int i;
    PcInstance[] arrayOfPcInstance;
    for (i = (arrayOfPcInstance = WorldInstance.getInstance().getPc()).length, b = 0; b < i; ) {
      PcInstance pc = arrayOfPcInstance[b];
      pc.SendPacket((S_BasePacket)new S_ObjectChatting(null, announcement, 20));
      b++;
    } 
  }
}
