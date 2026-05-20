package net.network.client;

import net.LineageClient;
import net.database.DungeonTable;
import net.util.Util;
import net.world.WorldMap;
import net.world.instance.PcInstance;

public class C_ObjectPotalPointer extends C_BasePacket {
  private int x;
  
  private int y;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.x = readH();
    this.y = readH();
    if (pc.getDistance(this.x, this.y, pc.getMap(), 10) && WorldMap.getInstance().get_map(this.x, this.y, pc.getMap()) == 100) {
      pc.setX(this.x);
      pc.setY(this.y);
      DungeonTable.getInstance().gotoDungeon(pc);
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
      sb.append(this.x);
      sb.append(" , ");
      sb.append(this.y);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
