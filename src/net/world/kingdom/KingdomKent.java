package net.world.kingdom;

public class KingdomKent extends Kingdom {
  public static final int[] LOC_FLAG = new int[] { 33088, 33198, 32720, 32820, 4 };
  
  private static class Holder {
    static KingdomKent instance = new KingdomKent();
  }
  
  public static KingdomKent getInstance() {
    return Holder.instance;
  }
  
  private KingdomKent() {
    super(1);
    this.inMap = 15;
    Flag(LOC_FLAG[0], LOC_FLAG[1], LOC_FLAG[2], LOC_FLAG[3], LOC_FLAG[4]);
    addCastleTop(513, 33170, 32773, 4);
    addDoor(21, 33171, 32759, 4, 4, true);
    addDoor(22, 33112, 32770, 4, 6, false);
    addDoorman(38, 33172, 32761, 4, 4, true);
    addDoorman(38, 33110, 32769, 4, 6, false);
    addGuard(39, 33081, 32764, 4, 4);
    addGuard(516, 33119, 32760, 4, 6);
    addGuard(516, 33119, 32756, 4, 6);
    addGuard(516, 33119, 32773, 4, 6);
    addGuard(516, 33119, 32777, 4, 6);
    addGuard(517, 33166, 32766, 4, 4);
    addGuard(517, 33172, 32766, 4, 4);
    addGuard(495, 32733, 32788, this.inMap, 0);
    addGuard(495, 32733, 32792, this.inMap, 0);
    addGuard(495, 32733, 32796, this.inMap, 0);
    addGuard(495, 32733, 32800, this.inMap, 0);
    addGuard(495, 32739, 32788, this.inMap, 0);
    addGuard(495, 32739, 32792, this.inMap, 0);
    addGuard(495, 32739, 32796, this.inMap, 0);
    addGuard(495, 32739, 32800, this.inMap, 0);
    addIshmael(19, 32738, 32784, this.inMap, 4, "potempin");
  }
}
