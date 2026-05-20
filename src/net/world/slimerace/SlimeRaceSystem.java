package net.world.slimerace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import net.Config;
import net.database.DatabaseConnection;
import net.database.ItemsTable;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectMoving;
import net.util.Util;
import net.world.instance.SlimeraceInstance;
import net.world.instance.inventory.function.SlimeRaceTicket;
import net.world.object.L1Object;

public class SlimeRaceSystem extends TimerTask {
  private static Timer timer;
  
  public static final int SlimeRaceTicketDbId = 344;
  
  private int SlimeRaceUid;
  
  private L1Object npc_gora1 = null;
  
  private L1Object npc_gora2 = null;
  
  public EVENT_STATUS status = EVENT_STATUS.CLEAR;
  
  private List<String> SlimeFinish;
  
  private List<SlimeraceInstance> SlimeList;
  
  private String[][] SlimeNameList = new String[][] { 
      { "$382", "$378" }, { "$389", "$347" }, { "$397", "$355" }, { "$396", "$354" }, { "$390", "$348" }, { "$398", "$356" }, { "$387", "$345" }, { "$383", "$379" }, { "$391", "$349" }, { "$401", "$359" }, 
      { "$388", "$346" }, { "$394", "$352" }, { "$386", "$344" }, { "$393", "$351" }, { "$384", "$380" }, { "$400", "$358" }, { "$399", "$357" }, { "$392", "$350" }, { "$395", "$353" }, { "$385", "$381" } };
  
  private static class Holder {
    static SlimeRaceSystem instance = new SlimeRaceSystem();
  }
  
  public static SlimeRaceSystem getInstance() {
    return Holder.instance;
  }
  
  private SlimeRaceSystem() {
    this.SlimeFinish = new ArrayList<String>();
    this.SlimeList = new ArrayList<SlimeraceInstance>();
    for (int i = 0; i < 5; i++) {
      SlimeraceInstance s = new SlimeraceInstance();
      s.setObjectId(Config.getObjectID_ETC());
      s.setGfx(31);
      this.SlimeList.add(s);
      s.setHomeMap(4);
      s.setHomeX(32615 + i);
      s.setHomeY(32655);
      s.setHeading(4);
    } 
    this.SlimeRaceUid = DatabaseConnection.getInstance().query_select_count("SELECT COUNT(uid) FROM slimerace_log") + 1;
  }
  
  public void start() {
    timer = new Timer(false);
    timer.schedule(getInstance(), 0L, 840L);
  }
  
  public void run() {
    try {
      int i;
      switch (this.status.ordinal()) {
        case 1:
          for (i = 10; i > 0; i--) {
            SendMessage("第 " + i + " !");
            Thread.sleep(1000L);
          } 
          SendMessage("开始!");
          setStatus(EVENT_STATUS.PLAY);
          break;
        case 0:
          SlimeMove();
          SlimeFinish();
          if (this.SlimeFinish.size() == 5)
            setStatus(EVENT_STATUS.STOP); 
          break;
        case 2:
          insertDB();
          Thread.sleep(60000L);
          this.SlimeFinish.clear();
          for (SlimeraceInstance s : this.SlimeList) {
            s.toDelete();
            s.clean();
          } 
          setStatus(EVENT_STATUS.CLEAR);
          break;
        case 3:
          printSlime();
          for (i = Config.SLIME_TIME; i > 0; i--) {
            Thread.sleep(60000L);
            SendMessage("距本场比赛开始还剩余 " + i + " 分钟!");
          } 
          Thread.sleep(40000L);
          for (i = 10; i > 0; i--) {
            SendMessage(String.valueOf(i) + "!");
            Thread.sleep(1000L);
          } 
          SendMessage("开始!");
          setStatus(EVENT_STATUS.READY);
          break;
      } 
    } catch (Exception exception) {}
  }
  
