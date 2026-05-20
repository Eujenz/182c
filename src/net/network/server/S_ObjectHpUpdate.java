package net.network.server;

import net.world.object.Character;

public class S_ObjectHpUpdate extends S_BasePacket {
  public S_ObjectHpUpdate(Character cha) {
    writeC(13);
    writeH(cha.getCurrentHp());
    writeH(cha.getTotalHp());
  }
}
