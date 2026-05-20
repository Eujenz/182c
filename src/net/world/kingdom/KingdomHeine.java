package net.world.kingdom;

public class KingdomHeine extends Kingdom {
  private static class Holder {
    static KingdomHeine instance = new KingdomHeine();
  }
  
  public static KingdomHeine getInstance() {
    return Holder.instance;
  }
  
  private KingdomHeine() {
    super(5);
    this.inMap = 64;
    Flag(33458, 33583, 33315, 33490, 4);
  }
}
