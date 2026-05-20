package net.world.npc;

import net.database.bean.Npc;
import net.network.server.S_BasePacket;
import net.network.server.S_ShowHtml;
import net.world.instance.PcInstance;
import net.world.kingdom.KingdomKent;

public class GuardKent extends Guard {
  public GuardKent(Npc npc) {
    super(npc);
    switch (npc.get_npcId()) {
      case 516:
        this.Areaatk = 8;
        break;
      case 517:
        this.Areaatk = 2;
        break;
    } 
  }
  
  public void Talk(PcInstance pc) {
    pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "dcguard6", getName(), KingdomKent.getInstance().getAgentNAME(), KingdomKent.getInstance().getClanNAME()));
  }
}
