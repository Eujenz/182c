package net.world.function;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import net.Config;
import net.database.DatabaseConnection;
import net.network.server.S_BasePacket;
import net.network.server.S_Clan;
import net.network.server.S_ObjectTitle;
import net.network.server.S_ServerMessage;
import net.network.server.S_ServerMessageYesNo;
import net.network.server.S_War;
import net.network.server.S_WorldStatPacket;
import net.world.WorldInstance;
import net.world.function.bean.Clan;
import net.world.instance.PcInstance;
import net.world.kingdom.Kingdom;
import net.world.kingdom.KingdomAbyss;
import net.world.kingdom.KingdomGiran;
import net.world.kingdom.KingdomHeine;
import net.world.kingdom.KingdomKent;
import net.world.kingdom.KingdomOrcish;
import net.world.kingdom.KingdomWindawood;
import net.world.object.L1Object;

public class ClanSystem {
  private Map<Integer, Clan> list;
  
  private static class Holder {
    static ClanSystem instance = new ClanSystem();
  }
  
  public static ClanSystem getInstance() {
    return Holder.instance;
  }
  
  private ClanSystem() {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    this.list = new HashMap<Integer, Clan>();
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM clan_list");
      rs = st.executeQuery();
      while (rs.next()) {
        Clan c = new Clan();
        c.set_id(rs.getInt(1));
        c.set_name(rs.getString(2));
        c.set_lordname(rs.getString(3));
        c.set_icon(psjump(rs.getString(4)));
        c.set_list(rs.getString(5));
        this.list.put(Integer.valueOf(c.get_id()), c);
      } 
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  public Kingdom isKingdomZone(L1Object o) {
    for (int i = 1; i < 7; i++) {
      Kingdom k = getInstance().getKingdom(i);
      if (k != null && k.isKingdomZone(o))
        return k; 
    } 
    return null;
  }
  
  public Kingdom getKingdom(int type) {
    switch (type) {
      case 1:
        return (Kingdom)KingdomKent.getInstance();
      case 2:
        return (Kingdom)KingdomOrcish.getInstance();
      case 3:
        return (Kingdom)KingdomWindawood.getInstance();
      case 4:
        return (Kingdom)KingdomGiran.getInstance();
      case 5:
        return (Kingdom)KingdomHeine.getInstance();
      case 6:
        return (Kingdom)KingdomAbyss.getInstance();
    } 
    return null;
  }
  
  public Kingdom getKingdom(PcInstance pc) {
    Clan c = getClan(pc.getClanId());
    if (c != null)
      return c.getKingdom(); 
    return null;
  }
  
  public void ClanKingdomStatus(PcInstance pc) {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM kingdom");
      rs = st.executeQuery();
      while (rs.next()) {
        pc.SendPacket((S_BasePacket)new S_WorldStatPacket(rs.getInt("id"), rs.getInt("agent_id")));
        if (getKingdom(rs.getInt("id")).isWar()) {
          pc.Message("\\fR" + rs.getString("name"));
          pc.SendPacket((S_BasePacket)new S_War(2, rs.getInt("id")));
        } 
      } 
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  public void WarRoyalDelete(PcInstance pc) {
    if (pc.getClassType() == 0) {
      Clan cha_c = getClan(pc.getClanId());
      if (cha_c != null) {
        if (cha_c.get_warClan() != null) {
          WarEnd(cha_c, cha_c.get_warClan());
          WarWin(cha_c.get_warClan(), cha_c);
          cha_c.get_warClan().set_warClan(null);
          cha_c.set_warClan(null);
        } 
        String WarClan = WarCheck(cha_c);
        if (WarClan != null) {
          Clan use_c = getClanName(WarClan);
          if (use_c.getKingdom() != null && use_c.getKingdom().isWar()) {
            WarClanUpdate(cha_c, "");
            WorldInstance.getInstance().SendPacket((S_BasePacket)new S_War(3, cha_c.get_name(), WarClan));
          } 
        } 
      } 
    } 
  }
  
  public void WarSubmission(PcInstance pc, String clan_name) {
    Clan cha_c = getClan(pc.getClanId());
    Clan use_c = getClanName(clan_name);
    if (cha_c != null) {
      if (use_c != null && use_c.getKingdom() != null)
        WarClanUpdate(cha_c, ""); 
      if (cha_c.get_warClan() != null)
        cha_c.get_warClan().getRoyal().SendPacket((S_BasePacket)new S_ServerMessageYesNo(221, cha_c.get_name())); 
    } 
  }
  
  public void WarSubmissionFinal(PcInstance pc, boolean yn) {
    Clan cha_c = getClan(pc.getClanId());
    Clan use_c = cha_c.get_warClan();
    if (yn) {
      WarEnd(cha_c, use_c);
      WarSubmission(cha_c, use_c);
      WarWin(cha_c, use_c);
    } else {
      use_c.getRoyal().SendPacket((S_BasePacket)new S_ServerMessage(237, cha_c.get_name()));
    } 
    cha_c.set_warClan(null);
    use_c.set_warClan(null);
  }
  
  private void WarSubmission(Clan win, Clan lose) {
    WorldInstance.getInstance().SendPacket((S_BasePacket)new S_War(2, lose.get_name(), win.get_name()));
  }
  
  private void WarEnd(Clan win, Clan lose) {
    WorldInstance.getInstance().SendPacket((S_BasePacket)new S_War(3, win.get_name(), lose.get_name()));
  }
  
  private void WarWin(Clan win, Clan lose) {
    WorldInstance.getInstance().SendPacket((S_BasePacket)new S_War(4, win.get_name(), lose.get_name()));
  }
  
  public void War(PcInstance pc, String clan_name) {
    Clan cha_c = getClan(pc.getClanId());
    Clan use_c = getClanName(clan_name);
    if (use_c.getKingdom().isWar()) {
      if (cha_c != null && use_c != null && cha_c.get_id() != use_c.get_id()) {
        if (pc.getClassType() == 0) {
          if (cha_c.get_warClan() == null) {
            if (use_c.getKingdom() != null) {
              if (pc.getLevel() > 24) {
                boolean zone_check = false;
                byte b;
                int i;
                PcInstance[] arrayOfPcInstance;
                for (i = (arrayOfPcInstance = cha_c.get_List()).length, b = 0; b < i; ) {
                  PcInstance cha = arrayOfPcInstance[b];
                  if (use_c.getKingdom().isKingdomZone((L1Object)cha)) {
                    zone_check = true;
                    break;
                  } 
                  b++;
                } 
                if (!zone_check) {
                  if (cha_c.getKingdom() == null && !AgitSystem.getInstance().CheckAgit(pc.getClanId())) {
                    if (WarCheck(cha_c) == null) {
                      WarClanUpdate(cha_c, use_c.get_name());
                      if (use_c.getKingdom().isWar()) {
                        WorldInstance.getInstance().SendPacket((S_BasePacket)new S_War(1, cha_c.get_name(), use_c.get_name()));
                        cha_c.set_warClan(use_c);
                      } 
                    } else {
                      pc.SendPacket((S_BasePacket)new S_ServerMessage(522));
                    } 
                  } else {
                    pc.SendPacket((S_BasePacket)new S_ServerMessage(474));
                  } 
                } else {
                  pc.SendPacket((S_BasePacket)new S_ServerMessage(477));
                } 
              } else {
                pc.SendPacket((S_BasePacket)new S_ServerMessage(475));
              } 
            } else if (pc.getLevel() > 14) {
              if (use_c.get_warClan() == null) {
                cha_c.set_warClan(use_c);
                use_c.set_warClan(cha_c);
                PcInstance use = use_c.getRoyal();
                if (use != null) {
                  use.SendPacket((S_BasePacket)new S_ServerMessageYesNo(217, cha_c.get_name()));
                } else {
                  pc.SendPacket((S_BasePacket)new S_ServerMessage(218, use_c.get_name()));
                } 
              } else {
                pc.Message("对方已经在战争中。");
              } 
            } else {
              pc.SendPacket((S_BasePacket)new S_ServerMessage(232));
            } 
          } else {
            pc.SendPacket((S_BasePacket)new S_ServerMessage(234));
          } 
        } else {
          pc.SendPacket((S_BasePacket)new S_ServerMessage(478));
        } 
      } else if (cha_c == null) {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(272));
      } 
    } else {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(640));
    } 
  }
  
