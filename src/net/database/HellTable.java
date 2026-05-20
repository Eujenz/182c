package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;
import net.database.bean.Hell;
import net.world.instance.PcInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HellTable {
  final Logger log = LoggerFactory.getLogger(HellTable.class);
  
  private HashMap<String, Hell> list = new HashMap<String, Hell>();
  
  private static class Holder {
    static HellTable instance = new HellTable();
  }
  
  public static HellTable getInstance() {
    return Holder.instance;
  }
  
  public HellTable() {
    System.out.print("[SQL] 加载地狱表.");
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM char_hell");
      rs = st.executeQuery();
      chatable(rs);
    } catch (Exception e) {
      this.log.error(e.getLocalizedMessage(), e);
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  private void chatable(ResultSet rs) throws Exception {
    while (rs.next()) {
      Hell hell = new Hell();
      hell.setObjid(rs.getInt("objid"));
      hell.setName(rs.getString("name"));
      hell.setTime(rs.getInt("time"));
      put(hell);
    } 
    System.out.println(" 数量:" + this.list.size());
  }
  
  public void put(Hell hell) {
    this.list.put(hell.getName(), hell);
  }
  
  public void remove(String name) {
    if (this.list.containsKey(name))
      this.list.remove(name); 
  }
  
  public Collection<Hell> getList() {
    return this.list.values();
  }
  
  public Hell get(PcInstance pc) {
    if (isHell(pc))
      return this.list.get(pc.getName()); 
    return null;
  }
  
  public boolean isHell(Hell hell) {
    if (this.list.containsKey(hell.getName()))
      return true; 
    return false;
  }
  
  public boolean isHell(PcInstance pc) {
    if (this.list.containsKey(pc.getName()))
      return true; 
    return false;
  }
  
  public void updateTime(Hell hell) {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE char_hell SET time='");
    sb.append(hell.getTime());
    sb.append("' WHERE objid='");
    sb.append(hell.getObjid());
    sb.append("'");
    DatabaseConnection.getInstance().query_update(sb.toString());
  }
  
  public void add(Hell hell) {
    StringBuilder sb = new StringBuilder();
    sb.append("INSERT INTO char_hell SET objid='");
    sb.append(hell.getObjid());
    sb.append("', name='");
    sb.append(hell.getName());
    sb.append("', time='");
    sb.append(hell.getTime());
    sb.append("'");
    DatabaseConnection.getInstance().query_insert(sb.toString());
    put(hell);
  }
  
  public void add(PcInstance pc) {
    Hell hell = get(pc);
    if (hell == null) {
      Hell hellnew = new Hell();
      hellnew.setObjid(pc.getObjectId());
      hellnew.setName(pc.getName());
      hellnew.setTime(300);
      add(hellnew);
    } else {
      hell.setTime(hell.getTime() + 300);
      updateTime(hell);
    } 
  }
  
  public void remove(Hell hell) {
    StringBuilder sb = new StringBuilder();
    sb.append("DELETE FROM char_hell WHERE objid='");
    sb.append(hell.getObjid());
    sb.append("'");
    DatabaseConnection.getInstance().query_delete(sb.toString());
    remove(hell.getName());
  }
  
  public void remove(PcInstance pc) {
    Hell hell = get(pc);
    if (hell != null)
      remove(hell); 
  }
}
