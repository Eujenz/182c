package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.Config;
import net.database.bean.L1Map;
import net.database.bean.Monster;
import net.util.Util;
import net.world.WorldMap;
import net.world.instance.MonsterInstance;
import net.world.monster.Aracnevil;
import net.world.monster.Atubaorc;
import net.world.monster.Baltuzar;
import net.world.monster.Baphomet;
import net.world.monster.Beagle;
import net.world.monster.BlackKnight;
import net.world.monster.Blob;
import net.world.monster.BombFlower;
import net.world.monster.Caspa;
import net.world.monster.Cerberus;
import net.world.monster.Cockatrice;
import net.world.monster.Collie;
import net.world.monster.DeathKnight;
import net.world.monster.Doberman;
import net.world.monster.Drake;
import net.world.monster.Dudamariorc;
import net.world.monster.Dwarf;
import net.world.monster.EarthDragon;
import net.world.monster.Elder;
import net.world.monster.Ettin;
import net.world.monster.FieryDragon;
import net.world.monster.FloatingEye;
import net.world.monster.Gandiorc;
import net.world.monster.Ghoul;
import net.world.monster.Gnoll;
import net.world.monster.Goblin;
import net.world.monster.Gremlin;
import net.world.monster.Harpy;
import net.world.monster.HornCerveru;
import net.world.monster.Ifrit;
import net.world.monster.Kobold;
import net.world.monster.Kurtz;
import net.world.monster.Merkyor;
import net.world.monster.Necromancer;
import net.world.monster.Nerugaorc;
import net.world.monster.Orc;
import net.world.monster.OrcArcher;
import net.world.monster.OrcFighter;
import net.world.monster.Phoenix;
import net.world.monster.Ramia;
import net.world.monster.Rovaorc;
import net.world.monster.SaintBurnard;
import net.world.monster.Sema;
import net.world.monster.Shepherd;
import net.world.monster.Slime;
import net.world.monster.Spartoi;
import net.world.monster.StoneGolem;
import net.world.monster.Succubus;
import net.world.monster.SucubusQueen;
import net.world.monster.Ungoliant;
import net.world.monster.WaterDragon;
import net.world.monster.Werewolf;
import net.world.monster.Wolf;

public class MonsterSpawnTable {
  private int[] bossGfx = new int[] { 
      111, 53, 183, 185, 173, 187, 240, 1041, 1062, 183, 
      1245, 1180, 1572, 1590, 1390, 1773, 1791, 2001 };
  
  private static class Holder {
    static MonsterSpawnTable instance = new MonsterSpawnTable();
  }
  
  public static MonsterSpawnTable getInstance() {
    return Holder.instance;
  }
  
