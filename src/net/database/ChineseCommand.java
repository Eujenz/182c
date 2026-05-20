package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChineseCommand {
  final Logger log = LoggerFactory.getLogger(ChineseCommand.class);
  
  public static List<String> chinese_list = new ArrayList<String>();
  
  public static List<String> command_list = new ArrayList<String>();
  
  private static class Holder {
    static ChineseCommand instance = new ChineseCommand();
  }
  
  public static ChineseCommand getInstance() {
    return Holder.instance;
  }
  
  public void load() {
    System.out.print("[SQL] 加載GM指令 ");
    Connection con = null;
    PreparedStatement statement = null;
    ResultSet Data = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      statement = con.prepareStatement("SELECT * FROM gmcommand");
      Data = statement.executeQuery();
      lists(Data);
    } catch (Exception localException) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, statement, Data);
    } 
  }
  
  private void lists(ResultSet rs) throws Exception {
    while (rs.next()) {
      String chinese = rs.getString("chinese");
      String command = rs.getString("command");
      chinese_list.add(chinese);
      command_list.add(command);
    } 
    //this;
    System.out.println("數量:" + chinese_list.size());
  }
}
