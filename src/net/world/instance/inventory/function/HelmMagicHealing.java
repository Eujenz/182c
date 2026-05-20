package net.world.instance.inventory.function;

import net.database.SkillTable;
import net.database.bean.Item;
import net.world.instance.ItemArmorInstance;
import net.world.object.Character;

public class HelmMagicHealing extends ItemArmorInstance {
  public HelmMagicHealing(Item i) {
    super(i);
  }
  
  public void itemOption(Character cha) {
    super.itemOption(cha);
    if (isEquipped()) {
      boolean flag = false;
      if (!cha.getSkill().isHaveMagic(1)) {
        cha.getSkill().add(SkillTable.getInstance().getTemplate(cha, 1));
        cha.getSkill().addOfHelmMagic(SkillTable.getInstance().getTemplate(cha, 1));
        flag = true;
      } 
      if (!cha.getSkill().isHaveMagic(13)) {
        cha.getSkill().add(SkillTable.getInstance().getTemplate(cha, 13));
        cha.getSkill().addOfHelmMagic(SkillTable.getInstance().getTemplate(cha, 13));
        flag = true;
      } 
      if (flag)
        cha.getSkill().sendList(); 
    } else {
      if (cha.getSkill().isHaveMagicOfHelmMagic(1)) {
        cha.getSkill().remove(1);
        cha.getSkill().removeOfHelmMagic(1);
      } 
      if (cha.getSkill().isHaveMagicOfHelmMagic(13)) {
        cha.getSkill().remove(13);
        cha.getSkill().removeOfHelmMagic(13);
      } 
    } 
  }
}
