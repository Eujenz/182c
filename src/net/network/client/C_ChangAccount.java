package net.network.client;

import net.LineageClient;
import net.database.AccountTable;
import net.network.server.S_BasePacket;
import net.network.server.S_LoginFail;
import net.util.Util;

public class C_ChangAccount extends C_BasePacket {
  private String id;
  
  private String pw;
  
  private String pww;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    this.id = readS();
    this.pw = readS();
    this.pww = readS();
    if (AccountTable.getInstance().select_id(this.id)) {
      if (AccountTable.getInstance().select_login(this.id, this.pw)) {
        AccountTable.getInstance().update_password(this.id, this.pww);
        lc.SendPacket((S_BasePacket)new S_LoginFail(4));
      } else {
        lc.SendPacket((S_BasePacket)new S_LoginFail(26));
      } 
    } else {
      lc.SendPacket((S_BasePacket)new S_LoginFail(8));
    } 
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
      sb.append(" , ");
      sb.append(this.pww);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
