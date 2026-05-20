package net.network.server;

import net.util.Util;
import net.world.instance.PcInstance;

public class S_SkillAdd extends S_BasePacket {
  public S_SkillAdd(int lv1, int lv2, int lv3, int lv4, int lv5, int lv6, int lv7, int lv8, int lv9, int lv10) {
    writeC(30);
    writeC(10);
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
  }
  
  public S_SkillAdd(PcInstance pc, int mode, int dx) {
    byte skill_list_values = 32;
    writeC(30);
    writeC(32);
    for (int i = 0; i < 28; i++) {
      if (i == mode) {
        writeC(dx);
      } else {
        writeC(0);
      } 
    } 
  }
  
  public S_SkillAdd(int lv1, int lv2, int lv3, int lv4, int lv5, int lv6, int lv7, int lv8, int lv9, int lv10, int elf1, int elf2, int elf3, int elf4, int elf5, int crown, int knight1, int knight2) {
    writeC(30);
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
    writeC(knight1);
    writeC(knight2);
    writeC(0);
    writeC(0);
    writeC(crown);
    writeC(0);
    writeC(elf1);
    writeC(elf2);
    writeC(elf3);
    writeC(elf4);
    writeC(elf5);
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
