package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.world.WorldInstance;
import net.world.instance.PcInstance;

public class FriendTable {
  public static void friendAdd(String pcname, String friendname) {
    Connection con = null;
    PreparedStatement st = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("INSERT INTO characters_friend SET pcname=?, friendname=?");
      st.setString(1, pcname);
      st.setString(2, friendname);
      st.execute();
    } catch (Exception localException) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st);
    } 
  }
  
  public static void friendDel(String pcname, String friendname) {
    StringBuffer sb = new StringBuffer();
    sb.append("delete from characters_friend where pcname='");
    sb.append(pcname);
    sb.append("' and friendname='");
    sb.append(friendname);
    sb.append("'");
    DatabaseConnection.getInstance().query_delete(sb.toString());
    PcInstance pc = WorldInstance.getInstance().getPc(pcname);
    PcInstance.list.remove(friendname);
  }
  
  public static boolean friendCheck(String pcname, String friendname) {
    Connection con = null;
    PreparedStatement statement = null;
    ResultSet Data = null;
    try {
      boolean has = false;
      con = DatabaseConnection.getInstance().getConnection();
      statement = con.prepareStatement("SELECT * FROM characters_friend");
      Data = statement.executeQuery();
      while (Data.next()) {
        String pcnames = Data.getString("pcname");
        String friendnames = Data.getString("friendname");
        if (pcname.equalsIgnoreCase(pcnames) && friendname.equalsIgnoreCase(friendnames)) {
          has = true;
          break;
        } 
      } 
      if (has)
        return true; 
    } catch (Exception localException) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, statement, Data);
    } 
    return false;
  }
  
  public static void friendList(String pcname) {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM characters_friend");
      rs = st.executeQuery();
      while (rs.next()) {
        String pcnames = rs.getString("pcname");
        String friendnames = rs.getString("friendname");
        PcInstance pc = WorldInstance.getInstance().getPc(pcnames);
        if (pcnames.equalsIgnoreCase(pcname))
          if (!PcInstance.list.contains(friendnames))
            PcInstance.list.add(friendnames);  
      } 
    } catch (Exception localException) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
}
