package net;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.database.DatabaseConnection;
import net.database.ItemsTable;
import net.world.WorldInstance;
import net.world.instance.ItemInstance;

public class Config {
  public static int STATUS = 16;
  
  public static String DRIVER;
  
  public static String URL;
  
  public static String USER;
  
  public static String PASS;
  
  public static String SERVER_IP = "*";
  
  public static int SERVER_PORT = 2000;
  
  public static boolean DEBUG = false;
  
  public static boolean DEBUG_SPEED_PRINT = false;
  
  public static boolean SPEED_MESSAGE = false;
  
  public static boolean LOGSCLIENTPACKET = false;
  
  public static int ATTACK_COUNT;
  
  public static int MOVE_COUNT;
  
  public static int MAGIC_COUNT;
  
  public static int SPEED_HACK = 3;
  
  public static double SPEED_1 = 1.3D;
  
  public static double SPEED_2 = 1.6D;
  
  public static boolean SPEED_1_PUN = false;
  
  public static boolean SPEED_2_PUN = false;
  
  public static final String TIME_ZONE = "GMT+8";
  
  public static final String[] LANGUAGE_CODE_ARRAY = new String[] { "UTF8", "EUCKR", "UTF8", "BIG5", "SJIS", "GBK" };
  
  public static int CLIENT_LANGUAGE = 3;
  
  public static String CLIENT_LANGUAGE_CODE;
  
  public static int RATE_EXP = 2;
  
  public static int RATE_ADEN = 2;
  
  public static int RATE_EN = 2;
  
  public static int RATE_DROP = 2;
  
  public static final int RATE_PETHP = 15;
  
  public static final int DROP_TOTEM = 100;
  
  public static final boolean PACKET = false;
  
  public static boolean AUTO_ACCOUNT = true;
  
  public static final int MAX_CHAR = 3;
  
  public static final int INVENTORY_COUNT = 180;
  
  public static final int WAREHOUSE_COUNT = 100;
  
  public static final int TRADE_MAXITEM = 12;
  
  public static final int PARTY_MAX = 8;
  
  public static int WORLDTIME;
  
  public static int LEVEL_MAX = 52;
  
  public static final int WAREHOUSEPRICE = 30;
  
  public static final int PETGETPRICE = 80;
  
  public static int PET_TO_DEAT_TIME = 600;
  
  public static final int BOARDWRITEPRICE = 300;
  
  public static final int SLIMERACEPRICE = 100;
  
  public static int CHARACTER_STAT = 25;
  
  public static final int OBJECT_SHOUT_LOC = 50;
  
  public static final int HPMPTIMER = 1000;
  
  public static final int ITEMTIMER = 1000;
  
  public static final int BUFFTIMER = 1000;
  
  public static final int WORLDTIMER = 1000;
  
  public static final int SYSTEMTIMER = 10000;
  
  public static final int CLASSTYPE_ROYAL = 0;
  
  public static final int CLASSTYPE_KNIGHT = 1;
  
  public static final int CLASSTYPE_ELF = 2;
  
  public static final int CLASSTYPE_WIZARD = 3;
  
  public static final int BOARDTYPE_ISLAND = 0;
  
  public static final int BOARDTYPE_GIRAN = 1;
  
  public static final int BOARDTYPE_HEINE = 2;
  
  public static final int NORMAL_ZONE = 0;
  
  public static final int SAFETY_ZONE = 16;
  
  public static final int COMBAT_ZONE = 32;
  
  public static final int STATUS_MOVE = 0;
  
  public static final int STATUS_NORMAR_ATTACK = 1;
  
  public static final int STATUS_NORMAR_BOWATTACK = 21;
  
  public static final int RoyalLocationX = 32700;
  
  public static final int RoyalLocationY = 32868;
  
  public static final int RoyalLocationMAP = 69;
  
  public static final int KnightLocationX = 32700;
  
  public static final int KnightLocationY = 32868;
  
  public static final int KnightLocationMAP = 69;
  
  public static final int ElfLocationX = 32700;
  
  public static final int ElfLocationY = 32868;
  
  public static final int ElfLocationMAP = 69;
  
  public static final int WizardLocationX = 32700;
  
  public static final int WizardLocationY = 32868;
  
  public static final int WizardLocationMAP = 69;
  
  public static final int RoyalGFX_Male = 0;
  
  public static final int RoyalGFX_Female = 1;
  
  public static final int KnightGFX_Male = 61;
  
  public static final int KnightGFX_Female = 48;
  
