package net.world.time.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.world.instance.skill.Magic;
import net.world.object.L1Object;

public class BuffTimer {
  private L1Object o;
  
  private List<Magic> list;
  
  private Map<Integer, Integer> time;
  
  public BuffTimer(L1Object o, Magic m) {
    this.list = new ArrayList<Magic>();
    this.time = new HashMap<Integer, Integer>();
    this.o = o;
    add(m);
  }
  
  public L1Object getObject() {
    return this.o;
  }
  
  public void setObject(L1Object o) {
    this.o = o;
  }
  
  public void add(Magic m) {
    if (!contains(m)) {
      this.list.add(m);
      this.time.put(Integer.valueOf(m.getSkill().getSkill_id()), Integer.valueOf(m.getTime()));
    } 
  }
  
  public void remove(Magic m) {
    byte b;
    int i;
    Magic[] arrayOfMagic;
    for (i = (arrayOfMagic = getList()).length, b = 0; b < i; ) {
      Magic mm = arrayOfMagic[b];
      if (mm.getSkill().getSkill_id() == m.getSkill().getSkill_id()) {
        this.list.remove(mm);
        this.time.remove(Integer.valueOf(mm.getSkill().getSkill_id()));
      } 
      b++;
    } 
  }
  
  public boolean contains(Magic m) {
    byte b;
    int i;
    Magic[] arrayOfMagic;
    for (i = (arrayOfMagic = getList()).length, b = 0; b < i; ) {
      Magic mm = arrayOfMagic[b];
      if (mm.getSkill().getSkill_id() == m.getSkill().getSkill_id())
        return true; 
      b++;
    } 
    return false;
  }
  
  public Magic[] getList() {
    return this.list.<Magic>toArray(new Magic[this.list.size()]);
  }
  
  public int getCount() {
    return this.list.size();
  }
  
  public int getTime(Magic m) {
    byte b;
    int i;
    Magic[] arrayOfMagic;
    for (i = (arrayOfMagic = getList()).length, b = 0; b < i; ) {
      Magic mm = arrayOfMagic[b];
      if (mm.getSkill().getSkill_id() == m.getSkill().getSkill_id()) {
        Integer time = this.time.get(Integer.valueOf(mm.getSkill().getSkill_id()));
        if (time != null) {
          this.time.put(Integer.valueOf(m.getSkill().getSkill_id()), Integer.valueOf(time.intValue() - 1));
          return time.intValue();
        } 
        return 0;
      } 
      b++;
    } 
    return 0;
  }
  
  public void clear() {
    byte b;
    int i;
    Magic[] arrayOfMagic;
    for (i = (arrayOfMagic = getList()).length, b = 0; b < i; ) {
      Magic m = arrayOfMagic[b];
      m.isTimerStop(getObject());
      b++;
    } 
    this.time.clear();
    this.list.clear();
    this.list = null;
    this.time = null;
    this.o = null;
  }
}
