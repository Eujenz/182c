package net.world.monster;

import java.util.List;
import net.database.ItemsTable;
import net.database.bean.Monster;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.world.ai.NpcExp;
import net.world.instance.ItemInstance;
import net.world.instance.MonsterInstance;
import net.world.instance.PcInstance;
import net.world.object.Character;

public class Aracnevil extends MonsterInstance {
  public Aracnevil(Monster mon) {
    super(mon);
  }
  
  public void toDead() {
    if (this.exp_list.size() == 1) {
      NpcExp ne = this.exp_list.values().iterator().next();
      Character cha = ne.getCha();
      if (cha instanceof PcInstance) {
        PcInstance pc = (PcInstance)cha;
        List<ItemInstance> item1 = pc.getInventory().getItemDbId(117);
        if (item1 != null)
          return; 
        if (pc.getClassType() == 1 && pc.getQuest().get_step(1) == 1) {
          ItemInstance temp = ItemsTable.getInstance().newItem(117, false, true);
          pc.getInventory().add(temp);
          pc.SendPacket((S_BasePacket)new S_ServerMessage(143, getName(), temp.toString()));
        } 
      } 
    } 
    super.toDead();
  }
}
