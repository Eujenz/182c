package net.world.kingdom;

public class KingdomGiran extends Kingdom {
  private static class Holder {
    static KingdomGiran instance = new KingdomGiran();
  }
  
  public static KingdomGiran getInstance() {
    return Holder.instance;
  }
  
  private KingdomGiran() {
    super(4);
    this.inMap = 52;
    Flag(33559, 33686, 32615, 32755, 4);
  }
}
