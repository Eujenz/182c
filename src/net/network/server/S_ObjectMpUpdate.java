package net.network.server;

import net.world.object.Character;

public class S_ObjectMpUpdate extends S_BasePacket {
  public S_ObjectMpUpdate(Character cha) {
    writeC(77);
    writeH(cha.getCurrentMp());
    writeH(cha.getTotalMp());
  }
}
