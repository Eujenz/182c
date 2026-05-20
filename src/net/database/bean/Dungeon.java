package net.database.bean;

public class Dungeon {
  int _id;
  
  int _x;
  
  int _y;
  
  int _mapid;
  
  int _tx;
  
  int _ty;
  
  int _tmapid;
  
  int _theading;
  
  boolean ck;
  
  int _item_id;
  
  int _item_type;
  
  int _item_count;
  
  public int get_item_id() {
    return this._item_id;
  }
  
  public void set_item_id(int _item_id) {
    this._item_id = _item_id;
  }
  
  public int get_item_type() {
    return this._item_type;
  }
  
  public void set_item_type(int _item_type) {
    this._item_type = _item_type;
  }
  
  public int get_item_count() {
    return this._item_count;
  }
  
  public void set_item_count(int _item_count) {
    this._item_count = _item_count;
  }
  
  public int get_tx() {
    return this._tx;
  }
  
  public void set_tx(int _tx) {
    this._tx = _tx;
  }
  
  public int get_ty() {
    return this._ty;
  }
  
  public void set_ty(int _ty) {
    this._ty = _ty;
  }
  
  public int get_tmapid() {
    return this._tmapid;
  }
  
  public void set_tmapid(int _tmapid) {
    this._tmapid = _tmapid;
  }
  
  public int get_theading() {
    return this._theading;
  }
  
  public void set_theading(int _theading) {
    this._theading = _theading;
  }
  
  public int get_id() {
    return this._id;
  }
  
  public void set_id(int id) {
    this._id = id;
  }
  
  public int get_x() {
    return this._x;
  }
  
  public void set_x(int x) {
    this._x = x;
  }
  
  public int get_y() {
    return this._y;
  }
  
  public void set_y(int y) {
    this._y = y;
  }
  
  public int get_mapid() {
    return this._mapid;
  }
  
  public void set_mapid(int mapid) {
    this._mapid = mapid;
  }
  
  public boolean get_ck() {
    return this.ck;
  }
  
  public void set_ck(boolean ck) {
    this.ck = ck;
  }
}
