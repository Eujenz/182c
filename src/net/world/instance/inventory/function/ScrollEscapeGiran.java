package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.util.Util;
import net.world.instance.ItemInstance;
import net.world.object.Character;

public class ScrollEscapeGiran extends ItemInstance {
  public ScrollEscapeGiran(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    cha.setTempMap(4);
    switch (Util.rand(0, 4)) {
      case 0:
        cha.setTempX(33428 + Util.rand(0, 2));
        cha.setTempY(32823 + Util.rand(0, 2));
        break;
      case 1:
        cha.setTempX(33418 + Util.rand(0, 2));
        cha.setTempY(32818 + Util.rand(0, 2));
        break;
      case 2:
        cha.setTempX(33439 + Util.rand(0, 2));
        cha.setTempY(32817 + Util.rand(0, 2));
        break;
      case 3:
        cha.setTempX(33435 + Util.rand(0, 2));
        cha.setTempY(32803 + Util.rand(0, 2));
        break;
      case 4:
        cha.setTempX(33432 + Util.rand(0, 2));
        cha.setTempY(32824 + Util.rand(0, 2));
        break;
    } 
    cha.toTeleport(cha.getTempX(), cha.getTempY(), cha.getTempMap());
  }
}
