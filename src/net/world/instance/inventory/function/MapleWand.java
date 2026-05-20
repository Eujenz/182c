package net.world.instance.inventory.function;

import net.database.PolymorphTable;
import net.database.bean.Item;
import net.database.bean.Poly;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectAdd;
import net.network.server.S_ObjectPoly;
import net.network.server.S_ServerMessage;
import net.network.server.S_ServerMessageYesNo;
import net.util.Util;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;
import net.world.time.ItemTimerInstance;

public class MapleWand extends ItemInstance {
  private static final int firstTime = 7200;
  
  public static final String name = "$260";
  
  public MapleWand(Item i) {
    super(i);
    setTime(7200);
    setName("$260");
    setHaveCount(Util.rand(5, 15));
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    cha.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)cha, 17), true);
    if (getHaveCount() == 0) {
      cha.SendPacket((S_BasePacket)new S_ServerMessage(79));
      return;
    } 
    if (getHaveCount() > 0) {
      setHaveCount(cha, getHaveCount() - 1);
      int id = bp.readD();
      L1Object o = cha.getObject(id);
      if (o != null || id == cha.getObjectId()) {
        Character character = null;
        if (id == cha.getObjectId())
          character = cha; 
        if (character instanceof PcInstance) {
          PcInstance pc = (PcInstance)character;
          if (figure((L1Object)cha, pc))
            result(cha, (L1Object)character); 
        } else if (character instanceof net.world.instance.MonsterInstance) {
        
        } 
      } 
    } 
  }
  
  public void isTimerStop(Character cha) {
    cha.setGfx(cha.getClassGfx());
    cha.SendPacket((S_BasePacket)new S_ObjectPoly((L1Object)cha), true);
  }
  
  public void isSetting() {
    setTime(7200);
  }
  
  public int getFirstTime() {
    return 7200;
  }
  
  private boolean figure(L1Object cha, PcInstance temp) {
    if (temp.isDead())
      return false; 
    if (cha.getObjectId() == temp.getObjectId())
      return true; 
    if (cha instanceof PcInstance) {
      PcInstance pc = (PcInstance)cha;
      return (Util.rand(0, pc.getLevel() + pc.getDynamicInt() * 2) > Util.rand(0, temp.getLevel() + temp.getMr()));
    } 
    return (Util.rand(0, 100) < 50);
  }
  
  private void result(Character cha, L1Object o) {
    if (o instanceof net.world.instance.MonsterInstance) {
      Poly p = PolymorphTable.getInstance().getPoly(Util.rand(1, 41));
      if (p != null) {
        o.setGfx(p.getPolyid());
        cha.SendPacket((S_BasePacket)new S_ObjectPoly(o), true);
        cha.SendPacket((S_BasePacket)new S_ObjectAdd(o), true);
        return;
      } 
    } 
    if (o.getInventory() != null && o.getInventory().RingOfPolymorphControl()) {
      if (o instanceof PcInstance) {
        PcInstance pc = (PcInstance)o;
        pc.setpolyitem(this);
      } 
      o.SendPacket((S_BasePacket)new S_ServerMessageYesNo(180));
    } else {
      Poly p = PolymorphTable.getInstance().getPoly(Util.rand(1, 41));
      if (p != null) {
        BuffTimerInstance.getInstance().remove(o, 43);
        ItemTimerInstance.getInstance().remove((Character)o, "$971");
        ItemTimerInstance.getInstance().remove((Character)o, "$260");
        ItemTimerInstance.getInstance().add((Character)o, this);
        PolymorphTable.getInstance().polyEquipped(o, p);
        o.setGfx(p.getPolyid());
        o.SendPacket((S_BasePacket)new S_ObjectPoly(o), true);
        o.SendPacket((S_BasePacket)new S_ServerMessage(241, cha.getName()));
      } 
    } 
  }
}
