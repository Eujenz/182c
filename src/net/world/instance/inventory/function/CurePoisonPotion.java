package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ObjectPoison;
import net.network.server.S_ServerMessage;
import net.world.instance.ItemInstance;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;

public class CurePoisonPotion extends ItemInstance {
  public CurePoisonPotion(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    cha.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)cha, getItem().get_EffectID()), true);
    cha.SendPacket((S_BasePacket)new S_ServerMessage(211));
    cha.SendPacket((S_BasePacket)new S_ObjectPoison(cha.getObjectId(), cha.isPoison(), cha.isLock()), true);
    BuffTimerInstance.getInstance().remove((L1Object)cha, 8);
    BuffTimerInstance.getInstance().remove((L1Object)cha, 21);
  }
}
