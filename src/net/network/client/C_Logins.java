package net.network.client;

import net.LineageClient;
import net.database.AccountTable;
import net.database.BanListTable;
import net.util.Util;
import net.world.function.GmCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class C_Logins extends C_BasePacket {
  final Logger log = LoggerFactory.getLogger(C_Logins.class);
  
  private String id;
  
  private String pw;
  
  private static final String LOGINS_LOG = "非法登陆 [%s] 使用账号 [%S] 密码 [%s] [%s] 已封锁ip";
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    String lcIp = lc.getIP();
    if (lcIp == null) {
      lc.close();
      return;
    } 
    String temp = Util.StringToken(readS());
    if (temp == null) {
      if (!BanListTable.getInstance().isBanList(lcIp)) {
        GmCommand.getInstance().BanTable("1", lcIp);
        this.log.info(String.format("非法登陆 [%s] 使用账号 [%S] 密码 [%s] [%s] 已封锁ip", new Object[] { lc.getIP(), this.id, this.pw, "空帐号" }));
      } 
      lc.close();
      return;
    } 
    this.id = temp.toLowerCase();
    this.pw = Util.StringToken(readS());
    if (this.pw == null) {
      if (!BanListTable.getInstance().isBanList(lcIp)) {
        GmCommand.getInstance().BanTable("1", lcIp);
        this.log.info(String.format("非法登陆 [%s] 使用账号 [%S] 密码 [%s] [%s] 已封锁ip", new Object[] { lc.getIP(), this.id, this.pw, "空密码" }));
      } 
      lc.close();
      return;
    } 
    AccountTable.getInstance().Logins(lc, this.id, this.pw);
    lc.setLoginTime(System.currentTimeMillis());
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.id);
      sb.append(" , ");
      sb.append(this.pw);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
