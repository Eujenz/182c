package net.world;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import net.database.bean.L1Map;
import net.world.object.L1Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorldMap {
  final Logger log = LoggerFactory.getLogger(WorldMap.class);
  
  private Map<Integer, L1Map> list = new HashMap<Integer, L1Map>();
  
  private static class Holder {
    static WorldMap instance = new WorldMap();
  }
  
  public static WorldMap getInstance() {
    return Holder.instance;
  }
  
  public L1Map map(int m) {
    return this.list.get(Integer.valueOf(m));
  }
  
  public int get_map(int x, int y, int map) {
    try {
      L1Map m = this.list.get(Integer.valueOf(map));
      if (m != null) {
        if (x < m.get_locX1())
          return 0; 
        if (y < m.get_locY1())
          return 0; 
        return m.get_byte((m.get_locX2() - m.get_locX1()) * (y - m.get_locY1()) + x - m.get_locX1() + y - m.get_locY1());
      } 
    } catch (Exception exception) {}
    return 0;
  }
  
  public void set_map(int x, int y, int map, int val) {
    try {
      L1Map m = this.list.get(Integer.valueOf(map));
      if (m != null) {
        int locx = m.get_locX2() - m.get_locX1();
        int getx = x - m.get_locX1();
        int gety = y - m.get_locY1();
        m.set_byte(locx * gety + getx + gety, (byte)val);
      } 
    } catch (Exception exception) {}
  }
  
  public boolean IsThroughObject(int x, int y, int map, int dir) {
    try {
      switch (dir) {
        case 0:
          return ((get_map(x, y, map) & 0x2) > 0);
        case 1:
          return ((get_map(x, y, map) & 0x2) > 0 && (get_map(x, y - 1, map) & 0x1) > 0);
        case 2:
          return ((get_map(x, y, map) & 0x1) > 0);
        case 3:
          return !(((get_map(x, y + 1, map) & 0x2) <= 0 || (get_map(x, y + 1, map) & 0x1) <= 0) && ((
            get_map(x, y, map) & 0x1) <= 0 || (get_map(x + 1, y + 1, map) & 0x2) <= 0));
        case 4:
          return ((get_map(x, y + 1, map) & 0x2) > 0);
        case 5:
          return !(((get_map(x, y + 1, map) & 0x2) <= 0 || (get_map(x - 1, y + 1, map) & 0x1) <= 0) && ((
            get_map(x - 1, y, map) & 0x1) <= 0 || (get_map(x - 1, y + 1, map) & 0x2) <= 0));
        case 6:
          return ((get_map(x - 1, y, map) & 0x1) > 0);
        case 7:
          return !(((get_map(x, y, map) & 0x2) <= 0 || (get_map(x - 1, y - 1, map) & 0x1) <= 0) && ((
            get_map(x - 1, y, map) & 0x1) <= 0 || (get_map(x - 1, y, map) & 0x2) <= 0));
      } 
    } catch (Exception exception) {}
    return false;
  }
  
  public boolean IsThroughAttack(int x, int y, int map, int dir) {
    try {
      switch (dir) {
        case 0:
          return ((get_map(x, y, map) & 0x8) > 0);
        case 1:
          return !(((get_map(x, y, map) & 0x8) <= 0 || (get_map(x, y - 1, map) & 0x4) <= 0) && ((get_map(x, y, map) & 0x4) <= 0 || (get_map(x + 1, y, map) & 0x8) <= 0));
        case 2:
          return ((get_map(x, y, map) & 0x4) > 0);
        case 3:
          return !(((get_map(x, y + 1, map) & 0x8) <= 0 || (get_map(x, y + 1, map) & 0x4) <= 0) && ((
            get_map(x, y, map) & 0x4) <= 0 || (get_map(x + 1, y + 1, map) & 0x8) <= 0));
        case 4:
          return ((get_map(x, y + 1, map) & 0x8) > 0);
        case 5:
          return !(((get_map(x, y + 1, map) & 0x8) <= 0 || (get_map(x - 1, y + 1, map) & 0x4) <= 0) && ((
            get_map(x - 1, y, map) & 0x4) <= 0 || (get_map(x - 1, y + 1, map) & 0x8) <= 0));
        case 6:
          return ((get_map(x - 1, y, map) & 0x4) > 0);
        case 7:
          return !(((get_map(x, y, map) & 0x8) <= 0 || (get_map(x - 1, y - 1, map) & 0x4) <= 0) && ((
            get_map(x - 1, y, map) & 0x4) <= 0 || (get_map(x - 1, y, map) & 0x8) <= 0));
      } 
    } catch (Exception exception) {}
    return false;
  }
  
  public boolean CombatZone(int x, int y, int map) {
    return (getZone(x, y, map) == 32);
  }
  
  public boolean SafetyZone(int x, int y, int map) {
    return (getZone(x, y, map) == 16);
  }
  
  public boolean NormalZone(int x, int y, int map) {
    return (getZone(x, y, map) == 0);
  }
  
  public boolean AttackZone(L1Object cha, L1Object use) {
    if (SafetyZone(cha.getX(), cha.getY(), cha.getMap()) || SafetyZone(use.getX(), use.getY(), use.getMap())) {
      if (use instanceof net.world.instance.PcInstance && cha instanceof net.world.instance.PcInstance)
        return false; 
      if (use instanceof net.world.instance.SummonInstance && cha instanceof net.world.instance.PcInstance)
        return false; 
      if (use instanceof net.world.instance.PcInstance && cha instanceof net.world.instance.SummonInstance)
        return false; 
    } 
    return true;
  }
  
  public boolean isElfTree(L1Object o) {
    if (o.getMap() == 4)
      return (o.getX() >= 33050 && o.getX() <= 33058 && o.getY() >= 32333 && o.getY() <= 32341); 
    return false;
  }
  
  private WorldMap() {
    long time = System.currentTimeMillis();
    try {
      File f = new File("maps/Cache");
      if (f.isDirectory()) {
        read(false);
      } else {
        this.log.debug("地图缓存文件夹不存在.");
        f.mkdir();
        read(true);
        writeCache();
      } 
    } catch (Exception exception) {}
    System.out.println("[SERVER] 读取世界地图信息. (" + (System.currentTimeMillis() - time) + " msec)");
  }
  
  private void writeCache() throws Exception {
    this.log.debug("创建缓存文件.");
    BufferedOutputStream bw = null;
    byte b;
    int i;
    L1Map[] arrayOfL1Map;
    for (i = (arrayOfL1Map = (L1Map[])this.list.values().toArray((Object[])new L1Map[this.list.size()])).length, b = 0; b < i; ) {
      L1Map m = arrayOfL1Map[b];
      bw = new BufferedOutputStream(new FileOutputStream("maps/Cache/" + m.get_mapid() + ".data"));
      bw.write(m.get_byte());
      bw.close();
      b++;
    } 
    this.log.debug(" (完成)");
  }
  
  private void read(boolean type) throws Exception {
    if (type)
      this.log.debug("Text 从世界地图信息提取文件."); 
    BufferedReader lnrr = new BufferedReader(new FileReader("maps/maps.csv"));
    byte[] temp = new byte[22149121];
    String maps;
    while ((maps = lnrr.readLine()) != null) {
      if (!maps.startsWith("#")) {
        StringTokenizer st1 = new StringTokenizer(maps, ",");
        int readID = Integer.parseInt(st1.nextToken());
        int x1 = Integer.parseInt(st1.nextToken());
        int x2 = Integer.parseInt(st1.nextToken());
        int y1 = Integer.parseInt(st1.nextToken());
        int y2 = Integer.parseInt(st1.nextToken());
        int size = Integer.parseInt(st1.nextToken());
        if (type) {
          readText(temp, readID, size, x1, x2, y1, y2);
          continue;
        } 
        readCache(readID, x1, x2, y1, y2, size);
      } 
    } 
    lnrr.close();
  }
  
  private void readText(byte[] temp, int readID, int size, int x1, int x2, int y1, int y2) throws Exception {
    int TotalSize = -1;
    BufferedReader lnr = new BufferedReader(new FileReader("maps/Text/" + readID + ".txt"));
    String line;
    while ((line = lnr.readLine()) != null) {
      StringTokenizer st = new StringTokenizer(line, ",");
      for (int i = 0; i < size; i++) {
        int t = Integer.parseInt(st.nextToken());
        if (127 < t) {
          temp[++TotalSize] = Byte.MAX_VALUE;
        } else {
          temp[++TotalSize] = (byte)t;
        } 
      } 
    } 
    lnr.close();
    byte[] MAP = new byte[TotalSize - 1];
    System.arraycopy(temp, 0, MAP, 0, MAP.length);
    L1Map m = new L1Map();
    m.set_mapid(readID);
    m.set_locX1(x1);
    m.set_locX2(x2);
    m.set_locY1(y1);
    m.set_locY2(y2);
    m.set_byte(MAP);
    this.list.put(Integer.valueOf(m.get_mapid()), m);
  }
  
  private void readCache(int readID, int x1, int x2, int y1, int y2, int size) throws Exception {
    BufferedInputStream bis = new BufferedInputStream(new FileInputStream("maps/Cache/" + readID + ".data"));
    byte[] data = new byte[bis.available()];
    bis.read(data, 0, data.length);
    bis.close();
    L1Map m = new L1Map();
    m.set_mapid(readID);
    m.set_locX1(x1);
    m.set_locX2(x2);
    m.set_locY1(y1);
    m.set_locY2(y2);
    m.set_byte(data);
    m.setSize(size);
    this.list.put(Integer.valueOf(m.get_mapid()), m);
  }
  
  private int getZone(int x, int y, int map) {
    return get_map(x, y, map) & 0x30;
  }
  
  public L1Map[] getMap() {
    return (L1Map[])this.list.values().toArray((Object[])new L1Map[this.list.size()]);
  }
}
