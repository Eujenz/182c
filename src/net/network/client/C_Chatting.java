package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.function.ChattingSystem;
import net.world.instance.PcInstance;

public class C_Chatting extends C_BasePacket {
  private int type;
  
  private String chat;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.type = readC();
    this.chat = readS();
    if (this.chat != null)
      ChattingSystem.getInstance().Chatting(pc, this.type, this.chat); 
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.type);
      sb.append(" , ");
      sb.append(this.chat);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
