package net.network.server;

import net.world.instance.PcInstance;

public final class S_Dexup extends S_BasePacket {
  public S_Dexup(PcInstance pc, int time) {
    this(pc, pc.getDynamicDex(), time);
  }
  
  public S_Dexup(PcInstance pc, int type, int time) {
    writeC(108);
    writeH(time);
    writeC(pc.getDex());
    writeC(type);
    writeD(0);
  }
}
