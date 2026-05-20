package net.network.server;

import net.database.bean.Item;
import net.world.instance.ItemInstance;
import net.world.instance.inventory.function.DogCollar;
import net.world.instance.inventory.function.Letter;

public class S_Inventory extends S_BasePacket {
  private int elf;
  
  private int royal;
  
  private int mage;
  
  private int knight;
  
  private int size;
  
  protected boolean TohandSword;
  
  protected int enLevel;
  
  protected int durability;
  
  protected int Weight;
  
  protected StringBuilder _sb;
  
  protected String getName(ItemInstance items) {
    this._sb = new StringBuilder();
    if (items instanceof Letter && items.getItem().get_gfxid() != 464) {
      Letter letter = (Letter)items;
      this._sb.append(letter.getSenderName());
      this._sb.append(" : ");
      this._sb.append(letter.getSubject());
    } else {
      if (items.isDefinite() && items.getItem().getType1() != 0 && items.getItem().getType1() != 3 && items.getItem().getType() != 1) {
        if (items.getEnLevel() >= 0) {
          this._sb.append("+");
        } else {
          this._sb.append("-");
        } 
        this._sb.append(items.getEnLevel());
        this._sb.append(" ");
      } 
      this._sb.append(items.getName());
      if (items.isEquipped())
        switch (items.getItem().getType1()) {
          case 1:
            this._sb.append(" ($9)");
            break;
          case 2:
            this._sb.append(" ($117)");
            break;
          default:
            if (items instanceof net.world.instance.inventory.function.Candle || items instanceof net.world.instance.inventory.function.Lamp || items instanceof net.world.instance.inventory.function.Lantern)
              this._sb.append(" ($10)"); 
            break;
        }  
      if (items.getCount() > 1L) {
        this._sb.append(" (");
        this._sb.append(items.getCount());
        this._sb.append(")");
      } 
      if (items.isDefinite() && (items instanceof net.world.instance.inventory.function.MapleWand || items instanceof net.world.instance.inventory.function.PineWand || items instanceof net.world.instance.inventory.function.EbonyWand)) {
        this._sb.append(" (");
        this._sb.append(items.getHaveCount());
        this._sb.append(")");
      } 
      if (items instanceof DogCollar) {
        DogCollar pet = (DogCollar)items;
        this._sb.append(" [Lv.");
        this._sb.append(pet.getPetLevel());
        this._sb.append(" ");
        this._sb.append(pet.getPetName());
        this._sb.append("]");
      } 
    } 
    return this._sb.toString();
  }
  
  protected void armor(ItemInstance temp) {
    writeC(sendSize(temp.getItem().getType1(), temp.getItem()));
    writeC(19);
    writeC(temp.getItem().get_ac());
    writeC(temp.getItem().get_material());
    writeD(temp.getItem().getWeight());
    if (temp.getEnLevel() != 0) {
      writeC(2);
      writeC(temp.getEnLevel());
    } 
    this.royal = (temp.getItem().get_royal() != 1) ? 0 : 1;
    this.knight = (temp.getItem().get_knight() != 1) ? 0 : 2;
    this.elf = (temp.getItem().get_elf() != 1) ? 0 : 4;
    this.mage = (temp.getItem().get_mage() != 1) ? 0 : 8;
    writeC(7);
    writeC(this.elf + this.royal + this.mage + this.knight);
    if (temp.getItem().getAddstr() != 0) {
      writeC(8);
      writeC(temp.getItem().getAddstr());
    } 
    if (temp.getItem().getAdddex() != 0) {
      writeC(9);
      writeC(temp.getItem().getAdddex());
    } 
    if (temp.getItem().getAddcon() != 0) {
      writeC(10);
      writeC(temp.getItem().getAddcon());
    } 
    if (temp.getItem().getAddwis() != 0) {
      writeC(11);
      writeC(temp.getItem().getAddwis());
    } 
    if (temp.getItem().getAddint() != 0) {
      writeC(12);
      writeC(temp.getItem().getAddint());
    } 
    if (temp.getItem().getAddcha() != 0) {
      writeC(13);
      writeC(temp.getItem().getAddcha());
    } 
    if (temp.getItem().getAddhp() != 0) {
      writeC(14);
      writeH(temp.getItem().getAddhp());
    } 
    if (temp.getItem().getAddmr() != 0) {
      writeC(15);
      writeH(temp.getItem().getAddmr() + temp.getDynamicMr());
    } 
    if (temp.getItem().getAddsp() != 0) {
      writeC(17);
      writeC(temp.getItem().getAddsp());
    } 
    if (temp.getItem().gethaste())
      writeC(18); 
    if (temp.getItem().getMinLvl() != 0) {
      writeC(26);
      writeH(temp.getItem().getMinLvl());
    } 
    if (temp.getItem().get_fire() != 0) {
      writeC(27);
      writeC(temp.getItem().get_fire());
    } 
    if (temp.getItem().get_water() != 0) {
      writeC(28);
      writeC(temp.getItem().get_water());
    } 
    if (temp.getItem().get_wind() != 0) {
      writeC(29);
      writeC(temp.getItem().get_wind());
    } 
    if (temp.getItem().get_earth() != 0) {
      writeC(30);
      writeC(temp.getItem().get_earth());
    } 
  }
  
