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
import net.world.pc.L1PinkName;

public class WeaponBreak extends Magic {
  public WeaponBreak(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    if (HpMpCheck() && ConsumeCount()) {
      L1Object o = this.operator.getObject(id);
      if (o != null || id == this.operator.getObjectId()) {
        Character character = null;
        L1PinkName.execute((L1Object)this.operator, o);
        if (id == this.operator.getObjectId())
          character = this.operator; 
        if (this.operator.isInvis())
          Detection.invis((L1Object)this.operator); 
        if (Figure((L1Object)character)) {
          if (character.getInventory() != null) {
            ItemInstance weapon = character.getInventory().getSlot(11);
            if (weapon != null) {
              weapon.setDurability((short)(weapon.getDurability() + 1));
              character.SendPacket((S_BasePacket)new S_ServerMessage(268, weapon.toString()));
              character.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)character, getSkill().getCastGfx()), true);
              character.SendPacket((S_BasePacket)new S_InventoryStatus(weapon));
            } 
          } 
        } else {
          this.operator.SendPacket((S_BasePacket)new S_ServerMessage(280));
        } 
      } 
    } 
  }
}
