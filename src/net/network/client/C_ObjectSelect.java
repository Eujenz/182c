package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.instance.PcInstance;
import net.world.instance.PetInstance;
import net.world.object.L1Object;

public class C_ObjectSelect extends C_BasePacket {
  public synchronized void read(LineageClient lc, byte[] data) {
    PcInstance pcInstance1 = null;
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    int pet = readD();
    int type = readC();
    int targetId = readD();
    L1Object o = pc.getObject(targetId);
    L1Object pets = pc.getObject(pet);
    if (targetId == pc.getObjectId())
      pcInstance1 = pc; 
    if (pcInstance1 != null && !pcInstance1.isDead())
      if (pets instanceof PetInstance) {
        ((PetInstance)pets).set_Status(1);
        ((PetInstance)pets).setFight(true);
        ((PetInstance)pets).addAttackList((L1Object)pcInstance1);
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
