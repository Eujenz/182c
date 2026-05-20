package net.network.client;

import net.LineageClient;
import net.database.CharacterTable;
import net.database.FriendTable;
import net.util.Util;
import net.world.instance.PcInstance;

public class C_FriendAdd extends C_BasePacket {
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    String name = readS();
    int objectid = CharacterTable.getInstance().getCharacterObjectId(name);
    if (objectid == 0 || objectid == pc.getObjectId())
      return; 
    if (!FriendTable.friendCheck(pc.getName(), name))
      FriendTable.friendAdd(pc.getName(), name); 
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    return sb.toString();
  }
}
