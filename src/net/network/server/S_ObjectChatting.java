package net.network.server;

import net.world.object.L1Object;

public class S_ObjectChatting extends S_BasePacket {
  public S_ObjectChatting(L1Object cha, String chat, boolean Shouting) {
    writeC(45);
    if (Shouting) {
      writeC(2);
    } else {
      writeC(0);
    } 
    writeD(cha.getObjectId());
    writeS(String.valueOf(cha.getName()) + ": " + chat);
  }
  
  public S_ObjectChatting(L1Object cha, String chat, int type) {
    StringBuilder sb;
    switch (type) {
      case 0:
        writeC(19);
        writeC(type);
        writeD(cha.getObjectId());
        writeS(String.valueOf(cha.getName()) + ": " + chat);
        writeH(cha.getX());
        writeH(cha.getY());
        break;
      case 2:
        writeC(19);
        writeC(type);
        writeD(cha.getObjectId());
        writeS("<" + cha.getName() + "> " + chat);
        break;
      case 3:
        writeC(15);
        writeC(type);
        sb = new StringBuilder();
        if (cha.isGm()) {
          sb.append("[******] ");
        } else {
          sb.append("[" + cha.getName() + "] ");
        } 
        sb.append(chat);
        writeS(sb.toString());
        break;
      case 4:
        writeC(15);
        writeC(type);
        writeS("{" + cha.getName() + "} " + chat);
        break;
      case 8:
        writeC(20);
        writeS(cha.getName());
        writeS(chat);
        break;
      case 9:
        writeC(15);
        writeC(type);
        writeS("-> (" + cha.getName() + ") " + chat);
        break;
      case 11:
        writeC(15);
        writeC(type);
        writeS("(" + cha.getName() + ") " + chat);
        break;
      case 20:
        writeC(15);
        writeC(9);
        writeS(chat);
        break;
      case 21:
        writeC(15);
        writeC(3);
        writeS(chat);
        break;
    } 
  }
  
  public S_ObjectChatting(L1Object cha, String chat, int type, int id) {
    writeC(19);
    writeC(type);
    writeD(id);
    writeS(String.valueOf(cha.getName()) + ": " + chat);
    writeH(cha.getX());
    writeH(cha.getY());
  }
}
