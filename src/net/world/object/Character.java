package net.world.object;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.Config;
import net.database.SkillTable;
import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_InventoryStatus;
import net.network.server.S_ObjectHitratio;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.WorldMap;
import net.world.function.AgitSystem;
import net.world.function.ClanSystem;
import net.world.instance.ItemInstance;
import net.world.instance.MonsterInstance;
import net.world.instance.PcInstance;
import net.world.instance.books.Books;
import net.world.instance.buff.Buff;
import net.world.instance.inventory.Inventory;
import net.world.instance.skill.Skills;
import net.world.kingdom.Kingdom;
import net.world.kingdom.KingdomWindawood;
import net.world.time.BuffTimerInstance;
import net.world.time.ItemTimerInstance;

public class Character extends L1Object {
  public L1Object fire;
  
  public L1Object fire2;
  
  private long exp;
  
  private int level;
  
  private int currentHp;
  
  private int dynamicHp;
  
  private int maxHp;
  
  private int currentMp;
  
  private int dynamicMp;
  
  private int maxMp;
  
  private int ac;
  
  private int acDex;
  
  private int dynamicAc;
  
  private int Str;
  
  private int Con;
  
  private int Dex;
  
  private int Wis;
  
  private int Int;
  
  private int Cha;
  
  private int dynamicInt;
  
  private int dynamicStr;
  
  private int dynamicCon;
  
  private int dynamicDex;
  
  private int dynamicWis;
  
  private int dynamicCha;
  
  private int food;
  
  private int dynamicMr;
  
  private int dynamicSp;
  
  private int dynamicEarthress;
  
  private int dynamicWaterress;
  
  private int dynamicFireress;
  
  private int dynamicWindress;
  
  private int Earthress;
  
  private int Waterress;
  
  private int Fireress;
  
  private int Windress;
  
  private int dynamicTicHp;
  
  private int dynamicTicMp;
  
  private Inventory inventory;
  
  private Skills skill;
  
  private Books books;
  
  private Buff buff;
  
  protected int TimeHpTic;
  
  protected int TimeMpTic;
  
  private boolean hpMove;
  
  private boolean mpMove;
  
  private boolean hpFight;
  
  private boolean mpFight;
  
  private int ClassType;
  
  protected L1Object toAttackObject;
  
  protected L1Object toRevival;
  
  public int _dmg;
  
  private Map<Integer, Long> _skillDelay = new HashMap<Integer, Long>();
  
  private boolean _isSkillDelay = false;
  
  protected double exprate;
  
  public int getLevel() {
    return this.level;
  }
  
  public void setLevel(int level) {
    this.level = level;
  }
  
  public int getCurrentHp() {
    if (getTotalHp() < this.currentHp)
      this.currentHp = getTotalHp(); 
    return this.currentHp;
  }
  
  public void setCurrentHp(int currentHp) {
    if (!isDead()) {
      if (getTotalHp() < currentHp) {
        currentHp = getTotalHp();
      } else if (currentHp < 0) {
        currentHp = 0;
      } 
      if (isGm() && currentHp == 0)
        currentHp = getTotalHp(); 
      if (Config.SHOW_OW_HPBAR && this instanceof PcInstance) {
        PcInstance pc = (PcInstance)this;
        pc.SendPacket((S_BasePacket)new S_ObjectHitratio((L1Object)pc, true));
      } 
      if (currentHp == 0)
        setDead(true); 
      this.currentHp = currentHp;
      if (Config.BROADCAST_HP_TO_AROUND && (this instanceof MonsterInstance || this instanceof PcInstance))
        for (L1Object obj : getObjectList()) {
          if (obj instanceof PcInstance && obj.isHpBar()) {
            System.out.println(obj.getName());
            obj.SendPacket((S_BasePacket)new S_ObjectHitratio(this, true));
          } 
        }  
    } 
  }
  
  public int getMaxHp() {
    return this.maxHp;
  }
  
  public void setMaxHp(int maxHp) {
    if (maxHp < 1)
      maxHp = 1; 
    this.maxHp = maxHp;
  }
  
  public int getCurrentMp() {
    if (getTotalMp() < this.currentMp)
      this.currentMp = getTotalMp(); 
    return this.currentMp;
  }
  
  public void setCurrentMp(int currentMp) {
    if (!isDead()) {
      if (getTotalMp() < currentMp) {
        currentMp = getTotalMp();
      } else if (currentMp < 0) {
        currentMp = 0;
      } 
      if (isGm() && currentMp == 0)
        currentMp = getTotalMp(); 
      this.currentMp = currentMp;
    } 
  }
  
  public int getMaxMp() {
    return this.maxMp;
  }
  
  public void setMaxMp(int mp) {
    if (mp < 0)
      mp = 0; 
    this.maxMp = mp;
  }
  
  public int getAc() {
    return this.ac;
  }
  
  public void setAc(int ac) {
    if (ac < 0)
      ac = 0; 
    if (ac > 138)
      ac = 138; 
    this.ac = ac;
  }
  
  public int getStr() {
    return this.Str;
  }
  
  public void setStr(int str) {
    this.Str = str;
  }
  
  public int getCon() {
    return this.Con;
  }
  
  public void setCon(int con) {
    this.Con = con;
  }
  
  public int getDex() {
    return this.Dex;
  }
  
  public void setDex(int dex) {
    this.Dex = dex;
  }
  
  public int getWis() {
    return this.Wis;
  }
  
  public void setWis(int wis) {
    this.Wis = wis;
  }
  
  public int getInt() {
    return this.Int;
  }
  
  public void setInt(int i) {
    this.Int = i;
  }
  
  public int getCha() {
    return this.Cha;
  }
  
  public void setCha(int cha) {
    this.Cha = cha;
  }
  
  public long getExp() {
    return this.exp;
  }
  
  public double get_exprate() {
    return this.exprate;
  }
  
  public void set_exprate(double exprate) {
    this.exprate = exprate;
  }
  
  public void addExp(long _exp) {
    if (Config.DEBUG)
      System.out.println("目前:" + getName() + ",level:" + getLevel()); 
    int Lv = getLevel();
    if (_exp > 0L)
      if (Lv < 65) {
        _exp *= Config.RATE_EXP;
      } else if (Lv > 64 && Lv < 80) {
        _exp = (long)(_exp / Math.pow(2.0D, ((Lv - 60) / 5)) * Config.RATE_EXP);
      } else {
        _exp = (long)(_exp / Math.pow(2.0D, (Lv - 76)) * Config.RATE_EXP);
      }  
    if (isExpDouble())
      _exp = (long)(_exp * get_exprate()); 
    if (Config.DEBUG)
      System.out.println("增加exp:" + _exp); 
    this.exp += _exp;
    if (Config.DEBUG)
      System.out.println("最後exp:" + this.exp); 
  }
  
