package net.network.server;

import net.world.instance.PcInstance;

public final class S_Strup extends S_BasePacket {
  public S_Strup(PcInstance pc, int time) {
    this(pc, pc.getDynamicStr(), time);
  }
  
  public S_Strup(PcInstance pc, int type, int time) {
    writeC(107);
    writeH(time);
    writeC(pc.getStr());
    writeC(pc.getInventory().getWeight());
    writeC(type);
    writeD(0);
  }
}
