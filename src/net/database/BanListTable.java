package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BanListTable {
  private final Logger log = LoggerFactory.getLogger(BanListTable.class);
  
  private List<String> list = new ArrayList<String>();
  
  private static class Holder {
    static BanListTable instance = new BanListTable();
  }
  
  public static BanListTable getInstance() {
    return Holder.instance;
  }
  
  private BanListTable() {
    System.out.print("[SQL] 加载ban ip表.");
    load();
    System.out.println(" 数量:" + this.list.size());
  }
  
  public void load() {
    this.list.clear();
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM ban_list");
      rs = st.executeQuery();
      while (rs.next())
        this.list.add(rs.getString("ip")); 
    } catch (Exception e) {
      this.log.error(e.getLocalizedMessage(), e);
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  @Deprecated
  public boolean isBanList(String ip) {
    return this.list.contains(ip);
  }
  
  public List<String> getList() {
    return this.list;
  }
  
  public void banIP(String ip) {
    if (!this.list.contains(ip)) {
      StringBuffer sb = new StringBuffer();
      sb.append("INSERT INTO ban_list SET ip='");
      sb.append(ip);
      sb.append("'");
      DatabaseConnection.getInstance().query_insert(sb.toString());
      this.list.add(ip);
    } 
  }
  
  public void unBanIP(String ip) {
    StringBuffer sb = new StringBuffer();
    sb.append("delete from ban_list where ip='");
    sb.append(ip);
    sb.append("'");
    DatabaseConnection.getInstance().query_delete(sb.toString());
    load();
  }
}
