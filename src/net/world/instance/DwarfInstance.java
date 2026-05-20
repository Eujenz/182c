package net.world.instance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.database.DatabaseConnection;
import net.database.ItemsTable;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.network.server.S_ShowHtml;
import net.network.server.S_WarehouseItemList;
import net.world.function.ClanSystem;
import net.world.function.bean.Clan;
import net.world.instance.inventory.function.DogCollar;
import net.world.instance.inventory.function.Letter;
import net.world.instance.inventory.function.SlimeRaceTicket;
import net.world.object.L1Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DwarfInstance extends L1Object {
  final Logger log = LoggerFactory.getLogger(DwarfInstance.class);
  
  static final String WARE_HOUSE_PUT_LOG = "%s 存?? ? %s 存入??";
  
  static final String WARE_HOUSE_GET_LOG = "%s 取?? ? %s 取出??";
  
  static final String CLAN_WARE_HOUSE_PUT_LOG = "%s 存盟? ? %s 存入盟?";
  
  static final String CLAN_WARE_HOUSE_GET_LOG = "%s 取盟? ? %s 取出盟?";
  
  static final String ELF_HOUSE_PUT_LOG = "%s 存妖? ? %s 存入妖?";
  
  static final String ELF_HOUSE_GET_LOG = "%s 取妖? ? %s 取出妖?";
  
  protected boolean CheckLev(int lev) {
    return (5 <= lev);
  }
  
  public void WareHousePut(C_BasePacket data, PcInstance pc) {
    int Count = data.readH();
    int w_Count = getWhereHouseItemCount(pc.getClient().getUID());
    ItemInstance invTemp = null;
    ItemInstance wareTemp = null;
    if (Count > 0 && w_Count + Count <= 100) {
      for (int i = 0; i < Count; i++) {
        invTemp = pc.getInventory().getItemInvId(data.readD());
        long count = data.readD();
        if (invTemp != null && !invTemp.isEquipped() && invTemp.getItem().isWerehouse() && 2147483647L > count && count > 0L && count <= invTemp.getCount()) {
          wareTemp = null;
          if (invTemp.getItem().isPiles())
            wareTemp = getItem(pc.getClient().getUID(), invTemp, false); 
          if (invTemp.getCount() == count) {
            if (wareTemp == null) {
              wareTemp = invTemp;
              insertItem(pc, wareTemp, false);
            } else {
              wareTemp.setCount(wareTemp.getCount() + count);
              updateItem(wareTemp, false);
            } 
          } else if (wareTemp == null) {
            wareTemp = invTemp.clone();
            wareTemp.setCount(count);
            insertItem(pc, wareTemp, false);
          } else {
            wareTemp.setCount(wareTemp.getCount() + count);
            updateItem(wareTemp, false);
          } 
          this.log.info(String.format("%s 存?? ? %s 存入??", new Object[] { pc.getName(), wareTemp.logString() }));
          invTemp.setCount(pc, invTemp.getCount() - count);
        } 
      } 
      pc.getInventory().save();
    } else {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(75));
    } 
  }
  
  public void WareHouseGet(C_BasePacket data, PcInstance pc) {
    int count = data.readH();
    if (count > 0 && count <= 100) {
      int item_id = 0;
      long item_count = 0L;
      ItemInstance wareTemp = null;
      ItemInstance invTemp = null;
      if (pc.getInventory().Aden((count * 30), true)) {
        for (int i = 0; i < count; i++) {
          item_id = data.readD();
          item_count = data.readD();
          wareTemp = getItem(item_id, false);
          if (wareTemp != null && item_count > 0L && item_count <= wareTemp.getCount()) {
            invTemp = null;
            invTemp = pc.getInventory().isItem(wareTemp);
            if (pc.getInventory().getCount() >= 180 && invTemp == null) {
              pc.SendPacket((S_BasePacket)new S_ServerMessage(263));
              break;
            } 
            if (!pc.getInventory().isWeight((int)(wareTemp.getItem().getWeight() * item_count))) {
              pc.SendPacket((S_BasePacket)new S_ServerMessage(82));
              break;
            } 
            long intialCount = wareTemp.getCount();
            if (wareTemp.getCount() == item_count) {
              if (invTemp == null) {
                pc.getInventory().add(wareTemp);
                invTemp = wareTemp;
              } else {
                invTemp.setCount(pc, invTemp.getCount() + item_count);
              } 
              removeItem(wareTemp, false);
            } else {
              if (invTemp == null) {
                invTemp = wareTemp.clone();
                invTemp.setCount(item_count);
                pc.getInventory().add(invTemp);
              } else {
                invTemp.setCount(pc, invTemp.getCount() + item_count);
              } 
              wareTemp.setCount(wareTemp.getCount() - item_count);
              updateItem(wareTemp, false);
            } 
            this.log.info(String.format("%s 取?? ? %s 取出??", new Object[] { pc.getName(), wareTemp.logString() }));
          } 
        } 
        pc.getInventory().save();
      } else {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(189));
      } 
    } 
  }
  
  public void ClanWareHousePut(C_BasePacket data, PcInstance pc) {
    if (pc.getClanId() != 0) {
      if (pc.getClassType() == 0 || (pc.getTitle() != null && pc.getTitle().length() > 0)) {
        int Count = data.readH();
        int w_Count = getClanWareHouseItemCount(pc.getClanId());
        if (Count > 0 && Count + w_Count <= 100) {
          ItemInstance invTemp = null;
          ItemInstance wareTemp = null;
          int inv_id = 0;
          long count = 0L;
          for (int i = 0; i < Count; i++) {
            inv_id = data.readD();
            count = data.readD();
            invTemp = pc.getInventory().getItemInvId(inv_id);
            if (invTemp != null && !invTemp.isEquipped() && invTemp.getItem().isWerehouse() && 2147483647L >= count && count > 0L && count <= invTemp.getCount()) {
              wareTemp = null;
              if (invTemp.getItem().isPiles())
                wareTemp = getItem(pc.getClanId(), invTemp, true); 
              if (invTemp.getCount() == count) {
                if (wareTemp == null) {
                  wareTemp = invTemp;
                  insertItem(pc, wareTemp, true);
                } else {
                  wareTemp.setCount(wareTemp.getCount() + count);
                  updateItem(wareTemp, true);
                } 
              } else if (wareTemp == null) {
                wareTemp = invTemp.clone();
                wareTemp.setCount(count);
                insertItem(pc, wareTemp, true);
              } else {
                wareTemp.setCount(wareTemp.getCount() + count);
                updateItem(wareTemp, true);
              } 
              this.log.info(String.format("%s 存盟? ? %s 存入盟?", new Object[] { pc.getName(), wareTemp.logString() }));
              invTemp.setCount(pc, invTemp.getCount() - count);
            } 
          } 
          pc.getInventory().save();
        } else {
          pc.SendPacket((S_BasePacket)new S_ServerMessage(75));
        } 
      } else {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(209));
      } 
    } else {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(208));
    } 
  }
  
  public void ClanWareHouseGet(C_BasePacket data, PcInstance pc) {
    if (pc.getClanId() != 0) {
      if (pc.getClassType() == 0 || (pc.getTitle() != null && pc.getTitle().length() > 0)) {
        int count = data.readH();
        if (count > 0 && count <= 100) {
          int item_id = 0;
          long item_count = 0L;
          ItemInstance wareTemp = null;
          ItemInstance invTemp = null;
          if (pc.getInventory().Aden((count * 30), true)) {
            for (int i = 0; i < count; i++) {
              item_id = data.readD();
              item_count = data.readD();
              wareTemp = getItem(item_id, true);
              if (wareTemp != null && item_count > 0L && item_count <= wareTemp.getCount()) {
                invTemp = null;
                invTemp = pc.getInventory().isItem(wareTemp);
                if (pc.getInventory().getCount() >= 180 && invTemp == null) {
                  pc.SendPacket((S_BasePacket)new S_ServerMessage(263));
                  break;
                } 
                if (!pc.getInventory().isWeight((int)(wareTemp.getItem().getWeight() * item_count))) {
                  pc.SendPacket((S_BasePacket)new S_ServerMessage(82));
                  break;
                } 
                long intialCount = wareTemp.getCount();
                if (wareTemp.getCount() == item_count) {
                  if (invTemp == null) {
                    invTemp = wareTemp;
                    pc.getInventory().add(wareTemp);
                  } else {
                    invTemp.setCount(pc, invTemp.getCount() + item_count);
                  } 
                  removeItem(wareTemp, true);
                } else {
                  if (invTemp == null) {
                    invTemp = wareTemp.clone();
                    invTemp.setCount(item_count);
                    pc.getInventory().add(invTemp);
                  } else {
                    invTemp.setCount(pc, invTemp.getCount() + item_count);
                  } 
                  wareTemp.setCount(wareTemp.getCount() - item_count);
                  updateItem(wareTemp, true);
                } 
                this.log.info(String.format("%s 取盟? ? %s 取出盟?", new Object[] { pc.getName(), wareTemp.logString() }));
              } 
            } 
            pc.getInventory().save();
          } else {
            pc.SendPacket((S_BasePacket)new S_ServerMessage(189));
          } 
        } 
      } else {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(728));
      } 
      Clan clan = ClanSystem.getInstance().getClan(pc.getClanId());
      if (clan != null)
        clan.setLockWarehouse(false); 
    } else {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(208));
    } 
  }
  
  public void ElfWareHousePut(C_BasePacket data, PcInstance pc) {
    pc.Message("功能開發中..");
  }
  
  public void ElfWareHouseGet(C_BasePacket data, PcInstance pc) {
    pc.Message("功能開發中..");
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("retrieve")) {
      getWareHouseItemList(pc);
    } else if (text1.equalsIgnoreCase("retrieve-pledge")) {
      if (pc.getClanId() != 0) {
        getClanWareHouseItemList(pc);
      } else {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(208));
      } 
    } else if (text1.equalsIgnoreCase("retrieve-elven")) {
      getWareHouseElfItemList(pc);
    } 
  }
  
  private void insertItem(PcInstance pc, ItemInstance item, boolean clan) {
    StringBuffer sb = new StringBuffer();
    if (clan) {
      sb.append("INSERT INTO warehouse_clan SET clan_id='");
      sb.append(pc.getClanId());
    } else {
      sb.append("INSERT INTO warehouse SET account_uid='");
      sb.append(pc.getClient().getUID());
    } 
    sb.append("', inv_id='");
    sb.append(item.getInvID());
    sb.append("', pet_id='");
    sb.append(item.getPetObjectId());
    sb.append("', letter_id='");
    sb.append(item.getLetterUid());
    sb.append("', id='");
    sb.append(item.getItem().getItemId());
    sb.append("', type='");
    sb.append(item.getItem().getType1());
    sb.append("', gfxid='");
    sb.append(item.getItem().get_gfxid());
    sb.append("', name='");
    sb.append(getName(item));
    sb.append("', count='");
    sb.append(item.getCount());
    sb.append("', have_count='");
    sb.append(item.getHaveCount());
    sb.append("', en='");
    sb.append(item.getEnLevel());
    sb.append("', definite='");
    sb.append(item.isDefinite() ? 1 : 0);
    sb.append("', bless='");
    sb.append(item.getBless());
    sb.append("', durability='");
    sb.append(item.getDurability());
    sb.append("', time='");
    sb.append(item.getTime());
    if (item instanceof SlimeRaceTicket) {
      SlimeRaceTicket t = (SlimeRaceTicket)item;
      sb.append("', slimerace_uid='");
      sb.append(t.getSlimeRaceUid());
      sb.append("', slimeracer_idx='");
      sb.append(t.getSlimeRacerIdx());
      sb.append("', slimeracer_name='");
      sb.append(t.getSlimeRacerName());
    } 
    sb.append("'");
    DatabaseConnection.getInstance().query_insert(sb.toString());
  }
  
  private void updateItem(ItemInstance item, boolean clan) {
    StringBuffer sb = new StringBuffer();
    if (clan) {
      sb.append("UPDATE warehouse_clan SET count='");
    } else {
      sb.append("UPDATE warehouse SET count='");
    } 
    sb.append(item.getCount());
    sb.append("', name='");
    sb.append(getName(item));
    sb.append("' WHERE uid='");
    sb.append(item.getUid());
    sb.append("'");
    DatabaseConnection.getInstance().query_update(sb.toString());
  }
  
  private void removeItem(ItemInstance item, boolean clan) {
    StringBuffer sb = new StringBuffer();
    if (clan) {
      sb.append("DELETE FROM warehouse_clan WHERE uid='");
    } else {
      sb.append("DELETE FROM warehouse WHERE uid='");
    } 
    sb.append(item.getUid());
    sb.append("'");
    DatabaseConnection.getInstance().query_delete(sb.toString());
  }
  
  private ItemInstance getItem(int id, ItemInstance item, boolean clan) {
    ItemInstance temp = null;
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      StringBuffer sb = new StringBuffer();
      if (clan) {
        sb.append("SELECT * FROM warehouse_clan WHERE id='");
        sb.append(item.getItem().getItemId());
        sb.append("' AND bless='");
        sb.append(item.getBless());
        sb.append("' AND clan_id='");
        sb.append(id);
        sb.append("'");
      } else {
        sb.append("SELECT * FROM warehouse WHERE id='");
        sb.append(item.getItem().getItemId());
        sb.append("' AND bless='");
        sb.append(item.getBless());
        sb.append("' AND account_uid='");
        sb.append(id);
        sb.append("'");
      } 
      if (item instanceof SlimeRaceTicket) {
        SlimeRaceTicket srt = (SlimeRaceTicket)item;
        sb.append(" AND slimerace_uid='");
        sb.append(srt.getSlimeRaceUid());
        sb.append("' AND slimeracer_idx='");
        sb.append(srt.getSlimeRacerIdx());
        sb.append("'");
      } 
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement(sb.toString());
      rs = st.executeQuery();
      if (rs.next()) {
        temp = ItemsTable.getInstance().newItem(rs.getInt("id"), (rs.getInt("definite") == 1), false);
        temp.setUid(rs.getInt("uid"));
        temp.setInvID(rs.getInt("inv_id"));
        temp.setPetObjectId(rs.getInt("pet_id"));
        temp.setLetterUid(rs.getInt("letter_id"));
        temp.setCount(rs.getInt("count"));
        temp.setHaveCount(rs.getInt("have_count"));
        temp.setEnLevel(rs.getInt("en"));
        temp.setBless(rs.getInt("bless"));
        temp.setDurability(rs.getInt("durability"));
        temp.setTime(rs.getInt("time"));
        if (temp instanceof DogCollar)
          setPetInfo((DogCollar)temp); 
        if (temp instanceof Letter)
          setLetterInfo((Letter)temp); 
        if (temp instanceof SlimeRaceTicket) {
          SlimeRaceTicket t = (SlimeRaceTicket)temp;
          t.setSlimeRaceUid(rs.getInt("slimerace_uid"));
          t.setSlimeRacerIdx(rs.getInt("slimeracer_idx"));
          t.setSlimeRacerName(rs.getString("slimeracer_name"));
        } 
        return temp;
      } 
    } catch (Exception localException) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
    DatabaseConnection.getInstance().close(con, st, rs);
    DatabaseConnection.getInstance().close(con, st, rs);
    return temp;
  }
  
  private ItemInstance getItem(int uid, boolean clan) {
    ItemInstance temp = null;
    if (uid > 0) {
      Connection con = null;
      PreparedStatement st = null;
      ResultSet rs = null;
      try {
        con = DatabaseConnection.getInstance().getConnection();
        if (clan) {
          st = con.prepareStatement("SELECT * FROM warehouse_clan WHERE uid=?");
        } else {
          st = con.prepareStatement("SELECT * FROM warehouse WHERE uid=?");
        } 
        st.setInt(1, uid);
        rs = st.executeQuery();
        if (rs.next()) {
          temp = ItemsTable.getInstance().newItem(rs.getInt("id"), (rs.getInt("definite") == 1), false);
          temp.setUid(rs.getInt("uid"));
          temp.setInvID(rs.getInt("inv_id"));
          temp.setObjectId(rs.getInt("inv_id"));
          temp.setCount(rs.getInt("count"));
          temp.setHaveCount(rs.getInt("have_count"));
          temp.setEnLevel(rs.getInt("en"));
          temp.setBless(rs.getInt("bless"));
          temp.setDurability(rs.getInt("durability"));
          temp.setTime(rs.getInt("time"));
          if (rs.getInt("pet_id") > 0) {
            temp.setPetObjectId(rs.getInt("pet_id"));
            setPetInfo((DogCollar)temp);
          } 
          if (rs.getInt("letter_id") > 0) {
            temp.setLetterUid(rs.getInt("letter_id"));
            setLetterInfo((Letter)temp);
          } 
          if (temp instanceof SlimeRaceTicket) {
            SlimeRaceTicket t = (SlimeRaceTicket)temp;
            t.setSlimeRaceUid(rs.getInt("slimerace_uid"));
            t.setSlimeRacerIdx(rs.getInt("slimeracer_idx"));
            t.setSlimeRacerName(rs.getString("slimeracer_name"));
          } 
          return temp;
        } 
      } catch (Exception localException) {
      
      } finally {
        DatabaseConnection.getInstance().close(con, st, rs);
      } 
      DatabaseConnection.getInstance().close(con, st, rs);
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
    return temp;
  }
  
  private void getWareHouseItemList(PcInstance pc) {
    int count = getWhereHouseItemCount(pc.getClient().getUID());
    if (count > 0) {
      Connection con = null;
      PreparedStatement st = null;
      ResultSet rs = null;
      try {
        con = DatabaseConnection.getInstance().getConnection();
        st = con.prepareStatement("SELECT * FROM warehouse WHERE account_uid=? ORDER BY uid ASC");
        st.setInt(1, pc.getClient().getUID());
        rs = st.executeQuery();
        pc.SendPacket((S_BasePacket)new S_WarehouseItemList(getObjectId(), rs, count, 3));
      } catch (Exception localException) {
      
      } finally {
        DatabaseConnection.getInstance().close(con, st, rs);
      } 
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "noitemret"));
    } 
  }
  
  private void getWareHouseElfItemList(PcInstance pc) {
    int count = getWhereHouseElfItemCount(pc.getClient().getUID());
    if (count > 0) {
      Connection con = null;
      PreparedStatement st = null;
      ResultSet rs = null;
      try {
        con = DatabaseConnection.getInstance().getConnection();
        st = con.prepareStatement("SELECT * FROM warehouse_elf WHERE account_uid=? ORDER BY uid ASC");
        st.setInt(1, pc.getClient().getUID());
        rs = st.executeQuery();
        pc.SendPacket((S_BasePacket)new S_WarehouseItemList(getObjectId(), rs, count, 9));
      } catch (Exception localException) {
      
      } finally {
        DatabaseConnection.getInstance().close(con, st, rs);
      } 
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "noitemret"));
    } 
  }
  
  private void getClanWareHouseItemList(PcInstance pc) {
    Clan clan = ClanSystem.getInstance().getClan(pc.getClanId());
    if (clan != null && clan.isLockWarehouse()) {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(209));
      return;
    } 
    int count = getClanWareHouseItemCount(pc.getClanId());
    if (count > 0) {
      clan.setLockWarehouse(true);
      pc.setUseClanWareHouse(true);
      Connection con = null;
      PreparedStatement st = null;
      ResultSet rs = null;
      try {
        con = DatabaseConnection.getInstance().getConnection();
        st = con.prepareStatement("SELECT * FROM warehouse_clan WHERE clan_id=? ORDER BY uid ASC");
        st.setInt(1, pc.getClanId());
        rs = st.executeQuery();
        pc.SendPacket((S_BasePacket)new S_WarehouseItemList(getObjectId(), rs, count, 5));
      } catch (Exception localException) {
      
      } finally {
        DatabaseConnection.getInstance().close(con, st, rs);
      } 
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "noitemret"));
    } 
  }
  
  private int getWhereHouseItemCount(int uid) {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT count(*) FROM warehouse WHERE account_uid='");
    sb.append(uid);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select_count(sb.toString());
  }
  
  private int getWhereHouseElfItemCount(int uid) {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT count(*) FROM warehouse_elf WHERE account_uid='");
    sb.append(uid);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select_count(sb.toString());
  }
  
  private int getClanWareHouseItemCount(int clan_id) {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT count(*) FROM warehouse_clan WHERE clan_id='");
    sb.append(clan_id);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select_count(sb.toString());
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
  
  private String getName(ItemInstance items) {
    StringBuffer sb = new StringBuffer();
    if (items.isDefinite() && items.getItem().getType1() != 0 && items.getItem().getType1() != 3 && items.getItem().getType() != 1) {
      if (items.getEnLevel() >= 0) {
        sb.append("+");
      } else {
        sb.append("-");
      } 
      sb.append(items.getEnLevel());
      sb.append(" ");
    } 
    if (items instanceof Letter && items.getLetterUid() > 0) {
      Letter l = (Letter)items;
      sb.append(l.getSenderName());
      sb.append(" : ");
      sb.append(l.getSubject());
    } else {
      sb.append(items.getName());
      if (items.getCount() > 1L) {
        sb.append(" (");
        sb.append(items.getCount());
        sb.append(")");
      } 
      if (items.isDefinite() && (items instanceof net.world.instance.inventory.function.MapleWand || items instanceof net.world.instance.inventory.function.PineWand || items instanceof net.world.instance.inventory.function.EbonyWand)) {
        sb.append(" (");
        sb.append(items.getHaveCount());
        sb.append(")");
      } 
      if (items instanceof DogCollar) {
        DogCollar pet = (DogCollar)items;
        sb.append(" [Lv.");
        sb.append(pet.getPetLevel());
        sb.append(" ");
        sb.append(pet.getPetName());
        sb.append("]");
      } 
    } 
    return sb.toString();
  }
}
