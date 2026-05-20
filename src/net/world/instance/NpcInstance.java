package net.world.instance;

import net.Config;
import net.database.bean.Npc;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAttack;
import net.util.Util;
import net.world.WorldMap;
import net.world.ai.AStar;
import net.world.ai.MonAi;
import net.world.ai.Node;
import net.world.ai.NpcAi;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;
import net.world.time.HpMpTimer;
import net.world.time.ItemTimerInstance;

public class NpcInstance extends Character {
  private Npc npc;
  
  private int recessCount;
  
  private boolean recess;
  
  private boolean escape;
  
  private boolean itemFind;
  
  private ItemInstance itemTarget;
  
  protected int Areaatk;
  
  protected int Areamagic;
  
  private int MoveRnd;
  
  private int MoveRndCount;
  
  private int MoveX;
  
  private int MoveY;
  
  protected AStar aStar;
  
  protected int[][] iPath;
  
  private Node nodePath;
  
  private int iCurrentPath;
  
  private int AIlocationX;
  
  private int AIlocationY;
  
  protected long deadTime;
  
  private boolean summon;
  
  protected long ai_start_time;
  
  protected int ai_time;
  
  public NpcInstance(Npc npc) {
    if (npc != null) {
      this.npc = npc;
      this.aStar = new AStar();
      this.iPath = new int[100][2];
    } 
  }
  
  public Npc getNpc() {
    return this.npc;
  }
  
  public void setNpc(Npc npc) {
    this.npc = npc;
  }
  
  public boolean isRecess() {
    return this.recess;
  }
  
  public void setRecess(boolean recess) {
    if (recess)
      this.recessCount = 15; 
    this.recess = recess;
  }
  
  public boolean isEscape() {
    return this.escape;
  }
  
  public void setEscape(boolean escape) {
    this.escape = escape;
  }
  
  public boolean isItemFind() {
    return this.itemFind;
  }
  
  public void setItemFind(boolean itemFind) {
    this.itemFind = itemFind;
  }
  
  public ItemInstance getItemTarget() {
    return this.itemTarget;
  }
  
  public void setItemTarget(ItemInstance itemTarget) {
    this.itemTarget = itemTarget;
  }
  
  public int getMoveRndCount() {
    return this.MoveRndCount;
  }
  
  public void setMoveRndCount(int moveRndCount) {
    this.MoveRndCount = moveRndCount;
  }
  
  public int getMoveRnd() {
    return this.MoveRnd;
  }
  
  public void setMoveRnd(int moveRnd) {
    this.MoveRnd = moveRnd;
  }
  
  public boolean isSummon() {
    return this.summon;
  }
  
  public void setSummon(boolean summon) {
    this.summon = summon;
  }
  
  protected void reSpawn() {
    setDelete(false);
    setFight(false);
    setDead(false);
    setSpeed(false);
    setPoison(false);
    setSlow(false);
    clearFightList();
    setGfx(getClassGfx());
    setGfxMode(getClassGfxMode());
    setCurrentHp(getTotalHp());
    setCurrentMp(getTotalMp());
    setHeading(getHomeHeading());
    toTeleport(getHomeX(), getHomeY(), getHomeMap());
  }
  
  public int getSpawnTime() {
    return 60;
  }
  
  public boolean isAi(long time) {
    int speed = this.ai_time;
    if (isSpeed())
      speed = (int)(speed - speed * 0.3D); 
    if (isSlow())
      speed = (int)(speed + speed * 0.3D); 
    if (time == 0L || time - this.ai_start_time >= speed)
      return true; 
    return false;
  }
  
  public void toAttack(L1Object target, int type) {
    setFight(true);
    setEscape(true);
    this.toAttackObject = target;
  }
  
  public void toEscape(long time) {
    if (getDistance(this.toAttackObject.getX(), this.toAttackObject.getY(), this.toAttackObject.getMap(), 15)) {
      oppositionHeading(this.toAttackObject);
      int x = getX();
      int y = getY();
      switch (getHeading()) {
        case 0:
          y -= 3;
          break;
        case 1:
          x += 3;
          y -= 3;
          break;
        case 2:
          x += 3;
          break;
        case 3:
          x += 3;
          y += 3;
          break;
        case 4:
          y += 3;
          break;
        case 5:
          x -= 3;
          y += 3;
          break;
        case 6:
          x -= 3;
          break;
        case 7:
          x -= 3;
          y -= 3;
          break;
      } 
      StartMove(x, y);
      this.ai_start_time = time;
      this.ai_time = getNpc().getModespeed(getGfxMode());
    } else {
      setFight(false);
      setEscape(false);
    } 
    toItemDestroy();
  }
  
