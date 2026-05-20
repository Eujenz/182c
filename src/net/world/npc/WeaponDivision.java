package net.world.npc;

import net.database.SkillTable;
import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.network.server.S_ShowHtml;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;

public final class WeaponDivision extends L1Object {
  public void Talk(PcInstance pc) {
    if (pc == null)
      return; 
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "enchanterw1"));
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (pc == null)
      return; 
    if (text1.equalsIgnoreCase("encw"))
      if (pc.getInventory().Aden(100L, true)) {
        ItemInstance weapon = pc.getInventory().getWeapon();
        if (weapon != null) {
          Skill s = SkillTable.getInstance().getTemplate(9);
          if (s != null) {
            Magic m = new Enchant((Character)pc, s);
            if (m != null)
              m.toMagic(weapon.getInvID()); 
          } 
        } else {
          pc.Message("你没拿武器。");
        } 
      } else {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(189));
      }  
  }
}
