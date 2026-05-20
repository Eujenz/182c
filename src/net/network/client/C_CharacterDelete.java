package net.network.client;

import net.LineageClient;
import net.database.BanListTable;
import net.database.CharacterTable;
import net.util.Util;
import net.world.function.GmCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class C_CharacterDelete extends C_BasePacket {
  final Logger log = LoggerFactory.getLogger(C_CharacterDelete.class);
  
  private static final String DELETE_LOG = "非法删除角色 [%s] 使用账号 [%S] 角色名 [%s] [%s] 已封锁ip";
  
  private static final String _DELETE_LOG = "删除角色IP[%s] 账号[%s] 角色[%s]";
  
  private String name;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    String lcIp = lc.getIP();
    if (lcIp == null) {
      lc.close();
      return;
    } 
    lc.setDeleteCharCount(lc.getDeleteCharCount() + 1);
    if (lc.getDeleteCharCount() > 1) {
      if (!BanListTable.getInstance().isBanList(lcIp)) {
        GmCommand.getInstance().BanTable("1", lcIp);
        this.log.info(String.format("非法删除角色 [%s] 使用账号 [%S] 角色名 [%s] [%s] 已封锁ip", new Object[] { lc.getIP(), lc.getID(), this.name, "速度过快" }));
      } 
      lc.close();
      return;
    } 
    this.name = Util.StringToken(readS());
    if (this.name == null) {
      if (!BanListTable.getInstance().isBanList(lcIp)) {
        GmCommand.getInstance().BanTable("1", lcIp);
        this.log.info(String.format("非法删除角色 [%s] 使用账号 [%S] 角色名 [%s] [%s] 已封锁ip", new Object[] { lc.getIP(), lc.getID(), this.name, "空角色名" }));
      } 
      lc.close();
      return;
    } 
    CharacterTable.getInstance().CharacterDelete(lc, this.name);
    this.log.info(String.format("删除角色IP[%s] 账号[%s] 角色[%s]", new Object[] { lcIp, lc.getID(), this.name }));
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.name);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