  protected void armor(Item temp) {
    writeC(sendSize(temp.getType1(), temp));
    writeC(19);
    writeC(temp.get_ac());
    writeC(temp.get_material());
    writeD(temp.getWeight());
    this.elf = (temp.get_elf() != 1) ? 0 : 4;
    this.royal = (temp.get_royal() != 1) ? 0 : 1;
    this.mage = (temp.get_mage() != 1) ? 0 : 8;
    this.knight = (temp.get_knight() != 1) ? 0 : 2;
    writeC(7);
    writeC(this.elf + this.royal + this.mage + this.knight);
    if (temp.getAddstr() != 0) {
      writeC(8);
      writeC(temp.getAddstr());
    } 
    if (temp.getAdddex() != 0) {
      writeC(9);
      writeC(temp.getAdddex());
    } 
    if (temp.getAddcon() != 0) {
      writeC(10);
      writeC(temp.getAddcon());
    } 
    if (temp.getAddwis() != 0) {
      writeC(11);
      writeC(temp.getAddwis());
    } 
    if (temp.getAddint() != 0) {
      writeC(12);
      writeC(temp.getAddint());
    } 
    if (temp.getAddcha() != 0) {
      writeC(13);
      writeC(temp.getAddcha());
    } 
    if (temp.getAddhp() != 0) {
      writeC(14);
      writeH(temp.getAddhp());
    } 
    if (temp.getAddmr() != 0) {
      writeC(15);
      writeH(temp.getAddmr());
    } 
    if (temp.getAddsp() != 0) {
      writeC(17);
      writeC(temp.getAddsp());
    } 
    if (temp.gethaste())
      writeC(18); 
    if (temp.getMinLvl() != 0) {
      writeC(26);
      writeH(temp.getMinLvl());
    } 
    if (temp.get_fire() != 0) {
      writeC(27);
      writeC(temp.get_fire());
    } 
    if (temp.get_water() != 0) {
      writeC(28);
      writeC(temp.get_water());
    } 
    if (temp.get_wind() != 0) {
      writeC(29);
      writeC(temp.get_wind());
    } 
    if (temp.get_earth() != 0) {
      writeC(30);
      writeC(temp.get_earth());
    } 
  }
  
  protected void weapon(ItemInstance temp) {
    writeC(sendSize(temp.getItem().getType1(), temp.getItem()));
    writeC(1);
    writeC(temp.getItem().get_dmgsmall());
    writeC(temp.getItem().get_dmglarge());
    writeC(temp.getItem().get_material());
    writeD(temp.getItem().getWeight());
    if (temp.getEnLevel() != 0) {
      writeC(2);
      writeC(temp.getEnLevel());
    } 
    if (temp.getDurability() != 0) {
      writeC(3);
      writeC(temp.getDurability());
    } 
    if (this.TohandSword)
      writeC(4); 
    if (temp.getItem().getAddHit() != 0) {
      writeC(5);
      writeC(temp.getItem().getAddHit());
    } 
    if (temp.getItem().getAddDmg() != 0) {
      writeC(6);
      writeC(temp.getItem().getAddDmg());
    } 
    this.royal = (temp.getItem().get_royal() != 1) ? 0 : 1;
    this.knight = (temp.getItem().get_knight() != 1) ? 0 : 2;
    this.elf = (temp.getItem().get_elf() != 1) ? 0 : 4;
    this.mage = (temp.getItem().get_mage() != 1) ? 0 : 8;
    writeC(7);
    writeC(this.elf + this.royal + this.mage + this.knight);
    if (temp.getItem().getAddstr() != 0) {
      writeC(8);
      writeC(temp.getItem().getAddstr());
    } 
    if (temp.getItem().getAdddex() != 0) {
      writeC(9);
      writeC(temp.getItem().getAdddex());
    } 
    if (temp.getItem().getAddcon() != 0) {
      writeC(10);
      writeC(temp.getItem().getAddcon());
    } 
    if (temp.getItem().getAddwis() != 0) {
      writeC(11);
      writeC(temp.getItem().getAddwis());
    } 
    if (temp.getItem().getAddint() != 0) {
      writeC(12);
      writeC(temp.getItem().getAddint());
    } 
    if (temp.getItem().getAddcha() != 0) {
      writeC(13);
      writeC(temp.getItem().getAddcha());
    } 
    if (temp.getItem().getAddhp() != 0) {
      writeC(14);
      writeH(temp.getItem().getAddhp());
    } 
    if (temp.getItem().getAddmr() != 0) {
      writeC(15);
      writeH(temp.getItem().getAddmr() + temp.getDynamicMr());
    } 
    if (temp.getItem().getSteal_mp() != 0)
      writeC(16); 
    if (temp.getItem().getAddsp() != 0) {
      writeC(17);
      writeC(temp.getItem().getAddsp());
    } 
    if (temp.getItem().gethaste())
      writeC(18); 
  }
  
