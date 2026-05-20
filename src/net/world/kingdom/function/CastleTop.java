package net.world.kingdom.function;

import net.Config;
import net.database.NpcTable;
import net.database.bean.Npc;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectRestore;
import net.world.kingdom.Kingdom;
import net.world.object.Character;
import net.world.object.L1Object;

public class CastleTop extends Character {
  private Kingdom k;
  
  private CrownVisual crownvisual;
  
  private Crown crown;
  
  public CastleTop(Kingdom k, Npc n) {
    this.k = k;
    setName(n.get_nameid());
    setGfx(n.get_gfxid());
    setGfxMode(n.get_gfxMode());
    setClassGfx(n.get_gfxid());
    setClassGfxMode(n.get_gfxMode());
    setMaxHp(n.getHp());
    super.setCurrentHp(n.getHp());
  }
  
  public void toDead() {
    setGfxMode(35);
    SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this, getGfxMode()), true);
    addCrown();
  }
  
  public void setCurrentHp(int currentHp) {
    if (this.k.isWar() && !isDead()) {
      int hp = (int)((currentHp / getTotalHp()) * 100.0D);
      int mode = 0;
      if (hp > 50) {
        mode = 32;
      } else if (hp > 20) {
        mode = 33;
      } else {
        mode = 34;
      } 
      if (getGfxMode() != mode) {
        setGfxMode(mode);
        SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this, getGfxMode()), true);
      } 
      super.setCurrentHp(currentHp);
    } 
  }
  
  public void toAttack(L1Object target, int type) {
    this.k.toAttack(target, type);
  }
  
  public void toRevival(L1Object own) {
    if (own == null) {
      setGfxMode(getClassGfxMode());
      setDelete(false);
      setDead(false);
      setPoison(false);
      setCurrentHp(getTotalHp());
      setCurrentMp(getTotalMp());
      SendPacket((S_BasePacket)new S_ObjectRestore(own, (L1Object)this), true);
    } 
  }
  
  private void addCrown() {
    Npc n1 = NpcTable.getInstance().getNpcTemplate(514);
    Npc n2 = NpcTable.getInstance().getNpcTemplate(600);
    if (n1 != null && n2 != null) {
      this.crownvisual = new CrownVisual();
      this.crownvisual.setObjectId(Config.getObjectID_ETC());
      this.crownvisual.setName(n1.get_nameid());
      this.crownvisual.setGfx(n1.get_gfxid());
      this.crownvisual.setClassGfx(n1.get_gfxid());
      this.crownvisual.setGfxMode(n1.get_gfxMode());
      this.crownvisual.setClassGfxMode(n1.get_gfxMode());
      this.crownvisual.setX(getX());
      this.crownvisual.setY(getY());
      this.crownvisual.setMap(getMap());
      this.crownvisual.toTeleport(this.crownvisual.getX(), this.crownvisual.getY(), this.crownvisual.getMap());
      this.crown = new Crown(this.k, this.crownvisual);
      this.crown.setObjectId(Config.getObjectID_ETC());
      this.crown.setName(n2.get_nameid());
      this.crown.setGfx(n2.get_gfxid());
      this.crown.setClassGfx(n2.get_gfxid());
      this.crown.setGfxMode(n2.get_gfxMode());
      this.crown.setClassGfxMode(n2.get_gfxMode());
      this.crown.setX(getX());
      this.crown.setY(getY());
      this.crown.setMap(getMap());
      this.crown.toTeleport(this.crown.getX(), this.crown.getY(), this.crown.getMap());
    } 
  }
}
