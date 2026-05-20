package net.network.client;

import java.util.ArrayList;

import net.LineageClient;
import net.database.FriendTable;
import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.util.Util;
import net.world.WorldInstance;
import net.world.instance.PcInstance;

public class C_FriendList extends C_BasePacket {
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    ArrayList<String> list = new ArrayList<String>();
    list = PcInstance.list;
    readD();
    String msg = "";
    String msg2 = "";
    FriendTable.friendList(pc.getName());
    PcInstance target = null;
    for (int i = 0; i < list.size(); i++) {
      target = WorldInstance.getInstance().getPc(list.get(i));
      if (target != null) {
        msg = msg + (String)list.get(i) + " ";
      } else {
        msg2 = msg2 + (String)list.get(i) + " ";
      } 
    } 
    pc.SendPacket((S_BasePacket)new S_ShowHtml(pc.getObjectId(), "friendlist", msg, msg2));
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