  protected void weapon(Item temp) {
    writeC(sendSize(temp.getType1(), temp));
    writeC(1);
    writeC(temp.get_dmgsmall());
    writeC(temp.get_dmglarge());
    writeC(temp.get_material());
    writeD(temp.getWeight());
    if (this.TohandSword)
      writeC(4); 
    if (temp.getAddHit() != 0) {
      writeC(5);
      writeC(temp.getAddHit());
    } 
    if (temp.getAddDmg() != 0) {
      writeC(6);
      writeC(temp.getAddDmg());
    } 
    this.elf = (temp.get_elf() != 1) ? 0 : 4;
    this.royal = (temp.get_royal() != 1) ? 0 : 1;
    this.mage = (temp.get_mage() != 1) ? 0 : 8;
    this.knight = (temp.get_knight() != 1) ? 0 : 2;
    writeC(7);
    writeC(this.elf + this.royal + this.mage + this.knight);
    if (temp.getAddstr() != 0) {
      writeC(8);
      writeC(temp.getAddstr());
    } 
    if (temp.getAdddex() != 0) {
      writeC(9);
      writeC(temp.getAdddex());
    } 
    if (temp.getAddcon() != 0) {
      writeC(10);
      writeC(temp.getAddcon());
    } 
    if (temp.getAddwis() != 0) {
      writeC(11);
      writeC(temp.getAddwis());
    } 
    if (temp.getAddint() != 0) {
      writeC(12);
      writeC(temp.getAddint());
    } 
    if (temp.getAddcha() != 0) {
      writeC(13);
      writeC(temp.getAddcha());
    } 
    if (temp.getAddhp() != 0) {
      writeC(14);
      writeH(temp.getAddhp());
    } 
    if (temp.getAddmr() != 0) {
      writeC(15);
      writeH(temp.getAddmr());
    } 
    if (temp.getSteal_mp() != 0)
      writeC(16); 
    if (temp.getAddsp() != 0) {
      writeC(17);
      writeC(temp.getAddsp());
    } 
    if (temp.gethaste())
      writeC(18); 
  }
  
  protected void etc(ItemInstance temp) {
    this.Weight = temp.getItem().getWeight();
    writeD(temp.getInvID());
    writeC(actionid(temp));
    writeC(temp.getHaveCount());
    writeH(temp.getItem().get_gfxid());
    writeC(temp.getBless());
    writeD((int)temp.getCount());
    writeC(temp.isDefinite() ? 1 : 0);
    if (!temp.isDefinite()) {
      writeS(getName(temp));
      writeC(0);
    } else {
      writeS(getName(temp));
      if (temp instanceof DogCollar) {
        DogCollar pet = (DogCollar)temp;
        writeC(15);
        writeC(25);
        writeH(pet.getPetClassId());
        writeC(26);
        writeH(pet.getPetLevel());
        writeC(31);
        writeH(pet.getPetMxhp());
        writeC(23);
        writeC(pet.getItem().get_material());
        writeD(this.Weight);
      } else {
        this.Weight *= (int)temp.getCount();
        writeC(6);
        writeC(23);
        writeC(temp.getItem().get_material());
        writeD(this.Weight);
      } 
    } 
  }
  
