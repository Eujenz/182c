package net.world.instance.skill.function;

import java.util.ArrayList;
import java.util.List;
import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.world.instance.inventory.function.ScrollTeleportation;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;

public class MassTeleport extends Magic {
  private List<L1Object> list;
  
  public MassTeleport(Character cha, Skill skill) {
    super(cha, skill);
    this.list = new ArrayList<L1Object>();
  }
  
  public void toMagic(int id) {
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    if (HpMpCheck() && ConsumeCount()) {
      this.list.clear();
      byte b;
      int i;
      L1Object[] arrayOfL1Object;
      for (i = (arrayOfL1Object = this.operator.getObjectList()).length, b = 0; b < i; ) {
        L1Object o = arrayOfL1Object[b];
        if (this.operator.getDistance(o.getX(), o.getY(), o.getMap(), 1) && this.operator.getClanId() > 0 && this.operator.getClanId() == o.getClanId() && 
          o instanceof net.world.instance.PcInstance)
          this.list.add(o); 
        b++;
      } 
      if (id > 0) {
        ScrollTeleportation.Teleport(this.operator, id);
      } else {
        ScrollTeleportation.RndTeleport(this.operator);
      } 
      for (L1Object o : this.list)
        o.toTeleport(this.operator.getX(), this.operator.getY(), this.operator.getMap()); 
    } 
  }
}
