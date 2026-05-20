package net.world.kingdom.function;

import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.kingdom.Kingdom;
import net.world.object.L1Object;

public class Doorman extends L1Object {
  private boolean SideType;
  
  private Kingdom k;
  
  private int npc_id;
  
  public Doorman(Kingdom k, int npc_id) {
    this.k = k;
    this.npc_id = npc_id;
  }
  
  public boolean isSideType() {
    return this.SideType;
  }
  
  public void setSideType(boolean sideType) {
    this.SideType = sideType;
  }
  
  public void Talk(PcInstance pc) {
    if (this.k.getClanID() == pc.getClanId()) {
      if (this.SideType) {
        if (pc.getClassType() == 0) {
          pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "gateokeeper", pc.getName()));
        } else {
          pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "gatekeeper", pc.getName()));
        } 
      } else {
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "gatekeeper2"));
      } 
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "gatekeeperop"));
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.equalsIgnoreCase("openegate")) {
      this.k.DoorOpenClose(true, false);
    } else if (text1.equalsIgnoreCase("closeegate")) {
      this.k.DoorOpenClose(false, false);
    } else if (text1.equalsIgnoreCase("openigate")) {
      this.k.DoorOpenClose(true, true);
    } else if (text1.equalsIgnoreCase("closeigate")) {
      this.k.DoorOpenClose(false, true);
    } 
  }
}
