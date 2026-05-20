package net.network.server;

import net.util.Util;

public class test extends S_BasePacket {
  public test() {
    writeC(Util.rand(0, 255));
    writeD(Util.rand(0, 268435455));
    writeD(Util.rand(0, 268435455));
  }
}
