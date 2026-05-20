package net.world.object;

import java.util.HashMap;
import java.util.Map;

import net.Config;
import net.Location;
import net.database.DatabaseConnection;
import net.network.client.C_BasePacket;
import net.network.server.S_Attribute;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectAdd;
import net.network.server.S_ObjectChatting;
import net.network.server.S_ObjectHitratio;
import net.network.server.S_ObjectMoving;
import net.network.server.S_ObjectRemove;
import net.network.server.S_PinkName;
import net.network.server.S_ShowHtml;
import net.network.server.S_WorldMap;
import net.util.TimeLine;
import net.world.WorldInstance;
import net.world.WorldMap;
import net.world.function.ClanSystem;
import net.world.instance.ItemInstance;
import net.world.instance.MonsterInstance;
import net.world.instance.NpcInstance;
import net.world.instance.PcInstance;
import net.world.instance.inventory.Inventory;
import net.world.instance.skill.Magic;
import net.world.kingdom.Kingdom;
import net.world.kingdom.KingdomAbyss;
import net.world.kingdom.KingdomKent;
import net.world.kingdom.function.DoorKingdom;
import net.world.pc.L1PinkName;

public class L1Object extends TimeLine {
  protected Map<Integer, L1Object> objectList = new HashMap<Integer, L1Object>();
  
  protected Map<Integer, L1Object> worldObject = new HashMap<Integer, L1Object>();
  
  private int objectId;
  
  private String name;
  
  private String title;
  
  private int x;
  
  private int y;
  
  private int map;
  
  private int homeX;
  
  private int homeY;
  
  private int homeMap;
  
  private int homeHeading;
  
  private int tempX;
  
  private int tempY;
  
  private int tempMap;
  
  private int clanId;
  
  private String clanName;
  
  private int gfx;
  
  private int gfxMode;
  
  private int classGfx;
  
  private int classGfxMode;
  
  private int lawful;
  
  private int heading;
  
  private int light;
  
  private boolean speed;
  
  private boolean brave;
  
  private boolean slow;
  
  private int status;
  
  private boolean fight;
  
  private boolean move;
  
  private boolean gm;
  
  private boolean hpBar;
  
  private long count = 1L;
  
  private boolean dead;
  
  private boolean lock;
  
  private boolean delete;
  
  private boolean invis;
  
  private boolean poison;
  
  private int dmg;
  
  public L1Object Freeze;
  
  private boolean buffEnchantWeapon;
  
  private boolean buffBlessedArmor;
  
  private boolean buffImmuneToHarm;
  
  private int partyId;
  
  private boolean autoPickup;
  
  public int board_idx;
  
  private boolean lockFreeze;
  
  public L1Object lockFreezeObject;
  
  private boolean isPinkName;
  
  private int pinkNameTime;
  
  private int elfAttr;
  
  private boolean isStatusFireWeapon;
  
  private boolean isStatusWindShot;
  
  private boolean isExpDouble;
  
  public int color = 0;
  
  private boolean isStatusReturnDamage;
  
  private boolean isStatusStonesArmor;
  
  private boolean isStatusArmorSword;
  
  private boolean isStatusBraveMind;
  
  private boolean isStatusHoterWeapon;
  
  private boolean isStatusNatures;
  
  private boolean isStatusBurningWeapon;
  
  private boolean isStatusBlessOfFire;
  
  private boolean isStatusEyeOfStorm;
  
  private boolean isStatusStormShot;
  
  public int getcolor() {
    return this.color;
  }
  
  public void setcolor(int color) {
    this.color = color;
  }
  
  public boolean isStatusReturnDamage() {
    return this.isStatusReturnDamage;
  }
  
  public void setStatusReturnDamage(boolean isStatusReturnDamage) {
    this.isStatusReturnDamage = isStatusReturnDamage;
  }
  
  public boolean isStatusStonesArmor() {
    return this.isStatusStonesArmor;
  }
  
  public void setStatusStonesArmor(boolean isStatusStonesArmor) {
    this.isStatusStonesArmor = isStatusStonesArmor;
  }
  
