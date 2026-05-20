package net.world.instance.inventory.function;

import net.database.MonsterSpawnTable;
import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.util.Util;
import net.world.WorldMap;
import net.world.ai.MonAi;
import net.world.instance.ItemInstance;
import net.world.instance.MonsterInstance;
import net.world.object.Character;
import net.world.object.L1Object;

public class PineWand extends ItemInstance {
  private static int[] poly = new int[] { 
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 
      11, 12, 13, 14, 18, 19, 20, 21 };
  
  public PineWand(Item i) {
    super(i);
    setHaveCount(Util.rand(5, 15));
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    cha.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)cha, 17), false);
    if (getHaveCount() > 0) {
      setHaveCount(cha, getHaveCount() - 1);
      int x = cha.getX();
      int y = cha.getY();
      if (WorldMap.getInstance().IsThroughObject(cha.getX(), cha.getY(), cha.getMap(), cha.getHeading()))
        switch (cha.getHeading()) {
          case 0:
            y--;
            break;
          case 1:
            x++;
            y--;
            break;
          case 2:
            x++;
            break;
          case 3:
            x++;
            y++;
            break;
          case 4:
            y++;
            break;
          case 5:
            x--;
            y++;
            break;
          case 6:
            x--;
            break;
          default:
            x--;
            y--;
            break;
        }  
      MonsterInstance mon = MonsterSpawnTable.getInstance().newMonster(poly[Util.rand(0, poly.length - 1)]);
      if (mon != null) {
        mon.setSummon(true);
        mon.setHeading(cha.getHeading());
        mon.setHomeX(x);
        mon.setHomeY(y);
        mon.setHomeMap(cha.getMap());
        mon.toTeleport(x, y, cha.getMap());
        MonAi.getInstance().addMon(mon);
      } 
    } 
  }
}
