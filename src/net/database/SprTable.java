package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SprTable {
  final Logger log = LoggerFactory.getLogger(SprTable.class);
  
  private static final Map<Integer, Spr> _dataMap = new HashMap<Integer, Spr>();
  
  private static class Holder {
    static SprTable instance = new SprTable();
  }
  
  public static SprTable getInstance() {
    return Holder.instance;
  }
  
  private static class Spr {
    private final Map<Integer, Integer> moveSpeed = new HashMap<Integer, Integer>();
    
    private final Map<Integer, Integer> attackSpeed = new HashMap<Integer, Integer>();
    
    private final Map<Integer, Integer> dirSpellSpeed = new HashMap<Integer, Integer>();
    
    private final Map<Integer, Integer> nodirSpellSpeed = new HashMap<Integer, Integer>();
    
    private Spr() {}
  }
  
  private SprTable() {
    loadSprAction();
  }
  
  public void loadSprAction() {
    System.out.print("[SQL] 加载Spr_frame列表.");
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    Spr spr = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM sprite_frame");
      rs = st.executeQuery();
      while (rs.next()) {
        int key = rs.getInt("gfx");
        if (!_dataMap.containsKey(Integer.valueOf(key))) {
          spr = new Spr();
          _dataMap.put(Integer.valueOf(key), spr);
        } else {
          spr = _dataMap.get(Integer.valueOf(key));
        } 
        int actid = rs.getInt("action");
        int speed = rs.getInt("frame");
        switch (actid) {
          case 0:
          case 4:
          case 11:
          case 20:
          case 24:
          case 40:
          case 46:
          case 50:
            spr.moveSpeed.put(Integer.valueOf(actid), Integer.valueOf(speed));
          case 18:
            spr.dirSpellSpeed.put(Integer.valueOf(actid), Integer.valueOf(speed));
          case 19:
            spr.nodirSpellSpeed.put(Integer.valueOf(actid), Integer.valueOf(speed));
          case 1:
          case 5:
          case 12:
          case 21:
          case 25:
          case 30:
          case 31:
          case 41:
          case 47:
          case 51:
            spr.attackSpeed.put(Integer.valueOf(actid), Integer.valueOf(speed));
        } 
      } 
    } catch (Exception e) {
      this.log.error(e.getLocalizedMessage(), e);
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
    System.out.println(" 数量:" + _dataMap.size());
  }
  
  public int getMoveSpeed(int sprid, int actid) {
    if (_dataMap.containsKey(Integer.valueOf(sprid))) {
      if ((_dataMap.get(Integer.valueOf(sprid))).moveSpeed.containsKey(Integer.valueOf(actid)))
        return ((Integer)(_dataMap.get(Integer.valueOf(sprid))).moveSpeed.get(Integer.valueOf(actid))).intValue(); 
      if (actid == 0)
        return 0; 
      return ((Integer)(_dataMap.get(Integer.valueOf(sprid))).moveSpeed.get(Integer.valueOf(0))).intValue();
    } 
    return 0;
  }
  
  public int getAttackSpeed(int sprid, int actid) {
    if (_dataMap.containsKey(Integer.valueOf(sprid))) {
      Map<Integer, Integer> as = (_dataMap.get(Integer.valueOf(sprid))).attackSpeed;
      if (as != null) {
        if (as.containsKey(Integer.valueOf(actid)))
          return ((Integer)as.get(Integer.valueOf(actid))).intValue(); 
        if (actid == 1)
          return 0; 
        if (as.containsKey(Integer.valueOf(1)))
          return ((Integer)as.get(Integer.valueOf(1))).intValue(); 
      } 
    } 
    return 0;
  }
  
  public int getDirSpellSpeed(int sprid, int actid) {
    if (_dataMap.containsKey(Integer.valueOf(sprid))) {
      if ((_dataMap.get(Integer.valueOf(sprid))).dirSpellSpeed.containsKey(Integer.valueOf(actid)))
        return ((Integer)(_dataMap.get(Integer.valueOf(sprid))).dirSpellSpeed.get(Integer.valueOf(actid))).intValue(); 
      if (actid == 18)
        return 0; 
      return ((Integer)(_dataMap.get(Integer.valueOf(sprid))).dirSpellSpeed.get(Integer.valueOf(18))).intValue();
    } 
    return 0;
  }
  
  public int getNodirSpellSpeed(int sprid, int actid) {
    if (_dataMap.containsKey(Integer.valueOf(sprid))) {
      if ((_dataMap.get(Integer.valueOf(sprid))).nodirSpellSpeed.containsKey(Integer.valueOf(actid)))
        return ((Integer)(_dataMap.get(Integer.valueOf(sprid))).nodirSpellSpeed.get(Integer.valueOf(actid))).intValue(); 
      if (actid == 19)
        return 0; 
      return ((Integer)(_dataMap.get(Integer.valueOf(sprid))).nodirSpellSpeed.get(Integer.valueOf(19))).intValue();
    } 
    return 0;
  }
}
