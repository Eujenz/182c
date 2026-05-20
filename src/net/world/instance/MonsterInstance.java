package net.world.instance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.Config;
import net.database.MonsterItemDropTable;
import net.database.bean.Monster;
import net.database.bean.Npc;
import net.network.server.S_BasePacket;
import net.network.server.S_InventoryStatus;
import net.network.server.S_ObjectLawful;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.WorldMap;
import net.world.ai.AStar;
import net.world.ai.MonAi;
import net.world.ai.NpcExp;
import net.world.drop.NpcDrop;
import net.world.function.PartySystem;
import net.world.function.SummonSystem;
import net.world.function.bean.Party;
import net.world.instance.inventory.Inventory;
import net.world.instance.inventory.NpcInventory;
import net.world.instance.skill.MonsterSkill;
import net.world.instance.skill.Skills;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.MonDropTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonsterInstance extends NpcInstance {
  final Logger log = LoggerFactory.getLogger(MonsterInstance.class);
  
  private Monster mon;
  
  protected Map<Integer, NpcExp> exp_list;
  
  protected List<L1Object> attackList;
  
  protected Map<Integer, NpcDrop> attackNpcList;
  
  private double addExp;
  
  public int x1;
  
  public int x2;
  
  public int y1;
  
  public int y2;
  
  private int _spawnTime;
  
  private static final int[] ALL_BOSS_ID = new int[] { 
      16, 17, 22, 23, 24, 25, 26, 27, 53, 59, 
      63, 70, 88, 89, 91, 101, 105, 123, 130, 132, 
      138 };
  
  static final String GIVE_LOG = "%s 丢给怪物 %s %s 数量:%s";
  
  public MonsterInstance(Monster mon) {
    super((Npc)null);
    this.mon = mon;
    setLevel(mon.getLevel());
    setObjectId(Config.getObjectID_ETC());
    setExp(mon.getExp());
    setGfx(mon.getGfx());
    setGfxMode(0);
    setClassGfx(mon.getGfx());
    setClassGfxMode(0);
    setName(mon.getNameid());
    super.setLawful(65536 + mon.getLawful());
    setMaxHp(mon.getHp());
    setCurrentHp(getMaxHp());
    setMaxMp(mon.getMp());
    setCurrentMp(getMaxMp());
    setInventory((Inventory)new NpcInventory(this));
    setSkill((Skills)new MonsterSkill(this));
    this.Areaatk = mon.getAreaatk();
    this.aStar = new AStar();
    this.iPath = new int[100][2];
    this.exp_list = new HashMap<Integer, NpcExp>();
    this.addExp = mon.getExp() / mon.getHp();
    this.attackList = new ArrayList<L1Object>();
    this.attackNpcList = new HashMap<Integer, NpcDrop>();
    calcReSpawnTime();
    MonAi.getInstance().addMon(this);
  }
  
  public Monster getMon() {
    return this.mon;
  }
  
  public void setMon(Monster mon) {
    this.mon = mon;
  }
  
  public void toGiveMeItem(Character cha, ItemInstance item, long count) {
    if (getInventory().insert(item, count))
      item.setCount(cha, item.getCount()); 
    this.log.info(String.format("%s 丢给怪物 %s %s 数量:%s", new Object[] { cha.getName(), getMon().getName(), item.logString(), Long.valueOf(count) }));
  }
  
  public void toItem(long time) {
    if (getItemTarget() != null && !getItemTarget().isDelete()) {
      this.ai_start_time = time;
      if (getDistance(getItemTarget().getX(), getItemTarget().getY(), getItemTarget().getMap(), 0)) {
        getItemTarget().pickup(this, getX(), getY(), getItemTarget().getCount());
        setItemFind(false);
        setItemTarget((ItemInstance)null);
        this.ai_time = getMon().getModespeed(15);
        if (getInventory().getCount() > 10) {
          ItemInstance temp = getInventory().getAll()[0];
          if (temp != null) {
            getInventory().remove(getInventory().getAll()[0]);
            temp = null;
          } 
        } 
      } else {
        StartMove(getItemTarget().getX(), getItemTarget().getY());
        this.ai_time = getMon().getModespeed(getGfxMode());
      } 
    } else {
      setItemFind(false);
      setItemTarget((ItemInstance)null);
    } 
    toItemDestroy();
  }
  
  protected boolean SearchItem(int[] item_list) {
    byte b;
    int i;
    L1Object[] arrayOfL1Object;
    for (i = (arrayOfL1Object = getObjectList()).length, b = 0; b < i; ) {
      L1Object o = arrayOfL1Object[b];
      if (o instanceof ItemInstance) {
        ItemInstance item = (ItemInstance)o;
        byte b1;
        int j, arrayOfInt[];
        for (j = (arrayOfInt = item_list).length, b1 = 0; b1 < j; ) {
          int item_nameN = arrayOfInt[b1];
          if (item_nameN < 0 || item.getItem().get_nameidN() == item_nameN) {
            setItemFind(true);
            setItemTarget(item);
            return true;
          } 
          b1++;
        } 
      } 
      b++;
    } 
    return false;
  }
  
  protected L1Object SearchPlayer() {
    byte b;
    int i;
    L1Object[] arrayOfL1Object;
    for (i = (arrayOfL1Object = getObjectList()).length, b = 0; b < i; ) {
      L1Object o = arrayOfL1Object[b];
      if (o instanceof PcInstance && getDistance(o.getX(), o.getY(), o.getMap(), 1))
        return o; 
      b++;
    } 
    return null;
  }
  
  protected boolean FightStart() {
    int type = 0;
    if (getMon().isAgro())
      type++; 
    if (getMon().isPoly())
      type += 2; 
    byte b;
    int i;
    L1Object[] arrayOfL1Object;
    for (i = (arrayOfL1Object = getObjectList()).length, b = 0; b < i; ) {
      L1Object o = arrayOfL1Object[b];
      if (!o.isGm() && o instanceof PcInstance && 
        getDistance(o.getX(), o.getY(), o.getMap(), 12) && !o.isDead())
        switch (type) {
          case 0:
            if (o.getClassGfx() == o.getGfx() && !o.isInvis()) {
              addAttackList(o);
              setFight(true);
            } 
            break;
          case 1:
            if (o.getClassGfx() == o.getGfx()) {
              addAttackList(o);
              setFight(true);
            } 
            break;
          case 2:
            if (!o.isInvis()) {
              addAttackList(o);
              setFight(true);
            } 
            break;
          case 3:
            addAttackList(o);
            setFight(true);
            break;
        }  
      b++;
    } 
    return isFight();
  }
  
  public synchronized void toAttack(L1Object target, int type) {
    if (!isDead()) {
      if (target instanceof Character) {
        Character c = (Character)target;
        NpcExp ne = this.exp_list.get(Integer.valueOf(c.getObjectId()));
        if (ne == null) {
          ne = new NpcExp(c);
          this.exp_list.put(Integer.valueOf(target.getObjectId()), ne);
        } 
        if (getCurrentHp() < c._dmg) {
          ne.setExp(ne.getExp() + this.addExp * getCurrentHp());
        } else {
          ne.setExp(ne.getExp() + this.addExp * c._dmg);
        } 
        if (target instanceof PcInstance) {
          PcInstance pc = (PcInstance)target;
          if (getMon().isToughskin() && type == 1 && Util.rand(0, 100) < 10) {
            ItemInstance weapon = pc.getInventory().getSlot(11);
            if (weapon != null && weapon.getItem().isCanbedmg()) {
              weapon.setDurability(weapon.getDurability() + 1);
              target.SendPacket((S_BasePacket)new S_InventoryStatus(weapon));
              target.SendPacket((S_BasePacket)new S_ServerMessage(268, weapon.toString()));
            } 
          } 
        } 
        if (!isAttackNpcList(target))
          addAttackNpcList(target); 
        NpcDrop nd = this.attackNpcList.get(Integer.valueOf(target.getObjectId()));
        if (nd != null) {
          nd.setDmg(c._dmg);
          nd.setTotalDmg(c._dmg);
        } 
        if (getMon().isTribal()) {
          L1Object[] aobject;
          int j = (aobject = getWorldList()).length;
          for (int i = 0; i < j; i++) {
            L1Object o = aobject[i];
            if (o instanceof MonsterInstance && !(o instanceof SummonInstance)) {
              MonsterInstance mon = (MonsterInstance)o;
              if (mon.getMon().isTribal() && mon.getMon().getTribalID() == getMon().getTribalID()) {
                mon.setFight(true);
                mon.addAttackList(target);
              } 
            } 
          } 
        } 
      } 
      setFight(true);
      addAttackList(target);
    } 
  }
  
  protected void addSummonToAttackList(L1Object obj) {
    if (obj instanceof PcInstance) {
      PcInstance pc = (PcInstance)obj;
      byte b;
      int i;
      SummonInstance[] arrayOfSummonInstance;
      for (i = (arrayOfSummonInstance = SummonSystem.getInstance().getSummon(pc).getList()).length, b = 0; b < i; ) {
        SummonInstance sum = arrayOfSummonInstance[b];
        addAttackList((L1Object)sum);
        b++;
      } 
    } 
  }
  
  public void toFight(long time) {
    synchronized (this.attackList) {
      if (this.attackList.size() > 0) {
        L1Object cha = this.attackList.get(0);
        if (Util.rand(0, 100) <= 10 && this.attackList.size() > 1) {
          int idx = Util.rand(1, this.attackList.size() - 1);
          cha = this.attackList.set(idx, this.attackList.get(0));
          this.attackList.set(0, cha);
        } 
        if (cha != null) {
          if (cha.isInvis() || cha.isDelete() || cha.isDead() || cha.isRecess() || !getDistance(cha.getX(), cha.getY(), cha.getMap(), 17)) {
            this.attackList.remove(cha);
          } else {
            this.ai_start_time = time;
            if (getDistance(cha.getX(), cha.getY(), cha.getMap(), this.Areaatk) && LongAttackCK(cha, this.Areaatk)) {
              if (this.Areaatk > 2) {
                AttackBow(cha, cha.getX(), cha.getY(), getGfxMode() + 1, 66, true);
              } else {
                Attack(cha, cha.getX(), cha.getY(), getGfxMode() + 1, 0);
              } 
              this.ai_time = getMon().getModespeed(getGfxMode() + 1);
            } else {
              StartMove(cha.getX(), cha.getY());
              this.ai_time = getMon().getModespeed(getGfxMode());
            } 
          } 
        } else {
          this.attackList.remove(cha);
        } 
      } else {
        clearFightList();
      } 
      toItemDestroy();
    } 
  }
  
  public void toWalk(long time) {
    if (getMon().isAttack() && FightStart())
      return; 
    super.toWalk(time);
  }
  
  public void toDead() {
    super.toDead();
    L1Object o = null;
    Map<String, L1Object> owners = new HashMap<String, L1Object>();
    for (NpcExp ne : this.exp_list.values()) {
      Character character = ne.getCha();
      if (!ne.getCha().isDead() && getDistance(ne.getCha().getX(), ne.getCha().getY(), ne.getCha().getMap(), 12)) {
        long exp = (long)ne.getExp();
        if (Config.DEBUG)
          System.out.println("原始exp:" + exp); 
        long lawfult = (getLevel() * 3 / 2);
        if (ne.getCha().getPartyId() > 0) {
          PartySystem.getInstance().addExp(ne.getCha(), exp, lawfult);
        } else if (ne.getCha() instanceof SummonInstance) {
          exp = (long)(exp * 0.5D);
          ne.getCha().addExp(exp);
        } else {
          ne.getCha().addExp(exp);
          ne.getCha().setLawful((int)(ne.getCha().getLawful() + lawfult));
        } 
        if (character instanceof SummonInstance) {
          owners.put(character.getOwn().getName(), character.getOwn());
          continue;
        } 
        if (character instanceof PcInstance)
          owners.put(character.getName(), character); 
      } 
    } 
    drop();
    getInventory().clear();
  }
  
  public void drop() {
    if (this.attackNpcList.size() < 1)
      return; 
    StringBuilder sb = new StringBuilder();
    StringBuilder sb2 = new StringBuilder();
    String str = "";
    boolean doLog = (getLevel() > 27);
    Random random = new Random();
    int count = getInventory().getCount();
    int tempCount = count;
    int tempCount2 = 0;
    double maxDmg = 0.0D;
    int errorCount = 0;
    int errorCountMax = count;
    int x = 0;
    int y = 0;
    for (NpcDrop nd : this.attackNpcList.values())
      maxDmg += nd.getTotalDmg(); 
    int tempDmg = (int)(maxDmg / this.attackNpcList.size());
    if (tempDmg <= 0)
      tempDmg = 10; 
    int randomInt = random.nextInt(tempDmg);
    while (count > 0) {
      errorCount++;
      if (errorCount > errorCountMax) {
        sb2.append(" 出现死循环 ");
        break;
      } 
      for (NpcDrop nd : this.attackNpcList.values()) {
        L1Object o = nd.getCha();
        double totalDmg = 0.0D;
        totalDmg = nd.getTotalDmg();
        if (totalDmg > randomInt) {
          Character character = null;
          if (o instanceof SummonInstance || o instanceof PetInstance)
            character = o.getOwn(); 
          byte b;
          int i;
          ItemInstance[] arrayOfItemInstance;
          for (i = (arrayOfItemInstance = getInventory().getAll()).length, b = 0; b < i; ) {
            ItemInstance item = arrayOfItemInstance[b];
            if (item == null) {
              b++;
              continue;
            } 
            if (character != null && character.isAutoPickup() && !(this instanceof SummonInstance) && 
              getDistance(character.getX(), character.getY(), character.getMap(), Config.DROP_RANGE)) {
              Inventory inv = character.getInventory();
              ItemInstance temp = inv.isItem(item);
              if (temp == null) {
                inv.add(item);
              } else {
                temp.setCount(character, temp.getCount() + item.getCount());
              } 
              tempCount2++;
              if (character instanceof PcInstance) {
                PcInstance pc = (PcInstance)character;
                if (Config.PARTY_MESSAGE) {
                  if (pc.getPartyId() > 0) {
                    Party p = PartySystem.getInstance().get(pc.getPartyId());
                    if (p != null) {
                      StringBuilder pm = new StringBuilder();
                      pm.append("\\f7");
                      pm.append("队员 ");
                      pm.append(pc.getName());
                      pm.append(" 从 ");
                      pm.append(getMon().getName());
                      pm.append(" 获得了 ");
                      pm.append(item.toString2());
                      pm.append("。");
                      pc.dropMsg.add(pm.toString());
                      MonDropTimer.getInstance().add(pc);
                    } 
                  } else {
                    pc.SendPacket((S_BasePacket)new S_ServerMessage(143, getName(), item.toString()));
                  } 
                } else {
                  pc.SendPacket((S_BasePacket)new S_ServerMessage(143, getName(), item.toString()));
                } 
              } else {
                sb2.append(" 入包的不是角色：");
                sb2.append(character.getName());
                sb2.append(" ");
              } 
            } else {
              sb.append(getMon().getName()).append(" 掉落地面 (").append(getX()).append(",").append(getY()).append("):");
              x = Util.rand(getX() - 1, getX() + 1);
              y = Util.rand(getY() - 1, getY() + 1);
              if (WorldMap.getInstance().IsThroughObject(x, y, getMap(), 0)) {
                item.toTeleport(x, y, getMap());
              } else {
                item.toTeleport(getX(), getY(), getMap());
              } 
              tempCount2++;
            } 
            getInventory().remove(item);
            count = getInventory().getCount();
            break;
          } 
        } 
      } 
    } 
  }
  
  public void addAttackList(L1Object o) {
    synchronized (this.attackList) {
      if (!this.attackList.contains(o) && !o.equals(this))
        this.attackList.add(o); 
    } 
  }
  
  public void addAttackNpcList(L1Object o) {
    synchronized (this.attackNpcList) {
      NpcDrop nd = this.attackNpcList.get(Integer.valueOf(o.getObjectId()));
      if (nd == null) {
        nd = new NpcDrop(o);
        this.attackNpcList.put(Integer.valueOf(o.getObjectId()), nd);
      } 
    } 
  }
  
  public boolean isAttackNpcList(L1Object o) {
    boolean flag = false;
    synchronized (this.attackNpcList) {
      NpcDrop nd = this.attackNpcList.get(Integer.valueOf(o.getObjectId()));
      if (nd != null)
        flag = true; 
    } 
    return flag;
  }
  
  public boolean isAttackList(L1Object o) {
    boolean flag = false;
    synchronized (this.attackList) {
      if (this.attackList.contains(o) && !o.equals(this))
        flag = true; 
    } 
    return flag;
  }
  
  public void clearFightList() {
    setFight(false);
    this.attackList.clear();
    this.attackNpcList.clear();
    this.exp_list.clear();
  }
  
  public void setLawful(int lawful) {
    super.setLawful(lawful);
    SendPacket((S_BasePacket)new S_ObjectLawful((L1Object)this));
  }
  
  public void toSave(boolean memory_delete) {
    super.toSave(memory_delete);
    clearFightList();
  }
  
  public int getSpawnTime() {
    return this._spawnTime;
  }
  
  public void calcReSpawnTime() {
    if (this.mon.isSpawnRandom()) {
      Random rd = new Random();
      this._spawnTime = this.mon.getSpawnTime() * (rd.nextInt(60) + 70) / 100;
    } else {
      this._spawnTime = this.mon.getSpawnTime();
    } 
  }
  
  protected void reSpawn() {
    int x = 0;
    int y = 0;
    int c = 0;
    do {
      x = Util.rand(this.x1, this.x2);
      y = Util.rand(this.y1, this.y2);
      if (WorldMap.getInstance().IsThroughObject(x, y + 1, getMap(), 0) && WorldMap.getInstance().IsThroughObject(x - 1, y + 1, getMap(), 1) && 
        WorldMap.getInstance().IsThroughObject(x - 1, y, getMap(), 2) && WorldMap.getInstance().IsThroughObject(x - 1, y - 1, getMap(), 3) && 
        WorldMap.getInstance().IsThroughObject(x, y - 1, getMap(), 4) && WorldMap.getInstance().IsThroughObject(x + 1, y - 1, getMap(), 5) && 
        WorldMap.getInstance().IsThroughObject(x + 1, y, getMap(), 6) && WorldMap.getInstance().IsThroughObject(x + 1, y + 1, getMap(), 7))
        break; 
      ++c;
    } while (c < 50);
    setHomeX(x);
    setHomeY(y);
    if (Config.REBIRTH_TYPE == 1 && 
      isScreenPc(getHomeX(), getHomeY(), getHomeMap())) {
      calcReSpawnTime();
      return;
    } 
    getInventory().clear();
    MonsterItemDropTable.getInstance().MonsterItemDrop(this);
    calcReSpawnTime();
    super.reSpawn();
  }
  
  public boolean isTame(boolean meat) {
    if (isDead())
      return false; 
    double nowhp = getCurrentHp();
    double maxhp = getTotalHp();
    int p = (int)(nowhp / maxhp * 100.0D);
    if (meat) {
      if (p <= 30) {
        if (p <= 5)
          return (Util.rand(0, 100) < Util.rand(0, 80)); 
        if (p <= 10)
          return (Util.rand(0, 100) < Util.rand(0, 50)); 
        return (Util.rand(0, 100) < Util.rand(0, 30));
      } 
    } else if (p <= 20) {
      if (p <= 5)
        return (Util.rand(0, 100) < Util.rand(0, 45)); 
      if (p <= 10)
        return (Util.rand(0, 100) < Util.rand(0, 30)); 
      return (Util.rand(0, 100) < Util.rand(0, 15));
    } 
    return false;
  }
  
  public boolean isBoss() {
    int bossId = getMon().getUid();
    byte b;
    int i, arrayOfInt[];
    for (i = (arrayOfInt = ALL_BOSS_ID).length, b = 0; b < i; ) {
      int j = arrayOfInt[b];
      if (j == bossId)
        return true; 
      b++;
    } 
    return false;
  }
}
