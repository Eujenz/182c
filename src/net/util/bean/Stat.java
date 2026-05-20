package net.util.bean;

public class Stat {
  private int Str;
  
  private int Dex;
  
  private int Con;
  
  private int Wis;
  
  private int Int;
  
  private int Cha;
  
  private int Hp;
  
  private int Mp;
  
  private int type;
  
  private int male;
  
  private int female;
  
  private int x;
  
  private int y;
  
  private int map;
  
  public int getStr() {
    return this.Str;
  }
  
  public void setStr(int str) {
    this.Str = str;
  }
  
  public int getDex() {
    return this.Dex;
  }
  
  public void setDex(int dex) {
    this.Dex = dex;
  }
  
  public int getCon() {
    return this.Con;
  }
  
  public void setCon(int con) {
    this.Con = con;
  }
  
  public int getWis() {
    return this.Wis;
  }
  
  public void setWis(int wis) {
    this.Wis = wis;
  }
  
  public int getInt() {
    return this.Int;
  }
  
  public void setInt(int i) {
    this.Int = i;
  }
  
  public int getCha() {
    return this.Cha;
  }
  
  public void setCha(int cha) {
    this.Cha = cha;
  }
  
  public int getHp() {
    return this.Hp;
  }
  
  public void setHp(int hp) {
    this.Hp = hp;
  }
  
  public int getMp() {
    return this.Mp;
  }
  
  public void setMp(int mp) {
    this.Mp = mp;
  }
  
  public int getType() {
    return this.type;
  }
  
  public void setType(int type) {
    this.type = type;
  }
  
  public int getMale() {
    return this.male;
  }
  
  public void setMale(int male) {
    this.male = male;
  }
  
  public int getFemale() {
    return this.female;
  }
  
  public void setFemale(int female) {
    this.female = female;
  }
  
  public int getStat() {
    return getStr() + getDex() + getCon() + getWis() + getInt() + getCha();
  }
  
  public int getX() {
    return this.x;
  }
  
  public void setX(int x) {
    this.x = x;
  }
  
  public int getY() {
    return this.y;
  }
  
  public void setY(int y) {
    this.y = y;
  }
  
  public int getMap() {
    return this.map;
  }
  
  public void setMap(int map) {
    this.map = map;
  }
}