  public void setExp(long exp) {
    this.exp = exp;
  }
  
  public Inventory getInventory() {
    return this.inventory;
  }
  
  public void setInventory(Inventory inventory) {
    this.inventory = inventory;
  }
  
  public Skills getSkill() {
    return this.skill;
  }
  
  public void setSkill(Skills skill) {
    this.skill = skill;
  }
  
  public Books getBooks() {
    return this.books;
  }
  
  public void setBooks(Books books) {
    this.books = books;
  }
  
  public Buff getBuff() {
    return this.buff;
  }
  
  public void setBuff(Buff buff) {
    this.buff = buff;
  }
  
  public int getAcDex() {
    return this.acDex;
  }
  
  public void setAcDex(int acDex) {
    this.acDex = acDex;
  }
  
  public int getDynamicAc() {
    return this.dynamicAc;
  }
  
  public void setDynamicAc(int dynamicAc) {
    this.dynamicAc = dynamicAc;
  }
  
  public int getDynamicInt() {
    return this.dynamicInt;
  }
  
  public void setDynamicInt(int dynamicInt) {
    this.dynamicInt = dynamicInt;
  }
  
  public int getDynamicStr() {
    return this.dynamicStr;
  }
  
  public void setDynamicStr(int dynamicStr) {
    this.dynamicStr = dynamicStr;
  }
  
  public int getDynamicCon() {
    return this.dynamicCon;
  }
  
  public void setDynamicCon(int dynamicCon) {
    this.dynamicCon = dynamicCon;
  }
  
  public int getDynamicDex() {
    return this.dynamicDex;
  }
  
  public void setDynamicDex(int dynamicDex) {
    this.dynamicDex = dynamicDex;
  }
  
  public int getDynamicWis() {
    return this.dynamicWis;
  }
  
  public void setDynamicWis(int dynamicWis) {
    this.dynamicWis = dynamicWis;
  }
  
  public int getDynamicCha() {
    return this.dynamicCha;
  }
  
  public void setDynamicCha(int dynamicCha) {
    this.dynamicCha = dynamicCha;
  }
  
  public int getFood() {
    return this.food;
  }
  
  public void setFood(int food) {
    if (food >= 29) {
      food = 29;
    } else if (food <= 0) {
      food = 0;
    } 
    this.food = food;
  }
  
  public int getDynamicMr() {
    return this.dynamicMr;
  }
  
  public void setDynamicMr(int dynamicMr) {
    this.dynamicMr = dynamicMr;
  }
  
  public int getDynamicSp() {
    return this.dynamicSp;
  }
  
  public void setDynamicSp(int dynamicSp) {
    this.dynamicSp = dynamicSp;
  }
  
  public int getTotalEarthress() {
    return getEarthress() + getDynamicEarthress();
  }
  
  public int getDynamicEarthress() {
    return this.dynamicEarthress;
  }
  
  public void setDynamicEarthress(int dynamicEarthress) {
    this.dynamicEarthress = dynamicEarthress;
  }
  
  public int getTotalWaterress() {
    return getWaterress() + getDynamicWaterress();
  }
  
  public int getDynamicWaterress() {
    return this.dynamicWaterress;
  }
  
  public void setDynamicWaterress(int dynamicWaterress) {
    this.dynamicWaterress = dynamicWaterress;
  }
  
  public int getDynamicFireress() {
    return this.dynamicFireress;
  }
  
  public void setDynamicFireress(int dynamicFireress) {
    this.dynamicFireress = dynamicFireress;
  }
  
  public int getDynamicWindress() {
    return this.dynamicWindress;
  }
  
  public void setDynamicWindress(int dynamicWindress) {
    this.dynamicWindress = dynamicWindress;
  }
  
  public int getEarthress() {
    return this.Earthress;
  }
  
  public void setEarthress(int earthress) {
    this.Earthress = earthress;
  }
  
  public int getWaterress() {
    return this.Waterress;
  }
  
  public void setWaterress(int waterress) {
    this.Waterress = waterress;
  }
  
  public int getTotalFireress() {
    return getFireress() + getDynamicFireress();
  }
  
  public int getFireress() {
    return this.Fireress;
  }
  
  public void setFireress(int fireress) {
    this.Fireress = fireress;
  }
  
  public int getTotalWindress() {
    return getWindress() + getDynamicWindress();
  }
  
  public int getWindress() {
    return this.Windress;
  }
  
  public void setWindress(int windress) {
    this.Windress = windress;
  }
  
  public int getDynamicHp() {
    return this.dynamicHp;
  }
  
  public void setDynamicHp(int dynamicHp) {
    this.dynamicHp = dynamicHp;
  }
  
  public int getDynamicMp() {
    return this.dynamicMp;
  }
  
  public void setDynamicMp(int dynamicMp) {
    this.dynamicMp = dynamicMp;
  }
  
  public int getDynamicTicHp() {
    return this.dynamicTicHp;
  }
  
  public void setDynamicTicHp(int dynamicTicHp) {
    if (dynamicTicHp < 0)
      dynamicTicHp = 0; 
    this.dynamicTicHp = dynamicTicHp;
  }
  
  public int getDynamicTicMp() {
    return this.dynamicTicMp;
  }
  
  public void setDynamicTicMp(int dynamicTicMp) {
    if (dynamicTicMp < 0)
      dynamicTicMp = 0; 
    this.dynamicTicMp = dynamicTicMp;
  }
  
  public int getClassType() {
    return this.ClassType;
  }
  
  public String getClassTypeName() {
    switch (getClassType()) {
      case 0:
        return "王";
      case 1:
        return "?";
      case 2:
        return "妖";
      case 3:
        return "法";
    } 
    return null;
  }
  
  public void setClassType(int classType) {
    this.ClassType = classType;
  }
  
  public int getTotalStr() {
    return getStr() + getDynamicStr() + getLvStr();
  }
  
  public int getTotalDex() {
    return getDex() + getDynamicDex() + getLvDex();
  }
  
  public int getTotalCon() {
    return getCon() + getDynamicCon() + getLvCon();
  }
  
  public int getTotalCha() {
    return getCha() + getDynamicCha() + getLvCha();
  }
  
  public int getTotalInt() {
    return getInt() + getDynamicInt() + getLvInt();
  }
  
  public int getTotalWis() {
    return getWis() + getDynamicWis() + getLvWis();
  }
  
  public int getTotalHp() {
    return getMaxHp() + getDynamicHp();
  }
  
  public int getTotalMp() {
    return getMaxMp() + getDynamicMp();
  }
  
  public int getTotalAc() {
    if (getAc() + getAcDex() + getDynamicAc() > 138)
      return 138; 
    return getAc() + getAcDex() + getDynamicAc();
  }
  
