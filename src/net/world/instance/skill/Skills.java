package net.world.instance.skill;

import java.util.HashMap;
import java.util.Map;

public class Skills {
  private Map<Integer, Magic> list = new HashMap<Integer, Magic>();
  
  private Map<Integer, Magic> list_HelmMagic = new HashMap<Integer, Magic>();
  
  public boolean isGerengMagic() {
    return false;
  }
  
  public boolean isHaveMagic(int skill_id) {
    Magic m = get(skill_id);
    if (m != null)
      return true; 
    return false;
  }
  
  public Magic[] getAll() {
    return (Magic[])this.list.values().toArray((Object[])new Magic[this.list.size()]);
  }
  
  public int getCount() {
    return this.list.size();
  }
  
  public void add(Magic m) {
    this.list.put(Integer.valueOf(m.getSkill().getSkill_id()), m);
  }
  
  public Magic get(int skill_id) {
    return this.list.get(Integer.valueOf(skill_id));
  }
  
  public void remove(Magic m) {
    this.list.remove(Integer.valueOf(m.getSkill().getSkill_id()));
  }
  
  public void remove(int skill_id) {
    this.list.remove(Integer.valueOf(skill_id));
  }
  
  public void delete() {
    this.list.clear();
    this.list = null;
  }
  
  public boolean isHaveMagicOfHelmMagic(int skill_id) {
    Magic m = getOfHelmMagic(skill_id);
    if (m != null)
      return true; 
    return false;
  }
  
  public Magic[] getAllOfHelmMagic() {
    return (Magic[])this.list_HelmMagic.values().toArray((Object[])new Magic[this.list_HelmMagic.size()]);
  }
  
  public int getCountOfHelmMagic() {
    return this.list_HelmMagic.size();
  }
  
  public void addOfHelmMagic(Magic m) {
    this.list_HelmMagic.put(Integer.valueOf(m.getSkill().getSkill_id()), m);
  }
  
  public Magic getOfHelmMagic(int skill_id) {
    return this.list_HelmMagic.get(Integer.valueOf(skill_id));
  }
  
  public void removeOfHelmMagic(Magic m) {
    this.list_HelmMagic.remove(Integer.valueOf(m.getSkill().getSkill_id()));
  }
  
  public void removeOfHelmMagic(int skill_id) {
    this.list_HelmMagic.remove(Integer.valueOf(skill_id));
  }
  
  public void deleteOfHelmMagic() {
    this.list_HelmMagic.clear();
    this.list_HelmMagic = null;
  }
  
  public void sendList() {}
  
  public void save() {}
  
  public void read() {}
  
  public void toMagic(int lv, int no, int id, int x, int y) {}
}
