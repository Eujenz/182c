package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.instance.ItemInstance;
import net.world.object.Character;
import net.world.object.L1Object;

public class HealingPotion extends ItemInstance {
  protected int MIN_HP = 10;
  
  protected int MAX_HP = 30;
  
  public HealingPotion(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    if (cha instanceof net.world.instance.PcInstance)
      setCount(cha, getCount() - 1L); 
    int hp = Util.rand(this.MIN_HP, this.MAX_HP);
    cha.setCurrentHp(cha.getCurrentHp() + hp);
    cha.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)cha, getItem().get_EffectID()), true);
    cha.SendPacket((S_BasePacket)new S_ServerMessage(77));
  }
}