  public int getEr() {
    int er = 0;
    if (getTotalDex() < 8) {
      er = -1;
    } else {
      er = (getTotalDex() - 8) / 2;
    } 
    er += getLevel() / 10;
    if (isStatusStonesArmor())
      er += 15; 
    return er;
  }
  
  public int getSp(boolean wispotion_check) {
    int _Sp = 0;
    if (getInt() < 9) {
      _Sp--;
    } else if (getInt() < 11) {
      _Sp += 0;
    } else if (getInt() < 15) {
      _Sp++;
    } else if (getInt() < 18) {
      _Sp += 2;
    } else {
      _Sp += getInt() - 15;
    } 
    switch (getClassType()) {
      case 0:
        if (getLevel() >= 10)
          _Sp++; 
        if (getLevel() >= 20)
          _Sp++; 
        break;
      case 1:
        if (getLevel() >= 50)
          _Sp++; 
        break;
      case 2:
        if (getLevel() >= 8)
          _Sp++; 
        if (getLevel() >= 16)
          _Sp++; 
        if (getLevel() >= 24)
          _Sp++; 
        if (getLevel() >= 32)
          _Sp++; 
        if (getLevel() >= 40)
          _Sp++; 
        if (getLevel() >= 48)
          _Sp++; 
        break;
      case 3:
        if (getLevel() >= 4)
          _Sp++; 
        if (getLevel() >= 8)
          _Sp++; 
        if (getLevel() >= 12)
          _Sp++; 
        if (getLevel() >= 16)
          _Sp++; 
        if (getLevel() >= 20)
          _Sp++; 
        if (getLevel() >= 24)
          _Sp++; 
        if (getLevel() >= 28)
          _Sp++; 
        if (getLevel() >= 32)
          _Sp++; 
        if (getLevel() >= 36)
          _Sp++; 
        if (getLevel() >= 40)
          _Sp++; 
        break;
    } 
    if (wispotion_check && ItemTimerInstance.getInstance().contains(this, "$944 "))
      return getDynamicSp() + _Sp - 2; 
    return getDynamicSp() + _Sp + getLvInt();
  }
  
  public int getMr() {
    int mr = 0;
    switch (getClassType()) {
      case 0:
      case 4:
        mr = 10;
        break;
      case 1:
        mr = 0;
        break;
      case 2:
        mr = 25;
        break;
      case 3:
        mr = 15;
        break;
    } 
    mr += getDynamicMr() + toOriginalStatMR();
    if (mr < 0)
      mr = 0; 
    if (mr > 100)
      mr = 100; 
    return mr;
  }
  
  public int hpTic() {
    int tic = getDynamicTicHp();
    switch (getCon()) {
      case 24:
      case 25:
        tic += 8;
      case 22:
      case 23:
        tic += 7;
      case 20:
      case 21:
        tic += 6;
      case 18:
      case 19:
        tic += 5;
      case 16:
      case 17:
        tic += 4;
      case 12:
      case 13:
      case 14:
      case 15:
        tic += 3;
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
        tic += 2;
        break;
    } 
    if (getCon() > 25) {
      tic += 10;
    } else {
      tic++;
    } 
    tic = Util.rand(tic - 4, tic);
    return (tic < 0) ? 0 : tic;
  }
  
  public int mpTic() {
    int tic = getDynamicTicMp();
    switch (getWis()) {
      case 24:
      case 25:
        tic += 4;
      case 18:
      case 19:
      case 20:
      case 21:
      case 22:
      case 23:
        tic += 3;
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
      case 17:
        tic += 2;
        break;
    } 
    if (getWis() > 25) {
      tic += 5;
    } else {
      tic++;
    } 
    return (tic < 0) ? 0 : tic;
  }
  
  public void setMove(boolean move) {
    if (!isMove() && move) {
      if (!this.hpMove) {
        this.hpMove = true;
        this.TimeHpTic += 5;
      } 
      if (!this.mpMove) {
        this.mpMove = true;
        this.TimeMpTic += 5;
      } 
    } 
    super.setMove(move);
  }
  
  public void setFight(boolean fight) {
    if (fight) {
      if (!this.hpFight) {
        this.hpFight = true;
        this.TimeHpTic += 15;
      } 
      if (!this.mpFight) {
        this.mpFight = true;
        this.TimeMpTic += 15;
      } 
    } 
    super.setFight(fight);
  }
  
  public boolean isHpTic() {
    if (--this.TimeHpTic <= 0) {
      setMove(false);
      setFight(false);
      this.hpFight = false;
      this.hpMove = false;
      this.TimeHpTic = getHpTime();
      return true;
    } 
    return false;
  }
  
  public boolean isMpTic() {
    if (--this.TimeMpTic <= 0) {
      setMove(false);
      setFight(false);
      this.mpFight = false;
      this.mpMove = false;
      this.TimeMpTic = getMpTime();
      return true;
    } 
    return false;
  }
  
  protected int getHpTime() {
    int time = 35;
    if (getFood() >= 5) {
      time -= 10;
      if (getFood() >= 15) {
        time -= 10;
        if (getFood() >= 29)
          time -= 5; 
      } 
    } 
    if (getClassType() == 2 && WorldMap.getInstance().isElfTree(this))
      time -= 8; 
    if (AgitSystem.getInstance().checkAgitLocation(this))
      time -= 8; 
    if (getMap() == KingdomWindawood.getInstance().getInMap())
      time -= 8; 
    time -= toOriginalStatHpTic();
    if (time < 0)
      time = 0; 
    return time;
  }
  
  protected int getMpTime() {
    int time = 40;
    if (getFood() >= 5) {
      time -= 10;
      if (getFood() >= 15) {
        time -= 10;
        if (getFood() >= 29)
          time -= 5; 
      } 
    } 
    if (getClassType() == 2 && WorldMap.getInstance().isElfTree(this))
      time -= 10; 
    if (AgitSystem.getInstance().checkAgitLocation(this))
      time -= 10; 
    if (getMap() == KingdomWindawood.getInstance().getInMap())
      time -= 10; 
    time -= toOriginalStatMpTic();
    if (time < 0)
      time = 0; 
    return time;
  }
  
  public long Exp(L1Object temp, double exp) {
    return (long)exp;
  }
  
  public boolean LongAttackCK(L1Object temp, int loc) {
    int myx = getX();
    int myy = getY();
    int map = getMap();
    int tax = temp.getX();
    int tay = temp.getY();
    int count = 12;
    int h = 0;
    do {
      h = calcheading(myx, myy, tax, tay);
      if (!WorldMap.getInstance().IsThroughAttack(myx, myy, map, h))
        return false; 
      switch (h) {
        case 0:
          myy--;
          break;
        case 1:
          myx++;
          myy--;
          break;
        case 2:
          myx++;
          break;
        case 3:
          myx++;
          myy++;
          break;
        case 4:
          myy++;
          break;
        case 5:
          myx--;
          myy++;
          break;
        case 6:
          myx--;
          break;
        default:
          myx--;
          myy--;
          break;
      } 
      if (myx == tax && myy == tay)
        break; 
      --count;
    } while (count > 0);
    return true;
  }
  
