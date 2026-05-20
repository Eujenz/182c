package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import net.Config;
import net.database.bean.MonsterItemDrop;
import net.util.Util;
import net.world.instance.ItemInstance;
import net.world.instance.MonsterInstance;

public class MonsterItemDropTable {
  private Map<Integer, MonsterItemDrop> list;
  
  private static class Holder {
    static MonsterItemDropTable instance = new MonsterItemDropTable();
  }
  
  public static MonsterItemDropTable getInstance() {
    return Holder.instance;
  }
  
  private MonsterItemDropTable() {
    System.out.print("[SQL] 加载掉落信息.");
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    MonsterItemDrop mid = null;
    try {
      this.list = new HashMap<Integer, MonsterItemDrop>();
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM monster_item_drop");
      rs = st.executeQuery();
      while (rs.next()) {
        if (this.list.get(Integer.valueOf(rs.getInt("monid"))) == null) {
          mid = new MonsterItemDrop(rs.getInt("monid"));
          this.list.put(Integer.valueOf(rs.getInt("monid")), mid);
          mid.add(rs);
          continue;
        } 
        ((MonsterItemDrop)this.list.get(Integer.valueOf(rs.getInt("monid")))).add(rs);
      } 
      System.out.println(" 数量:" + this.list.size());
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  public void MonsterItemDrop(MonsterInstance mon) {
    MonsterItemDrop mid = this.list.get(Integer.valueOf(mon.getMon().getUid()));
    if (mid != null)
      for (MonsterItemDrop.drop d : mid.getList()) {
        if (Util.rand(1, 10000) <= d.getChance() * Config.RATE_DROP) {
          int bless = 1;
          switch (d.getSpecial()) {
            case 0:
              bless = 0;
              break;
            case 1:
              bless = 1;
              break;
            case 2:
              bless = 2;
              break;
          } 
          ItemInstance item = ItemsTable.getInstance().newItem(d.getItemid(), false, true);
          if (item != null) {
            item.setBless((byte)bless);
            item.setCount(Util.rand(d.getCount_min(), d.getCount_max()));
            mon.getInventory().add(item);
          } 
        } 
      }  
    if (mon.getMon().getDropAdena() > 0) {
      ItemInstance item = ItemsTable.getInstance().newItem(5, false, true);
      item.setCount((Util.rand(mon.getLevel() * 3, mon.getLevel() * 8) * Config.RATE_ADEN * mon.getMon().getDropAdena()));
      mon.getInventory().add(item);
    } 
  }
}
