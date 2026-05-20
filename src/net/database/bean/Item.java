package net.database.bean;

public class Item {
  public static final byte SLOT_HELM = 0;
  
  public static final byte SLOT_EARRING = 1;
  
  public static final byte SLOT_NECKLACE = 2;
  
  public static final byte SLOT_T = 3;
  
  public static final byte SLOT_ARMOR = 4;
  
  public static final byte SLOT_CLOAK = 5;
  
  public static final byte SLOT_RING_LEFT = 6;
  
  public static final byte SLOT_RING_RIGHT = 7;
  
  public static final byte SLOT_BELT = 8;
  
  public static final byte SLOT_GLOVE = 9;
  
  public static final byte SLOT_SHIELD = 10;
  
  public static final byte SLOT_WEAPON = 11;
  
  public static final byte SLOT_BOOTS = 12;
  
  public static final byte SLOT_NONE = 13;
  
  public static final byte TYPE_NONE = 0;
  
  public static final byte TYPE_BOOK = 30;
  
  public static final byte TYPE_ARROW = 1;
  
  public static final byte TYPE_AXE = 2;
  
  public static final byte TYPE_BOW = 3;
  
  public static final byte TYPE_SPEAR = 4;
  
  public static final byte TYPE_SWORD = 5;
  
  public static final byte TYPE_WAND = 6;
  
  public static final byte TYPE_CLAW = 7;
  
  public static final byte TYPE_DAGGER = 8;
  
  public static final byte TYPE_EDORYU = 9;
  
  public static final byte TYPE_GAUNTLET = 10;
  
  public static final byte TYPE_THROWINGKNIFE = 11;
  
  public static final byte TYPE_TOHANDSWORD = 24;
  
  public static final byte TYPE_HELM = 12;
  
  public static final byte TYPE_EARRING = 13;
  
  public static final byte TYPE_NECKLACE = 14;
  
  public static final byte TYPE_SHIRT = 15;
  
  public static final byte TYPE_ARMOR = 16;
  
  public static final byte TYPE_CLOAK = 17;
  
  public static final byte TYPE_RING = 18;
  
  public static final byte TYPE_BELT = 19;
  
  public static final byte TYPE_GLOVE = 20;
  
  public static final byte TYPE_SHIELD = 21;
  
  public static final byte TYPE_WEAPON = 22;
  
  public static final byte TYPE_BOOTS = 23;
  
  public static final byte WEAPON_NONE = 0;
  
  public static final byte WEAPON_SWORD = 4;
  
  public static final byte WEAPON_AXE = 11;
  
  public static final byte WEAPON_BOW = 20;
  
  public static final byte WEAPON_SPEER = 24;
  
  public static final byte WEAPON_WAND = 40;
  
  public static final byte WEAPON_DAGGER = 46;
  
  public static final byte WEAPON_TOHANDSWORD = 50;
  
  public static final byte WEAPON_EDORYU = 54;
  
  public static final byte WEAPON_CLAW = 58;
  
  public static final byte WEAPON_GAUNTLET = 62;
  
  public static final byte WEAPON_ARROW = 66;
  
  public static final byte WEAPON_THROWINGKNIFE = 106;
  
  public static final byte MATERIAL_NORMAL = 0;
  
  public static final byte MATERIAL_LIQUID = 1;
  
  public static final byte MATERIAL_MILAB = 2;
  
  public static final byte MATERIAL_VEGITABLE = 3;
  
  public static final byte MATERIAL_ANIMAL = 4;
  
  public static final byte MATERIAL_PAPER = 5;
  
  public static final byte MATERIAL_SILK = 6;
  
  public static final byte MATERIAL_SKIN = 7;
  
  public static final byte MATERIAL_WOOD = 8;
  
  public static final byte MATERIAL_BONE = 9;
  
  public static final byte MATERIAL_DRAGON = 10;
  
  public static final byte MATERIAL_IRON = 11;
  
