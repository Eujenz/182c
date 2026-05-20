package net.world.function.bean;

import java.util.ArrayList;
import java.util.List;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;

public class Trade {
  private PcInstance cha;
  
  private PcInstance use;
  
  private boolean cha_ok;
  
  private boolean use_ok;
  
  private List<ItemInstance> cha_list = new ArrayList<ItemInstance>();
  
  private List<ItemInstance> use_list = new ArrayList<ItemInstance>();
  
  public PcInstance getCha() {
    return this.cha;
  }
  
  public void setCha(PcInstance cha) {
    this.cha = cha;
  }
  
  public PcInstance getUse() {
    return this.use;
  }
  
  public void setUse(PcInstance use) {
    this.use = use;
  }
  
  public boolean isCha_ok() {
    return this.cha_ok;
  }
  
  public void setCha_ok(boolean cha_ok) {
    this.cha_ok = cha_ok;
  }
  
  public boolean isUse_ok() {
    return this.use_ok;
  }
  
  public void setUse_ok(boolean use_ok) {
    this.use_ok = use_ok;
  }
  
  public List<ItemInstance> getCha_list() {
    return this.cha_list;
  }
  
  public List<ItemInstance> getUse_list() {
    return this.use_list;
  }
  
  public void addCha_list(ItemInstance item) {
    this.cha_list.add(item);
  }
  
  public void addUse_list(ItemInstance item) {
    this.use_list.add(item);
  }
  
  public void clear() {
    this.cha_list.clear();
    this.use_list.clear();
    this.cha_list = null;
    this.use_list = null;
  }
}
