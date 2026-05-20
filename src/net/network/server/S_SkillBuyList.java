package net.network.server;

import net.util.Util;
import net.world.instance.PcInstance;
import net.world.instance.skill.Magic;

public class S_SkillBuyList extends S_BasePacket {
  public S_SkillBuyList(PcInstance cha, int t) {
    int size = 1;
    writeC(78);
    writeD(100);
    writeH(size);
    writeD(t);
  }
  
  public S_SkillBuyList(PcInstance cha) {
    Magic[] list = cha.getSkill().getAll();
    int size = skillCount(cha, list);
    writeC(78);
    writeD(100);
    writeH(size);
    Skill(cha, list, size);
  }
  
  private void Skill(PcInstance cha, Magic[] skill, int count) {
    boolean 체크 = false;
    switch (cha.getClassType()) {
      case 0:
        if (count > 0) {
          if (cha.getLevel() >= 10 && cha.getLevel() < 20) {
            for (int i = 0; i <= 5; i++) {
              byte b;
              int j;
              Magic[] arrayOfMagic;
              for (j = (arrayOfMagic = skill).length, b = 0; b < j; ) {
                Magic m = arrayOfMagic[b];
                if (m.getSkill().getSkill_id() == i + 1) {
                  체크 = true;
                  break;
                } 
                b++;
              } 
              if (!체크)
                writeD(i); 
              체크 = false;
            } 
            break;
          } 
          if (cha.getLevel() >= 10 && cha.getLevel() >= 20)
            for (int i = 0; i <= 10; i++) {
              byte b;
              int j;
              Magic[] arrayOfMagic;
              for (j = (arrayOfMagic = skill).length, b = 0; b < j; ) {
                Magic m = arrayOfMagic[b];
                if (m.getSkill().getSkill_id() == i + 1) {
                  체크 = true;
                  break;
                } 
                b++;
              } 
              if (!체크)
                if (i > 4 && i < 10) {
                  writeD(i + 3);
                } else if (i > 9) {
                  writeD(i + 6);
                } else {
                  writeD(i);
                }  
              체크 = false;
            }  
          break;
        } 
        writeH(1);
        writeD(120);
        break;
      case 1:
        if (count > 0) {
          if (cha.getLevel() >= 50)
            for (int i = 0; i <= 5; i++) {
              byte b;
              int j;
              Magic[] arrayOfMagic;
              for (j = (arrayOfMagic = skill).length, b = 0; b < j; ) {
                Magic m = arrayOfMagic[b];
                if (m.getSkill().getSkill_id() == i + 1) {
                  체크 = true;
                  break;
                } 
                b++;
              } 
              if (!체크)
                writeD(i); 
              체크 = false;
            }  
          break;
        } 
        writeH(1);
        writeD(120);
        break;
      case 2:
        if (count > 0) {
          if (cha.getLevel() >= 8 && cha.getLevel() < 16) {
            for (int i = 0; i <= 5; i++) {
              byte b;
              int j;
              Magic[] arrayOfMagic;
              for (j = (arrayOfMagic = skill).length, b = 0; b < j; ) {
                Magic m = arrayOfMagic[b];
                if (m.getSkill().getSkill_id() == i + 1) {
                  체크 = true;
                  break;
                } 
                b++;
              } 
              if (!체크)
                writeD(i); 
              체크 = false;
            } 
            break;
          } 
          if (cha.getLevel() >= 8 && cha.getLevel() < 24) {
            for (int i = 0; i <= 10; i++) {
              byte b;
              int j;
              Magic[] arrayOfMagic;
              for (j = (arrayOfMagic = skill).length, b = 0; b < j; ) {
                Magic m = arrayOfMagic[b];
                if (m.getSkill().getSkill_id() == i + 1) {
                  체크 = true;
                  break;
                } 
                b++;
              } 
              if (!체크)
                if (i > 4 && i < 10) {
                  writeD(i + 3);
                } else if (i > 9) {
                  writeD(i + 6);
                } else {
                  writeD(i);
                }  
              체크 = false;
            } 
            break;
          } 
          if (cha.getLevel() >= 8 && cha.getLevel() >= 24)
            for (int i = 0; i <= 15; i++) {
              byte b;
              int j;
              Magic[] arrayOfMagic;
              for (j = (arrayOfMagic = skill).length, b = 0; b < j; ) {
                Magic m = arrayOfMagic[b];
                if (m.getSkill().getSkill_id() == i + 1) {
                  체크 = true;
                  break;
                } 
                b++;
              } 
              if (!체크)
                if (i > 4 && i < 10) {
                  writeD(i + 3);
                } else if (i > 9) {
                  writeD(i + 6);
                } else {
                  writeD(i);
                }  
              체크 = false;
            }  
          break;
        } 
        writeH(1);
        writeD(120);
        break;
      case 3:
        if (count > 0) {
          if (cha.getLevel() >= 4 && cha.getLevel() < 8) {
            for (int i = 0; i <= 5; i++) {
              byte b;
              int j;
              Magic[] arrayOfMagic;
              for (j = (arrayOfMagic = skill).length, b = 0; b < j; ) {
                Magic m = arrayOfMagic[b];
                if (m.getSkill().getSkill_id() == i + 1) {
                  체크 = true;
                  break;
                } 
                b++;
              } 
              if (!체크)
                writeD(i); 
              체크 = false;
            } 
            break;
          } 
          if (cha.getLevel() >= 4 && cha.getLevel() < 12) {
            for (int i = 0; i <= 10; i++) {
              byte b;
              int j;
              Magic[] arrayOfMagic;
              for (j = (arrayOfMagic = skill).length, b = 0; b < j; ) {
                Magic m = arrayOfMagic[b];
                if (m.getSkill().getSkill_id() == i + 1) {
                  체크 = true;
                  break;
                } 
                b++;
              } 
              if (!체크)
                if (i > 4 && i < 10) {
                  writeD(i + 3);
                } else if (i > 9) {
                  writeD(i + 6);
                } else {
                  writeD(i);
                }  
              체크 = false;
            } 
            break;
          } 
          if (cha.getLevel() >= 4 && cha.getLevel() >= 12)
            for (int i = 0; i <= 15; i++) {
              byte b;
              int j;
              Magic[] arrayOfMagic;
              for (j = (arrayOfMagic = skill).length, b = 0; b < j; ) {
                Magic m = arrayOfMagic[b];
                if (m.getSkill().getSkill_id() == i + 1) {
                  체크 = true;
                  break;
                } 
                b++;
              } 
              if (!체크)
                if (i > 4 && i < 10) {
                  writeD(i + 3);
                } else if (i > 9) {
                  writeD(i + 6);
                } else {
                  writeD(i);
                }  
              체크 = false;
            }  
          break;
        } 
        writeH(1);
        writeD(120);
        break;
    } 
  }
  
