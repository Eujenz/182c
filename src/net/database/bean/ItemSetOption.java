package net.database.bean;

import java.util.HashMap;
import java.util.Map;
import net.world.instance.PcInstance;

public class ItemSetOption {
  private Map<Integer, PcInstance> list = new HashMap<Integer, PcInstance>();
  
  private int uid;
  
  private String name;
  
  private int count;
  
  private int addMp;
  
  private int addHp;
  
  private int addStr;
  
  private int addDex;
  
  private int addCon;
  
  private int addInt;
  
  private int addWis;
  
  private int addCha;
  
  private int addAc;
  
  private int addMr;
  
  private int ticHp;
  
  private int ticMp;
  
  private int polymorph;
  
  private int wisdress;
  
  private int wateress;
  
  private int fireress;
  
  private int earthress;
  
  private boolean gm;
  
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
  
  public int getCount() {
    return this.count;
  }
  
  public void setCount(int count) {
    this.count = count;
  }
  
  public int getAddMp() {
    return this.addMp;
  }
  
  public void setAddMp(int addMp) {
    this.addMp = addMp;
  }
  
  public int getAddHp() {
    return this.addHp;
  }
  
  public void setAddHp(int addHp) {
    this.addHp = addHp;
  }
  
  public int getAddStr() {
    return this.addStr;
  }
  
  public void setAddStr(int addStr) {
    this.addStr = addStr;
  }
  
  public int getAddDex() {
    return this.addDex;
  }
  
  public void setAddDex(int addDex) {
    this.addDex = addDex;
  }
  
  public int getAddCon() {
    return this.addCon;
  }
  
  public void setAddCon(int addCon) {
    this.addCon = addCon;
  }
  
  public int getAddInt() {
    return this.addInt;
  }
  
  public void setAddInt(int addInt) {
    this.addInt = addInt;
  }
  
  public int getAddWis() {
    return this.addWis;
  }
  
  public void setAddWis(int addWis) {
    this.addWis = addWis;
  }
  
  public int getAddCha() {
    return this.addCha;
  }
  
  public void setAddCha(int addCha) {
    this.addCha = addCha;
  }
  
  public int getAddAc() {
    return this.addAc;
  }
  
  public void setAddAc(int addAc) {
    this.addAc = addAc;
  }
  
  public int getAddMr() {
    return this.addMr;
  }
  
  public void setAddMr(int addMr) {
    this.addMr = addMr;
  }
  
  public int getTicHp() {
    return this.ticHp;
  }
  
  public void setTicHp(int ticHp) {
    this.ticHp = ticHp;
  }
  
  public int getTicMp() {
    return this.ticMp;
  }
  
  public void setTicMp(int ticMp) {
    this.ticMp = ticMp;
  }
  
  public int getPolymorph() {
    return this.polymorph;
  }
  
  public void setPolymorph(int polymorph) {
    this.polymorph = polymorph;
  }
  
  public int getWisdress() {
    return this.wisdress;
  }
  
  public void setWisdress(int wisdress) {
    this.wisdress = wisdress;
  }
  
  public int getWateress() {
    return this.wateress;
  }
  
  public void setWateress(int wateress) {
    this.wateress = wateress;
  }
  
  public int getFireress() {
    return this.fireress;
  }
  
  public void setFireress(int fireress) {
    this.fireress = fireress;
  }
  
  public int getEarthress() {
    return this.earthress;
  }
  
  public void setEarthress(int earthress) {
    this.earthress = earthress;
  }
  
  public boolean isGm() {
    return this.gm;
  }
  
  public void setGm(boolean gm) {
    this.gm = gm;
  }
  
  public Map<Integer, PcInstance> getList() {
    return this.list;
  }
  
  public boolean isList(PcInstance pc) {
    return this.list.containsKey(Integer.valueOf(pc.getObjectId()));
  }
  
  public void addList(PcInstance pc) {
    this.list.put(Integer.valueOf(pc.getObjectId()), pc);
  }
  
  public void removeList(PcInstance pc) {
    this.list.remove(Integer.valueOf(pc.getObjectId()));
  }
}
