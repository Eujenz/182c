package net.world.instance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.database.DatabaseConnection;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.world.object.L1Object;

public class TeleportInstance extends L1Object {
  private int npcId;
  
  public TeleportInstance(int npcId) {
    this.npcId = npcId;
  }
  
  protected int[] AdenCheck() {
    int[] aden = null;
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM npc_teleport WHERE npc_id=? ORDER BY tele_num ASC");
      st.setInt(1, this.npcId);
      rs = st.executeQuery();
      rs.last();
      aden = new int[rs.getRow()];
      rs.first();
      int idx = 0;
      do {
        aden[idx++] = rs.getInt("aden");
      } while (rs.next());
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
    return aden;
  }
  
  protected void ActionCheck(PcInstance cha, String text) {
    if (!getDistance((L1Object)cha, 4)) {
      cha.Message("距離太遠.");
      return;
    } 
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM npc_teleport WHERE action=?");
      st.setString(1, text);
      rs = st.executeQuery();
      if (rs.next()) {
        int check_map = rs.getInt("check_map");
        int check_lv_min = rs.getInt("check_lv_min");
        int check_lv_max = rs.getInt("check_lv_max");
        int aden = rs.getInt("aden");
        int x = rs.getInt("x");
        int y = rs.getInt("y");
        int map = rs.getInt("map");
        if ((check_map == 0 || check_map == cha.getMap()) && (
          check_lv_min == 0 || check_lv_min <= cha.getLevel()) && (
          check_lv_max == 0 || check_lv_max >= cha.getLevel()) && 
          cha.getInventory().Aden(aden, true))
          cha.toTeleport(x, y, map); 
      } else {
        cha.SendPacket((S_BasePacket)new S_ServerMessage(189));
      } 
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
}
