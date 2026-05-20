package net.world.function;

import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.world.WorldInstance;
import net.world.instance.PcInstance;

public class Commander {
  private static class Holder {
    static Commander instance = new Commander();
  }
  
  public static Commander getInstance() {
    return Holder.instance;
  }
  
  public void who(PcInstance pc, String name) {
    if (pc.isGm())
      for (PcInstance pcInstance1 : WorldInstance.getInstance().getPc())
        pc.Message(pcInstance1.getName() + "       level：" + pcInstance1.getLevel());  
    PcInstance pcInstance = WorldInstance.getInstance().getPc(name);
    if (pcInstance != null) {
      StringBuffer text = new StringBuffer();
      if (pcInstance.getTitle() != null && pcInstance.getTitle().length() > 0)
        text.append(pcInstance.getTitle() + " "); 
      text.append(pcInstance.getName() + " ");
      if (pcInstance.getLawful() < 65536) {
        text.append("(邪惡) ");
      } else if (pcInstance.getLawful() >= 65536 && pcInstance.getLawful() < 65536) {
        text.append("(中立) ");
      } else if (pcInstance.getLawful() >= 65536) {
        text.append("(正義) ");
      } 
      if (pcInstance.getClanName() != null && pcInstance.getClanName().length() > 0) {
        text.append("[");
        text.append(pcInstance.getClanName());
        text.append("]");
      } 
      pc.Message(text.toString());
    } 
    pc.SendPacket((S_BasePacket)new S_ServerMessage(81, String.valueOf(WorldInstance.getInstance().getPcSize())));
  }
}
