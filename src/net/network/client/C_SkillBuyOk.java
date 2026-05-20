package net.network.client;

import net.LineageClient;
import net.database.SkillTable;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.instance.PcInstance;
import net.world.instance.inventory.Inventory;
import net.world.instance.skill.Magic;
import net.world.object.Character;

public class C_SkillBuyOk extends C_BasePacket {
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    Inventory inv = pc.getInventory();
    if (inv == null) {
      pc.Message("什么都没带不能学魔法。");
      return;
    } 
    if (!inv.isSkillCheckHelmMagic(pc))
      return; 
    int count = readH();
    if (count > 0 && count < 31) {
      Magic[] s = new Magic[count];
      int totalAden = 0;
      for (int i = 0; i < count; i++) {
        int id = readD() + 1;
        if (id > 5 && id < 14) {
          id -= 3;
        } else if (id > 13) {
          id -= 6;
        } 
        if (id < 16) {
          s[i] = SkillTable.getInstance().getTemplate((Character)pc, id);
          totalAden += s[i].getSkill().getPrice();
        } 
      } 
      if (pc.getInventory().Aden(totalAden, true)) {
        byte b;
        int j;
        Magic[] arrayOfMagic;
        for (j = (arrayOfMagic = s).length, b = 0; b < j; ) {
          Magic ss = arrayOfMagic[b];
          pc.getSkill().add(ss);
          b++;
        } 
        pc.getSkill().sendList();
      } else {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(189));
      } 
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
