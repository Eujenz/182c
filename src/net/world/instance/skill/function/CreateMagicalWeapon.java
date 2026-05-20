package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_InventoryStatus;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ServerMessage;
import net.world.instance.ItemInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;

public class CreateMagicalWeapon extends Magic {
  public CreateMagicalWeapon(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    if (HpMpCheck() && ConsumeCount()) {
      ItemInstance item = this.operator.getInventory().getItemInvId(id);
      if (item != null && item instanceof net.world.instance.ItemWeaponInstance && item.getEnLevel() == 0 && item.getBless() >= 0 && item.getBless() < 128 && 
        item.getItem().isEnchant()) {
        String[] EnMsg = new String[3];
        EnMsg[1] = "$252";
        EnMsg[2] = "$247";
        EnMsg[0] = item.toString();
        item.setEnLevel(1);
        this.operator.SendPacket((S_BasePacket)new S_ServerMessage(161, EnMsg));
        this.operator.SendPacket((S_BasePacket)new S_InventoryStatus(item));
        this.operator.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)this.operator, getSkill().getCastGfx()), true);
      } else {
        this.operator.SendPacket((S_BasePacket)new S_ServerMessage(280));
      } 
    } 
  }
}
