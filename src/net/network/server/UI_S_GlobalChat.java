package net.network.server;

public final class UI_S_GlobalChat extends S_BasePacket {
  public UI_S_GlobalChat(String msg) {
    writeC(221);
    writeC(3);
    writeS(msg);
  }
}
