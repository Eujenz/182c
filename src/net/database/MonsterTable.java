package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import net.database.bean.Monster;
import net.util.ClientFileLoad;

public class MonsterTable {
  private HashMap<Integer, Monster> list;
  
  private static class Holder {
    static MonsterTable instance = new MonsterTable();
  }
  
  public static MonsterTable getInstance() {
    return Holder.instance;
  }
  
  private MonsterTable() {
    System.out.print("[SQL] 加载怪物列表.");
    this.list = new HashMap<Integer, Monster>();
    MonsterData();
  }
  
  private void MonsterData() {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM monster");
      rs = st.executeQuery();
      while (rs.next()) {
        Monster mon = new Monster();
        mon.setUid(rs.getInt("uid"));
        mon.setName(rs.getString("name"));
        mon.setNameid(rs.getString("name_id"));
        try {
          mon.setNameidN(Integer.valueOf(rs.getString("name_id").substring(1, rs.getString("name_id").length())).intValue());
        } catch (Exception exception) {}
        mon.setGfx(rs.getInt("gfx"));
        mon.setLevel(rs.getInt("level"));
        mon.setHp(rs.getInt("hp"));
        mon.setMp(rs.getInt("mp"));
        mon.setMinDmg(rs.getInt("min_dmg"));
        mon.setMaxDmg(rs.getInt("max_dmg"));
        mon.setAc(rs.getInt("ac"));
        mon.setMr(rs.getInt("mr"));
        mon.setExp(rs.getInt("exp"));
        mon.setLawful(rs.getInt("lawful"));
        mon.setSize(rs.getString("size"));
        mon.setDie((rs.getInt("die") == 1));
        mon.setTribalID(rs.getInt("tribal_id"));
        mon.setTribal((mon.getTribalID() > 0));
        mon.setAgro((rs.getInt("agro") == 1));
        mon.setPoly((rs.getInt("poly") == 1));
        mon.setItempick((rs.getInt("item_pick") == 1));
        mon.setTameable((rs.getInt("tameable") == 1));
        mon.setRunType(rs.getInt("runtype"));
        mon.setAttack((rs.getInt("attack") == 1));
        mon.setAreaatk(rs.getInt("areaatk"));
        mon.setResurrection((rs.getInt("resurrection") == 1));
        mon.setToughskin((rs.getInt("tough_skin") == 1));
        mon.setDropAdena(rs.getInt("drop_adena"));
        mon.setUndead((rs.getInt("undead") == 5));
        for (int i = 0; i < 50; i++) {
          try {
            int speed = ClientFileLoad.getInstance().getGfxMode(mon.getGfx(), i);
            if (speed > 0)
              mon.addModespeed(i, speed); 
          } catch (Exception exception) {}
        } 
        this.list.put(Integer.valueOf(mon.getUid()), mon);
      } 
      System.out.println(" 数量:" + this.list.size());
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  public Monster getMonster(int uid) {
    return this.list.get(Integer.valueOf(uid));
  }
  
  public Monster getMonsterNameId(int name_id) {
    for (Monster m : this.list.values()) {
      if (m.getNameidN() == name_id)
        return m; 
    } 
    return null;
  }
}
