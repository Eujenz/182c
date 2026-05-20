package net.world.monster;

import net.database.ItemsTable;
import net.database.bean.Monster;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.ai.NpcExp;
import net.world.instance.ItemInstance;
import net.world.instance.MonsterInstance;
import net.world.instance.PcInstance;
import net.world.object.Character;
import net.world.object.L1Object;

public class Nerugaorc extends MonsterInstance {
  public Nerugaorc(Monster m) {
    super(m);
  }
  
  public void toDead() {
    Character character = null;
    L1Object o = null;
    byte b;
    int i;
    NpcExp[] arrayOfNpcExp;
    for (i = (arrayOfNpcExp = (NpcExp[])this.exp_list.values().toArray((Object[])new NpcExp[this.exp_list.size()])).length, b = 0; b < i; ) {
      NpcExp ne = arrayOfNpcExp[b];
      character = ne.getCha();
      b++;
    } 
    if (Util.rand(0, 100) < 100 && this.exp_list.size() == 1 && character != null && character instanceof PcInstance && ((PcInstance)character).isTotem()) {
      ItemInstance item = ItemsTable.getInstance().newItem(135, false, true);
      character.getInventory().insert(item, item.getCount());
      character.SendPacket((S_BasePacket)new S_ServerMessage(143, getName(), item.toString()));
    } 
    super.toDead();
  }
}
