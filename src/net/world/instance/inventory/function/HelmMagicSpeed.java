package net.world.instance.inventory.function;

import net.database.SkillTable;
import net.database.bean.Item;
import net.world.instance.ItemArmorInstance;
import net.world.object.Character;

public class HelmMagicSpeed extends ItemArmorInstance {
  public HelmMagicSpeed(Item i) {
    super(i);
  }
  
  public void itemOption(Character cha) {
    super.itemOption(cha);
    if (isEquipped()) {
      boolean flag = false;
      if (!cha.getSkill().isHaveMagic(17)) {
        cha.getSkill().add(SkillTable.getInstance().getTemplate(cha, 17));
        cha.getSkill().addOfHelmMagic(SkillTable.getInstance().getTemplate(cha, 17));
        flag = true;
      } 
      if (!cha.getSkill().isHaveMagic(28)) {
        cha.getSkill().add(SkillTable.getInstance().getTemplate(cha, 28));
        cha.getSkill().addOfHelmMagic(SkillTable.getInstance().getTemplate(cha, 28));
        flag = true;
      } 
      if (flag)
        cha.getSkill().sendList(); 
    } else {
      if (cha.getSkill().isHaveMagicOfHelmMagic(17)) {
        cha.getSkill().remove(17);
        cha.getSkill().removeOfHelmMagic(17);
      } 
      if (cha.getSkill().isHaveMagicOfHelmMagic(28)) {
        cha.getSkill().remove(28);
        cha.getSkill().removeOfHelmMagic(28);
      } 
    } 
  }
}
