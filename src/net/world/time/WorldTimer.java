package net.world.time;

import java.util.Timer;
import java.util.TimerTask;
import net.Config;
import net.database.ItemsTable;
import net.network.server.S_BasePacket;
import net.network.server.S_WorldStatPacket;
import net.util.Util;
import net.world.WorldInstance;
import net.world.function.ClanSystem;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.kingdom.Kingdom;

public class WorldTimer extends TimerTask {
  private static Timer timer;
  
  private int worldtime = 0;
  
  private int elftownspawn = 0;
  
  private int autosaveplayer = 0;
  
  private static class Holder {
    static WorldTimer instance = new WorldTimer();
  }
  
  public static WorldTimer getInstance() {
    return Holder.instance;
  }
  
  public void start() {
    timer = new Timer(false);
    timer.schedule(getInstance(), 0L, 1000L);
  }
  
  public void run() {
    KingdomWarTimeCheck();
    WorldTime();
    ElementalStoneSpawn();
    AutoSavePlayer();
  }
  
  private void AutoSavePlayer() {
    if (++this.autosaveplayer % 600 == 0) {
      this.elftownspawn = 0;
      byte b;
      int i;
      PcInstance[] arrayOfPcInstance;
      for (i = (arrayOfPcInstance = WorldInstance.getInstance().getPc()).length, b = 0; b < i; ) {
        PcInstance pc = arrayOfPcInstance[b];
        pc.toSave(false);
        b++;
      } 
    } 
  }
  
  private void ElementalStoneSpawn() {
    if (++this.elftownspawn % 60 == 0) {
      this.elftownspawn = 0;
      ItemInstance item = ItemsTable.getInstance().newItem(207, false, true);
      item.toTeleport(Util.rand(32960, 33216), Util.rand(32191, 32511), 4);
    } 
  }
  
  private void WorldTime() {
    Config.WORLDTIME += 3;
    if (++this.worldtime % 60 == 0) {
      this.worldtime = 0;
      WorldInstance.getInstance().SendPacket((S_BasePacket)new S_WorldStatPacket());
      Config.save();
    } 
  }
  
  private void KingdomWarTimeCheck() {
    long time = System.currentTimeMillis();
    for (int i = 1; i < 7; i++) {
      Kingdom k = ClanSystem.getInstance().getKingdom(i);
      if (k.isWarTimeSetting() && k.getWarDay() == 0L) {
        k.setWarDay(Util.YearMonthDateKingdom());
        k.updateDB();
      } 
      if (k.getWarDay() != 0L && time >= k.getWarDay())
        if (k.isWar()) {
          if (time >= k.getWarDay() + 7200000L)
            k.stop(); 
        } else {
          k.start();
        }  
    } 
  }
}
