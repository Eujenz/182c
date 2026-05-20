package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import net.database.bean.Exp;

public class ExpTable {
  private HashMap<Integer, Exp> _Exp;
  
  private static class Holder {
    static ExpTable instance = new ExpTable();
  }
  
  public static ExpTable getInstance() {
    return Holder.instance;
  }
  
  private ExpTable() {
    System.out.print("[SQL] 加载经验表.");
    this._Exp = new HashMap<Integer, Exp>();
    exp();
  }
  
  private void exp() {
    try {
      Connection con = DatabaseConnection.getInstance().getConnection();
      PreparedStatement statement = con.prepareStatement("SELECT * FROM exp");
      ResultSet exp = statement.executeQuery();
      exptable(exp);
      exp.close();
      statement.close();
      con.close();
    } catch (Exception exception) {}
  }
  
  private void exptable(ResultSet Data) throws Exception {
    while (Data.next()) {
      Exp exp = new Exp();
      exp.set_id(Data.getInt(1));
      exp.set_level(Data.getInt(2));
      exp.set_exp(Data.getInt(3));
      exp.set_bonus(Data.getInt(4));
      this._Exp.put(Integer.valueOf(exp.get_id()), exp);
    } 
    System.out.println(" 数量:" + this._Exp.size());
    Data.close();
  }
  
  public Exp getTemplate(int id) {
    return this._Exp.get(Integer.valueOf(id));
  }
}
