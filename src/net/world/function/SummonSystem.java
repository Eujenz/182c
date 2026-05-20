package net.world.function;

import java.util.HashMap;
import java.util.Map;

import net.Config;
import net.database.ItemsTable;
import net.database.MonsterTable;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ObjectPet;
import net.network.server.S_ServerMessage;
import net.network.server.S_ServerMessageYesNo;
import net.network.server.S_ShowHtml;
import net.util.Util;
import net.world.ai.MonAi;
import net.world.function.bean.Summon;
import net.world.instance.ItemInstance;
import net.world.instance.MonsterInstance;
import net.world.instance.PcInstance;
import net.world.instance.PetInstance;
import net.world.instance.SummonInstance;
import net.world.object.Character;
import net.world.object.L1Object;

public class SummonSystem {
  private Map<Integer, Summon> list;
  
  private static class Holder {
    static SummonSystem instance = new SummonSystem();
  }
  
  public static SummonSystem getInstance() {
    return Holder.instance;
  }
  
  private SummonSystem() {
    this.list = new HashMap<Integer, Summon>();
  }
  
  public void toTeleport(Character own) {
    Summon s = this.list.get(Integer.valueOf(own.getObjectId()));
    if (s != null)
      for (SummonInstance ss : s.getList())
        ss.toTeleport(own.getX(), own.getY(), own.getMap());  
  }
  
  public synchronized void toAttack(Character own, L1Object target) {
    Summon s = this.list.get(Integer.valueOf(own.getObjectId()));
    if (s != null)
      for (SummonInstance ss : s.getList()) {
        ss.setFight(true);
        ss.addAttackList(target);
      }  
  }
  
  public void Commander(SummonInstance sum, String cmd) {
    Summon s = this.list.get(Integer.valueOf(sum.getOwn().getObjectId()));
    if (s != null)
      if ("attackchr".equalsIgnoreCase(cmd) && sum instanceof PetInstance) {
        sum.getOwn().SendPacket((S_BasePacket)new S_ObjectPet(sum, 0));
      } else if ("changename".equalsIgnoreCase(cmd) && sum instanceof PetInstance) {
        sum.getOwn().SendPacket((S_BasePacket)new S_ServerMessageYesNo(325));
      } else if ("aggressive".equalsIgnoreCase(cmd)) {
        s.Aggressive();
      } else if ("defensive".equalsIgnoreCase(cmd)) {
        s.Defensive();
      } else if ("stay".equalsIgnoreCase(cmd)) {
        s.Stay();
      } else if ("extend".equalsIgnoreCase(cmd)) {
        s.Extend();
      } else if ("alert".equalsIgnoreCase(cmd)) {
        s.Alert();
      } else if ("getitem".equalsIgnoreCase(cmd) && sum instanceof PetInstance) {
        s.Getitem();
      } else if ("dismiss".equalsIgnoreCase(cmd)) {
        if (sum.getSkillId() == 0) {
          s.remove(sum);
          sum.getOwn().SendPacket((S_BasePacket)new S_ShowHtml(sum.getOwn().getObjectId(), ""));
          this.list.remove(Integer.valueOf(sum.getOwn().getObjectId()));
        } else {
          for (SummonInstance ss : s.getList()) {
            if (ss.getSkillId() == sum.getSkillId())
              s.remove(ss); 
          } 
          sum.getOwn().SendPacket((S_BasePacket)new S_ShowHtml(sum.getOwn().getObjectId(), ""));
          this.list.remove(Integer.valueOf(sum.getOwn().getObjectId()));
        } 
      }  
  }
  