  private int skillCount(PcInstance cha, Magic[] skill) {
    int 스킬갯수 = 0;
    int 토탈 = 0;
    switch (cha.getClassType()) {
      case 0:
        if (cha.getLevel() >= 10 && cha.getLevel() < 20) {
          토탈 = 5;
          break;
        } 
        if (cha.getLevel() >= 10 && cha.getLevel() >= 20)
          토탈 = 10; 
        break;
      case 1:
        if (cha.getLevel() >= 50)
          토탈 = 5; 
        break;
      case 2:
        if (cha.getLevel() >= 8 && cha.getLevel() < 16) {
          토탈 = 5;
          break;
        } 
        if (cha.getLevel() >= 8 && cha.getLevel() < 24) {
          토탈 = 10;
          break;
        } 
        if (cha.getLevel() >= 8 && cha.getLevel() >= 24)
          토탈 = 15; 
        break;
      case 3:
        if (cha.getLevel() >= 4 && cha.getLevel() < 8) {
          토탈 = 5;
          break;
        } 
        if (cha.getLevel() >= 4 && cha.getLevel() < 12) {
          토탈 = 10;
          break;
        } 
        if (cha.getLevel() >= 4 && cha.getLevel() >= 12)
          토탈 = 15; 
        break;
      case 4:
        if (cha.getLevel() >= 12 && cha.getLevel() < 24) {
          토탈 = 5;
          break;
        } 
        if (cha.getLevel() >= 12 && cha.getLevel() >= 24)
          토탈 = 10; 
        break;
    } 
    byte b;
    int i;
    Magic[] arrayOfMagic;
    for (i = (arrayOfMagic = skill).length, b = 0; b < i; ) {
      Magic m = arrayOfMagic[b];
      if (m.getSkill().getSkill_id() <= 토탈)
        스킬갯수++; 
      b++;
    } 
    return 토탈 - 스킬갯수;
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
