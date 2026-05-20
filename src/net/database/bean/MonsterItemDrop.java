package net.database.bean;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MonsterItemDrop {
  private int monid;
  
  private List<drop> list = new ArrayList<drop>();
  
  public MonsterItemDrop(int monid) {
    this.monid = monid;
  }
  
  public void add(ResultSet rs) throws Exception {
    drop d = new drop();
    d.setUid(rs.getInt("uid"));
    d.setName(rs.getString("name"));
    d.setItemid(rs.getInt("itemid"));
    d.setCount_min((rs.getInt("count_min") == 0) ? 1 : rs.getInt("count_min"));
    d.setCount_max((rs.getInt("count_max") == 0) ? 1 : rs.getInt("count_max"));
    d.setSpecial(rs.getInt("special"));
    d.setChance(rs.getInt("chance"));
    this.list.add(d);
  }
  
  public List<drop> getList() {
    return this.list;
  }
  
  public int getMonID() {
    return this.monid;
  }
  
  public class drop {
    private int uid;
    
    private String name;
    
    private int itemid;
    
    private int count_min;
    
    private int count_max;
    
    private int special;
    
    private int chance;
    
    public int getUid() {
      return this.uid;
    }
    
    public void setUid(int uid) {
      this.uid = uid;
    }
    
    public String getName() {
      return this.name;
    }
    
    public void setName(String name) {
      this.name = name;
    }
    
    public int getItemid() {
      return this.itemid;
    }
    
    public void setItemid(int itemid) {
      this.itemid = itemid;
    }
    
    public int getCount_min() {
      return this.count_min;
    }
    
    public void setCount_min(int countMin) {
      this.count_min = countMin;
    }
    
    public int getCount_max() {
      return this.count_max;
    }
    
    public void setCount_max(int countMax) {
      this.count_max = countMax;
    }
    
    public int getSpecial() {
      return this.special;
    }
    
    public void setSpecial(int special) {
      this.special = special;
    }
    
    public int getChance() {
      return this.chance;
    }
    
    public void setChance(int chance) {
      this.chance = chance;
    }
  }
}
