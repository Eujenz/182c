package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.world.object.Character;

public class ScrollEscapeTemp extends Meat {
  public ScrollEscapeTemp(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    cha.setTempMap(4);
    switch (getItem().getItemId()) {
      case 451:
        cha.setTempX(32615);
        cha.setTempY(32772);
        break;
      case 452:
        cha.setTempX(33060);
        cha.setTempY(32745);
        break;
      case 453:
        cha.setTempX(32608);
        cha.setTempY(33178);
        break;
      case 454:
        cha.setTempX(32596);
        cha.setTempY(32916);
        cha.setTempMap(0);
        break;
      case 455:
        cha.setTempX(33110);
        cha.setTempY(33365);
        break;
      case 456:
        cha.setTempX(33723);
        cha.setTempY(32512);
        break;
      case 457:
        cha.setTempX(33428);
        cha.setTempY(32823);
        break;
      case 458:
        cha.setTempX(33599);
        cha.setTempY(33252);
        break;
      case 459:
        cha.setTempX(33068);
        cha.setTempY(32336);
        break;
    } 
    cha.toTeleport(cha.getTempX(), cha.getTempY(), cha.getTempMap());
  }
}