  public static final int ElfGFX_Male = 138;
  
  public static final int ElfGFX_Female = 37;
  
  public static final int WizardGFX_Male = 734;
  
  public static final int WizardGFX_Female = 1186;
  
  public static final int RoyalHP = 14;
  
  public static final int RoyalMP = 2;
  
  public static final int KnightHP = 16;
  
  public static final int KnightMP = 1;
  
  public static final int ElfHP = 15;
  
  public static final int ElfMP = 4;
  
  public static final int WizardHP = 12;
  
  public static final int WizardMP = 8;
  
  public static List<ItemInstance> beginnerItems;
  
  public static int RoyalMaxHP;
  
  public static int RoyalMaxMP;
  
  public static int KnightMaxHP;
  
  public static int KnightMaxMP;
  
  public static int ElfMaxHP;
  
  public static int ElfMaxMP;
  
  public static int WizardMaxHP;
  
  public static int WizardMaxMP;
  
  public static boolean shutdown = false;
  
  private static int ObjectID = 1;
  
  private static int ObjectID_ETC = 100000;
  
  private static int ClanID = 1;
  
  public static final int LocationRange = 12;
  
  public static final int SEARCH_MONSTER_TARGET_LOCATION = 17;
  
  public static final int SEARCH_WORLD_LOCATION = 40;
  
  public static int WORLD_ALLCHATING = 5;
  
  public static final int WAREHOUSE_LEV = 5;
  
  public static final int WHISPER_LEV = 7;
  
  public static final int PK_LEV = 1;
  
  public static int CLAN_MAKE_LEV = 5;
  
  public static int CLAN_NAME_MAX_SIZE = 20;
  
  public static final int CHAOTICZONE_X1 = 32884;
  
  public static final int CHAOTICZONE_X2 = 32891;
  
  public static final int CHAOTICZONE_Y1 = 32647;
  
  public static final int CHAOTICZONE_Y2 = 32656;
  
  public static final int LAWFULLZONE_X1 = 33117;
  
  public static final int LAWFULLZONE_X2 = 33128;
  
  public static final int LAWFULLZONE_Y1 = 32931;
  
  public static final int LAWFULLZONE_Y2 = 32942;
  
  public static final int TREEX1 = 33050;
  
  public static final int TREEX2 = 33058;
  
  public static final int TREEY1 = 32333;
  
  public static final int TREEY2 = 32341;
  
  public static final byte ITEM_ETC = 0;
  
  public static final byte ITEM_WEAPON = 1;
  
  public static final byte ITEM_ARMOR = 2;
  
  public static final byte ITEM_BOOK = 3;
  
  public static final byte ITEM_REMOVE = 0;
  
  public static final byte ITEM_ADD = 1;
  
  public static final byte ITEM_HAVEREMOVE = 2;
  
  public static final byte SLOT_HELM = 0;
  
  public static final byte SLOT_EARRING = 1;
  
  public static final byte SLOT_NECKLACE = 2;
  
  public static final byte SLOT_T = 3;
  
  public static final byte SLOT_ARMOR = 4;
  
  public static final byte SLOT_CLOAK = 5;
  
  public static final byte SLOT_RING_LEFT = 6;
  
  public static final byte SLOT_RING_RIGHT = 7;
  
  public static final byte SLOT_BELT = 8;
  
  public static final byte SLOT_GLOVE = 9;
  
  public static final byte SLOT_SHIELD = 10;
  
  public static final byte SLOT_WEAPON = 11;
  
  public static final byte SLOT_BOOTS = 12;
  
  public static final byte SLOT_NONE = 13;
  
  public static final byte ITEM_TYPE1_WEAPON = 1;
  
  public static final byte ITEM_TYPE1_ARMOR = 2;
  
  public static final byte ITEM_TYPE1_CLOAK = 10;
  
  public static final byte ITEM_TYPE1_T = 18;
  
  public static final byte ITEM_TYPE1_GLOVE = 20;
  
  public static final byte ITEM_TYPE1_BOOTS = 21;
  
  public static final byte ITEM_TYPE1_HELM = 22;
  
  public static final byte ITEM_TYPE1_RING = 23;
  
  public static final byte ITEM_TYPE1_NECKLACE = 24;
  
  public static final byte ITEM_TYPE1_SHIELD = 25;
  
  public static final byte ITEM_TYPE1_BELT = 37;
  
  public static final byte ITEM_TYPE1_EARRING = 40;
  
  public static final byte ITEM_TYPE1_PET = 0;
  
