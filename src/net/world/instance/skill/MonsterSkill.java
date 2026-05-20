package net.world.instance.skill;

import net.world.instance.MonsterInstance;

public class MonsterSkill extends Skills {
  private MonsterInstance mon;
  
  public MonsterSkill(MonsterInstance mon) {
    this.mon = mon;
  }
  
  public void delete() {
    this.mon = null;
    super.delete();
  }
  
  public void toMagic(int lv, int no, int id) {
    byte b;
    int i;
    Magic[] arrayOfMagic;
    for (i = (arrayOfMagic = getAll()).length, b = 0; b < i; ) {
      Magic m = arrayOfMagic[b];
      if (m.getSkill().getSkill_level() == lv && m.getSkill().getSkill_no() == no) {
        m.toMagic(id);
        break;
      } 
      b++;
    } 
  }
}