  public int StatusUP(boolean HpMp) {
    int con = getCon() + getLvCon();
    int wis = getWis() + getLvWis();
    int start_hp = 0;
    int start_mp = 0;
    int temp = 0;
    int HPMP = 0;
    if (HpMp) {
      if (this instanceof PcInstance) {
        switch (getClassType()) {
          case 0:
          case 2:
            temp = Util.rand(1, 32);
            if (con <= 15) {
              start_hp = 5;
            } else {
              start_hp = con - 10;
            } 
            if (temp <= 6) {
              start_hp++;
            } else if (temp <= 16) {
              start_hp += 2;
            } else if (temp <= 26) {
              start_hp += 3;
            } else if (temp <= 31) {
              start_hp += 4;
            } else {
              start_hp += 5;
            } 
            HPMP = start_hp;
            break;
          case 1:
            temp = Util.rand(1, 64);
            if (con <= 15) {
              start_hp = 6;
            } else {
              start_hp = con - 9;
            } 
            if (temp <= 7) {
              start_hp++;
            } else if (temp <= 22) {
              start_hp += 2;
            } else if (temp <= 42) {
              start_hp += 3;
            } else if (temp <= 57) {
              start_hp += 4;
            } else if (temp <= 63) {
              start_hp += 5;
            } else {
              start_hp += 6;
            } 
            HPMP = start_hp;
            break;
          case 3:
            temp = Util.rand(1, 8);
            if (con <= 15) {
              start_hp = 3;
            } else {
              start_hp = con - 12;
            } 
            if (temp <= 4) {
              start_hp++;
            } else if (temp <= 7) {
              start_hp += 2;
            } else {
              start_hp += 3;
            } 
            HPMP = start_hp;
            break;
        } 
      } else if (this instanceof net.world.instance.SummonInstance) {
        temp = Util.rand(1, 32);
        start_hp = 5;
        if (temp <= 6) {
          start_hp++;
        } else if (temp <= 16) {
          start_hp += 2;
        } else if (temp <= 26) {
          start_hp += 3;
        } else if (temp <= 31) {
          start_hp += 4;
        } else {
          start_hp += 5;
        } 
        HPMP = start_hp;
      } 
    } else {
      switch (getClassType()) {
        case 0:
          if (wis <= 11) {
            start_mp = Util.rand(2, 3);
          } else if (wis >= 12 && wis <= 14) {
            temp = Util.rand(1, 4);
            if (temp == 1) {
              start_mp = 2;
            } else if (temp <= 3) {
              start_mp = 3;
            } else {
              start_mp = 4;
            } 
          } else if (wis >= 15 && wis <= 17) {
            temp = Util.rand(1, 4);
            if (temp == 1) {
              start_mp = 3;
            } else if (temp <= 3) {
              start_mp = 4;
            } else {
              start_mp = 5;
            } 
          } else if (wis >= 18 && wis <= 20) {
            temp = Util.rand(1, 6);
            if (temp == 1) {
              start_mp = 3;
            } else if (temp <= 3) {
              start_mp = 4;
            } else if (temp <= 5) {
              start_mp = 5;
            } else {
              start_mp = 6;
            } 
          } else if (wis >= 21 && wis <= 23) {
            temp = Util.rand(1, 10);
            if (temp == 1) {
              start_mp = 3;
            } else if (temp <= 3) {
              start_mp = 4;
            } else if (temp <= 7) {
              start_mp = 5;
            } else if (temp <= 9) {
              start_mp = 6;
            } else {
              start_mp = 7;
            } 
          } else if (wis >= 24 && wis <= 26) {
            temp = Util.rand(1, 14);
            if (temp == 1) {
              start_mp = 3;
            } else if (temp <= 3) {
              start_mp = 4;
            } else if (temp <= 7) {
              start_mp = 5;
            } else if (temp <= 11) {
              start_mp = 6;
            } else if (temp <= 13) {
              start_mp = 7;
            } else {
              start_mp = 8;
            } 
          } else {
            temp = Util.rand(1, 22);
            if (temp == 1) {
              start_mp = 3;
            } else if (temp <= 3) {
              start_mp = 4;
            } else if (temp <= 7) {
              start_mp = 5;
            } else if (temp <= 15) {
              start_mp = 6;
            } else if (temp <= 19) {
              start_mp = 7;
            } else if (temp <= 21) {
              start_mp = 8;
            } else {
              start_mp = 9;
            } 
          } 
          HPMP = start_mp;
          break;
        case 1:
          if (wis <= 9) {
            temp = Util.rand(1, 4);
            if (temp == 1) {
              start_mp = 0;
            } else if (temp <= 3) {
              start_mp = 1;
            } else {
              start_mp = 2;
            } 
          } else {
            temp = Util.rand(1, 4);
            if (temp == 1) {
              start_mp = 1;
            } else if (temp <= 3) {
              start_mp = 2;
            } else {
              start_mp = 3;
            } 
          } 
          HPMP = start_mp;
          break;
        case 2:
          if (wis <= 14) {
            temp = Util.rand(1, 6);
            if (temp == 1) {
              start_mp = 3;
            } else if (temp <= 3) {
              start_mp = 4;
            } else if (temp <= 5) {
              start_mp = 5;
            } else {
              start_mp = 6;
            } 
          } else if (wis >= 15 && wis <= 17) {
            temp = Util.rand(1, 6);
            if (temp == 1) {
              start_mp = 4;
            } else if (temp <= 3) {
              start_mp = 5;
            } else if (temp <= 5) {
              start_mp = 6;
            } else {
              start_mp = 7;
            } 
          } else if (wis >= 18 && wis <= 20) {
            temp = Util.rand(1, 14);
            if (temp == 1) {
              start_mp = 4;
            } else if (temp <= 3) {
              start_mp = 5;
            } else if (temp <= 7) {
              start_mp = 6;
            } else if (temp <= 11) {
              start_mp = 7;
            } else if (temp <= 13) {
              start_mp = 8;
            } else {
              start_mp = 9;
            } 
          } else if (wis >= 21 && wis <= 23) {
            temp = Util.rand(1, 30);
            if (temp == 1) {
              start_mp = 4;
            } else if (temp <= 3) {
              start_mp = 5;
            } else if (temp <= 7) {
              start_mp = 6;
            } else if (temp <= 15) {
              start_mp = 7;
            } else if (temp <= 23) {
              start_mp = 8;
            } else if (temp <= 27) {
              start_mp = 9;
            } else if (temp <= 29) {
              start_mp = 10;
            } else {
              start_mp = 11;
            } 
          } else if (wis >= 24 && wis <= 26) {
            temp = Util.rand(1, 62);
            if (temp == 1) {
              start_mp = 4;
            } else if (temp <= 3) {
              start_mp = 5;
            } else if (temp <= 7) {
              start_mp = 6;
            } else if (temp <= 15) {
              start_mp = 7;
            } else if (temp <= 31) {
              start_mp = 8;
            } else if (temp <= 47) {
              start_mp = 9;
            } else if (temp <= 55) {
              start_mp = 10;
            } else if (temp <= 59) {
              start_mp = 11;
            } else if (temp <= 61) {
              start_mp = 12;
            } else {
              start_mp = 13;
            } 
          } else {
            temp = Util.rand(1, 126);
            if (temp == 1) {
              start_mp = 4;
            } else if (temp <= 3) {
              start_mp = 5;
            } else if (temp <= 7) {
              start_mp = 6;
            } else if (temp <= 15) {
              start_mp = 7;
            } else if (temp <= 31) {
              start_mp = 8;
            } else if (temp <= 63) {
              start_mp = 9;
            } else if (temp <= 95) {
              start_mp = 10;
            } else if (temp <= 111) {
              start_mp = 11;
            } else if (temp <= 119) {
              start_mp = 12;
            } else if (temp <= 123) {
              start_mp = 13;
            } else if (temp <= 125) {
              start_mp = 14;
            } else {
              start_mp = 15;
            } 
          } 
          HPMP = start_mp;
          break;
        case 3:
          if (wis <= 14) {
            temp = Util.rand(1, 10);
            if (temp == 1) {
              start_mp = 4;
            } else if (temp <= 3) {
              start_mp = 5;
            } else if (temp <= 7) {
              start_mp = 6;
            } else if (temp <= 9) {
              start_mp = 7;
            } else {
              start_mp = 8;
            } 
          } else if (wis >= 15 && wis <= 17) {
            temp = Util.rand(1, 10);
            if (temp == 1) {
              start_mp = 6;
            } else if (temp <= 3) {
              start_mp = 7;
            } else if (temp <= 7) {
              start_mp = 8;
            } else if (temp <= 9) {
              start_mp = 9;
            } else {
              start_mp = 10;
            } 
          } else if (wis >= 18 && wis <= 20) {
            temp = Util.rand(1, 22);
            if (temp == 1) {
              start_mp = 6;
            } else if (temp <= 3) {
              start_mp = 7;
            } else if (temp <= 7) {
              start_mp = 8;
            } else if (temp <= 15) {
              start_mp = 9;
            } else if (temp <= 19) {
              start_mp = 10;
            } else if (temp <= 21) {
              start_mp = 11;
            } else {
              start_mp = 12;
            } 
          } else if (wis >= 21 && wis <= 23) {
            temp = Util.rand(1, 46);
            if (temp == 1) {
              start_mp = 6;
            } else if (temp <= 3) {
              start_mp = 7;
            } else if (temp <= 7) {
              start_mp = 8;
            } else if (temp <= 15) {
              start_mp = 9;
            } else if (temp <= 31) {
              start_mp = 10;
            } else if (temp <= 39) {
              start_mp = 11;
            } else if (temp <= 43) {
              start_mp = 12;
            } else if (temp <= 45) {
              start_mp = 13;
            } else {
              start_mp = 14;
            } 
          } else if (wis >= 24 && wis <= 26) {
            temp = Util.rand(1, 94);
            if (temp == 1) {
              start_mp = 6;
            } else if (temp <= 3) {
              start_mp = 7;
            } else if (temp <= 7) {
              start_mp = 8;
            } else if (temp <= 15) {
              start_mp = 9;
            } else if (temp <= 31) {
              start_mp = 10;
            } else if (temp <= 63) {
              start_mp = 11;
            } else if (temp <= 79) {
              start_mp = 12;
            } else if (temp <= 87) {
              start_mp = 13;
            } else if (temp <= 91) {
              start_mp = 14;
            } else if (temp <= 93) {
              start_mp = 15;
            } else {
              start_mp = 16;
            } 
          } else {
            temp = Util.rand(1, 190);
            if (temp == 1) {
              start_mp = 6;
            } else if (temp <= 3) {
              start_mp = 7;
            } else if (temp <= 7) {
              start_mp = 8;
            } else if (temp <= 15) {
              start_mp = 9;
            } else if (temp <= 31) {
              start_mp = 10;
            } else if (temp <= 63) {
              start_mp = 11;
            } else if (temp <= 127) {
              start_mp = 12;
            } else if (temp <= 159) {
              start_mp = 13;
            } else if (temp <= 175) {
              start_mp = 14;
            } else if (temp <= 183) {
              start_mp = 15;
            } else if (temp <= 187) {
              start_mp = 16;
            } else if (temp <= 189) {
              start_mp = 17;
            } else {
              start_mp = 18;
            } 
          } 
          HPMP = start_mp;
          break;
      } 
    } 
    return HPMP;
  }
  
