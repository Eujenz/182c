package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;

import net.Config;
import net.LineageClient;
import net.database.bean.L1Characte;
import net.database.bean.L1GetBackRestart;
import net.network.server.S_BasePacket;
import net.network.server.S_CharacterAdd;
import net.network.server.S_CharacterInfo;
import net.network.server.S_CharacterRemove;
import net.network.server.S_CharacterStat;
import net.network.server.S_LoginFail;
import net.network.server.S_ObjectChatting;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ServerMessage;
import net.network.server.S_WorldJoin;
import net.network.server.S_WorldStatPacket;
import net.util.BadNamesList;
import net.world.WorldInstance;
import net.world.function.ClanSystem;
import net.world.function.GmCommand;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.instance.inventory.function.Letter;
import net.world.object.Character;
import net.world.object.L1Object;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterTable {
  final Logger log = LoggerFactory.getLogger(CharacterTable.class);
  
  private HashMap<String, L1Characte> _list = new HashMap<String, L1Characte>();
  
  static final String JOIN_LOG = "IP[%s] ??[%s] 角色[%s] ?前等?{%s} ??{%s} 登?游? ";
  
  private static final String _JOIN_LOG = "(假人登?攻?)非法IP[%s] ??[%s] 角色[%s] 登?游? ";
  
  private static final String _JOIN_LOG_ = "(假人登?攻?)非法IP[%s] ??[%s] 角色[%s] 封?IP ";
  
  private static final String _JOIN_LOG_ILLEGAL = "(假人登?攻?)非法(未?建角色即?入游??)IP[%s] ??[%s] 角色[%s] 封?IP ";
  
  private static class Holder {
    static CharacterTable instance = new CharacterTable();
  }
  
  public static CharacterTable getInstance() {
    return Holder.instance;
  }
  
  public void load() {
    System.out.print("[SQL] 加载角色表.");
    try {
      Connection con = DatabaseConnection.getInstance().getConnection();
      PreparedStatement statement = con.prepareStatement("SELECT * FROM characters");
      ResultSet cha = statement.executeQuery();
      chatable(cha);
      cha.close();
      statement.close();
      con.close();
    } catch (Exception e) {
      this.log.error(e.getLocalizedMessage(), e);
    } 
  }
  
  private void chatable(ResultSet Data) throws Exception {
    while (Data.next()) {
      L1Characte cha = new L1Characte();
      cha.setName(Data.getString(1));
      cha.setAccount(Data.getString(2));
      this._list.put(String.valueOf(cha.getName()), cha);
    } 
    System.out.println(" 数量:" + this._list.size());
    Data.close();
  }
  
  public void put(String name, String account) {
    L1Characte cha = new L1Characte();
    cha.setName(name);
    cha.setAccount(account);
    this._list.put(String.valueOf(name), cha);
  }
  
  public void remove(String name) {
    if (this._list.containsKey(name))
      this._list.remove(name); 
  }
  
  public Collection<L1Characte> getList() {
    return this._list.values();
  }
  
  public void CharacterSave(PcInstance pc) {
    Connection con = null;
    PreparedStatement st = null;
    try {
      StringBuffer sb = new StringBuffer();
      sb.append(pc.getLvStr());
      sb.append(" ");
      sb.append(pc.getLvDex());
      sb.append(" ");
      sb.append(pc.getLvCon());
      sb.append(" ");
      sb.append(pc.getLvWis());
      sb.append(" ");
      sb.append(pc.getLvInt());
      sb.append(" ");
      sb.append(pc.getLvCha());
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("UPDATE characters SET level=?, nowHp=?, maxHp=?, nowMp=?, maxMp=?, ac=?, exp=?, locX=?, locY=?, locMap=?, title=?, food=?, gfx=?, lawful=?, gfx_mode=?, clanID=?, clanNAME=?, pkcount=?, pkTime=?, global_chating=?, trade_chating=?, whisper_chating=?, totem=?, lvStat=?, elf_attr=? WHERE objID=?");
      st.setInt(1, pc.getLevel());
      st.setInt(2, pc.getCurrentHp());
      st.setInt(3, pc.getMaxHp());
      st.setInt(4, pc.getCurrentMp());
      st.setInt(5, pc.getMaxMp());
      st.setInt(6, pc.getAc());
      st.setLong(7, pc.getExp());
      st.setInt(8, pc.getX());
      st.setInt(9, pc.getY());
      st.setInt(10, pc.getMap());
      st.setString(11, pc.getTitle());
      st.setInt(12, pc.getFood());
      st.setInt(13, pc.getGfx());
      st.setInt(14, pc.getLawful());
      st.setInt(15, pc.getGfxMode());
      st.setInt(16, pc.getClanId());
      st.setString(17, pc.getClanName());
      st.setInt(18, pc.getPkCount());
      if (pc.getPkTime() == 0L) {
        st.setString(19, "0000-00-00 00:00:00");
      } else {
        st.setTimestamp(19, new Timestamp(pc.getPkTime()));
      } 
      st.setInt(20, pc.isGlobalChat() ? 1 : 0);
      st.setInt(21, pc.isTradeChat() ? 1 : 0);
      st.setInt(22, pc.isWhisperChat() ? 1 : 0);
      st.setInt(23, pc.isTotem() ? 1 : 0);
      st.setString(24, sb.toString());
      st.setInt(25, pc.getElfAttr());
      st.setInt(26, pc.getObjectId());
      st.executeUpdate();
    } catch (Exception localException) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st);
    } 
  }
  
  public void CharacterWorldJoin(LineageClient lc, String name) {
    String lcIp = lc.getIP();
    if (lcIp == null) {
      lc.close();
      return;
    } 
    if (lc.getSecurityVerification() < 2) {
      if (!BanListTable.getInstance().isBanList(lcIp)) {
        GmCommand.getInstance().BanTable("1", lcIp);
        this.log.info(String.format("(假人登?攻?)非法(未?建角色即?入游??)IP[%s] ??[%s] 角色[%s] 封?IP ", new Object[] { lcIp, lc.getID(), name }));
      } 
      lc.close();
      return;
    } 
    lc.setWorldJoinCount(lc.getWorldJoinCount() + 1);
    if (lc.getWorldJoinCount() > 1) {
      this.log.info(String.format("(假人登?攻?)非法IP[%s] ??[%s] 角色[%s] 登?游? ", new Object[] { lcIp, lc.getID(), name }));
      if (!BanListTable.getInstance().isBanList(lcIp)) {
        GmCommand.getInstance().BanTable("1", lcIp);
        this.log.info(String.format("(假人登?攻?)非法IP[%s] ??[%s] 角色[%s] 封?IP ", new Object[] { lcIp, lc.getID(), name }));
      } 
      lc.close();
      return;
    } 
    if (nameCheck(name, lc.getID()) && WorldInstance.getInstance().getPc(name) == null) {
      Connection con = null;
      PreparedStatement st = null;
      ResultSet rs = null;
      try {
        con = DatabaseConnection.getInstance().getConnection();
        st = con.prepareStatement("SELECT * FROM characters WHERE name=? AND block_date='0000-00-00 00:00:00'");
        st.setString(1, name);
        rs = st.executeQuery();
        if (rs.next()) {
          PcInstance pc = new PcInstance(lc, rs);
          if (pc.isGm()) {
            pc.setSpeed(true);
            pc.setBrave(true);
            pc.setStatus(pc.getStatus() + 16);
          } 
          pc.SendPacket((S_BasePacket)new S_WorldJoin());
          pc.getInventory().sendList();
          pc.getBooks().sendList();
          pc.getSkill().sendList();
          pc.SendPacket((S_BasePacket)new S_CharacterStat((Character)pc));
          GetBackRestartTable gbrTable = GetBackRestartTable.getInstance();
          L1GetBackRestart[] gbrList = gbrTable.getGetBackRestartTableList();
          for (L1GetBackRestart gbr : gbrList) {
            if (pc.getHomeMap() == gbr.getArea()) {
              pc.setHomeX(gbr.getLocX());
              pc.setHomeY(gbr.getLocY());
              pc.setHomeMap(gbr.getMapId());
              break;
            } 
          } 
          pc.toTeleport(pc.getHomeX(), pc.getHomeY(), pc.getHomeMap());
          pc.getInventory().JoinWorld();
          pc.getBuff().read();
          lc.SendPacket((S_BasePacket)new S_WorldStatPacket(0));
          ClanSystem.getInstance().ClanKingdomStatus(pc);
          pc.LvStat(true);
          pc.setAutoPickup(Config.AUTO_PICKUP);
          isLetterInventory(pc);
          ClanSystem.getInstance().worldIn(pc);
          if (pc.getClanId() != 0 && pc.getClassType() == 0)
            WarClanUpdate(pc.getClanId()); 
          pc.SendPacket((S_BasePacket)new S_ObjectChatting(null, "", 20));
          if (Config.EVENT_POLYSCROLL)
            pc.SendPacket((S_BasePacket)new S_ObjectChatting(null, "變身活動開始啦，可以變死騎了。所有變身沒有等級限制！", 20)); 
          this.log.info(String.format("IP[%s] ??[%s] 角色[%s] ?前等?{%s} ??{%s} 登?游? ", new Object[] { lcIp, lc.getID(), name, Integer.valueOf(pc.getLevel()), Long.valueOf(pc.getExp()) }));
        } else {
          lc.close();
        } 
      } catch (Exception e) {
        this.log.error(e.getLocalizedMessage(), e);
      } finally {
        DatabaseConnection.getInstance().close(con, st, rs);
      } 
    } else {
      lc.close();
    } 
  }
  
  private void WarClanUpdate(int clanId) {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE clan_list SET WarClan='");
    sb.append("");
    sb.append("' WHERE ClanId='");
    sb.append(clanId);
    sb.append("'");
    DatabaseConnection.getInstance().query_update(sb.toString());
  }
  
  public boolean elfAttrUpdate(PcInstance pc, int attr) {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE characters SET elf_attr='");
    sb.append(attr);
    sb.append("' WHERE objID='");
    sb.append(pc.getObjectId());
    sb.append("'");
    return DatabaseConnection.getInstance().query_update(sb.toString());
  }
  
  private void isLetterInventory(PcInstance pc) {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    boolean finder = false;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM characters_letter WHERE paperInventory='0' AND paperTo=?");
      st.setString(1, pc.getName());
      rs = st.executeQuery();
      while (rs.next()) {
        Letter letter = (Letter)ItemsTable.getInstance().newItem(341, false, true);
        letter.setLetterUid(rs.getInt("uid"));
        letter.setSenderName(rs.getString("paperFrom"));
        letter.setSubject(rs.getString("paperSubject"));
        pc.getInventory().add((ItemInstance)letter);
        letter.updateInventory(letter.getLetterUid());
        finder = true;
      } 
      if (finder) {
        pc.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)pc, 1091), true);
        pc.SendPacket((S_BasePacket)new S_ServerMessage(428));
      } 
    } catch (Exception localException) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  public void CharacterDelete(LineageClient lc, String name) {
    if (nameCheck(name, lc.getID())) {
      int obj_id = getCharacterObjectId(name);
      int clan_id = getCharacterClanId(name);
      if (obj_id > 0) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM characters WHERE objID='");
        sb.append(obj_id);
        sb.append("'");
        DatabaseConnection.getInstance().query_delete(sb.toString());
        sb = new StringBuilder();
        sb.append("DELETE FROM characters_books WHERE char_id='");
        sb.append(obj_id);
        sb.append("'");
        DatabaseConnection.getInstance().query_delete(sb.toString());
        sb = new StringBuilder();
        sb.append("DELETE FROM characters_buffs WHERE char_id='");
        sb.append(obj_id);
        sb.append("'");
        DatabaseConnection.getInstance().query_delete(sb.toString());
        sb = new StringBuilder();
        sb.append("DELETE FROM characters_inventory WHERE char_id='");
        sb.append(obj_id);
        sb.append("'");
        DatabaseConnection.getInstance().query_delete(sb.toString());
        sb = new StringBuilder();
        sb.append("DELETE FROM characters_skills WHERE char_id='");
        sb.append(obj_id);
        sb.append("'");
        DatabaseConnection.getInstance().query_delete(sb.toString());
        if (clan_id > 0)
          ClanSystem.getInstance().ClanKin(name, clan_id); 
        remove(name);
      } 
      lc.SendPacket((S_BasePacket)new S_CharacterRemove(5));
    } else {
      lc.close();
    } 
  }
  
  public int getCharacterObjectId(String name) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT objID FROM characters WHERE name='");
    sb.append(name);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select_count(sb.toString());
  }
  
  public int getCharacterName(int id) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT name FROM characters WHERE objID='");
    sb.append(id);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select_count(sb.toString());
  }
  
  private int getCharacterClanId(String name) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT clanID FROM characters WHERE name='");
    sb.append(name);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select_count(sb.toString());
  }
  
  private boolean nameCheck(String name, String id) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * FROM characters WHERE name='");
    sb.append(name);
    sb.append("' AND account='");
    sb.append(id);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select(sb.toString());
  }
  
  public int getCharacterCount(int uid) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT COUNT(*) FROM characters WHERE account_uid='");
    sb.append(uid);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select_count(sb.toString());
  }
  
  public void CharacterList(LineageClient lc) {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM characters WHERE account_uid=?");
      st.setInt(1, lc.getUID());
      rs = st.executeQuery();
      while (rs.next())
        lc.SendPacket((S_BasePacket)new S_CharacterInfo(rs)); 
    } catch (Exception localException) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  public void InsertCharacter(LineageClient lc, String name, int type, int sex) {
    if (SelectCharacterName(name)) {
      lc.SendPacket((S_BasePacket)new S_LoginFail(6));
      return;
    } 
    if (!filter(name)) {
      lc.SendPacket((S_BasePacket)new S_LoginFail(9));
      return;
    } 
    if (lc.getStat().getStat() != 75) {
      lc.SendPacket((S_BasePacket)new S_LoginFail(21));
      return;
    } 
    if (lc.getStat().getType() != type) {
      lc.SendPacket((S_BasePacket)new S_LoginFail(23));
      return;
    } 
    int obj_id = Config.getObjectID();
    StringBuilder sb = new StringBuilder();
    sb.append("INSERT INTO characters SET name='");
    sb.append(name);
    sb.append("', account='");
    sb.append(lc.getID());
    sb.append("', account_uid='");
    sb.append(lc.getUID());
    sb.append("', objID='");
    sb.append(obj_id);
    sb.append("', nowHP='");
    sb.append(lc.getStat().getHp());
    sb.append("', maxHP='");
    sb.append(lc.getStat().getHp());
    sb.append("', nowMP='");
    sb.append(lc.getStat().getMp());
    sb.append("', maxMP='");
    sb.append(lc.getStat().getMp());
    sb.append("', str='");
    sb.append(lc.getStat().getStr());
    sb.append("', con='");
    sb.append(lc.getStat().getCon());
    sb.append("', dex='");
    sb.append(lc.getStat().getDex());
    sb.append("', wis='");
    sb.append(lc.getStat().getWis());
    sb.append("', inter='");
    sb.append(lc.getStat().getInt());
    sb.append("', cha='");
    sb.append(lc.getStat().getCha());
    sb.append("', sex='");
    sb.append(sex);
    sb.append("', class='");
    sb.append(type);
    sb.append("', locX='");
    sb.append(lc.getStat().getX());
    sb.append("', locY='");
    sb.append(lc.getStat().getY());
    sb.append("', locMAP='");
    sb.append(lc.getStat().getMap());
    sb.append("', gfx='");
    sb.append((sex == 0) ? lc.getStat().getMale() : lc.getStat().getFemale());
    sb.append("'");
    DatabaseConnection.getInstance().query_insert(sb.toString());
    Beginner.getInstance().giveItem(obj_id, type);
    if (3 == type)
      addWizardMagic(lc, obj_id); 
    lc.SendPacket((S_BasePacket)new S_LoginFail(2));
    lc.SendPacket((S_BasePacket)new S_CharacterAdd(name, null, type, sex, 65536, lc.getStat().getHp(), lc.getStat().getMp(), 0, 1, lc.getStat().getStr(), lc.getStat().getDex(), lc.getStat().getCon(), lc.getStat().getWis(), lc.getStat().getCha(), lc.getStat().getInt()));
    put(name, lc.getID());
  }
  
  private void addWizardMagic(LineageClient lc, int obj_id) {
    Connection con = null;
    PreparedStatement pstm = null;
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("DELETE FROM characters_skills WHERE char_id='");
      sb.append(obj_id);
      sb.append("'");
      DatabaseConnection.getInstance().query_delete(sb.toString());
      con = DatabaseConnection.getInstance().getConnection();
      pstm = con.prepareStatement(String.format("INSERT INTO characters_skills SET %s, %s, %s ", new Object[] { "char_id=?", "skill_id=?", "skill_name=?" }));
      pstm.setInt(1, obj_id);
      pstm.setInt(2, 4);
      pstm.setString(3, "光箭");
      pstm.execute();
    } catch (Exception e) {
      e.printStackTrace();
      this.log.error(e.getLocalizedMessage(), e);
    } finally {
      DatabaseConnection.getInstance().close(con, pstm);
    } 
  }
  
  private void addInventoryItem(LineageClient lc, int charId) {
    Connection con = null;
    PreparedStatement pstm = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      pstm = con.prepareStatement(String.format("INSERT INTO characters_inventory SET %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s ", new Object[] { 
              "uid=?", "char_id=?", "pet_id=?", "letter_id=?", "item_id=?", "count=?", "have_count=?", "en=?", "equipped=?", "definite=?", 
              "bless=?", "durability=?", "time=?", "slimerace_uid=?", "silmerace_idx=?", "silmerace_name=?" }));
      for (ItemInstance item : Config.beginnerItems) {
        pstm.setInt(1, Config.getObjectID_ETC());
        pstm.setInt(2, charId);
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
        if (item instanceof net.world.instance.inventory.function.SlimeRaceTicket) {
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
    } catch (Exception e) {
      e.printStackTrace();
      this.log.error(e.getLocalizedMessage(), e);
    } finally {
      DatabaseConnection.getInstance().close(con, pstm);
    } 
  }
  
  private boolean filter(String name) {
    if (name.length() > 16)
      return false; 
    return !BadNamesList.getInstance().isBadName(name);
  }
  
  private boolean SelectCharacterName(String name) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * FROM characters WHERE name='");
    sb.append(name);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select(sb.toString());
  }
}
