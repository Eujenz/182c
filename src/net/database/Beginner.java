package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.Config;

public class Beginner {
  private static Logger _log = Logger.getLogger(Beginner.class.getName());
  
  private static Beginner _instance;
  
  public static Beginner getInstance() {
    if (_instance == null)
      _instance = new Beginner(); 
    return _instance;
  }
  
  public int giveItem(int charId, int classType) {
    Connection con = null;
    PreparedStatement pstm1 = null;
    PreparedStatement pstm2 = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      pstm1 = con.prepareStatement("SELECT * FROM beginner WHERE activate IN(?,?)");
      pstm1.setString(1, "A");
      pstm1.setString(2, "A");
      switch (classType) {
        case 0:
          pstm1.setString(2, "P");
          break;
        case 1:
          pstm1.setString(2, "K");
          break;
        case 2:
          pstm1.setString(2, "E");
          break;
        case 3:
          pstm1.setString(2, "W");
          break;
      } 
      rs = pstm1.executeQuery();
      while (rs.next()) {
        pstm2 = null;
        try {
          pstm2 = con.prepareStatement(String.format(
                "INSERT INTO characters_inventory SET %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s ", new Object[] { 
                  "uid=?", 
                  "char_id=?", "pet_id=?", "letter_id=?", "item_id=?", 
                  
                  "count=?", "have_count=?", "en=?", "equipped=?", "definite=?", 
                  "bless=?", "durability=?", "time=?", "slimerace_uid=?", "silmerace_idx=?", 
                  
                  "silmerace_name=?" }));
          pstm2.setInt(1, Config.getObjectID_ETC());
          pstm2.setInt(2, charId);
          pstm2.setInt(3, 0);
          pstm2.setInt(4, 0);
          pstm2.setInt(5, rs.getInt("item_id"));
          pstm2.setInt(6, rs.getInt("count"));
          pstm2.setInt(7, rs.getInt("charge_count"));
          pstm2.setInt(8, rs.getInt("enchantlvl"));
          pstm2.setInt(9, 0);
          pstm2.setInt(10, 1);
          pstm2.setInt(11, rs.getInt("bless"));
          pstm2.setInt(12, 0);
          pstm2.setInt(13, 0);
          pstm2.setInt(14, 0);
          pstm2.setInt(15, 0);
          pstm2.setString(16, "");
          pstm2.execute();
        } catch (SQLException e2) {
          _log.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
          continue;
        } finally {
          DatabaseConnection.getInstance().close(pstm2);
        } 
      } 
    } catch (Exception e1) {
      _log.log(Level.SEVERE, e1.getLocalizedMessage(), e1);
    } finally {
      DatabaseConnection.getInstance().close(con, pstm1, rs);
    } 
    return 0;
  }
}