  public static final int ATTACK_TYPE_WEAPON = 1;
  
  public static final int ATTACK_TYPE_BOW = 2;
  
  public static final int ATTACK_TYPE_MAGIC = 3;
  
  public static final int TYPE_SPEEDHACK_ATTACK = 0;
  
  public static final int TYPE_SPEEDHACK_MOVING = 1;
  
  public static final int TYPE_SPEEDHACK_MAGIC = 2;
  
  public static final int MAX_TAX = 50;
  
  public static final int MAX_FOOD = 29;
  
  public static final int MIN_FOOD = 1;
  
  public static final int MAX_AC = 138;
  
  public static final int LAW_LAWFUL = 98303;
  
  public static final int LAW_CHAOTIC = 32768;
  
  public static final int LAW_NEUTRAL = 65536;
  
  public static final int KINGDOM_WAR_DAY = 4;
  
  public static final int WARTIME = 7200000;
  
  public static final int ATTACK_INVENTORYWEIGHT = 24;
  
  public static final int AI_MAINTENANCE_TIME = 60;
  
  public static final int ELF_COLLECT_TIME = 600000;
  
  public static final boolean GMCOMMAND = true;
  
  public static boolean AUTO_PICKUP = true;
  
  public static boolean EVENT_POLYSCROLL = false;
  
  public static boolean QUEST_GRADUATION = false;
  
  public static int QUEST_WIZARD_MANA_LEVEL = 15;
  
  public static final int[] Tmap = new int[] { 
      0, 1, 3, 4, 5, 6, 7, 8, 9, 10, 
      11, 12, 13, 14, 15, 16, 17, 19, 20, 23, 
      25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 
      35, 36, 38, 39, 40, 41, 42, 
      43, 44, 45, 
      46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 
      56, 57, 58, 59, 60, 61, 62, 63, 64, 68, 
      69, 75, 76, 77, 78, 79, 83, 84, 85, 86, 
      209, 210, 211, 212, 213, 214, 215, 
      216, 300, 301, 
      304, 400, 401, 420, 440, 445, 480, 522, 523, 524 };
  
  public static final int[][] AGITLOCATION = new int[][] { 
      { 33381, 33387, 32653, 32656 }, { 33476, 33479, 32665, 32671 }, { 33425, 33437, 32865, 32872 }, { 33362, 33365, 32681, 32687 }, { 33448, 33451, 32729, 32735 }, { 33457, 33463, 32832, 32835 }, { 33414, 33417, 32850, 32856 }, { 33354, 33357, 32774, 32781 }, { 33351, 33353, 32774, 32776 }, { 33397, 33404, 32788, 32791 }, 
      { 33402, 33404, 32792, 32794 }, { 33479, 33486, 32788, 32791 }, { 33484, 33486, 32792, 32794 }, { 33431, 33438, 32838, 32841 }, { 33436, 33438, 32842, 32844 }, { 33363, 33375, 32755, 32762 }, { 33373, 33385, 32822, 32829 } };
  
  public static final int[] RTmap = new int[] { 70, 200, 303, 666 };
  
  public static final int[] Rmap = new int[] { 70, 200, 303, 666 };
  
  public static boolean SHOW_NPC_ID = false;
  
  public static int ALT_ITEM_DELETION_TIME = 30;
  
  public static int CHECK_SPEED_TYPE;
  
  public static int CHECK_STRICTNESS;
  
  public static int PUNISHMENT;
  
  public static boolean SHOW_OW_HPBAR;
  
  public static boolean BROADCAST_HP_TO_AROUND = false;
  
  public static boolean OPEN_RECHARGE;
  
  public static long NEW_TALENT_TIME;
  
  public static int RESTART_TIME;
  
  public static int PARTY_EXP_TYPE = 1;
  
  public static double RATE_PARTY_EXP = 0.2D;
  
  public static boolean PINK_NAME;
  
  public static boolean BOSS;
  
  public static boolean PARTY_MESSAGE;
  
  public static int REDUCE_INJURY_AC;
  
  public static boolean SLIME;
  
  public static int SLIME_TIME;
  
  public static double RateWeightLimit;
  
  public static boolean TEST;
  
  public static boolean TEST_HIT;
  
  public static boolean TEST_REDUCE_INJURY_AC;
  
  public static int MAX_LINK_AMOUNT;
  
  public static boolean LOGIN_VERIFICATION;
  
  public static int LOGIN_TIMER;
  
  public static int LOGIN_LV;
  
