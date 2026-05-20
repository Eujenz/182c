package net.world.instance;

import net.world.object.L1Object;

public class SlimeraceInstance extends L1Object {
  public boolean finish;
  
  public boolean Lucky;
  
  public int Status;
  
  public double Theory;
  
  public int idx;
  
  public void clean() {
    this.finish = false;
    this.Lucky = false;
    this.Status = 0;
    this.Theory = 0.0D;
  }
}