  public boolean isStatusArmorSword() {
    return this.isStatusArmorSword;
  }
  
  public void setStatusArmorSword(boolean isStatusArmorSword) {
    this.isStatusArmorSword = isStatusArmorSword;
  }
  
  public boolean isStatusBraveMind() {
    return this.isStatusBraveMind;
  }
  
  public void setStatusBraveMind(boolean isStatusBraveMind) {
    this.isStatusBraveMind = isStatusBraveMind;
  }
  
  public boolean isStatusHoterWeapon() {
    return this.isStatusHoterWeapon;
  }
  
  public void setStatusHoterWeapon(boolean isStatusHoterWeapon) {
    this.isStatusHoterWeapon = isStatusHoterWeapon;
  }
  
  public boolean isStatusNatures() {
    return this.isStatusNatures;
  }
  
  public void setStatusNatures(boolean isStatusNatures) {
    this.isStatusNatures = isStatusNatures;
  }
  
  public boolean isStatusBurningWeapon() {
    return this.isStatusBurningWeapon;
  }
  
  public void setStatusBurningWeapon(boolean isStatusBurningWeapon) {
    this.isStatusBurningWeapon = isStatusBurningWeapon;
  }
  
  public boolean isStatusBlessOfFire() {
    return this.isStatusBlessOfFire;
  }
  
  public void setStatusBlessOfFire(boolean isStatusBlessOfFire) {
    this.isStatusBlessOfFire = isStatusBlessOfFire;
  }
  
  public boolean isStatusEyeOfStorm() {
    return this.isStatusEyeOfStorm;
  }
  
  public void setStatusEyeOfStorm(boolean isStatusEyeOfStorm) {
    this.isStatusEyeOfStorm = isStatusEyeOfStorm;
  }
  
  public boolean isStatusStormShot() {
    return this.isStatusStormShot;
  }
  
  public void setStatusStormShot(boolean isStatusStormShot) {
    this.isStatusStormShot = isStatusStormShot;
  }
  
  public int getObjectId() {
    return this.objectId;
  }
  
