package net.network.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import net.database.DatabaseConnection;
import net.util.Util;
import net.world.npc.AuctionBoard;

public class S_AgitInfo extends S_BasePacket {
  private String log_agitId;
  
  private String log_name;
  
  private String log_loc;
  
  private String log_size;
  
  private String log_agent;
  
  private String log_bidder;
  
  private String log_price;
  
  private String log_end_date;
  
  private String auctionboard;
  
  public S_AgitInfo(AuctionBoard ab, String agitId) {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    StringTokenizer st2 = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM agit_list WHERE agit_id=?");
      st.setInt(1, Integer.valueOf(agitId).intValue());
      rs = st.executeQuery();
      if (rs.next()) {
        st2 = new StringTokenizer(rs.getString(11), "/");
        String m = st2.nextToken();
        String d = st2.nextToken();
        String h = st2.nextToken();
        this.log_agitId = agitId;
        this.log_name = rs.getString(2);
        this.log_loc = rs.getString(3);
        this.log_size = rs.getString(4);
        this.log_agent = rs.getString(8);
        this.log_bidder = rs.getString(9);
        this.log_price = Integer.toString(rs.getInt(10));
        this.log_end_date = String.valueOf(m) + d + h;
        this.auctionboard = ab.toString();
        writeC(42);
        writeD(ab.getObjectId());
        writeS("agsel");
        writeS(agitId);
        writeH(9);
        writeS(rs.getString(2));
        writeS(rs.getString(3));
        writeS(rs.getString(4));
        writeS(rs.getString(8));
        writeS(rs.getString(9));
        writeS(Integer.toString(rs.getInt(10)));
        writeS(m);
        writeS(d);
        writeS(h);
      } 
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
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
      sb.append(this.log_agitId);
      sb.append(" , ");
      sb.append(this.log_name);
      sb.append(" , ");
      sb.append(this.log_loc);
      sb.append(" , ");
      sb.append(this.log_size);
      sb.append(" , ");
      sb.append(this.log_agent);
      sb.append(" , ");
      sb.append(this.log_bidder);
      sb.append(" , ");
      sb.append(this.log_price);
      sb.append(" , ");
      sb.append(this.log_end_date);
      sb.append(" , ");
      sb.append(this.auctionboard);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
