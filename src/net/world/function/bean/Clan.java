package net.world.function.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import net.database.DatabaseConnection;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.world.WorldInstance;
import net.world.instance.PcInstance;
import net.world.kingdom.Kingdom;

public class Clan {
  private int _id;
  
  private String _name;
  
  private String _lordname;
  
  private byte[] _icon;
  
  private String _list;
  
  private List<PcInstance> ClanList = new ArrayList<PcInstance>();
  
  private Kingdom k;
  
  private Clan warClan;
  
  private PcInstance use;
  
  private boolean isLockWarehouse;
  
  public Clan get_warClan() {
    return this.warClan;
  }
  
  public void set_warClan(Clan warClan) {
    this.warClan = warClan;
  }
  
  public Kingdom getKingdom() {
    return this.k;
  }
  
  public void setKingdom(Kingdom k) {
    this.k = k;
  }
  
  public PcInstance getUse() {
    return this.use;
  }
  
  public void setUse(PcInstance use) {
    this.use = use;
  }
  
  public PcInstance getRoyal() {
    byte b;
    int i;
    PcInstance[] arrayOfPcInstance;
    for (i = (arrayOfPcInstance = get_List()).length, b = 0; b < i; ) {
      PcInstance cha = arrayOfPcInstance[b];
      if (cha.getClassType() == 0)
        return cha; 
      b++;
    } 
    return null;
  }
  
  public void close() {
    try {
      StringTokenizer st = new StringTokenizer(get_list(), " ");
      int size = st.countTokens();
      while (size-- > 0) {
        String name = st.nextToken();
        kinFinal(name);
        PcInstance pcInstance = WorldInstance.getInstance().getPc(name);
        if (pcInstance != null) {
          pcInstance.setClanId(0);
          pcInstance.setClanName("");
          pcInstance.setTitle("");
        } 
      } 
    } catch (Exception exception) {}
    this.ClanList.clear();
  }
  
  public void close(PcInstance cha) {
    try {
      cha.setClanId(0);
      cha.setClanName("");
      cha.setTitle("");
      StringTokenizer st = new StringTokenizer(get_list(), " ");
      String[] db = new String[st.countTokens() - 1];
      int size = db.length;
      for (int i = 0; i < size; ) {
        String name = st.nextToken();
        if (!name.equalsIgnoreCase(cha.getName()))
          db[i++] = name; 
      } 
      StringBuffer db2 = new StringBuffer();
      for (int j = 0; j < size; j++) {
        db2.append(db[j]);
        if (j + 1 < size)
          db2.append(" "); 
      } 
      set_list(db2.toString());
      remove_List(cha);
    } catch (Exception exception) {}
  }
  
  public void kin(String name) {
    try {
      StringTokenizer st0 = new StringTokenizer(get_list(), " ");
      int size = st0.countTokens();
      for (int j = 0; j < size; j++) {
        if (st0.nextToken().equalsIgnoreCase(name)) {
          PcInstance pcInstance = WorldInstance.getInstance().getPc(name);
          if (pcInstance != null) {
            pcInstance.setClanId(0);
            pcInstance.setClanName("");
            pcInstance.setTitle("");
            pcInstance.SendPacket((S_BasePacket)new S_ServerMessage(238, get_name()));
          } else {
            kinFinal(name);
          } 
          StringTokenizer st = new StringTokenizer(get_list(), " ");
          String[] db = new String[st.countTokens() - 1];
          int db_size = db.length;
          for (int i = 0; i < db_size; ) {
            String name1 = st.nextToken();
            if (!name1.equalsIgnoreCase(name))
              db[i++] = name1; 
          } 
          StringBuffer db2 = new StringBuffer();
          for (int k = 0; k < db_size; k++) {
            db2.append(db[k]);
            if (k + 1 < db_size)
              db2.append(" "); 
          } 
          set_list(db2.toString());
          byte b;
          int m;
          PcInstance[] arrayOfPcInstance;
          for (m = (arrayOfPcInstance = get_List()).length, b = 0; b < m; ) {
            PcInstance use = arrayOfPcInstance[b];
            if (use.getClassType() == 0) {
              use.SendPacket((S_BasePacket)new S_ServerMessage(240, name));
            } else {
              use.SendPacket((S_BasePacket)new S_ServerMessage(108, name));
            } 
            b++;
          } 
          break;
        } 
      } 
    } catch (Exception exception) {}
  }
  
