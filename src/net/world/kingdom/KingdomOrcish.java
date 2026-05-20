package net.world.kingdom;

public class KingdomOrcish extends Kingdom {
  private static class Holder {
    static KingdomOrcish instance = new KingdomOrcish();
  }
  
  public static KingdomOrcish getInstance() {
    return Holder.instance;
  }
  
  private KingdomOrcish() {
    super(2);
    this.inMap = 4;
    Flag(32750, 32850, 32250, 32350, 4);
  }
}
