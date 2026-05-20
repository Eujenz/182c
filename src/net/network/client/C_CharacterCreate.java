package net.network.client;

import net.LineageClient;
import net.database.BanListTable;
import net.database.CharacterTable;
import net.network.server.S_BasePacket;
import net.network.server.S_LoginFail;
import net.util.Util;
import net.util.bean.Stat;
import net.world.function.GmCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class C_CharacterCreate extends C_BasePacket {
  final Logger log = LoggerFactory.getLogger(C_CharacterCreate.class);
  
  private static final String CREATE_LOG = "非法创建角色 [%s] 使用账号 [%S] 角色名 [%s] [%s] 已封锁ip";
  
  private static final String _CREATE_LOG = "创建角色IP[%s] 账号[%s] 角色[%s]";
  
  private static final int[] ORIGINAL_STR = new int[] { 13, 16, 11, 8 };
  
  private static final int[] ORIGINAL_DEX = new int[] { 10, 12, 12, 7 };
  
  private static final int[] ORIGINAL_CON = new int[] { 10, 14, 12, 12 };
  
  private static final int[] ORIGINAL_WIS = new int[] { 11, 9, 12, 12 };
  
  private static final int[] ORIGINAL_CHA = new int[] { 13, 12, 9, 8 };
  
  private static final int[] ORIGINAL_INT = new int[] { 10, 8, 12, 12 };
  
  private static final int[] ORIGINAL_AMOUNT = new int[] { 8, 4, 7, 16 };
  
  private String name;
  
  private int type;
  
  private int sex;
  
  private int Str;
  
  private int Dex;
  
  private int Con;
  
  private int Wis;
  
  private int Cha;
  
  private int Int;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    String lcIp = lc.getIP();
    if (lcIp == null) {
      lc.close();
      return;
    } 
    lc.setCreateCharCount(lc.getCreateCharCount() + 1);
    if (lc.getCreateCharCount() > 1) {
      if (!BanListTable.getInstance().isBanList(lcIp)) {
        GmCommand.getInstance().BanTable("1", lcIp);
        this.log.info(String.format("非法创建角色 [%s] 使用账号 [%S] 角色名 [%s] [%s] 已封锁ip", new Object[] { lc.getIP(), lc.getID(), this.name, "速度过快" }));
      } 
      lc.close();
      return;
    } 
    this.name = Util.StringToken(readS());
    if (this.name == null || "".equalsIgnoreCase(this.name)) {
      if (!BanListTable.getInstance().isBanList(lcIp)) {
        GmCommand.getInstance().BanTable("1", lcIp);
        this.log.info(String.format("非法创建角色 [%s] 使用账号 [%S] 角色名 [%s] [%s] 已封锁ip", new Object[] { lc.getIP(), lc.getID(), this.name, "空角色名" }));
      } 
      lc.close();
      return;
    } 
    this.name = this.name.replaceAll("　", "");
    this.type = readC();
    if (this.type < 0 || this.type > 3) {
      if (!BanListTable.getInstance().isBanList(lcIp)) {
        GmCommand.getInstance().BanTable("1", lcIp);
        this.log.info(String.format("非法创建角色 [%s] 使用账号 [%S] 角色名 [%s] [%s] 已封锁ip", new Object[] { lc.getIP(), lc.getID(), this.name, "职业错误" }));
      } 
      lc.close();
      return;
    } 
    this.sex = readC();
    if (this.sex < 0 || this.sex > 1) {
      if (!BanListTable.getInstance().isBanList(lcIp)) {
        GmCommand.getInstance().BanTable("1", lcIp);
        this.log.info(String.format("非法创建角色 [%s] 使用账号 [%S] 角色名 [%s] [%s] 已封锁ip", new Object[] { lc.getIP(), lc.getID(), this.name, "性别错误" }));
      } 
      lc.close();
      return;
    } 
    this.Str = readC();
    this.Dex = readC();
    this.Con = readC();
    this.Wis = readC();
    this.Cha = readC();
    this.Int = readC();
    boolean isStatusError = false;
    int originalStr = ORIGINAL_STR[this.type];
    int originalDex = ORIGINAL_DEX[this.type];
    int originalCon = ORIGINAL_CON[this.type];
    int originalWis = ORIGINAL_WIS[this.type];
    int originalCha = ORIGINAL_CHA[this.type];
    int originalInt = ORIGINAL_INT[this.type];
    int originalAmount = ORIGINAL_AMOUNT[this.type];
    if (this.Str < originalStr || this.Dex < originalDex || this.Con < originalCon || this.Wis < originalWis || this.Cha < originalCha || this.Int < originalInt || 
      this.Str > originalStr + originalAmount || this.Dex > originalDex + originalAmount || this.Con > originalCon + originalAmount || this.Wis > originalWis + originalAmount || 
      this.Cha > originalCha + originalAmount || this.Int > originalInt + originalAmount)
      isStatusError = true; 
    if (isStatusError) {
      lc.SendPacket((S_BasePacket)new S_LoginFail(21));
      return;
    } 
    Stat stat = lc.getStat();
    if (stat == null) {
      if (!BanListTable.getInstance().isBanList(lcIp)) {
        GmCommand.getInstance().BanTable("1", lcIp);
        this.log.info(String.format("非法创建角色 [%s] 使用账号 [%S] 角色名 [%s] [%s] 已封锁ip", new Object[] { lc.getIP(), lc.getID(), this.name, "空Stat" }));
      } 
      lc.close();
      return;
    } 
    stat.setStr(this.Str);
    stat.setDex(this.Dex);
    stat.setCon(this.Con);
    stat.setWis(this.Wis);
    stat.setCha(this.Cha);
    stat.setInt(this.Int);
    CharacterTable.getInstance().InsertCharacter(lc, this.name, this.type, this.sex);
    lc.setSecurityVerification(2);
    this.log.info(String.format("创建角色IP[%s] 账号[%s] 角色[%s]", new Object[] { lcIp, lc.getID(), this.name }));
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
      sb.append(" , ");
      sb.append(this.type);
      sb.append(" , ");
      sb.append(this.sex);
      sb.append(" , ");
      sb.append(this.Str);
      sb.append(" , ");
      sb.append(this.Dex);
      sb.append(" , ");
      sb.append(this.Con);
      sb.append(" , ");
      sb.append(this.Wis);
      sb.append(" , ");
      sb.append(this.Cha);
      sb.append(" , ");
      sb.append(this.Int);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