  private void insertDB() {
    Connection con = null;
    PreparedStatement st = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("INSERT INTO slimerace_log SET search_item=?, uid=?");
      st.setInt(1, getIdx(this.SlimeFinish.get(0)));
      st.setInt(2, this.SlimeRaceUid++);
      st.execute();
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st);
    } 
  }
  
  private int getIdx(String name) {
    for (int i = this.SlimeNameList.length - 1; i >= 0; i--) {
      if (this.SlimeNameList[i][1].equalsIgnoreCase(name))
        return i; 
    } 
    return 0;
  }
  
  private void SlimeFinish() {
    for (SlimeraceInstance s : this.SlimeList) {
      if (s.getY() == 32684 && !s.finish) {
        s.finish = true;
        if (!this.SlimeFinish.contains(s.getName())) {
          this.SlimeFinish.add(s.getName());
          SendMessage("第" + this.SlimeFinish.size() + "名 - " + s.getName());
        } 
      } 
    } 
  }
  
  private void SlimeMove() {
    for (SlimeraceInstance s : this.SlimeList) {
      if (!s.finish && !SlimeLucky(s)) {
        s.setY(s.getY() + 1);
        s.SendPacket((S_BasePacket)new S_ObjectMoving((L1Object)s), true);
      } 
    } 
  }
  
  private boolean SlimeLucky(SlimeraceInstance s) {
    if (s.Theory < Util.rand(0.0D, 100.0D) && !s.Lucky)
      switch (s.Status) {
        case 0:
          return (40 < Util.rand(0, 100));
        case 1:
          return (60 < Util.rand(0, 100));
        case 2:
          return (80 < Util.rand(0, 100));
      }  
    return false;
  }
  
  private void printSlime() {
    setSlimeName();
    setSlimeTheory();
    VisualSlime();
  }
  
  private void VisualSlime() {
    for (SlimeraceInstance s : this.SlimeList)
      s.toTeleport(s.getHomeX(), s.getHomeY(), s.getHomeMap()); 
  }
  
  private void setSlimeTheory() {
    for (SlimeraceInstance s : this.SlimeList) {
      s.Theory = Util.rand(0.0D, 20.0D);
      s.Status = Util.rand(0, 2);
      if (s.Status <= 1) {
        if (s.Status == 0) {
          if (Util.rand(0, 100) > 60)
            s.Lucky = true; 
          continue;
        } 
        if (Util.rand(0, 100) > 80)
          s.Lucky = true; 
      } 
    } 
  }
  
  private void setSlimeName() {
    String name = null;
    int idx = 0;
    for (SlimeraceInstance s : this.SlimeList) {
      while (true) {
        idx = Util.rand(0, this.SlimeNameList.length - 1);
        name = this.SlimeNameList[idx][1];
        if (!isSlimeName(name)) {
          s.setName(name);
          s.idx = idx;
        } 
      } 
    } 
  }
  
  private boolean isSlimeName(String name) {
    for (SlimeraceInstance s : this.SlimeList) {
      if (s.getName() != null && s.getName().equalsIgnoreCase(name))
        return true; 
    } 
    return false;
  }
  
  private void setStatus(EVENT_STATUS es) {
    this.status = es;
  }
  
  private void SendMessage(String msg) {
    if (this.npc_gora1 != null)
      this.npc_gora1.ShoutChatting(msg); 
    if (this.npc_gora2 != null)
      this.npc_gora2.ShoutChatting(msg); 
  }
  
  public void setGora1(L1Object gora) {
    this.npc_gora1 = gora;
  }
  
  public void setGora2(L1Object gora) {
    this.npc_gora2 = gora;
  }
  
  public String[] getSlimeStatus() {
    List<String> _status = new ArrayList<String>();
    if (this.status == EVENT_STATUS.CLEAR)
      for (SlimeraceInstance s : this.SlimeList) {
        _status.add(this.SlimeNameList[s.idx][0]);
        switch (s.Status) {
          case 0:
            _status.add("最低");
            break;
          case 1:
            _status.add("一般");
            break;
          case 2:
            _status.add("最好");
            break;
        } 
        String theory = String.valueOf(s.Theory);
        _status.add(String.valueOf(theory.substring(0, theory.length() - 14)) + "%");
      }  
    return _status.<String>toArray(new String[_status.size()]);
  }
  
  public String SlimeRaceTicketName(int idx) {
    String name = ((SlimeraceInstance)this.SlimeList.get(idx)).getName();
    StringBuffer sb = new StringBuffer();
    sb.append(this.SlimeRaceUid);
    sb.append("-");
    sb.append(getIdx(name));
    sb.append(" ");
    sb.append(name);
    return sb.toString();
  }
  
  public SlimeRaceTicket getSlimeRaceTicket(int idx) {
    try {
      String name = ((SlimeraceInstance)this.SlimeList.get(idx)).getName();
      SlimeRaceTicket srt = (SlimeRaceTicket)ItemsTable.getInstance().newItem(344, true, true);
      srt.setSlimeRacerIdx(getIdx(name));
      srt.setSlimeRacerName(name);
      srt.setSlimeRaceUid(this.SlimeRaceUid);
      return srt;
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public enum EVENT_STATUS {
    READY, PLAY, STOP, CLEAR;
  }
}
