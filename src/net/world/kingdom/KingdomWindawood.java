package net.world.kingdom;

public class KingdomWindawood extends Kingdom {
  private static class Holder {
    static KingdomWindawood instance = new KingdomWindawood();
  }
  
  public static KingdomWindawood getInstance() {
    return Holder.instance;
  }
  
  private KingdomWindawood() {
    super(3);
    this.inMap = 29;
    Flag(32571, 32721, 33350, 33460, 4);
    addCastleTop(513, 32669, 33409, 4);
    addDoor(22, 32590, 33408, 4, 6, false);
    addDoor(21, 32678, 33392, 4, 4, true);
    addDoorman(38, 32588, 33407, 4, 6, false);
    addDoorman(38, 32679, 33393, 4, 4, true);
    addGuard(589, 32584, 33405, 4, 6);
    addGuard(588, 32584, 33413, 4, 6);
    addGuard(589, 32673, 33399, 4, 4);
    addGuard(588, 32679, 33399, 4, 4);
    addGuard(39, 32736, 32789, 29, 4);
    addGuard(39, 32733, 32789, 29, 4);
    addGuard(39, 32737, 32794, 29, 6);
    addGuard(39, 32737, 32798, 29, 6);
    addGuard(39, 32737, 32802, 29, 6);
    addGuard(39, 32731, 32794, 29, 2);
    addGuard(39, 32731, 32798, 29, 2);
    addGuard(39, 32731, 32802, 29, 2);
    addIshmael(210, 32737, 32783, 29, 5, "othmond");
  }
}
