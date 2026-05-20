package net.network.server;

import net.Config;

public final class UI_S_ServerVersion extends S_BasePacket {
  public UI_S_ServerVersion() {
    writeC(222);
    writeS("验证服务端版本");
    writeC(2);
    writeC(Config.CLIENT_LANGUAGE);
  }
}
