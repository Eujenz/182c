package net.world.monster;

import net.database.bean.Monster;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectMode;
import net.world.instance.MonsterInstance;
import net.world.object.Character;
import net.world.object.L1Object;

public class StoneGolem extends MonsterInstance {
  private static final int[] item_list = new int[] { 1, 14, 69, 74, 20 };
  
  public StoneGolem(Monster m) {
    super(m);
    setRecess(true);
  }
  
  public void toWalk(long time) {
    super.toWalk(time);
    if (isFight() || 
      SearchItem(item_list));
  }
  
  protected void reSpawn() {
    super.reSpawn();
    setRecess(true);
  }
  
  public void toRecess(long time) {
    this.ai_start_time = time;
    this.ai_time = 400;
    L1Object o = SearchPlayer();
    if (o != null) {
      MagicalAttackEncounters((Character)o);
      return;
    } 
    if (SearchItem(item_list)) {
      setRecess(false);
      this.ai_time = getMon().getModespeed(11);
      return;
    } 
  }
  
  public void MagicalAttackEncounters(Character cha) {
    if (!isDead() && isRecess()) {
      setRecess(false);
      addAttackList((L1Object)cha);
      setFight(true);
    } 
  }
  
  public void setRecess(boolean recess) {
    if (isRecess() && !recess) {
      setGfxMode(11);
      SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this), true);
      setGfxMode(0);
      SendPacket((S_BasePacket)new S_ObjectMode((L1Object)this), true);
    } else {
      setGfxMode(4);
      SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this), true);
      SendPacket((S_BasePacket)new S_ObjectMode((L1Object)this), true);
    } 
    super.setRecess(recess);
  }
}
