package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import net.database.bean.Skill;
import net.world.instance.skill.Magic;
import net.world.instance.skill.function.BlessedArmor;
import net.world.instance.skill.function.CancelMagic;
import net.world.instance.skill.function.ChattingClose;
import net.world.instance.skill.function.ChillTouch;
import net.world.instance.skill.function.CreateMagicalWeapon;
import net.world.instance.skill.function.CreateZombie;
import net.world.instance.skill.function.CurePoison;
import net.world.instance.skill.function.CurseBlind;
import net.world.instance.skill.function.CurseParalyze;
import net.world.instance.skill.function.CursePoison;
import net.world.instance.skill.function.Detection;
import net.world.instance.skill.function.EarthBind;
import net.world.instance.skill.function.EnchantWeapon;
import net.world.instance.skill.function.EnergyBolt;
import net.world.instance.skill.function.Entangle;
import net.world.instance.skill.function.Firewall;
import net.world.instance.skill.function.Freeze;
import net.world.instance.skill.function.FreezeCockatrice;
import net.world.instance.skill.function.FullHeal;
import net.world.instance.skill.function.GreaterHeal;
import net.world.instance.skill.function.Haste;
import net.world.instance.skill.function.HasteNpc;
import net.world.instance.skill.function.Heal;
import net.world.instance.skill.function.HealPledge;
import net.world.instance.skill.function.ImmuneToHarm;
import net.world.instance.skill.function.Invisibility;
import net.world.instance.skill.function.LesserHeal;
import net.world.instance.skill.function.Light;
import net.world.instance.skill.function.Lightning;
import net.world.instance.skill.function.MassTeleport;
import net.world.instance.skill.function.NaturesBlessing;
import net.world.instance.skill.function.NaturesMiracle;
import net.world.instance.skill.function.NaturesTouch;
import net.world.instance.skill.function.NpcOfSpellDirection;
import net.world.instance.skill.function.NpcOfSpellNoDirection;
import net.world.instance.skill.function.NpcSpellDirection;
import net.world.instance.skill.function.NpcSpellDirectionOwn;
import net.world.instance.skill.function.PhysicalEnchantDex;
import net.world.instance.skill.function.PhysicalEnchantStr;
import net.world.instance.skill.function.Polymorph;
import net.world.instance.skill.function.RemoveCurse;
import net.world.instance.skill.function.Resurrection;
import net.world.instance.skill.function.Shield;
import net.world.instance.skill.function.ShockStun;
import net.world.instance.skill.function.Slow;
import net.world.instance.skill.function.SummonGreaterElemental;
import net.world.instance.skill.function.SummonLesserElemental;
import net.world.instance.skill.function.SummonMonster;
import net.world.instance.skill.function.TameMonster;
import net.world.instance.skill.function.Teleport;
import net.world.instance.skill.function.Tornado;
import net.world.instance.skill.function.TripleArrow;
import net.world.instance.skill.function.TurnToNature;
import net.world.instance.skill.function.TurnUndead;
import net.world.instance.skill.function.WeaponBreak;
import net.world.instance.skill.function.elf.AreaOfSilence;
import net.world.instance.skill.function.elf.BlessOfEarth;
import net.world.instance.skill.function.elf.BlessOfFire;
import net.world.instance.skill.function.elf.BloodToSoul;
import net.world.instance.skill.function.elf.BodyToMind;
import net.world.instance.skill.function.elf.BurningWeapon;
import net.world.instance.skill.function.elf.ClearMind;
import net.world.instance.skill.function.elf.EarthSkin;
import net.world.instance.skill.function.elf.EraseMagic;
import net.world.instance.skill.function.elf.EyeOfStorm;
import net.world.instance.skill.function.elf.FireWeapon;
import net.world.instance.skill.function.elf.IronSkin;
import net.world.instance.skill.function.elf.ProtectionFromElemental;
import net.world.instance.skill.function.elf.ResistElemental;
import net.world.instance.skill.function.elf.ResistMagic;
import net.world.instance.skill.function.elf.StormShot;
import net.world.instance.skill.function.elf.StormWalk;
import net.world.instance.skill.function.elf.TeleportToMother;
import net.world.instance.skill.function.elf.WindShot;
import net.world.instance.skill.function.elf.WindWalk;
import net.world.object.Character;

public class SkillTable {
  private HashMap<Integer, Skill> _Skill;
  
  private static class Holder {
    static SkillTable instance = new SkillTable();
  }
  
  public static SkillTable getInstance() {
    return Holder.instance;
  }
  
  private SkillTable() {
    this._Skill = new HashMap<Integer, Skill>();
    skill();
  }
  
  private void skill() {
    System.out.print("[SQL] 加载技能.");
    try {
      Connection con = DatabaseConnection.getInstance().getConnection();
      PreparedStatement st = con.prepareStatement("SELECT * FROM skill_list");
      ResultSet rs = st.executeQuery();
      skilltable(rs);
      DatabaseConnection.getInstance().close(con, st, rs);
    } catch (Exception localException) {}
  }
  
