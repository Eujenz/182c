package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.object.Character;
import net.world.object.L1Object;

public class ScrollLabeledVELOXNEB extends ItemInstance {
  public ScrollLabeledVELOXNEB(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    L1Object obj = cha.getObject(bp.readD());
    if (obj != null) {
      if (obj instanceof PcInstance) {
        PcInstance tgpc = (PcInstance)obj;
        if (tgpc.isOverlapping()) {
          cha.SendPacket((S_BasePacket)new S_ServerMessage(592));
          return;
        } 
      } 
      obj.toRevival((L1Object)cha);
    } 
  }
}
