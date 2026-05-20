package net.database.bean;

public class Exp {
  int _id;
  
  int _level;
  
  long _exp;
  
  long _bonus;
  
  public int get_id() {
    return this._id;
  }
  
  public void set_id(int id) {
    this._id = id;
  }
  
  public int get_level() {
    return this._level;
  }
  
  public void set_level(int level) {
    this._level = level;
  }
  
  public long get_exp() {
    return this._exp;
  }
  
  public void set_exp(long exp) {
    this._exp = exp;
  }
  
  public long get_bonus() {
    return this._bonus;
  }
  
  public void set_bonus(long bonus) {
    this._bonus = bonus;
  }
}
