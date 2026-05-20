package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ObjectRestore;
import net.network.server.S_ServerMessageYesNo;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;

public class NaturesMiracle extends Magic {
  public NaturesMiracle(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    if (HpMpCheck() && ConsumeCount()) {
      L1Object o = this.operator.getObject(id);
      if (o instanceof net.world.instance.PcInstance) {
        Character tg = (Character)o;
        if (tg != null && 
          tg.isDead()) {
          tg.setStatusNatures(true);
          tg.SendPacket((S_BasePacket)new S_ServerMessageYesNo(321));
        } 
      } else if (o instanceof net.world.instance.NpcInstance) {
        Character npc = (Character)o;
        if (npc != null && 
          npc.isDead()) {
          npc.clearFightList();
          npc.setDead(false);
          npc.setPoison(false);
          npc.setGfxMode(0);
          npc.SendPacket((S_BasePacket)new S_ObjectRestore((L1Object)this.operator, (L1Object)npc), true);
          npc.setCurrentHp(npc.getTotalHp());
          this.operator.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)npc, getSkill().getCastGfx()), true);
        } 
      } 
    } 
  }
}
