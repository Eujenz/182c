package net.world.instance.inventory.function;

import net.database.SkillTable;
import net.database.bean.Item;
import net.database.bean.Skill;
import net.network.client.C_BasePacket;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.object.Character;
import net.world.object.L1Object;

public class PowerScroll extends ItemInstance {
  public PowerScroll(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    boolean useit = false;
    PcInstance pc = (PcInstance)cha;
    Skill s = SkillTable.getInstance().getTemplate(getItem().getSkill_id());
    if (s != null) {
      int id1;
      L1Object target;
      int id2;
      int id3;
      switch (getItem().getSkill_id()) {
        case 2:
        case 3:
        case 5:
        case 10:
          SkillTable.getInstance().getTemplate(cha, getItem().getSkill_id()).toMagic(pc.getObjectId());
          useit = true;
          break;
        case 1:
        case 4:
        case 6:
        case 7:
        case 8:
        case 11:
        case 12:
        case 13:
        case 14:
        case 16:
        case 17:
        case 18:
        case 19:
        case 20:
        case 21:
        case 22:
        case 23:
        case 24:
        case 25:
          id1 = bp.readD();
          bp.readH();
          bp.readH();
          target = cha.getObject(id1);
          if (s.getType().equalsIgnoreCase("attack") || s.getSkill_id() == 8 || s.getSkill_id() == 12 || s.getSkill_id() == 14 || s.getSkill_id() == 18 || s.getSkill_id() == 20 || s.getSkill_id() == 21 || s.getSkill_id() == 24)
            if (id1 == pc.getObjectId())
              id1 = 0;  
          if ((s.getSkill_id() == 6 || s.getSkill_id() == 17) && 
            target instanceof net.world.instance.MonsterInstance)
            id1 = 0; 
          if (id1 != 0) {
            SkillTable.getInstance().getTemplate(cha, getItem().getSkill_id()).toMagic(id1);
            useit = true;
            break;
          } 
          pc.Message("施咒取消。");
          break;
        case 15:
          id2 = bp.readD();
          if (id2 != 0) {
            ItemInstance weapon = cha.getInventory().getItemInvId(id2);
            if (weapon != null && weapon instanceof net.world.instance.ItemWeaponInstance) {
              SkillTable.getInstance().getTemplate(cha, getItem().getSkill_id()).toMagic(weapon.getObjectId());
              useit = true;
              break;
            } 
            pc.Message("施咒取消。");
            break;
          } 
          pc.Message("施咒取消。");
          break;
        case 9:
          id3 = bp.readD();
          if (id3 != 0) {
            ItemInstance armor = cha.getInventory().getItemInvId(id3);
            if (armor != null && armor instanceof net.world.instance.ItemArmorInstance) {
              SkillTable.getInstance().getTemplate(cha, getItem().getSkill_id()).toMagic(armor.getObjectId());
              useit = true;
              break;
            } 
            pc.Message("施咒取消。");
            break;
          } 
          pc.Message("施咒取消。");
          break;
      } 
    } 
    if (useit)
      if (getCount() == 1L) {
        pc.getInventory().remove(this);
      } else {
        setCount((Character)pc, getCount() - 1L);
      }  
  }
}
