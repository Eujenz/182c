package net.world.instance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import net.Config;
import net.database.DatabaseConnection;
import net.database.ExpTable;
import net.database.bean.Exp;
import net.database.bean.Monster;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_InventoryStatus;
import net.network.server.S_ObjectPet;
import net.network.server.S_ObjectRestore;
import net.network.server.S_ServerMessage;
import net.world.function.SummonSystem;
import net.world.instance.inventory.function.DogCollar;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.HpMpTimer;

public class PetInstance extends SummonInstance {
  private static final String FoodLevel0 = "$612";
  
  private static final String FoodLevel1 = "$611";
  
  private static final String FoodLevel2 = "$610";
  
  private static final String FoodLevel3 = "$609";
  
  private static final String FoodLevel4 = "$608";
  
  private String FoodStatus;
  
  private boolean isReceive;
  
  static final String GIVE_LOG = "%s 丢给宠物 %s %s 数量:%s";
  
  public PetInstance(Monster mon, Character cha) {
    super(mon, cha, 0, 0);
    this.FoodStatus = "$610";
    setDynamicTicHp(10);
    setDynamicTicMp(1);
    HpMpTimer.getInstance().add(this);
    setLevel(5);
    setExp(631L);
  }
  
  public void toGiveMeItem(Character cha, ItemInstance item, long count) {
    if (count == 1L)
      switch (item.getItem().get_nameidN()) {
        case 23:
        case 623:
          setFood(29);
          item.setCount(cha, item.getCount() - count);
          this.FoodStatus = "$612";
          break;
        case 234:
        case 235:
        case 237:
        case 238:
        case 264:
        case 794:
        case 1251:
        case 1252:
        case 1253:
          item.clickItem(this, (C_BasePacket)null);
          item.setCount(cha, item.getCount() - count);
          break;
        default:
          super.toGiveMeItem(cha, item, count);
          break;
      }  
    this.log.info(String.format("%s 丢给宠物 %s %s 数量:%s", new Object[] { cha.getName(), getMon().getName(), item.logString(), Long.valueOf(count) }));
  }
  
