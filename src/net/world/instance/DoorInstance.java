package net.world.instance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.database.DatabaseConnection;
import net.network.server.S_Attribute;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.world.WorldMap;
import net.world.object.L1Object;

public class DoorInstance extends L1Object {
  public void toClick(L1Object o) {
    if (SearchDoor(o)) {
      if (getGfxMode() == 28) {
        close();
      } else {
        open();
      } 
      send();
    } 
  }
  
  public void OpenClose(boolean open) {
    if (open) {
      open();
    } else {
      close();
    } 
    send();
  }
  
  protected void open() {
    setGfxMode(28);
    if (getHeading() == 4) {
      WorldMap.getInstance().set_map(getX(), getY() + 1, getMap(), 31);
    } else if (getHeading() == 6) {
      WorldMap.getInstance().set_map(getX() - 1, getY(), getMap(), 31);
    } else {
      WorldMap.getInstance().set_map(getX(), getY(), getMap(), 31);
    } 
  }
  
  protected void close() {
    setGfxMode(29);
    if (getHeading() == 4) {
      WorldMap.getInstance().set_map(getX(), getY() + 1, getMap(), 16);
    } else if (getHeading() == 6) {
      WorldMap.getInstance().set_map(getX() - 1, getY(), getMap(), 16);
    } else {
      WorldMap.getInstance().set_map(getX(), getY(), getMap(), 16);
    } 
  }
  
  protected void send() {
    SendPacket((S_BasePacket)new S_ObjectAction(this, getGfxMode()), true);
    SendPacket((S_BasePacket)new S_Attribute(this), true);
  }
  
  private boolean SearchDoor(L1Object o) {
    if (o instanceof PcInstance) {
      PcInstance pc = (PcInstance)o;
      Connection con = null;
      PreparedStatement st = null;
      ResultSet rs = null;
      String loc = String.valueOf(getX()) + " " + getY();
      try {
        con = DatabaseConnection.getInstance().getConnection();
        st = con.prepareStatement("SELECT clan_id FROM agit_list WHERE door1_loc=? OR door2_loc=? OR door3_loc=? OR door4_loc=?");
        st.setString(1, loc);
        st.setString(2, loc);
        st.setString(3, loc);
        st.setString(4, loc);
        rs = st.executeQuery();
        if (rs.next())
          return (pc.getClanId() != 0 && pc.getClanId() == rs.getInt(1)); 
      } catch (Exception exception) {
      
      } finally {
        try {
          rs.close();
        } catch (Exception exception) {}
        try {
          st.close();
        } catch (Exception exception) {}
        try {
          con.close();
        } catch (Exception exception) {}
      } 
      try {
        rs.close();
      } catch (Exception exception) {}
      try {
        st.close();
      } catch (Exception exception) {}
      try {
        con.close();
      } catch (Exception exception) {}
    } 
    return true;
  }
}