  private MonsterSpawnTable() {
    System.out.println("[SQL] 加载怪物产生列表.");
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM monster_spawnlist");
      rs = st.executeQuery();
      while (rs.next()) {
        Monster mon = MonsterTable.getInstance().getMonster(rs.getInt("monster"));
        if (mon != null) {
          int count = rs.getInt("count");
          int x = rs.getInt("spawn_x");
          int y = rs.getInt("spawn_y");
          int map = rs.getInt("spawn_map");
          int size = rs.getInt("loc_size");
          int spawnTime = rs.getInt("re_spawn");
          if (size > 0 && mon.getSpawnLoc() <= 0)
            mon.setSpawnLoc(size); 
          if (x > 0 && mon.getSpawnX() <= 0) {
            mon.setSpawnX(x);
            mon.setSpawnY(y);
          } 
          if (spawnTime > 0 && mon.getSpawnTime() <= 0)
            mon.setSpawnTime(spawnTime); 
          mon.setSpawnRandom("true".equalsIgnoreCase(rs.getString("random")));
          int x1 = 0;
          int x2 = 0;
          int y1 = 0;
          int y2 = 0;
          if (size > 0) {
            x1 = x - size;
            x2 = x + size;
            y1 = y - size;
            y2 = y + size;
          } else {
            L1Map MAP = WorldMap.getInstance().map(map);
            if (MAP == null)
              continue; 
            x1 = MAP.get_locX1();
            x2 = MAP.get_locX2();
            y1 = MAP.get_locY1();
            y2 = MAP.get_locY2();
          } 
          for (int i = 0; i < count; i++) {
            try {
              int c = 0;
              if (size > 0) {
                do {
                  x = Util.rand(x1, x2);
                  y = Util.rand(y1, y2);
                  if (WorldMap.getInstance().IsThroughObject(x, y + 1, map, 0) && WorldMap.getInstance().IsThroughObject(x - 1, y + 1, map, 1) && 
                    WorldMap.getInstance().IsThroughObject(x - 1, y, map, 2) && WorldMap.getInstance().IsThroughObject(x - 1, y - 1, map, 3) && 
                    WorldMap.getInstance().IsThroughObject(x, y - 1, map, 4) && WorldMap.getInstance().IsThroughObject(x + 1, y - 1, map, 5) && 
                    WorldMap.getInstance().IsThroughObject(x + 1, y, map, 6) && WorldMap.getInstance().IsThroughObject(x + 1, y + 1, map, 7))
                    break; 
                  ++c;
                } while (c < 50);
              } else {
                do {
                  c++;
                  x = Util.rand(x1, x2);
                  y = Util.rand(y1, y2);
                  if (WorldMap.getInstance().IsThroughObject(x, y + 1, map, 0) && WorldMap.getInstance().IsThroughObject(x - 1, y + 1, map, 1) && 
                    WorldMap.getInstance().IsThroughObject(x - 1, y, map, 2) && WorldMap.getInstance().IsThroughObject(x - 1, y - 1, map, 3) && 
                    WorldMap.getInstance().IsThroughObject(x, y - 1, map, 4) && WorldMap.getInstance().IsThroughObject(x + 1, y - 1, map, 5) && 
                    WorldMap.getInstance().IsThroughObject(x + 1, y, map, 6) && WorldMap.getInstance().IsThroughObject(x + 1, y + 1, map, 7))
                    break; 
                  ++c;
                } while (c < 50);
              } 
              if (c < 50) {
                MonsterInstance m = Function(mon);
                m.setHomeX(x);
                m.setHomeY(y);
                m.setHomeMap(map);
                m.x1 = x1;
                m.x2 = x2;
                m.y1 = y1;
                m.y2 = y2;
                m.toTeleport(m.getHomeX(), m.getHomeY(), m.getHomeMap());
                if (!Config.BOSS) {
                  byte b;
                  int j;
                  int[] arrayOfInt;
                  for (j = (arrayOfInt = this.bossGfx).length, b = 0; b < j; ) {
                    int boss = arrayOfInt[b];
                    if (m.getGfx() == boss) {
                      m.setDead(true);
                      m.setDelete(true);
                    } 
                    b++;
                  } 
                } 
              } 
            } catch (Exception exception) {}
          } 
        } 
      } 
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  private MonsterInstance Function(Monster mon) {
    FloatingEye floatingEye;
    Slime slime;
    Werewolf werewolf;
    Elder elder;
    StoneGolem stoneGolem;
    Dwarf dwarf;
    Orc orc;
    Aracnevil aracnevil;
    OrcFighter orcFighter;
    OrcArcher orcArcher;
    Wolf wolf;
    BlackKnight blackKnight;
    Kurtz kurtz;
    Baphomet baphomet;
    Ghoul ghoul;
    Spartoi spartoi;
    Ungoliant ungoliant;
    Necromancer necromancer;
    Baltuzar baltuzar;
    Caspa caspa;
    Merkyor merkyor;
    Sema sema;
    DeathKnight deathKnight;
    Atubaorc atubaorc;
    Nerugaorc nerugaorc;
    Gandiorc gandiorc;
    Rovaorc rovaorc;
    Dudamariorc dudamariorc;
    Blob blob;
    SaintBurnard saintBurnard;
    Doberman doberman;
    Collie collie;
    Shepherd shepherd;
    Beagle beagle;
    Cerberus cerberus;
    Goblin goblin;
    Harpy harpy;
    Cockatrice cockatrice;
    Kobold kobold;
    Drake drake;
    Succubus succubus;
    Ettin ettin = null;
    SucubusQueen sucubusQueen;
    Gremlin gremlin;
    EarthDragon earthDragon;
    Gnoll gnoll;
    WaterDragon waterDragon;
    Ramia ramia;
    Ifrit ifrit;
    HornCerveru hornCerveru;
    Phoenix phoenix;
    BombFlower bombFlower;
    FieryDragon fieryDragon;
    MonsterInstance o = null;
    switch (mon.getNameidN()) {
      case 6:
        floatingEye = new FloatingEye(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)floatingEye);
        return (MonsterInstance)floatingEye;
      case 8:
        slime = new Slime(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)slime);
        return (MonsterInstance)slime;
      case 18:
        werewolf = new Werewolf(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)werewolf);
        return (MonsterInstance)werewolf;
      case 19:
        elder = new Elder(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)elder);
        return (MonsterInstance)elder;
      case 56:
        stoneGolem = new StoneGolem(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)stoneGolem);
        return (MonsterInstance)stoneGolem;
      case 58:
        dwarf = new Dwarf(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)dwarf);
        return (MonsterInstance)dwarf;
      case 59:
        orc = new Orc(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)orc);
        return (MonsterInstance)orc;
      case 265:
        aracnevil = new Aracnevil(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)aracnevil);
        return (MonsterInstance)aracnevil;
      case 266:
        orcFighter = new OrcFighter(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)orcFighter);
        return (MonsterInstance)orcFighter;
      case 267:
        orcArcher = new OrcArcher(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)orcArcher);
        return (MonsterInstance)orcArcher;
      case 268:
        wolf = new Wolf(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)wolf);
        return (MonsterInstance)wolf;
      case 272:
        blackKnight = new BlackKnight(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)blackKnight);
        return (MonsterInstance)blackKnight;
      case 274:
        kurtz = new Kurtz(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)kurtz);
        return (MonsterInstance)kurtz;
      case 306:
        baphomet = new Baphomet(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)baphomet);
        return (MonsterInstance)baphomet;
      case 317:
        ghoul = new Ghoul(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)ghoul);
        return (MonsterInstance)ghoul;
      case 318:
        spartoi = new Spartoi(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)spartoi);
        return (MonsterInstance)spartoi;
      case 319:
        ungoliant = new Ungoliant(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)ungoliant);
        return (MonsterInstance)ungoliant;
      case 331:
        necromancer = new Necromancer(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)necromancer);
        return (MonsterInstance)necromancer;
      case 335:
        baltuzar = new Baltuzar(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)baltuzar);
        return (MonsterInstance)baltuzar;
      case 336:
        caspa = new Caspa(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)caspa);
        return (MonsterInstance)caspa;
      case 337:
        merkyor = new Merkyor(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)merkyor);
        return (MonsterInstance)merkyor;
      case 338:
        sema = new Sema(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)sema);
        return (MonsterInstance)sema;
      case 371:
        deathKnight = new DeathKnight(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)deathKnight);
        return (MonsterInstance)deathKnight;
      case 494:
        atubaorc = new Atubaorc(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)atubaorc);
        return (MonsterInstance)atubaorc;
      case 495:
        nerugaorc = new Nerugaorc(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)nerugaorc);
        return (MonsterInstance)nerugaorc;
      case 496:
        gandiorc = new Gandiorc(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)gandiorc);
        return (MonsterInstance)gandiorc;
      case 497:
        rovaorc = new Rovaorc(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)rovaorc);
        return (MonsterInstance)rovaorc;
      case 498:
        dudamariorc = new Dudamariorc(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)dudamariorc);
        return (MonsterInstance)dudamariorc;
      case 758:
        blob = new Blob(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)blob);
        return (MonsterInstance)blob;
      case 904:
        saintBurnard = new SaintBurnard(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)saintBurnard);
        return (MonsterInstance)saintBurnard;
      case 905:
        doberman = new Doberman(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)doberman);
        return (MonsterInstance)doberman;
      case 906:
        collie = new Collie(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)collie);
        return (MonsterInstance)collie;
      case 907:
        shepherd = new Shepherd(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)shepherd);
        return (MonsterInstance)shepherd;
      case 908:
        beagle = new Beagle(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)beagle);
        return (MonsterInstance)beagle;
      case 925:
        cerberus = new Cerberus(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)cerberus);
        return (MonsterInstance)cerberus;
      case 958:
        goblin = new Goblin(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)goblin);
        return (MonsterInstance)goblin;
      case 959:
        harpy = new Harpy(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)harpy);
        return (MonsterInstance)harpy;
      case 969:
        cockatrice = new Cockatrice(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)cockatrice);
        return (MonsterInstance)cockatrice;
      case 970:
        kobold = new Kobold(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)kobold);
        return (MonsterInstance)kobold;
      case 972:
        drake = new Drake(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)drake);
        return (MonsterInstance)drake;
      case 1000:
        succubus = new Succubus(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)succubus);
        return (MonsterInstance)succubus;
      case 1010:
        if (mon.getUid() == 73)
          ettin = new Ettin(mon); 
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)ettin);
        return (MonsterInstance)ettin;
      case 1019:
        sucubusQueen = new SucubusQueen(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)sucubusQueen);
        return (MonsterInstance)sucubusQueen;
      case 1046:
        gremlin = new Gremlin(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)gremlin);
        return (MonsterInstance)gremlin;
      case 1116:
        earthDragon = new EarthDragon(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)earthDragon);
        return (MonsterInstance)earthDragon;
      case 1391:
        gnoll = new Gnoll(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)gnoll);
        return (MonsterInstance)gnoll;
      case 1426:
        waterDragon = new WaterDragon(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)waterDragon);
        return (MonsterInstance)waterDragon;
      case 1428:
        ramia = new Ramia(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)ramia);
        return (MonsterInstance)ramia;
      case 1567:
        ifrit = new Ifrit(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)ifrit);
        return (MonsterInstance)ifrit;
      case 1568:
        hornCerveru = new HornCerveru(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)hornCerveru);
        return (MonsterInstance)hornCerveru;
      case 1569:
        phoenix = new Phoenix(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)phoenix);
        return (MonsterInstance)phoenix;
      case 1571:
        bombFlower = new BombFlower(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)bombFlower);
        return (MonsterInstance)bombFlower;
      case 1605:
        fieryDragon = new FieryDragon(mon);
        MonsterItemDropTable.getInstance().MonsterItemDrop((MonsterInstance)fieryDragon);
        return (MonsterInstance)fieryDragon;
    } 
    MonsterInstance monsterInstance1 = new MonsterInstance(mon);
    MonsterItemDropTable.getInstance().MonsterItemDrop(monsterInstance1);
    return monsterInstance1;
  }
  
  public MonsterInstance newMonster(int id) {
    Monster m = MonsterTable.getInstance().getMonster(id);
    if (m != null)
      return Function(m); 
    return null;
  }
}
