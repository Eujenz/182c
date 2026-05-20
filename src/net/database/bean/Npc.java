package net.database.bean;

import java.util.HashMap;
import java.util.Map;

public class Npc {
  private int _npcId;
  
  private String _name;
  
  private String _type;
  
  private boolean ai;
  
  private int _gfxid;
  
  private String _nameid;
  
  private int _nameidN;
  
  private int hp;
  
  private int lawful;
  
  private int light;
  
  private int gfxMode;
  
  private boolean die;
  
  private Map<Integer, GfxModeSpeed> modespeed = new HashMap<Integer, GfxModeSpeed>();
  
  public void set_gfxMode(int gfxMode) {
    this.gfxMode = gfxMode;
  }
  
  public int get_gfxMode() {
    return this.gfxMode;
  }
  
  public void set_light(int light) {
    this.light = light;
  }
  
  public int get_light() {
    return this.light;
  }
  
  public void set_ai(boolean ai) {
    this.ai = ai;
  }
  
  public boolean get_ai() {
    return this.ai;
  }
  
  public boolean isDie() {
    return this.die;
  }
  
  public void setDie(boolean die) {
    this.die = die;
  }
  
  public int getLawful() {
    return this.lawful;
  }
  
  public void setLawful(int lawful) {
    this.lawful = lawful;
  }
  
  public String get_name() {
    return this._name;
  }
  
  public void set_name(String _name) {
    this._name = _name;
  }
  
  public int get_npcId() {
    return this._npcId;
  }
  
  public void set_npcId(int id) {
    this._npcId = id;
  }
  
  public String get_type() {
    return this._type;
  }
  
  public void set_type(String _type) {
    this._type = _type;
  }
  
  public int get_gfxid() {
    return this._gfxid;
  }
  
  public void set_gfxid(int _gfxid) {
    this._gfxid = _gfxid;
  }
  
  public String get_nameid() {
    return this._nameid;
  }
  
  public void set_nameid(String _nameid) {
    this._nameid = _nameid;
  }
  
  public void setHp(int hp) {
    this.hp = hp;
  }
  
  public int getHp() {
    return this.hp;
  }
  
  public void set_nameidN(int n) {
    this._nameidN = n;
  }
  
  public int get_nameidN() {
    return this._nameidN;
  }
  
  public void addModespeed(int mode, int speed) {
    this.modespeed.put(Integer.valueOf(mode), new GfxModeSpeed(mode, speed));
  }
  
  public int getModespeed(int mode) {
    GfxModeSpeed gms = this.modespeed.get(Integer.valueOf(mode));
    if (gms == null)
      return 1000; 
    return gms.getSpeed();
  }
}
