package net.database.bean;

import java.util.HashMap;
import java.util.Map;

public class Monster {
  private int uid;
  
  private String name;
  
  private String nameid;
  
  private int nameidN;
  
  private int gfx;
  
  private int level;
  
  private int hp;
  
  private int mp;
  
  private int minDmg;
  
  private int maxDmg;
  
  private int ac;
  
  private int mr;
  
  private long exp;
  
  private int lawful;
  
  private String size;
  
  private boolean die;
  
  private boolean tribal;
  
  private int tribalID;
  
  private boolean agro;
  
  private boolean poly;
  
  private boolean itempick;
  
  private boolean tameable;
  
  private int RunType;
  
  private boolean attack;
  
  private int areaatk;
  
  private boolean resurrection;
  
  private boolean toughskin;
  
  private boolean isUndead;
  
  private int dropAdena;
  
  private Map<Integer, GfxModeSpeed> modespeed = new HashMap<Integer, GfxModeSpeed>();
  
  private int spawnLoc;
  
  private int spawnX;
  
  private int spawnY;
  
  private int spawnTime;
  
  private boolean spawnRandom;
  
  public int getUid() {
    return this.uid;
  }
  
  public void setUid(int uid) {
    this.uid = uid;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getNameid() {
    return this.nameid;
  }
  
  public void setNameid(String nameid) {
    this.nameid = nameid;
  }
  
  public int getNameidN() {
    return this.nameidN;
  }
  
  public void setNameidN(int nameidN) {
    this.nameidN = nameidN;
  }
  
  public int getGfx() {
    return this.gfx;
  }
  
  public void setGfx(int gfx) {
    this.gfx = gfx;
  }
  
  public int getLevel() {
    return this.level;
  }
  
  public void setLevel(int level) {
    this.level = level;
  }
  
  public int getHp() {
    return this.hp;
  }
  
  public void setHp(int hp) {
    this.hp = hp;
  }
  
  public int getMp() {
    return this.mp;
  }
  
  public void setMp(int mp) {
    this.mp = mp;
  }
  
  public int getMinDmg() {
    return this.minDmg;
  }
  
  public void setMinDmg(int minDmg) {
    this.minDmg = minDmg;
  }
  
  public int getMaxDmg() {
    return this.maxDmg;
  }
  
  public void setMaxDmg(int maxDmg) {
    this.maxDmg = maxDmg;
  }
  
  public int getAc() {
    return this.ac;
  }
  
  public void setAc(int ac) {
    this.ac = ac;
  }
  
  public int getMr() {
    return this.mr;
  }
  
  public void setMr(int mr) {
    this.mr = mr;
  }
  
  public long getExp() {
    return this.exp;
  }
  
  public void setExp(long exp) {
    this.exp = exp;
  }
  
  public int getLawful() {
    return this.lawful;
  }
  
  public void setLawful(int lawful) {
    this.lawful = lawful;
  }
  
  public String getSize() {
    return this.size;
  }
  
  public void setSize(String size) {
    this.size = size;
  }
  
  public boolean isDie() {
    return this.die;
  }
  
  public void setDie(boolean die) {
    this.die = die;
  }
  
  public boolean isTribal() {
    return this.tribal;
  }
  
  public void setTribal(boolean tribal) {
    this.tribal = tribal;
  }
  
  public int getTribalID() {
    return this.tribalID;
  }
  
  public void setTribalID(int tribalID) {
    this.tribalID = tribalID;
  }
  
  public boolean isAgro() {
    return this.agro;
  }
  
  public void setAgro(boolean agro) {
    this.agro = agro;
  }
  
  public boolean isPoly() {
    return this.poly;
  }
  
  public void setPoly(boolean poly) {
    this.poly = poly;
  }
  
  public boolean isItempick() {
    return this.itempick;
  }
  
  public void setItempick(boolean itempick) {
    this.itempick = itempick;
  }
  
  public boolean isTameable() {
    return this.tameable;
  }
  
  public void setTameable(boolean tameable) {
    this.tameable = tameable;
  }
  
  public int getRunType() {
    return this.RunType;
  }
  
  public void setRunType(int runType) {
    this.RunType = runType;
  }
  
  public boolean isAttack() {
    return this.attack;
  }
  
  public void setAttack(boolean attack) {
    this.attack = attack;
  }
  
  public int getAreaatk() {
    return this.areaatk;
  }
  
  public void setAreaatk(int areaatk) {
    this.areaatk = areaatk;
  }
  
  public boolean isResurrection() {
    return this.resurrection;
  }
  
  public void setResurrection(boolean resurrection) {
    this.resurrection = resurrection;
  }
  
  public boolean isToughskin() {
    return this.toughskin;
  }
  
  public void setToughskin(boolean toughskin) {
    this.toughskin = toughskin;
  }
  
  public boolean isUndead() {
    return this.isUndead;
  }
  
  public void setUndead(boolean isUndead) {
    this.isUndead = isUndead;
  }
  
  public int getDropAdena() {
    return this.dropAdena;
  }
  
  public void setDropAdena(int dropAdena) {
    this.dropAdena = dropAdena;
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
  
  public int getSpawnLoc() {
    return this.spawnLoc;
  }
  
  public void setSpawnLoc(int spawnLoc) {
    this.spawnLoc = spawnLoc;
  }
  
  public int getSpawnX() {
    return this.spawnX;
  }
  
  public void setSpawnX(int spawnX) {
    this.spawnX = spawnX;
  }
  
  public int getSpawnY() {
    return this.spawnY;
  }
  
  public void setSpawnY(int spawnY) {
    this.spawnY = spawnY;
  }
  
  public int getSpawnTime() {
    return this.spawnTime;
  }
  
  public void setSpawnTime(int spawnTime) {
    this.spawnTime = spawnTime;
  }
  
  public boolean isSpawnRandom() {
    return this.spawnRandom;
  }
  
  public void setSpawnRandom(boolean spawnRandom) {
    this.spawnRandom = spawnRandom;
  }
}
