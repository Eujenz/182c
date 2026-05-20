package net.world.instance.bean;

import net.database.bean.Item;

public class Shop {
  private int uid;
  
  private int npcid;
  
  private int id;
  
  private int count;
  
  private int price;
  
  private int taxPrice;
  
  private boolean shop;
  
  private Item item;
  
  public int getUid() {
    return this.uid;
  }
  
  public void setUid(int uid) {
    this.uid = uid;
  }
  
  public int getNpcid() {
    return this.npcid;
  }
  
  public void setNpcid(int npcid) {
    this.npcid = npcid;
  }
  
  public int getId() {
    return this.id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public int getCount() {
    return this.count;
  }
  
  public void setCount(int count) {
    this.count = count;
  }
  
  public int getPrice() {
    return this.price;
  }
  
  public void setPrice(int price) {
    this.price = price;
  }
  
  public boolean isShop() {
    return this.shop;
  }
  
  public void setShop(boolean shop) {
    this.shop = shop;
  }
  
  public Item getItem() {
    return this.item;
  }
  
  public void setItem(Item item) {
    this.item = item;
  }
  
  public int getTaxPrice() {
    return this.taxPrice;
  }
  
  public void setTaxPrice(int taxPrice) {
    this.taxPrice = taxPrice;
  }
}