  protected int actionid(ItemInstance temp) {
    int actionid = 0;
    if (temp.getItem().getItemId() >= 600 && temp.getItem().getItemId() <= 636) {
      switch (temp.getItem().getSkill_id()) {
        case 2:
        case 3:
        case 5:
        case 10:
          actionid = 0;
          break;
        case 1:
        case 4:
        case 6:
        case 7:
        case 8:
        case 11:
        case 12:
        case 13:
        case 14:
        case 16:
        case 17:
        case 18:
        case 19:
        case 20:
        case 21:
        case 22:
        case 23:
        case 24:
        case 25:
          actionid = 5;
          break;
        case 15:
          actionid = 27;
          break;
        case 9:
          actionid = 26;
          break;
      } 
      return actionid;
    } 
    if (temp.getItem().get_name().equalsIgnoreCase("暴風疾走")) {
      actionid = 5;
      return actionid;
    } 
    if (temp.getItem().get_name().equalsIgnoreCase("衝擊之暈")) {
      actionid = 5;
      return actionid;
    } 
    if (temp.getItem().get_name().equalsIgnoreCase("三重矢")) {
      actionid = 5;
      return actionid;
    } 
    if (temp.getItem().get_name().startsWith("經驗藥水")) {
      actionid = 0;
      return actionid;
    } 
    if (temp.getItem().get_name().startsWith("商城變卷")) {
      actionid = 0;
      return actionid;
    } 
    switch (temp.getItem().get_nameidN()) {
      case 1486:
      case 1892:
      case 1893:
      case 1894:
      case 1895:
        actionid = 28;
        return actionid;
      case 28:
      case 258:
        actionid = 3;
        return actionid;
      case 27:
      case 260:
      case 263:
        actionid = 5;
        return actionid;
      case 505:
        actionid = 9;
        return actionid;
      case 230:
        if (temp.getBless() == 1) {
          actionid = 6;
        } else if (temp.getBless() == 0) {
          actionid = 29;
        } 
        return actionid;
      case 971:
        actionid = 16;
        return actionid;
      case 257:
        actionid = 8;
        return actionid;
      case 244:
      case 1100:
        actionid = 26;
        return actionid;
      case 249:
        actionid = 27;
        return actionid;
      case 55:
        actionid = 7;
        return actionid;
      case 243:
        if (temp.getBless() == 1) {
          actionid = 0;
        } else {
          actionid = 14;
        } 
        return actionid;
      case 1086:
        actionid = 15;
        return actionid;
    } 
    switch (temp.getItem().getItemId()) {
      case 304:
        actionid = 33;
        return actionid;
      case 340:
        actionid = 12;
        return actionid;
      case 341:
      case 342:
        actionid = 13;
        return actionid;
      case 343:
        actionid = 9;
        return actionid;
    } 
    actionid = 0;
    return actionid;
  }
  
  protected int sendSize(int type, Item temp) {
    this.size = 0;
    switch (type) {
      case 1:
        this.size += 10;
        if (this.enLevel != 0)
          this.size += 2; 
        if (this.durability != 0)
          this.size += 2; 
        if (this.TohandSword)
          this.size++; 
        if (temp.getAddHit() != 0)
          this.size += 2; 
        if (temp.getAddDmg() != 0)
          this.size += 2; 
        if (temp.getAddstr() != 0)
          this.size += 2; 
        if (temp.getAdddex() != 0)
          this.size += 2; 
        if (temp.getAddcon() != 0)
          this.size += 2; 
        if (temp.getAddint() != 0)
          this.size += 2; 
        if (temp.getAddcha() != 0)
          this.size += 2; 
        if (temp.getAddwis() != 0)
          this.size += 2; 
        if (temp.getAddhp() != 0)
          this.size += 3; 
        if (temp.getAddmr() != 0)
          this.size += 3; 
        if (temp.getSteal_mp() != 0)
          this.size++; 
        if (temp.getAddsp() != 0)
          this.size += 2; 
        if (temp.gethaste())
          this.size++; 
        break;
      case 2:
        this.size += 9;
        if (this.enLevel != 0)
          this.size += 2; 
        if (temp.getAddstr() != 0)
          this.size += 2; 
        if (temp.getAdddex() != 0)
          this.size += 2; 
        if (temp.getAddcon() != 0)
          this.size += 2; 
        if (temp.getAddint() != 0)
          this.size += 2; 
        if (temp.getAddcha() != 0)
          this.size += 2; 
        if (temp.getAddwis() != 0)
          this.size += 2; 
        if (temp.getAddmr() != 0)
          this.size += 3; 
        if (temp.getAddsp() != 0)
          this.size += 2; 
        if (temp.gethaste())
          this.size++; 
        if (temp.get_fire() != 0)
          this.size += 2; 
        if (temp.get_water() != 0)
          this.size += 2; 
        if (temp.get_wind() != 0)
          this.size += 2; 
        if (temp.get_earth() != 0)
          this.size += 2; 
        if (temp.getAddhp() != 0)
          this.size += 3; 
        if (temp.getMinLvl() != 0)
          this.size += 3; 
        break;
    } 
    return this.size;
  }
}