  public void toRecess(long time) {
    this.ai_start_time = time;
    this.ai_time = getNpc().getModespeed(getGfxMode());
    if (--this.recessCount <= 0) {
      this.recessCount = 15;
      setRecess(false);
      setMoveRnd(100);
      setMoveRndCount(5);
      setHeading(Util.rand(0, 7));
    } 
    toItemDestroy();
  }
  
  public void toWalk(long time) {
    this.ai_start_time = time;
    if (this instanceof MonsterInstance) {
      this.ai_time = ((MonsterInstance)this).getMon().getModespeed(getGfxMode());
    } else {
      this.ai_time = getNpc().getModespeed(getGfxMode());
    } 
    setMove(true);
    RandomWalk();
    toItemDestroy();
  }
  
  protected boolean StartMove(int tx, int ty) {
    boolean canMove = true;
    if (this.AIlocationX == tx && this.AIlocationY == ty) {
      if (--this.iCurrentPath >= 0) {
        boolean ck = true;
        byte b;
        int i;
        L1Object[] arrayOfL1Object;
        for (i = (arrayOfL1Object = getObjectList()).length, b = 0; b < i; ) {
          L1Object o = arrayOfL1Object[b];
          if (o instanceof Character && !o.isDead() && this.iPath[this.iCurrentPath][0] == o.getX() && this.iPath[this.iCurrentPath][1] == o.getY()) {
            ck = false;
            break;
          } 
          b++;
        } 
        if (ck) {
          setHeading(calcheading(this.iPath[this.iCurrentPath][0], this.iPath[this.iCurrentPath][1]));
          toMove(this.iPath[this.iCurrentPath][0], this.iPath[this.iCurrentPath][1], getHeading());
        } else {
          this.AIlocationX = 0;
          this.AIlocationY = 0;
          StartMove(tx, ty);
        } 
      } else {
        this.iCurrentPath = -1;
      } 
    } else {
      this.AIlocationX = tx;
      this.AIlocationY = ty;
      this.aStar.ResetPath();
      this.nodePath = this.aStar.FindPath(this, tx, ty);
      if (this.nodePath == null) {
        this.AIlocationX = 0;
        this.AIlocationY = 0;
        canMove = false;
      } else {
        this.iCurrentPath = -1;
        while (this.nodePath != null) {
          this.iCurrentPath++;
          this.iPath[this.iCurrentPath][0] = this.nodePath.x;
          this.iPath[this.iCurrentPath][1] = this.nodePath.y;
          this.nodePath = this.nodePath.prev;
        } 
        if (--this.iCurrentPath >= 0) {
          setHeading(calcheading(this.iPath[this.iCurrentPath][0], this.iPath[this.iCurrentPath][1]));
          toMove(this.iPath[this.iCurrentPath][0], this.iPath[this.iCurrentPath][1], getHeading());
        } else {
          this.iCurrentPath = -1;
        } 
      } 
    } 
    return canMove;
  }
  
  protected void RandomWalk() {
    int rnd = Util.rand(0, 100);
    if (rnd <= getMoveRnd() && getMoveRndCount() == 5) {
      setMoveRnd(getMoveRnd() - 5);
      if (rnd > 70)
        setHeading(Util.rand(0, 7)); 
      if (!getDistance(getHomeX(), getHomeY(), getHomeMap(), 17))
        calcheading(getHomeX(), getHomeY()); 
      StartMove();
    } else {
      setMoveRndCount(getMoveRndCount() - 1);
      if (getMoveRndCount() <= 0) {
        setMoveRnd(100);
        setMoveRndCount(5);
        setHeading(Util.rand(0, 7));
      } 
    } 
  }
  
  public void Attack(L1Object target, int x, int y, int action, int effectId) {
    super.Attack(target, x, y, action, effectId);
    int dmg = 0;
    if (target != null && getDistance(x, y, target.getMap(), 2) && !target.isDead()) {
      dmg = DmgSystem(target, false, 1);
      if (dmg > 0) {
        this._dmg = dmg;
        target.toAttack((L1Object)this, 1);
        target.setCurrentHp(target.getCurrentHp() - dmg);
      } 
    } 
    SendPacket((S_BasePacket)new S_ObjectAttack(this, target, action, dmg, effectId, false, false), true);
  }
  
