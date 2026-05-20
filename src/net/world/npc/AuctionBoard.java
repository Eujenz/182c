package net.world.npc;

import net.network.server.S_AgitInfo;
import net.network.server.S_AgitList;
import net.network.server.S_AgitLocation;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.world.function.AgitSystem;
import net.world.function.ClanSystem;
import net.world.instance.PcInstance;
import net.world.object.L1Object;

public class AuctionBoard extends L1Object {
  public void Talk(PcInstance pc) {
    pc.SendPacket((S_BasePacket)new S_AgitList(this, pc));
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (text1.startsWith("select")) {
      pc.SendPacket((S_BasePacket)new S_AgitInfo(this, text2));
    } else if (text1.startsWith("map")) {
      pc.SendPacket((S_BasePacket)new S_AgitLocation(this, text2));
    } else if (text1.startsWith("apply")) {
      if (pc.getClassType() == 0 && pc.getClanId() > 0) {
        if (pc.getLevel() > 14) {
          if (!AgitSystem.getInstance().isBidder(pc.getName())) {
            if (ClanSystem.getInstance().getKingdom(pc) == null) {
              if (!AgitSystem.getInstance().CheckAgit(pc.getClanId())) {
                int gold = AgitSystem.getInstance().getAgitPrice(Integer.valueOf(text2).intValue());
                if (pc.getInventory().Aden(gold, true)) {
                  try {
                    AgitSystem.getInstance().updateAgit(pc, Integer.valueOf(text2).intValue());
                    pc.Message("恭喜你成功购得血盟小屋.");
                  } catch (Exception exception) {}
                } else {
                  pc.SendPacket((S_BasePacket)new S_ServerMessage(189));
                } 
              } else {
                pc.SendPacket((S_BasePacket)new S_ServerMessage(521));
              } 
            } else {
              pc.SendPacket((S_BasePacket)new S_ServerMessage(520));
            } 
          } else {
            pc.SendPacket((S_BasePacket)new S_ServerMessage(523));
          } 
        } else {
          pc.SendPacket((S_BasePacket)new S_ServerMessage(519));
        } 
      } else {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(518));
      } 
    } 
  }
}
