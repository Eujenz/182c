package net.world.npc;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_CharacterStat;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ServerMessage;
import net.world.instance.ItemArmorInstance;
import net.world.instance.ItemInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;

class ArmorEnchant extends Magic {
  private static final int AC = 3;
  
  public ArmorEnchant(Character cha, Skill skill) {
    super(cha, skill);
    setTime(1800);
  }
  
  public void toMagic(int id) {
    ItemInstance item = this.operator.getInventory().getItemInvId(id);
    if (item != null) {
      BuffTimerInstance.getInstance().remove((L1Object)item, this);
      BuffTimerInstance.getInstance().add((L1Object)item, this);
    } 
  }
  
  public void isTimerRun(L1Object o) {
    ItemArmorInstance item = (ItemArmorInstance)o;
    this.operator.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)this.operator, getSkill().getCastGfx()), true);
    message((L1Object)item);
    item.setBuffBlessedArmor(true);
    if (item.isEquipped() && item.getCha() != null) {
      item.getCha().setAc(item.getCha().getAc() + 3);
      item.getCha().SendPacket((S_BasePacket)new S_CharacterStat(item.getCha()));
    } 
  }
  
  public void isTimerStop(L1Object o) {
    ItemArmorInstance item = (ItemArmorInstance)o;
    this.operator.SendPacket((S_BasePacket)new S_ServerMessage(308, o.toString()));
    item.setBuffBlessedArmor(false);
    if (item.isEquipped() && item.getCha() != null) {
      item.getCha().setAc(item.getCha().getAc() - 3);
      item.getCha().SendPacket((S_BasePacket)new S_CharacterStat(item.getCha()));
    } 
  }
  
  private void message(L1Object o) {
    String[] EnMsg = new String[3];
    EnMsg[0] = o.toString();
    EnMsg[1] = "$245";
    EnMsg[2] = "$247";
    this.operator.SendPacket((S_BasePacket)new S_ServerMessage(161, EnMsg));
  }
}