  private void skilltable(ResultSet Data) throws Exception {
    while (Data.next()) {
      Skill skill = new Skill();
      skill.setSkill_id(Data.getInt(1));
      skill.setName(Data.getString(2));
      skill.setSkill_level(Data.getInt(3));
      skill.setSkill_no(Data.getInt(4));
      skill.setMagic(Data.getString(5));
      skill.setMpConsume(Data.getInt(6));
      skill.setHpConsume(Data.getInt(7));
      skill.setItemConsume(Data.getInt(8));
      skill.setItemConsumeCount(Data.getInt(9));
      skill.setReuseDelay(Data.getInt(10));
      skill.setBuffDuration(Data.getInt(11));
      skill.setType(Data.getString(12));
      skill.setMindmg(Data.getInt(13));
      skill.setMaxdmg(Data.getInt(14));
      skill.setId(Data.getInt(15));
      skill.setCastGfx(Data.getInt(16));
      skill.setRange(Data.getInt(17));
      skill.set_lawfulconsume(Data.getInt(18));
      skill.setAttr(Data.getInt(19));
      if (skill.getSkill_id() <= 8) {
        skill.setPrice(100);
      } else if (skill.getSkill_id() <= 16) {
        skill.setPrice(400);
      } else if (skill.getSkill_id() <= 24) {
        skill.setPrice(900);
      } 
      this._Skill.put(new Integer(Data.getInt(1)), skill);
    } 
    System.out.println(" 数量:" + this._Skill.size());
  }
  
  public Skill getTemplate(int id) {
    return this._Skill.get(new Integer(id));
  }
  