  public void WarFinal(PcInstance pc, boolean yn) {
    Clan cha_c = getClan(pc.getClanId());
    Clan use_c = cha_c.get_warClan();
    if (yn) {
      use_c.getRoyal().SendPacket((S_BasePacket)new S_ServerMessage(236, cha_c.get_name()));
      cha_c.set_warClan(null);
      use_c.set_warClan(null);
    } else {
      WorldInstance.getInstance().SendPacket((S_BasePacket)new S_War(1, use_c.get_name(), cha_c.get_name()));
    } 
  }
  
  private void WarClanUpdate(Clan c, String clan_name) {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE clan_list SET WarClan='");
    sb.append(clan_name);
    sb.append("' WHERE ClanId='");
    sb.append(c.get_id());
    sb.append("'");
    DatabaseConnection.getInstance().query_update(sb.toString());
  }
  
  public String WarCheck(Clan c) {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM clan_list WHERE ClanId=?");
      st.setInt(1, c.get_id());
      rs = st.executeQuery();
      if (rs.next() && rs.getString("WarClan") != null && rs.getString("WarClan").length() > 0)
        return rs.getString("WarClan"); 
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
    DatabaseConnection.getInstance().close(con, st, rs);
    return null;
  }
  
  public void ClanPlayerTitle(PcInstance pc, L1Object o, String title) {
    if (pc.getLevel() >= 10) {
      if (pc.getClanId() == o.getClanId()) {
        if (o.getLevel() >= 10) {
          o.setTitle(title);
          o.SendPacket((S_BasePacket)new S_ObjectTitle(o), true);
          if (pc.getObjectId() != o.getObjectId()) {
            String[] msg = new String[3];
            msg[0] = pc.getName();
            msg[1] = o.getName();
            msg[2] = o.getTitle();
            SendPacket(pc, (S_BasePacket)new S_ServerMessage(203, msg));
          } 
        } else {
          pc.SendPacket((S_BasePacket)new S_ServerMessage(202, o.getName()));
        } 
      } else {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(201, o.getName()));
      } 
    } else {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(197));
    } 
  }
  
  public synchronized void ClanKin(PcInstance pc, String name) {
    Clan c = getClan(pc.getClanId());
    if (c != null && pc.getClassType() == 0 && !pc.getName().equalsIgnoreCase(name))
      if ((c.getKingdom() != null && c.getKingdom().isWar()) || c.get_warClan() != null) {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(439));
      } else {
        c.kin(name);
        ClanUpdateDB(c, c.get_id());
      }  
  }
  
  public void ClanKin(String name, int clan_id) {
    Clan c = getClan(clan_id);
    if (c != null) {
      c.kin(name);
      ClanUpdateDB(c, c.get_id());
    } 
  }
  
  public synchronized void JoinFinal(PcInstance pc, boolean ck) {
    Clan c = getClan(pc.getClanId());
    if (c != null)
      if (ck && c.getUse() != null && !c.getUse().isDelete()) {
        StringTokenizer st = new StringTokenizer(c.get_list(), " ");
        int clanjoin_max = 2;
        if (pc.getLevel() > 49)
          clanjoin_max = 3; 
        if (pc.getTotalCha() * clanjoin_max > st.countTokens()) {
          c.getUse().setClanId(pc.getClanId());
          c.getUse().setClanName(pc.getClanName());
          c.set_list(String.valueOf(c.get_list()) + " " + c.getUse().getName());
          c.set_List(c.getUse());
          ClanUpdateDB(c, c.get_id());
          c.getUse().SendPacket((S_BasePacket)new S_ServerMessage(95, pc.getClanName()));
          c.SendPacket((S_BasePacket)new S_ServerMessage(94, c.getUse().getName()));
          c.getUse().setTitle("");
          c.getUse().SendPacket((S_BasePacket)new S_ObjectTitle((L1Object)c.getUse()), true);
        } else {
          c.getUse().SendPacket((S_BasePacket)new S_ServerMessage(188, pc.getName()));
        } 
      } else {
        c.getUse().SendPacket((S_BasePacket)new S_ServerMessage(237, pc.getClanName()));
      }  
  }
  
  public void Join(PcInstance pc) {
    PcInstance use = TradeSystem.getInstance().userFind(pc);
    if (use != null)
      if (pc.getClassType() == 0) {
        if (pc.getClassGfx() == 0) {
          pc.SendPacket((S_BasePacket)new S_ServerMessage(87));
        } else {
          pc.SendPacket((S_BasePacket)new S_ServerMessage(88));
        } 
      } else if (pc.getClanId() != 0) {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(89));
      } else if (use.getClassType() == 0 && use.getClanId() != 0) {
        use.SendPacket((S_BasePacket)new S_ServerMessageYesNo(97, pc.getName()));
        getClan(use.getClanId()).setUse(pc);
      }  
  }
  
  public void ClanList(PcInstance pc) {
    Clan c = getClan(pc.getClanId());
    if (c != null)
      if (pc.getClassType() == 0) {
        pc.SendPacket((S_BasePacket)new S_Clan(c, "pledgeM"));
      } else {
        pc.SendPacket((S_BasePacket)new S_Clan(c, "pledge"));
      }  
  }
  
  public void IconRead(PcInstance pc, int clan_id) {
    Clan c = getClan(clan_id);
    if (c != null && c.get_icon() != null) {
      int size = (c.get_icon()).length;
      if (size > 0)
        pc.SendPacket((S_BasePacket)new S_Clan(c, size)); 
    } 
  }
  
  public void IconUpdate(PcInstance pc, byte[] icon) {
    Clan c = getClan(pc.getClanId());
    if (c != null)
      if (pc.getClassType() == 0) {
        int clan_id_temp = pc.getClanId();
        this.list.remove(Integer.valueOf(c.get_id()));
        c.set_icon(icon);
        c.set_id(Config.getClanID());
        ClanUpdateDB(c, clan_id_temp);
        updateWarehouseClanID(c, clan_id_temp);
        if (c.getKingdom() != null) {
          c.getKingdom().setClanID(c.get_id());
          c.getKingdom().updateDB();
        } 
        this.list.put(Integer.valueOf(c.get_id()), c);
      } else {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(219));
      }  
  }
  
  private void updateWarehouseClanID(Clan c, int clan_id) {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE warehouse_clan SET clan_id='");
    sb.append(c.get_id());
    sb.append("' WHERE clan_id='");
    sb.append(clan_id);
    sb.append("'");
    DatabaseConnection.getInstance().query_update(sb.toString());
  }
  
  public void ClanOut(PcInstance pc) {
    Clan c = getClan(pc.getClanId());
    if (c != null)
      if ((c.getKingdom() != null && c.getKingdom().isWar()) || c.get_warClan() != null) {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(331));
      } else if (pc.getClassType() == 0) {
        if (!AgitSystem.getInstance().CheckAgit(pc.getClanId()) && c.getKingdom() == null) {
          c.SendPacket((S_BasePacket)new S_ServerMessage(269, pc.getName(), pc.getClanName()));
          c.close();
          ClanDeleteDB(c.get_id());
          this.list.remove(Integer.valueOf(c.get_id()));
        } else {
          pc.SendPacket((S_BasePacket)new S_ServerMessage(665));
        } 
      } else {
        c.SendPacket((S_BasePacket)new S_ServerMessage(178, pc.getName(), pc.getClanName()));
        c.close(pc);
        ClanUpdateDB(c, c.get_id());
      }  
  }
  
  private void ClanDeleteDB(int clan_id) {
    StringBuffer sb = new StringBuffer();
    sb.append("DELETE FROM clan_list WHERE ClanId='");
    sb.append(clan_id);
    sb.append("'");
    DatabaseConnection.getInstance().query_delete(sb.toString());
  }
  
  private void ClanUpdateDB(Clan c, int clan_id) {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE clan_list SET ClanId='");
    sb.append(c.get_id());
    sb.append("', Icon='");
    if (c.get_icon() != null) {
      StringBuffer icon = new StringBuffer();
      byte b;
      int i;
      byte[] arrayOfByte;
      for (i = (arrayOfByte = c.get_icon()).length, b = 0; b < i; ) {
        byte b_icon = arrayOfByte[b];
        icon.append(fillHex(b_icon & 0xFF, 2));
        b++;
      } 
      sb.append(icon.toString());
    } 
    sb.append("', List='");
    sb.append(c.get_list());
    sb.append("' WHERE ClanId='");
    sb.append(clan_id);
    sb.append("'");
    DatabaseConnection.getInstance().query_update(sb.toString());
  }
  
  public void ClanCreate(PcInstance pc, String clan_name) {
    if (pc.getClanId() == 0 && clan_name != null) {
      if (pc.getClassType() == 0) {
        if (pc.getLevel() >= Config.CLAN_MAKE_LEV) {
          if (clan_name.length() <= Config.CLAN_NAME_MAX_SIZE) {
            if (!CheckClanName(clan_name)) {
              pc.setClanName(clan_name);
              ClanCreate(pc);
              pc.SendPacket((S_BasePacket)new S_ServerMessage(84, clan_name));
            } else {
              pc.SendPacket((S_BasePacket)new S_ServerMessage(99));
            } 
          } else {
            pc.SendPacket((S_BasePacket)new S_ServerMessage(98));
          } 
        } else {
          pc.SendPacket((S_BasePacket)new S_ServerMessage(233));
        } 
      } else {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(85));
      } 
    } else {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(86));
    } 
  }
  
  private void ClanCreate(PcInstance pc) {
    pc.setClanId(Config.getClanID());
    Clan c = new Clan();
    c.set_id(pc.getClanId());
    c.set_name(pc.getClanName());
    c.set_lordname(pc.getName());
    c.set_list(pc.getName());
    c.set_List(pc);
    this.list.put(Integer.valueOf(c.get_id()), c);
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO clan_list SET ClanId='");
    sb.append(pc.getClanId());
    sb.append("', ClanName='");
    sb.append(pc.getClanName());
    sb.append("', lord='");
    sb.append(pc.getName());
    sb.append("', Icon='', List='");
    sb.append(c.get_list());
    sb.append("'");
    DatabaseConnection.getInstance().query_insert(sb.toString());
  }
  
  private boolean CheckClanName(String clan_name) {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT * FROM clan_list WHERE ClanName='");
    sb.append(clan_name);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select(sb.toString());
  }
  
  public void SendPacket(PcInstance cha, S_BasePacket data) {
    Clan c = getClan(cha.getClanId());
    if (c != null)
      c.SendPacket(data); 
  }
  
  public void worldIn(PcInstance pc) {
    Clan c = getClan(pc.getClanId());
    if (c != null) {
      byte b;
      int i;
      PcInstance[] arrayOfPcInstance;
      for (i = (arrayOfPcInstance = c.get_List()).length, b = 0; b < i; ) {
        PcInstance cha = arrayOfPcInstance[b];
        cha.Message("血盟成员 " + pc.getName() + " 刚刚上线。");
        b++;
      } 
      c.set_List(pc);
      if (c.get_warClan() != null)
        pc.SendPacket((S_BasePacket)new S_War(8, c.get_name(), c.get_warClan().get_name())); 
      String WarClan = WarCheck(c);
      if (WarClan != null) {
        Clan cc = getClanName(WarClan);
        if (cc != null && cc.getKingdom() != null && cc.getKingdom().isWar())
          pc.SendPacket((S_BasePacket)new S_War(8, c.get_name(), c.get_warClan().get_name())); 
      } 
      if (c.getKingdom() != null && c.getKingdom().isWar()) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
          con = DatabaseConnection.getInstance().getConnection();
          st = con.prepareStatement("SELECT * FROM clan_list WHERE WarClan=?");
          st.setString(1, c.get_name());
          rs = st.executeQuery();
          while (rs.next())
            pc.SendPacket((S_BasePacket)new S_War(8, c.get_name(), rs.getString("ClanName"))); 
        } catch (Exception exception) {
        
        } finally {
          DatabaseConnection.getInstance().close(con, st, rs);
        } 
      } 
    } 
  }
  
  public void worldOut(PcInstance pc) {
    Clan c = getClan(pc.getClanId());
    if (c != null) {
      c.remove_List(pc);
      WarRoyalDelete(pc);
    } 
  }
  
  public Clan getClan(int id) {
    return this.list.get(Integer.valueOf(id));
  }
  
  public Clan getClanName(String name) {
    byte b;
    int i;
    Clan[] arrayOfClan;
    for (i = (arrayOfClan = (Clan[])this.list.values().toArray((Object[])new Clan[this.list.size()])).length, b = 0; b < i; ) {
      Clan c = arrayOfClan[b];
      if (c.get_name().equalsIgnoreCase(name))
        return c; 
      b++;
    } 
    return null;
  }
  
  private String fillHex(int data, int digits) {
    String number = Integer.toHexString(data);
    for (int i = number.length(); i < digits; i++)
      number = "0" + number; 
    return number;
  }
  
  public byte[] psjump(String line) {
    int line_size = line.length();
    byte[] b = new byte[line_size / 2];
    int i = 0;
    for (int j = 0; i < line_size; j++) {
      b[j] = (byte)Integer.parseInt(line.substring(i, i + 2), 16);
      i += 2;
    } 
    return b;
  }
}