  public boolean addPet(Character cha, MonsterInstance mon) {
    Summon s = this.list.get(Integer.valueOf(cha.getObjectId()));
    if (s == null) {
      s = new Summon(cha);
      this.list.put(Integer.valueOf(cha.getObjectId()), s);
    } 
    if (isSummon(cha, true)) {
      int obj_id = mon.getObjectId();
      mon.clearFightList();
      mon.getInventory().clear();
      mon.toDelete();
      mon.setDead(true);
      mon.setObjectId(Config.getObjectID_ETC());
      if (mon.isSummon()) {
        MonAi.getInstance().removeMon(mon);
        mon.toSave(true);
      } 
      PetInstance pet = new PetInstance(mon.getMon(), cha);
      pet.setObjectId(obj_id);
      pet.setHeading(mon.getHeading());
      pet.toTeleport(mon.getX(), mon.getY(), mon.getMap());
      s.add((SummonInstance)pet);
      ItemInstance pet_item = ItemsTable.getInstance().newItem(308, false, true);
      pet_item.setPet(pet);
      cha.getInventory().add(pet_item);
      pet.insertDB();
      return true;
    } 
    return false;
  }
  
  public void addCreateZombie(Character cha, MonsterInstance mon, int skill_id, int cast_gfx, int duration) {
    Summon s = this.list.get(Integer.valueOf(cha.getObjectId()));
    if (s == null) {
      s = new Summon(cha);
      this.list.put(Integer.valueOf(cha.getObjectId()), s);
    } 
    if (isSummon(cha, false)) {
      mon.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)mon, cast_gfx), true);
      mon.toDelete();
      mon.toSave(true);
      MonAi.getInstance().removeMon(mon);
      SummonInstance sum = new SummonInstance(MonsterTable.getInstance().getMonster(7), cha, skill_id, duration);
      sum.toTeleport(mon.getX(), mon.getY(), mon.getMap());
      s.add(sum);
    } 
  }
  
  public void addTameMonster(Character cha, MonsterInstance mon, int skill_id, int cast_gfx, int duration) {
    Summon s = this.list.get(Integer.valueOf(cha.getObjectId()));
    if (s == null) {
      s = new Summon(cha);
      this.list.put(Integer.valueOf(cha.getObjectId()), s);
    } 
    if (isSummon(cha, false)) {
      mon.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)mon, cast_gfx), true);
      mon.clearFightList();
      mon.getInventory().clear();
      mon.toDelete();
      mon.setDead(true);
      SummonInstance sum = new SummonInstance(mon.getMon(), cha, skill_id, duration);
      sum.toTeleport(mon.getX(), mon.getY(), mon.getMap());
      s.add(sum);
    } else {
      cha.SendPacket((S_BasePacket)new S_ServerMessage(324));
    } 
  }
  
  public void addSummonMonster(Character cha, int skill_id, int duration) {
    Summon s = this.list.get(Integer.valueOf(cha.getObjectId()));
    if (s == null) {
      s = new Summon(cha);
      this.list.put(Integer.valueOf(cha.getObjectId()), s);
    } 
    int mobId = getMobId(cha);
    while (isSummon(cha, false)) {
      SummonInstance sum = new SummonInstance(MonsterTable.getInstance().getMonster(mobId), cha, skill_id, duration);
      sum.toTeleport(Util.rand(cha.getX() - 3, cha.getX() + 3), Util.rand(cha.getY() - 3, cha.getY() + 3), cha.getMap());
      s.add(sum);
    } 
  }
  
  public void addElfSummonMonster(Character cha, int skill_id, int duration, int mon) {
    Summon s = this.list.get(Integer.valueOf(cha.getObjectId()));
    if (s != null) {
      PcInstance pc = (PcInstance)cha;
      pc.Message("無法重複召喚精靈。");
      return;
    } 
    int mobId = getElfMobId(cha, mon);
    if (mobId == 0)
      return; 
    if (s == null) {
      s = new Summon(cha);
      this.list.put(Integer.valueOf(cha.getObjectId()), s);
    } 
    SummonInstance sum = new SummonInstance(MonsterTable.getInstance().getMonster(mobId), cha, skill_id, duration);
    sum.toTeleport(Util.rand(cha.getX() - 3, cha.getX() + 3), Util.rand(cha.getY() - 3, cha.getY() + 3), cha.getMap());
    s.add(sum);
  }
  
  public void addGmSummonMonster(Character cha, int skill_id, int duration, int mob_id, int count) {
    Summon s = this.list.get(Integer.valueOf(cha.getObjectId()));
    if (s == null) {
      s = new Summon(cha);
      this.list.put(Integer.valueOf(cha.getObjectId()), s);
    } 
    while (count > 0) {
      SummonInstance sum = new SummonInstance(MonsterTable.getInstance().getMonster(mob_id), cha, skill_id, duration);
      sum.toTeleport(Util.rand(cha.getX() - 3, cha.getX() + 3), Util.rand(cha.getY() - 3, cha.getY() + 3), cha.getMap());
      s.add(sum);
      count--;
    } 
  }
  
  public void remove(SummonInstance sum) {
    Summon s = this.list.get(Integer.valueOf(sum.getOwn().getObjectId()));
    if (s != null)
      s.remove(sum); 
  }
  
  public void remove(Character cha) {
    Summon s = this.list.get(Integer.valueOf(cha.getObjectId()));
    if (s != null) {
      s.clear();
      this.list.remove(Integer.valueOf(cha.getObjectId()));
    } 
  }
  
  public void remove(Character cha, int skill_id) {
    Summon s = this.list.get(Integer.valueOf(cha.getObjectId()));
    if (s != null)
      for (SummonInstance sum : s.getList()) {
        if (sum.getSkillId() == skill_id)
          s.remove(sum); 
      }  
  }
  
  public Summon getSummon(Character cha) {
    Summon s = this.list.get(Integer.valueOf(cha.getObjectId()));
    if (s == null) {
      s = new Summon(cha);
      this.list.put(Integer.valueOf(cha.getObjectId()), s);
    } 
    return this.list.get(Integer.valueOf(cha.getObjectId()));
  }
  
  public boolean isSummon(Character cha, boolean pet) {
    int max = 0;
    int count = 0;
    Summon s = this.list.get(Integer.valueOf(cha.getObjectId()));
    if (s == null) {
      s = new Summon(cha);
      this.list.put(Integer.valueOf(cha.getObjectId()), s);
    } 
    if (pet && cha.getTotalCha() > 18) {
      max = 3;
      if (max > 4)
        max = 4; 
    } else {
      max = cha.getTotalCha() / 6;
    } 
    switch (cha.getClassType()) {
      case 0:
        max++;
        break;
      case 2:
        max += 2;
        break;
      case 3:
        max++;
        break;
    } 
    count = max - s.getListSize();
    return (count > 0);
  }
  
  private int getMobId(Character cha) {
    int mobId = 4;
    if (cha.getLevel() < 32) {
      mobId = 4;
    } else if (cha.getLevel() < 36) {
      mobId = 90;
    } else if (cha.getLevel() < 40) {
      mobId = 110;
    } else if (cha.getLevel() < 44) {
      mobId = 21;
    } else if (cha.getLevel() < 48) {
      mobId = 76;
    } else if (cha.getLevel() < 52) {
      mobId = 52;
    } else if (Config.TEST) {
      mobId = 1000;
    } else {
      mobId = 73;
    } 
    return mobId;
  }
  
  private int getElfMobId(Character cha, int mon) {
    int mobId = 0;
    PcInstance pc = (PcInstance)cha;
    switch (mon) {
      case 1:
        switch (pc.getElfAttr()) {
          case 1:
            mobId = 201;
            break;
          case 2:
            mobId = 204;
            break;
          case 4:
            mobId = 202;
            break;
          case 8:
            mobId = 203;
            break;
        } 
        break;
      case 2:
        switch (pc.getElfAttr()) {
          case 1:
            mobId = 206;
            break;
          case 2:
            mobId = 205;
            break;
          case 4:
            mobId = 207;
            break;
          case 8:
            mobId = 208;
            break;
        } 
        break;
    } 
    return mobId;
  }
}
