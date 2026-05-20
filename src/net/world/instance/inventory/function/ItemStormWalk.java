package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectHeading;
import net.world.instance.ItemInstance;
import net.world.object.Character;
import net.world.object.L1Object;

public class ItemStormWalk extends ItemInstance {
  public ItemStormWalk(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    L1Object o = cha.getObject(bp.readD());
    int x = bp.readH();
    int y = bp.readH();
    cha.setHeading(cha.calcheading(x, y));
    cha.SendPacket((S_BasePacket)new S_ObjectHeading((L1Object)cha), true);
    cha.toTeleport(x, y, cha.getMap());
  }
}
