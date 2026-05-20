package net.database.bean;

public final class L1GetBackRestart {
  private int area;
  
  private String note;
  
  private int locX;
  
  private int locY;
  
  private int mapId;
  
  public int getArea() {
    return this.area;
  }
  
  public int getLocX() {
    return this.locX;
  }
  
  public int getLocY() {
    return this.locY;
  }
  
  public int getMapId() {
    return this.mapId;
  }
  
  public String getNote() {
    return this.note;
  }
  
  public void setArea(int i) {
    this.area = i;
  }
  
  public void setLocX(int i) {
    this.locX = i;
  }
  
  public void setLocY(int i) {
    this.locY = i;
  }
  
  public void setMapId(int i) {
    this.mapId = i;
  }
  
  public void setNote(String s) {
    this.note = s;
  }
}
