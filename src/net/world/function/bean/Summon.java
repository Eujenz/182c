package net.world.function.bean;

import java.util.ArrayList;
import java.util.List;
import net.world.instance.SummonInstance;
import net.world.object.Character;

public class Summon {
  private Character cha;
  
  private List<SummonInstance> list = new ArrayList<SummonInstance>();
  
  public Summon(Character cha) {
    this.cha = cha;
  }
  
  public Character getCha() {
    return this.cha;
  }
  
  public void setCha(Character cha) {
    this.cha = cha;
  }
  
  public SummonInstance[] getList() {
    return this.list.<SummonInstance>toArray(new SummonInstance[this.list.size()]);
  }
  
  public int getListSize() {
    return this.list.size();
  }
  
  public void add(SummonInstance sum) {
    this.list.add(sum);
  }
  
  public void remove(SummonInstance sum) {
    this.list.remove(sum);
    sum.Dismiss();
  }
  
  public void clear() {
    byte b;
    int i;
    SummonInstance[] arrayOfSummonInstance;
    for (i = (arrayOfSummonInstance = getList()).length, b = 0; b < i; ) {
      SummonInstance s = arrayOfSummonInstance[b];
      s.Dismiss();
      b++;
    } 
    this.list.clear();
    this.list = null;
  }
  
  public void Aggressive() {
    byte b;
    int i;
    SummonInstance[] arrayOfSummonInstance;
    for (i = (arrayOfSummonInstance = getList()).length, b = 0; b < i; ) {
      SummonInstance s = arrayOfSummonInstance[b];
      s.set_Status(1);
      b++;
    } 
  }
  
  public void Defensive() {
    byte b;
    int i;
    SummonInstance[] arrayOfSummonInstance;
    for (i = (arrayOfSummonInstance = getList()).length, b = 0; b < i; ) {
      SummonInstance s = arrayOfSummonInstance[b];
      s.set_Status(2);
      b++;
    } 
  }
  
  public void Stay() {
    byte b;
    int i;
    SummonInstance[] arrayOfSummonInstance;
    for (i = (arrayOfSummonInstance = getList()).length, b = 0; b < i; ) {
      SummonInstance s = arrayOfSummonInstance[b];
      s.set_Status(0);
      b++;
    } 
  }
  
  public void Extend() {
    byte b;
    int i;
    SummonInstance[] arrayOfSummonInstance;
    for (i = (arrayOfSummonInstance = getList()).length, b = 0; b < i; ) {
      SummonInstance s = arrayOfSummonInstance[b];
      s.set_Status(3);
      b++;
    } 
  }
  
  public void Alert() {
    byte b;
    int i;
    SummonInstance[] arrayOfSummonInstance;
    for (i = (arrayOfSummonInstance = getList()).length, b = 0; b < i; ) {
      SummonInstance s = arrayOfSummonInstance[b];
      s.set_Status(4);
      b++;
    } 
  }
  
  public void Getitem() {
    byte b;
    int i;
    SummonInstance[] arrayOfSummonInstance;
    for (i = (arrayOfSummonInstance = getList()).length, b = 0; b < i; ) {
      SummonInstance s = arrayOfSummonInstance[b];
      s.set_Status(5);
      b++;
    } 
  }
}
