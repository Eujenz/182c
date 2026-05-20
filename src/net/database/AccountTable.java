package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import net.ClientController;
import net.Config;
import net.LineageClient;
import net.Opcodes;
import net.database.bean.L1Account;
import net.network.client.C_BasePacket;
import net.network.server.S_AmountCharacter;
import net.network.server.S_BasePacket;
import net.network.server.S_LoginFail;
import net.network.server.S_LoginsOk;
import net.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountTable {
  final Logger log = LoggerFactory.getLogger(AccountTable.class);
  
  private HashMap<String, L1Account> _list = new HashMap<String, L1Account>();
  
  static final String OUT_LOG = "IP[%s] 账号[%s] 退出游戏";
  
  private static class Holder {
    static AccountTable instance = new AccountTable();
  }
  
  public static AccountTable getInstance() {
    return Holder.instance;
  }
  
  public void LoginsOut(LineageClient lc) {
    if (lc.getID() != null) {
      try {
        ClientController.getInstance().remove(lc);
      } catch (Exception exception) {}
      try {
        update_status(lc, false);
      } catch (Exception exception) {}
      try {
        lc.loginCountClear();
      } catch (Exception exception) {}
      if (lc.getPc() != null)
        ((C_BasePacket)Opcodes.C_LIST.get(Integer.valueOf(16))).read(lc, null); 
      this.log.info(String.format("IP[%s] 账号[%s] 退出游戏", new Object[] { lc.getIP(), lc.getID() }));
    } 
  }
  
  static String LOGINS_LOG = "[%s] 使用账号 [%S] 密码 [%s] 登陆 %S";
  
  static String LOGINS_TRY_LOG = "[%s] 试图连线...";
  
  public void Logins(LineageClient lc, String id, String pw) {
    if (lc.loginCountUp()) {
      if (select_id(id)) {
        if (select_block(id)) {
          this.log.info(String.format(LOGINS_TRY_LOG, new Object[] { lc.getIP() }));
          if (select_login(id, pw)) {
            if (Config.OPEN_RECHARGE && 
              !isLoginOfMonthCards(id)) {
              lc.SendPacket((S_BasePacket)new S_LoginFail(36));
              return;
            } 
            lc.setID(id);
            lc.setUID(getAccountUid(id));
            lc.setLevel(getAccountLevel(id));
            if (select_status(id)) {
              update_status(lc, true);
              ClientController.getInstance().put(lc);
              lc.SendPacket((S_BasePacket)new S_LoginsOk(true));
              lc.SendPacket((S_BasePacket)new S_LoginsOk(false));
              lc.SendPacket((S_BasePacket)new S_AmountCharacter(CharacterTable.getInstance().getCharacterCount(lc.getUID())));
              CharacterTable.getInstance().CharacterList(lc);
              this.log.info(String.format(LOGINS_LOG, new Object[] { lc.getIP(), id, pw, "成功" }));
              lc.setSecurityVerification(2);
            } else {
              ClientController.getInstance().close(lc);
              lc.SendPacket((S_BasePacket)new S_LoginFail(22));
              lc.PlayerStep--;
            } 
          } else {
            this.log.info(String.format(LOGINS_LOG, new Object[] { lc.getIP(), id, pw, "失败" }));
            lc.SendPacket((S_BasePacket)new S_LoginFail(8));
            lc.PlayerStep--;
          } 
        } else {
          lc.close();
        } 
      } else if (Config.AUTO_ACCOUNT) {
        lc.setID(id);
        insert_account(lc, id, pw);
        lc.setUID(getAccountUid(id));
        lc.setLevel(getAccountLevel(id));
        update_status(lc, true);
        ClientController.getInstance().put(lc);
        lc.SendPacket((S_BasePacket)new S_AmountCharacter(0));
        lc.setSecurityVerification(1);
        if (Config.OPEN_RECHARGE) {
          long nowTime = System.currentTimeMillis() / 1000L;
          put(lc.getID(), nowTime + Config.NEW_TALENT_TIME * 60L);
          updateMonthCards(lc.getID(), (nowTime + Config.NEW_TALENT_TIME * 60L) * 1000L);
        } 
      } else {
        lc.SendPacket((S_BasePacket)new S_LoginFail(26));
        lc.PlayerStep--;
      } 
    } else {
      lc.close();
    } 
  }
  
  private boolean isLoginOfMonthCards(String id) {
    boolean flag = false;
    long nowTime = System.currentTimeMillis() / 1000L;
    String mt = getMonthCards(id);
    long time = 0L;
    if (mt != null)
      time = Util.timeStrTran1970Seconds(mt).longValue(); 
    put(id, time);
    if (time > nowTime)
      flag = true; 
    return flag;
  }
  
  public void load() {
    System.out.print("[SQL] 加载帐号表.");
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM account");
      rs = st.executeQuery();
      chatable(rs);
    } catch (Exception e) {
      this.log.error(e.getLocalizedMessage(), e);
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  private void chatable(ResultSet rs) throws Exception {
    while (rs.next()) {
      L1Account cha = new L1Account();
      cha.setUid(rs.getInt("uid"));
      cha.setAccount(rs.getString("id"));
      cha.setPw(rs.getString("pw"));
      cha.setStatus(rs.getBoolean("status"));
      cha.setLevel(rs.getInt("level"));
      cha.setRegister_date(rs.getTimestamp("register_date").getTime());
      cha.setLogins_date(rs.getTimestamp("logins_date").getTime());
      cha.setBlock_date(rs.getTimestamp("block_date").getTime());
      cha.setLast_ip(rs.getString("last_ip"));
      cha.setMonth_cards(rs.getTimestamp("month_cards").getTime() / 1000L);
      this._list.put(String.valueOf(cha.getAccount()), cha);
    } 
    System.out.println(" 数量:" + this._list.size());
  }
  
  public void put(String account, long monthTiming) {
    L1Account cha = new L1Account();
    cha.setAccount(account);
    cha.setMonth_cards(monthTiming);
    this._list.put(String.valueOf(cha.getAccount()), cha);
  }
  
  public void remove(String account) {
    if (this._list.containsKey(account))
      this._list.remove(account); 
  }
  
  public Collection<L1Account> getList() {
    return this._list.values();
  }
  
  public void updateMonthCards(String id, long time) {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE account SET month_cards='");
    sb.append(new Timestamp(time));
    sb.append("' WHERE id='");
    sb.append(id);
    sb.append("'");
    DatabaseConnection.getInstance().query_update(sb.toString());
  }
  
  public String getMonthCards(String id) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT month_cards FROM account WHERE id='");
    sb.append(id);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select_string(sb.toString());
  }
  
  public void update_password(String id, String pw) {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE account SET pw=OLD_PASSWORD('");
    sb.append(pw);
    sb.append("') WHERE id='");
    sb.append(id);
    sb.append("'");
    DatabaseConnection.getInstance().query_update(sb.toString());
  }
  
  public void update_status(LineageClient lc, boolean login) {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE account SET status='");
    sb.append(login ? 1 : 0);
    if (login) {
      sb.append("', logins_date='");
      sb.append(new Timestamp(System.currentTimeMillis()));
      sb.append("', last_ip='");
      sb.append(lc.getIP());
    } 
    sb.append("' WHERE id='");
    sb.append(lc.getID());
    sb.append("'");
    DatabaseConnection.getInstance().query_update(sb.toString());
  }
  
  static String CREAT_LOG = "IP[%s] 创建账号 [%S] 密码 [%s]";
  
  private void insert_account(LineageClient lc, String id, String pw) {
    StringBuilder sb = new StringBuilder();
    sb.append("INSERT INTO account SET id='");
    sb.append(id);
    sb.append("', pw=OLD_PASSWORD('");
    sb.append(pw);
    sb.append("'), status='1', register_date='");
    sb.append(new Timestamp(System.currentTimeMillis()));
    sb.append("', logins_date='");
    sb.append(new Timestamp(System.currentTimeMillis()));
    sb.append("', last_ip='");
    sb.append(lc.getIP());
    sb.append("'");
    DatabaseConnection.getInstance().query_insert(sb.toString());
    this.log.info(String.format(CREAT_LOG, new Object[] { lc.getIP(), id, pw }));
  }
  
  public boolean select_id(String id) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * FROM account WHERE id='");
    sb.append(id);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select(sb.toString());
  }
  
  public boolean select_login(String id, String pw) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * FROM account WHERE id='");
    sb.append(id);
    sb.append("' AND pw=OLD_PASSWORD('");
    sb.append(pw);
    sb.append("')");
    return DatabaseConnection.getInstance().query_select(sb.toString());
  }
  
  private boolean select_status(String id) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * FROM account WHERE id='");
    sb.append(id);
    sb.append("' AND status='0'");
    return DatabaseConnection.getInstance().query_select(sb.toString());
  }
  
  private boolean select_block(String id) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * FROM account WHERE id='");
    sb.append(id);
    sb.append("' AND block_date='0000-00-00 00:00:00'");
    return DatabaseConnection.getInstance().query_select(sb.toString());
  }
  
  private int getAccountUid(String id) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT uid FROM account WHERE id='");
    sb.append(id);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select_count(sb.toString());
  }
  
  private int getAccountLevel(String id) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT level FROM account WHERE id='");
    sb.append(id);
    sb.append("'");
    return DatabaseConnection.getInstance().query_select_count(sb.toString());
  }
}
