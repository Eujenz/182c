package net.network.server;

import net.util.Util;
import net.world.instance.PcInstance;
import net.world.kingdom.Kingdom;

public class S_KingdomTaxIn extends S_BasePacket {
  private String log_kingdom;
  
  private String log_object;
  
  public S_KingdomTaxIn(Kingdom k, PcInstance cha) {
    this.log_kingdom = k.toString();
    this.log_object = cha.toString();
    writeC(76);
    writeD(k.getUid());
    writeD((int)cha.getInventory().getAden().getCount());
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.log_kingdom);
      sb.append(" , ");
      sb.append(this.log_object);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
