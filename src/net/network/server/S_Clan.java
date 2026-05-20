package net.network.server;

import java.util.StringTokenizer;
import net.util.Util;
import net.world.WorldInstance;
import net.world.function.bean.Clan;

public class S_Clan extends S_BasePacket {
  public S_Clan(Clan c, int size) {
    writeC(53);
    writeD(c.get_id());
    writeB(c.get_icon(), size);
  }
  
  public S_Clan(Clan c, String action) {
    writeC(42);
    writeD(0);
    writeS(action);
    writeC(0);
    StringTokenizer st = new StringTokenizer(c.get_list(), " ");
    StringBuffer list = new StringBuffer();
    int size = st.countTokens();
    while (size-- > 0) {
      String name = st.nextToken();
      if (WorldInstance.getInstance().getPc(name) != null)
        list.append(String.valueOf(name) + " "); 
    } 
    if ("pledgeM".equalsIgnoreCase(action)) {
      writeH(3);
      writeS(c.get_name());
      writeS(list.toString());
      writeS(c.get_list());
    } else {
      writeH(2);
      writeS(c.get_name());
      writeS(list.toString());
    } 
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
