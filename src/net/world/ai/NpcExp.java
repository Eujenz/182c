package net.world.ai;

import net.world.object.Character;

public class NpcExp {
  private Character cha;
  
  private double exp;
  
  public NpcExp(Character cha) {
    this.cha = cha;
  }
  
  public Character getCha() {
    return this.cha;
  }
  
  public void setCha(Character cha) {
    this.cha = cha;
  }
  
  public double getExp() {
    return this.exp;
  }
  
  public void setExp(double exp) {
    this.exp = exp;
  }
}
