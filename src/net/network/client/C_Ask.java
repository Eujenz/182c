package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.function.AskSystem;
import net.world.instance.PcInstance;

public class C_Ask extends C_BasePacket {
  int type;
  
  int yn;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.type = readH();
    this.yn = readC();
    AskSystem.getInstance().ask(pc, this.type, this.yn, this);
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
      sb.append(this.yn);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
