package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import net.database.bean.Dungeon;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectPotal;
import net.util.Util;
import net.world.WorldMap;
import net.world.instance.PcInstance;

public class DungeonTable {
  private static ArrayList<Dungeon> _list;
  
  private static class Holder {
    static DungeonTable instance = new DungeonTable();
  }
  
  public static DungeonTable getInstance() {
    return Holder.instance;
  }
  
  private DungeonTable() {
    _list = new ArrayList<Dungeon>();
    dungeonLoading();
  }
  
  private static void dungeonLoading() {
    System.out.print("[SQL] 加载地监信息.");
    try {
      Connection con = DatabaseConnection.getInstance().getConnection();
      PreparedStatement statement = con.prepareStatement("SELECT * FROM dungeon");
      ResultSet Data = statement.executeQuery();
      while (Data.next()) {
        WorldMap.getInstance().set_map(Data.getInt("locX"), Data.getInt("locY"), Data.getInt("locM"), 100);
        Dungeon d = new Dungeon();
        d.set_x(Data.getInt("locX"));
        d.set_y(Data.getInt("locY"));
        d.set_mapid(Data.getInt("locM"));
        d.set_tx(Data.getInt("gotoX"));
        d.set_ty(Data.getInt("gotoY"));
        d.set_tmapid(Data.getInt("gotoM"));
        d.set_theading(Data.getInt("gotoH"));
        d.set_item_id(Data.getInt("item_id"));
        d.set_item_type(Data.getInt("item_type"));
        d.set_item_count(Data.getInt("item_count"));
        _list.add(d);
      } 
      Data.close();
      statement.close();
      con.close();
      System.out.println(" 数量:" + _list.size());
    } catch (Exception exception) {}
  }
  
  public void gotoDungeon(PcInstance pc) {
    byte b;
    int i;
    Dungeon[] arrayOfDungeon;
    for (i = (arrayOfDungeon = _list.<Dungeon>toArray(new Dungeon[_list.size()])).length, b = 0; b < i; ) {
      Dungeon d = arrayOfDungeon[b];
      if (d != null) {
        if (d.get_item_id() != 227 && 
          pc.getX() == d.get_x() && pc.getY() == d.get_y() && pc.getMap() == d.get_mapid()) {
          if (d.get_item_id() > 0) {
            if (pc.getInventory().getItemDbId(d.get_item_id()) == null)
              break; 
            switch (d.get_item_id()) {
              case 120:
                ship1(pc, d);
                break;
              case 121:
                ship2(pc, d);
                break;
            } 
          } 
          teleport(pc, d);
          break;
        } 
      } else {
        _list.remove(d);
      } 
      b++;
    } 
  }
  
  private void ship1(PcInstance pc, Dungeon d) {
    int h = 1;
    for (int i = 0; i < 8; i++) {
      if (check(h, 30, h + 1, 30)) {
        teleport(pc, d);
        break;
      } 
      h += 3;
    } 
  }
  
  private void ship2(PcInstance pc, Dungeon d) {
    int h = 0;
    for (int i = 0; i < 8; i++) {
      if (check(h, 0, h + 1, 0)) {
        teleport(pc, d);
        break;
      } 
      h += 3;
    } 
  }
  
  private void ship3(PcInstance pc, Dungeon d) {
    int h = 0;
    for (int i = 0; i < 8; i++) {
      if (check(h, 0, h + 1, 0)) {
        teleport(pc, d);
        break;
      } 
      h += 3;
    } 
  }
  
  private void ship4(PcInstance pc, Dungeon d) {
    int h = 0;
    for (int i = 0; i < 8; i++) {
      if (check(h, 0, h + 1, 0)) {
        teleport(pc, d);
        break;
      } 
      h += 3;
    } 
  }
  
  private void ship5(PcInstance pc, Dungeon d) {
    int h = 1;
    for (int i = 0; i < 8; i++) {
      if (check(h, 30, h + 1, 30)) {
        teleport(pc, d);
        break;
      } 
      h += 3;
    } 
  }
  
  private void ship6(PcInstance pc, Dungeon d) {
    int h = 1;
    for (int i = 0; i < 8; i++) {
      if (check(h, 30, h + 1, 30)) {
        teleport(pc, d);
        break;
      } 
      h += 3;
    } 
  }
  
  public void ship(PcInstance pc, int m) {
    switch (pc.getMap()) {
      case 5:
        if (m >= 55)
          pc.toTeleport(Util.rand(32559, 32564), Util.rand(32722, 32733), 4); 
        break;
      case 84:
        if (m >= 55)
          pc.toTeleport(Util.rand(33425, 33441), Util.rand(33482, 33484), 4); 
        break;
      case 447:
        if (m >= 55)
          pc.toTeleport(Util.rand(32296, 32298), Util.rand(33073, 33087), 440); 
        break;
      case 6:
        if (m >= 25)
          pc.toTeleport(Util.rand(32630, 32632), Util.rand(32972, 32982), 0); 
        break;
      case 83:
        if (m >= 25)
          pc.toTeleport(Util.rand(32935, 32937), Util.rand(33051, 33056), 70); 
        break;
      case 446:
        if (m >= 25)
          pc.toTeleport(Util.rand(32750, 32752), Util.rand(32861, 32874), 445); 
        break;
    } 
  }
  
  private boolean check(int h1, int m1, int h2, int m2) {
    int Hour = Util.WorldTimeToHour();
    int Minute = Util.WorldTimeToMinute();
    if (Hour >= h1 && Minute >= m1 && Hour <= h2)
      return true; 
    if (Hour <= h2 && Minute <= m2 && Hour >= h1)
      return true; 
    return false;
  }
  
  private void teleport(PcInstance pc, Dungeon d) {
    pc.setTempMap(d.get_tmapid());
    pc.setTempX(d.get_tx());
    pc.setTempY(d.get_ty());
    pc.setHeading(d.get_theading());
    pc.SendPacket((S_BasePacket)new S_ObjectPotal(pc.getMap(), pc.getTempMap()));
  }
  
  public static void reload() {
    _list = new ArrayList<Dungeon>();
    dungeonLoading();
  }
}