  public static final byte MATERIAL_STEEL = 12;
  
  public static final byte MATERIAL_GURI = 13;
  
  public static final byte MATERIAL_SILVER = 14;
  
  public static final byte MATERIAL_GOLD = 15;
  
  public static final byte MATERIAL_WHITEGOLD = 16;
  
  public static final byte MATERIAL_MISRIL = 17;
  
  public static final byte MATERIAL_BLACKMISTRIL = 18;
  
  public static final byte MATERIAL_GLASS = 19;
  
  public static final byte MATERIAL_JEWER = 20;
  
  public static final byte MATERIAL_ORE = 21;
  
  public static final byte MATERIAL_ORI = 22;
  
  private int _itemId;
  
  private String _name;
  
  private byte type;
  
  private int type1;
  
  private int type2;
  
  private int gfxmode;
  
  private int _dmgsmall;
  
  private int _dmglarge;
  
  private int _material;
  
  private int _weight;
  
  private int _groundgfxid;
  
  private int _gfxid;
  
  private String nameid;
  
  private int nameidN;
  
  private int _price;
  
  private boolean sell;
  
  private boolean piles;
  
  private boolean trade;
  
  private boolean canDrop;
  
  private boolean werehouse;
  
  private boolean enchant;
  
  private int _safenchant;
  
  private int _royal;
  
  private int _knight;
  
  private int _elf;
  
  private int _mage;
  
  private int addHit;
  
  private int addDmg;
  
  private int ac;
  
  private int addstr;
  
  private int adddex;
  
  private int addcon;
  
  private int addint;
  
  private int addwis;
  
  private int addcha;
  
  private int addhp;
  
  private int addmp;
  
  private int addsp;
  
  private int addmr;
  
  private int addHpr;
  
  private int addMpr;
  
  private int steal_hp;
  
  private int steal_mp;
  
  private boolean haste;
  
  private boolean canbedmg;
  
  private int minLvl;
  
  private int maxLvl;
  
  private int earth;
  
  private int fire;
  
  private int wind;
  
  private int water;
  
  private int down_weight;
  
  private int effect;
  
  private boolean lawful;
  
  private boolean chaotic;
  
  private boolean momtree;
  
  private boolean neutral;
  
  private boolean tower;
  
  private int skill_id;
  
  private boolean tohand;
  
  private int setitemUid;
  
  private int continuous;
  
  public int get_EffectID() {
    return this.effect;
  }
  
  public void set_EffectID(int effect) {
    this.effect = effect;
  }
  
  public boolean isSell() {
    return this.sell;
  }
  
  public void setSell(boolean sell) {
    this.sell = sell;
  }
  
  public boolean isPiles() {
    return this.piles;
  }
  
  public void setPiles(boolean piles) {
    this.piles = piles;
  }
  
  public int get_downWeight() {
    return this.down_weight;
  }
  
  public void set_downWeight(int down_weight) {
    this.down_weight = down_weight;
  }
  
  public boolean isEnchant() {
    return this.enchant;
  }
  
  public void setEnchant(boolean enchant) {
    this.enchant = enchant;
  }
  
  public int get_ac() {
    return this.ac;
  }
  
  public void set_ac(int _ac) {
    this.ac = _ac;
  }
  
  public int get_earth() {
    return this.earth;
  }
  
  public void set_earth(int _earth) {
    this.earth = _earth;
  }
  
  public int get_fire() {
    return this.fire;
  }
  
  public void set_fire(int _fire) {
    this.fire = _fire;
  }
  
  public int get_wind() {
    return this.wind;
  }
  
  public void set_wind(int _wind) {
    this.wind = _wind;
  }
  
  public int get_water() {
    return this.water;
  }
  
  public void set_water(int _water) {
    this.water = _water;
  }
  
  public boolean isCanDrop() {
    return this.canDrop;
  }
  
