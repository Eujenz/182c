package net.world.npc;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.kingdom.Kingdom;
import net.world.kingdom.function.Chamberlain;

public class Ishmael extends Chamberlain {
  private String html;
  
  public Ishmael(Kingdom k, String html) {
    super(k);
    this.html = html;
  }
  
  public void Talk(PcInstance pc) {
    if (pc.isGm()) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), String.valueOf(this.html) + "1"));
      return;
    } 
    if (this.k.getClanID() == pc.getClanId()) {
      if (pc.getClassType() == 0) {
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), String.valueOf(this.html) + "1"));
      } else {
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), String.valueOf(this.html) + "6", pc.getName()));
      } 
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), String.valueOf(this.html) + "7"));
    } 
  }
}
