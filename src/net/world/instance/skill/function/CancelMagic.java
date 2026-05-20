package net.world.instance.skill.function;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.WorldMap;
import net.world.instance.MonsterInstance;
import net.world.instance.NpcInstance;
import net.world.instance.PcInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.pc.L1PinkName;
import net.world.time.BuffTimerInstance;
import net.world.time.ItemTimerInstance;

public class CancelMagic extends Magic {
  public CancelMagic(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    this.operator.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this.operator, MagicAction2), true);
    if (HpMpCheck() && ConsumeCount()) {
      L1Object o = this.operator.getObject(id);
      if (o != null || id == this.operator.getObjectId()) {
        Character character = null;
        L1PinkName.execute((L1Object)this.operator, o);
        if (id == this.operator.getObjectId())
          character = this.operator; 
        if (this.operator.isInvis())
          Detection.invis((L1Object)this.operator); 
        if ((Figure((L1Object)character) || isClan((L1Object)character)) && character instanceof Character) {
          if (character instanceof MonsterInstance) {
            MonsterInstance mon = (MonsterInstance)character;
            if (mon.isBoss())
              return; 
          } 
          character.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)character, getSkill().getCastGfx()), true);
          BuffTimerInstance.getInstance().remove((L1Object)character, 2);
          BuffTimerInstance.getInstance().remove((L1Object)character, 3);
          BuffTimerInstance.getInstance().remove((L1Object)character, 17);
          BuffTimerInstance.getInstance().remove((L1Object)character, 20);
          BuffTimerInstance.getInstance().remove((L1Object)character, 27);
          BuffTimerInstance.getInstance().remove((L1Object)character, 28);
          BuffTimerInstance.getInstance().remove((L1Object)character, 39);
          BuffTimerInstance.getInstance().remove((L1Object)character, 43);
          BuffTimerInstance.getInstance().remove((L1Object)character, 44);
          ItemTimerInstance.getInstance().remove(character, "$234");
          ItemTimerInstance.getInstance().remove(character, "$943");
          ItemTimerInstance.getInstance().remove(character, "$944");
          ItemTimerInstance.getInstance().remove(character, "$1507");
          ItemTimerInstance.getInstance().remove(character, "$239");
          ItemTimerInstance.getInstance().remove(character, "$232");
          ItemTimerInstance.getInstance().remove(character, "$110");
          ItemTimerInstance.getInstance().remove(character, "$260");
          ItemTimerInstance.getInstance().remove(character, "$971");
        } else {
          this.operator.SendPacket((S_BasePacket)new S_ServerMessage(280));
        } 
      } 
    } 
  }
  
  protected boolean Figure(L1Object temp) {
    if (isChaoticMagic() && !WorldMap.getInstance().AttackZone((L1Object)this.operator, temp))
      return false; 
    if (temp instanceof net.world.kingdom.function.CastleTop || temp instanceof net.world.kingdom.function.Crown)
      return false; 
    if (temp.isDead())
      return false; 
    if (temp instanceof PcInstance) {
      PcInstance tgpc = (PcInstance)temp;
      return rnd(tgpc);
    } 
    if (temp instanceof NpcInstance) {
      NpcInstance npc = (NpcInstance)temp;
      if (getSkill().getSkill_id() == 12)
        return (Util.rand(0, this.operator.getLevel()) > Util.rand(0, npc.getLevel()) && Util.rand(0, this.operator.getDynamicInt()) > Util.rand(0, npc.getMr())); 
      return (Util.rand(0, this.operator.getLevel() + this.operator.getDynamicInt()) > Util.rand(0, 100 + npc.getLevel()));
    } 
    return (Util.rand(0, 100) < 50);
  }
  
  private boolean rnd(PcInstance tgpc) {
    int rnd = _random.nextInt(100) + 1;
    int probability = calcProbability(tgpc);
    probability = Math.min(probability, 90);
    probability = Math.max(probability, 1);
    if (probability >= rnd)
      return true; 
    return false;
  }
  
  private int calcProbability(PcInstance tgpc) {
    int result = this.operator.getLevel() + this.operator.getSp(false) * 5 - (tgpc.getLevel() * 7 / 5 + tgpc.getMr() * 12 / 5) / 2;
    return result;
  }
}
