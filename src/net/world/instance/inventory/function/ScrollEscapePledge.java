package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectLock;
import net.network.server.S_ServerMessage;
import net.world.function.AgitSystem;
import net.world.function.ClanSystem;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.object.Character;
import net.world.object.L1Object;

public class ScrollEscapePledge extends ItemInstance {
  public ScrollEscapePledge(Item i) {
    super(i);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    setCount(cha, getCount() - 1L);
    byte b;
    int i, arrayOfInt[];
    for (i = (arrayOfInt = ScrollLabeledVERRYEDHORAE.Rmap).length, b = 0; b < i; ) {
      int m = arrayOfInt[b];
      if (cha.getMap() == m) {
        cha.SendPacket((S_BasePacket)new S_ServerMessage(647));
        cha.SendPacket((S_BasePacket)new S_ObjectLock());
        return;
      } 
      b++;
    } 
    boolean kingdom = (ClanSystem.getInstance().getKingdom((PcInstance)cha) != null);
    boolean agit = (cha.getClanId() > 0 && AgitSystem.getInstance().CheckAgit(cha.getClanId()));
    if (cha instanceof PcInstance && (kingdom || agit)) {
      PcInstance pc = (PcInstance)cha;
      if (kingdom) {
        ClanSystem.getInstance().getKingdom(pc).gotoKingdom(pc);
      } else {
        AgitSystem.getInstance().gotoAgit(pc);
      } 
    } else {
      byte b1;
      int j;
      int[] arrayOfInt1;
      for (j = (arrayOfInt1 = ScrollLabeledVERRYEDHORAE.Rmap).length, b1 = 0; b1 < j; ) {
        int m = arrayOfInt1[b1];
        if (cha.getMap() == m) {
          cha.SendPacket((S_BasePacket)new S_ServerMessage(647));
          cha.SendPacket((S_BasePacket)new S_ObjectLock());
          return;
        } 
        b1++;
      } 
      ScrollLabeledVERRYEDHORAE.Location((L1Object)cha);
    } 
    cha.toTeleport(cha.getTempX(), cha.getTempY(), cha.getTempMap());
  }
}
