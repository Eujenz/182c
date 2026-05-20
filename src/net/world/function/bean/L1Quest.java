package net.world.function.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.database.DatabaseConnection;
import net.world.instance.PcInstance;

public class L1Quest implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private static Logger _log = Logger.getLogger(L1Quest.class.getName());
  
  public static final int QUEST_LEVEL15 = 1;
  
  public static final int QUEST_LEVEL30 = 2;
  
  public static final int QUEST_LEVEL45 = 3;
  
  public static final int QUEST_LEVEL50 = 4;
  
  public static final int QUEST_LYRA = 10;
  
  public static final int QUEST_OILSKINMANT = 11;
  
  public static final int QUEST_DOROMOND = 20;
  
  public static final int QUEST_RUBA = 21;
  
  public static final int QUEST_AREX = 22;
  
  public static final int QUEST_LUKEIN1 = 23;
  
  public static final int QUEST_TBOX1 = 24;
  
  public static final int QUEST_TBOX2 = 25;
  
  public static final int QUEST_TBOX3 = 26;
  
  public static final int QUEST_SIMIZZ = 27;
  
  public static final int QUEST_DOIL = 28;
  
  public static final int QUEST_RUDIAN = 29;
  
  public static final int QUEST_RESTA = 30;
  
  public static final int QUEST_CADMUS = 31;
  
  public static final int QUEST_KAMYLA = 32;
  
  public static final int QUEST_CRYSTAL = 33;
  
  public static final int QUEST_LIZARD = 34;
  
  public static final int QUEST_KEPLISHA = 35;
  
  public static final int QUEST_DESIRE = 36;
  
  public static final int QUEST_SHADOWS = 37;
  
  public static final int QUEST_ROI = 38;
  
  public static final int QUEST_TOSCROLL = 39;
  
  public static final int QUEST_MOONOFLONGBOW = 40;
  
  public static final int QUEST_GENERALHAMELOFRESENTMENT = 41;
  
  public static final int QUEST_GRADUATION = 100;
  
  public static final int QUEST_TUTOR = 300;
  
  public static final int QUEST_TUTOR2 = 304;
  
  public static final int QUEST_YURIE = 200;
  
  public static final int QUEST_END = 255;
  
  private PcInstance _owner = null;
  
  private Map<Integer, Integer> _quest = null;
  
  public L1Quest(PcInstance owner) {
    this._owner = owner;
  }
  
  private void read() {
    this._owner.getName();
  }
  
  public void add_step(int quest_id, int add) {
    int step = get_step(quest_id);
    step += add;
    set_step(quest_id, step);
  }
  
  public PcInstance get_owner() {
    return this._owner;
  }
  
  public int get_step(int quest_id) {
    if (this._quest == null) {
      Connection con = null;
      PreparedStatement pstm = null;
      ResultSet rs = null;
      try {
        this._quest = new HashMap<Integer, Integer>();
        con = DatabaseConnection.getInstance().getConnection();
        pstm = con.prepareStatement("SELECT * FROM characters_quests WHERE char_id=?");
        pstm.setInt(1, this._owner.getObjectId());
        rs = pstm.executeQuery();
        while (rs.next())
          this._quest.put(Integer.valueOf(rs.getInt(2)), Integer.valueOf(rs.getInt(3))); 
      } catch (Exception e) {
        _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
      } finally {
        DatabaseConnection.getInstance().close(con, pstm, rs);
      } 
    } 
    Integer step = this._quest.get(Integer.valueOf(quest_id));
    if (step == null)
      return 0; 
    return step.intValue();
  }
  
  public boolean isEnd(int quest_id) {
    if (get_step(quest_id) == 255)
      return true; 
    return false;
  }
  
  public void set_end(int quest_id) {
    set_step(quest_id, 255);
  }
  
  public void set_step(int quest_id, int step) {
    Connection con = null;
    PreparedStatement pstm = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      if (this._quest.get(Integer.valueOf(quest_id)) == null) {
        pstm = con.prepareStatement("INSERT INTO characters_quests SET char_id = ?, quest_id = ?, quest_step = ?");
        pstm.setInt(1, this._owner.getObjectId());
        pstm.setInt(2, quest_id);
        pstm.setInt(3, step);
        pstm.execute();
      } else {
        pstm = con.prepareStatement("UPDATE characters_quests SET quest_step = ? WHERE char_id = ? AND quest_id = ?");
        pstm.setInt(1, step);
        pstm.setInt(2, this._owner.getObjectId());
        pstm.setInt(3, quest_id);
        pstm.execute();
      } 
    } catch (Exception e) {
      _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
    } finally {
      DatabaseConnection.getInstance().close(con, pstm);
    } 
    this._quest.put(Integer.valueOf(quest_id), Integer.valueOf(step));
  }
}
