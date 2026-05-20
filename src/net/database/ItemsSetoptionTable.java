package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import net.database.bean.ItemSetOption;

public class ItemsSetoptionTable {
  private Map<Integer, ItemSetOption> list;
  
  private static class Holder {
    static ItemsSetoptionTable instance = new ItemsSetoptionTable();
  }
  
  public static ItemsSetoptionTable getInstance() {
    return Holder.instance;
  }
  
  private ItemsSetoptionTable() {
    System.out.print("[SQL] 加载物品组合项.");
    this.list = new HashMap<Integer, ItemSetOption>();
    readDB();
  }
  
  private void readDB() {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM items_setoption");
      rs = st.executeQuery();
      while (rs.next()) {
        ItemSetOption is = new ItemSetOption();
        is.setUid(rs.getInt("uid"));
        is.setName(rs.getString("name"));
        is.setCount(rs.getInt("count"));
        is.setAddHp(rs.getInt("add_hp"));
        is.setAddMp(rs.getInt("add_mp"));
        is.setAddStr(rs.getInt("add_str"));
        is.setAddDex(rs.getInt("add_dex"));
        is.setAddCon(rs.getInt("add_con"));
        is.setAddInt(rs.getInt("add_int"));
        is.setAddWis(rs.getInt("add_wis"));
        is.setAddCha(rs.getInt("add_cha"));
        is.setAddAc(rs.getInt("add_ac"));
        is.setAddMr(rs.getInt("add_mr"));
        is.setTicHp(rs.getInt("tic_hp"));
        is.setTicMp(rs.getInt("tic_mp"));
        is.setPolymorph(rs.getInt("polymorph"));
        is.setWisdress(rs.getInt("windress"));
        is.setWateress(rs.getInt("wateress"));
        is.setFireress(rs.getInt("fireress"));
        is.setEarthress(rs.getInt("earthress"));
        is.setGm((rs.getInt("gm") == 1));
        this.list.put(Integer.valueOf(is.getUid()), is);
      } 
      System.out.println(" 数量:" + this.list.size());
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  public ItemSetOption getBean(int uid) {
    return this.list.get(Integer.valueOf(uid));
  }
}
