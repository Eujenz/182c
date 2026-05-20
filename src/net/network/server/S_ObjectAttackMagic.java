package net.network.server;

import java.util.List;
import net.Config;
import net.world.object.L1Object;

public class S_ObjectAttackMagic extends S_BasePacket {
  public S_ObjectAttackMagic(L1Object cha, int t_objid, int t_x, int t_y, int action, int dmg, int gfx) {
    writeC(35);
    writeC(action);
    writeD(cha.getObjectId());
    writeD(t_objid);
    writeC(dmg);
    writeC(cha.getHeading());
    writeD(Config.getObjectID_ETC());
    writeH(gfx);
    writeC(6);
    writeH(cha.getX());
    writeH(cha.getY());
    writeH(t_x);
    writeH(t_y);
    writeH(0);
    writeC(0);
  }
  
  public S_ObjectAttackMagic(L1Object cha, L1Object temp, int action, int dmg, int gfx) {
    writeC(35);
    writeC(action);
    writeD(cha.getObjectId());
    writeD(temp.getObjectId());
    writeC(dmg);
    writeC(cha.getHeading());
    writeD(Config.getObjectID_ETC());
    writeH(gfx);
    writeC(6);
    writeH(cha.getX());
    writeH(cha.getY());
    writeH(temp.getX());
    writeH(temp.getY());
    writeH(0);
    writeC(0);
  }
  
  public S_ObjectAttackMagic(L1Object cha, List<L1Object> list, int action, boolean none, int gfx, int x, int y) {
    writeC(57);
    writeC(action);
    writeD(cha.getObjectId());
    if (none) {
      writeH(cha.getX());
      writeH(cha.getY());
      writeC(cha.getHeading());
      writeD(Config.getObjectID_ETC());
      writeH(gfx);
      writeC(0);
      writeH(0);
      writeH(list.size());
      for (L1Object o : list) {
        writeD(o.getObjectId());
        writeC(o.getDmg());
      } 
    } else {
      if (gfx == 170 || gfx == 171) {
        writeH(cha.getX());
        writeH(cha.getY());
      } else {
        writeH(x);
        writeH(y);
      } 
      writeC(cha.getHeading());
      writeD(Config.getObjectID_ETC());
      writeH(gfx);
      writeC(8);
      writeH(0);
      writeH(list.size());
      for (L1Object o : list) {
        writeD(o.getObjectId());
        writeC(o.getDmg());
      } 
    } 
  }
}
