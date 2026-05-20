package net.world.instance.inventory.function;

import net.database.SkillTable;
import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectHeading;
import net.world.instance.ItemInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.pc.L1PinkName;

public class ItemShockStun extends ItemInstance {
  public ItemShockStun(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    if (cha.getClassType() != 1)
      return; 
    L1Object o = cha.getObject(bp.readD());
    int x = bp.readH();
    int y = bp.readH();
    if (!cha.isInvis() && o != null)
      if (cha.getDistance(o.getX(), o.getY(), o.getMap(), 1) && 
        o instanceof Character && 
        !o.isLock()) {
        cha.setHeading(cha.calcheading(x, y));
        cha.SendPacket((S_BasePacket)new S_ObjectHeading((L1Object)cha), true);
        cha.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)cha, Magic.MagicAction), true);
        L1PinkName.execute((L1Object)cha, o);
        cha.setFight(true);
        o.setFight(true);
        SkillTable.getInstance().getTemplate(cha, 87).toMagic(o.getObjectId());
      }  
  }
}
