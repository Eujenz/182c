package net.world.instance.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.Config;
import net.database.DatabaseConnection;
import net.database.ItemsSetoptionTable;
import net.database.ItemsTable;
import net.database.bean.ItemSetOption;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_CharacterStat;
import net.network.server.S_InventoryAdd;
import net.network.server.S_InventoryDelete;
import net.network.server.S_ObjectSpMr;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.instance.inventory.function.DogCollar;
import net.world.instance.inventory.function.Letter;
import net.world.instance.inventory.function.SlimeRaceTicket;
import net.world.object.Character;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PcInventory extends Inventory {
  final Logger log = LoggerFactory.getLogger(PcInventory.class);
  
  private PcInstance pc;
  
  private ItemInstance[] slot;
  
  private long clickTime;
  
  private ItemSetOption is;
  
  private double addMaxWeight;
  
  public PcInventory(PcInstance pc) {
    this.pc = pc;
    read();
    this.slot = new ItemInstance[14];
  }
  
  public void SendPacket(S_BasePacket bp) {
    this.pc.SendPacket(bp);
  }
  
  public void clickItem(C_BasePacket bp) {
    if (!getCha().isDead()) {
      long time = System.currentTimeMillis();
      ItemInstance temp = getItemInvId(bp.readD());
      if (temp != null && time - this.clickTime >= temp.getItem().getContinuous()) {
        this.clickTime = time;
        temp.clickItem(getCha(), bp);
        if (temp instanceof net.world.instance.ItemWeaponInstance || temp instanceof net.world.instance.ItemArmorInstance)
          SetItemSetting(temp); 
      } 
    } 
  }
  
  public void JoinWorld() {
    int tempAc = 0;
    for (ItemInstance item : getAll()) {
      if (item.isEquipped()) {
        switch (item.getItem().getType1()) {
          case 2:
            tempAc += item.getItem().get_ac() + item.getEnLevel();
            break;
        } 
        item.Equipped((Character)this.pc);
        item.itemOption((Character)this.pc);
      } 
    } 
    if (this.pc.getAc() != tempAc) {
      this.pc.setAc(tempAc);
      this.pc.SendPacket((S_BasePacket)new S_CharacterStat((Character)this.pc));
    } 
    for (ItemInstance item : getAll()) {
      if (item.isEquipped() && (item instanceof net.world.instance.ItemWeaponInstance || item instanceof net.world.instance.ItemArmorInstance))
        SetItemSetting(item); 
    } 
    this.pc.setCurrentHp(this.pc.temp_hp);
    this.pc.setCurrentMp(this.pc.temp_mp);
  }
  
  public synchronized void save() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.pc.getName()).append(" 保存背包物品 ?始！\n");
    for (ItemInstance item : getAll())
      sb.append("\t").append(item.logString()).append("\n"); 
    sb.append(this.pc.getName()).append(" 保存背包物品 完成！");
    this.log.info(sb.toString());
    Connection con = null;
    PreparedStatement pstm = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      con.setAutoCommit(false);
      con.prepareStatement("DELETE FROM characters_inventory WHERE char_id='" + this.pc.getObjectId() + "'").execute();
      pstm = con.prepareStatement(String.format("INSERT INTO characters_inventory SET %s,  %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s ", new Object[] { 
              "uid=?", "char_id=?", "pet_id=?", "letter_id=?", "item_id=?", "count=?", "have_count=?", "en=?", "equipped=?", "definite=?", 
              "bless=?", "durability=?", "time=?", "slimerace_uid=?", "silmerace_idx=?", "silmerace_name=?" }));
      for (ItemInstance item : getAll()) {
        pstm.setInt(1, item.getObjectId());
        pstm.setInt(2, this.pc.getObjectId());
        pstm.setInt(3, item.getPetObjectId());
        pstm.setInt(4, item.getLetterUid());
        pstm.setInt(5, item.getItem().getItemId());
        pstm.setInt(6, (int)item.getCount());
        pstm.setInt(7, item.getHaveCount());
        pstm.setInt(8, item.getEnLevel());
        pstm.setInt(9, item.isEquipped() ? 1 : 0);
        pstm.setInt(10, item.isDefinite() ? 1 : 0);
        pstm.setInt(11, item.getBless());
        pstm.setInt(12, item.getDurability());
        pstm.setInt(13, item.getTime());
        if (item instanceof SlimeRaceTicket) {
          pstm.setInt(14, (int)item.getCount());
          pstm.setInt(15, (int)item.getCount());
          pstm.setInt(16, (int)item.getCount());
        } else {
          pstm.setInt(14, 0);
          pstm.setInt(15, 0);
          pstm.setString(16, "");
        } 
        pstm.execute();
      } 
      con.commit();
    } catch (Exception e) {
      e.printStackTrace();
      this.log.error("?取背包?异常：", e);
    } finally {
      DatabaseConnection.getInstance().close(con, pstm);
    } 
  }
  
  public synchronized void read() {
    Connection con = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      pstm = con.prepareStatement("select * from characters_inventory where char_id='" + this.pc.getObjectId() + "'");
      rs = pstm.executeQuery();
      while (rs.next()) {
        ItemInstance item = ItemsTable.getInstance().newItem(rs.getInt("item_id"), (rs.getInt("definite") == 1), false);
        item.setInvID(rs.getInt(1));
        item.setObjectId(item.getInvID());
        item.setPetObjectId(rs.getInt("pet_id"));
        item.setLetterUid(rs.getInt("letter_id"));
        item.setCount(rs.getInt("count"));
        item.setHaveCount(rs.getInt("have_count"));
        item.setEnLevel(rs.getInt("en"));
        item.setEquipped((rs.getInt("equipped") == 1 && item.itemLvCheck((Character)this.pc)));
        item.setBless(rs.getInt("bless"));
        item.setDurability(rs.getInt("durability"));
        item.setTime(rs.getInt("time"));
        if (item instanceof DogCollar)
          setPetInfo((DogCollar)item); 
        if (item instanceof Letter)
          setLetterInfo((Letter)item); 
        if (item instanceof SlimeRaceTicket) {
          SlimeRaceTicket t = (SlimeRaceTicket)item;
          t.setSlimeRaceUid(rs.getInt("slimerace_uid"));
          t.setSlimeRacerIdx(rs.getInt("silmerace_idx"));
          t.setSlimeRacerName(rs.getString("silmerace_name"));
        } 
        item.isSetting();
        super.add(item);
      } 
      StringBuilder sb = new StringBuilder();
      sb.append(this.pc.getName()).append(" ?取背包物品 ?始！\n");
      for (ItemInstance item : getAll())
        sb.append("\t").append(item.logString()).append("\n"); 
      sb.append(this.pc.getName()).append(" ?取背包物品 完成！");
      this.log.info(sb.toString());
    } catch (Exception e) {
      e.printStackTrace();
      this.log.error(e.getLocalizedMessage(), e);
    } finally {
      DatabaseConnection.getInstance().close(con, pstm, rs);
    } 
  }
  
  private void setPetInfo(DogCollar item) {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM characters_pet WHERE id=?");
      st.setInt(1, item.getPetObjectId());
      rs = st.executeQuery();
      if (rs.next()) {
        item.setPetName(rs.getString("name"));
        item.setPetClassId(rs.getInt("classId"));
        item.setPetLevel(rs.getInt("level"));
        item.setPetMxhp(rs.getInt("maxHp"));
        item.setDeleteDb((rs.getInt("del") == 1));
      } 
    } catch (Exception localException) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  private void setLetterInfo(Letter item) {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM characters_letter WHERE uid=?");
      st.setInt(1, item.getLetterUid());
      rs = st.executeQuery();
      if (rs.next()) {
        item.setSenderName(rs.getString("paperFrom"));
        item.setSubject(rs.getString("paperSubject"));
      } 
    } catch (Exception localException) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  public void sendList() {
    for (ItemInstance item : getAll())
      this.pc.SendPacket((S_BasePacket)new S_InventoryAdd(item)); 
  }
  
  public void add(ItemInstance item) {
    super.add(item);
    this.pc.SendPacket((S_BasePacket)new S_InventoryAdd(item));
    this.pc.SendPacket((S_BasePacket)new S_CharacterStat((Character)this.pc));
  }
  
  public void remove(ItemInstance item) {
    super.remove(item);
    this.pc.SendPacket((S_BasePacket)new S_InventoryDelete(item));
    this.pc.SendPacket((S_BasePacket)new S_CharacterStat((Character)this.pc));
  }
  
  public ItemInstance getSlot(int s) {
    return this.slot[s];
  }
  
  public void setSlot(int s, ItemInstance temp) {
    this.slot[s] = temp;
  }
  
  public boolean Arrow(boolean Gamso) {
    ItemInstance temp = getSlot(11);
    if (temp != null) {
      temp = getArrow();
      if (temp != null) {
        if (Gamso)
          temp.setCount((Character)this.pc, temp.getCount() - 1L); 
        return true;
      } 
    } 
    return false;
  }
  
  public void delete() {
    for (ItemInstance item : getAll()) {
      if (item.isEquipped() && (item instanceof net.world.instance.ItemWeaponInstance || item instanceof net.world.instance.ItemArmorInstance)) {
        this.is = ItemsSetoptionTable.getInstance().getBean(item.getItem().getSetitemUid());
        if (this.is != null)
          this.is.removeList(this.pc); 
      } 
    } 
    this.pc = null;
    this.slot = null;
    super.delete();
  }
  
  public boolean Aden(long CheckCount, boolean GamSo) {
    if (CheckCount == 0L)
      return true; 
    ItemInstance temp = getAden();
    if (temp != null && temp.getCount() >= CheckCount && CheckCount >= 0L) {
      if (GamSo)
        temp.setCount((Character)this.pc, temp.getCount() - CheckCount); 
      return true;
    } 
    return false;
  }
  
  public boolean Money(long CheckCount, boolean GamSo) {
    if (CheckCount == 0L)
      return true; 
    ItemInstance temp = getMoney();
    if (temp != null && temp.getCount() >= CheckCount && CheckCount >= 0L) {
      if (GamSo)
        temp.setCount((Character)this.pc, temp.getCount() - CheckCount); 
      return true;
    } 
    return false;
  }
  
  public int getWeight() {
    int r = 0;
    if (this.pc != null) {
      int weight = 0;
      double max_weight = 0.0D;
      weight = super.getWeight();
      max_weight = getMaxWeight() + this.addMaxWeight;
      r = (int)(weight / max_weight * 30.0D);
      if (r > 29)
        r = 29; 
    } 
    return r;
  }
  
  public boolean isWeight(int weight) {
    weight += super.getWeight();
    double max_weight = getMaxWeight() + this.addMaxWeight;
    return (weight / max_weight * 30.0D < 30.0D);
  }
  
  public double getMaxWeight() {
    double str = this.pc.getTotalStr();
    double con = this.pc.getTotalCon();
    double max_weight = (str + con + 1.0D + this.pc.toOriginalStatConWeight() + this.pc.toOriginalStatStrWeight()) / 2.0D * 150.0D * Config.RateWeightLimit;
    max_weight += max_weight;
    return max_weight;
  }
  
  public void addMaxWeight(double value) {
    this.addMaxWeight = value;
  }
  
  public double getMaxWeightForAdd() {
    return this.addMaxWeight;
  }
  
  public boolean RingOfTeleportControl() {
    ItemInstance r1 = getSlot(6);
    ItemInstance r2 = getSlot(7);
    if ((r1 != null && r1 instanceof net.world.instance.inventory.function.RingTeleportControl) || (r2 != null && r2 instanceof net.world.instance.inventory.function.RingTeleportControl))
      return true; 
    return false;
  }
  
  public boolean RingOfPolymorphControl() {
    ItemInstance r1 = getSlot(6);
    ItemInstance r2 = getSlot(7);
    if ((r1 != null && r1 instanceof net.world.instance.inventory.function.RingPolymorphControl) || (r2 != null && r2 instanceof net.world.instance.inventory.function.RingPolymorphControl))
      return true; 
    return false;
  }
  
  public Character getCha() {
    return (Character)this.pc;
  }
  
  public void SetItemSetting(ItemInstance item) {
    this.is = ItemsSetoptionTable.getInstance().getBean(item.getItem().getSetitemUid());
    if (this.is != null) {
      int count = 0;
      for (ItemInstance s : this.slot) {
        if (s != null && s.getItem().getSetitemUid() == this.is.getUid())
          count++; 
      } 
      if (count == this.is.getCount()) {
        if (item.isEquipped() && !this.is.isList(this.pc)) {
          this.is.addList(this.pc);
          SetItemOption(this.is, true);
        } 
      } else if (count < this.is.getCount() && !item.isEquipped() && this.is.isList(this.pc)) {
        this.is.removeList(this.pc);
        SetItemOption(this.is, false);
      } 
    } 
  }
  
  private void SetItemOption(ItemSetOption is, boolean equipped) {
    if (is.getAddHp() != 0)
      this.pc.setDynamicHp(equipped ? (this.pc.getDynamicHp() + is.getAddHp()) : (this.pc.getDynamicHp() - is.getAddHp())); 
    if (is.getAddMp() != 0)
      this.pc.setDynamicMp(equipped ? (this.pc.getDynamicMp() + is.getAddMp()) : (this.pc.getDynamicMp() - is.getAddMp())); 
    if (is.getAddStr() != 0)
      this.pc.setDynamicStr(equipped ? (this.pc.getDynamicStr() + is.getAddStr()) : (this.pc.getDynamicStr() - is.getAddStr())); 
    if (is.getAddDex() != 0)
      this.pc.setDynamicDex(equipped ? (this.pc.getDynamicDex() + is.getAddDex()) : (this.pc.getDynamicDex() - is.getAddDex())); 
    if (is.getAddCon() != 0)
      this.pc.setDynamicCon(equipped ? (this.pc.getDynamicCon() + is.getAddCon()) : (this.pc.getDynamicCon() - is.getAddCon())); 
    if (is.getAddInt() != 0)
      this.pc.setDynamicInt(equipped ? (this.pc.getDynamicInt() + is.getAddInt()) : (this.pc.getDynamicInt() - is.getAddInt())); 
    if (is.getAddWis() != 0)
      this.pc.setDynamicWis(equipped ? (this.pc.getDynamicWis() + is.getAddWis()) : (this.pc.getDynamicWis() - is.getAddWis())); 
    if (is.getAddCha() != 0)
      this.pc.setDynamicCha(equipped ? (this.pc.getDynamicCha() + is.getAddCha()) : (this.pc.getDynamicCha() - is.getAddCha())); 
    if (is.getAddAc() != 0)
      this.pc.setDynamicAc(equipped ? (this.pc.getDynamicAc() + is.getAddAc()) : (this.pc.getDynamicAc() - is.getAddAc())); 
    if (is.getAddMr() != 0)
      this.pc.setDynamicMr(equipped ? (this.pc.getDynamicMr() + is.getAddMr()) : (this.pc.getDynamicMr() - is.getAddMr())); 
    if (is.getTicHp() != 0)
      this.pc.setDynamicTicHp(equipped ? (this.pc.getDynamicTicHp() + is.getTicHp()) : (this.pc.getDynamicTicHp() - is.getTicHp())); 
    if (is.getTicMp() != 0)
      this.pc.setDynamicTicMp(equipped ? (this.pc.getDynamicTicMp() + is.getTicMp()) : (this.pc.getDynamicTicMp() - is.getTicMp())); 
    is.getPolymorph();
    if (is.getWisdress() != 0)
      this.pc.setDynamicWindress(equipped ? (this.pc.getDynamicWindress() + is.getWisdress()) : (this.pc.getDynamicWindress() - is.getWisdress())); 
    if (is.getWateress() != 0)
      this.pc.setDynamicWaterress(equipped ? (this.pc.getDynamicWaterress() + is.getWateress()) : (this.pc.getDynamicWaterress() - is.getWateress())); 
    if (is.getFireress() != 0)
      this.pc.setDynamicFireress(equipped ? (this.pc.getDynamicFireress() + is.getFireress()) : (this.pc.getDynamicFireress() - is.getFireress())); 
    if (is.getEarthress() != 0)
      this.pc.setDynamicEarthress(equipped ? (this.pc.getDynamicEarthress() + is.getEarthress()) : (this.pc.getDynamicEarthress() - is.getEarthress())); 
    if (is.isGm())
      this.pc.setGm(equipped); 
    this.pc.SendPacket((S_BasePacket)new S_CharacterStat((Character)this.pc));
    this.pc.SendPacket((S_BasePacket)new S_ObjectSpMr(this.pc.getSp(true), this.pc.getMr()));
  }
}