  public boolean isPkLevel(L1Object target) {
    if (target instanceof PcInstance && (getLevel() < 1 || target.getLevel() < 1)) {
      Kingdom k = ClanSystem.getInstance().isKingdomZone(this);
      if (k != null) {
        if (!k.isWar())
          return false; 
      } else {
        return false;
      } 
    } 
    return true;
  }
  
  public int DmgSystem(L1Object target, boolean bow, int count) {
    int dmg = 0;
    try {
      boolean isshock = BuffTimerInstance.getInstance().contains(target, 87);
      if (!target.isLock() || isshock)
        if (this instanceof PcInstance && isPkLevel(target)) {
          ItemInstance weapon = getInventory().getSlot(11);
          if (WorldMap.getInstance().AttackZone(this, target)) {
            if (getGfx() == 32)
              return Util.rand(5, 15); 
            if (HitFigure(target, weapon)) {
              if (Config.TEST_HIT)
                System.out.println("命中"); 
              if (weapon == null) {
                dmg = Util.rand(0, DmgFigure(false));
                if (dmg <= 0)
                  dmg = Util.rand(0, 1); 
              } else {
                boolean small = true;
                if (target instanceof MonsterInstance) {
                  MonsterInstance mon = (MonsterInstance)target;
                  small = "small".equalsIgnoreCase(mon.getMon().getSize());
                } 
                dmg = DmgFigure(bow);
                dmg = DmgWeaponFigure(dmg, weapon, small);
                dmg = DmgPlus(weapon, target, bow, dmg);
                Random rnd = new Random();
                Skill theskill = SkillTable.getInstance().getTemplate(116);
                if (isStatusBraveMind())
                  dmg += 10; 
                if (!bow && isStatusFireWeapon())
                  dmg += 4; 
                if (!bow && isStatusBurningWeapon())
                  dmg += 6; 
                if (!bow && isStatusBlessOfFire())
                  dmg += 5; 
                if (bow && isStatusEyeOfStorm())
                  dmg += 3; 
                if (bow && isStatusStormShot())
                  dmg += 6; 
                if (target instanceof PcInstance && target.isStatusArmorSword() && getInventory() != null) {
                  ItemInstance weapons = getInventory().getSlot(11);
                  if (weapons != null) {
                    weapons.setDurability((short)(weapons.getDurability() + 1));
                    SendPacket((S_BasePacket)new S_ServerMessage(268, weapons.toString()));
                    SendPacket((S_BasePacket)new S_InventoryStatus(weapons));
                  } 
                } 
                if (target instanceof MonsterInstance) {
                  MonsterInstance mon = (MonsterInstance)target;
                  if (mon.getMon().isUndead())
                    if (weapon.getItem().getType() == 3) {
                      ItemInstance arrow = getInventory().getArrow();
                      if (arrow != null)
                        switch (arrow.getItem().get_material()) {
                          case 14:
                          case 17:
                          case 22:
                            dmg += Util.rand(1, 10);
                            break;
                        }  
                    } else {
                      switch (weapon.getItem().get_material()) {
                        case 14:
                        case 17:
                        case 22:
                          dmg += Util.rand(1, 10);
                          break;
                      } 
                    }  
                  if (mon.getMon().isToughskin() && bow)
                    dmg /= 2; 
                } else if (Config.REDUCE_INJURY_AC != 0 && target instanceof PcInstance) {
                  PcInstance targetPc = (PcInstance)target;
                  int totalAc = targetPc.getTotalAc() / Config.REDUCE_INJURY_AC;
                  dmg -= Util.rand(1, totalAc);
                } 
                dmg -= weapon.getDurability();
              } 
            } else if (Config.TEST_HIT) {
              System.out.println("?命中");
            } 
          } 
        } else if (this instanceof net.world.npc.Guard) {
          if (this instanceof net.world.kingdom.function.KingdomGuard) {
            if (!(this instanceof net.world.kingdom.function.KentBowGuard))
              dmg = Util.rand(1, 2); 
          } else {
            dmg = Util.rand(40, 80);
          } 
        } else if (this instanceof net.world.instance.SummonInstance) {
          if (WorldMap.getInstance().AttackZone(this, target))
            if (this instanceof net.world.instance.PetInstance) {
              dmg = 6;
              int j = 0;
              for (int i = 0; i < 10; i++) {
                if (i * 5 > getLevel())
                  break; 
                j = i;
                dmg++;
              } 
              dmg = Util.rand(j, dmg);
            } else {
              dmg = Util.rand(0, getLevel());
            }  
        } else if (this instanceof MonsterInstance) {
          MonsterInstance mon = (MonsterInstance)this;
          dmg = Util.rand(mon.getMon().getMinDmg(), mon.getMon().getMaxDmg());
          if (target instanceof Character) {
            dmg -= Util.rand(1, ((Character)target).getTotalAc());
            if (dmg < 0)
              dmg = 0; 
          } 
        }  
    } catch (Exception localException) {}
    if (target.isBuffImmuneToHarm())
      dmg /= 2; 
    if (dmg < 0)
      dmg = 0; 
    if (Config.TEST_REDUCE_INJURY_AC && this instanceof PcInstance) {
      System.out.println("最後傷害值：" + dmg);
      this.testDmgList.add(Integer.valueOf(dmg));
      if (this.testDmgList.size() >= 20) {
        int q = 0;
        for (Iterator<Integer> iter = this.testDmgList.iterator(); iter.hasNext(); ) {
          int i = ((Integer)iter.next()).intValue();
          q += i;
        } 
        System.out.println("20次平均傷害值：" + (q / 20));
        this.testDmgList.clear();
      } 
    } 
    return dmg;
  }
  