  public static int LOGIN_NO;
  
  public static boolean HTML_ONLINE;
  
  public static String HTML_ONLINE_URL;
  
  public static long HTML_ONLINE_TIME;
  
  public static boolean HTML_ONLINE_PC;
  
  public static String HTML_ONLINE_URL_PC;
  
  public static long HTML_ONLINE_TIME_PC;
  
  public static int REBIRTH_TYPE;
  
  public static int DROP_RANGE;
  
  private static void loadConfig() throws Exception {
    Properties pp = new Properties();
    try {
      InputStream is = new FileInputStream(new File("./config/config.properties"));
      pp.load(is);
      is.close();
      DRIVER = pp.getProperty("Driver", "com.mysql.jdbc.Driver");
      URL = pp.getProperty("URL", "jdbc:mysql://127.0.0.1/psjump182?useUnicode=true&characterEncoding=utf8");
      USER = pp.getProperty("Login", "root");
      PASS = pp.getProperty("Password", "7777");
      RATE_EXP = Integer.valueOf(pp.getProperty("RateXp", "2")).intValue();
      RATE_ADEN = Integer.valueOf(pp.getProperty("RateDropAdena", "2")).intValue();
      RATE_EN = Integer.valueOf(pp.getProperty("EnchantChance", "2")).intValue();
      RATE_DROP = Integer.valueOf(pp.getProperty("RateDropItems", "2")).intValue();
      AUTO_ACCOUNT = Boolean.valueOf(pp.getProperty("AutoCreateAccounts", "True")).booleanValue();
      SERVER_PORT = Integer.valueOf(pp.getProperty("GameserverPort", "2000")).intValue();
      DEBUG = Boolean.valueOf(pp.getProperty("DebugMode", "False")).booleanValue();
      DEBUG_SPEED_PRINT = Boolean.valueOf(pp.getProperty("DebugSpeedPrint", "False")).booleanValue();
      CLIENT_LANGUAGE = Integer.valueOf(pp.getProperty("ClientLanguage", "3")).intValue();
      CLIENT_LANGUAGE_CODE = LANGUAGE_CODE_ARRAY[CLIENT_LANGUAGE];
      int AutoLoot = Integer.valueOf(pp.getProperty("AutoLoot", "1")).intValue();
      AUTO_PICKUP = !(AutoLoot == 0);
      QUEST_WIZARD_MANA_LEVEL = Integer.valueOf(pp.getProperty("QuestWizardManaLevel", "15")).intValue();
      EVENT_POLYSCROLL = Boolean.valueOf(pp.getProperty("EventPolyScroll", "False")).booleanValue();
      ALT_ITEM_DELETION_TIME = Integer.valueOf(pp.getProperty("ALT_ITEM_DELETION_TIME", "30")).intValue();
      SPEED_MESSAGE = Boolean.valueOf(pp.getProperty("SPEED_MESSAGE", "False")).booleanValue();
      ATTACK_COUNT = Integer.valueOf(pp.getProperty("ATTACK_COUNT", "6")).intValue();
      MOVE_COUNT = Integer.valueOf(pp.getProperty("MOVE_COUNT", "8")).intValue();
      MAGIC_COUNT = Integer.valueOf(pp.getProperty("MAGIC_COUNT", "4")).intValue();
      SPEED_HACK = Integer.valueOf(pp.getProperty("SPEED_HACK", "3")).intValue();
      SPEED_1 = Double.valueOf(pp.getProperty("SPEED_1", "1.3")).doubleValue();
      SPEED_2 = Double.valueOf(pp.getProperty("SPEED_1", "1.6")).doubleValue();
      SPEED_1_PUN = Boolean.valueOf(pp.getProperty("SPEED_1_PUN", "False")).booleanValue();
      SPEED_2_PUN = Boolean.valueOf(pp.getProperty("SPEED_2_PUN", "False")).booleanValue();
      PET_TO_DEAT_TIME = Integer.valueOf(pp.getProperty("PET_TO_DEAT_TIME", "600")).intValue();
      LEVEL_MAX = Integer.valueOf(pp.getProperty("LEVEL_MAX", "52")).intValue();
      RoyalMaxHP = Integer.valueOf(pp.getProperty("RoyalMaxHP", "1400")).intValue();
      RoyalMaxMP = Integer.valueOf(pp.getProperty("RoyalMaxMP", "800")).intValue();
      KnightMaxHP = Integer.valueOf(pp.getProperty("KnightMaxHP", "2000")).intValue();
      KnightMaxMP = Integer.valueOf(pp.getProperty("KnightMaxMP", "600")).intValue();
      ElfMaxHP = Integer.valueOf(pp.getProperty("ElfMaxHP", "1400")).intValue();
      ElfMaxMP = Integer.valueOf(pp.getProperty("ElfMaxMP", "900")).intValue();
      WizardMaxHP = Integer.valueOf(pp.getProperty("WizardMaxHP", "1000")).intValue();
      WizardMaxMP = Integer.valueOf(pp.getProperty("WizardMaxMP", "= 1200")).intValue();
      SHOW_NPC_ID = Boolean.valueOf(pp.getProperty("SHOW_NPC_ID", "False")).booleanValue();
      CHECK_SPEED_TYPE = Integer.valueOf(pp.getProperty("checkSpeedType", "1")).intValue();
      CHECK_STRICTNESS = Integer.valueOf(pp.getProperty("CheckStrictness", "115")).intValue();
      PUNISHMENT = Integer.valueOf(pp.getProperty("Punishment", "0")).intValue();
      SHOW_OW_HPBAR = Boolean.valueOf(pp.getProperty("showOwHpBar", "False")).booleanValue();
      BROADCAST_HP_TO_AROUND = Boolean.valueOf(pp.getProperty("broadcastHpToAround", "False")).booleanValue();
      OPEN_RECHARGE = Boolean.valueOf(pp.getProperty("openRecharge", "False")).booleanValue();
      NEW_TALENT_TIME = Long.valueOf(pp.getProperty("newTalentTime", "5")).longValue();
      RESTART_TIME = Integer.valueOf(pp.getProperty("restartTime", "5")).intValue();
      PARTY_EXP_TYPE = Integer.valueOf(pp.getProperty("partyExpType", "1")).intValue();
      RATE_PARTY_EXP = Double.valueOf(pp.getProperty("ratePartyExp", "0.20")).doubleValue();
      PINK_NAME = Boolean.valueOf(pp.getProperty("pinkName", "False")).booleanValue();
      BOSS = Boolean.valueOf(pp.getProperty("boss", "True")).booleanValue();
      PARTY_MESSAGE = Boolean.valueOf(pp.getProperty("partyMessage", "False")).booleanValue();
      REDUCE_INJURY_AC = Integer.valueOf(pp.getProperty("reduceInjuryAc", "1")).intValue();
      SLIME = Boolean.valueOf(pp.getProperty("Slime", "False")).booleanValue();
      SLIME_TIME = Integer.valueOf(pp.getProperty("SlimeTime", "2")).intValue();
      RateWeightLimit = Double.valueOf(pp.getProperty("RateWeightLimit", "1")).doubleValue();
      loadTest();
      loadLogin();
      html();
      monster();
      cha();
    } catch (Exception e) {
      System.out.println("加載配置時出錯誤");
      e.printStackTrace();
      throw e;
    } 
    System.out.println("BOSS開機是否出現：" + (BOSS ? "出現" : "不出現"));
    System.out.println("配置信息加載完成>>>");
  }
  