  public void addExp(long exp) {
    if (exp > 0L && (Config.LEVEL_MAX == 0 || getLevel() < Config.LEVEL_MAX)) {
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
        if (hp >= 500)
          hp = 500; 
        if (mp >= 200)
          mp = 200; 
        hp = getMaxHp() + hp;
        mp = getMaxMp() + mp;
        setMaxHp(hp);
        setMaxMp(mp);
        setCurrentHp(getTotalHp());
        setCurrentMp(getTotalMp());
        setLevel(e.get_level());
        updateCollarStatus();
        this.owner.SendPacket((S_BasePacket)new S_ServerMessage(320, getName()));
      } 
    } 
  }
  
  public void toDead(long time) {
    super.toDead(time);
    if (isDelete()) {
      deleteDB();
      if (this.owner != null && this.owner.getInventory() != null) {
        List<ItemInstance> list = this.owner.getInventory().getItemNameId(1173);
        for (ItemInstance item : list) {
          DogCollar dc = (DogCollar)item;
          if (dc.getPetObjectId() == getObjectId()) {
            this.owner.getInventory().remove((ItemInstance)dc);
            break;
          } 
        } 
      } 
    } 
  }
  
  public void toDead() {
    clearFightList();
    super.toDead();
    if (!isReceive())
      dropExp(); 
  }
  
  private void dropExp() {
    if (getLevel() <= 5)
      return; 
    int nowLevelByExp = getNowLevelByExp();
    Exp nowExp = ExpTable.getInstance().getTemplate(nowLevelByExp);
    Exp beforeExp = ExpTable.getInstance().getTemplate(nowLevelByExp - 1);
    long exp = 0L;
    if (getLevel() <= 47) {
      exp = (long)((nowExp.get_exp() * 8L) * 0.01D);
    } else {
      exp = (long)((nowExp.get_exp() * 4L) * 0.01D);
    } 
    super.addExp(-exp);
    if (beforeExp.get_bonus() >= getExp()) {
      dropHpMp();
      setLevel(getNowLevelByExp());
      updateCollarStatus();
    } 
  }
  
  private void dropHpMp() {
    int hp = StatusUP(true);
    int mp = StatusUP(false);
    int nowLevel = getNowLevelByExp();
    int level = nowLevel - getLevel();
    for (int i = level; i > 1; i--) {
      hp += StatusUP(true);
      mp += StatusUP(false);
    } 
    hp = getMaxHp() - hp;
    mp = getMaxMp() - mp;
    setMaxHp(hp);
    setMaxMp(mp);
  }
  
  private int getNowLevelByExp() {
    Exp e = null;
    for (int i = 1; i <= 100; i++) {
      e = ExpTable.getInstance().getTemplate(i);
      if (getExp() < e.get_bonus())
        break; 
    } 
    if (e != null)
      return e.get_level(); 
    return 1;
  }
  
  private void updateCollarStatus() {
    if (this.owner != null && this.owner.getInventory() != null) {
      List<ItemInstance> list = this.owner.getInventory().getItemNameId(1173);
      for (ItemInstance item : list) {
        DogCollar dc = (DogCollar)item;
        if (dc.getPetObjectId() == getObjectId()) {
          dc.setPetLevel(getLevel());
          dc.setPetMxhp(getMaxHp());
          this.owner.getInventory().SendPacket((S_BasePacket)new S_InventoryStatus((ItemInstance)dc));
          break;
        } 
      } 
    } 
  }
  
  public void toRevival(L1Object own) {
    if (isDead()) {
      this.deadTime = 0L;
      clearFightList();
      setDead(false);
      setPoison(false);
      setGfxMode(0);
      SendPacket((S_BasePacket)new S_ObjectRestore(own, (L1Object)this), true);
      setFood(1);
      setCurrentHp(getLevel() + 10);
    } 
  }
  
  public void Talk(PcInstance pc) {
    if (!isDead() && !isDelete()) {
      pc.SendPacket((S_BasePacket)new S_ObjectPet(this));
      pc.setSummon(this);
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (!isDead() && !isDelete()) {
      SummonSystem.getInstance().Commander(this, text1);
      this.owner.SendPacket((S_BasePacket)new S_ObjectPet(this));
    } 
  }
  
  public void isStatus(long time) {
    if (this.owner != null && this.owner.getInventory() != null) {
      List<ItemInstance> list = this.owner.getInventory().getItemNameId(1173);
      if (list != null)
        for (ItemInstance item : list) {
          DogCollar dc = (DogCollar)item;
          if (dc.getPetObjectId() == getObjectId() && dc.getPet() == null) {
            dc.setPet(this);
            break;
          } 
        }  
    } 
  }
  
  public String getFoodStatus() {
    return this.FoodStatus;
  }
  
  public void setFoodStatus(String foodStatus) {
    this.FoodStatus = foodStatus;
  }
  
  public void insertDB() {
    StringBuilder sb = new StringBuilder();
    sb.append("INSERT INTO characters_pet SET ");
    sb.append("name='");
    sb.append(getName());
    sb.append("'");
    sb.append(", classId='");
    sb.append(getMon().getNameidN());
    sb.append("'");
    sb.append(", level='");
    sb.append(getLevel());
    sb.append("'");
    sb.append(", nowHp='");
    sb.append(getCurrentHp());
    sb.append("'");
    sb.append(", maxHp='");
    sb.append(getMaxHp());
    sb.append("'");
    sb.append(", nowMp='");
    sb.append(getCurrentMp());
    sb.append("'");
    sb.append(", maxMp='");
    sb.append(getMaxMp());
    sb.append("'");
    sb.append(", exp='");
    sb.append(getExp());
    sb.append("'");
    sb.append(", lawful='");
    sb.append(getLawful());
    sb.append("'");
    sb.append(", gfx='");
    sb.append(getGfx());
    sb.append("'");
    sb.append(", food='");
    sb.append(getFoodStatus());
    sb.append("'");
    sb.append(", id='");
    sb.append(getObjectId());
    sb.append("'");
    DatabaseConnection.getInstance().query_insert(sb.toString());
  }
  
  public void readDB() {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM characters_pet WHERE id=? AND del='0'");
      st.setInt(1, getObjectId());
      rs = st.executeQuery();
      if (rs.next()) {
        setName(rs.getString("name"));
        setLevel(rs.getInt("level"));
        setMaxHp(rs.getInt("maxHp"));
        setCurrentHp(rs.getInt("nowHp"));
        setMaxMp(rs.getInt("maxMp"));
        setCurrentMp(rs.getInt("nowMp"));
        setExp(rs.getInt("exp"));
        setLawful(rs.getInt("lawful"));
        setGfx(rs.getInt("gfx"));
        setFoodStatus(rs.getString("food"));
        Exp exp = ExpTable.getInstance().getTemplate(getLevel());
        long check_exp = exp.get_bonus() - exp.get_exp();
        return;
      } 
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  public void toSave(boolean memory_delete) {
    super.toSave(memory_delete);
    updateDB();
  }
  
  public void updateDB() {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE characters_pet SET ");
    sb.append("name='");
    sb.append(getName());
    sb.append("'");
    sb.append(", classId='");
    sb.append(getMon().getNameidN());
    sb.append("'");
    sb.append(", level='");
    sb.append(getLevel());
    sb.append("'");
    sb.append(", nowHp='");
    sb.append(getCurrentHp());
    sb.append("'");
    sb.append(", maxHp='");
    sb.append(getMaxHp());
    sb.append("'");
    sb.append(", nowMp='");
    sb.append(getCurrentMp());
    sb.append("'");
    sb.append(", maxMp='");
    sb.append(getMaxMp());
    sb.append("'");
    sb.append(", exp='");
    sb.append(getExp());
    sb.append("'");
    sb.append(", lawful='");
    sb.append(getLawful());
    sb.append("'");
    sb.append(", gfx='");
    sb.append(getGfx());
    sb.append("'");
    sb.append(", food='");
    sb.append(getFoodStatus());
    sb.append("'");
    sb.append(" WHERE id='");
    sb.append(getObjectId());
    sb.append("'");
    DatabaseConnection.getInstance().query_update(sb.toString());
  }
  
  public void deleteDB() {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE characters_pet SET del='1' WHERE id='");
    sb.append(getObjectId());
    sb.append("'");
    DatabaseConnection.getInstance().query_delete(sb.toString());
  }
  
  public void NameUpdate(String name) {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM characters_pet WHERE name=?");
      st.setString(1, name);
      rs = st.executeQuery();
      if (!rs.next()) {
        setName(name);
        updateDB();
        this.owner.SendPacket((S_BasePacket)new S_ObjectPet(this, 1), true);
        this.owner.SendPacket((S_BasePacket)new S_ObjectPet(this));
      } else {
        this.owner.SendPacket((S_BasePacket)new S_ServerMessage(327));
      } 
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  public String getExpPercentage() {
    if (getLevel() <= 5) {
      Exp exp = ExpTable.getInstance().getTemplate(getLevel());
      double d1 = (exp.get_bonus() - exp.get_exp());
      double d2 = (getExp() - exp.get_exp());
      return String.valueOf((int)(d2 / d1 * 100.0D));
    } 
    Exp nowExp = ExpTable.getInstance().getTemplate(getLevel());
    Exp beforeExp = ExpTable.getInstance().getTemplate(getLevel() - 1);
    double a = nowExp.get_exp();
    double b = (getExp() - beforeExp.get_bonus());
    return String.valueOf((int)(b / a * 100.0D));
  }
  
  public boolean isReceive() {
    return this.isReceive;
  }
  
  public void setReceive(boolean isReceive) {
    this.isReceive = isReceive;
  }
  
  public void drop() {}
}
