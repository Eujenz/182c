package net.world.drop;

import net.world.object.L1Object;

public final class NpcDrop {
  private L1Object cha;
  
  private double dmg;
  
  private double totalDmg;
  
  public NpcDrop(L1Object pc) {
    this.cha = pc;
  }
  
  public L1Object getCha() {
    return this.cha;
  }
  
  public void setCha(L1Object cha) {
    this.cha = cha;
  }
  
  public double getDmg() {
    return this.dmg;
  }
  
  public void setDmg(double dmg) {
    this.dmg = dmg;
  }
  
  public double getTotalDmg() {
    return this.totalDmg;
  }
  
  public void setTotalDmg(double totalDmg) {
    this.totalDmg += totalDmg;
    if (totalDmg == -1.0D)
      this.totalDmg = 0.0D; 
  }
}
