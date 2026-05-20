package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import net.database.bean.Npc;
import net.util.ClientFileLoad;

public class NpcTable {
  private HashMap<Integer, Npc> _npcs;
  
  private static class Holder {
    static NpcTable instance = new NpcTable();
  }
  
  public static NpcTable getInstance() {
    return Holder.instance;
  }
  
  private NpcTable() {
    System.out.print("[SQL] 加载NPC.");
    this._npcs = new HashMap<Integer, Npc>();
    RestoreNpcData();
  }
  
  private void RestoreNpcData() {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM npc");
      rs = st.executeQuery();
      while (rs.next()) {
        Npc npcDat = new Npc();
        npcDat.set_npcId(rs.getInt(1));
        npcDat.set_name(rs.getString(2));
        npcDat.set_type(rs.getString(3));
        npcDat.set_ai(Boolean.valueOf(rs.getString(4)).booleanValue());
        npcDat.set_gfxid(rs.getInt(5));
        npcDat.set_nameid(rs.getString(6));
        try {
          npcDat.set_nameidN(Integer.valueOf(rs.getString(6).substring(1, rs.getString(6).length())).intValue());
        } catch (Exception exception) {}
        npcDat.setHp(rs.getInt(7));
        npcDat.setLawful(rs.getInt(8));
        npcDat.set_light(rs.getInt(9));
        npcDat.set_gfxMode(rs.getInt(10));
        npcDat.setDie((rs.getInt(11) == 1));
        for (int i = 0; i < 50; i++) {
          try {
            int speed = ClientFileLoad.getInstance().getGfxMode(npcDat.get_gfxid(), i);
            if (speed > 0)
              npcDat.addModespeed(i, speed); 
          } catch (Exception exception) {}
        } 
        this._npcs.put(new Integer(npcDat.get_npcId()), npcDat);
      } 
      System.out.println(" 数量:" + this._npcs.size());
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  public Npc getNpcTemplate(int id) {
    return this._npcs.get(new Integer(id));
  }
}
