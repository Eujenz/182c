package net.world.function;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import net.Config;
import net.database.DatabaseConnection;
import net.database.ItemsTable;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.network.server.S_ShowHtml;
import net.world.instance.AgitInstance;
import net.world.instance.DoorInstance;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.instance.inventory.function.ScrollLabeledVERRYEDHORAE;
import net.world.object.L1Object;

public class AgitSystem {
  private static class Holder {
    static AgitSystem instance = new AgitSystem();
  }
  
  public static AgitSystem getInstance() {
    return Holder.instance;
  }
  
  public void Agsell(PcInstance pc, AgitInstance agit) {
    if (pc.getClassType() == 0) {
      CloseDoor(pc, agit);
      int gold = getAgitPrice(agit.getAgitId());
      ItemInstance aden = ItemsTable.getInstance().newItem(5, false, true);
      aden.setCount(gold);
      if (!pc.getInventory().insert(aden, gold))
        aden.toTeleport(pc.getX(), pc.getY(), pc.getMap()); 
      cleanDB(agit.getAgitId());
      Expel(agit);
      pc.Message("處理成功.");
    } else {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(518));
    } 
  }
  
  public boolean CheckAgit(int clan_id) {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT * FROM agit_list WHERE clan_id='");
    sb.append(clan_id);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select(sb.toString());
  }
  
  public boolean isBidder(String name) {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT bidder FROM agit_list WHERE bidder='");
    sb.append(name);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select(sb.toString());
  }
  
  public int getAgitPrice(int agitId) {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT price FROM agit_list WHERE agit_id='");
    sb.append(agitId);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select_count(sb.toString());
  }
  
  public void updateAgit(PcInstance pc, int agitId) {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE agit_list SET clan_id='");
    if (pc != null)
      sb.append(pc.getClanId()); 
    if (pc != null) {
      sb.append("', clan_name='");
      sb.append(pc.getClanName());
      sb.append("', agent='");
      sb.append(pc.getName());
    } 
    sb.append("', sell='false' WHERE agit_id='");
    sb.append(agitId);
    sb.append("'");
    DatabaseConnection.getInstance().query_update(sb.toString());
  }
  
  public void gotoAgit(PcInstance pc) {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM agit_list WHERE clan_id=?");
      st.setInt(1, pc.getClanId());
      rs = st.executeQuery();
      if (rs.next()) {
        StringTokenizer stt = new StringTokenizer(rs.getString("loc"), ", ");
        int locX = Integer.valueOf(stt.nextToken()).intValue();
        int locY = Integer.valueOf(stt.nextToken()).intValue();
        pc.setTempX(locX);
        pc.setTempY(locY);
        pc.setTempMap(4);
      } 
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  public int getClanId(int agitId) {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT clan_id FROM agit_list WHERE agit_id='");
    sb.append(agitId);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select_count(sb.toString());
  }
  
  public String getAgitName(int agitId) {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT name FROM agit_list WHERE agit_id='");
    sb.append(agitId);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select_string(sb.toString());
  }
  
  public void OpenDoor(PcInstance pc, AgitInstance agit) {
    byte b;
    int i;
    L1Object[] arrayOfL1Object;
    for (i = (arrayOfL1Object = pc.getObjectList()).length, b = 0; b < i; ) {
      L1Object o = arrayOfL1Object[b];
      if (o instanceof DoorInstance)
        agit.OpenDoor((DoorInstance)o); 
      b++;
    } 
  }
  
  public void CloseDoor(PcInstance pc, AgitInstance agit) {
    byte b;
    int i;
    L1Object[] arrayOfL1Object;
    for (i = (arrayOfL1Object = pc.getObjectList()).length, b = 0; b < i; ) {
      L1Object o = arrayOfL1Object[b];
      if (o instanceof DoorInstance)
        agit.CloseDoor((DoorInstance)o); 
      b++;
    } 
  }
  
  public void Expel(AgitInstance agit) {
    byte b;
    int i;
    L1Object[] arrayOfL1Object;
    for (i = (arrayOfL1Object = agit.getObjectList()).length, b = 0; b < i; ) {
      L1Object o = arrayOfL1Object[b];
      if (o instanceof PcInstance && checkAgitLocation(o, agit.getAgitLocationIdx()) && (o.getClanId() == 0 || getClanId(agit.getAgitId()) != o.getClanId())) {
        ScrollLabeledVERRYEDHORAE.Location(o);
        o.toTeleport(o.getTempX(), o.getTempY(), o.getTempMap());
      } 
      b++;
    } 
  }
  
  public boolean checkAgitLocation(L1Object o) {
    byte b;
    int i;
    int[][] arrayOfInt;
    for (i = (arrayOfInt = Config.AGITLOCATION).length, b = 0; b < i; ) {
      int[] arrayOfInt1 = arrayOfInt[b];
      if (arrayOfInt1[0] <= o.getX() && arrayOfInt1[1] >= o.getX() && arrayOfInt1[2] <= o.getY() && arrayOfInt1[3] >= o.getY() && 4 == o.getMap())
        return true; 
      b++;
    } 
    return false;
  }
  
  public boolean checkAgitLocation(L1Object o, int idx) {
    if (Config.AGITLOCATION[idx][0] <= o.getX() && Config.AGITLOCATION[idx][1] >= o.getX() && Config.AGITLOCATION[idx][2] <= o.getY() && 
      Config.AGITLOCATION[idx][3] >= o.getY() && 4 == o.getMap())
      return true; 
    return false;
  }
  
  public void ChangName(PcInstance pc, String name) {
    if (pc.getClanId() > 0 && CheckAgit(pc.getClanId()))
      if (isAgitName(name) || name.length() <= 1) {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(514));
      } else if (name.length() >= 10) {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(513));
      } else if (pc.getClassType() != 0) {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(518));
      } else {
        updateName(pc.getClanId(), name);
        if (pc.agit != null)
          pc.SendPacket((S_BasePacket)new S_ShowHtml(pc.agit.getObjectId(), "agit", pc.agit.getName(), name)); 
      }  
  }
  
  private boolean isAgitName(String name) {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT * FROM agit_list WHERE name='");
    sb.append(name);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select(sb.toString());
  }
  
  private void updateName(int clanId, String name) {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE agit_list SET name='");
    sb.append(name);
    sb.append("' WHERE clan_id='");
    sb.append(clanId);
    sb.append("'");
    DatabaseConnection.getInstance().query_update(sb.toString());
  }
  
  private void cleanDB(int agitId) {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE agit_list SET clan_id='0', clan_name='', agent='', sell='true' WHERE agit_id='");
    sb.append(agitId);
    sb.append("'");
    DatabaseConnection.getInstance().query_update(sb.toString());
  }
}