  public PcInstance[] get_List() {
    return this.ClanList.<PcInstance>toArray(new PcInstance[this.ClanList.size()]);
  }
  
  public void set_List(PcInstance cha) {
    if (!ck_List(cha))
      this.ClanList.add(cha); 
  }
  
  public void remove_List(PcInstance cha) {
    this.ClanList.remove(cha);
  }
  
  public PcInstance get_ListId(int objectId) {
    byte b;
    int i;
    PcInstance[] arrayOfPcInstance;
    for (i = (arrayOfPcInstance = get_List()).length, b = 0; b < i; ) {
      PcInstance cha = arrayOfPcInstance[b];
      if (cha != null && !cha.isDelete()) {
        if (cha.getObjectId() == objectId)
          return cha; 
      } else {
        remove_List(cha);
      } 
      b++;
    } 
    return null;
  }
  
  public boolean ck_List(PcInstance use) {
    return this.ClanList.contains(use);
  }
  
  public void SendPacket(S_BasePacket data) {
    byte b;
    int i;
    PcInstance[] arrayOfPcInstance;
    for (i = (arrayOfPcInstance = get_List()).length, b = 0; b < i; ) {
      PcInstance cha = arrayOfPcInstance[b];
      if (cha != null && !cha.isDelete()) {
        cha.SendPacket(data.clone());
      } else {
        remove_List(cha);
      } 
      b++;
    } 
    data.clear();
  }
  
  public String get_list() {
    return this._list;
  }
  
  public void set_list(String list) {
    this._list = list;
  }
  
  public byte[] get_icon() {
    return this._icon;
  }
  
  public void set_icon(byte[] icon) {
    this._icon = icon;
  }
  
  public String get_lordname() {
    return this._lordname;
  }
  
  public void set_lordname(String lordname) {
    this._lordname = lordname;
  }
  
  public String get_name() {
    return this._name;
  }
  
  public void set_name(String name) {
    this._name = name;
  }
  
  public int get_id() {
    return this._id;
  }
  
  public void set_id(int id) {
    if (this._id != 0 && get_list() != null) {
      StringTokenizer st = new StringTokenizer(get_list(), " ");
      int size = st.countTokens();
      while (size-- > 0) {
        String name = st.nextToken();
        PcInstance pcInstance = WorldInstance.getInstance().getPc(name);
        if (pcInstance != null) {
          pcInstance.setClanId(id);
          continue;
        } 
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("UPDATE characters SET clanID='");
        stringBuffer.append(id);
        stringBuffer.append("' WHERE name='");
        stringBuffer.append(name);
        stringBuffer.append("'");
        DatabaseConnection.getInstance().query_update(stringBuffer.toString());
      } 
      StringBuffer sb = new StringBuffer();
      sb.append("UPDATE kingdom SET clan_id='");
      sb.append(id);
      sb.append("' WHERE clan_id='");
      sb.append(this._id);
      sb.append("'");
      DatabaseConnection.getInstance().query_update(sb.toString());
      sb = new StringBuffer();
      sb.append("UPDATE agit_list SET clan_id='");
      sb.append(id);
      sb.append("' WHERE clan_id='");
      sb.append(this._id);
      sb.append("'");
      DatabaseConnection.getInstance().query_update(sb.toString());
    } 
    this._id = id;
  }
  
  private void kinFinal(String name) {
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE characters SET title='', clanID='0', clanNAME='' WHERE name='");
    sb.append(name);
    sb.append("'");
    DatabaseConnection.getInstance().query_update(sb.toString());
  }
  
  public boolean isLockWarehouse() {
    return this.isLockWarehouse;
  }
  
  public void setLockWarehouse(boolean isLockWarehouse) {
    this.isLockWarehouse = isLockWarehouse;
  }
}