  private List<Integer> testDmgList = new ArrayList<Integer>();
  
  private boolean HitFigure(L1Object target, ItemInstance weapon) {
    int basic_flee = 5;
    int target_flee = 0;
    int stat = 0;
    int max_flee = 29;
    basic_flee += toHitLv();
    if (isStatusHoterWeapon())
      basic_flee += 5; 
    if (weapon != null && weapon.getItem().getType() == 3) {
      stat = toHitDex() + toOriginalStatBowHit();
      if (target instanceof Character) {
        Character cha = (Character)target;
        target_flee = cha.getEr() + cha.toOriginalStatER();
        if (target.getLevel() >= getLevel() + 5)
          target_flee += target.getLevel() - getLevel() + 5; 
      } 
      if (isStatusWindShot())
        basic_flee += 6; 
      if (isStatusEyeOfStorm())
        basic_flee += 2; 
      if (isStatusStormShot())
        basic_flee += 3; 
    } else {
      stat = toHitStr() + toOriginalStatHit();
      if (target instanceof Character) {
        Character cha = (Character)target;
        target_flee = cha.getTotalAc() / 3;
        if (target.getLevel() >= getLevel() + 5)
          target_flee += target.getLevel() - getLevel() + 5; 
      } 
    } 
    basic_flee += stat;
    basic_flee += stat / 3;
    basic_flee -= target_flee;
    if (basic_flee <= max_flee)
      return (Util.rand(0, max_flee) < Util.rand(basic_flee, max_flee)); 
    return true;
  }
  
  private int DmgFigure(boolean bow) {
    int dmg = 0;
    if (bow) {
      if (getClassType() == 2)
        dmg += Util.rand(0, getLevel() / 10); 
      dmg += dmgFigureCalcDex();
      dmg += toOriginalStatBowDamage();
    } else {
      if (getClassType() == 1)
        dmg += Util.rand(0, getLevel() / 10); 
      dmg += dmgFigureCalcStr();
      dmg += toOriginalStatDamage();
    } 
    return dmg;
  }
  
  private int dmgFigureCalcDex() {
    int dmg = 0;
    int stat = 0;
    if (getTotalDex() == 15) {
      stat = 1;
    } else if (getTotalDex() == 16) {
      stat = 2;
    } else if (getTotalDex() == 17) {
      stat = 3;
    } else if (getTotalDex() >= 18 && getTotalDex() <= 20) {
      stat = 4;
    } else if (getTotalDex() >= 21 && getTotalDex() <= 23) {
      stat = 5;
    } else if (getTotalDex() >= 24 && getTotalDex() <= 26) {
      stat = 6;
    } 
    if (getTotalDex() > 26)
      stat += getTotalDex() - 26; 
    if (stat < 0) {
      dmg += Util.rand(stat, 0);
    } else {
      dmg += Util.rand(0, stat);
    } 
    return dmg;
  }
  