  public void AttackBow(L1Object target, int x, int y, int action, int effectId, boolean arrow) {
    super.AttackBow(target, x, y, action, effectId, arrow);
    int dmg = 0;
    if (target != null && !target.isDead() && arrow) {
      dmg = DmgSystem(target, true, 1);
      if (dmg > 0) {
        target.setCurrentHp(target.getCurrentHp() - dmg);
        target.toAttack((L1Object)this, 2);
      } 
    } 
    SendPacket((S_BasePacket)new S_ObjectAttack(this, target, action, dmg, effectId, true, arrow), true);
  }
  
  public void toDead(long time) {
    this.ai_start_time = time;
    this.ai_time = 1000;
    if (this.deadTime == 0L)
      this.deadTime = time; 
    if (isDelete()) {
      if (isSummon()) {
        if (this instanceof MonsterInstance) {
          MonAi.getInstance().removeMon((MonsterInstance)this);
        } else {
          NpcAi.getInstance().removeNpc(this);
        } 
        toSave(true);
      } else if (time - this.deadTime >= (1000 * getSpawnTime())) {
        reSpawn();
        this.deadTime = 0L;
      } 
    } else if (this instanceof PetInstance && time - this.deadTime >= (Config.PET_TO_DEAT_TIME * 1000)) {
      toDelete();
      setDelete(true);
      this.deadTime = 0L;
    } else if (!(this instanceof PetInstance) && time - this.deadTime >= 60000L) {
      toDelete();
      setDelete(true);
      this.deadTime = 0L;
    } 
  }
  
  public void toDead() {
    ItemTimerInstance.getInstance().remove(this);
    BuffTimerInstance.getInstance().remove((L1Object)this);
  }
  
  public void toSave(boolean memory_delete) {
    if (memory_delete) {
      ItemTimerInstance.getInstance().remove(this);
      BuffTimerInstance.getInstance().remove((L1Object)this);
      HpMpTimer.getInstance().remove(this);
      if (getInventory() != null)
        getInventory().delete(); 
      if (getSkill() != null)
        getSkill().delete(); 
      if (getBooks() != null)
        getBooks().delete(); 
      setInventory(null);
      setSkill(null);
      setBooks(null);
    } 
  }
  
  private void StartMove() {
    if (WorldMap.getInstance().IsThroughObject(getX(), getY(), getMap(), getHeading())) {
      setDirectionMove(getHeading());
    } else {
      int i = 0;
      for (int h = getHeading(); i < 8; i++) {
        h++;
        if (h > 7)
          h = 0; 
        setHeading(h);
        if (WorldMap.getInstance().IsThroughObject(getX(), getY(), getMap(), getHeading())) {
          setDirectionMove(getHeading());
          break;
        } 
      } 
    } 
  }
  
  private void setDirectionMove(int dir) {
    int nx = getX() + get_XY(dir, true);
    int ny = getY() + get_XY(dir, false);
    boolean[] gh = new boolean[8];
    byte b;
    int i;
    L1Object[] arrayOfL1Object;
    for (i = (arrayOfL1Object = getObjectList()).length, b = 0; b < i; ) {
      L1Object o = arrayOfL1Object[b];
      if (!o.isDead() && !o.isDelete() && o instanceof Character && getDistance(o.getX(), o.getY(), o.getMap(), 1))
        for (int j = 0; j < 8; j++) {
          if (!gh[j]) {
            int x = get_XY(j, true) + getX();
            int y = get_XY(j, false) + getY();
            if (o.getX() == x && o.getY() == y) {
              gh[j] = true;
              break;
            } 
          } 
        }  
      b++;
    } 
    if (gh[calcheading(nx, ny)]) {
      for (int j = 0; j < 8; dir++) {
        if (dir > 7)
          dir = 0; 
        if (!gh[dir] && WorldMap.getInstance().IsThroughObject(getX(), getY(), getMap(), dir)) {
          nx = getX() + get_XY(dir, true);
          ny = getY() + get_XY(dir, false);
          toMove(nx, ny, dir);
          break;
        } 
        j++;
      } 
    } else {
      toMove(nx, ny, getHeading());
    } 
  }
}
