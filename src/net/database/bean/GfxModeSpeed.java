package net.database.bean;

public class GfxModeSpeed {
  private int mode;
  
  private int speed;
  
  public GfxModeSpeed(int mode, int speed) {
    this.mode = mode;
    this.speed = speed;
  }
  
  public int getMode() {
    return this.mode;
  }
  
  public void setMode(int mode) {
    this.mode = mode;
  }
  
  public int getSpeed() {
    return this.speed;
  }
  
  public void setSpeed(int speed) {
    this.speed = speed;
  }
}
