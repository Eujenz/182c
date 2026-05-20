package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.Config;
import net.database.bean.Npc;
import net.world.ai.NpcAi;
import net.world.instance.BoardInstance;
import net.world.instance.DoorInstance;
import net.world.instance.NpcInstance;
import net.world.instance.SignInstance;
import net.world.kingdom.function.AbyssGuard;
import net.world.npc.Aaman;
import net.world.npc.Aanon;
import net.world.npc.Alda;
import net.world.npc.Aldred;
import net.world.npc.Alice;
import net.world.npc.Almon;
import net.world.npc.Amanda;
import net.world.npc.Andyn;
import net.world.npc.Annabel;
import net.world.npc.Anton;
import net.world.npc.Arieh;
import net.world.npc.Arina;
import net.world.npc.ArmorDivision;
import net.world.npc.Ashur;
import net.world.npc.AuctionBoard;
import net.world.npc.AuctionManager;
import net.world.npc.Axellon;
import net.world.npc.Bahof;
import net.world.npc.Balsim;
import net.world.npc.Barent;
import net.world.npc.Barnia;
import net.world.npc.Bernice;
import net.world.npc.Berry;
import net.world.npc.Beth;
import net.world.npc.Bius;
import net.world.npc.Borgin;
import net.world.npc.Bridget;
import net.world.npc.Britt;
import net.world.npc.Buakheu;
import net.world.npc.Carmel;
import net.world.npc.Catriona;
import net.world.npc.Catty;
import net.world.npc.Chiky;
import net.world.npc.Cold;
import net.world.npc.Cove;
import net.world.npc.Cracker;
import net.world.npc.Daisy;
import net.world.npc.Damon;
import net.world.npc.Daria;
import net.world.npc.Derek;
import net.world.npc.Detecter;
import net.world.npc.Diane;
import net.world.npc.Dick;
import net.world.npc.Dio;
import net.world.npc.Dorin;
import net.world.npc.Doris;
import net.world.npc.Drist;
import net.world.npc.Duana;
import net.world.npc.El;
import net.world.npc.Eleanora;
import net.world.npc.Eliza;
import net.world.npc.Ellyonne;
import net.world.npc.Elmina;
import net.world.npc.Emilia;
import net.world.npc.Emma;
import net.world.npc.Erma;
import net.world.npc.Ernest;
import net.world.npc.Escapefi;
import net.world.npc.Est;
import net.world.npc.Eulalia;
import net.world.npc.Eve;
import net.world.npc.Evelyn;
import net.world.npc.Evert;
import net.world.npc.Evilman;
import net.world.npc.Farlin;
import net.world.npc.Ferdinand;
import net.world.npc.Fiin;
import net.world.npc.Fraoun;
import net.world.npc.Garth;
import net.world.npc.Gayle;
import net.world.npc.Geraldine;
import net.world.npc.Gereng;
import net.world.npc.Giles;
import net.world.npc.Ginevra;
import net.world.npc.Gleda;
import net.world.npc.Glen;
import net.world.npc.Goodman;
import net.world.npc.Gora;
import net.world.npc.Gotham;
import net.world.npc.Guard;
import net.world.npc.GuardKent;
import net.world.npc.Gulian;
import net.world.npc.Gunter;
import net.world.npc.Hakim;
import net.world.npc.HarborMaster;
import net.world.npc.Hector;
import net.world.npc.Henrietta;
import net.world.npc.Herbert;
import net.world.npc.Hirim;
import net.world.npc.Htel;
import net.world.npc.Illdrath;
import net.world.npc.Ione;
import net.world.npc.Ishtar;
import net.world.npc.Ivelviin;
import net.world.npc.Jackson;
import net.world.npc.Jason;
import net.world.npc.Jem;
import net.world.npc.Jenny;
import net.world.npc.Jessy;
import net.world.npc.Jilda;
import net.world.npc.Jin;
import net.world.npc.Joanna;
import net.world.npc.Joel;
import net.world.npc.Johan;
import net.world.npc.Johnson;
import net.world.npc.Judice;
import net.world.npc.Julia;
import net.world.npc.Juliana;
import net.world.npc.Julie;
import net.world.npc.Justine;
import net.world.npc.Karelia;
import net.world.npc.Karim;
import net.world.npc.Karine;
import net.world.npc.Kevin;
import net.world.npc.Kirius;
import net.world.npc.Kreister;
import net.world.npc.Kriom;
import net.world.npc.Kuhatin;
import net.world.npc.Laban;
import net.world.npc.Ladar;
import net.world.npc.Leal;
import net.world.npc.Lengo;
import net.world.npc.Leonora;
import net.world.npc.Leslie;
import net.world.npc.Lien;
import net.world.npc.Lina;
import net.world.npc.Livia;
import net.world.npc.Lucas;
import net.world.npc.Lucinda;
import net.world.npc.Luck;
import net.world.npc.Luth;
import net.world.npc.Luudiel;
import net.world.npc.Lyra;
import net.world.npc.Malcom;
import net.world.npc.Mandra;
import net.world.npc.Manus;
import net.world.npc.Marbin;
import net.world.npc.Margaret;
import net.world.npc.Margery;
import net.world.npc.Marina;
import net.world.npc.Mary;
import net.world.npc.Matt;
import net.world.npc.Mayer;
import net.world.npc.Mellin;
import net.world.npc.Mennefer;
import net.world.npc.Mild;
import net.world.npc.Minerva;
import net.world.npc.Miryam;
import net.world.npc.Momo;
import net.world.npc.Mona;
import net.world.npc.MoneySells;
import net.world.npc.Moran;
import net.world.npc.Moria;
import net.world.npc.Narhen;
import net.world.npc.Nerupa;
import net.world.npc.Neutralman;
import net.world.npc.Nicoline;
import net.world.npc.Nodim;
import net.world.npc.Noela;
import net.world.npc.Old;
import net.world.npc.Oliver;
import net.world.npc.Orcm;
import net.world.npc.Oriel;
import net.world.npc.Orim;
import net.world.npc.Osa;
import net.world.npc.Pagoru;
import net.world.npc.Pandora;
import net.world.npc.Paulina;
import net.world.npc.Philip;
import net.world.npc.Pierre;
import net.world.npc.Pin;
import net.world.npc.Ralf;
import net.world.npc.Randal;
import net.world.npc.Randith;
import net.world.npc.Ribian;
import net.world.npc.Rinda;
import net.world.npc.Riol;
import net.world.npc.Sally;
import net.world.npc.Saloma;
import net.world.npc.Sarah;
import net.world.npc.Sauram;
import net.world.npc.Selena;
import net.world.npc.Sherwin;
import net.world.npc.Shivan;
import net.world.npc.Sidney;
import net.world.npc.Sigrid;
import net.world.npc.Siriss;
import net.world.npc.Stanley;
import net.world.npc.Stella;
import net.world.npc.Steve;
import net.world.npc.Tanya;
import net.world.npc.Tarkin;
import net.world.npc.Tekla;
import net.world.npc.Teodora;
import net.world.npc.Terry;
import net.world.npc.Tessa;
import net.world.npc.Therapist;
import net.world.npc.Thram;
import net.world.npc.Tilon;
import net.world.npc.Touma;
import net.world.npc.Tovia;
import net.world.npc.Tracy;
import net.world.npc.Trey;
import net.world.npc.Tyrus;
import net.world.npc.Valeska;
import net.world.npc.Varyeth;
import net.world.npc.Velma;
import net.world.npc.Vergil;
import net.world.npc.Verita;
import net.world.npc.Vevina;
import net.world.npc.Victor;
import net.world.npc.Vincent;
import net.world.npc.WeaponDivision;
import net.world.npc.Werner;
import net.world.npc.Wilma;
import net.world.npc.Yiwanga;
import net.world.npc.Ysorya;
import net.world.npc.Zeno;
import net.world.npc.elf.Arachne;
import net.world.npc.elf.Ent;
import net.world.npc.elf.Fairy;
import net.world.npc.elf.FairyQueen;
import net.world.npc.elf.Pan;
import net.world.object.L1Object;
import net.world.slimerace.SlimeRaceSystem;

