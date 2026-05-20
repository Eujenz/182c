package net.world.instance.inventory.function;

import net.database.SkillTable;
import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.object.Character;
import net.world.object.L1Object;

public class ItemTripleArrow extends ItemInstance {
  public ItemTripleArrow(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    if (cha.getClassType() != 2)
      return; 
    boolean canuse = false;
    if (cha instanceof PcInstance) {
      PcInstance pc = (PcInstance)cha;
      if (pc.getInventory().getWeapon() != null) {
        if (pc.getInventory().getWeapon().getItem().getType() == 3)
          canuse = true; 
      } else {
        return;
      } 
    } 
    if (!canuse)
      return; 
    L1Object o = cha.getObject(bp.readD());
    int x = bp.readH();
    int y = bp.readH();
    if (o != null)
      SkillTable.getInstance().getTemplate(cha, 132).toMagic(o.getObjectId()); 
  }
}
