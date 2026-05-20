package net.network.server;

import net.util.Util;

public class S_MapUse extends S_BasePacket {
  public S_MapUse(int objid, int itemid) {
    writeC(110);
    writeD(objid);
    switch (itemid) {
      case 40373:
        writeD(16);
        break;
      case 296:
        writeD(1);
        break;
      case 297:
        writeD(2);
        break;
      case 298:
        writeD(3);
        break;
      case 299:
        writeD(4);
        break;
      case 300:
        writeD(5);
        break;
      case 301:
        writeD(6);
        break;
      case 302:
        writeD(7);
        break;
      case 303:
        writeD(8);
        break;
      case 309:
        writeD(9);
        break;
      case 326:
        writeD(10);
        break;
      case 327:
        writeD(11);
        break;
      case 328:
        writeD(12);
        break;
      case 329:
        writeD(13);
        break;
      case 357:
        writeD(14);
        break;
      case 40388:
        writeD(15);
        break;
      case 40389:
        writeD(17);
        break;
      case 40390:
        writeD(18);
        break;
    } 
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
