package net.network.server;

import java.sql.ResultSet;
import net.util.Util;

public class S_WarehouseItemList extends S_BasePacket {
  public S_WarehouseItemList(int npcId, ResultSet rs, int count, int option) {
    writeC(49);
    writeD(npcId);
    try {
      writeH(count);
      writeC(option);
      while (rs.next()) {
        int num = rs.getInt("uid");
        int type = rs.getInt("type");
        int gfxid = rs.getInt("gfxid");
        int bless = rs.getInt("bless");
        count = rs.getInt("count");
        boolean isid = (rs.getInt("definite") == 1);
        String name = rs.getString("name");
        writeD(num);
        writeC(type);
        writeH(gfxid);
        writeC(bless);
        writeD(count);
        writeC(isid ? 1 : 0);
        writeS(name);
      } 
    } catch (Exception exception) {}
    writeD(30);
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    return sb.toString();
  }
}
