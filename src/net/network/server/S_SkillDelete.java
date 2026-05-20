package net.network.server;

import net.util.Util;

public class S_SkillDelete extends S_BasePacket {
  public S_SkillDelete(int lv1, int lv2, int lv3, int lv4, int lv5, int lv6, int lv7, int lv8, int lv9, int lv10, int elf1, int elf2, int elf3) {
    writeC(31);
    writeC(32);
    writeC(lv1);
    writeC(lv2);
    writeC(lv3);
    writeC(lv4);
    writeC(lv5);
    writeC(lv6);
    writeC(lv7);
    writeC(lv8);
    writeC(lv9);
    writeC(lv10);
    writeC(0);
    writeC(0);
    writeC(0);
    writeC(0);
    writeC(0);
    writeC(0);
    writeC(elf1);
    writeC(elf2);
    writeC(elf3);
    writeC(0);
    writeC(0);
    writeC(0);
    writeD(0);
    writeD(0);
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    return sb.toString();
  }
}
