package net.util;

import net.world.instance.PcInstance;

public final class CalcStat {
  public static final int calcAc(PcInstance pc) {
    int acBonus = 0;
    int level = pc.getLevel();
    switch (pc.getDex()) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
        acBonus += level >> 3;
        return acBonus;
      case 10:
      case 11:
      case 12:
        acBonus += level / 7;
        return acBonus;
      case 13:
      case 14:
      case 15:
        acBonus += level / 6;
        return acBonus;
      case 16:
      case 17:
        acBonus += level / 5;
        return acBonus;
    } 
    acBonus += level >> 2;
    return acBonus;
  }
  
  public static final int calcMr(int wis) {
    int mrBonus = 0;
    switch (wis) {
      case 13:
      case 14:
        mrBonus = 1;
        return mrBonus - 1;
      case 15:
      case 16:
        mrBonus = 3;
        return mrBonus - 1;
      case 17:
        mrBonus = 6;
        return mrBonus - 1;
      case 18:
        mrBonus = 10;
        return mrBonus - 1;
      case 19:
        mrBonus = 15;
        return mrBonus - 1;
      case 20:
        mrBonus = 21;
        return mrBonus - 1;
      case 21:
        mrBonus = 28;
        return mrBonus - 1;
      case 22:
        mrBonus = 37;
        return mrBonus - 1;
      case 23:
        mrBonus = 47;
        return mrBonus - 1;
      case 24:
      case 25:
        mrBonus = 50;
        return mrBonus - 1;
    } 
    mrBonus = 1;
    return mrBonus - 1;
  }
}