  private int dmgFigureCalcStr() {
    int dmg = 0;
    int stat = 0;
    if (getTotalStr() <= 8) {
      stat = -2;
    } else if (getTotalStr() >= 9 && getTotalStr() <= 10) {
      stat = -1;
    } else if (getTotalStr() >= 11 && getTotalStr() <= 12) {
      stat = 0;
    } else if (getTotalStr() >= 13 && getTotalStr() <= 14) {
      stat = 1;
    } else if (getTotalStr() >= 15 && getTotalStr() <= 16) {
      stat = 2;
    } else if (getTotalStr() >= 17 && getTotalStr() <= 18) {
      stat = 3;
    } else if (getTotalStr() >= 19 && getTotalStr() <= 20) {
      stat = 4;
    } else if (getTotalStr() >= 21 && getTotalStr() <= 22) {
      stat = 5;
    } else if (getTotalStr() >= 23 && getTotalStr() <= 26) {
      stat = 6;
    } else if (getTotalStr() >= 27 && getTotalStr() <= 28) {
      stat = 7;
    } else if (getTotalStr() >= 29 && getTotalStr() <= 30) {
      stat = 8;
    } else if (getTotalStr() >= 31) {
      stat = 9;
    } 
    if (getTotalStr() > 31)
      stat += getTotalStr() - 31; 
    if (stat < 0) {
      dmg += Util.rand(stat, 0);
    } else {
      dmg += Util.rand(0, stat);
    } 
    return dmg;
  }
  
  private int DmgWeaponFigure(int dmg, ItemInstance weapon, boolean small) {
    int d = 0;
    boolean flag = false;
    int weaponId = weapon.getItem().getItemId();
    if (weaponId == 390 || weaponId == 391 || weaponId == 392)
      flag = true; 
    if (small) {
      int smallDmg = weapon.getItem().get_dmgsmall();
      if (flag)
        smallDmg = randDgm(smallDmg); 
      d = smallDmg + weapon.getEnLevel() + weapon.getItem().getAddDmg();
    } else {
      int largeDmg = weapon.getItem().get_dmglarge();
      if (flag)
        largeDmg = randDgm(largeDmg); 
      d = largeDmg + weapon.getEnLevel() + weapon.getItem().getAddDmg();
    } 
    if (weapon.getItem().getType() == 3) {
      ItemInstance arrow = getInventory().getArrow();
      if (arrow != null)
        if (small) {
          d += arrow.getItem().get_dmgsmall();
        } else {
          d += arrow.getItem().get_dmglarge();
        }  
    } 
    d = Util.rand(0, d);
    if (weapon.getEnLevel() > 6) {
      int temp = 1;
      if (weapon.getEnLevel() > 7)
        temp = weapon.getEnLevel() - 5 + weapon.getEnLevel() - 8; 
      d += Util.rand(0, temp);
    } 
    if (weapon.getBless() == 0 && Util.rand(0, 100) <= 10)
      d += Util.rand(0, 2); 
    return dmg + d;
  }
  
  private int randDgm(int dmg) {
    switch (getStr()) {
      case 18:
        dmg = (int)Util.rand(dmg * 0.1D, dmg);
        break;
      case 19:
        dmg = (int)Util.rand(dmg * 0.2D, dmg);
        break;
      case 24:
        dmg = (int)Util.rand(dmg * 0.3D, dmg);
        break;
      case 26:
        dmg = (int)Util.rand(dmg * 0.4D, dmg);
        break;
      case 27:
        dmg = (int)Util.rand(dmg * 0.5D, dmg);
        break;
      case 28:
      case 29:
      case 30:
        dmg = (int)Util.rand(dmg * 0.6D, dmg);
        break;
      case 31:
        dmg = (int)Util.rand(dmg * 0.7D, dmg);
        break;
      case 32:
        dmg = (int)Util.rand(dmg * 0.8D, dmg);
        break;
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
        dmg = (int)Util.rand(dmg * 0.9D, dmg);
        break;
      default:
        dmg = Util.rand(1, dmg);
        break;
    } 
    switch (getLevel()) {
      case 49:
        dmg++;
        break;
      case 52:
        dmg += 2;
        break;
      case 55:
        dmg += 3;
        break;
      case 60:
        dmg += 4;
        break;
      case 65:
      case 66:
      case 67:
      case 68:
      case 69:
      case 70:
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
      case 76:
      case 77:
      case 78:
      case 79:
      case 80:
        dmg += 5;
        break;
    } 
    return dmg;
  }
  
  private int DmgPlus(ItemInstance weapon, L1Object target, boolean bow, int dmg) {
    if (weapon != null && weapon.isBuffEnchantWeapon())
      dmg += 2; 
    return dmg;
  }
  
  private int toHitLv() {
    if (getClassType() == 1)
      return getLevel() / 3; 
    return getLevel() / 5;
  }
  
  private int toHitStr() {
    return getTotalStr() - 10;
  }
  
  private int toHitDex() {
    return getTotalDex() - 10;
  }
  
  public int toOriginalStatMagicDamage() {
    int sum = 0;
    int Int = getInt();
    switch (getClassType()) {
      case 3:
        Int -= 12;
        if (Int >= 1)
          sum++; 
        break;
    } 
    return sum;
  }
  
  public int toOriginalStatMagicCritical() {
    int sum = 0;
    int Int = getInt();
    switch (getClassType()) {
      case 2:
        Int -= 12;
        if (Int >= 2)
          sum++; 
        if (Int >= 4)
          sum++; 
        break;
      case 3:
        Int -= 12;
        if (Int >= 3)
          sum++; 
        if (Int >= 4)
          sum++; 
        if (Int >= 5)
          sum++; 
        if (Int >= 6)
          sum++; 
        break;
    } 
    return sum;
  }
  
  public int toOriginalStatMagicHit() {
    int sum = 0;
    int Int = getInt();
    switch (getClassType()) {
      case 0:
        Int -= 10;
        if (Int >= 2)
          sum++; 
        if (Int >= 4)
          sum++; 
        break;
      case 1:
        Int -= 8;
        if (Int >= 2)
          sum++; 
        if (Int >= 4)
          sum++; 
        break;
      case 2:
        Int -= 12;
        if (Int >= 1)
          sum++; 
        if (Int >= 3)
          sum++; 
        break;
      case 3:
        Int -= 12;
        if (Int >= 2)
          sum++; 
        break;
    } 
    return sum;
  }
  
  public int toOriginalStatMpTic() {
    int sum = 0;
    int wis = getWis();
    switch (getClassType()) {
      case 0:
        wis -= 11;
        if (wis >= 2)
          sum++; 
        if (wis >= 4)
          sum++; 
        break;
      case 1:
        wis -= 9;
        if (wis >= 2)
          sum++; 
        if (wis >= 4)
          sum++; 
        break;
      case 2:
        wis -= 12;
        if (wis >= 3)
          sum++; 
        if (wis >= 6)
          sum++; 
        break;
      case 3:
        wis -= 12;
        if (wis >= 2)
          sum++; 
        if (wis >= 4)
          sum++; 
        if (wis >= 6)
          sum++; 
        break;
    } 
    return sum;
  }
  
