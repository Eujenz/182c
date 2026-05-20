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

public final class ArmorDivision extends L1Object {
  public void Talk(PcInstance pc) {
    if (pc == null)
      return; 
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "enchantera1"));
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (pc == null)
      return; 
    if (text1.equalsIgnoreCase("enca"))
      if (pc.getInventory().Aden(100L, true)) {
        ItemInstance armor = pc.getInventory().getArmor();
        if (armor != null) {
          Skill s = SkillTable.getInstance().getTemplate(15);
          if (s != null) {
            Magic m = new ArmorEnchant((Character)pc, s);
            if (m != null)
              m.toMagic(armor.getInvID()); 
          } 
        } else {
          pc.Message("你没穿盔甲。");
        } 
      } else {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(189));
      }  
  }
}
