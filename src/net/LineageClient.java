package net;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import net.database.AccountTable;
import net.network.server.S_BasePacket;
import net.util.bean.Stat;
import net.world.WorldInstance;
import net.world.instance.PcInstance;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineageClient {
  final Logger logger = LoggerFactory.getLogger(LineageClient.class);
  
  public static final String CLIENT_KEY = "LineageClient Key";
  
  private IoSession session;
  
  private short loginCount = 0;
  
  private String ID;
  
  private int UID;
  
  private int level;
  
  private long time;
  
  private long loginTime;
  
  private boolean login;
  
  private int noticeIdx;
  
  private Stat s;
  
  private PcInstance pc;
  
  public int PlayerStep;
  
  public long packet_C_total_size;
  
  public long packet_S_total_size;
  
  public ByteBuffer buffer = null;
  
  private int codeCount;
  
  private int worldJoinCount;
  
  private int securityVerification;
  
  private int createCharCount;
  
  private int deleteCharCount;
  
  private int ellyonneCount;
  
  private boolean isUI;
  
  public List<String> log_packet = new ArrayList<String>();
  
  public LineageClient(IoSession session) {
    this.session = session;
  }
  
  public synchronized void SendPacket(S_BasePacket bp) {
    if (this.session != null)
      this.session.write(bp); 
  }
  
  public void close() {
    if (this.session != null) {
      if (isUI())
        WorldInstance.getInstance().removeUIClient(this); 
      AccountTable.getInstance().LoginsOut(this);
      String name = (this.pc == null) ? "" : this.pc.getName();
      this.logger.debug("玩家退出遊戲,IP:" + getIP() + ",name：" + name);
      this.session.close(true);
      this.session = null;
    } 
  }
  
  public boolean loginCountUp() {
    return ((this.loginCount = (short)(this.loginCount + 1)) < 3);
  }
  
  public void loginCountClear() {
    this.loginCount = 0;
  }
  
  public String getID() {
    return this.ID;
  }
  
  public void setID(String id) {
    this.ID = id;
  }
  
  public int getLevel() {
    return this.level;
  }
  
  public void setLevel(int level) {
    this.level = level;
  }
  
  public int getUID() {
    return this.UID;
  }
  
  public void setUID(int UID) {
    this.UID = UID;
  }
  
  public boolean isConnected() {
    if (this.session != null)
      return this.session.isConnected(); 
    return false;
  }
  
  public long getPingTime() {
    return this.time;
  }
  
  public void setPingTime(long time) {
    this.time = time;
  }
  
  public long getLoginTime() {
    return this.loginTime;
  }
  
  public void setLoginTime(long loginTime) {
    this.loginTime = loginTime;
  }
  
  public boolean isLogin() {
    return this.login;
  }
  
  public void setLogin(boolean login) {
    this.login = login;
  }
  
  public String getIP() {
    if (this.session != null)
      return (String)this.session.getAttribute("IP"); 
    return null;
  }
  
  public int getNoticeIdx() {
    return this.noticeIdx;
  }
  
  public void setNoticeIdx(int noticeIdx) {
    this.noticeIdx = noticeIdx;
  }
  
  public Stat getStat() {
    return this.s;
  }
  
  public void setStat(Stat s) {
    this.s = s;
  }
  
  public void setPc(PcInstance pc) {
    this.pc = pc;
  }
  
  public PcInstance getPc() {
    return this.pc;
  }
  
  public int getCodeCount() {
    return this.codeCount;
  }
  
  public void setCodeCount(int codeCount) {
    this.codeCount = codeCount;
  }
  
  public int getWorldJoinCount() {
    return this.worldJoinCount;
  }
  
  public void setWorldJoinCount(int worldJoinCount) {
    this.worldJoinCount = worldJoinCount;
  }
  
  public int getSecurityVerification() {
    return this.securityVerification;
  }
  
  public void setSecurityVerification(int securityVerification) {
    this.securityVerification = securityVerification;
  }
  
  public int getCreateCharCount() {
    return this.createCharCount;
  }
  
  public void setCreateCharCount(int createCharCount) {
    this.createCharCount = createCharCount;
  }
  
  public int getDeleteCharCount() {
    return this.deleteCharCount;
  }
  
  public void setDeleteCharCount(int deleteCharCount) {
    this.deleteCharCount = deleteCharCount;
  }
  
  public int getEllyonneCount() {
    return this.ellyonneCount;
  }
  
  public void setEllyonneCount(int ellyonneCount) {
    this.ellyonneCount = ellyonneCount;
  }
  
  public boolean isUI() {
    return this.isUI;
  }
  
  public void setUI(boolean isUI) {
    this.isUI = isUI;
  }
}
