package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomGift {
  final Logger log = LoggerFactory.getLogger(RandomGift.class);
  
  public static List<String> boxidlist = new ArrayList<String>();
  
  public static List<String> randomlist = new ArrayList<String>();
  
  public static List<String> itemidlist = new ArrayList<String>();
  
  public static List<String> countlist = new ArrayList<String>();
  
  public static String boxid = null;
  
  public static String random = null;
  
  public static String itemid = null;
  
  public static String count = null;
  
  private static class Holder {
    static RandomGift instance = new RandomGift();
  }
  
  public static RandomGift getInstance() {
    return Holder.instance;
  }
  
  public void load() {
    System.out.print("[SQL] 加載福袋系統 ");
    Connection con = null;
    PreparedStatement statement = null;
    ResultSet Data = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      statement = con.prepareStatement("SELECT * FROM item_box");
      Data = statement.executeQuery();
      lists(Data);
    } catch (Exception localException) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, statement, Data);
    } 
  }
  
  private void lists(ResultSet rs) throws Exception {
    while (rs.next()) {
      boxid = rs.getString("boxid");
      random = rs.getString("random");
      itemid = rs.getString("itemid");
      count = rs.getString("count");
      boxidlist.add(boxid);
      randomlist.add(random);
      itemidlist.add(itemid);
      countlist.add(count);
    } 
    System.out.println("數量:" + boxidlist.size());
  }
}
