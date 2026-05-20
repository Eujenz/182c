package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.instance.PcInstance;

public class C_Magic extends C_BasePacket {
  private int lv;
  
  private int no;
  
  private int id;
  
  private int x;
  
  private int y;
  
  private String testA;
  
  private int testB;
  
  private int testH;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    if (pc.isLockFreeze())
      return; 
    if (!pc.isDelete() && !pc.isDead()) {
      try {
        this.lv = readC() + 1;
        this.no = readC();
        this.x = 0;
        this.y = 0;
        if ((this.lv == 1 && this.no == 4) || (this.lv == 9 && this.no == 4)) {
          if (pc.getInventory().RingOfTeleportControl() || (pc.getLevel() > 49 && pc.getClassType() == 2)) {
            readH();
            this.id = readD();
          } 
        } else if (this.lv == 8 && this.no == 1) {
          this.x = readH();
          this.y = readH();
        } else if (this.lv == 15 && this.no == 0) {
          this.id = readD();
          this.x = readH();
          this.y = readH();
        } else {
          this.id = readD();
        } 
      } catch (Exception localException) {}
      pc.getSkill().toMagic(this.lv, this.no, this.id, this.x, this.y);
    } 
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.lv);
      sb.append(" , ");
      sb.append(this.no);
      sb.append(" , ");
      sb.append(this.id);
    } catch (Exception localException) {}
    return sb.toString();
  }
}
