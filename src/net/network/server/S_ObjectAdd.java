package net.network.server;

import net.Config;
import net.database.bean.Npc;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.object.L1Object;

public class S_ObjectAdd extends S_Inventory {
  public S_ObjectAdd(L1Object o) {
    String name = o.getName();
    String own = o.getOwnName();
    int hp = 255;
    int clanId = o.getClanId();
    if (o instanceof ItemInstance) {
      ItemInstance item = (ItemInstance)o;
      name = getName(item);
    } else if (o instanceof net.world.kingdom.function.DoorKingdom || o instanceof net.world.kingdom.function.KingdomGuard || o instanceof net.world.kingdom.function.CastleTop) {
      clanId = 0;
    } 
    writeC(11);
    writeH(o.getX());
    writeH(o.getY());
    writeD(o.getObjectId());
    writeH(o.getGfx());
    writeC(o.getGfxMode());
    writeC(o.getHeading());
    writeC(o.getLight());
    writeC(o.isSlow() ? 2 : (o.isSpeed() ? 1 : 0));
    writeD((int)o.getCount());
    writeH(o.getLawful());
    writeS(name);
    writeS(o.getTitle());
    writeC(o.getStatus());
    writeD(clanId);
    writeS(o.getClanName());
    writeS(own);
    writeC(0);
    writeC(hp);
    writeC(0);
    writeC(0);
    writeS(null);
    writeC(255);
    writeC(255);
  }
  
  public S_ObjectAdd(L1Object o, L1Object t) {
    String name = o.getName();
    String own = o.getOwnName();
    int hp = 255;
    int clanId = o.getClanId();
    if (o instanceof ItemInstance) {
      ItemInstance item = (ItemInstance)o;
      name = getName(item);
    } else if (o instanceof net.world.instance.SummonInstance) {
      if (own != null && own.equalsIgnoreCase(t.getName())) {
        double nowhp = o.getCurrentHp();
        double maxhp = o.getTotalHp();
        hp = (int)(nowhp / maxhp * 100.0D);
      } 
    } else if (o instanceof PcInstance) {
      if (o.getPartyId() > 0 && o.getPartyId() == t.getPartyId()) {
        double nowhp = o.getCurrentHp();
        double maxhp = o.getTotalHp();
        hp = (int)(nowhp / maxhp * 100.0D);
      } 
    } else if (o instanceof net.world.kingdom.function.DoorKingdom || o instanceof net.world.kingdom.function.KingdomGuard || o instanceof net.world.kingdom.function.CastleTop) {
      clanId = 0;
    } 
    writeC(11);
    writeH(o.getX());
    writeH(o.getY());
    writeD(o.getObjectId());
    writeH(o.getGfx());
    writeC(o.getGfxMode());
    writeC(o.getHeading());
    if (o.isGm() && o.isInvis()) {
      writeC(0);
    } else {
      writeC(o.getLight());
    } 
    writeC(o.isSlow() ? 2 : (o.isSpeed() ? 1 : 0));
    writeD((int)o.getCount());
    writeH(o.getLawful());
    writeS(name);
    writeS(o.getTitle());
    writeC(o.getStatus());
    writeD(clanId);
    writeS(o.getClanName());
    writeS(own);
    writeC(0);
    writeC(hp);
    writeC(0);
    writeC(0);
    writeS(null);
    writeC(255);
    writeC(255);
  }
  
  public S_ObjectAdd(Npc n, PcInstance o) {
    writeC(11);
    writeH(o.getX());
    writeH(o.getY());
    writeD(Config.getObjectID_ETC());
    writeH(n.get_gfxid());
    writeC(n.get_gfxMode());
    writeC(o.getHeading());
    writeC(o.getLight());
    writeC(o.isSlow() ? 2 : (o.isSpeed() ? 1 : 0));
    writeD((int)o.getCount());
    writeH(o.getLawful());
    writeS(null);
    writeS(o.getTitle());
    writeC(o.getStatus());
    writeD(o.getClanId());
    writeS(o.getClanName());
    writeS(null);
    writeC(0);
    writeC(255);
    writeC(0);
    writeC(0);
    writeS(null);
    writeC(255);
    writeC(255);
  }
}
