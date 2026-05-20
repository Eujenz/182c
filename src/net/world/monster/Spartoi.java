package net.world.monster;

import net.database.ItemsTable;
import net.database.bean.Monster;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectMode;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.instance.ItemInstance;
import net.world.instance.MonsterInstance;
import net.world.instance.PcInstance;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.HpMpTimer;

public class Spartoi extends MonsterInstance {
  public Spartoi(Monster m) {
    super(m);
    setFood(29);
    setDynamicTicHp(10);
    HpMpTimer.getInstance().add((Character)this);
  }
  
  public void toQuest(PcInstance pc, Magic m) {
    if (pc != null && m != null && 
      pc.getClassType() == 3 && m instanceof net.world.instance.skill.function.TurnUndead && ((
      pc.getQuest().get_step(100) == 1 && Util.rand(1, 100) <= 10) || pc.isGm())) {
      ItemInstance temp = pc.getInventory().getItemNameId("$1097");
      if (temp == null) {
        temp = ItemsTable.getInstance().newItem(292, false, true);
        pc.getInventory().add(temp);
        pc.getQuest().set_step(100, 2);
        pc.SendPacket((S_BasePacket)new S_ServerMessage(143, getName(), temp.toString()));
      } 
    } 
  }
  
  public void toFight(long time) {
    if (getCurrentHp() <= getMaxHp() * 0.3D && Util.rand(0, 100) < 10) {
      this.ai_start_time = time;
      setRecess(true);
      return;
    } 
    super.toFight(time);
  }
  
  public void toRecess(long time) {
    this.ai_start_time = time;
    this.ai_time = 400;
    if (getCurrentHp() > getMaxHp() * 0.5D) {
      L1Object o = SearchPlayer();
      if (o != null) {
        MagicalAttackEncounters((Character)o);
        return;
      } 
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
      setGfxMode(4);
      SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this), true);
      setGfxMode(0);
      SendPacket((S_BasePacket)new S_ObjectMode((L1Object)this), true);
    } else {
      setGfxMode(11);
      SendPacket((S_BasePacket)new S_ObjectAction((L1Object)this), true);
      setGfxMode(28);
      SendPacket((S_BasePacket)new S_ObjectMode((L1Object)this), true);
    } 
    super.setRecess(recess);
  }
}
