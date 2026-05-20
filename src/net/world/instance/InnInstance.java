package net.world.instance;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.object.L1Object;

public class InnInstance extends L1Object {
  public void Talk(PcInstance pc) {
    if (pc.getLawful() < 65536) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "inn1"));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "inn"));
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (!text1.equalsIgnoreCase("room") && 
      !text1.equalsIgnoreCase("hall"))
      text1.equalsIgnoreCase("return"); 
  }
}
