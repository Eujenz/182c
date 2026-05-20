package net.world.instance.skill.function.elf;

import net.database.bean.Skill;
import net.network.server.S_BasePacket;
import net.network.server.S_CharacterStat;
import net.network.server.S_ObjectEffect;
import net.world.instance.MonsterInstance;
import net.world.instance.PcInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;

public final class EraseMagic extends Magic {
  public EraseMagic(Character cha, Skill skill) {
    super(cha, skill);
  }
  
  public void toMagic(int id) {
    if (HpMpCheck() && ConsumeCount()) {
      L1Object o = this.operator.getObject(id);
      if (o != null || id == this.operator.getObjectId()) {
        Character character = null;
        if (id == this.operator.getObjectId())
          character = this.operator; 
        if (character instanceof PcInstance) {
          character.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)character, getSkill().getCastGfx()), true);
          BuffTimerInstance.getInstance().remove((L1Object)character, this);
          BuffTimerInstance.getInstance().add((L1Object)character, this);
        } 
        if (character instanceof MonsterInstance) {
          character.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)character, getSkill().getCastGfx()), true);
          BuffTimerInstance.getInstance().remove((L1Object)character, this);
          BuffTimerInstance.getInstance().add((L1Object)character, this);
        } 
      } 
    } 
  }
  
  public void isTimerRun(L1Object o) {
    if (o instanceof PcInstance) {
      PcInstance pc = (PcInstance)o;
      pc.getMr();
      pc.SendPacket((S_BasePacket)new S_CharacterStat((Character)pc));
    } else if (o instanceof MonsterInstance) {
      MonsterInstance npc = (MonsterInstance)o;
      npc.getMr();
    } 
  }
  
  public void isTimerStop(L1Object o) {
    if (o instanceof PcInstance) {
      PcInstance pc = (PcInstance)o;
      pc.getMr();
      pc.SendPacket((S_BasePacket)new S_CharacterStat((Character)pc));
    } else if (o instanceof MonsterInstance) {
      MonsterInstance npc = (MonsterInstance)o;
      npc.getMr();
    } 
  }
}
