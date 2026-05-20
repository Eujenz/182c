package net.world.time;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectPoison;
import net.network.server.S_ObjectRemove;
import net.util.Util;
import net.world.WorldInstance;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.instance.inventory.Inventory;
import net.world.instance.inventory.PcInventory;
import net.world.object.Character;
import net.world.object.L1Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HpMpTimer extends TimerTask {
  final Logger log = LoggerFactory.getLogger(HpMpTimer.class);
  
  private static Timer timer;
  
  private List<Character> list;
  
  private int time = 0;
  
  private static class Holder {
    static HpMpTimer instance = new HpMpTimer();
  }
  
  public static HpMpTimer getInstance() {
    return Holder.instance;
  }
  
  private HpMpTimer() {
    this.list = new ArrayList<Character>();
  }
  
  public void start() {
    timer = new Timer(false);
    timer.schedule(getInstance(), 0L, 1000L);
  }
  
  public void run() {
    try {
      synchronized (this.list) {
        byte b;
        int i;
        Character[] arrayOfCharacter;
        for (i = (arrayOfCharacter = this.list.<Character>toArray(new Character[this.list.size()])).length, b = 0; b < i; ) {
          Character cha = arrayOfCharacter[b];
          if (!cha.isDead()) {
            boolean bRest = true;
            if (cha instanceof PcInstance) {
              PcInventory pci = (PcInventory)((PcInstance)cha).getInventory();
              bRest = (pci.getWeight() <= 14);
            } 
            if (bRest) {
              if (cha.isHpTic() && cha.getTotalHp() != cha.getCurrentHp())
                cha.setCurrentHp(cha.getCurrentHp() + cha.hpTic()); 
              if (cha.isMpTic() && cha.getTotalMp() != cha.getCurrentMp())
                cha.setCurrentMp(cha.getCurrentMp() + cha.mpTic()); 
              if (cha instanceof PcInstance && cha.getClassType() == 2) {
                Inventory inv = cha.getInventory();
                ItemInstance item = inv.getSlot(5);
                if (inv != null && item != null && 
                  item instanceof net.world.instance.inventory.function.ElvenCloak && cha.getTotalHp() != cha.getCurrentHp())
                  cha.setCurrentHp(cha.getCurrentHp() + Util.rand(0, 1)); 
              } 
            } 
          } 
          if (cha instanceof PcInstance) {
            PcInstance pc = (PcInstance)cha;
            if (!pc.getClient().isConnected()) {
              pc.toReset();
              pc.toDelete();
              WorldInstance.getInstance().removePc(pc);
              pc.setInventory(null);
              pc.setSkill(null);
              pc.setBooks(null);
              pc.setBuff(null);
              pc.getClient().setPc(null);
            } 
            if (pc.isLockFreeze()) {
              this.time++;
              if (this.time == 11) {
                pc.setLock(false);
                pc.setLockFreeze(false);
                pc.SendPacket((S_BasePacket)new S_ObjectPoison(pc.getObjectId(), pc.isPoison(), pc.isLock()), true);
                pc.SendPacket((S_BasePacket)new S_ObjectPoison(13));
                L1Object Freeze = pc.getLockFreezeObject();
                if (Freeze != null) {
                  pc.SendPacket((S_BasePacket)new S_ObjectRemove(Freeze), true);
                  Freeze.toDelete();
                  pc.setLockFreezeObject(null);
                } 
                this.time = 0;
              } 
            } 
          } 
          b++;
        } 
      } 
    } catch (Exception e) {
      this.log.error(e.getLocalizedMessage(), e);
    } 
  }
  
  public void add(Character cha) {
    synchronized (this.list) {
      if (!this.list.contains(cha))
        this.list.add(cha); 
    } 
  }
  
  public void remove(Character cha) {
    synchronized (this.list) {
      this.list.remove(cha);
    } 
  }
}
