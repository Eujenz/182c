package net.network.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.database.DatabaseConnection;
import net.util.Util;
import net.world.kingdom.Kingdom;

public class S_KingdomTaxOut extends S_BasePacket {
  private String log_kingdom;
  
  public S_KingdomTaxOut(Kingdom k) {
    this.log_kingdom = k.toString();
    writeC(70);
    int uid = k.getUid();
    writeD(uid);
    int taxTotal = getTaxTotal(uid);
    writeD(taxTotal);
  }
  
  private int getTaxTotal(int uid) {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    int taxTotal = 0;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM kingdom WHERE id=?");
      st.setInt(1, uid);
      rs = st.executeQuery();
      if (rs.next())
        taxTotal = (int)rs.getLong("tax_total"); 
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
    return taxTotal;
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
    } catch (Exception exception) {}
    return sb.toString();
  }
}
