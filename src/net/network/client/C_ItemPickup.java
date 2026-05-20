package net.network.client;

import net.LineageClient;
import net.network.server.S_BasePacket;
import net.network.server.S_CharacterStat;
import net.util.Util;
import net.world.instance.PcInstance;
import net.world.object.Character;
import net.world.object.L1Object;

public class C_ItemPickup extends C_BasePacket {
  private int x;
  
  private int y;
  
  private int inv_id;
  
  private long count;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.x = readH();
    this.y = readH();
    this.inv_id = readD();
    this.count = readD();
    L1Object temp = pc.getObject(this.inv_id);
    if (temp != null && !pc.isLock())
      temp.pickup((Character)pc, this.x, this.y, this.count); 
    pc.SendPacket((S_BasePacket)new S_CharacterStat((Character)pc));
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
      sb.append(this.inv_id);
      sb.append(" , ");
      sb.append(this.count);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
