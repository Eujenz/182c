package net.network.client;

import net.LineageClient;
import net.util.Util;
import net.world.WorldInstance;
import net.world.function.ClanSystem;
import net.world.function.bean.Clan;
import net.world.instance.PcInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class C_RequestCharselete extends C_BasePacket {
  final Logger log = LoggerFactory.getLogger(C_RequestCharselete.class);
  
  static final String OUT_LOG = "IP[%s] 账号[%s] 角色[%s] 级别[%s] 经验值[%s] 小退游戏";
  
  public synchronized void read(LineageClient lc, byte[] data) {
    super.read(lc, data);
    if (lc == null)
      return; 
    PcInstance pc = lc.getPc();
    if (pc == null)
      return; 
    if (pc.isUseClanWareHouse()) {
      Clan clan = ClanSystem.getInstance().getClan(pc.getClanId());
      if (clan != null)
        clan.setLockWarehouse(false); 
    } 
    this.log.info(String.format("IP[%s] 账号[%s] 角色[%s] 级别[%s] 经验值[%s] 小退游戏", new Object[] { lc.getIP(), lc.getID(), pc.getName(), Integer.valueOf(pc.getLevel()), Long.valueOf(pc.getExp()) }));
    pc.toReset();
    pc.toDelete();
    WorldInstance.getInstance().removePc(pc);
    pc.setInventory(null);
    pc.setSkill(null);
    pc.setBooks(null);
    pc.setBuff(null);
    lc.setPc(null);
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
