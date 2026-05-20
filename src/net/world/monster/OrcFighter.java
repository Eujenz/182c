package net.world.monster;

import net.database.bean.Monster;
import net.world.instance.MonsterInstance;

public class OrcFighter extends MonsterInstance {
  public OrcFighter(Monster m) {
    super(m);
    setClassGfxMode(4);
    setGfxMode(4);
  }
}
