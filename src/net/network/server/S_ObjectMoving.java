package net.network.server;

import net.world.object.L1Object;

public class S_ObjectMoving extends S_BasePacket {
  public S_ObjectMoving(L1Object o) {
    int x = o.getX();
    int y = o.getY();
    switch (o.getHeading()) {
      case 0:
        y++;
        break;
      case 1:
        x--;
        y++;
        break;
      case 2:
        x--;
        break;
      case 3:
        x--;
        y--;
        break;
      case 4:
        y--;
        break;
      case 5:
        x++;
        y--;
        break;
      case 6:
        x++;
        break;
      case 7:
        x++;
        y++;
        break;
      default:
        return;
    } 
    writeC(18);
    writeD(o.getObjectId());
    writeH(x);
    writeH(y);
    writeC(o.getHeading());
  }
}
