package net.database.bean;

public class Skill {
  int Skill_id;
  
  String name;
  
  int Skill_level;
  
  int Skill_no;
  
  String Magic;
  
  int MpConsume;
  
  int HpConsume;
  
  int ItemConsume;
  
  int ItemConsumeCount;
  
  int ReuseDelay;
  
  int BuffDuration;
  
  String type;
  
  int Mindmg;
  
  int Maxdmg;
  
  int Id;
  
  int CastGfx;
  
  int Range;
  
  int lawfulconsume;
  
  private int attr;
  
  int price;
  
  public int get_lawfulconsume() {
    return this.lawfulconsume;
  }
  
  public void set_lawfulconsume(int lawfulconsume) {
    this.lawfulconsume = lawfulconsume;
  }
  
  public int getRange() {
    return this.Range;
  }
  
  public void setRange(int range) {
    this.Range = range;
  }
  
  public void setPrice(int price) {
    this.price = price;
  }
  
  public int getPrice() {
    return this.price;
  }
  
  public int getSkill_no() {
    return this.Skill_no;
  }
  
  public void setSkill_no(int skill_no) {
    this.Skill_no = skill_no;
  }
  
  public int getSkill_level() {
    return this.Skill_level;
  }
  
  public void setSkill_level(int skill_level) {
    this.Skill_level = skill_level;
  }
  
  public int getBuffDuration() {
    return this.BuffDuration;
  }
  
  public void setBuffDuration(int buffDuration) {
    this.BuffDuration = buffDuration;
  }
  
  public int getCastGfx() {
    return this.CastGfx;
  }
  
  public void setCastGfx(int castGfx) {
    this.CastGfx = castGfx;
  }
  
  public int getHpConsume() {
    return this.HpConsume;
  }
  
  public void setHpConsume(int hpConsume) {
    this.HpConsume = hpConsume;
  }
  
  public int getId() {
    return this.Id;
  }
  
  public void setId(int id) {
    this.Id = id;
  }
  
  public int getItemConsume() {
    return this.ItemConsume;
  }
  
  public void setItemConsume(int itemConsume) {
    this.ItemConsume = itemConsume;
  }
  
  public int getItemConsumeCount() {
    return this.ItemConsumeCount;
  }
  
  public void setItemConsumeCount(int itemConsumeCount) {
    this.ItemConsumeCount = itemConsumeCount;
  }
  
  public String getMagic() {
    return this.Magic;
  }
  
  public void setMagic(String magic) {
    this.Magic = magic;
  }
  
  public int getMpConsume() {
    return this.MpConsume;
  }
  
  public void setMpConsume(int mpConsume) {
    this.MpConsume = mpConsume;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public int getMindmg() {
    return this.Mindmg;
  }
  
  public void setMindmg(int mindmg) {
    this.Mindmg = mindmg;
  }
  
  public int getMaxdmg() {
    return this.Maxdmg;
  }
  
  public void setMaxdmg(int maxdmg) {
    this.Maxdmg = maxdmg;
  }
  
  public int getReuseDelay() {
    return this.ReuseDelay;
  }
  
  public void setReuseDelay(int reuseDelay) {
    this.ReuseDelay = reuseDelay;
  }
  
  public int getSkill_id() {
    return this.Skill_id;
  }
  
  public void setSkill_id(int skill_id) {
    this.Skill_id = skill_id;
  }
  
  public String getType() {
    return this.type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public int getAttr() {
    return this.attr;
  }
  
  public void setAttr(int attr) {
    this.attr = attr;
  }
}
