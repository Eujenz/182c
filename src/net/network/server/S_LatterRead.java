package net.network.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.database.DatabaseConnection;
import net.util.Util;
import net.world.instance.ItemInstance;

public class S_LatterRead extends S_BasePacket {
  public S_LatterRead(ItemInstance latter) {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM characters_letter WHERE uid=?");
      st.setInt(1, latter.getLetterUid());
      rs = st.executeQuery();
      if (rs.next()) {
        writeC(94);
        writeD(latter.getInvID());
        writeH(latter.getItem().get_gfxid());
        writeH(949);
        writeS(rs.getString(3));
        writeS(rs.getString(4));
        writeSS(rs.getString(5));
        writeSS(rs.getString(6));
        writeC(2);
      } 
      rs.close();
      st.close();
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
