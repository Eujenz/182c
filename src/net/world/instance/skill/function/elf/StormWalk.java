package net.world.instance.skill.function.elf;

import net.database.bean.Skill;
import net.world.instance.skill.Magic;
import net.world.object.Character;

public class StormWalk extends Magic {
  public StormWalk(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {}
}
