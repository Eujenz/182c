package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.function.Commander;
import net.world.instance.PcInstance;

public class C_Who extends C_BasePacket {
  private String name;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.name = readS();
    Commander.getInstance().who(pc, this.name);
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
