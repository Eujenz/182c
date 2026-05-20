package net.network.server;

import net.Config;
import net.world.object.Character;
import net.world.object.L1Object;

public class S_ObjectAttack extends S_BasePacket {
  public S_ObjectAttack(Character cha, L1Object target, int action, int dmg, int effectId, boolean bow, boolean arrow) {
    writeC(35);
    writeC(action);
    writeD(cha.getObjectId());
    if (target != null) {
      writeD(target.getObjectId());
    } else {
      writeD(0);
    } 
    writeC(dmg);
    writeC(cha.getHeading());
    if (bow) {
      bow(cha, target, effectId, arrow);
    } else {
      weapon(cha, target, effectId);
    } 
  }
  
  private void bow(Character cha, L1Object target, int effectId, boolean arrow) {
    if (arrow) {
      writeD(Config.getObjectID_ETC());
      writeH(effectId);
      writeC(0);
      writeH(cha.getX());
      writeH(cha.getY());
      if (target != null) {
        writeH(target.getX());
        writeH(target.getY());
      } else {
        int locx = 0;
        int locy = 0;
        switch (cha.getHeading()) {
          case 0:
            locy -= 15;
            break;
          case 1:
            locx += 15;
            locy -= 15;
            break;
          case 2:
            locx += 15;
            break;
          case 3:
            locx += 15;
            locy += 15;
            break;
          case 4:
            locy += 15;
            break;
          case 5:
            locx -= 15;
            locy += 15;
            break;
          case 6:
            locx -= 15;
            break;
          case 7:
            locx -= 15;
            locy -= 15;
            break;
        } 
        writeH(cha.getX() + locx);
        writeH(cha.getY() + locy);
      } 
      writeH(0);
      writeC(0);
    } else {
      writeD(0);
      writeC(0);
    } 
  }
  
  private void weapon(Character cha, L1Object target, int effectId) {
    if (effectId == 0)
      writeD(0); 
    writeC(0);
  }
}
