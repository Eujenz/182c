package net.world.instance.inventory.function;

import net.Config;
import net.database.bean.Item;
import net.database.bean.L1Map;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectLock;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.WorldMap;
import net.world.function.AgitSystem;
import net.world.function.ClanSystem;
import net.world.instance.ItemInstance;
import net.world.instance.books.bean.Book;
import net.world.object.Character;
import net.world.object.L1Object;

public class ScrollTeleportation extends ItemInstance {
  private static Object teleport = new Object();
  
  public ScrollTeleportation(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    if (cha.getInventory().RingOfTeleportControl() || getBless() == 0) {
      bp.readH();
      Teleport(cha, bp.readD());
    } else {
      RndTeleport(cha);
    } 
  }
  
  public static synchronized void Teleport(Character cha, int id) {
    byte b1;
    int i, arrayOfInt[];
    for (i = (arrayOfInt = Config.RTmap).length, b1 = 0; b1 < i; ) {
      int m = arrayOfInt[b1];
      if (cha.getMap() == m) {
        cha.SendPacket((S_BasePacket)new S_ObjectLock());
        cha.SendPacket((S_BasePacket)new S_ServerMessage(647));
        return;
      } 
      b1++;
    } 
    Book b = cha.getBooks().get(id);
    if (b != null) {
      cha.toTeleport(b.getLocX(), b.getLocY(), b.getLocMAP());
    } else {
      RndTeleport(cha);
    } 
  }
  
  public static void RndTeleport(Character cha) {
    synchronized (teleport) {
      byte b;
      int i;
      int[] arrayOfInt;
      for (i = (arrayOfInt = Config.Tmap).length, b = 0; b < i; ) {
        int m = arrayOfInt[b];
        if (cha.getMap() == m) {
          RndLocation((L1Object)cha);
          if (m == 4) {
            int tempX = cha.getX();
            int tempY = cha.getY();
            cha.setX(cha.getTempX());
            cha.setY(cha.getTempY());
            if (!AgitSystem.getInstance().checkAgitLocation((L1Object)cha) && ClanSystem.getInstance().isKingdomZone((L1Object)cha) == null) {
              cha.setX(tempX);
              cha.setY(tempY);
              cha.toTeleport(cha.getTempX(), cha.getTempY(), cha.getTempMap());
              return;
            } 
            cha.setX(tempX);
            cha.setY(tempY);
          } else {
            cha.toTeleport(cha.getTempX(), cha.getTempY(), cha.getTempMap());
            return;
          } 
        } 
        b++;
      } 
      cha.SendPacket((S_BasePacket)new S_ObjectLock());
      cha.SendPacket((S_BasePacket)new S_ServerMessage(276));
    } 
  }
  
  private static void RndLocation(L1Object cha) {
    cha.setTempMap(cha.getMap());
    L1Map m = WorldMap.getInstance().map(cha.getMap());
    if (m != null) {
      int max = 500;
      do {
        if (m.get_mapid() == 4) {
          cha.setTempX(Util.rand(m.get_locX1() + 200, m.get_locX2()));
          cha.setTempY(Util.rand(m.get_locY1() + 200, m.get_locY2()));
        } else {
          cha.setTempX(Util.rand(m.get_locX1(), m.get_locX2()));
          cha.setTempY(Util.rand(m.get_locY1(), m.get_locY2()));
        } 
        max--;
        if (max < 0)
          break; 
      } while (!WorldMap.getInstance().IsThroughObject(cha.getTempX(), cha.getTempY() + 1, cha.getTempMap(), 0));
    } else {
      cha.setTempX(cha.getX());
      cha.setTempY(cha.getY());
    } 
  }
}
