package net.world.instance.skill.function;

import java.util.ArrayList;
import java.util.List;
import net.Config;
import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectAdd;
import net.network.server.S_ObjectHeading;
import net.network.server.S_ObjectRemove;
import net.util.Util;
import net.world.function.ClanSystem;
import net.world.function.SummonSystem;
import net.world.instance.MonsterInstance;
import net.world.instance.skill.Magic;
import net.world.kingdom.Kingdom;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;

public class Firewall extends Magic {
  private int x;
  
  private int y;
  
  private MonsterInstance mon;
  
  private List<L1Object> list;
  
  private List<MonsterInstance> monlist;
  
  private L1Object fire;
  
  private L1Object fire2;
  
  private int firex;
  
  private int firey;
  
  public Firewall(Character cha, Skill skill) {
    super(cha, skill);
    this.list = new ArrayList<L1Object>();
  }
  
  public void toMagic(int tx, int ty) {
    if (this.operator.isInvis())
      Detection.invis((L1Object)this.operator); 
    this.x = tx;
    this.y = ty;
    isTimerStop((L1Object)null);
    this.operator.setHeading(this.operator.calcheading(this.x, this.y));
    this.operator.SendPacket((S_BasePacket)new S_ObjectHeading((L1Object)this.operator), true);
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    BuffTimerInstance.getInstance().remove((L1Object)this.operator, this);
    BuffTimerInstance.getInstance().add((L1Object)this.operator, this);
  }
  
  public void isTimer(L1Object o) {
    if (this.fire != null || this.fire2 != null)
      toFire(); 
  }
  
  private void toFire() {
    this.list.clear();
    for (L1Object o : this.operator.getObjectList()) {
      if (!o.isDead() && !o.isDelete() && ((this.firex == o.getX() && this.firey == o.getY()) || (this.x == o.getX() && this.y == o.getY()))) {
        Dmage(o);
        if (o.getDmg() > 0)
          this.list.add(o); 
      } 
    } 
  }
  
  private void Dmage(L1Object o) {
    if (o instanceof net.world.instance.SummonInstance || o instanceof net.world.instance.PcInstance) {
      if (o instanceof net.world.instance.SummonInstance && o.getOwn().getObjectId() == this.operator.getObjectId())
        return; 
      if (o instanceof net.world.instance.PcInstance && isClan(o))
        return; 
      if (this.operator instanceof net.world.instance.PcInstance) {
        Kingdom k = ClanSystem.getInstance().isKingdomZone(o);
        if (k == null || !k.isWar())
          return; 
      } 
    } 
    int dmg = Damage(o, true);
    o.setDmg(dmg);
    if (dmg > 0) {
      this.operator._dmg = dmg;
      o.toAttack((L1Object)this.operator, 3);
      o.setCurrentHp(o.getCurrentHp() - dmg);
      SummonSystem.getInstance().toAttack(this.operator, o);
    } 
  }
  
  public void isTimerRun(L1Object o) {
    this.operator.fire = new L1Object();
    this.operator.fire.setObjectId(Config.getObjectID_ETC());
    this.operator.fire.setGfx(168);
    this.operator.fire.toTeleport(this.x, this.y, this.operator.getMap());
    this.operator.SendPacket((S_BasePacket)new S_ObjectAdd(this.operator.fire), true);
    this.fire = this.operator.fire;
    this.operator.fire2 = new L1Object();
    this.operator.fire2.setObjectId(Config.getObjectID_ETC());
    this.operator.fire2.setGfx(168);
    do {
      this.firex = this.x + Util.rand(0, 2) - 1;
      this.firey = this.y + Util.rand(0, 2) - 1;
    } while (this.firex == this.x && this.firey == this.y);
    this.operator.fire2.toTeleport(this.firex, this.firey, this.operator.getMap());
    this.operator.SendPacket((S_BasePacket)new S_ObjectAdd(this.operator.fire2), true);
    this.fire2 = this.operator.fire2;
  }
  
  public void isTimerStop(L1Object o) {
    if (this.fire != null) {
      this.operator.SendPacket((S_BasePacket)new S_ObjectRemove(this.fire), true);
      this.fire.toDelete();
      this.fire = null;
      this.operator.fire = null;
    } 
    if (this.fire2 != null) {
      this.operator.SendPacket((S_BasePacket)new S_ObjectRemove(this.fire2), true);
      this.fire2.toDelete();
      this.fire = null;
      this.operator.fire2 = null;
    } 
  }
}
