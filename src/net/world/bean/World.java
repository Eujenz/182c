package net.world.bean;

import java.util.ArrayList;
import java.util.List;
import net.world.object.L1Object;

public class World {
  private int map;
  
  private List<L1Object> list = new ArrayList<L1Object>();
  
  private List<L1Object> add_list = new ArrayList<L1Object>();
  
  private List<L1Object> remove_list = new ArrayList<L1Object>();
  
  public World(int map) {
    this.map = map;
  }
  
  public L1Object[] getList() {
    update();
    synchronized (this.list) {
      return this.list.<L1Object>toArray(new L1Object[this.list.size()]);
    } 
  }
  
  public void add(L1Object o) {
    synchronized (this.add_list) {
      this.add_list.add(o);
    } 
  }
  
  public boolean contains(L1Object o) {
    update();
    synchronized (this.list) {
      return this.list.contains(o);
    } 
  }
  
  public void remove(L1Object o) {
    synchronized (this.remove_list) {
      this.remove_list.add(o);
    } 
  }
  
  public int size() {
    return this.list.size();
  }
  
  public int getMap() {
    return this.map;
  }
  
  public void setMap(int map) {
    this.map = map;
  }
  
  private void update() {
    synchronized (this.remove_list) {
      byte b;
      int i;
      L1Object[] arrayOfL1Object;
      for (i = (arrayOfL1Object = this.remove_list.<L1Object>toArray(new L1Object[this.remove_list.size()])).length, b = 0; b < i; ) {
        L1Object o = arrayOfL1Object[b];
        synchronized (this.list) {
          this.list.remove(o);
        } 
        b++;
      } 
      this.remove_list.clear();
    } 
    synchronized (this.add_list) {
      byte b;
      int i;
      L1Object[] arrayOfL1Object;
      for (i = (arrayOfL1Object = this.add_list.<L1Object>toArray(new L1Object[this.add_list.size()])).length, b = 0; b < i; ) {
        L1Object o = arrayOfL1Object[b];
        synchronized (this.list) {
          this.list.add(o);
        } 
        b++;
      } 
      this.add_list.clear();
    } 
  }
}