  public Magic getTemplate(Character pc, int id) {
    Magic magic = null;
    Skill s = getInstance().getTemplate(id);
    if (s != null) {
      ShockStun shockStun;
      TripleArrow tripleArrow;
      LesserHeal lesserHeal;
      Heal heal;
      GreaterHeal greaterHeal;
      FullHeal fullHeal;
      Light light;
      Shield shield;
      EnergyBolt energyBolt;
      Teleport teleport;
      CurePoison curePoison;
      ChillTouch chillTouch;
      CursePoison cursePoison;
      EnchantWeapon enchantWeapon;
      Detection detection;
      Lightning lightning;
      TurnUndead turnUndead;
      CurseBlind curseBlind;
      BlessedArmor blessedArmor;
      PhysicalEnchantDex physicalEnchantDex;
      WeaponBreak weaponBreak;
      Slow slow;
      CurseParalyze curseParalyze;
      TameMonster tameMonster;
      RemoveCurse removeCurse;
      CreateZombie createZombie;
      PhysicalEnchantStr physicalEnchantStr;
      Haste haste;
      CancelMagic cancelMagic;
      HealPledge healPledge;
      Freeze freeze;
      SummonMonster summonMonster;
      Tornado tornado;
      Firewall firewall;
      Invisibility invisibility;
      Resurrection resurrection;
      Polymorph polymorph;
      ImmuneToHarm immuneToHarm;
      MassTeleport massTeleport;
      CreateMagicalWeapon createMagicalWeapon;
      ResistMagic resistMagic;
      BodyToMind bodyToMind;
      TeleportToMother teleportToMother;
      ClearMind clearMind;
      ResistElemental resistElemental;
      BloodToSoul bloodToSoul;
      FireWeapon fireWeapon;
      TurnToNature turnToNature;
      WindWalk windWalk;
      ProtectionFromElemental protectionFromElemental;
      Entangle entangle;
      EraseMagic eraseMagic;
      SummonLesserElemental summonLesserElemental;
      BlessOfFire blessOfFire;
      EyeOfStorm eyeOfStorm;
      EarthBind earthBind;
      NaturesTouch naturesTouch;
      BlessOfEarth blessOfEarth;
      AreaOfSilence areaOfSilence;
      SummonGreaterElemental summonGreaterElemental;
      BurningWeapon burningWeapon;
      NaturesBlessing naturesBlessing;
      NaturesMiracle naturesMiracle;
      StormShot stormShot;
      StormWalk stormWalk;
      IronSkin ironSkin;
      WindShot windShot;
      EarthSkin earthSkin;
      NpcOfSpellDirection npcOfSpellDirection;
      NpcOfSpellNoDirection npcOfSpellNoDirection;
      NpcSpellDirection npcSpellDirection;
      NpcSpellDirectionOwn npcSpellDirectionOwn;
      HasteNpc hasteNpc;
      FreezeCockatrice freezeCockatrice;
      switch (s.getSkill_id()) {
        case 87:
          return (Magic)new ShockStun(pc, s);
        case 132:
          return (Magic)new TripleArrow(pc, s);
        case 1:
          return (Magic)new LesserHeal(pc, s);
        case 13:
          return (Magic)new Heal(pc, s);
        case 23:
          return (Magic)new GreaterHeal(pc, s);
        case 36:
          return (Magic)new FullHeal(pc, s);
        case 2:
          return (Magic)new Light(pc, s);
        case 3:
          return (Magic)new Shield(pc, s);
        case 4:
        case 22:
        case 30:
        case 50:
          return (Magic)new EnergyBolt(pc, s);
        case 5:
          return (Magic)new Teleport(pc, s);
        case 6:
          return (Magic)new CurePoison(pc, s);
        case 7:
        case 19:
          return (Magic)new ChillTouch(pc, s);
        case 8:
          return (Magic)new CursePoison(pc, s);
        case 9:
          return (Magic)new EnchantWeapon(pc, s);
        case 10:
          return (Magic)new Detection(pc, s);
        case 11:
        case 16:
        case 47:
          return (Magic)new Lightning(pc, s);
        case 12:
          return (Magic)new TurnUndead(pc, s);
        case 14:
          return (Magic)new CurseBlind(pc, s);
        case 15:
          return (Magic)new BlessedArmor(pc, s);
        case 17:
          return (Magic)new PhysicalEnchantDex(pc, s);
        case 18:
          return (Magic)new WeaponBreak(pc, s);
        case 20:
          return (Magic)new Slow(pc, s);
        case 21:
          return (Magic)new CurseParalyze(pc, s);
        case 24:
          return (Magic)new TameMonster(pc, s);
        case 25:
          return (Magic)new RemoveCurse(pc, s);
        case 26:
          return (Magic)new CreateZombie(pc, s);
        case 27:
          return (Magic)new PhysicalEnchantStr(pc, s);
        case 28:
          return (Magic)new Haste(pc, s);
        case 29:
          return (Magic)new CancelMagic(pc, s);
        case 31:
          return (Magic)new HealPledge(pc, s);
        case 32:
          return (Magic)new Freeze(pc, s);
        case 33:
          return (Magic)new SummonMonster(pc, s);
        case 35:
        case 38:
        case 1000:
        case 1001:
        case 1002:
        case 1003:
        case 1004:
          return (Magic)new Tornado(pc, s);
        case 37:
          return (Magic)new Firewall(pc, s);
        case 39:
          return (Magic)new Invisibility(pc, s);
        case 40:
          return (Magic)new Resurrection(pc, s);
        case 43:
          return (Magic)new Polymorph(pc, s);
        case 44:
          return (Magic)new ImmuneToHarm(pc, s);
        case 45:
          return (Magic)new MassTeleport(pc, s);
        case 46:
          return (Magic)new CreateMagicalWeapon(pc, s);
        case 129:
          return (Magic)new ResistMagic(pc, s);
        case 130:
          return (Magic)new BodyToMind(pc, s);
        case 131:
          return (Magic)new TeleportToMother(pc, s);
        case 137:
          return (Magic)new ClearMind(pc, s);
        case 138:
          return (Magic)new ResistElemental(pc, s);
        case 146:
          return (Magic)new BloodToSoul(pc, s);
        case 148:
          return (Magic)new FireWeapon(pc, s);
        case 145:
          return (Magic)new TurnToNature(pc, s);
        case 150:
          return (Magic)new WindWalk(pc, s);
        case 147:
          return (Magic)new ProtectionFromElemental(pc, s);
        case 152:
          return (Magic)new Entangle(pc, s);
        case 153:
          return (Magic)new EraseMagic(pc, s);
        case 154:
          return (Magic)new SummonLesserElemental(pc, s);
        case 155:
          return (Magic)new BlessOfFire(pc, s);
        case 156:
          return (Magic)new EyeOfStorm(pc, s);
        case 157:
          return (Magic)new EarthBind(pc, s);
        case 158:
          return (Magic)new NaturesTouch(pc, s);
        case 159:
          return (Magic)new BlessOfEarth(pc, s);
        case 161:
          return (Magic)new AreaOfSilence(pc, s);
        case 162:
          return (Magic)new SummonGreaterElemental(pc, s);
        case 163:
          return (Magic)new BurningWeapon(pc, s);
        case 164:
          return (Magic)new NaturesBlessing(pc, s);
        case 165:
          return (Magic)new NaturesMiracle(pc, s);
        case 166:
          return (Magic)new StormShot(pc, s);
        case 167:
          return (Magic)new StormWalk(pc, s);
        case 168:
          return (Magic)new IronSkin(pc, s);
        case 149:
          return (Magic)new WindShot(pc, s);
        case 151:
          return (Magic)new EarthSkin(pc, s);
        case 1005:
        case 1007:
        case 1009:
          return (Magic)new NpcOfSpellDirection(pc, s);
        case 1006:
        case 1008:
        case 1010:
          return (Magic)new NpcOfSpellNoDirection(pc, s);
        case 1011:
        case 1012:
          return (Magic)new NpcSpellDirection(pc, s);
        case 1013:
        case 1015:
          return (Magic)new NpcSpellDirectionOwn(pc, s);
        case 1014:
          return (Magic)new HasteNpc(pc, s);
        case 1016:
          return (Magic)new FreezeCockatrice(pc, s);
        case 10000:
          return (Magic)new ChattingClose(pc, s);
      } 
      magic = new Magic(pc, s);
    } 
    return magic;
  }
}
