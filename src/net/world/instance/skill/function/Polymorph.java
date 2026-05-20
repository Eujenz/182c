package net.world.instance.skill.function;

import net.database.PolymorphTable;
import net.database.bean.Poly;
import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ObjectPoly;
import net.network.server.S_ServerMessageYesNo;
import net.util.Util;
import net.world.instance.ItemInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;

public class Polymorph extends Magic {
  public static final int id = 43;
  
  public Polymorph(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    Character character = null;
    L1Object o = this.operator.getObject(id);
    if (id == this.operator.getObjectId())
      character = this.operator; 
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    ItemInstance item = this.operator.getInventory().getItemNameId("$261");
    boolean hasring = false;
    if (item != null && 
      item.isEquipped())
      hasring = true; 
    if (hasring) {
      character.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)character, getSkill().getCastGfx()), true);
      character.SendPacket((S_BasePacket)new S_ServerMessageYesNo(180));
    } else {
      Poly p = PolymorphTable.getInstance().getPoly(Util.rand(1, 41));
      if (p != null) {
        PolymorphTable.getInstance().polyEquipped((L1Object)character, p);
        character.setGfx(p.getPolyid());
        character.SendPacket((S_BasePacket)new S_ObjectPoly((L1Object)character), true);
        character.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)character, getSkill().getCastGfx()), true);
        BuffTimerInstance.getInstance().remove((L1Object)this.operator, this);
        BuffTimerInstance.getInstance().add((L1Object)this.operator, this);
      } 
    } 
  }
  
  public void toMagic(L1Object pc, int id) {
    L1Object o = pc;
    Poly p = PolymorphTable.getInstance().getPoly(id);
    if (p != null) {
      PolymorphTable.getInstance().polyEquipped(o, p);
      o.setGfx(p.getPolyid());
      o.SendPacket((S_BasePacket)new S_ObjectPoly(o), true);
      BuffTimerInstance.getInstance().remove((L1Object)this.operator, this);
      BuffTimerInstance.getInstance().add((L1Object)this.operator, this);
    } 
  }
  
  public void isTimerStop(L1Object o) {
    o.setGfx(o.getClassGfx());
    o.SendPacket((S_BasePacket)new S_ObjectPoly(o), true);
  }
}
