package net.world.instance.skill.function.elf;

import net.database.bean.Skill;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;

public final class WindWalk extends Magic {
  public WindWalk(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(L1Object o, int id) {}
}
