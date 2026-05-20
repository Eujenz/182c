package net.world.pc;

import net.world.instance.PcInstance;
import net.world.object.L1Object;

public final class L1PinkName {
  public static final int TIME = 10;
  
  public static void execute(L1Object atk, L1Object tg) {
    if (atk == null || tg == null)
      return; 
    if (!(atk instanceof PcInstance))
      return; 
    if (tg instanceof PcInstance) {
      PcInstance pc = (PcInstance)tg;
      if (pc.isPinkName())
        return; 
    } 
    if (!(tg instanceof PcInstance) && !(tg instanceof net.world.instance.SummonInstance))
      return; 
    if (atk.getObjectId() == tg.getObjectId())
      return; 
    if (tg.getLawful() < 65536)
      return; 
    if (atk.isWarZone() || tg.isWarZone())
      return; 
    if (!atk.isNormalZone() || !tg.isNormalZone())
      return; 
    startPinkName(atk);
  }
  
  private static void startPinkName(L1Object atk) {
    if (!atk.isPinkName())
      (new Thread(new PinkNameTimer(atk))).start(); 
    atk.startPinkName();
  }
  
  private static void stopPinkName(L1Object atk) {
    if (atk == null)
      return; 
    atk.stopPinkName();
  }
  
  private static class PinkNameTimer implements Runnable {
    private L1Object atk;
    
    public PinkNameTimer(L1Object atk) {
      this.atk = atk;
    }
    
    public void run() {
      try {
        while (this.atk.getPinkNameTime() > 0 && 
          this.atk != null) {
          if (this.atk.getPinkNameTime() <= 0)
            break; 
          if (this.atk.isDead())
            break; 
          this.atk.setPinkNameTime(this.atk.getPinkNameTime() - 1);
          Thread.sleep(1000L);
        } 
      } catch (InterruptedException interruptedException) {
      
      } catch (Exception exception) {
      
      } finally {
        L1PinkName.stopPinkName(this.atk);
      } 
    }
  }
}
