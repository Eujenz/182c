package net.world.instance.bean;

public class Craft {
  private String name;
  
  private int itemnameId;
  
  private int count;
  
  private StringBuilder tostring;
  
  public Craft(String name, int itemnameId, int count) {
    this.name = name;
    this.itemnameId = itemnameId;
    this.count = count;
    this.tostring = new StringBuilder();
    this.tostring.append("$");
    this.tostring.append(itemnameId);
    this.tostring.append(" (");
    this.tostring.append(count);
    this.tostring.append(")");
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public int getItemnameId() {
    return this.itemnameId;
  }
  
  public void setItemnameId(int itemnameId) {
    this.itemnameId = itemnameId;
  }
  
  public int getCount() {
    return this.count;
  }
  
  public void setCount(int count) {
    this.count = count;
  }
  
  public String toString() {
    return this.tostring.toString();
  }
  
  public String toString(int count) {
    StringBuilder ts = new StringBuilder();
    ts.append(this.name);
    ts.append(" (");
    ts.append(count);
    ts.append(")");
    return ts.toString();
  }
}
