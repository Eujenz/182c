package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_CharacterStat;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ServerMessage;
import net.world.instance.ItemArmorInstance;
import net.world.instance.ItemInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;

public class BlessedArmor extends Magic {
  public static final int AC = 3;
  
  public BlessedArmor(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    if (HpMpCheck() && ConsumeCount()) {
      ItemInstance item = this.operator.getInventory().getItemInvId(id);
      if (item != null && item instanceof ItemArmorInstance && item.getItem().getType() == 16) {
        BuffTimerInstance.getInstance().remove((L1Object)item, this);
        BuffTimerInstance.getInstance().add((L1Object)item, this);
      } else {
        this.operator.SendPacket((S_BasePacket)new S_ServerMessage(280));
      } 
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
