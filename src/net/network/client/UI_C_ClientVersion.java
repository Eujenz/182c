package net.network.client;

import net.LineageClient;
import net.network.server.S_BasePacket;
import net.network.server.UI_S_ServerVersion;
import net.world.WorldInstance;

public final class UI_C_ClientVersion extends C_BasePacket {
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    String message = readS();
    if ("验证服务端版本".equalsIgnoreCase(message)) {
      int value = readC();
      if (value == 2) {
        lc.SendPacket((S_BasePacket)new UI_S_ServerVersion());
        lc.setUI(true);
        WorldInstance.getInstance().addUIClient(lc);
      } 
    } 
  }
}
