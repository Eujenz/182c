package net.util.bean;

import java.util.HashMap;
import java.util.Map;

public class GfxFrameList {
  private int gfxID;
  
  private String name;
  
  public Map<Integer, GfxFrameMode> mode = new HashMap<Integer, GfxFrameMode>();
  
  public int getGfxID() {
    return this.gfxID;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setGfxID(int gfxID) {
    this.gfxID = gfxID;
  }
  
  public void setName(String name) {
    this.name = name;
  }
}
