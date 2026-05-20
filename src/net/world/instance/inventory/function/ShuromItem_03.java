package net.world.instance.inventory.function;

import net.database.ExpTable;
import net.database.bean.Exp;
import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.world.instance.ItemInstance;
import net.world.object.Character;

public class ShuromItem_03 extends ItemInstance {
  public ShuromItem_03(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    Exp e = ExpTable.getInstance().getTemplate(cha.getLevel());
    if (e != null)
      cha.addExp(e.get_bonus()); 
  }
}
