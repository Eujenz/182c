package net.network.client;

import net.LineageClient;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectHeading;
import net.util.Util;
import net.world.instance.PcInstance;
import net.world.object.L1Object;

public class C_ChangeHead extends C_BasePacket {
  private int h;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.h = readC();
    if (this.h >= 0 && this.h <= 7) {
      pc.setHeading(this.h);
      pc.SendPacket((S_BasePacket)new S_ObjectHeading((L1Object)pc), false);
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
      sb.append(this.h);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
