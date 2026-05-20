package net.world.kingdom;

public class KingdomAbyss extends Kingdom {
  public static final int[] LOC_FLAG = new int[] { 32758, 32870, 32790, 32919, 66 };
  
  private static class Holder {
    static KingdomAbyss instance = new KingdomAbyss();
  }
  
  public static KingdomAbyss getInstance() {
    return Holder.instance;
  }
  
  private KingdomAbyss() {
    super(6);
    this.inMap = 66;
    Flag(LOC_FLAG[0], LOC_FLAG[1], LOC_FLAG[2], LOC_FLAG[3], LOC_FLAG[4]);
    addCastleTop(513, 32829, 32818, 66);
    addDoor(590, 32780, 32858, 66, 6, false);
    addDoor(591, 32812, 32887, 66, 4, false);
    addDoorman(593, 32814, 32895, 66, 6, false);
    addDoorman(593, 32810, 32877, 66, 2, false);
    addDoorman(593, 32789, 32859, 66, 0, false);
    addDoorman(593, 32774, 32857, 66, 4, false);
    addDoorman(593, 32852, 32806, 66, 2, true);
    addDoorman(593, 32843, 32814, 66, 6, true);
    addGuard(515, 32807, 32893, 66, 4);
    addGuard(515, 32815, 32892, 66, 4);
    addGuard(515, 32815, 32822, 66, 6);
    addGuard(515, 32840, 32810, 66, 6);
    addGuard(515, 32840, 32818, 66, 6);
    addGuard(515, 32825, 32833, 66, 4);
    addGuard(515, 32775, 32865, 66, 6);
    addGuard(515, 32775, 32855, 66, 6);
    addIshmael(463, 32865, 32808, 66, 6, "potempin");
  }
}
