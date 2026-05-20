package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.function.ChattingSystem;
import net.world.instance.PcInstance;

public class C_ChattingWhisper extends C_BasePacket {
  private String name;
  
  private String chat;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.name = readS();
    this.chat = readS();
    if (this.name == null || "".equalsIgnoreCase(this.name))
      return; 
    ChattingSystem.getInstance().whisper(pc, this.name, this.chat);
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
      sb.append(this.chat);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
