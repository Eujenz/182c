package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.world.instance.ItemInstance;
import net.world.instance.inventory.function.ScrollTeleportation;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;

public class Teleport extends Magic {
  public Teleport(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    boolean flag = false;
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    ItemInstance[] items = this.operator.getInventory().getAll();
    if (items != null) {
      byte b;
      int i;
      ItemInstance[] arrayOfItemInstance;
      for (i = (arrayOfItemInstance = items).length, b = 0; b < i; ) {
        ItemInstance item = arrayOfItemInstance[b];
        if (item.getItem().getItemId() == 107 && 
          item.isEquipped())
          flag = true; 
        b++;
      } 
    } 
    if (HpMpCheck() && ConsumeCount())
      if (id > 0 && flag) {
        ScrollTeleportation.Teleport(this.operator, id);
      } else {
        ScrollTeleportation.RndTeleport(this.operator);
      }  
  }
}
