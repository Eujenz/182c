package net.network.server;

import net.util.Util;
import net.world.function.ClanSystem;
import net.world.object.L1Object;

public class S_KingdomTaxSetting extends S_BasePacket {
  private String log_object;
  
  private String log_type;
  
  private String log_maxtax;
  
  private String log_tax;
  
  public S_KingdomTaxSetting(L1Object o, int type) {
    this.log_object = o.toString();
    this.log_type = String.valueOf(type);
    this.log_maxtax = String.valueOf(50);
    this.log_tax = String.valueOf(ClanSystem.getInstance().getKingdom(type).getTax());
    writeC(69);
    writeD(o.getObjectId());
    writeC(0);
    writeC(50);
    writeC(ClanSystem.getInstance().getKingdom(type).getTax());
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.log_object);
      sb.append(" , ");
      sb.append(this.log_type);
      sb.append(" , ");
      sb.append(this.log_maxtax);
      sb.append(" , ");
      sb.append(this.log_tax);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
