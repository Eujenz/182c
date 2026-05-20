package net.world.time;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import net.Config;
import net.database.AccountTable;
import net.network.server.S_BasePacket;
import net.network.server.S_CloseClient;
import net.network.server.S_ServerMessage;
import net.world.WorldInstance;
import net.world.instance.PcInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ShutdownTimer extends TimerTask {
  final Logger log = LoggerFactory.getLogger(ShutdownTimer.class);
  
  private static Timer timer;
  
  private static Calendar sdc = null;
  
  private static class Holder {
    static ShutdownTimer instance = new ShutdownTimer();
  }
  
  public static ShutdownTimer getInstance() {
    return Holder.instance;
  }
  
  private ShutdownTimer() {
    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
    sdc = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
    sdc.set(11, Config.RESTART_TIME);
    if (cal.compareTo(sdc) > -1)
      sdc.add(5, 1); 
    System.out.println("预计关机时间:" + sdc.getTime());
  }
  
  public void start() {
    timer = new Timer(false);
    timer.schedule(getInstance(), 0L, 1000L);
  }
  
  public void run() {
    try {
      restart();
    } catch (Exception e) {
      this.log.error(e.getLocalizedMessage(), e);
    } 
  }
  
  public void setShutdownTime(Calendar cal) {
    if (cal.compareTo(Calendar.getInstance()) > 0) {
      sdc = cal;
      byte b;
      int i;
      PcInstance[] arrayOfPcInstance;
      for (i = (arrayOfPcInstance = WorldInstance.getInstance().getPc()).length, b = 0; b < i; ) {
        PcInstance pc = arrayOfPcInstance[b];
        if (pc != null && pc.isGm())
          pc.Message("重設置關機時間:" + sdc.getTime()); 
        b++;
      } 
    } 
  }
  
  private void restart() {
    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
    int time = Long.valueOf(sdc.getTimeInMillis() - cal.getTimeInMillis()).intValue() / 1000;
    int quotient = time / 10;
    int remainder = time % 10;
    if (quotient > 6)
      return; 
    if (remainder == 0 || quotient == 0)
      //WorldInstance.getInstance().SendPacket((S_BasePacket)new S_ServerMessage(72, time)); 
      WorldInstance.getInstance().SendPacket(new S_ServerMessage(72, new StringBuilder().append(time).toString()));
    if (time < 1)
      shutdown(); 
  }
  
  public void shutdown() {
    Config.shutdown = true;
    try {
      try {
        savePlayer();
      } catch (Exception e) {
        this.log.debug("保用戶信息異常： " + e.toString());
      } 
      try {
        Config.save();
      } catch (Exception e) {
        this.log.debug("保存設置異常： " + e.toString());
      } 
      try {
        KingdomUpdateTimer.getInstance().save();
      } catch (Exception e) {
        this.log.debug("保存世界異常： " + e.toString());
      } 
      System.out.println("资料保存完毕，服务器开始重启。");
      System.exit(0);
    } catch (Exception e) {
      this.log.debug(e.getLocalizedMessage(), e);
    } 
  }
  
  private void savePlayer() {
    byte b;
    int i;
    PcInstance[] arrayOfPcInstance;
    for (i = (arrayOfPcInstance = WorldInstance.getInstance().getPc()).length, b = 0; b < i; ) {
      PcInstance pc = arrayOfPcInstance[b];
      if (pc != null) {
        pc.toSave(false);
        pc.Message("\\fV服务器自动重启。");
        pc.SendPacket((S_BasePacket)new S_CloseClient(0));
        AccountTable.getInstance().LoginsOut(pc.getClient());
      } 
      b++;
    } 
  }
  
  private int hour(Calendar cal) {
    return cal.get(11);
  }
  
  private int minute(Calendar cal) {
    return cal.get(12);
  }
  
  private int second(Calendar cal) {
    return cal.get(13);
  }
}
