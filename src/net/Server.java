package net;

import java.io.BufferedInputStream;
import java.io.File;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import net.database.AccountTable;
import net.database.BanListTable;
import net.database.CharacterTable;
import net.database.ChineseCommand;
import net.database.DatabaseConnection;
import net.database.DungeonTable;
import net.database.ExpTable;
import net.database.GetBackRestartTable;
import net.database.HellTable;
import net.database.ItemsSetoptionTable;
import net.database.ItemsTable;
import net.database.MonsterItemDropTable;
import net.database.MonsterSpawnTable;
import net.database.MonsterTable;
import net.database.NpcSpawnTable;
import net.database.NpcTable;
import net.database.PolymorphTable;
import net.database.RandomGift;
import net.database.SkillTable;
import net.database.SprTable;
import net.mina.LineageBlackListFilter;
import net.mina.LineageCodecFactory;
import net.mina.LineageConnectionThrottleFilter;
import net.online.WriteHTMLOnline;
import net.online.WriteHTMLOnlinePc;
import net.util.ClientFileLoad;
import net.util.ServerBasePacketPooling;
import net.util.SystemTimer;
import net.world.WorldInstance;
import net.world.WorldMap;
import net.world.ai.MonAi;
import net.world.ai.NpcAi;
import net.world.function.ClanSystem;
import net.world.function.WorldCleanSystem;
import net.world.kingdom.KingdomAbyss;
import net.world.kingdom.KingdomGiran;
import net.world.kingdom.KingdomHeine;
import net.world.kingdom.KingdomKent;
import net.world.kingdom.KingdomOrcish;
import net.world.kingdom.KingdomWindawood;
import net.world.slimerace.SlimeRaceSystem;
import net.world.time.BanListTimer;
import net.world.time.BuffTimerInstance;
import net.world.time.HellTimerInstance;
import net.world.time.HpMpTimer;
import net.world.time.ItemTimerInstance;
import net.world.time.KingdomUpdateTimer;
import net.world.time.LoginTimer;
import net.world.time.MonDropTimer;
import net.world.time.MonthCardsTimer;
import net.world.time.RemoveCharacterCreateTimer;
import net.world.time.RemoveCharacterDeleteTimer;
import net.world.time.RemoveCodeCountTimer;
import net.world.time.ShutdownTimer;
import net.world.time.WorldTimer;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {
  static final Logger log = LoggerFactory.getLogger(Server.class);
  
  static boolean inv = false;
  
  static boolean book = false;
  
  static boolean skill = false;
  
  public static void main(String[] args) throws Exception {
    loading();
    NioSocketAcceptor acceptor = new NioSocketAcceptor();
    DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
    chain.addLast(LineageBlackListFilter.NAME, (IoFilter)new LineageBlackListFilter());
    chain.addLast(LineageConnectionThrottleFilter.NAME, (IoFilter)new LineageConnectionThrottleFilter(300L));
    chain.addLast("codec", (IoFilter)new ProtocolCodecFilter((ProtocolCodecFactory)new LineageCodecFactory()));
    acceptor.setHandler((IoHandler)LineageProtocolHandler.getInstance());
    acceptor.bind(new InetSocketAddress(Config.SERVER_PORT));
    String startMsg = "使用端口" + Config.SERVER_PORT + "，啟動服務器。";
    log.info(startMsg);
    System.out.println(startMsg);
    Config.save();
    if (inv || book || skill) {
      System.out.println("讀取完成，重載遊戲");
      System.exit(1);
    } 
    (new ConsoleProcess()).start();
  }
  
  private static void loading() throws Exception {
    new Config();
    ServerBasePacketPooling.getInstance();
    DatabaseConnection.getInstance();
    ClientFileLoad.getInstance();
    WorldMap.getInstance();
    ChineseCommand.getInstance().load();
    DungeonTable.getInstance();
    WorldInstance.getInstance();
    ExpTable.getInstance();
    AccountTable.getInstance().load();
    CharacterTable.getInstance().load();
    HellTable.getInstance();
    PolymorphTable.getInstance();
    ItemsTable.getInstance();
    ItemsSetoptionTable.getInstance();
    SkillTable.getInstance();
    NpcTable.getInstance();
    NpcSpawnTable.getInstance();
    MonsterItemDropTable.getInstance();
    MonsterTable.getInstance();
    MonsterSpawnTable.getInstance();
    ClanSystem.getInstance();
    SprTable.getInstance();
    KingdomKent.getInstance();
    KingdomOrcish.getInstance();
    KingdomWindawood.getInstance();
    KingdomGiran.getInstance();
    KingdomHeine.getInstance();
    KingdomAbyss.getInstance();
    GetBackRestartTable.getInstance();
    ItemTimerInstance.getInstance().start();
    BuffTimerInstance.getInstance().start();
    HpMpTimer.getInstance().start();
    WorldTimer.getInstance().start();
    KingdomUpdateTimer.getInstance().start();
    RemoveCodeCountTimer.getInstance().start();
    RemoveCharacterCreateTimer.getInstance().start();
    RemoveCharacterDeleteTimer.getInstance().start();
    HellTimerInstance.getInstance().start();
    SystemTimer.getInstance().start();
    AnnouncementsCycle.getInstance();
    BanListTable.getInstance().load();
    BanListTimer.getInstance().start();
    if (Config.OPEN_RECHARGE)
      MonthCardsTimer.getInstance().start(); 
    if (Config.RESTART_TIME >= 0 && Config.RESTART_TIME <= 23)
      ShutdownTimer.getInstance().start(); 
    NpcAi.getInstance().start();
    MonAi.getInstance().start();
    if (Config.SLIME)
      SlimeRaceSystem.getInstance().start(); 
    if (Config.LOGIN_VERIFICATION)
      (new LoginTimer()).start(); 
    WorldCleanSystem.getInstance().initialize();
    Config.initBeginnerItems();
    if (Config.HTML_ONLINE)
      WriteHTMLOnline.getInstance().start(); 
    if (Config.HTML_ONLINE_PC)
      WriteHTMLOnlinePc.getInstance().start(); 
    RandomGift.getInstance().load();
    MonDropTimer.getInstance().start();
  }
  
  private void readOldDate(String[] args) {
    if (args.length > 2) {
      inv = "1".equals(args[0]);
      book = "1".equals(args[1]);
      skill = "1".equals(args[2]);
    } 
    if (inv || book || skill) {
      File[] all = (new File("db/")).listFiles();
      for (File account : all) {
        if (!account.isFile()) {
          File[] accounts = account.listFiles();
          Connection con = null;
          PreparedStatement pstm = null;
          ResultSet rs = null;
          BufferedInputStream bis = null;
          byte[] data = null;
          StringTokenizer st = null;
          String fileName = "";
          File[] arr$ = accounts;
          int len$ = arr$.length, i$ = 0;
          while (true) {
            if (i$ < len$) {
              File ch = arr$[i$];
              File[] db = ch.listFiles();
              if (db.length >= 1)
                System.out.println("正整理<" + account.getName() + "> 下的 <" + ch.getName() + "> 文件到數據庫中"); 
            } else {
              break;
            } 
            i$++;
          } 
        } 
      } 
      System.out.println("讀取完成，重載遊戲");
      System.exit(1);
    } 
  }
}
