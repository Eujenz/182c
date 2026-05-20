package net.network.client;

import net.LineageClient;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectTitle;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.WorldInstance;
import net.world.function.ClanSystem;
import net.world.instance.PcInstance;
import net.world.object.L1Object;

public class C_CharacterTitle extends C_BasePacket {
  private String name;
  
  private String title;
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    this.name = readS();
    this.title = readS();
    if (this.title == null || this.title.trim().length() < 1 || (this.title.contains(" ") && this.title.length() > 8) || this.title.length() > 15) {
      pc.Message("含有空格最大只支持8个字符或总长度不能超过15个字符！");
      return;
    } 
    if (!pc.isDead()) {
      PcInstance pcInstance = WorldInstance.getInstance().getPc(this.name);
      if (pcInstance != null) {
        if (pc.getClassType() == 0 && pc.getClanId() != 0) {
          ClanSystem.getInstance().ClanPlayerTitle(pc, (L1Object)pcInstance, this.title);
        } else if (pc.getClanId() != 0) {
          pc.SendPacket((S_BasePacket)new S_ServerMessage(198));
        } else if (pc.getName().equalsIgnoreCase(pcInstance.getName())) {
          if (pc.getLevel() >= 40) {
            pc.setTitle(this.title);
            pc.SendPacket((S_BasePacket)new S_ObjectTitle((L1Object)pc), true);
          } else {
            pc.SendPacket((S_BasePacket)new S_ServerMessage(200));
          } 
        } else {
          pc.SendPacket((S_BasePacket)new S_ServerMessage(196));
        } 
      } else {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(196));
      } 
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
      sb.append(this.name);
      sb.append(" , ");
      sb.append(this.title);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
