package net.database.bean;

public class L1Map {
  int _mapid;
  
  int locX1;
  
  int locX2;
  
  int locY1;
  
  int locY2;
  
  byte[] data;
  
  int size;
  
  public int getSize() {
    return this.size;
  }
  
  public void setSize(int size) {
    this.size = size;
  }
  
  public void set_mapid(int id) {
    this._mapid = id;
  }
  
  public int get_mapid() {
    return this._mapid;
  }
  
  public void set_locX1(int locX1) {
    this.locX1 = locX1;
  }
  
  public int get_locX1() {
    return this.locX1;
  }
  
  public void set_locX2(int locX2) {
    this.locX2 = locX2;
  }
  
  public int get_locX2() {
    return this.locX2;
  }
  
  public void set_locY1(int locY1) {
    this.locY1 = locY1;
  }
  
  public int get_locY1() {
    return this.locY1;
  }
  
  public void set_locY2(int locY2) {
    this.locY2 = locY2;
  }
  
  public int get_locY2() {
    return this.locY2;
  }
  
  public void set_byte(byte[] data) {
    this.data = data;
  }
  
  public void set_byte(int loc, byte val) {
    this.data[loc] = val;
  }
  
  public byte get_byte(int loc) {
    return this.data[loc];
  }
  
  public byte[] get_byte() {
    return this.data;
  }
}
