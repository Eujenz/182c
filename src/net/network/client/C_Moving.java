package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.instance.PcInstance;

public class C_Moving extends C_BasePacket {
  private int x;
  
  private int y;
  
  private int h;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.x = readH();
    this.y = readH();
    this.h = readC();
    pc.toMove(this.x, this.y, this.h);
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.x);
      sb.append(" , ");
      sb.append(this.y);
      sb.append(" , ");
      sb.append(this.h);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