  public void setCanDrop(boolean drop) {
    this.canDrop = drop;
  }
  
  public boolean isTrade() {
    return this.trade;
  }
  
  public void setTrade(boolean trade) {
    this.trade = trade;
  }
  
  public void setAddMpr(int mpr) {
    this.addMpr = mpr;
  }
  
  public int getAddMpr() {
    return this.addMpr;
  }
  
  public void setAddHpr(int hpr) {
    this.addHpr = hpr;
  }
  
  public int get_tichp() {
    return this.addHpr;
  }
  
  public int getMinLvl() {
    return this.minLvl;
  }
  
  public void setMinLvl(int minLvl) {
    this.minLvl = minLvl;
  }
  
  public int getMaxLvl() {
    return this.maxLvl;
  }
  
  public void setMaxLvl(int maxLvl) {
    this.maxLvl = maxLvl;
  }
  
  public boolean isCanbedmg() {
    return this.canbedmg;
  }
  
  public void setCanbedmg(boolean canbedmg) {
    this.canbedmg = canbedmg;
  }
  
  public void setSteal_hp(int hp) {
    this.steal_hp = hp;
  }
  
  public int getSteal_hp() {
    return this.steal_hp;
  }
  
  public void setSteal_mp(int mp) {
    this.steal_mp = mp;
  }
  
  public int getSteal_mp() {
    return this.steal_mp;
  }
  
  public int get_safenchant() {
    return this._safenchant;
  }
  
  public void set_safenchant(int _safenchant) {
    this._safenchant = _safenchant;
  }
  
  public boolean gethaste() {
    return this.haste;
  }
  
  public void sethaste(boolean _haste) {
    this.haste = _haste;
  }
  
  public int get_price() {
    return this._price;
  }
  
  public void set_price(int price) {
    this._price = price;
  }
  
  public int getAddcha() {
    return this.addcha;
  }
  
  public void setAddcha(int addcha) {
    this.addcha = addcha;
  }
  
  public int getAddcon() {
    return this.addcon;
  }
  
  public void setAddcon(int addcon) {
    this.addcon = addcon;
  }
  
  public int getAdddex() {
    return this.adddex;
  }
  
  public void setAdddex(int adddex) {
    this.adddex = adddex;
  }
  
  public int getAddhp() {
    return this.addhp;
  }
  
  public void setAddhp(int addhp) {
    this.addhp = addhp;
  }
  
  public int getAddint() {
    return this.addint;
  }
  
  public void setAddint(int addint) {
    this.addint = addint;
  }
  
  public int getAddmp() {
    return this.addmp;
  }
  
  public void setAddmp(int addmp) {
    this.addmp = addmp;
  }
  
  public int getAddstr() {
    return this.addstr;
  }
  
  public void setAddstr(int addstr) {
    this.addstr = addstr;
  }
  
  public int getAddwis() {
    return this.addwis;
  }
  
  public void setAddwis(int addwis) {
    this.addwis = addwis;
  }
  
  public int get_mage() {
    return this._mage;
  }
  
  public void set_mage(int mage) {
    this._mage = mage;
  }
  
  public int get_elf() {
    return this._elf;
  }
  
  public void set_elf(int elf) {
    this._elf = elf;
  }
  
  public int get_knight() {
    return this._knight;
  }
  
  public void set_knight(int knight) {
    this._knight = knight;
  }
  
  public int get_royal() {
    return this._royal;
  }
  
  public void set_royal(int royal) {
    this._royal = royal;
  }
  
  public int get_dmglarge() {
    return this._dmglarge;
  }
  
  public void set_dmglarge(int _dmglarge) {
    this._dmglarge = _dmglarge;
  }
  
  public int get_dmgsmall() {
    return this._dmgsmall;
  }
  
  public void set_dmgsmall(int _dmgsmall) {
    this._dmgsmall = _dmgsmall;
  }
  
  public int get_gfxid() {
    return this._gfxid;
  }
  