  public int toOriginalStatMR() {
    int sum = 0;
    int wis = getWis();
    switch (getClassType()) {
      case 0:
        wis -= 11;
        if (wis >= 1)
          sum++; 
        if (wis >= 3)
          sum++; 
        break;
      case 1:
        wis -= 9;
        if (wis >= 1)
          sum++; 
        if (wis >= 3)
          sum += 2; 
        break;
      case 2:
        wis -= 12;
        if (wis >= 1)
          sum++; 
        if (wis >= 4)
          sum++; 
        break;
      case 3:
        wis -= 12;
        if (wis >= 3)
          sum++; 
        break;
    } 
    return sum;
  }
  
  public int toOriginalStatHpTic() {
    int sum = 0;
    int con = getCon();
    switch (getClassType()) {
      case 0:
        con -= 10;
        if (con >= 3)
          sum++; 
        if (con >= 5)
          sum++; 
        if (con >= 7)
          sum++; 
        if (con >= 8)
          sum++; 
        break;
      case 1:
        con -= 14;
        if (con >= 2)
          sum += 2; 
        if (con >= 4)
          sum += 2; 
        break;
      case 2:
        con -= 12;
        if (con >= 2)
          sum++; 
        if (con >= 4)
          sum++; 
        if (con >= 5)
          sum++; 
        break;
      case 3:
        con -= 12;
        if (con >= 5)
          sum++; 
        if (con >= 6)
          sum++; 
        break;
    } 
    return sum;
  }
  
  public int toOriginalStatConWeight() {
    int sum = 0;
    int con = getCon();
    switch (getClassType()) {
      case 0:
        con -= 10;
        if (con >= 1)
          sum++; 
        break;
      case 1:
        con -= 14;
        if (con >= 1)
          sum++; 
        break;
      case 2:
        con -= 12;
        if (con >= 3)
          sum++; 
        break;
      case 3:
        con -= 12;
        if (con >= 1)
          sum++; 
        if (con >= 3)
          sum++; 
        break;
    } 
    return sum;
  }
  
  public int toOriginalStatStrWeight() {
    int sum = 0;
    int str = getStr();
    switch (getClassType()) {
      case 0:
        str -= 13;
        if (str >= 1)
          sum++; 
        if (str >= 4)
          sum++; 
        if (str >= 7)
          sum++; 
        break;
      case 2:
        str -= 11;
        if (str >= 5)
          sum++; 
        break;
      case 3:
        str -= 8;
        if (str >= 1)
          sum++; 
        break;
    } 
    return sum;
  }
  
  public int toOriginalStatDamage() {
    int sum = 0;
    int str = getStr();
    switch (getClassType()) {
      case 0:
        str -= 13;
        if (str >= 2)
          sum++; 
        if (str >= 5)
          sum++; 
        break;
      case 1:
        str -= 16;
        if (str >= 2)
          sum += 2; 
        if (str >= 4)
          sum += 2; 
        break;
      case 2:
        str -= 11;
        if (str >= 1)
          sum++; 
        if (str >= 3)
          sum++; 
        break;
      case 3:
        str -= 8;
        if (str >= 2)
          sum++; 
        if (str >= 4)
          sum++; 
        break;
    } 
    return sum;
  }
  
  public int toOriginalStatHit() {
    int sum = 0;
    int str = getStr();
    switch (getClassType()) {
      case 0:
        str -= 13;
        if (str >= 3)
          sum++; 
        if (str >= 6)
          sum++; 
        break;
      case 1:
        str -= 16;
        if (str >= 1)
          sum += 2; 
        if (str >= 3)
          sum += 2; 
        break;
      case 2:
        str -= 11;
        if (str >= 2)
          sum++; 
        if (str >= 4)
          sum++; 
        break;
      case 3:
        str -= 8;
        if (str >= 3)
          sum++; 
        if (str >= 5)
          sum++; 
        break;
    } 
    return sum;
  }
  
  public int toOriginalStatBowDamage() {
    int sum = 0;
    int dex = getDex();
    switch (getClassType()) {
      case 0:
        dex -= 10;
        if (dex >= 3)
          sum++; 
        break;
      case 2:
        dex -= 12;
        if (dex >= 2)
          sum++; 
        if (dex >= 5)
          sum++; 
        break;
    } 
    return sum;
  }
  
  public int toOriginalStatER() {
    int sum = 0;
    int dex = getDex();
    switch (getClassType()) {
      case 0:
        dex -= 10;
        if (dex >= 4)
          sum++; 
        if (dex >= 6)
          sum++; 
        if (dex >= 8)
          sum++; 
        break;
      case 1:
        dex -= 12;
        if (dex >= 2)
          sum++; 
        if (dex >= 4)
          sum += 2; 
        break;
      case 3:
        dex -= 7;
        if (dex >= 2)
          sum++; 
        if (dex >= 4)
          sum++; 
        break;
    } 
    return sum;
  }
  
  public int toOriginalStatBowHit() {
    int sum = 0;
    int dex = getDex();
    switch (getClassType()) {
      case 2:
        dex -= 12;
        if (dex >= 1)
          sum += 2; 
        if (dex >= 4)
          sum += 2; 
        break;
    } 
    return sum;
  }
  
  public int getLvStr() {
    return 0;
  }
  
  public int getLvDex() {
    return 0;
  }
  
  public int getLvCon() {
    return 0;
  }
  
  public int getLvWis() {
    return 0;
  }
  
  public int getLvInt() {
    return 0;
  }
  
  public int getLvCha() {
    return 0;
  }
  
  public String getAccount() {
    if (this instanceof PcInstance) {
      PcInstance pc = (PcInstance)this;
      return pc.getClient().getID();
    } 
    return null;
  }
  
  public boolean isSkillDelay() {
    return this._isSkillDelay;
  }
  
  public void setSkillDelay(boolean _isSkillDelay) {
    this._isSkillDelay = _isSkillDelay;
  }
  
  public boolean checkSkillDelay(int skill_id) {
    Long old = this._skillDelay.get(Integer.valueOf(skill_id));
    long current = (new Date()).getTime();
    if (old == null) {
      this._skillDelay.put(Integer.valueOf(skill_id), Long.valueOf(current));
      return false;
    } 
    boolean result = (current - old.longValue() - getSkill().get(skill_id).getSkill().getReuseDelay() < 0L);
    if (!result)
      this._skillDelay.put(Integer.valueOf(skill_id), Long.valueOf(current)); 
    return result;
  }
}
