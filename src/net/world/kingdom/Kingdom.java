package net.world.kingdom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import net.Config;
import net.database.DatabaseConnection;
import net.database.ItemsTable;
import net.database.NpcTable;
import net.database.bean.Npc;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.network.server.S_War;
import net.network.server.S_WorldStatPacket;
import net.world.WorldInstance;
import net.world.ai.NpcAi;
import net.world.function.ClanSystem;
import net.world.function.bean.Clan;
import net.world.instance.ItemInstance;
import net.world.instance.NpcInstance;
import net.world.instance.PcInstance;
import net.world.instance.inventory.function.ScrollLabeledVERRYEDHORAE;
import net.world.kingdom.function.AbyssGuard;
import net.world.kingdom.function.CastleTop;
import net.world.kingdom.function.DoorKingdom;
import net.world.kingdom.function.Doorman;
import net.world.kingdom.function.KentBowGuard;
import net.world.kingdom.function.KentGuard;
import net.world.kingdom.function.KingdomGuard;
import net.world.kingdom.function.WindawoodGuard;
import net.world.npc.Guard;
import net.world.npc.Ishmael;
import net.world.object.Character;
import net.world.object.L1Object;

public class Kingdom {
  private int uid;
  
  private String name;
  
  private int HomeX;
  
  private int HomeY;
  
  private int HomeMap;
  
  private int clanID;
  
  private String clanNAME;
  
  private int agentID;
  
  private String agentNAME;
  
  private int tax;
  
  private long taxTotal;
  
  private long taxDay;
  
  private boolean war;
  
  private long warDay;
  
  private long warDayLast;
  
  private int flagX1;
  
  private int flagX2;
  
  private int flagY1;
  
  private int flagY2;
  
  private int flagMAP;
  
  private List<L1Object> _flag;
  
  private List<CastleTop> _top;
  
  private List<Guard> _guard;
  
  private List<DoorKingdom> _door;
  
  protected int inMap;
  
  private long[] warTime;
  
