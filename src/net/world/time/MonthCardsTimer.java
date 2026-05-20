package net.world.time;

import java.util.Timer;
import java.util.TimerTask;
import net.ClientController;
import net.LineageClient;
import net.database.AccountTable;
import net.database.bean.L1Account;
import net.network.server.S_BasePacket;
import net.network.server.S_CloseClient;
import net.world.instance.PcInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MonthCardsTimer extends TimerTask {
  final Logger log = LoggerFactory.getLogger(MonthCardsTimer.class);
  
  private static Timer timer;
  
  private static class Holder {
    static MonthCardsTimer instance = new MonthCardsTimer();
  }
  
  public static MonthCardsTimer getInstance() {
    return Holder.instance;
  }
  
  public void start() {
    timer = new Timer(false);
    timer.schedule(getInstance(), 0L, 1000L);
  }
  
  public void run() {
    try {
      startTimer();
    } catch (Exception e) {
      this.log.error(e.getLocalizedMessage(), e);
    } 
  }
  
  private void startTimer() {
    PcInstance pc = null;
    long nowTime = System.currentTimeMillis() / 1000L;
    byte b;
    int i;
    LineageClient[] arrayOfLineageClient;
    for (i = (arrayOfLineageClient = ClientController.getInstance().getList()).length, b = 0; b < i; ) {
      LineageClient lc = arrayOfLineageClient[b];
      pc = lc.getPc();
      if (pc != null)
        update(lc, pc, nowTime); 
      b++;
    } 
  }
  
  private void update(LineageClient lc, PcInstance pc, long nowTime) {
    for (L1Account acc : AccountTable.getInstance().getList()) {
      if (acc.getAccount().equalsIgnoreCase(lc.getID()))
        sendMessage(acc.getMonth_cards(), nowTime, lc, pc); 
    } 
  }
  
  private void sendMessage(long time, long nowTime, LineageClient lc, PcInstance pc) {
    if (time > nowTime) {
      long stopTime = time - nowTime;
      if (stopTime == 86400L) {
        pc.Message("遊戲時間只剩餘最後一天。");
      } else if (stopTime == 18000L) {
        pc.Message("遊戲時間只剩餘5個小時。");
      } else if (stopTime == 10800L) {
        pc.Message("遊戲時間只剩餘3個小時。");
      } else if (stopTime == 3600L) {
        pc.Message("遊戲時間只剩餘1個小時。");
      } else if (stopTime == 1800L) {
        pc.Message("遊戲時間只剩餘半個小時。");
      } else if (stopTime == 600L) {
        pc.Message("遊戲時間只剩餘10分鐘。");
      } else if (stopTime == 300L) {
        pc.Message("遊戲時間只剩餘5分鐘。");
      } else if (stopTime == 240L) {
        pc.Message("遊戲時間只剩餘4分鐘。");
      } else if (stopTime == 180L) {
        pc.Message("遊戲時間只剩餘3分鐘。");
      } else if (stopTime == 120L) {
        pc.Message("遊戲時間只剩餘2分鐘。");
      } else if (stopTime == 60L) {
        pc.Message("遊戲時間只剩餘1分鐘。");
      } else if (stopTime == 10L) {
        pc.Message("遊戲時間只剩餘10秒。");
      } else if (stopTime == 9L) {
        pc.Message("遊戲時間只剩餘9秒。");
      } else if (stopTime == 8L) {
        pc.Message("遊戲時間只剩餘8秒。");
      } else if (stopTime == 7L) {
        pc.Message("遊戲時間只剩餘7秒。");
      } else if (stopTime == 6L) {
        pc.Message("遊戲時間只剩餘6秒。");
      } else if (stopTime == 5L) {
        pc.Message("遊戲時間只剩餘5秒。");
      } else if (stopTime == 4L) {
        pc.Message("遊戲時間只剩餘4秒。");
      } else if (stopTime == 3L) {
        pc.Message("遊戲時間只剩餘3秒。");
      } else if (stopTime == 2L) {
        pc.Message("遊戲時間只剩餘2秒。");
      } else if (stopTime == 1L) {
        pc.Message("遊戲時間只剩餘1秒。");
      } 
    } else {
      pc.Message("\\fR遊戲時間不足，若想繼續玩遊戲，請儲值。");
      lc.SendPacket((S_BasePacket)new S_CloseClient(0));
      lc.close();
    } 
  }
}
