package net.network.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import net.database.DatabaseConnection;
import net.util.Util;
import net.world.instance.PcInstance;
import net.world.npc.AuctionBoard;

public class S_AgitList extends S_BasePacket {
  public S_AgitList(AuctionBoard ab, PcInstance pc) {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    StringTokenizer st2 = null;
    String kid = "奇嚴";
    try {
      if (ab.getMap() == 4 && ab.getX() == 33421 && ab.getY() == 32823) {
        kid = "奇嚴";
      } else if (ab.getMap() == 4 && ab.getX() == 33585 && ab.getY() == 33235) {
        kid = "海音";
      } 
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM agit_list WHERE sell='true' AND type=?");
      st.setString(1, kid);
      rs = st.executeQuery();
      rs.last();
      if (rs.getRow() > 0) {
        writeC(115);
        writeD(ab.getObjectId());
        writeH(rs.getRow());
        rs.first();
        do {
          st2 = new StringTokenizer(rs.getString(11), "/");
          writeD(rs.getInt(1));
          writeS(rs.getString(2));
          writeH(rs.getInt(4));
          writeC(Integer.parseInt(st2.nextToken()));
          writeC(Integer.parseInt(st2.nextToken()));
          writeD(rs.getInt(10));
        } while (rs.next());
      } else {
        pc.SendPacket(new S_ShowHtml(ab.getObjectId(), "agnolist"));
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
    return sb.toString();
  }
}
