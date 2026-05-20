package net.world.instance;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;

import net.Config;
import net.LineageClient;
import net.check.CheckSpeed;
import net.database.CharacterTable;
import net.database.DungeonTable;
import net.database.ExpTable;
import net.database.HellTable;
import net.database.SkillTable;
import net.database.bean.Exp;
import net.database.bean.Skill;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_CharacterStat;
import net.network.server.S_ObjectAdd;
import net.network.server.S_ObjectAttack;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ObjectHitratio;
import net.network.server.S_ObjectHpUpdate;
import net.network.server.S_ObjectLawful;
import net.network.server.S_ObjectMode;
import net.network.server.S_ObjectMpUpdate;
import net.network.server.S_ObjectRestore;
import net.network.server.S_ServerMessage;
import net.network.server.S_ServerMessageYesNo;
import net.network.server.S_ShowHtml;
import net.network.server.S_WorldMap;
import net.util.CalcStat;
import net.util.CharacterStatDice;
import net.util.SpeedHackChecker;
import net.util.Util;
import net.util.bean.Stat;
import net.world.WorldInstance;
import net.world.WorldMap;
import net.world.function.ClanSystem;
import net.world.function.PartySystem;
import net.world.function.SummonSystem;
import net.world.function.TradeSystem;
import net.world.function.bean.Clan;
import net.world.function.bean.L1Quest;
import net.world.function.bean.Party;
import net.world.instance.books.Books;
import net.world.instance.books.PcBooks;
import net.world.instance.buff.Buff;
import net.world.instance.inventory.Inventory;
import net.world.instance.inventory.PcInventory;
import net.world.instance.inventory.function.ScrollLabeledVERRYEDHORAE;
import net.world.instance.skill.Magic;
import net.world.instance.skill.PcSkill;
import net.world.instance.skill.Skills;
import net.world.instance.skill.function.Detection;
import net.world.kingdom.Kingdom;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;
import net.world.time.HpMpTimer;
import net.world.time.ItemTimerInstance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PcInstance extends Character {
  public static ArrayList<String> list = new ArrayList<String>();
  
  private static final Logger log = LoggerFactory.getLogger(PcInstance.class);
  
  private int lvStr;
  
  private int lvDex;
  
  private int lvCon;
  
  private int lvWis;
  
  private int lvInt;
  
  private int lvCha;
  
  private int sex;
  
  private int pkCount;
  
  private long pkTime;
  
  private boolean globalChat;
  
  private boolean tradeChat;
  
  private boolean whisperChat;
  
  private boolean totem;
  
  private boolean closeChat;
  
  private LineageClient lc;
  
  private int Areaatk;
  
  private SummonInstance sum;
  
  public long attack_time;
  
  public long moving_time;
  
  public long magic_time;
  
  public AgitInstance agit;
  
  public int temp_hp;
  
  public int temp_mp;
  
  public int pandorabox;
  
  public SpeedHackChecker attack;
  
  public SpeedHackChecker move;
  
  public SpeedHackChecker magic;
  
  private boolean speedStatusTeleport;
  
  private boolean monthCardsStat;
  
  private boolean monthCardsStatOK;
  
  private String monthCardsStatTempMessage;
  
  private L1Quest _quest;
  
  private boolean isUseClanWareHouse;
  
  private int hellTime;
  
  public List<String> dropMsg = new CopyOnWriteArrayList<String>();
  
  private final CheckSpeed _checkSpeed;
  
  private ItemInstance polyitem;
  
  public int getSex() {
    return this.sex;
  }
  
  public void setSex(int sex) {
    this.sex = sex;
  }
  
  public void setFood(int food) {
    super.setFood(food);
    if (!isDelete())
      SendPacket((S_BasePacket)new S_CharacterStat(this)); 
  }
  
  public int getPkCount() {
    return this.pkCount;
  }
  
  public void setPkCount(int pkCount) {
    this.pkCount = pkCount;
  }
  
  public long getPkTime() {
    return this.pkTime;
  }
  
  public void setPkTime(long pkTime) {
    this.pkTime = pkTime;
  }
  
  public boolean isGlobalChat() {
    return this.globalChat;
  }
  
  public void setGlobalChat(boolean globalChat) {
    this.globalChat = globalChat;
  }
  
  public boolean isTradeChat() {
    return this.tradeChat;
  }
  
  public void setTradeChat(boolean tradeChat) {
    this.tradeChat = tradeChat;
  }
  
  public boolean isWhisperChat() {
    return this.whisperChat;
  }
  
  public void setWhisperChat(boolean whisperChat) {
    this.whisperChat = whisperChat;
  }
  
  public boolean isTotem() {
    return this.totem;
  }
  
  public void setTotem(boolean totem) {
    this.totem = totem;
  }
  
  public boolean isCloseChat() {
    return this.closeChat;
  }
  
  public void setCloseChat(boolean closeChat) {
    this.closeChat = closeChat;
  }
  
  public void SendPacket(S_BasePacket bp) {
    this.lc.SendPacket(bp);
  }
  
  public LineageClient getClient() {
    return this.lc;
  }
  
  public int getLvStr() {
    return this.lvStr;
  }
  
  public void setLvStr(int lvStr) {
    this.lvStr = lvStr;
  }
  
  public int getLvDex() {
    return this.lvDex;
  }
  
  public void setLvDex(int lvDex) {
    this.lvDex = lvDex;
  }
  
  public int getLvCon() {
    return this.lvCon;
  }
  
  public void setLvCon(int lvCon) {
    this.lvCon = lvCon;
  }
  
  public int getLvWis() {
    return this.lvWis;
  }
  
  public void setLvWis(int lvWis) {
    this.lvWis = lvWis;
  }
  
  public int getLvInt() {
    return this.lvInt;
  }
  
  public void setLvInt(int lvInt) {
    this.lvInt = lvInt;
  }
  
  public int getLvCha() {
    return this.lvCha;
  }
  
  public void setLvCha(int lvCha) {
    this.lvCha = lvCha;
  }
  
  public void toSave(boolean memory_delete) {
    int gfx = getGfx();
    try {
      Buff buff = getBuff();
      if (buff != null)
        buff.save(); 
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
    } 
    try {
      Inventory inventory = getInventory();
      if (inventory != null)
        inventory.save(); 
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
    } 
    try {
      Skills skills = getSkill();
      if (skills != null)
        skills.save(); 
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
    } 
    try {
      Books books = getBooks();
      if (books != null)
        books.save(); 
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
    } 
    if (memory_delete) {
      try {
        ItemTimerInstance.getInstance().remove(this);
      } catch (Exception e) {
        log.error(e.getLocalizedMessage(), e);
      } 
      try {
        BuffTimerInstance.getInstance().remove((L1Object)this);
      } catch (Exception e) {
        log.error(e.getLocalizedMessage(), e);
      } 
    } 
    setGfx(gfx);
    CharacterTable.getInstance().CharacterSave(this);
  }
  
  public void toReset() {
    super.toReset();
    if (isDead()) {
      setFood(5);
      setDead(false);
      setCurrentHp(getLevel());
      setGfxMode((getInventory().getSlot(11) != null) ? getInventory().getSlot(11).getItem().getGfxmode() : 0);
      ScrollLabeledVERRYEDHORAE.Location((L1Object)this);
      setX(getTempX());
      setY(getTempY());
      setMap(getTempMap());
    } else if (!isGm()) {
      Kingdom k = ClanSystem.getInstance().isKingdomZone((L1Object)this);
      if (k != null && k.getClanID() != getClanId()) {
        ScrollLabeledVERRYEDHORAE.Location((L1Object)this);
        setX(getTempX());
        setY(getTempY());
        setMap(getTempMap());
      } 
    } 
    try {
      TradeSystem.getInstance().tradeCancel(this);
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
    } 
    try {
      HpMpTimer.getInstance().remove(this);
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
    } 
    try {
      ClanSystem.getInstance().worldOut(this);
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
    } 
    try {
      PartySystem.getInstance().outParty(this);
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
    } 
    try {
      SummonSystem.getInstance().remove(this);
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
    } 
    toSave(true);
  }
  
  public PcInstance(LineageClient lc, ResultSet rs) throws Exception {
    this._checkSpeed = new CheckSpeed(this);
    this.lc = lc;
    setDelete(true);
    setCount(1L);
    setStatus(4);
    setName(rs.getString("name"));
    setObjectId(rs.getInt("objID"));
    setHomeX(rs.getInt("locX"));
    setHomeY(rs.getInt("locY"));
    setHomeMap(rs.getInt("locMAP"));
    setTitle(rs.getString("title"));
    setSex(rs.getInt("sex"));
    setClassType(rs.getInt("class"));
    setGfx(rs.getInt("gfx"));
    setClassGfx((this.sex == 0) ? CharacterStatDice.getInstance().getStat(getClassType()).getMale() : CharacterStatDice.getInstance().getStat(getClassType()).getFemale());
    setGfxMode(rs.getInt("gfx_mode"));
    setLawful(rs.getInt("lawful"));
    setClanId(rs.getInt("clanID"));
    setClanName(rs.getString("clanNAME"));
    setLevel(rs.getInt("level"));
    setMaxHp(rs.getInt("maxHP"));
    setCurrentHp(rs.getInt("nowHP"));
    setMaxMp(rs.getInt("maxMP"));
    setCurrentMp(rs.getInt("nowMP"));
    setAc(rs.getInt("ac"));
    setStr(rs.getInt("str"));
    setCon(rs.getInt("con"));
    setDex(rs.getInt("dex"));
    setWis(rs.getInt("wis"));
    setInt(rs.getInt("inter"));
    setCha(rs.getInt("cha"));
    setExp(rs.getLong("exp"));
    StringTokenizer token = new StringTokenizer(rs.getString("lvStat"));
    setLvStr(Integer.valueOf(token.nextToken()).intValue());
    setLvDex(Integer.valueOf(token.nextToken()).intValue());
    setLvCon(Integer.valueOf(token.nextToken()).intValue());
    setLvWis(Integer.valueOf(token.nextToken()).intValue());
    setLvInt(Integer.valueOf(token.nextToken()).intValue());
    setLvCha(Integer.valueOf(token.nextToken()).intValue());
    this.temp_hp = rs.getInt("nowHP");
    this.temp_mp = rs.getInt("nowMP");
    setFood(rs.getInt("food"));
    setPkCount(rs.getInt("pkcount"));
    try {
      setPkTime(rs.getTimestamp("pkTime").getTime());
    } catch (Exception localException) {}
    setGlobalChat((rs.getInt("global_chating") == 1));
    setTradeChat((rs.getInt("trade_chating") == 1));
    setWhisperChat((rs.getInt("whisper_chating") == 1));
    setTotem((rs.getInt("totem") == 1));
    setElfAttr(rs.getInt("elf_attr"));
    this.TimeHpTic = getHpTime();
    this.TimeMpTic = getMpTime();
    lc.setPc(this);
    setInventory((Inventory)new PcInventory(this));
    setSkill((Skills)new PcSkill(this));
    setBooks((Books)new PcBooks(this));
    setBuff(new Buff(this));
    HpMpTimer.getInstance().add(this);
    WorldInstance.getInstance().addPc(this);
    if (getClient().getLevel() > 0)
      setGm(true); 
    this.attack = new SpeedHackChecker(this, 5, 0, Config.ATTACK_COUNT);
    this.move = new SpeedHackChecker(this, 5, 1, Config.MOVE_COUNT);
    this.magic = new SpeedHackChecker(this, 4, 2, Config.MAGIC_COUNT);
    this._quest = new L1Quest(this);
  }
  
  public CheckSpeed getCheckSped() {
    return this._checkSpeed;
  }
  
  public void toMove(int x, int y, int h) {
    if (Config.CHECK_SPEED_TYPE == 2) {
      getCheckSped().checkInterval(CheckSpeed.ACT_TYPE.MOVE);
    } else {
      this.move.check();
    } 
    if (getDistance(x, y, getMap(), 1)) {
      switch (h) {
        case 0:
          y--;
          break;
        case 1:
          x++;
          y--;
          break;
        case 2:
          x++;
          break;
        case 3:
          x++;
          y++;
          break;
        case 4:
          y++;
          break;
        case 5:
          x--;
          y++;
          break;
        case 6:
          x--;
          break;
        case 7:
          x--;
          y--;
          break;
        default:
          setHeading(0);
          y--;
          break;
      } 
      super.toMove(x, y, h);
      if (WorldMap.getInstance().get_map(getX(), getY(), getMap()) == 100)
        DungeonTable.getInstance().gotoDungeon(this); 
      if (isLockFreeze()) {
        int X = getTempX();
        int Y = getTempY();
        int value = 5;
        if (X > getX() + value || Y > getY() + value)
          getClient().close(); 
      } 
    } 
  }
  
  public void toTeleport(int x, int y, int map) {
    SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)this, 169), true);
    super.toTeleport(x, y, map);
    SummonSystem.getInstance().toTeleport(this);
    if (getGfx() != getClassGfx())
      SendPacket((S_BasePacket)new S_ObjectMode((L1Object)this), true); 
  }
  
  public void toTeleport(int x, int y, int map, boolean gm) {
    super.toTeleport(x, y, map);
  }
  
  public void toDungeon() {
    super.toTeleport(getTempX(), getTempY(), getTempMap());
    SummonSystem.getInstance().toTeleport(this);
    if (getGfx() != getClassGfx())
      SendPacket((S_BasePacket)new S_ObjectMode((L1Object)this), true); 
  }
  
  public synchronized void setCurrentHp(int currentHp) {
    super.setCurrentHp(currentHp);
    if (!isDelete()) {
      SendPacket((S_BasePacket)new S_ObjectHpUpdate(this));
      PartySystem.getInstance().updateHp(this);
    } 
  }
  
  public synchronized void setCurrentMp(int currentMp) {
    super.setCurrentMp(currentMp);
    if (!isDelete())
      SendPacket((S_BasePacket)new S_ObjectMpUpdate(this)); 
  }
  
  public void addExp(long exp) {
    if (exp != 0L && (Config.LEVEL_MAX == 0 || getLevel() < Config.LEVEL_MAX)) {
      super.addExp(exp);
      expChange();
    } 
  }
  
  public void expChange() {
    Exp e = ExpTable.getInstance().getTemplate(getLevel());
    if (e != null) {
      boolean lvUp = (e.get_bonus() <= getExp());
      if (lvUp) {
        int hp = StatusUP(true);
        int mp = StatusUP(false);
        int i;
        for (i = 1; i <= 100; i++) {
          e = ExpTable.getInstance().getTemplate(i);
          if (getExp() < e.get_bonus())
            break; 
        } 
        for (i = e.get_level() - getLevel(); i > 1; i--) {
          hp += StatusUP(true);
          mp += StatusUP(false);
        } 
        hp = getMaxHp() + hp;
        mp = getMaxMp() + mp;
        setMaxHp(hp);
        setMaxMp(mp);
        setCurrentHp(getTotalHp());
        setCurrentMp(getTotalMp());
        setLevel(e.get_level());
        if (getLevel() > 50)
          LvStat(true); 
        SendPacket((S_BasePacket)new S_CharacterStat(this));
        if (getLevel() > 30) {
          SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)this, 231), true);
        } else {
          SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)this, 231));
        } 
        switch (getMap()) {
          case 58:
          case 69:
            if (getLevel() < 13)
              break; 
            if (getQuest().get_step(300) != 255)
              getQuest().set_step(300, 255); 
            toTeleport(33084, 33391, 4);
            break;
        } 
      } else {
        SendPacket((S_BasePacket)new S_CharacterStat(this));
      } 
    } 
  }
  
  public void Attack(L1Object target, int x, int y, int action, int effectId) {
    if (Config.CHECK_SPEED_TYPE == 2) {
      getCheckSped().checkInterval(CheckSpeed.ACT_TYPE.ATTACK);
    } else {
      this.attack.check();
    } 
    if (!isDead()) {
      Inventory inv = getInventory();
      if (inv == null)
        return; 
      if (inv.getWeight() < 24) {
        super.Attack(target, x, y, action, effectId);
        int dmg = 0;
        this.Areaatk = 2;
        try {
          if (isInvis())
            Detection.invis((L1Object)this); 
          if (target != null && getDistance(x, y, target.getMap(), this.Areaatk) && !target.isDead() && !target.isDelete() && LongAttackCK(target, this.Areaatk))
            if (!(target instanceof SummonInstance) && !(target instanceof net.world.npc.elf.ElfGuard) && target.isRecess()) {
              dmg = 0;
            } else {
              dmg = DmgSystem(target, false, 1);
            }  
          if (dmg > 0) {
            setFight(true);
            if (inv.getSlot(11) != null)
              dmg += inv.getSlot(11).toAttackEffect((L1Object)this, target); 
            this._dmg = dmg;
            target.toAttack((L1Object)this, 1);
            target.setCurrentHp(target.getCurrentHp() - dmg);
            SummonSystem.getInstance().toAttack(this, target);
            if (ClanSystem.getInstance().getKingdom(this) != null)
              ClanSystem.getInstance().getKingdom(this).toAttack(target, 0); 
          } 
          if (isHpBar())
            SendPacket((S_BasePacket)new S_ObjectHitratio(target, true)); 
        } catch (Exception e) {
          log.error(e.getLocalizedMessage(), e);
        } 
        SendPacket((S_BasePacket)new S_ObjectAttack(this, target, action, dmg, effectId, false, false), true);
      } else {
        SendPacket((S_BasePacket)new S_ServerMessage(110));
      } 
    } 
  }
  
  public void AttackBow(L1Object target, int x, int y, int action, int effectId, boolean arrow) {
    if (Config.CHECK_SPEED_TYPE == 2) {
      getCheckSped().checkInterval(CheckSpeed.ACT_TYPE.ATTACK);
    } else {
      this.attack.check();
    } 
    if (!isDead()) {
      Inventory inv = getInventory();
      if (inv == null)
        return; 
      if (inv.getWeight() < 24) {
        super.AttackBow(target, x, y, action, effectId, arrow);
        int dmg = 0;
        this.Areaatk = 12;
        try {
          if (isInvis())
            Detection.invis((L1Object)this); 
          if (getGfx() == 32) {
            this.Areaatk = 12;
            effectId = 10;
            arrow = true;
          } else {
            ItemInstance item = inv.getSlot(11);
            if (item != null)
              effectId = item.getItem().get_EffectID(); 
            arrow = inv.Arrow(true);
          } 
          if (target != null && !target.isDead() && !target.isDelete() && arrow && LongAttackCK(target, this.Areaatk))
            if (!(target instanceof SummonInstance) && target.isRecess()) {
              dmg = 0;
            } else {
              dmg = DmgSystem(target, true, 1);
            }  
          if (dmg > 0) {
            setFight(true);
            this._dmg = dmg;
            target.toAttack((L1Object)this, 2);
            target.setCurrentHp(target.getCurrentHp() - dmg);
            SummonSystem.getInstance().toAttack(this, target);
            if (ClanSystem.getInstance().getKingdom(this) != null)
              ClanSystem.getInstance().getKingdom(this).toAttack(target, 0); 
          } 
        } catch (Exception e) {
          log.error(e.getLocalizedMessage(), e);
        } 
        SendPacket((S_BasePacket)new S_ObjectAttack(this, target, action, dmg, effectId, true, arrow), true);
      } else {
        SendPacket((S_BasePacket)new S_ServerMessage(110));
      } 
    } 
  }
  
  public synchronized void toAttack(L1Object target, int type) {
    setFight(true);
    this.toAttackObject = target;
    if (isInvis())
      Detection.invis((L1Object)this); 
    if (target instanceof PcInstance && WorldMap.getInstance().NormalZone(target.getX(), target.getY(), target.getMap()) && getLawful() >= 65536) {
      PcInstance cha = (PcInstance)target;
      Clan c = ClanSystem.getInstance().getClan(getClanId());
      if (c == null || c.get_warClan() == null || c.get_warClan().get_id() != cha.getClanId()) {
        Kingdom k = ClanSystem.getInstance().isKingdomZone((L1Object)this);
        if (k == null || !k.isWar()) {
          for (L1Object o : getObjectList()) {
            if (o instanceof net.world.npc.Guard)
              o.toAttack((L1Object)cha, type); 
          } 
        } else if (k != null && k.getClanID() == getClanId()) {
          k.toAttack(target, type);
        } 
      } 
    } 
  }
  
  public void toDead() {
    boolean exp_drop = false;
    boolean item_drop = false;
    if (this.toAttackObject != null) {
      if (this.toAttackObject instanceof PcInstance && WorldMap.getInstance().NormalZone(this.toAttackObject.getX(), this.toAttackObject.getY(), this.toAttackObject.getMap()) && getLawful() >= 65536) {
        Kingdom k = ClanSystem.getInstance().isKingdomZone((L1Object)this);
        if (k == null || !k.isWar()) {
          PcInstance cha = (PcInstance)this.toAttackObject;
          if (!WorldMap.getInstance().CombatZone(getX(), getY(), getMap()) && getLawful() >= 65536 && !isPinkName()) {
            cha.setPkCount(cha.getPkCount() + 1);
            if (cha.getPkCount() >= 100) {
              if (cha.getMap() != 666)
                cha.toTeleport(32670, 32800, 666); 
              HellTable.getInstance().add(cha);
            } 
            if (cha.getLawful() != 98303)
              cha.setPkTime(System.currentTimeMillis()); 
            if (cha.getLawful() >= 65536) {
              if (cha.getLevel() < 15) {
                cha.setLawful(65536 - Util.rand(1500, 2000));
              } else if (cha.getLevel() < 30) {
                cha.setLawful(65536 - Util.rand(3500, 5000));
              } else if (cha.getLevel() < 49) {
                cha.setLawful(65536 - Util.rand(7000, 9000));
              } else if (cha.getLevel() < 65) {
                cha.setLawful(65536 - Util.rand(16000, 22000));
              } else {
                cha.setLawful(32768);
              } 
            } else {
              cha.setLawful(cha.getLawful() - Util.rand(1500, 2000));
            } 
          } 
        } 
      } else if (this.toAttackObject instanceof SummonInstance && WorldMap.getInstance().NormalZone(this.toAttackObject.getX(), this.toAttackObject.getY(), this.toAttackObject.getMap()) && getLawful() >= 65536) {
        Kingdom k = ClanSystem.getInstance().isKingdomZone((L1Object)this);
        if (k == null || !k.isWar()) {
          PcInstance cha = (PcInstance)this.toAttackObject.getOwn();
          if (!WorldMap.getInstance().CombatZone(getX(), getY(), getMap()) && getLawful() >= 65536 && !isPinkName()) {
            cha.setPkCount(cha.getPkCount() + 1);
            if (cha.getPkCount() >= 100) {
              if (cha.getMap() != 666)
                cha.toTeleport(32670, 32800, 666); 
              HellTable.getInstance().add(cha);
            } 
            if (cha.getLawful() != 98303)
              cha.setPkTime(System.currentTimeMillis()); 
            if (cha.getLawful() >= 65536) {
              if (cha.getLevel() < 15) {
                cha.setLawful(65536 - Util.rand(1500, 2000));
              } else if (cha.getLevel() < 30) {
                cha.setLawful(65536 - Util.rand(3500, 5000));
              } else if (cha.getLevel() < 49) {
                cha.setLawful(65536 - Util.rand(7000, 9000));
              } else if (cha.getLevel() < 65) {
                cha.setLawful(65536 - Util.rand(16000, 22000));
              } else {
                cha.setLawful(32768);
              } 
            } else {
              cha.setLawful(cha.getLawful() - Util.rand(1500, 2000));
            } 
          } 
        } 
      } 
      if (this.toAttackObject instanceof net.world.npc.Guard)
        setPkTime(0L); 
      if (getLevel() > 9) {
        if (!exp_drop && WorldMap.getInstance().NormalZone(getX(), getY(), getMap()))
          exp_drop = true; 
        if (!exp_drop && (this.toAttackObject instanceof NpcInstance || this.toAttackObject instanceof MonsterInstance))
          exp_drop = true; 
        if (exp_drop && this.toAttackObject instanceof PcInstance) {
          int count = getPkCount() - 1;
          if (count < 0)
            count = 0; 
          setPkCount(count);
        } 
        if (exp_drop && (this.toAttackObject instanceof NpcInstance || this.toAttackObject instanceof MonsterInstance)) {
          List<ItemInstance> list = getInventory().getItemDbId(990001);
          if (list != null) {
            if (((ItemInstance)list.get(0)).getCount() == 1L) {
              getInventory().remove(list.get(0));
            } else {
              ((ItemInstance)list.get(0)).setCount(((ItemInstance)list.get(0)).getCount() - 1L);
            } 
            exp_drop = false;
          } 
        } 
      } 
      if (!item_drop && (this.toAttackObject instanceof NpcInstance || this.toAttackObject instanceof MonsterInstance))
        item_drop = true; 
      if (!item_drop && !WorldMap.getInstance().CombatZone(getX(), getY(), getMap()))
        item_drop = true; 
    } 
    if (exp_drop)
      DropExp(); 
    if (item_drop)
      DropItem(); 
    ClanSystem.getInstance().WarRoyalDelete(this);
    BuffTimerInstance.getInstance().remove((L1Object)this);
    ItemTimerInstance.getInstance().remove(this, "$234");
    ItemTimerInstance.getInstance().remove(this, "$943");
    ItemTimerInstance.getInstance().remove(this, "$944");
    ItemTimerInstance.getInstance().remove(this, "$1507");
    ItemTimerInstance.getInstance().remove(this, "$239");
    ItemTimerInstance.getInstance().remove(this, "$232");
    ItemTimerInstance.getInstance().remove(this, "$110");
    ItemTimerInstance.getInstance().remove(this, "$260");
    ItemTimerInstance.getInstance().remove(this, "$971");
  }
  
  private void DropExp() {
    Exp e1 = ExpTable.getInstance().getTemplate(getLevel());
    double exp = 0.0D;
    if (getLevel() < 45) {
      exp = e1.get_exp() * Util.rand(10, 12) * 0.01D;
    } else if (getLevel() < 49) {
      exp = e1.get_exp() * Util.rand(6, 9) * 0.01D;
    } else {
      exp = e1.get_exp() * Util.rand(3, 5) * 0.01D;
    } 
    if ((e1.get_bonus() - e1.get_exp()) > getExp() - exp) {
      setLevel(getLevel() - 1);
      int hp = getMaxHp() - StatusUP(true);
      int mp = getMaxMp() - StatusUP(false);
      Stat s = CharacterStatDice.getInstance().getStat(getClassType());
      if (hp < s.getHp())
        hp = s.getHp(); 
      if (mp < s.getMp())
        mp = s.getMp(); 
      setMaxHp(hp);
      setMaxMp(mp);
    } 
    super.addExp((long)-exp);
    SendPacket((S_BasePacket)new S_CharacterStat(this));
  }
  
  private void DropItem() {
    int dropCount = 0;
    int dropChance = 0;
    if (getLawful() < 65536) {
      dropCount = 3;
      dropChance = 50;
    } else if (getLawful() < 98303) {
      dropCount = 1;
      dropChance = (int)(10.0D * (1.0D - Double.valueOf((getLawful() - 65536)).doubleValue() / 32767.0D));
    } 
    for (ItemInstance item : getInventory().getAll()) {
      if (item.getItem().isCanDrop() && item.getItem().getItemId() != 5 && dropChance > Util.rand(1, 100)) {
        if (item.isEquipped())
          item.clickItem(this, (C_BasePacket)null); 
        item.drop(this, getX(), getY(), item.getCount());
        dropCount--;
        if (dropCount <= 0)
          break; 
      } 
    } 
  }
  
  public boolean LvStat(boolean packet) {
    int lvStat = getLevel() - 50;
    if (lvStat > 0) {
      int totalStat = getLvStr() + getLvDex() + getLvCon() + getLvInt() + getLvWis() + getLvCha();
      if (totalStat < lvStat) {
        if (packet)
          SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "RaiseAttr")); 
        return true;
      } 
    } 
    return false;
  }
  
  public void toGiveItem(L1Object o, int inv_id, long count) {
    if (o != null) {
      ItemInstance item = getInventory().getItemInvId(inv_id);
      if (item == null)
        return; 
      if (count <= 0L)
        return; 
      if (item.isEquipped()) {
        SendPacket((S_BasePacket)new S_ServerMessage(141));
        return;
      } 
      if (count <= 2147483647L && item.getCount() >= count && o.getInventory() != null)
        o.toGiveMeItem(this, item, count); 
    } 
  }
  
  public boolean isOverlapping() {
    for (L1Object o : getWorldList()) {
      if (o instanceof PcInstance) {
        PcInstance tgpc = (PcInstance)o;
        if (getMap() == tgpc.getMap() && getX() == tgpc.getX() && getY() == tgpc.getY())
          return true; 
      } 
    } 
    return false;
  }
  
  public void toRevival(L1Object own) {
    if (isDead()) {
      if (isOverlapping())
        return; 
      this.toRevival = own;
      SendPacket((S_BasePacket)new S_ServerMessageYesNo(321));
    } 
  }
  
  public void toRevivalFianl() {
    if (isDead()) {
      if (isOverlapping()) {
        SendPacket((S_BasePacket)new S_ServerMessage(592));
        return;
      } 
      if (getInventory().getSlot(11) != null) {
        setGfxMode(getInventory().getSlot(11).getItem().getGfxmode());
      } else {
        setGfxMode(0);
      } 
      setDead(false);
      SendPacket((S_BasePacket)new S_ObjectRestore(this.toRevival, (L1Object)this), true);
      SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)this, 230), true);
      setCurrentHp(getLevel() + 10);
    } 
  }
  
  public void toRestart() {
    if (isDead()) {
      setGfxMode((getInventory().getSlot(11) != null) ? getInventory().getSlot(11).getItem().getGfxmode() : 0);
      setDead(false);
      SendPacket((S_BasePacket)new S_ObjectRestore(this.toRevival, (L1Object)this), true);
      setCurrentHp(getLevel());
      setFood(5);
      toDelete();
      ScrollLabeledVERRYEDHORAE.Location((L1Object)this);
      setX(getTempX());
      setY(getTempY());
      setMap(getTempMap());
      WorldInstance.getInstance().insert((L1Object)this);
      setDelete(false);
      updateWorld();
      SendPacket((S_BasePacket)new S_WorldMap(getMap()));
      SendPacket((S_BasePacket)new S_ObjectAdd((L1Object)this));
      if (Config.SHOW_OW_HPBAR)
        setCurrentHp(getCurrentHp()); 
      updateObject();
    } 
  }
  
  public void setLawful(int lawful) {
    super.setLawful(lawful);
    if (!isDelete())
      SendPacket((S_BasePacket)new S_ObjectLawful((L1Object)this), true); 
  }
  
  public void updateObject() {
    super.updateObject();
  }
  
  public void setSummon(SummonInstance sum) {
    this.sum = sum;
  }
  
  public SummonInstance getSummon() {
    return this.sum;
  }
  
  public boolean isSpeedStatusTeleport() {
    return this.speedStatusTeleport;
  }
  
  public void setSpeedStatusTeleport(boolean flag) {
    this.speedStatusTeleport = flag;
  }
  
  public boolean isMonthCardsStat() {
    return this.monthCardsStat;
  }
  
  public void setMonthCardsStat(boolean monthCardsStat) {
    this.monthCardsStat = monthCardsStat;
  }
  
  public boolean isMonthCardsStatOK() {
    return this.monthCardsStatOK;
  }
  
  public void setMonthCardsStatOK(boolean monthCardsStatOK) {
    this.monthCardsStatOK = monthCardsStatOK;
  }
  
  public String getMonthCardsStatTempMessage() {
    return this.monthCardsStatTempMessage;
  }
  
  public void setMonthCardsStatTempMessage(String monthCardsStatTempMessage) {
    this.monthCardsStatTempMessage = monthCardsStatTempMessage;
  }
  
  public L1Quest getQuest() {
    return this._quest;
  }
  
  public int getTotalAc() {
    int ac = getAc() + getAcDex() + getDynamicAc() + CalcStat.calcAc(this) + getLvDex();
    if (ac > 138)
      return 138; 
    return ac;
  }
  
  public int getMr() {
    return super.getMr() + getLevel() / 2 + CalcStat.calcMr(getWis());
  }
  
  public void setMaxHp(int maxHp) {
    switch (getClassType()) {
      case 0:
        if (maxHp >= Config.RoyalMaxHP)
          maxHp = Config.RoyalMaxHP; 
        break;
      case 1:
        if (maxHp >= Config.KnightMaxHP)
          maxHp = Config.KnightMaxHP; 
        break;
      case 2:
        if (maxHp >= Config.ElfMaxHP)
          maxHp = Config.ElfMaxHP; 
        break;
      case 3:
        if (maxHp >= Config.WizardMaxHP)
          maxHp = Config.WizardMaxHP; 
        break;
    } 
    super.setMaxHp(maxHp);
  }
  
  public void setMaxMp(int maxHp) {
    switch (getClassType()) {
      case 0:
        if (maxHp >= Config.RoyalMaxMP)
          maxHp = Config.RoyalMaxMP; 
        break;
      case 1:
        if (maxHp >= Config.KnightMaxMP)
          maxHp = Config.KnightMaxMP; 
        break;
      case 2:
        if (maxHp >= Config.ElfMaxMP)
          maxHp = Config.ElfMaxMP; 
        break;
      case 3:
        if (maxHp >= Config.WizardMaxMP)
          maxHp = Config.WizardMaxMP; 
        break;
    } 
    super.setMaxMp(maxHp);
  }
  
  public boolean isSkillEffect(int id) {
    return BuffTimerInstance.getInstance().contains((L1Object)this, id);
  }
  
  public void addSkillEffect(int id) {
    Magic magic = SkillTable.getInstance().getTemplate(this, id);
    BuffTimerInstance.getInstance().remove((L1Object)this, magic);
    BuffTimerInstance.getInstance().add((L1Object)this, magic);
  }
  
  public void delSkillEffect(int id) {
    BuffTimerInstance.getInstance().remove((L1Object)this, id);
  }
  
  public int getSkillBuffTime(int id) {
    return BuffTimerInstance.getInstance().getBuffTime((L1Object)this, id);
  }
  
  public String getSkillIdName(int id) {
    Skill skill = SkillTable.getInstance().getTemplate(id);
    if (skill != null)
      return skill.getName(); 
    return null;
  }
  
  public boolean isParty() {
    return (getPartyId() != 0);
  }
  
  public List<PcInstance> getPartyMembers() {
    if (isParty()) {
      Party party = PartySystem.getInstance().get(getPartyId());
      if (party != null) {
        List<PcInstance> partyList = new ArrayList<PcInstance>();
        for (PcInstance use : party.getList()) {
          if (use != this)
            partyList.add(use); 
        } 
        return partyList;
      } 
    } 
    return null;
  }
  
  public boolean isUseClanWareHouse() {
    return this.isUseClanWareHouse;
  }
  
  public void setUseClanWareHouse(boolean isUseClanWareHouse) {
    this.isUseClanWareHouse = isUseClanWareHouse;
  }
  
  public int getHellTime() {
    return this.hellTime;
  }
  
  public void setHellTime(int hellTime) {
    this.hellTime = hellTime;
  }
  
  public ItemInstance getpolyitem() {
    return this.polyitem;
  }
  
  public void setpolyitem(ItemInstance polyitem) {
    this.polyitem = polyitem;
  }
}
