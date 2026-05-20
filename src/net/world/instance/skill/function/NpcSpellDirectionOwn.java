package net.world.instance.skill.function;

import java.util.ArrayList;
import java.util.List;
import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAttackMagic;
import net.world.function.ClanSystem;
import net.world.function.SummonSystem;
import net.world.instance.skill.Magic;
import net.world.kingdom.Kingdom;
import net.world.object.Character;
import net.world.object.L1Object;

public final class NpcSpellDirectionOwn extends Magic {
  private List<L1Object> list;
  
  public NpcSpellDirectionOwn(Character cha, Skill skill) {
    super(cha, skill);
    this.list = new ArrayList<L1Object>();
  }
  
  public void toMagic(int id) {
    this.list.clear();
    if (HpMpCheck() && ConsumeCount()) {
      if (this.operator.isInvis())
        Detection.invis((L1Object)this.operator); 
      this.list.clear();
      byte b;
      int i;
      L1Object[] arrayOfL1Object;
      for (i = (arrayOfL1Object = this.operator.getObjectList()).length, b = 0; b < i; ) {
        L1Object o = arrayOfL1Object[b];
        if (!o.isDead() && !o.isDelete() && this.operator.getDistance(o.getX(), o.getY(), o.getMap(), getSkill().getRange())) {
          Dmage(o);
          if (o.getDmg() > 0)
            this.list.add(o); 
        } 
        b++;
      } 
      if (this.list.size() > 0)
        this.operator.calcheading(((L1Object)this.list.get(0)).getX(), ((L1Object)this.list.get(0)).getY()); 
      this.operator.SendPacket((S_BasePacket)new S_ObjectAttackMagic((L1Object)this.operator, this.list, MagicAction, true, getSkill().getCastGfx(), this.operator.getX(), this.operator.getY()), true);
    } 
  }
  
  private void Dmage(L1Object o) {
    if (o instanceof net.world.instance.SummonInstance || o instanceof net.world.instance.PcInstance) {
      if (o instanceof net.world.instance.SummonInstance && 
        o.getOwn().getObjectId() == this.operator.getObjectId())
        return; 
      if (o instanceof net.world.instance.PcInstance && 
        isClan(o))
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
}
