package net.world.instance;

import net.Config;
import net.database.ItemsTable;
import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_CharacterStat;
import net.network.server.S_InventoryEquipped;
import net.network.server.S_InventoryStatus;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectAdd;
import net.network.server.S_ObjectHeading;
import net.network.server.S_ObjectSpMr;
import net.network.server.S_ServerMessage;
import net.world.object.Character;
import net.world.object.L1Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemInstance extends L1Object {
  final Logger log = LoggerFactory.getLogger(ItemInstance.class);
  
  private int uid;
  
  private int invID;
  
  private int haveCount;
  
  private int enLevel;
  
  private boolean equipped;
  
  private boolean definite;
  
  private int bless;
  
  private int durability;
  
  private Item _item;
  
  private int dynamicMr;
  
  private Character cha;
  
  private int time;
  
  private int petObjectId;
  
  private int letterUid;
  
  static final String DROP_LOG = "%s ? %s 于地面:(%s,%s,%s)";
  
  static final String PICK_UP_LOG = "%s 拾 %s ?拾?量:%s";
  
  static final String INFO_LOG = "%s objID:%s ?量:%s ?化:%s  祝福:%s";
  
  public ItemInstance(Item item) {
    this._item = item;
    setName(item.get_nameid());
    setDead(true);
  }
  
  public Character getCha() {
    return this.cha;
  }
  
  public void setCha(Character cha) {
    this.cha = cha;
  }
  
  public int getUid() {
    return this.uid;
  }
  
  public void setUid(int uid) {
    this.uid = uid;
  }
  
  public int getInvID() {
    return this.invID;
  }
  
  public void setInvID(int invID) {
    this.invID = invID;
  }
  
  public int getHaveCount() {
    return this.haveCount;
  }
  
  public void setHaveCount(int haveCount) {
    this.haveCount = haveCount;
  }
  
  public int getEnLevel() {
    return this.enLevel;
  }
  
  public void setEnLevel(int enLevel) {
    this.enLevel = enLevel;
  }
  
  public boolean isEquipped() {
    return this.equipped;
  }
  
  public void setEquipped(boolean equipped) {
    this.equipped = equipped;
  }
  
  public boolean isDefinite() {
    return this.definite;
  }
  
  public void setDefinite(boolean definite) {
    this.definite = definite;
  }
  
  public int getBless() {
    return this.bless;
  }
  
  public void setBless(int bless) {
    this.bless = bless;
  }
  
  public int getDurability() {
    return this.durability;
  }
  
  public void setDurability(int durability) {
    if (durability > 15)
      durability = 15; 
    if (durability < 0)
      durability = 0; 
    this.durability = durability;
  }
  
  public Item getItem() {
    return this._item;
  }
  
  public void setItem(Item item) {
    this._item = item;
  }
  
  public int getDynamicMr() {
    return this.dynamicMr;
  }
  
  public void setDynamicMr(int dynamicMr) {
    this.dynamicMr = dynamicMr;
  }
  
  public int getTime() {
    return this.time;
  }
  
  public void setTime(int time) {
    if (time < 0)
      time = 0; 
    this.time = time;
  }
  
  public int getPetObjectId() {
    return this.petObjectId;
  }
  
  public void setPetObjectId(int petObjectId) {
    this.petObjectId = petObjectId;
  }
  
  public int getLetterUid() {
    return this.letterUid;
  }
  
  public void setLetterUid(int letterUid) {
    this.letterUid = letterUid;
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    cha.SendPacket((S_BasePacket)new S_ServerMessage(74, toString()));
  }
  
  public void Equipped(Character cha) {}
  
  public void isEnchant(Character cha, boolean en, short rnd) {}
  
  public void isSetting() {}
  
  public void isTimerRun(Character cha) {}
  
  public void isTimerStop(Character cha) {}
  
  public void isTimer(Character cha) {}
  
  public void isTimerEnd(Character cha) {}
  
  public int getFirstTime() {
    return 0;
  }
  
  public boolean itemLvCheck(Character cha) {
    return (getItem().getMinLvl() <= cha.getLevel() && (cha.getLevel() <= getItem().getMaxLvl() || getItem().getMaxLvl() == 0));
  }
  
  public void setCount(Character cha, long count) {
    if (count <= 0L) {
      cha.getInventory().remove(this);
    } else {
      setCount(count);
      cha.SendPacket((S_BasePacket)new S_InventoryStatus(this));
    } 
    cha.SendPacket((S_BasePacket)new S_CharacterStat(cha));
  }
  
  public void setHaveCount(Character cha, int haveCount) {
    setHaveCount(haveCount);
    cha.SendPacket((S_BasePacket)new S_InventoryEquipped(this));
  }
  
  protected boolean ClassCheck(Character cha) {
    boolean ck = false;
    if (cha instanceof PcInstance) {
      PcInstance pc = (PcInstance)cha;
      switch (pc.getClassType()) {
        case 0:
          ck = (getItem().get_royal() == 1);
          return ck;
        case 1:
          ck = (getItem().get_knight() == 1);
          return ck;
        case 2:
          ck = (getItem().get_elf() == 1);
          return ck;
        case 3:
          ck = (getItem().get_mage() == 1);
          return ck;
      } 
      ck = true;
    } 
    return ck;
  }
  
  public void itemOption(Character cha) {
    if (getItem().getAddstr() != 0)
      if (isEquipped()) {
        cha.setDynamicStr(cha.getDynamicStr() + getItem().getAddstr());
      } else {
        cha.setDynamicStr(cha.getDynamicStr() - getItem().getAddstr());
      }  
    if (getItem().getAdddex() != 0)
      if (isEquipped()) {
        cha.setDynamicDex(cha.getDynamicDex() + getItem().getAdddex());
      } else {
        cha.setDynamicDex(cha.getDynamicDex() - getItem().getAdddex());
      }  
    if (getItem().getAddcon() != 0)
      if (isEquipped()) {
        cha.setDynamicCon(cha.getDynamicCon() + getItem().getAddcon());
      } else {
        cha.setDynamicCon(cha.getDynamicCon() - getItem().getAddcon());
      }  
    if (getItem().getAddint() != 0)
      if (isEquipped()) {
        cha.setDynamicInt(cha.getDynamicInt() + getItem().getAddint());
      } else {
        cha.setDynamicInt(cha.getDynamicInt() - getItem().getAddint());
      }  
    if (getItem().getAddcha() != 0)
      if (isEquipped()) {
        cha.setDynamicCha(cha.getDynamicCha() + getItem().getAddcha());
      } else {
        cha.setDynamicCha(cha.getDynamicCha() - getItem().getAddcha());
      }  
    if (getItem().getAddwis() != 0)
      if (isEquipped()) {
        cha.setDynamicWis(cha.getDynamicWis() + getItem().getAddwis());
      } else {
        cha.setDynamicWis(cha.getDynamicWis() - getItem().getAddwis());
      }  
    if (getItem().getAddhp() != 0)
      if (isEquipped()) {
        cha.setDynamicHp(cha.getDynamicHp() + getItem().getAddhp());
      } else {
        cha.setDynamicHp(cha.getDynamicHp() - getItem().getAddhp());
      }  
    if (getItem().getAddmp() != 0)
      if (isEquipped()) {
        cha.setDynamicMp(cha.getDynamicMp() + getItem().getAddmp());
      } else {
        cha.setDynamicMp(cha.getDynamicMp() - getItem().getAddmp());
      }  
    if (getItem().get_tichp() != 0)
      if (isEquipped()) {
        cha.setDynamicTicHp(cha.getDynamicTicHp() + getItem().get_tichp());
      } else {
        cha.setDynamicTicHp(cha.getDynamicTicHp() - getItem().get_tichp());
      }  
    if (getItem().getAddMpr() != 0)
      if (isEquipped()) {
        cha.setDynamicTicMp(cha.getDynamicTicMp() + getItem().getAddMpr());
      } else {
        cha.setDynamicTicMp(cha.getDynamicTicMp() - getItem().getAddMpr());
      }  
    if (getItem().getAddmr() != 0)
      if (isEquipped()) {
        cha.setDynamicMr(cha.getDynamicMr() + getItem().getAddmr() + getDynamicMr());
      } else {
        cha.setDynamicMr(cha.getDynamicMr() - getItem().getAddmr() - getDynamicMr());
      }  
    if (getItem().getAddsp() != 0)
      if (isEquipped()) {
        cha.setDynamicSp(cha.getDynamicSp() + getItem().getAddsp());
      } else {
        cha.setDynamicSp(cha.getDynamicSp() - getItem().getAddsp());
      }  
    if (getItem().get_fire() != 0)
      if (isEquipped()) {
        cha.setFireress(cha.getFireress() + getItem().get_fire());
      } else {
        cha.setFireress(cha.getFireress() - getItem().get_fire());
      }  
    if (getItem().get_water() != 0)
      if (isEquipped()) {
        cha.setWaterress(cha.getWaterress() + getItem().get_water());
      } else {
        cha.setWaterress(cha.getWaterress() - getItem().get_water());
      }  
    if (getItem().get_wind() != 0)
      if (isEquipped()) {
        cha.setWindress(cha.getWindress() + getItem().get_wind());
      } else {
        cha.setWindress(cha.getWindress() - getItem().get_wind());
      }  
    if (getItem().get_earth() != 0)
      if (isEquipped()) {
        cha.setEarthress(cha.getEarthress() + getItem().get_earth());
      } else {
        cha.setEarthress(cha.getEarthress() - getItem().get_earth());
      }  
    if (getItem().get_downWeight() != 0)
      isEquipped(); 
    if (cha instanceof PcInstance) {
      PcInstance pc = (PcInstance)cha;
      pc.SendPacket((S_BasePacket)new S_CharacterStat(pc));
      pc.SendPacket((S_BasePacket)new S_ObjectSpMr(cha.getDynamicSp(), cha.getDynamicMr()));
    } 
  }
  
  public ItemInstance clone() {
    ItemInstance temp = ItemsTable.getInstance().FunctionItem(getItem());
    temp.setName(getName());
    temp.setCount(getCount());
    temp.setHaveCount(getHaveCount());
    temp.setEnLevel(getEnLevel());
    temp.setEquipped(isEquipped());
    temp.setDefinite(isDefinite());
    temp.setBless(getBless());
    temp.setDurability(getDurability());
    temp.setPetObjectId(getPetObjectId());
    temp.setLetterUid(getLetterUid());
    temp.setGfx(temp.getItem().get_groundgfxid());
    temp.setObjectId(Config.getObjectID_ETC());
    temp.setInvID(temp.getObjectId());
    temp.setDynamicMr(temp.getDynamicMr());
    return temp;
  }
  
  public void drop(Character cha, int x, int y, long count) {
    if (isEquipped() && getItem().getType1() != 0) {
      cha.SendPacket((S_BasePacket)new S_ServerMessage(125));
      return;
    } 
    if (getItem().isCanDrop() || cha instanceof NpcInstance) {
      if (!cha.isInvis() && 2147483647L >= count && getCount() >= count && count > 0L && cha.getDistance(x, y, cha.getMap(), 1)) {
        toDrop(cha);
        ItemInstance temp = null;
        if (getCount() == count) {
          cha.getInventory().remove(this);
          toTeleport(x, y, cha.getMap());
          temp = this;
        } else if (count < getCount()) {
          temp = clone();
          temp.setCount(count);
          temp.toTeleport(x, y, cha.getMap());
          setCount(cha, getCount() - count);
        } 
        this.log.info(String.format("%s ? %s 于地面:(%s,%s,%s)", new Object[] { cha.getName(), temp.logString(), Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(cha.getMap()) }));
      } 
    } else {
      cha.SendPacket((S_BasePacket)new S_ServerMessage(210, getName()));
    } 
  }
  
  public void pickup(Character cha, int x, int y, long count) {
    if (cha.getDistance(x, y, cha.getMap(), 1) && !cha.isInvis() && !cha.isDead())
      if (2147483647L >= count && getCount() >= count && count > 0L) {
        ItemInstance temp = cha.getInventory().isItem(this);
        if (!(cha instanceof MonsterInstance) && cha.getInventory().getCount() >= 180 && temp == null) {
          cha.SendPacket((S_BasePacket)new S_ServerMessage(263));
        } else if (!(cha instanceof MonsterInstance) && !cha.getInventory().isWeight((int)(getItem().getWeight() * count))) {
          cha.SendPacket((S_BasePacket)new S_ServerMessage(82));
        } else {
          cha.calcheading(getX(), getY());
          cha.SendPacket((S_BasePacket)new S_ObjectHeading((L1Object)cha), false);
          cha.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)cha, 15), true);
          toPickup(cha);
          if (cha.getInventory().insert(this, count)) {
            if (cha instanceof PcInstance)
              this.log.info(String.format("%s 拾 %s ?拾?量:%s", new Object[] { cha.getName(), logString(), Long.valueOf(count) })); 
            if (getCount() > 0L) {
              SendPacket((S_BasePacket)new S_ObjectAdd(this), true);
            } else {
              toDelete();
            } 
          } 
        } 
      } else {
        toDelete();
        toReset();
      }  
  }
  
  public void toPickup(Character cha) {}
  
  public void toDrop(Character cha) {}
  
  protected boolean EquippedCheck(Character cha) {
    if (isEquipped()) {
      if (getItem().getType() == 16 && cha.getInventory().getSlot(5) != null) {
        cha.SendPacket((S_BasePacket)new S_ServerMessage(127));
        return false;
      } 
      if (getItem().getType() == 15 && cha.getInventory().getSlot(5) != null) {
        cha.SendPacket((S_BasePacket)new S_ServerMessage(127));
        return false;
      } 
      if (getItem().getType() == 15 && cha.getInventory().getSlot(4) != null) {
        cha.SendPacket((S_BasePacket)new S_ServerMessage(127));
        return false;
      } 
    } else {
      if (getItem().getType() == 16 && cha.getInventory().getSlot(5) != null) {
        cha.SendPacket((S_BasePacket)new S_ServerMessage(126, "$226", "$225"));
        return false;
      } 
      if (getItem().getType() == 15 && cha.getInventory().getSlot(4) != null) {
        cha.SendPacket((S_BasePacket)new S_ServerMessage(126, "$168", "$226"));
        return false;
      } 
      if (getItem().getType() == 15 && cha.getInventory().getSlot(5) != null) {
        cha.SendPacket((S_BasePacket)new S_ServerMessage(126, "$168", "$225"));
        return false;
      } 
      if (getItem().getType() == 21 && cha.getInventory().getSlot(11) != null && cha.getInventory().getSlot(11).getItem().isTohand()) {
        cha.SendPacket((S_BasePacket)new S_ServerMessage(129));
        return false;
      } 
      if ((getItem().getType() == 16 && cha.getInventory().getSlot(4) != null) || (getItem().getType() == 17 && cha.getInventory().getSlot(5) != null) || (getItem().getType() == 15 && cha.getInventory().getSlot(3) != null) || (getItem().getType() == 20 && cha.getInventory().getSlot(9) != null) || (getItem().getType() == 23 && cha.getInventory().getSlot(12) != null) || (getItem().getType() == 12 && cha.getInventory().getSlot(0) != null) || (getItem().getType() == 18 && cha.getInventory().getSlot(7) != null && cha.getInventory().getSlot(6) != null) || (getItem().getType() == 21 && cha.getInventory().getSlot(10) != null) || (getItem().getType() == 13 && cha.getInventory().getSlot(1) != null) || (getItem().getType() == 19 && cha.getInventory().getSlot(8) != null) || (getItem().getType() == 14 && cha.getInventory().getSlot(2) != null) || (getItem().getType() == 10 && cha.getInventory().getSlot(13) != null)) {
        cha.SendPacket((S_BasePacket)new S_ServerMessage(124));
        return false;
      } 
    } 
    return true;
  }
  
  public int toAttackEffect(L1Object cha, L1Object target) {
    return 0;
  }
  
  public void setPet(PetInstance pet) {}
  
  public PetInstance getPet() {
    return null;
  }
  
  public void absoluteEquipped(Character cha, boolean equipped) {}
  
  public String toString() {
    StringBuilder _sb = new StringBuilder();
    if (isDefinite() && getItem().getType() != 1 && (getItem().getType1() == 1 || getItem().getType1() == 2)) {
      if (getEnLevel() >= 0)
        _sb.append("+"); 
      _sb.append(getEnLevel());
      _sb.append(" ");
    } 
    _sb.append(getName());
    if (getCount() > 1L) {
      _sb.append(" (");
      _sb.append(getCount());
      _sb.append(")");
    } 
    return _sb.toString();
  }
  
  public String toString2() {
    StringBuilder _sb = new StringBuilder();
    if (isDefinite() && getItem().getType() != 1 && (getItem().getType1() == 1 || getItem().getType1() == 2)) {
      if (getEnLevel() >= 0)
        _sb.append("+"); 
      _sb.append(getEnLevel());
      _sb.append(" ");
    } 
    _sb.append(getItem().get_name());
    if (getCount() > 1L) {
      _sb.append(" (");
      _sb.append(getCount());
      _sb.append(")");
    } 
    return _sb.toString();
  }
  
  public String logString() {
    return String.format("%s objID:%s ?量:%s ?化:%s  祝福:%s", new Object[] { getItem().get_name(), Integer.valueOf(getObjectId()), Long.valueOf(getCount()), Integer.valueOf(getEnLevel()), Integer.valueOf(getBless()) });
  }
}
