package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.instance.PcInstance;

public class C_ClientOption extends C_BasePacket {
  private int type;
  
  private int onoff;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.type = readC();
    this.onoff = readC();
    switch (this.type) {
      case 0:
        pc.setGlobalChat((this.onoff == 1));
        break;
      case 2:
        pc.setWhisperChat((this.onoff == 1));
        break;
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
      sb.append(this.type);
      sb.append(" , ");
      sb.append(this.onoff);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
