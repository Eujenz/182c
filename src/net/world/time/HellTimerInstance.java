package net.world.time;

import java.util.Timer;
import java.util.TimerTask;
import net.database.HellTable;
import net.database.bean.Hell;
import net.world.WorldInstance;
import net.world.instance.PcInstance;

public final class HellTimerInstance extends TimerTask {
  private static Timer timer;
  
  private static class Holder {
    static HellTimerInstance instance = new HellTimerInstance();
  }
  
  public static HellTimerInstance getInstance() {
    return Holder.instance;
  }
  
  private HellTimerInstance() {}
  
  public void start() {
    timer = new Timer(false);
    timer.schedule(getInstance(), 0L, 1000L);
  }
  
  public void run() {
    byte b;
    int i;
    PcInstance[] arrayOfPcInstance;
    for (i = (arrayOfPcInstance = WorldInstance.getInstance().getPc()).length, b = 0; b < i; ) {
      PcInstance pc = arrayOfPcInstance[b];
      if (pc != null) {
        Hell hell = HellTable.getInstance().get(pc);
        if (hell != null) {
          if (hell.getTime() <= 0) {
            if (pc.getMap() == 666)
              pc.toTeleport(32747, 32434, 4); 
            HellTable.getInstance().remove(hell);
          } else if (pc.getMap() == 666) {
            hell.setTime(hell.getTime() - 1);
            HellTable.getInstance().updateTime(hell);
          } else {
            pc.toTeleport(32670, 32800, 666);
          } 
        } else if (pc.getMap() == 666) {
          pc.toTeleport(32747, 32434, 4);
        } 
      } 
      b++;
    } 
  }
}