  public Kingdom(int uid) {
    this.uid = uid;
    this.warTime = new long[6];
    this._flag = new ArrayList<L1Object>();
    this._top = new ArrayList<CastleTop>();
    this._guard = new ArrayList<Guard>();
    this._door = new ArrayList<DoorKingdom>();
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM kingdom WHERE id=?");
      st.setInt(1, uid);
      rs = st.executeQuery();
      if (rs.next()) {
        this.name = rs.getString("name");
        StringTokenizer stt = new StringTokenizer(rs.getString("loc"));
        this.HomeX = Integer.valueOf(stt.nextToken()).intValue();
        this.HomeY = Integer.valueOf(stt.nextToken()).intValue();
        this.HomeMap = Integer.valueOf(stt.nextToken()).intValue();
        this.clanID = rs.getInt("clan_id");
        this.clanNAME = rs.getString("clan_name");
        this.agentID = rs.getInt("agent_id");
        this.agentNAME = rs.getString("agent_name");
        this.tax = rs.getInt("tax");
        this.taxTotal = rs.getLong("tax_total");
        this.taxDay = rs.getTimestamp("tax_day").getTime();
        this.war = "true".equalsIgnoreCase(rs.getString("war"));
        this.warDay = rs.getTimestamp("war_day").getTime();
        this.warDayLast = rs.getTimestamp("war_day_last").getTime();
        if (this.clanID > 0)
          ClanSystem.getInstance().getClan(this.clanID).setKingdom(this); 
      } 
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  public boolean isWar() {
    return this.war;
  }
  
  public void setWar(boolean war) {
    this.war = war;
  }
  
  public int getUid() {
    return this.uid;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public int getHomeX() {
    return this.HomeX;
  }
  
  public void setHomeX(int homeX) {
    this.HomeX = homeX;
  }
  
  public int getHomeY() {
    return this.HomeY;
  }
  
  public void setHomeY(int homeY) {
    this.HomeY = homeY;
  }
  
  public int getHomeMap() {
    return this.HomeMap;
  }
  
  public void setHomeMap(int homeMap) {
    this.HomeMap = homeMap;
  }
  
  public int getClanID() {
    return this.clanID;
  }
  
  public void setClanID(int clanID) {
    this.clanID = clanID;
  }
  
  public String getClanNAME() {
    return this.clanNAME;
  }
  
  public void setClanNAME(String clanNAME) {
    this.clanNAME = clanNAME;
  }
  
  public int getAgentID() {
    return this.agentID;
  }
  
  public void setAgentID(int agentID) {
    this.agentID = agentID;
  }
  
  public String getAgentNAME() {
    return this.agentNAME;
  }
  
  public void setAgentNAME(String agentNAME) {
    this.agentNAME = agentNAME;
  }
  
  public int getTax() {
    return this.tax;
  }
  
  public void setTax(int tax) {
    this.tax = tax;
  }
  
  public long getTaxTotal() {
    return this.taxTotal;
  }
  
  public void setTaxTotal(long taxTotal) {
    this.taxTotal = taxTotal;
  }
  
  public long getTaxDay() {
    return this.taxDay;
  }
  
  public void setTaxDay(long taxDay) {
    this.taxDay = taxDay;
  }
  
  public long getWarDay() {
    return this.warDay;
  }
  
  public void setWarDay(long warDay) {
    this.warDay = warDay;
  }
  
  public long getWarDayLast() {
    return this.warDayLast;
  }
  
  public void setWarDayLast(long warDayLast) {
    this.warDayLast = warDayLast;
  }
  
  public void setUid(int uid) {
    this.uid = uid;
  }
  
  public int getInMap() {
    return this.inMap;
  }
  
  public void isResetDoor(PcInstance pc, boolean SideType) {
    if (!isWar()) {
      for (DoorKingdom door : this._door) {
        if (door.isSideType() == SideType) {
          int hp = 100 - door.getHp();
          int aden = hp * 1000;
          if (aden <= getTaxTotal()) {
            door.toRevival(null);
            setTaxTotal(getTaxTotal() - aden);
            updateDB();
            continue;
          } 
          pc.Message("성문을 수리하기위한 공금이 부족합니다..");
          return;
        } 
      } 
      pc.SendPacket((S_BasePacket)new S_ServerMessage(464, SideType ? "내성문" : "외성문"));
    } else {
      pc.Message("전쟁중에는 성문을 수리할수 없습니다.");
    } 
  }
  
  public List<String> getDoorStatus() {
    StringBuffer sb = null;
    List<String> list = new ArrayList<String>();
    for (DoorKingdom door : this._door) {
      sb = new StringBuffer();
      if (door.isSideType()) {
        sb.append("내성문 : hp(");
      } else {
        sb.append("7시 외성문 : hp(");
      } 
      sb.append(door.getHp());
      sb.append("%)");
      list.add(sb.toString());
    } 
    return list;
  }
  
  public void DoorOpenClose(boolean open, boolean side) {
    for (DoorKingdom door : this._door) {
      if (side == door.isSideType())
        door.OpenClose(open); 
    } 
  }
  
  public void toAttack(L1Object target, int type) {
    if (target.getClanId() == 0 || getClanID() != target.getClanId())
      for (Guard g : this._guard)
        g.addAttackList(target);  
  }
  
  public void gotoKingdom(PcInstance pc) {
    pc.setTempX(this.HomeX);
    pc.setTempY(this.HomeY);
    pc.setTempMap(this.HomeMap);
  }
  
  public void WarEnd() {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM clan_list WHERE WarClan=?");
      st.setString(1, getClanNAME());
      rs = st.executeQuery();
      while (rs.next())
        WorldInstance.getInstance().SendPacket((S_BasePacket)new S_War(3, rs.getString("ClanName"), getClanNAME())); 
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  public void ClearWarClan() {
    Connection con = null;
    PreparedStatement st = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("UPDATE clan_list SET WarClan='' WHERE WarClan=?");
      st.setString(1, getClanNAME());
      st.execute();
    } catch (Exception localException) {
      try {
        st.close();
      } catch (Exception exception) {}
      try {
        con.close();
      } catch (Exception exception) {}
    } finally {
      try {
        st.close();
      } catch (Exception exception) {}
      try {
        con.close();
      } catch (Exception exception) {}
    } 
  }
  
  public void Teleport() {
    byte b;
    int i;
    PcInstance[] arrayOfPcInstance;
    for (i = (arrayOfPcInstance = WorldInstance.getInstance().getPc()).length, b = 0; b < i; ) {
      PcInstance pcInstance = arrayOfPcInstance[b];
      if (pcInstance.getClanId() != getClanID() && isKingdomZone((L1Object)pcInstance)) {
        ScrollLabeledVERRYEDHORAE.Location((L1Object)pcInstance);
        pcInstance.toTeleport(pcInstance.getTempX(), pcInstance.getTempY(), pcInstance.getTempMap());
      } 
      b++;
    } 
  }
  
  public void restoreTop() {
    for (CastleTop top : this._top)
      top.toRevival(null); 
  }
  
  public void start() {
    setWar(true);
    Flag(true);
    WorldInstance.getInstance().Message("\\fR" + getName());
    WorldInstance.getInstance().SendPacket((S_BasePacket)new S_War(0, getUid()));
    StartWarPacket();
    Teleport();
  }
  
  public void stop() {
    setWar(false);
    Flag(false);
    WorldInstance.getInstance().Message("\\fR" + getName());
    WorldInstance.getInstance().SendPacket((S_BasePacket)new S_War(1, getUid()));
    WarEnd();
    ClearWarClan();
    setWarDay(0L);
    setWarDayLast(System.currentTimeMillis());
    updateDB();
    SendPacket((S_BasePacket)new S_War(4, getUid()));
    Teleport();
    restoreTop();
  }
  
  public void SendPacket(S_BasePacket bp) {
    Clan c = ClanSystem.getInstance().getClan(getClanID());
    if (c != null)
      c.SendPacket(bp); 
  }
  
  public boolean isWarTimeSetting() {
    return !(getWarDayLast() != 0L && getWarDay() != 0L);
  }
  
  public void setWarTimeSetting(PcInstance pc, int idx) {
    if (pc.getClanId() == getClanID() && pc.getClassType() == 0 && 
      isWarTimeSetting()) {
      setWarDay(get_warTime(idx).longValue());
      updateDB();
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(get_warTime(idx).longValue());
      int year = cal.get(1);
      int month = cal.get(2) + 1;
      int date = cal.get(5);
      StringBuffer sb = new StringBuffer();
      sb.append(month);
      sb.append("/");
      sb.append(date);
      sb.append("/");
      sb.append(year);
      pc.SendPacket((S_BasePacket)new S_ServerMessage(304, sb.toString()));
    } 
  }
  
  public void set_warTime(int idx, long time) {
    this.warTime[idx] = time;
  }
  
  public Long get_warTime(int idx) {
    return Long.valueOf(this.warTime[idx]);
  }
  
  public boolean isKingdomZone(L1Object o) {
    if (this.flagX1 <= o.getX() && this.flagX2 >= o.getX() && this.flagY1 <= o.getY() && this.flagY2 >= o.getY() && this.flagMAP == o.getMap())
      return true; 
    if (this.inMap != 4 && this.inMap == o.getMap())
      return true; 
    return false;
  }
  
  public void addTax(PcInstance pc, long count) {
    if (count > 0L && 2147483647L >= count && pc.getInventory().Aden(count, true)) {
      setTaxTotal(getTaxTotal() + count);
      updateDB();
    } 
  }
  
  public void removeTax(PcInstance pc, long count) {
    if (count > 0L && 2147483647L >= count && getTaxTotal() >= count) {
      setTaxTotal(getTaxTotal() - count);
      updateDB();
      ItemInstance aden = pc.getInventory().getAden();
      if (aden == null) {
        aden = ItemsTable.getInstance().newItem(5, false, true);
        aden.setCount(count);
        pc.getInventory().add(aden);
      } else {
        aden.setCount((Character)pc, aden.getCount() + count);
      } 
    } 
  }
  
  public void update(L1Object cha, boolean flag) {
    if (!flag)
      ClanSystem.getInstance().getClan(getClanID()).setKingdom(null); 
    setClanID(cha.getClanId());
    setClanNAME(cha.getClanName());
    setAgentID(cha.getObjectId());
    setAgentNAME(cha.getName());
    ClanSystem.getInstance().getClan(getClanID()).setKingdom(this);
    for (CastleTop t : this._top) {
      t.setClanId(cha.getClanId());
      t.setClanName(cha.getClanName());
    } 
    for (Guard g : this._guard) {
      g.setClanId(cha.getClanId());
      g.setClanName(cha.getClanName());
      g.clearFightList();
    } 
    for (DoorKingdom d : this._door) {
      d.setClanId(cha.getClanId());
      d.setClanName(cha.getClanName());
    } 
    WorldInstance.getInstance().SendPacket((S_BasePacket)new S_WorldStatPacket(getUid(), getAgentID()));
  }
  
  public void updateDB() {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE kingdom SET clan_id='");
    sb.append(getClanID());
    sb.append("', clan_name='");
    sb.append(getClanNAME());
    sb.append("', agent_id='");
    sb.append(getAgentID());
    sb.append("', agent_name='");
    sb.append(getAgentNAME());
    sb.append("', tax='");
    sb.append(getTax());
    sb.append("', tax_total='");
    sb.append(getTaxTotal());
    sb.append("', tax_day='");
    sb.append(new Timestamp(getTaxDay()));
    sb.append("', war='");
    sb.append(isWar());
    sb.append("', war_day='");
    sb.append(new Timestamp(getWarDay()));
    sb.append("', war_day_last='");
    sb.append(new Timestamp(getWarDayLast()));
    sb.append("' WHERE id='");
    sb.append(getUid());
    sb.append("'");
    DatabaseConnection.getInstance().query_update(sb.toString());
  }
  
  protected void Flag(int x1, int x2, int y1, int y2, int map) {
    this.flagX1 = x1;
    this.flagX2 = x2;
    this.flagY1 = y1;
    this.flagY2 = y2;
    this.flagMAP = map;
    int x = 0;
    int y = 0;
    x = x1;
    for (y = y1; x <= x2; x += 8)
      this._flag.add(Flag(x, y, map)); 
    x = x2;
    for (y = y1; y <= y2; y += 8)
      this._flag.add(Flag(x, y, map)); 
    x = x2;
    for (y = y2; x >= x1; x -= 8)
      this._flag.add(Flag(x, y, map)); 
    x = x1;
    for (y = y2; y >= y1; y -= 8)
      this._flag.add(Flag(x, y, map)); 
  }
  
  private L1Object Flag(int x, int y, int map) {
    L1Object flag = new L1Object();
    flag.setObjectId(Config.getObjectID_ETC());
    flag.setGfx(1284);
    flag.setX(x);
    flag.setY(y);
    flag.setMap(map);
    return flag;
  }
  
  private void Flag(boolean visual) {
    if (visual) {
      for (L1Object flag : this._flag)
        flag.toTeleport(flag.getX(), flag.getY(), flag.getMap()); 
    } else {
      for (L1Object flag : this._flag)
        flag.toDelete(); 
    } 
  }
  
  protected void addIshmael(int dbID, int x, int y, int map, int heading, String html) {
    Npc n = NpcTable.getInstance().getNpcTemplate(dbID);
    if (n != null) {
      Ishmael i = new Ishmael(this, html);
      i.setObjectId(Config.getObjectID_ETC());
      i.setLawful(65536);
      i.setClassGfx(n.get_gfxid());
      i.setClassGfxMode(n.get_gfxMode());
      i.setGfx(n.get_gfxid());
      i.setGfxMode(n.get_gfxMode());
      i.setName(n.get_nameid());
      i.setX(x);
      i.setY(y);
      i.setMap(map);
      i.setHeading(heading);
      i.toTeleport(x, y, map);
    } 
  }
  
  protected void addCastleTop(int dbID, int x, int y, int map) {
    Npc n = NpcTable.getInstance().getNpcTemplate(dbID);
    if (n != null) {
      CastleTop top = new CastleTop(this, n);
      top.setObjectId(Config.getObjectID_ETC());
      top.setClanName(getClanNAME());
      top.setClanId(getClanID());
      top.setLawful(65536);
      top.setX(x);
      top.setY(y);
      top.setMap(map);
      top.toTeleport(x, y, map);
      this._top.add(top);
    } 
  }
  
  protected void addDoor(int dbID, int x, int y, int map, int heading, boolean side) {
    Npc n = NpcTable.getInstance().getNpcTemplate(dbID);
    if (n != null) {
      DoorKingdom door = new DoorKingdom(this, dbID);
      door.setSideType(side);
      door.setObjectId(Config.getObjectID_ETC());
      door.setClanName(getClanNAME());
      door.setClanId(getClanID());
      door.setClassGfx(n.get_gfxid());
      door.setClassGfxMode(n.get_gfxMode());
      door.setGfx(n.get_gfxid());
      door.setGfxMode(n.get_gfxMode());
      door.setName(n.get_nameid());
      door.setMaxHp(n.getHp());
      door.setCurrentHp(door.getTotalHp());
      door.setX(x);
      door.setY(y);
      door.setMap(map);
      door.setHeading(heading);
      door.toTeleport(door.getX(), door.getY(), door.getMap());
      this._door.add(door);
    } 
  }
  
  protected void addDoorman(int dbID, int x, int y, int map, int heading, boolean SideType) {
    Npc n = NpcTable.getInstance().getNpcTemplate(dbID);
    if (n != null) {
      Doorman d = new Doorman(this, dbID);
      d.setObjectId(Config.getObjectID_ETC());
      d.setClassGfx(n.get_gfxid());
      d.setGfx(n.get_gfxid());
      d.setGfxMode(n.get_gfxMode());
      d.setName(n.get_nameid());
      d.setX(x);
      d.setY(y);
      d.setMap(map);
      d.setHeading(heading);
      d.setSideType(SideType);
      d.toTeleport(d.getX(), d.getY(), d.getMap());
    } 
  }
  
  protected void addGuard(int dbID, int x, int y, int map, int heading) {
    Npc n = NpcTable.getInstance().getNpcTemplate(dbID);
    if (n != null) {
      KentGuard kentGuard;
      WindawoodGuard windawoodGuard;
      AbyssGuard abyssGuard;
      KingdomGuard kingdomGuard1 = null, g = null;
      switch (getUid()) {
        case 1:
          if (dbID == 2) {
            KentBowGuard kentBowGuard = new KentBowGuard(this, n);
            break;
          } 
          kentGuard = new KentGuard(this, n);
          break;
        case 3:
          windawoodGuard = new WindawoodGuard(this, n);
          break;
        case 6:
          abyssGuard = new AbyssGuard(this, n);
          break;
        default:
          kingdomGuard1 = new KingdomGuard(this, n);
          break;
      } 
      kingdomGuard1.setObjectId(Config.getObjectID_ETC());
      kingdomGuard1.setClanName(getClanNAME());
      kingdomGuard1.setClanId(getClanID());
      kingdomGuard1.setClassGfx(n.get_gfxid());
      kingdomGuard1.setClassGfxMode(n.get_gfxMode());
      kingdomGuard1.setGfx(n.get_gfxid());
      kingdomGuard1.setGfxMode(n.get_gfxMode());
      kingdomGuard1.setMaxHp(n.getHp());
      kingdomGuard1.setCurrentHp(kingdomGuard1.getTotalHp());
      kingdomGuard1.setName(n.get_nameid());
      kingdomGuard1.setHomeX(x);
      kingdomGuard1.setHomeY(y);
      kingdomGuard1.setHomeMap(map);
      kingdomGuard1.setHomeHeading(heading);
      kingdomGuard1.setX(x);
      kingdomGuard1.setY(y);
      kingdomGuard1.setMap(map);
      kingdomGuard1.setHeading(heading);
      kingdomGuard1.setLight(n.get_light());
      kingdomGuard1.toTeleport(kingdomGuard1.getX(), kingdomGuard1.getY(), kingdomGuard1.getMap());
      NpcAi.getInstance().addNpc((NpcInstance)kingdomGuard1);
      this._guard.add(kingdomGuard1);
    } 
  }
  
  private void StartWarPacket() {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM clan_list WHERE WarClan=?");
      st.setString(1, getClanNAME());
      rs = st.executeQuery();
      while (rs.next()) {
        if (!getClanNAME().equalsIgnoreCase(null) && !"".equalsIgnoreCase(getClanNAME()))
          WorldInstance.getInstance().SendPacket((S_BasePacket)new S_War(1, rs.getString("ClanName"), getClanNAME())); 
      } 
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(this.uid);
    sb.append(", ");
    sb.append(this.name);
    sb.append(", ");
    sb.append(this.clanID);
    sb.append(", ");
    sb.append(this.clanNAME);
    sb.append(", ");
    sb.append(this.agentID);
    sb.append(", ");
    sb.append(this.agentNAME);
    sb.append(", ");
    sb.append(this.tax);
    sb.append(", ");
    sb.append(this.taxTotal);
    sb.append(", ");
    sb.append(this.taxDay);
    sb.append(", ");
    sb.append(this.warDay);
    sb.append(", ");
    sb.append(this.warDayLast);
    return sb.toString();
  }
}
