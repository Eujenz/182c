package net.network.server;

import net.util.Util;
import net.world.npc.AuctionBoard;

public class S_AgitLocation extends S_BasePacket {
  private String log_agitId;
  
  private String auctionboard;
  
  public S_AgitLocation(AuctionBoard ab, String agitId) {
    writeC(116);
    writeD(ab.getObjectId());
    writeD(Integer.valueOf(agitId).intValue());
    this.log_agitId = agitId;
    this.auctionboard = ab.toString();
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.log_agitId);
      sb.append(" , ");
      sb.append(this.auctionboard);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