  private static void loadTest() throws Exception {
    Properties pp = new Properties();
    try {
      InputStream is = new FileInputStream(new File("./config/test.properties"));
      pp.load(is);
      is.close();
      TEST = Boolean.valueOf(pp.getProperty("test", "False")).booleanValue();
      TEST_HIT = Boolean.valueOf(pp.getProperty("testHit", "False")).booleanValue();
      TEST_REDUCE_INJURY_AC = Boolean.valueOf(pp.getProperty("testReduceInjuryAc", "False")).booleanValue();
    } catch (Exception e) {
      System.out.println("加載‘測試配置’時出錯誤");
      e.printStackTrace();
      throw e;
    } 
  }
  
  private static void loadLogin() throws Exception {
    Properties pp = new Properties();
    try {
      InputStream is = new FileInputStream(new File("./config/login.properties"));
      pp.load(is);
      is.close();
      MAX_LINK_AMOUNT = Integer.valueOf(pp.getProperty("maxAmount", "100")).intValue();
      LOGIN_VERIFICATION = Boolean.valueOf(pp.getProperty("loginVerification", "False")).booleanValue();
      LOGIN_TIMER = Integer.valueOf(pp.getProperty("loginTimer", "3")).intValue();
      LOGIN_LV = Integer.valueOf(pp.getProperty("lv", "1")).intValue();
      LOGIN_NO = Integer.valueOf(pp.getProperty("no", "1")).intValue();
    } catch (Exception e) {
      System.out.println("加載‘登陸器驗證配置’時出錯誤");
      e.printStackTrace();
      throw e;
    } 
  }
  
