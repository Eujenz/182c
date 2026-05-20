package net.world.instance;

import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessageYesNo;
import net.network.server.S_ShowHtml;
import net.world.function.AgitSystem;
import net.world.object.L1Object;

public class AgitInstance extends L1Object {
  private int agitId;
  
  public AgitInstance(int agitId) {
    this.agitId = agitId;
  }
  
  public void Talk(PcInstance pc) {
    if (pc.getClanId() != 0 && AgitSystem.getInstance().getClanId(this.agitId) == pc.getClanId()) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "agit", getName(), AgitSystem.getInstance().getAgitName(this.agitId)));
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "agdeny"));
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (pc.getClanId() != 0 && AgitSystem.getInstance().getClanId(this.agitId) == pc.getClanId()) {
      if (text1.equalsIgnoreCase("open")) {
        AgitSystem.getInstance().OpenDoor(pc, this);
      } else if (text1.equalsIgnoreCase("close")) {
        AgitSystem.getInstance().CloseDoor(pc, this);
      } else if (text1.equalsIgnoreCase("expel")) {
        AgitSystem.getInstance().Expel(this);
      } else if (!text1.equalsIgnoreCase("pay")) {
        if (text1.equalsIgnoreCase("sell")) {
          AgitSystem.getInstance().Agsell(pc, this);
        } else if (text1.equalsIgnoreCase("name")) {
          pc.SendPacket((S_BasePacket)new S_ServerMessageYesNo(512));
          pc.agit = this;
        } else if (!text1.equalsIgnoreCase("rem")) {
          text1.startsWith("tel");
        } 
      } 
    } else {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "agdeny"));
    } 
  }
  
  public void OpenDoor(DoorInstance door) {}
  
  public void CloseDoor(DoorInstance door) {}
  
  public int getAgitLocationIdx() {
    return 0;
  }
  
  public int getAgitId() {
    return this.agitId;
  }
}
