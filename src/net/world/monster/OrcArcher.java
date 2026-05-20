package net.world.monster;

import net.database.bean.Monster;
import net.world.instance.MonsterInstance;

public class OrcArcher extends MonsterInstance {
  public OrcArcher(Monster m) {
    super(m);
    setClassGfxMode(20);
    setGfxMode(20);
  }
}
