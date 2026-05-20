package net.world.monster;

import net.database.bean.Monster;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.world.function.SummonSystem;
import net.world.instance.ItemInstance;
import net.world.instance.MonsterInstance;
import net.world.object.Character;

public class Shepherd extends MonsterInstance {
  public Shepherd(Monster m) {
    super(m);
  }
  
  public void toGiveMeItem(Character cha, ItemInstance item, long count) {
    if (item.getItem().get_nameidN() == 23) {
      if (isTame(true)) {
        item.setCount(cha, item.getCount() - count);
        if (!SummonSystem.getInstance().addPet(cha, this))
          cha.SendPacket((S_BasePacket)new S_ServerMessage(324)); 
        return;
      } 
      cha.SendPacket((S_BasePacket)new S_ServerMessage(324));
    } 
    super.toGiveMeItem(cha, item, count);
  }
}