public class NpcSpawnTable {
  private static class Holder {
    static NpcSpawnTable instance = new NpcSpawnTable();
  }
  
  public static NpcSpawnTable getInstance() {
    return Holder.instance;
  }
  
  private NpcSpawnTable() {
    System.out.println("[SQL] 加?NPC?生列表.");
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM npc_spawnlist");
      rs = st.executeQuery();
      while (rs.next()) {
        Npc n = NpcTable.getInstance().getNpcTemplate(rs.getInt(2));
        if (n != null) {
          L1Object o = FunctionNpc(n);
          if (o != null) {
            o.setObjectId(Config.getObjectID_ETC());
            o.setGfx(n.get_gfxid());
            o.setGfxMode(n.get_gfxMode());
            o.setClassGfx(o.getGfx());
            o.setClassGfxMode(o.getGfxMode());
            o.setLawful(n.getLawful());
            if (Config.SHOW_NPC_ID) {
              o.setName(n.get_nameid() + "[" + n.get_npcId() + "]");
            } else {
              o.setName(n.get_nameid());
            } 
            o.setMaxHp(n.getHp());
            o.setCurrentHp(n.getHp());
            o.setLight(n.get_light());
            o.setHomeX(rs.getInt(3));
            o.setHomeY(rs.getInt(4));
            o.setHomeMap(rs.getInt(5));
            o.setHeading(rs.getInt(6));
            o.setTitle(rs.getString(8));
            o.setHeading(rs.getInt(6));
            o.setHomeHeading(rs.getInt(6));
            o.toTeleport(o.getHomeX(), o.getHomeY(), o.getHomeMap());
            if (n.get_ai() && o instanceof NpcInstance)
              NpcAi.getInstance().addNpc((NpcInstance)o); 
          } 
        } 
      } 
    } catch (Exception localException) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  public L1Object FunctionNpc(Npc n) {
    Guard guard;
    Pandora pandora;
    Gunter gunter;
    Balsim balsim;
    Dorin dorin;
    HarborMaster harborMaster;
    Catty catty;
    Luth luth;
    Karim karim;
    Aaman aaman;
    Gora gora;
    Gereng gereng;
    Orim orim;
    Lengo lengo;
    Fiin fiin;
    Judice judice;
    Andyn andyn;
    Ysorya ysorya;
    Bahof bahof;
    Touma touma;
    Lyra lyra;
    Thram thram;
    Johnson johnson;
    Dick dick;
    Nerupa nerupa;
    El el;
    Arachne arachne;
    Pan pan;
    Fairy fairy;
    Ent ent;
    FairyQueen fairyQueen;
    Narhen narhen;
    Lucas lucas;
    Steve steve;
    Stanley stanley;
    Sidney sidney;
    Philip philip;
    Hector hector;
    Terry terry;
    Vincent vincent;
    Evert evert;
    Victor victor;
    Jason jason;
    Laban laban;
    Randal randal;
    Dio dio;
    Anton anton;
    Derek derek;
    Mona mona;
    Eliza eliza;
    Moria moria;
    Margaret margaret;
    Jessy jessy;
    Jenny jenny;
    Velma velma;
    Alice alice;
    Evelyn evelyn;
    Tovia tovia;
    Leal leal;
    Alda alda;
    Amanda amanda;
    Lina lina;
    Sally sally;
    Daisy daisy;
    Bridget bridget;
    Tanya tanya;
    Daria daria;
    Doris doris;
    Tracy tracy;
    Marina marina;
    Emma emma;
    Verita verita;
    Elmina elmina;
    Marbin marbin;
    Glen glen;
    Mellin mellin;
    Aanon aanon;
    Randith randith;
    Trey trey;
    Matt matt;
    Tarkin tarkin;
    Gotham gotham;
    Borgin borgin;
    Selena selena;
    Jackson jackson;
    Ashur ashur;
    Ladar ladar;
    Farlin farlin;
    Lien lien;
    Julie julie;
    Pin pin;
    Joel joel;
    Kuhatin kuhatin;
    Est est;
    Herbert herbert;
    Sauram sauram;
    Nodim nodim;
    Malcom malcom;
    Damon damon;
    Tyrus tyrus;
    Sherwin sherwin;
    Moran moran;
    Ferdinand ferdinand;
    Giles giles;
    Aldred aldred;
    Gulian gulian;
    Manus manus;
    Pierre pierre;
    Garth garth;
    Oliver oliver;
    Ernest ernest;
    Werner werner;
    Vergil vergil;
    Kevin kevin;
    Almon almon;
    Mayer mayer;
    Diane diane;
    Beth beth;
    Henrietta henrietta;
    Justine justine;
    Eve eve;
    Valeska valeska;
    Carmel carmel;
    Vevina vevina;
    Livia livia;
    Jilda jilda;
    Sarah sarah;
    Tekla tekla;
    Eleanora eleanora;
    Mary mary;
    Nicoline nicoline;
    Eulalia eulalia;
    Julia julia;
    Catriona catriona;
    Miryam miryam;
    Mennefer mennefer;
    Osa osa;
    Joanna joanna;
    Teodora teodora;
    Margery margery;
    Gleda gleda;
    Noela noela;
    Lucinda lucinda;
    Minerva minerva;
    Geraldine geraldine;
    Paulina paulina;
    Emilia emilia;
    Juliana juliana;
    Saloma saloma;
    Bernice bernice;
    Karelia karelia;
    Karine karine;
    Leonora leonora;
    Ione ione;
    Ginevra ginevra;
    Duana duana;
    Sigrid sigrid;
    Tessa tessa;
    Erma erma;
    Wilma wilma;
    AuctionManager auctionManager;
    AuctionBoard auctionBoard;
    BoardInstance boardInstance;
    Therapist therapist;
    Zeno zeno;
    Drist drist;
    Buakheu buakheu;
    Luudiel luudiel;
    Shivan shivan;
    Britt britt;
    Riol riol;
    Hakim hakim;
    AbyssGuard abyssGuard;
    Ivelviin ivelviin;
    Berry berry;
    Stella stella;
    Cold cold;
    Arieh arieh;
    Momo momo;
    Orcm orcm;
    Old old;
    Jin jin;
    Ralf ralf;
    Leslie leslie;
    Cove cove;
    Axellon axellon;
    Kriom kriom;
    ArmorDivision armorDivision;
    WeaponDivision weaponDivision;
    Siriss siriss;
    Fraoun fraoun;
    Johan johan;
    Gayle gayle;
    Illdrath illdrath;
    Ishtar ishtar;
    Goodman goodman;
    Neutralman neutralman;
    Evilman evilman;
    Arina arina;
    Annabel annabel;
    Oriel oriel;
    Barent barent;
    Mild mild;
    Kirius kirius;
    Bius bius;
    Mandra mandra;
    Varyeth varyeth;
    Kreister kreister;
    Rinda rinda;
    Ellyonne ellyonne;
    Pagoru pagoru;
    Hirim hirim;
    Barnia barnia;
    Ribian ribian;
    Jem jem;
    Escapefi escapefi;
    Detecter detecter;
    Chiky chiky;
    Luck luck;
    Tilon tilon;
    Htel htel;
    Cracker cracker;
    GuardKent guardKent;
    L1Object l1Object1 = null, obj = null;
    if (n.get_npcId() == 70550)
      return (L1Object)new Yiwanga(n.get_npcId()); 
    if (n.get_npcId() == 70449)
      return (L1Object)new MoneySells(n.get_npcId()); 
    switch (n.get_nameidN()) {
      case 240:
        return (L1Object)new Guard(n);
      case 269:
        return (L1Object)new Pandora(n.get_npcId());
      case 270:
        return (L1Object)new Gunter();
      case 304:
        return (L1Object)new Balsim(n.get_npcId());
      case 309:
        return (L1Object)new Dorin();
      case 320:
        return (L1Object)new HarborMaster(n.get_npcId());
      case 332:
        return (L1Object)new Catty(n.get_npcId());
      case 333:
        return (L1Object)new Luth(n.get_npcId());
      case 334:
        return (L1Object)new Karim();
      case 365:
        aaman = new Aaman();
        SlimeRaceSystem.getInstance().setGora1((L1Object)aaman);
        return (L1Object)aaman;
      case 373:
        gora = new Gora();
        SlimeRaceSystem.getInstance().setGora2((L1Object)gora);
        return (L1Object)gora;
      case 374:
        return (L1Object)new Gereng();
      case 406:
        return (L1Object)new Orim(n.get_npcId());
      case 454:
        return (L1Object)new Lengo(n);
      case 455:
        return (L1Object)new Fiin(n);
      case 456:
        return (L1Object)new Judice(n);
      case 458:
        return (L1Object)new Andyn(n.get_npcId());
      case 459:
        return (L1Object)new Ysorya(n.get_npcId());
      case 468:
        return (L1Object)new Bahof();
      case 478:
        return (L1Object)new Touma();
      case 484:
        return (L1Object)new Lyra();
      case 568:
        return (L1Object)new Thram();
      case 614:
        return (L1Object)new Johnson("johnson");
      case 615:
        return (L1Object)new Dick("dick");
      case 749:
        return (L1Object)new Nerupa(n.get_npcId());
      case 750:
        return (L1Object)new El();
      case 752:
        return (L1Object)new Arachne(n);
      case 753:
        return (L1Object)new Pan(n);
      case 754:
        return (L1Object)new Fairy(n);
      case 755:
        return (L1Object)new Ent(n);
      case 805:
        return (L1Object)new FairyQueen(n);
      case 811:
        return (L1Object)new Narhen(n.get_npcId());
      case 829:
        return (L1Object)new Lucas(n.get_npcId());
      case 830:
        return (L1Object)new Steve(n.get_npcId());
      case 831:
        return (L1Object)new Stanley(n.get_npcId());
      case 837:
        return (L1Object)new Sidney(n);
      case 838:
        return (L1Object)new Philip(n.get_npcId());
      case 846:
        return (L1Object)new Hector(n.get_npcId());
      case 847:
        return (L1Object)new Terry(n);
      case 848:
        return (L1Object)new Vincent(n.get_npcId());
      case 849:
        return (L1Object)new Evert(n.get_npcId());
      case 852:
        return (L1Object)new Victor(n);
      case 855:
        return (L1Object)new Jason(n.get_npcId());
      case 856:
        return (L1Object)new Laban(n);
      case 857:
        return (L1Object)new Randal(n.get_npcId());
      case 859:
        return (L1Object)new Dio(n.get_npcId());
      case 860:
        return (L1Object)new Anton(n.get_npcId());
      case 861:
        return (L1Object)new Derek(n.get_npcId());
      case 862:
        return (L1Object)new Mona(n);
      case 864:
        return (L1Object)new Eliza(n);
      case 865:
        return (L1Object)new Moria(n.get_npcId());
      case 866:
        return (L1Object)new Margaret(n.get_npcId());
      case 868:
        return (L1Object)new Jessy(n);
      case 870:
        return (L1Object)new Jenny(n);
      case 871:
        return (L1Object)new Velma(n);
      case 872:
        return (L1Object)new Alice(n);
      case 873:
        return (L1Object)new Evelyn(n);
      case 874:
        return (L1Object)new Tovia(n);
      case 875:
        return (L1Object)new Leal(n);
      case 876:
        return (L1Object)new Alda(n);
      case 877:
        return (L1Object)new Amanda(n);
      case 878:
        return (L1Object)new Lina(n);
      case 879:
        return (L1Object)new Sally(n);
      case 880:
        return (L1Object)new Daisy(n);
      case 881:
        return (L1Object)new Bridget(n);
      case 882:
        return (L1Object)new Tanya(n);
      case 883:
        return (L1Object)new Daria(n);
      case 884:
        return (L1Object)new Doris(n);
      case 885:
        return (L1Object)new Tracy(n);
      case 888:
        return (L1Object)new Marina();
      case 889:
        return (L1Object)new Emma();
      case 891:
        return (L1Object)new Verita(n.get_npcId());
      case 910:
        return (L1Object)new Elmina(n.get_npcId());
      case 911:
        return (L1Object)new Marbin("marbin");
      case 915:
        return (L1Object)new Glen(n.get_npcId());
      case 916:
        return (L1Object)new Mellin(n.get_npcId());
      case 917:
        return (L1Object)new Aanon();
      case 932:
        return (L1Object)new Randith();
      case 946:
        return (L1Object)new Trey(n.get_npcId());
      case 947:
        return (L1Object)new Matt(n.get_npcId());
      case 948:
        return (L1Object)new Tarkin();
      case 949:
        return (L1Object)new Gotham();
      case 950:
        return (L1Object)new Borgin();
      case 955:
        return (L1Object)new Selena();
      case 964:
        return (L1Object)new Jackson(n.get_npcId());
      case 965:
        return (L1Object)new Ashur(n.get_npcId());
      case 1053:
        return (L1Object)new Ladar(n.get_npcId());
      case 1054:
        return (L1Object)new Farlin(n.get_npcId());
      case 1055:
        return (L1Object)new Lien(n.get_npcId());
      case 1056:
        return (L1Object)new Julie(n.get_npcId());
      case 1057:
        return (L1Object)new Pin(n.get_npcId());
      case 1058:
        return (L1Object)new Joel(n.get_npcId());
      case 1073:
        return (L1Object)new Kuhatin();
      case 1145:
        return (L1Object)new Est(n.get_npcId());
      case 1246:
        return (L1Object)new Herbert(n.get_npcId());
      case 1248:
        return (L1Object)new Sauram();
      case 1249:
        return (L1Object)new Nodim();
      case 1259:
        return (L1Object)new Malcom(n);
      case 1260:
        return (L1Object)new Damon(n);
      case 1261:
        return (L1Object)new Tyrus(n);
      case 1262:
        return (L1Object)new Sherwin(n);
      case 1263:
        return (L1Object)new Moran(n);
      case 1264:
        return (L1Object)new Ferdinand(n);
      case 1265:
        return (L1Object)new Giles(n);
      case 1266:
        return (L1Object)new Aldred(n);
      case 1267:
        return (L1Object)new Gulian(n);
      case 1268:
        return (L1Object)new Manus(n);
      case 1269:
        return (L1Object)new Pierre(n);
      case 1270:
        return (L1Object)new Garth(n);
      case 1271:
        return (L1Object)new Oliver(n);
      case 1272:
        return (L1Object)new Ernest(n);
      case 1286:
        return (L1Object)new Werner(n.get_npcId());
      case 1295:
        return (L1Object)new Vergil(n.get_npcId());
      case 1298:
        return (L1Object)new Kevin("kevin");
      case 1299:
        return (L1Object)new Almon("almon");
      case 1301:
        return (L1Object)new Mayer(n.get_npcId());
      case 1306:
        return (L1Object)new Diane();
      case 1307:
        return (L1Object)new Beth();
      case 1308:
        return (L1Object)new Henrietta();
      case 1309:
        return (L1Object)new Justine();
      case 1310:
        return (L1Object)new Eve();
      case 1311:
        return (L1Object)new Valeska();
      case 1312:
        return (L1Object)new Carmel();
      case 1313:
        return (L1Object)new Vevina();
      case 1314:
        return (L1Object)new Livia();
      case 1315:
        return (L1Object)new Jilda();
      case 1316:
        return (L1Object)new Sarah();
      case 1317:
        return (L1Object)new Tekla();
      case 1318:
        return (L1Object)new Eleanora();
      case 1319:
        return (L1Object)new Mary();
      case 1320:
        return (L1Object)new Nicoline();
      case 3121:
        return (L1Object)new Eulalia();
      case 1322:
        return (L1Object)new Julia();
      case 1323:
        return (L1Object)new Catriona();
      case 1324:
        return (L1Object)new Miryam();
      case 1325:
        return (L1Object)new Mennefer();
      case 1326:
        return (L1Object)new Osa();
      case 1327:
        return (L1Object)new Joanna();
      case 1328:
        return (L1Object)new Teodora();
      case 1329:
        return (L1Object)new Margery();
      case 1330:
        return (L1Object)new Gleda();
      case 1331:
        return (L1Object)new Noela();
      case 1332:
        return (L1Object)new Lucinda();
      case 1333:
        return (L1Object)new Minerva();
      case 1334:
        return (L1Object)new Geraldine();
      case 1335:
        return (L1Object)new Paulina();
      case 1336:
        return (L1Object)new Emilia();
      case 1337:
        return (L1Object)new Juliana();
      case 1338:
        return (L1Object)new Saloma();
      case 1339:
        return (L1Object)new Bernice();
      case 1340:
        return (L1Object)new Karelia();
      case 1341:
        return (L1Object)new Karine();
      case 1342:
        return (L1Object)new Leonora();
      case 1343:
        return (L1Object)new Ione();
      case 1344:
        return (L1Object)new Ginevra();
      case 1345:
        return (L1Object)new Duana();
      case 1346:
        return (L1Object)new Sigrid();
      case 1347:
        return (L1Object)new Tessa();
      case 1348:
        return (L1Object)new Erma();
      case 1354:
        return (L1Object)new Wilma(n.get_npcId());
      case 1382:
        return (L1Object)new AuctionManager();
      case 1383:
        return (L1Object)new AuctionBoard();
      case 1385:
        return (L1Object)new BoardInstance();
      case 1415:
        return (L1Object)new Therapist();
      case 1418:
        return (L1Object)new Zeno(407);
      case 1423:
        return (L1Object)new Drist(411);
      case 1434:
        return (L1Object)new Buakheu(n.get_npcId());
      case 1510:
        return (L1Object)new Luudiel(n.get_npcId());
      case 1515:
        return (L1Object)new Shivan(n.get_npcId());
      case 1516:
        return (L1Object)new Britt(n.get_npcId());
      case 1517:
        return (L1Object)new Riol(n.get_npcId());
      case 1538:
        return (L1Object)new Hakim();
      case 1577:
        return (L1Object)new AbyssGuard(n);
      case 1592:
        return (L1Object)new Ivelviin(n.get_npcId());
      case 1594:
        return (L1Object)new Berry(n.get_npcId());
      case 1624:
        return (L1Object)new Stella(n.get_npcId());
      case 1637:
        return (L1Object)new Cold(n.get_npcId());
      case 1638:
        return (L1Object)new Arieh(n.get_npcId());
      case 1639:
        return (L1Object)new Momo(n.get_npcId());
      case 1640:
        return (L1Object)new Orcm(n.get_npcId());
      case 1641:
        return (L1Object)new Old(n.get_npcId());
      case 1642:
        return (L1Object)new Jin(n.get_npcId());
      case 1595:
        return (L1Object)new Ralf(n.get_npcId());
      case 1596:
        return (L1Object)new Leslie(n.get_npcId());
      case 1597:
        return (L1Object)new Cove("cove");
      case 1604:
        return (L1Object)new Axellon();
      case 1611:
        return (L1Object)new Kriom();
      case 1685:
        return (L1Object)new ArmorDivision();
      case 1686:
        return (L1Object)new WeaponDivision();
      case 1416:
        return (L1Object)new Siriss();
      case 1420:
        return (L1Object)new Fraoun(n.get_npcId());
      case 1414:
        return (L1Object)new Johan(n.get_npcId());
      case 1531:
        return (L1Object)new Gayle();
      case 1422:
        return (L1Object)new Illdrath(n.get_npcId());
      case 1417:
        return (L1Object)new Ishtar(n.get_npcId());
      case 1501:
        return (L1Object)new Goodman();
      case 1502:
        return (L1Object)new Neutralman();
      case 1503:
        return (L1Object)new Evilman();
      case 1524:
        return (L1Object)new Arina(n);
      case 1525:
        return (L1Object)new Annabel(n);
      case 1527:
        return (L1Object)new Oriel(n);
      case 1528:
        return (L1Object)new Barent(n);
      case 1772:
        return (L1Object)new Mild("mild");
      case 1773:
        return (L1Object)new Kirius(n.get_npcId());
      case 1775:
        return (L1Object)new Bius(n.get_npcId());
      case 1776:
        return (L1Object)new Mandra(n.get_npcId());
      case 1779:
        return (L1Object)new Varyeth(n.get_npcId());
      case 1781:
        return (L1Object)new Kreister(n.get_npcId());
      case 1875:
        return (L1Object)new Rinda(n.get_npcId());
      case 1780:
        return (L1Object)new Ellyonne();
      case 1876:
        return (L1Object)new Pagoru(n.get_npcId());
      case 1878:
        return (L1Object)new Hirim();
      case 1897:
        return (L1Object)new Barnia(70561);
      case 1898:
        return (L1Object)new Ribian(70565);
      case 1928:
        return (L1Object)new Jem();
      case 1932:
        return (L1Object)new Escapefi(n.get_npcId());
      case 1953:
        return (L1Object)new Detecter(n.get_npcId());
      case 1954:
        return (L1Object)new Chiky(n.get_npcId());
      case 1955:
        return (L1Object)new Luck(n.get_npcId());
      case 1956:
        return (L1Object)new Tilon(n.get_npcId());
      case 2098:
        return (L1Object)new Htel(70560);
    } 
    switch (n.get_npcId()) {
      case 497:
        return (L1Object)new Cracker();
      case 516:
      case 517:
        return (L1Object)new GuardKent(n);
    } 
    if ("Door".equalsIgnoreCase(n.get_type())) {
      DoorInstance doorInstance = new DoorInstance();
    } else if ("Sign".equalsIgnoreCase(n.get_type())) {
      SignInstance signInstance = new SignInstance();
    } else if (n.get_ai()) {
      NpcInstance npcInstance = new NpcInstance(n);
    } else {
      n.get_gfxid();
      l1Object1 = new L1Object();
    } 
    return l1Object1;
  }
  
  public void insertNpcSpawn(Npc n, int x, int y, int map, int heading) {
    Connection con = null;
    PreparedStatement st = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("INSERT INTO npc_spawnlist SET name=?, npcID=?, locX=?, locY=?, locMap=?, heading=?");
      st.setString(1, n.get_name() + " " + System.currentTimeMillis());
      st.setInt(2, n.get_npcId());
      st.setInt(3, x);
      st.setInt(4, y);
      st.setInt(5, map);
      st.setInt(6, heading);
      st.execute();
      st.close();
    } catch (Exception localException) {
      try {
        st.close();
      } catch (Exception localException1) {}
      try {
        con.close();
      } catch (Exception localException2) {}
      try {
        st.close();
      } catch (Exception localException3) {}
      try {
        con.close();
      } catch (Exception localException4) {}
    } finally {
      try {
        st.close();
      } catch (Exception localException5) {}
      try {
        con.close();
      } catch (Exception localException6) {}
    } 
  }
}
