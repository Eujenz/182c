package net;

import java.util.HashMap;
import java.util.Map;
import net.database.AccountTable;
import net.network.server.S_BasePacket;
import net.network.server.S_CloseClient;

public class ClientController implements Runnable {
  private Map<String, LineageClient> list = new HashMap<String, LineageClient>();
  
  private static class Holder {
    static ClientController instance = new ClientController();
  }
  
  public static ClientController getInstance() {
    return Holder.instance;
  }
  
  private ClientController() {
    (new Thread(getInstance())).start();
  }
  
  public void run() {
    try {
      long time = 0L;
      while (true) {
        Thread.sleep(1000L);
        time = System.currentTimeMillis();
        byte b;
        int i;
        LineageClient[] arrayOfLineageClient;
        for (i = (arrayOfLineageClient = getList()).length, b = 0; b < i; ) {
          LineageClient lc = arrayOfLineageClient[b];
          if (lc.getPingTime() > 0L && time - lc.getPingTime() >= 120000L) {
            lc.close();
            remove(lc);
          } 
          b++;
        } 
      } 
    } catch (Exception exception) {
      return;
    } 
  }
  
  public void clear() {
    byte b;
    int i;
    LineageClient[] arrayOfLineageClient;
    for (i = (arrayOfLineageClient = getList()).length, b = 0; b < i; ) {
      LineageClient lc = arrayOfLineageClient[b];
      AccountTable.getInstance().update_status(lc, false);
      b++;
    } 
  }
  
  public void close(LineageClient lc) {
    try {
      ((LineageClient)this.list.get(lc.getID())).SendPacket((S_BasePacket)new S_CloseClient(22));
      ((LineageClient)this.list.get(lc.getID())).close();
    } catch (Exception e) {
      AccountTable.getInstance().update_status(lc, false);
    } 
  }
  
  public void put(LineageClient lc) {
    this.list.put(lc.getID(), lc);
  }
  
  public void remove(LineageClient lc) {
    if (lc != null && containsKey(lc))
      this.list.remove(lc.getID()); 
  }
  
  public boolean containsKey(LineageClient lc) {
    return this.list.containsKey(lc.getID());
  }
  
  public LineageClient[] getList() {
    return (LineageClient[])this.list.values().toArray((Object[])new LineageClient[this.list.size()]);
  }
}