  private static void html() throws Exception {
    Properties pp = new Properties();
    try {
      InputStream is = new FileInputStream(new File("./config/html.properties"));
      pp.load(is);
      is.close();
      HTML_ONLINE = Boolean.valueOf(pp.getProperty("isOnline", "false")).booleanValue();
      HTML_ONLINE_URL = String.valueOf(pp.getProperty("onlineUrl", "./"));
      HTML_ONLINE_TIME = Long.valueOf(pp.getProperty("onlineTime", "2")).longValue();
      HTML_ONLINE_PC = Boolean.valueOf(pp.getProperty("isOnlinePc", "false")).booleanValue();
      HTML_ONLINE_URL_PC = String.valueOf(pp.getProperty("onlineUrlPc", "./"));
      HTML_ONLINE_TIME_PC = Long.valueOf(pp.getProperty("onlineTimePc", "2")).longValue();
    } catch (Exception e) {
      System.out.println("加載‘HTML配置’時出錯誤");
      e.printStackTrace();
      throw e;
    } 
  }
  
  private static void monster() throws Exception {
    Properties pp = new Properties();
    try {
      InputStream is = new FileInputStream(new File("./config/monster.properties"));
      pp.load(is);
      is.close();
      REBIRTH_TYPE = Integer.valueOf(pp.getProperty("rebirthType", "0")).intValue();
    } catch (Exception e) {
      System.out.println("加載‘monster配置’時出錯誤");
      e.printStackTrace();
      throw e;
    } 
  }
  
  private static void cha() throws Exception {
    Properties pp = new Properties();
    try {
      InputStream is = new FileInputStream(new File("./config/char.properties"));
      pp.load(is);
      is.close();
      DROP_RANGE = Integer.valueOf(pp.getProperty("dropRange", "4")).intValue();
    } catch (Exception e) {
      System.out.println("加載‘monster配置’時出錯誤");
      e.printStackTrace();
      throw e;
    } 
  }
  
  public Config() throws Exception {
    loadConfig();
    beginnerItems = new ArrayList<ItemInstance>();
    read();
  }
  
  public static void reload() throws Exception {
    loadConfig();
  }
  
  public static void initBeginnerItems() {
    beginnerItems.add(ItemsTable.getInstance().newItem(351, false, false));
    beginnerItems.add(ItemsTable.getInstance().newItem(352, false, false));
    beginnerItems.add(ItemsTable.getInstance().newItem(353, false, false));
    beginnerItems.add(ItemsTable.getInstance().newItem(327, false, false));
    ItemInstance arrow = ItemsTable.getInstance().newItem(44, false, false);
    arrow.setCount(500L);
    beginnerItems.add(arrow);
  }
  
  private static void read() throws Exception {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM server WHERE id='1'");
      rs = st.executeQuery();
      if (rs.next()) {
        WORLDTIME = rs.getInt(2);
        ObjectID = rs.getInt(3);
        ObjectID_ETC = rs.getInt(4);
        ClanID = rs.getInt(5);
      } 
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  public static void save() {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE server SET gametime='");
    sb.append(WORLDTIME);
    sb.append("', objectID='");
    sb.append(ObjectID);
    sb.append("', etcID='");
    sb.append(ObjectID_ETC);
    sb.append("', clanID='");
    sb.append(ClanID);
    if (!shutdown) {
      sb.append("', player_count='");
      sb.append(WorldInstance.getInstance().getPcSize());
      sb.append("', status='1");
    } else {
      sb.append("', status='0");
    } 
    sb.append("' WHERE id='1'");
    DatabaseConnection.getInstance().query_update(sb.toString());
  }
  
  public static int getObjectID() {
    return ObjectID++;
  }
  
  public static int getObjectID_ETC() {
    return ObjectID_ETC++;
  }
  
  public static int getClanID() {
    return ClanID++;
  }
  
  public static void ServerStatus(boolean oo) {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE server SET status='");
    sb.append(oo ? 1 : 0);
    sb.append("' WHERE id='1'");
    DatabaseConnection.getInstance().query_update(sb.toString());
  }
  
  public static void ServerPlayer() {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE server SET player_count='");
    sb.append(WorldInstance.getInstance().getPcSize());
    sb.append("' WHERE id='1'");
    DatabaseConnection.getInstance().query_update(sb.toString());
  }
}