  public void set_gfxid(int _gfxid) {
    this._gfxid = _gfxid;
  }
  
  public int get_groundgfxid() {
    return this._groundgfxid;
  }
  
  public void set_groundgfxid(int _groundgfxid) {
    this._groundgfxid = _groundgfxid;
  }
  
  public String get_nameid() {
    return this.nameid;
  }
  
  public void set_nameid(String _nameid) {
    this.nameid = _nameid;
  }
  
  public int get_nameidN() {
    return this.nameidN;
  }
  
  public void set_nameidN(int nameidN) {
    this.nameidN = nameidN;
  }
  
  public int getItemId() {
    return this._itemId;
  }
  
  public void setItemId(int itemId) {
    this._itemId = itemId;
  }
  
  public int getWeight() {
    return this._weight;
  }
  
  public void setWeight(int weight) {
    this._weight = weight;
  }
  
  public String get_name() {
    return this._name;
  }
  
  public void set_name(String _name) {
    this._name = _name;
  }
  
  public int get_material() {
    return this._material;
  }
  
  public void set_material(int _material) {
    this._material = _material;
  }
  
  public byte getType() {
    return this.type;
  }
  
  public void setType(byte type) {
    this.type = type;
  }
  
  public int getType1() {
    return this.type1;
  }
  
  public void setType1(int type1) {
    this.type1 = type1;
  }
  
  public int getType2() {
    return this.type2;
  }
  
  public void setType2(int type2) {
    this.type2 = type2;
  }
  
  public boolean isWerehouse() {
    return this.werehouse;
  }
  
  public void setWerehouse(boolean werehouse) {
    this.werehouse = werehouse;
  }
  
  public int getAddHit() {
    return this.addHit;
  }
  
  public void setAddHit(int addHit) {
    this.addHit = addHit;
  }
  
  public int getAddDmg() {
    return this.addDmg;
  }
  
  public void setAddDmg(int addDmg) {
    this.addDmg = addDmg;
  }
  
  public int getAddsp() {
    return this.addsp;
  }
  
  public void setAddsp(int addsp) {
    this.addsp = addsp;
  }
  
  public int getAddmr() {
    return this.addmr;
  }
  
  public void setAddmr(int addmr) {
    this.addmr = addmr;
  }
  
  public int getGfxmode() {
    return this.gfxmode;
  }
  
  public void setGfxmode(int gfxmode) {
    this.gfxmode = gfxmode;
  }
  
  public boolean getIstower() {
    return this.tower;
  }
  
  public void setIstower(boolean tower) {
    this.tower = tower;
  }
  
  public boolean getIsneutral() {
    return this.neutral;
  }
  
  public void setIsneutral(boolean neutral) {
    this.neutral = neutral;
  }
  
  public boolean getIsmomtree() {
    return this.momtree;
  }
  
  public void setIsmomtree(boolean momtree) {
    this.momtree = momtree;
  }
  
  public boolean getIschaotic() {
    return this.chaotic;
  }
  
  public void setIschaotic(boolean chaotic) {
    this.chaotic = chaotic;
  }
  
  public boolean getIslawful() {
    return this.lawful;
  }
  
  public void setIslawful(boolean lawful) {
    this.lawful = lawful;
  }
  
  public int getSkill_id() {
    return this.skill_id;
  }
  
  public void setSkill_id(int skill_id) {
    this.skill_id = skill_id;
  }
  
  public boolean isTohand() {
    return this.tohand;
  }
  
  public void setTohand(boolean tohand) {
    this.tohand = tohand;
  }
  
  public int getSetitemUid() {
    return this.setitemUid;
  }
  
  public void setSetitemUid(int setitemUid) {
    this.setitemUid = setitemUid;
  }
  
  public int getContinuous() {
    return this.continuous;
  }
  
  public void setContinuous(int continuous) {
    this.continuous = continuous;
  }
}
