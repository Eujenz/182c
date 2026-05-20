package net.network.client;

import net.LineageClient;
import net.util.CharacterStatDice;
import net.util.Util;

public class C_StatDice extends C_BasePacket {
  private int stat;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    this.stat = readC();
    lc.setStat(CharacterStatDice.getInstance().getStat(this.stat));
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.stat);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
