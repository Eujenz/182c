package net.network.server;

import net.util.Util;
import net.world.object.L1Object;

public class S_ObjectAction extends S_BasePacket {
  private String log_object;
  
  public S_ObjectAction(L1Object cha) {
    this.log_object = cha.toString();
    writeC(32);
    writeD(cha.getObjectId());
    writeC(cha.getGfxMode());
  }
  
  public S_ObjectAction(L1Object cha, int id) {
    this.log_object = cha.toString();
    writeC(32);
    writeD(cha.getObjectId());
    writeC(id);
  }
  
  public S_ObjectAction(L1Object cha, int x, int y) {
    this.log_object = cha.toString();
    writeC(32);
    writeD(cha.getObjectId());
    writeC(cha.getGfxMode());
    writeH(x);
    writeH(y);
  }
  
  public S_ObjectAction(L1Object cha, int id, int x, int y) {
    this.log_object = cha.toString();
    writeC(32);
    writeD(cha.getObjectId());
    writeC(id);
    writeH(x);
    writeH(y);
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.log_object);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
