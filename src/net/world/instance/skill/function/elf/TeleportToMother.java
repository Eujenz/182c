package net.world.instance.skill.function.elf;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.util.Util;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;

public final class TeleportToMother extends Magic {
  public TeleportToMother(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    if (HpMpCheck() && ConsumeCount()) {
      elvenForest(this.operator);
      this.operator.toTeleport(this.operator.getTempX(), this.operator.getTempY(), this.operator.getTempMap());
    } 
  }
  
  private void elvenForest(Character cha) {
    cha.setTempMap(4);
    switch (Util.rand(0, 2)) {
      case 0:
        cha.setTempX(33047);
        cha.setTempY(32337);
        break;
      case 1:
        cha.setTempX(33050);
        cha.setTempY(32340);
        break;
      case 2:
        cha.setTempX(33057);
        cha.setTempY(32342);
        break;
    } 
  }
}
