package net.world.instance.inventory.function;

import net.database.SkillTable;
import net.database.bean.Item;
import net.world.instance.ItemArmorInstance;
import net.world.object.Character;

public class HelmMagicPower extends ItemArmorInstance {
  public HelmMagicPower(Item i) {
    super(i);
  }
  
  public void itemOption(Character cha) {
    super.itemOption(cha);
    if (isEquipped()) {
      boolean flag = false;
      if (!cha.getSkill().isHaveMagic(9)) {
        cha.getSkill().add(SkillTable.getInstance().getTemplate(cha, 9));
        cha.getSkill().addOfHelmMagic(SkillTable.getInstance().getTemplate(cha, 9));
        flag = true;
      } 
      if (!cha.getSkill().isHaveMagic(10)) {
        cha.getSkill().add(SkillTable.getInstance().getTemplate(cha, 10));
        cha.getSkill().addOfHelmMagic(SkillTable.getInstance().getTemplate(cha, 10));
        flag = true;
      } 
      if (!cha.getSkill().isHaveMagic(27)) {
        cha.getSkill().add(SkillTable.getInstance().getTemplate(cha, 27));
        cha.getSkill().addOfHelmMagic(SkillTable.getInstance().getTemplate(cha, 27));
        flag = true;
      } 
      if (flag)
        cha.getSkill().sendList(); 
    } else {
      if (cha.getSkill().isHaveMagicOfHelmMagic(9)) {
        cha.getSkill().remove(9);
        cha.getSkill().removeOfHelmMagic(9);
      } 
      if (cha.getSkill().isHaveMagicOfHelmMagic(10)) {
        cha.getSkill().remove(10);
        cha.getSkill().removeOfHelmMagic(10);
      } 
      if (cha.getSkill().isHaveMagicOfHelmMagic(27)) {
        cha.getSkill().remove(27);
        cha.getSkill().removeOfHelmMagic(27);
      } 
    } 
  }
}
