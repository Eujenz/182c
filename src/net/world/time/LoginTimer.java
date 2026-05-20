package net.world.time;

import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
import net.ClientController;
import net.Config;
import net.GeneralThreadPool;
import net.LineageClient;
import net.world.instance.PcInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoginTimer extends TimerTask {
  private final Logger log = LoggerFactory.getLogger(LoginTimer.class);
  
  private ScheduledFuture<?> _timer;
  
  private static final long Time = Config.LOGIN_TIMER * 60L * 1000L;
  
  private static final String LOGIN_LOG = "登陆器检测  IP[%s]  账号[%s]  角色[%s]  等级[%s]  职业[%s]  [%s]  [%s]";
  
  private static final String LOGIN_LOG2 = "登陆器检测  IP[%s]  账号[%s]  [%s]  [%s]";
  
  public void start() {
    int timeMillis = 2000;
    this._timer = GeneralThreadPool.getInstance().scheduleAtFixedRate(this, 2000L, 2000L);
  }
  
  public void run() {
    try {
      long time = 0L;
      LineageClient[] lcList = null;
      String msg = null;
      time = System.currentTimeMillis();
      lcList = ClientController.getInstance().getList();
      if (lcList != null) {
        byte b;
        int i;
        LineageClient[] arrayOfLineageClient;
        for (i = (arrayOfLineageClient = lcList).length, b = 0; b < i; ) {
          LineageClient lc = arrayOfLineageClient[b];
          if (time - lc.getLoginTime() >= Time) {
            if (!lc.isLogin())
              msg = "没有使用指定登陆器"; 
            execute(lc, msg);
          } 
          b++;
        } 
      } 
    } catch (Exception e) {
      this.log.error("登陆器验证计时器异常重启", e);
      restart();
    } 
  }
  
  private void execute(LineageClient lc, String msg) {
    PcInstance pc = lc.getPc();
    if (pc != null) {
      this.log.info(String.format("登陆器检测  IP[%s]  账号[%s]  角色[%s]  等级[%s]  职业[%s]  [%s]  [%s]", new Object[] { pc.getClient().getIP(), pc.getClient().getID(), pc.getName(), Integer.valueOf(pc.getLevel()), pc.getClassTypeName(), "已踢下线", msg }));
    } else {
      this.log.info(String.format("登陆器检测  IP[%s]  账号[%s]  [%s]  [%s]", new Object[] { lc.getIP(), lc.getID(), "已踢下线", msg }));
    } 
    lc.close();
  }
  
  private void restart() {
    GeneralThreadPool.getInstance().cancel(this._timer, false);
    LoginTimer timer = new LoginTimer();
    timer.start();
  }
}