  public void setObjectId(int objectId) {
    this.objectId = objectId;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public int getX() {
    return this.x;
  }
  
  public void setX(int x) {
    this.x = x;
  }
  
  public int getY() {
    return this.y;
  }
  
  public void setY(int y) {
    this.y = y;
  }
  
  public int getMap() {
    return this.map;
  }
  
  public void setMap(int map) {
    this.map = map;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public int getClanId() {
    return this.clanId;
  }
  
  public void setClanId(int clanId) {
    this.clanId = clanId;
  }
  
  public String getClanName() {
    return this.clanName;
  }
  
  public void setClanName(String clanName) {
    this.clanName = clanName;
  }
  
  public int getGfx() {
    return this.gfx;
  }
  
  public void setGfx(int gfx) {
    this.gfx = gfx;
  }
  
  public int getGfxMode() {
    return this.gfxMode;
  }
  
  public void setGfxMode(int gfxMode) {
    this.gfxMode = gfxMode;
  }
  
  public int getClassGfx() {
    return this.classGfx;
  }
  
  public void setClassGfx(int classGfx) {
    this.classGfx = classGfx;
  }
  
  public int getClassGfxMode() {
    return this.classGfxMode;
  }
  
  public void setClassGfxMode(int classGfxMode) {
    this.classGfxMode = classGfxMode;
  }
  
  public int getLawful() {
    return this.lawful;
  }
  
  public void setLawful(int lawful) {
    if (lawful > 98303) {
      lawful = 98303;
    } else if (lawful < 32768) {
      lawful = 32768;
    } 
    this.lawful = lawful;
  }
  
  public int getHeading() {
    return this.heading;
  }
  
  public void setHeading(int heading) {
    this.heading = heading;
  }
  
  public int getLight() {
    return this.light;
  }
  
  public void setLight(int light) {
    this.light = light;
  }
  
  public boolean isSpeed() {
    return this.speed;
  }
  
  public void setSpeed(boolean speed) {
    this.speed = speed;
  }
  
  public boolean isBrave() {
    return this.brave;
  }
  
  public void setBrave(boolean brave) {
    this.brave = brave;
  }
  
  public boolean isSlow() {
    return this.slow;
  }
  
  public void setSlow(boolean slow) {
    this.slow = slow;
  }
  
  public int getStatus() {
    return this.status;
  }
  
  public void setStatus(int status) {
    this.status = status;
  }
  
  public boolean isFight() {
    return this.fight;
  }
  
  public void setFight(boolean fight) {
    this.fight = fight;
  }
  
  public boolean isMove() {
    return this.move;
  }
  
  public void setMove(boolean move) {
    this.move = move;
  }
  
  public void removeObject(L1Object o) {
    if (o.getGfx() == 168 || o.getGfx() == 1765 || o.getGfx() == 4183)
      return; 
    synchronized (this.objectList) {
      this.objectList.remove(Integer.valueOf(o.getObjectId()));
    } 
  }
  
  public void addObject(L1Object o) {
    if (o.getGfx() == 168 || o.getGfx() == 1765 || o.getGfx() == 4183)
      return; 
    synchronized (this.objectList) {
      this.objectList.put(Integer.valueOf(o.getObjectId()), o);
    } 
  }
  
  public boolean containsObject(L1Object o) {
    if (o.getGfx() == 168 || o.getGfx() == 1765 || o.getGfx() == 4183)
      return true; 
    synchronized (this.objectList) {
      return (this.objectList.get(Integer.valueOf(o.getObjectId())) != null);
    } 
  }
  
  public L1Object[] getObjectList() {
    synchronized (this.objectList) {
      return (L1Object[])this.objectList.values().toArray((Object[])new L1Object[this.objectList.size()]);
    } 
  }
  
  public boolean containsWorld(L1Object o) {
    synchronized (this.worldObject) {
      return (this.worldObject.get(Integer.valueOf(o.getObjectId())) != null);
    } 
  }
  
  public void addWorld(L1Object o) {
    synchronized (this.worldObject) {
      this.worldObject.put(Integer.valueOf(o.getObjectId()), o);
    } 
  }
  
  public void removeWorld(L1Object o) {
    synchronized (this.worldObject) {
      this.worldObject.remove(Integer.valueOf(o.getObjectId()));
    } 
  }
  
  public L1Object[] getWorldList() {
    synchronized (this.worldObject) {
      return (L1Object[])this.worldObject.values().toArray((Object[])new L1Object[this.objectList.size()]);
    } 
  }
  
  public boolean isGm() {
    return this.gm;
  }
  
  public void setGm(boolean gm) {
    this.gm = gm;
  }
  
  public boolean isHpBar() {
    return this.hpBar;
  }
  
  public void setHpBar(boolean hpBar) {
    this.hpBar = hpBar;
  }
  
  public synchronized long getCount() {
    return this.count;
  }
  
  public void setCount(long count) {
    this.count = count;
  }
  
  public int getHomeX() {
    return this.homeX;
  }
  
  public void setHomeX(int homeX) {
    this.homeX = homeX;
  }
  
  public int getHomeY() {
    return this.homeY;
  }
  
  public void setHomeY(int homeY) {
    this.homeY = homeY;
  }
  
  public int getHomeMap() {
    return this.homeMap;
  }
  
  public void setHomeMap(int homeMap) {
    this.homeMap = homeMap;
  }
  
  public int getHomeHeading() {
    return this.homeHeading;
  }
  
  public void setHomeHeading(int homeHeading) {
    this.homeHeading = homeHeading;
  }
  
  public int getTempX() {
    return this.tempX;
  }
  
  public void setTempX(int tempX) {
    this.tempX = tempX;
  }
  
  public int getTempY() {
    return this.tempY;
  }
  
  public void setTempY(int tempY) {
    this.tempY = tempY;
  }
  
  public int getTempMap() {
    return this.tempMap;
  }
  
  public void setTempMap(int tempMap) {
    this.tempMap = tempMap;
  }
  
  public boolean isDead() {
    return this.dead;
  }
  
  public void setDead(boolean dead) {
    this.dead = dead;
    if (!isDelete()) {
      if (isDead()) {
        toQuest(null, null);
        toDead();
      } 
      if (isDead() && !(this instanceof net.world.kingdom.function.CastleTop) && !(this instanceof ItemInstance)) {
        setGfxMode(8);
        SendPacket((S_BasePacket)new S_ObjectAction(this, getGfxMode()), true);
      } 
    } 
  }
  
  public boolean isLock() {
    return this.lock;
  }
  
  public void setLock(boolean lock) {
    this.lock = lock;
  }
  
  public boolean isDelete() {
    return this.delete;
  }
  
  public void setDelete(boolean delete) {
    this.delete = delete;
  }
  
  public boolean isInvis() {
    return this.invis;
  }
  
  public void setInvis(boolean invis) {
    if (!this.invis && invis)
      setStatus(getStatus() + 2); 
    if (this.invis && !invis)
      setStatus(getStatus() - 2); 
    this.invis = invis;
  }
  
  public boolean isPoison() {
    return this.poison;
  }
  
  public void setPoison(boolean poison) {
    if (!this.poison && poison)
      setStatus(getStatus() + 1); 
    if (this.poison && !poison)
      setStatus(getStatus() - 1); 
    this.poison = poison;
  }
  
  public int getDmg() {
    return this.dmg;
  }
  
  public void setDmg(int dmg) {
    this.dmg = dmg;
  }
  
  public boolean isBuffEnchantWeapon() {
    return this.buffEnchantWeapon;
  }
  
  public void setBuffEnchantWeapon(boolean buffEnchantWeapon) {
    this.buffEnchantWeapon = buffEnchantWeapon;
  }
  
  public boolean isBuffBlessedArmor() {
    return this.buffBlessedArmor;
  }
  
  public void setBuffBlessedArmor(boolean buffBlessedArmor) {
    this.buffBlessedArmor = buffBlessedArmor;
  }
  
  public boolean isBuffImmuneToHarm() {
    return this.buffImmuneToHarm;
  }
  
  public void setBuffImmuneToHarm(boolean buffImmuneToHarm) {
    this.buffImmuneToHarm = buffImmuneToHarm;
  }
  
  public int getPartyId() {
    return this.partyId;
  }
  
  public void setPartyId(int partyId) {
    this.partyId = partyId;
  }
  
  public boolean isAutoPickup() {
    return this.autoPickup;
  }
  
  public void setAutoPickup(boolean autoPickup) {
    this.autoPickup = autoPickup;
  }
  
  public int get_XY(int h, boolean type) {
    int loc = 0;
    switch (h) {
      case 0:
        if (!type)
          loc--; 
        break;
      case 1:
        if (type) {
          loc++;
          break;
        } 
        loc--;
        break;
      case 2:
        if (type)
          loc++; 
        break;
      case 3:
        loc++;
        break;
      case 4:
        if (!type)
          loc++; 
        break;
      case 5:
        if (type) {
          loc--;
          break;
        } 
        loc++;
        break;
      case 6:
        if (type)
          loc--; 
        break;
      case 7:
        loc--;
        break;
    } 
    return loc;
  }
  
  public L1Object getObject(int obj_id) {
    for (L1Object o : getObjectList()) {
      if (o.getObjectId() == obj_id)
        return o; 
    } 
    return null;
  }
  
  protected void oppositionHeading(L1Object temp) {
    int myx = getX();
    int myy = getY();
    int tx = temp.getX();
    int ty = temp.getY();
    if (tx > myx && ty > myy) {
      setHeading(7);
    } else if (tx < myx && ty < myy) {
      setHeading(3);
    } else if (tx > myx && ty == myy) {
      setHeading(6);
    } else if (tx < myx && ty == myy) {
      setHeading(2);
    } else if (tx == myx && ty < myy) {
      setHeading(4);
    } else if (tx == myx && ty > myy) {
      setHeading(0);
    } else if (tx < myx && ty > myy) {
      setHeading(1);
    } else if (tx > myx && ty < myy) {
      setHeading(5);
    } 
  }
  
  public int calcheading(int lx, int ly) {
    setHeading(calcheading(getX(), getY(), lx, ly));
    return getHeading();
  }
  
  public int calcheading(int myx, int myy, int tx, int ty) {
    if (tx > myx && ty > myy)
      return 3; 
    if (tx < myx && ty < myy)
      return 7; 
    if (tx > myx && ty == myy)
      return 2; 
    if (tx < myx && ty == myy)
      return 6; 
    if (tx == myx && ty < myy)
      return 0; 
    if (tx == myx && ty > myy)
      return 4; 
    if (tx < myx && ty > myy)
      return 5; 
    return 1;
  }
  
  public void SendSelf(S_BasePacket bp) {
    SendPacket(bp);
  }
  
  public void SendPacket(S_BasePacket bp, boolean me) {
    for (L1Object o : getObjectList()) {
      if (o instanceof PcInstance)
        o.SendPacket(bp.clone()); 
    } 
    if (me) {
      SendPacket(bp);
    } else {
      bp.clear();
    } 
  }
  
  public void SendPacket(S_BasePacket bp) {
    bp.clear();
  }
  
  public boolean getDistance(L1Object obj, int loc) {
    long dx = (obj.getX() - getX());
    long dy = (obj.getY() - getY());
    double distance = Math.sqrt((dx * dx + dy * dy));
    if (loc < (int)distance)
      return false; 
    if (getMap() != obj.getMap())
      return false; 
    return true;
  }
  
  public boolean getDistance(int tx, int ty, int tm, int loc) {
    long dx = (tx - getX());
    long dy = (ty - getY());
    double distance = Math.sqrt((dx * dx + dy * dy));
    if (loc < (int)distance)
      return false; 
    if (getMap() != tm)
      return false; 
    return true;
  }
  
  public boolean getDistance(int x, int y, int m, int tx, int ty, int tm, int loc) {
    long dx = (tx - x);
    long dy = (ty - y);
    double distance = Math.sqrt((dx * dx + dy * dy));
    if (loc < (int)distance)
      return false; 
    if (m != tm)
      return false; 
    return true;
  }
  
  public void toMove(int x, int y, int h) {
    setMove(true);
    setHeading(h);
    setX(x);
    setY(y);
    if (!getDistance(getTempX(), getTempY(), getMap(), 12)) {
      setTempX(x);
      setTempY(y);
      updateWorld();
    } 
    updateObject();
    if (this instanceof net.world.npc.elf.ElfGuard && !isZoneElfForest())
      toTeleport(33047, 32337, 4); 
  }
  
  public void toTeleport(int x, int y, int map) {
    toDelete();
    setX(x);
    setY(y);
    setMap(map);
    WorldInstance.getInstance().insert(this);
    setDelete(false);
    updateWorld();
    if (this instanceof PcInstance) {
      SendPacket((S_BasePacket)new S_WorldMap(getMap()));
      SendPacket((S_BasePacket)new S_ObjectAdd(this));
      if (Config.SHOW_OW_HPBAR)
        setCurrentHp(getCurrentHp()); 
    } 
    updateObject();
  }
  
  public String checkspeed(int char_id, int tid) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT ttime FROM characters_buffs WHERE char_id='");
    sb.append(char_id);
    sb.append("' and tid='");
    sb.append(tid);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select_string(sb.toString());
  }
  
  public void toDelete() {
    if (this.objectList.size() > 0) {
      for (L1Object o : getObjectList()) {
        o.removeObject(this);
        if (o instanceof PcInstance)
          o.SendPacket((S_BasePacket)new S_ObjectRemove(this)); 
      } 
      this.objectList.clear();
    } 
    if (this.worldObject.size() > 0) {
      for (L1Object o : getWorldList())
        o.removeWorld(this); 
      this.worldObject.clear();
    } 
    WorldInstance.getInstance().delete(this);
    setDelete(true);
  }
  
  public void Message(String msg) {
    SendPacket((S_BasePacket)new S_ObjectChatting(this, msg, 20));
  }
  
  public void updateWorld() {
    this.worldObject.clear();
    for (L1Object o : WorldInstance.getInstance().getLocation(this, 40)) {
      if (this instanceof PcInstance) {
        addWorld(o);
        if ((o instanceof MonsterInstance || (o instanceof NpcInstance && ((NpcInstance)o).getNpc().get_ai()) || o instanceof PcInstance || o instanceof ItemInstance) && !o.containsWorld(this))
          o.addWorld(this); 
        continue;
      } 
      if (this instanceof net.world.npc.Guard) {
        if (o instanceof PcInstance || o instanceof net.world.npc.Guard) {
          addWorld(o);
          if (!o.containsWorld(this))
            o.addWorld(this); 
        } 
        continue;
      } 
      if (this instanceof MonsterInstance) {
        MonsterInstance mon = (MonsterInstance)this;
        if (mon.getMon().isItempick() && o instanceof ItemInstance)
          addWorld(o); 
        if (o instanceof PcInstance || o instanceof MonsterInstance) {
          addWorld(o);
          if (!o.containsWorld(this))
            o.addWorld(this); 
        } 
        continue;
      } 
      if (this instanceof NpcInstance && ((NpcInstance)this).getNpc().get_ai()) {
        if (o instanceof PcInstance) {
          addWorld(o);
          if (!o.containsWorld(this))
            o.addWorld(this); 
        } 
        continue;
      } 
      if (this instanceof ItemInstance) {
        if (o instanceof PcInstance) {
          addWorld(o);
          if (!o.containsWorld(this))
            o.addWorld(this); 
          continue;
        } 
        if (o instanceof MonsterInstance && ((MonsterInstance)o).getMon().isItempick()) {
          addWorld(o);
          if (!o.containsWorld(this))
            o.addWorld(this); 
        } 
        continue;
      } 
      if (this instanceof net.world.kingdom.function.Crown || this instanceof net.world.kingdom.function.CrownVisual) {
        if (o instanceof PcInstance) {
          addWorld(o);
          if (!o.containsWorld(this))
            o.addWorld(this); 
        } 
        continue;
      } 
      if (this instanceof net.world.instance.SlimeraceInstance && o instanceof PcInstance) {
        addWorld(o);
        if (!o.containsWorld(this))
          o.addWorld(this); 
      } 
    } 
  }
  
  public void updateObject() {
    if (this instanceof PcInstance) {
      PcInstance pc = (PcInstance)this;
      int lvStat = pc.getLevel() - 50;
      if (lvStat > 0) {
        int totalStat = pc.getLvStr() + pc.getLvDex() + pc.getLvCon() + pc.getLvInt() + pc.getLvWis() + pc.getLvCha();
        if (totalStat < lvStat)
          pc.SendPacket((S_BasePacket)new S_ShowHtml(pc.getObjectId(), "RaiseAttr")); 
      } 
    } 
    for (L1Object o : getWorldList()) {
      if (o == null)
        break; 
      if (!getDistance(o.getX(), o.getY(), o.getMap(), 40)) {
        removeWorld(o);
        o.removeWorld(this);
      } else if (o.isDelete()) {
        removeWorld(o);
        removeObject(o);
      } else if (getDistance(o.getX(), o.getY(), o.getMap(), 14)) {
        if (containsObject(o)) {
          if (o instanceof PcInstance)
            o.SendPacket((S_BasePacket)new S_ObjectMoving(this)); 
        } else {
          addObject(o);
          o.addObject(this);
          if (o instanceof PcInstance)
            o.SendPacket((S_BasePacket)new S_ObjectAdd(this, o)); 
          if (this instanceof PcInstance) {
            SendPacket((S_BasePacket)new S_ObjectAdd(o, this));
            if (o instanceof net.world.instance.DoorInstance && o.getGfxMode() == 29)
              SendPacket((S_BasePacket)new S_Attribute(o)); 
            if (o instanceof DoorKingdom)
              ((DoorKingdom)o).Attribute(this); 
            if (this.hpBar)
              SendPacket((S_BasePacket)new S_ObjectHitratio(o, true)); 
          } 
        } 
      } else if (containsObject(o)) {
        removeObject(o);
        o.removeObject(this);
        if (this instanceof PcInstance)
          SendPacket((S_BasePacket)new S_ObjectRemove(o)); 
        if (o instanceof PcInstance)
          o.SendPacket((S_BasePacket)new S_ObjectRemove(this)); 
      } 
    } 
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("name[");
    sb.append(getName());
    sb.append("] x[");
    sb.append(getX());
    sb.append("] y[");
    sb.append(getY());
    sb.append("] map[");
    sb.append(getMap());
    sb.append("] wmp[");
    sb.append(WorldMap.getInstance().get_map(getX(), getY(), getMap()));
    sb.append("]");
    return sb.toString();
  }
  
  public void AttackBow(L1Object target, int x, int y, int action, int effectId, boolean arrow) {
    setHeading(calcheading(getX(), getY(), x, y));
    L1PinkName.execute(this, target);
  }
  
  public void Attack(L1Object target, int x, int y, int action, int effectId) {
    calcheading(x, y);
    L1PinkName.execute(this, target);
  }
  
  public void toReset() {
    setDelete(true);
  }
  
  public void ShoutChatting(String msg) {
    for (PcInstance pc : WorldInstance.getInstance().getPc()) {
      if (getDistance(pc.getX(), pc.getY(), pc.getMap(), 50))
        pc.SendPacket((S_BasePacket)new S_ObjectChatting(this, msg, true)); 
    } 
  }
  
  public void toSave(boolean memory_delete) {}
  
  public void drop(Character cha, int x, int y, long count) {}
  
  public void pickup(Character cha, int x, int y, long count) {}
  
  public void toAttack(L1Object target, int type) {}
  
  public int getCurrentHp() {
    return 0;
  }
  
  public int getCurrentMp() {
    return 0;
  }
  
  public int getTotalHp() {
    return 0;
  }
  
  public void setCurrentHp(int currentHp) {}
  
  public void setCurrentMp(int currentMp) {}
  
  public void setMaxMp(int maxMp) {}
  
  public void setMaxHp(int maxMp) {}
  
  public void Talk(PcInstance pc) {}
  
  public void Talk(PcInstance pc, String text1, String text2) {
    pc.Message(text1);
  }
  
  public void PetGet(C_BasePacket data, PcInstance pc) {}
  
  public void ShopBuy(C_BasePacket data, PcInstance pc) {}
  
  public void ShopSell(C_BasePacket data, PcInstance pc) {}
  
  public void WareHousePut(C_BasePacket data, PcInstance pc) {}
  
  public void WareHouseGet(C_BasePacket data, PcInstance pc) {}
  
  public void ClanWareHousePut(C_BasePacket data, PcInstance pc) {}
  
  public void ClanWareHouseGet(C_BasePacket data, PcInstance pc) {}
  
  public void ElfWareHousePut(C_BasePacket data, PcInstance pc) {}
  
  public void ElfWareHouseGet(C_BasePacket data, PcInstance pc) {}
  
  public void toDead(long time) {}
  
  public void toRecess(long time) {}
  
  public void toEscape(long time) {}
  
  public void toFight(long time) {}
  
  public void toItem(long time) {}
  
  public void toItemDestroy() {}
  
  public void toWalk(long time) {}
  
  public void toDead() {}
  
  public void toQuest(PcInstance pc, Magic m) {}
  
  public void toClick(L1Object o) {}
  
  public int getLevel() {
    return 0;
  }
  
  public void clearFightList() {}
  
  public void toRevival(L1Object own) {}
  
  public void toRevivalFianl() {}
  
  public void toGiveItem(L1Object o, int inv_id, long count) {}
  
  public void toGiveMeItem(Character cha, ItemInstance item, long count) {}
  
  public Inventory getInventory() {
    return null;
  }
  
  public String getOwnName() {
    return null;
  }
  
  public int getClassType() {
    return 0;
  }
  
  public void toDungeon() {}
  
  public Character getOwn() {
    return null;
  }
  
  public void MagicalAttackEncounters(Character cha) {}
  
  public void isStatus(long time) {}
  
  public boolean isRecess() {
    return false;
  }
  
  public void isShuromItemOption() {}
  
  public boolean isCloseChat() {
    return false;
  }
  
  public boolean isLockFreeze() {
    return this.lockFreeze;
  }
  
  public void setLockFreeze(boolean flag) {
    this.lockFreeze = flag;
  }
  
  public void setLockFreezeObject(L1Object obj) {
    this.lockFreezeObject = obj;
  }
  
  public L1Object getLockFreezeObject() {
    return this.lockFreezeObject;
  }
  
  public boolean isWarZone() {
    Kingdom k = ClanSystem.getInstance().isKingdomZone(this);
    if (k != null && k.isWar())
      return (isWarZoneAbyss() || isWarZoneKent()); 
    return false;
  }
  
  private boolean isWarZoneAbyss() {
    return isZone(KingdomAbyss.LOC_FLAG);
  }
  
  private boolean isWarZoneKent() {
    return isZone(KingdomKent.LOC_FLAG);
  }
  
  private boolean isZoneElfForest() {
    return isZone(Location.LOC_ELF_FOREST);
  }
  
  private boolean isZone(int[] loc) {
    return (getMap() == loc[4] && getX() >= loc[0] && getX() <= loc[1] && getY() >= loc[2] && getY() <= loc[3]);
  }
  
  public boolean isNormalZone() {
    return WorldMap.getInstance().NormalZone(getX(), getY(), getMap());
  }
  
  public boolean isPinkName() {
    return this.isPinkName;
  }
  
  public void setPinkName(boolean isPinkName) {
    this.isPinkName = isPinkName;
  }
  
  public int getPinkNameTime() {
    return this.pinkNameTime;
  }
  
  public void setPinkNameTime(int pinkNameTime) {
    this.pinkNameTime = pinkNameTime;
    if (this.pinkNameTime < 0)
      this.pinkNameTime = 0; 
  }
  
  public void startPinkName() {
    pinkNameStatus(10, true);
  }
  
  public void stopPinkName() {
    pinkNameStatus(0, false);
  }
  
  private void pinkNameStatus(int time, boolean status) {
    setPinkNameTime(time);
    if (Config.PINK_NAME)
      SendPacket((S_BasePacket)new S_PinkName(this), true); 
    setPinkName(status);
  }
  
  public int getElfAttr() {
    return this.elfAttr;
  }
  
  public void setElfAttr(int attr) {
    this.elfAttr = attr;
  }
  
  public boolean isExpDouble() {
    return this.isExpDouble;
  }
  
  public void setExpDouble(boolean isExpDouble) {
    this.isExpDouble = isExpDouble;
  }
  
  public boolean isStatusFireWeapon() {
    return this.isStatusFireWeapon;
  }
  
  public void setStatusFireWeapon(boolean isStatusFireWeapon) {
    this.isStatusFireWeapon = isStatusFireWeapon;
  }
  
  public boolean isStatusWindShot() {
    return this.isStatusWindShot;
  }
  
  public void setStatusWindShot(boolean isStatusWindShot) {
    this.isStatusWindShot = isStatusWindShot;
  }
  
  public boolean isScreenPc(int x, int y, int mapId) {
    boolean flag = false;
    for (PcInstance pc : WorldInstance.getInstance().getPc()) {
      if (pc != null && getDistance(pc.getX(), pc.getY(), pc.getMap(), x, y, mapId, 14)) {
        flag = true;
        break;
      } 
    } 
    return flag;
  }
}
