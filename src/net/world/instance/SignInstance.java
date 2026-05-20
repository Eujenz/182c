package net.world.instance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.database.DatabaseConnection;
import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.object.L1Object;

public class SignInstance extends L1Object {
  public void toClick(L1Object o) {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM agit_list WHERE sign_loc=?");
      st.setString(1, String.valueOf(getX()) + " " + getY());
      rs = st.executeQuery();
      if (rs.next())
        if (rs.getString(7).equalsIgnoreCase("false")) {
          o.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "agname", rs.getString(6), rs.getString(8), rs.getString(2)));
        } else {
          o.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "agnoname", rs.getString(2)));
        }  
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
}
