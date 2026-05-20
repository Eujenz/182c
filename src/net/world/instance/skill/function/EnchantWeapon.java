package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ServerMessage;
import net.world.instance.ItemInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;

public class EnchantWeapon extends Magic {
  public EnchantWeapon(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    if (HpMpCheck() && ConsumeCount()) {
      ItemInstance item = this.operator.getInventory().getItemInvId(id);
      if (item != null) {
        BuffTimerInstance.getInstance().remove((L1Object)item, this);
        BuffTimerInstance.getInstance().add((L1Object)item, this);
      } 
    } 
  }
  
  public void isTimerRun(L1Object o) {
    this.operator.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)this.operator, getSkill().getCastGfx()), true);
    o.setBuffEnchantWeapon(true);
    message(o);
  }
  
  public void isTimerEnd(L1Object o) {
    o.setBuffEnchantWeapon(false);
  }
  
  private void message(L1Object o) {
    String[] EnMsg = new String[3];
    EnMsg[0] = o.toString();
    EnMsg[1] = "$245";
    EnMsg[2] = "$247";
    this.operator.SendPacket((S_BasePacket)new S_ServerMessage(161, EnMsg));
  }
}
