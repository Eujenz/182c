package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.database.bean.L1GetBackRestart;

public class GetBackRestartTable {
  private static Logger _log = Logger.getLogger(GetBackRestartTable.class
      .getName());
  
  private static class Holder {
    static GetBackRestartTable instance = new GetBackRestartTable();
  }
  
  public static GetBackRestartTable getInstance() {
    return Holder.instance;
  }
  
  private final Map<Integer, L1GetBackRestart> _getbackrestart = new HashMap<Integer, L1GetBackRestart>();
  
  public GetBackRestartTable() {
    System.out.print("[SQL] 回城坐标资料表.");
    Connection con = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      pstm = con.prepareStatement("SELECT * FROM getback_restart");
      rs = pstm.executeQuery();
      while (rs.next()) {
        L1GetBackRestart gbr = new L1GetBackRestart();
        int area = rs.getInt("area");
        gbr.setArea(area);
        gbr.setLocX(rs.getInt("locx"));
        gbr.setLocY(rs.getInt("locy"));
        gbr.setMapId(rs.getInt("mapid"));
        this._getbackrestart.put(Integer.valueOf(area), gbr);
      } 
    } catch (Exception e) {
      _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
    } finally {
      DatabaseConnection.getInstance().close(con, pstm, rs);
    } 
    System.out.println("数量:" + this._getbackrestart.size());
  }
  
  public L1GetBackRestart[] getGetBackRestartTableList() {
    return (L1GetBackRestart[])this._getbackrestart.values().toArray(
        (Object[])new L1GetBackRestart[this._getbackrestart.size()]);
  }
}
