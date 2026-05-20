package net;

import java.util.HashMap;
import java.util.Map;

import net.network.client.C_Ask;
import net.network.client.C_Attack;
import net.network.client.C_AttackBow;
import net.network.client.C_BasePacket;
import net.network.client.C_BoardDelete;
import net.network.client.C_BoardList;
import net.network.client.C_BoardNext;
import net.network.client.C_BoardRead;
import net.network.client.C_BoardWrite;
import net.network.client.C_BookAdd;
import net.network.client.C_BookRemove;
import net.network.client.C_ChangAccount;
import net.network.client.C_ChangeHead;
import net.network.client.C_CharacterCreate;
import net.network.client.C_CharacterDelete;
import net.network.client.C_CharacterTitle;
import net.network.client.C_ChatGlobal;
import net.network.client.C_Chatting;
import net.network.client.C_ChattingWhisper;
import net.network.client.C_CheckPK;
import net.network.client.C_ClanCreate;
import net.network.client.C_ClanInfo;
import net.network.client.C_ClanJoin;
import net.network.client.C_ClanKin;
import net.network.client.C_ClanMarkDownload;
import net.network.client.C_ClanMarkUpload;
import net.network.client.C_ClanOut;
import net.network.client.C_ClanWar;
import net.network.client.C_ClientOption;
import net.network.client.C_ClientPing;
import net.network.client.C_DeleteInventoryItem;
import net.network.client.C_DoorClick;
import net.network.client.C_FriendAdd;
import net.network.client.C_FriendDel;
import net.network.client.C_FriendList;
import net.network.client.C_GiveItem;
import net.network.client.C_ItemClick;
import net.network.client.C_ItemDrop;
import net.network.client.C_ItemPickup;
import net.network.client.C_ItemTrade;
import net.network.client.C_ItemTradeAdd;
import net.network.client.C_ItemTradeCancel;
import net.network.client.C_ItemTradeOk;
import net.network.client.C_KingdomTaxGet;
import net.network.client.C_KingdomTaxPut;
import net.network.client.C_KingdomTaxSetting;
import net.network.client.C_KingdomWarTimeSetting;
import net.network.client.C_KingdomWarTimeSettingFinal;
import net.network.client.C_LineageWorldJoin;
import net.network.client.C_Logins;
import net.network.client.C_LoginsOut;
import net.network.client.C_Magic;
import net.network.client.C_Moving;
import net.network.client.C_NpcTalk;
import net.network.client.C_NpcTalkAction;
import net.network.client.C_ObjectPotalOk;
import net.network.client.C_ObjectPotalPointer;
import net.network.client.C_ObjectSelect;
import net.network.client.C_Party;
import net.network.client.C_PartyBan;
import net.network.client.C_PartyList;
import net.network.client.C_PartyOut;
import net.network.client.C_QuitGame;
import net.network.client.C_RequestCharselete;
import net.network.client.C_Shop;
import net.network.client.C_SkillBuy;
import net.network.client.C_SkillBuyOk;
import net.network.client.C_Smith;
import net.network.client.C_SmithFinal;
import net.network.client.C_StatDice;
import net.network.client.C_ToRestart;
import net.network.client.C_Who;
import net.network.client.LoginVerification;
import net.network.client.UI_C_ClientVersion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Opcodes {
  private static final Logger log = LoggerFactory.getLogger(Opcodes.class);
  
  public static final int[] test_op_list = new int[] { 
      1, 8, 9, 25, 37, 56, 59, 64, 67, 68, 
      80, 84, 46, 58, 65, 90, 91, 92, 93, 97, 
      99, 100, 103, 105, 110, 112, 114, 117, 118, 120, 
      124, 125, 126, 127, -128, -127, -126, -125, -124, -123, 
      -122, -121, -120, -119, -118, -117, -116, -115, -114, -113, 
      -112, -111, -110, -109, -108, -107, -106, -105, -104, -103, 
      -102, -101, -100, -99, -98, -97 };
  
  public static final Map<Integer, C_BasePacket> C_LIST = new HashMap<Integer, C_BasePacket>();
  
  public static final int C_OPCODE_THIRD = 0;
  
  public static final int C_OPCODE_LOGINPACKET = 1;
  
  public static final int C_OPCODE_RETURNTOLOGIN = 2;
  
  public static final int C_OPCODE_NEWCHAR = 4;
  
  public static final int C_OPCODE_FRIENDDEL = 111;
  
  public static final int C_OPCODE_LOGINTOSERVER = 5;
  
  public static final int C_OPCODE_CHANGEACCOUNT = 6;
  
  public static final int C_OPCODE_DELETECHAR = 7;
  
  public static final int C_OPCODE_FISHING_FINAL = 7;
  
  public static final int C_OPCODE_DUNGEON = 8;
  
  public static final int C_OPCODE_REQUESTHEADING = 9;
  
  public static final int C_OPCODE_REQUESTMOVECHAR = 10;
  
  public static final int C_OPCODE_ITEMPICKUP = 11;
  
  public static final int C_OPCODE_ITEMDROP = 12;
  
  public static final int C_OPCODE_CLIENTOPTION = 13;
  
  public static final int C_OPCODE_DOORS = 14;
  
  public static final int C_OPCODE_QUITGAME = 15;
  
  public static final int C_OPCODE_REQUESTCHARSELETE = 16;
  
  public static final int C_OPCODE_GIVEITEM = 17;
  
  public static final int C_OPCODE_REQUESTCHAT = 19;
  
  public static final int C_OPCODE_USERSHOP = 19;
  
  public static final int C_OPCODE_USESKILL = 20;
  
  public static final int C_OPCODE_CLANOUT = 22;
  
  public static final int C_OPCODE_REQUESTATTACK = 23;
  
  public static final int C_OPCODE_ATTACKBOW = 24;
  
  public static final int C_OPCODE_REQUESTWHO = 26;
  
  public static final int C_OPCODE_REQUESTCHATWHISPER = 27;
  
  public static final int C_OPCODE_USEITEM = 28;
  
  public static final int C_OPCODE_PAPERSYSTEM = 28;
  
  public static final int C_OPCODE_AUTOSAVE = 29;
  
  public static final int C_OPCODE_NONE_1 = 31;
  
  public static final int C_OPCODE_FRIENDADD = 110;
  
  public static final int C_OPCODE_PING = 32;
  
  public static final int C_OPCODE_CLANMAKE = 33;
  
  public static final int C_OPCODE_RequestPledge = 34;
  
  public static final int C_OPCODE_BASERESET = 34;
  
  public static final int C_OPCODE_ASK = 35;
  
  public static final int C_OPCODE_BLOCK_NAME = 37;
  
  public static final int C_OPCODE_NPCACTION = 39;
  
  public static final int C_OPCODE_SHOP = 40;
  
  public static final int C_OPCODE_PETITEM = 40;
  
  public static final int C_OPCODE_NPCTALK = 41;
  
  public static final int C_OPCODE_CRAFTFINAL = 42;
  
  public static final int C_OPCODE_TITLE = 43;
  
  public static final int C_OPCODE_BOOKMARK = 44;
  
  public static final int C_OPCODE_BOOKMARKDELETE = 45;
  
  public static final int C_OPCODE_UploadMark = 47;
  
  public static final int C_OPCODE_RequestMark = 48;
  
  public static final int C_OPCODE_PetStatus = 48;
  
  public static final int C_OPCODES_ClanWar = 49;
  
  public static final int C_OPCODE_REQUESTRESTART = 49;
  
  public static final int C_OPCODE_CLANKIN = 50;
  
  public static final int C_OPCODE_TRADE = 52;
  
  public static final int C_OPCODE_TREADCANCEL = 54;
  
  public static final int C_OPCODE_TREADOK = 55;
  
  public static final int C_OPCODE_TREADADDITEM = 56;
  
  public static final int C_OPCODE_ClientStatus = 60;
  
  public static final int C_OPCODES_TAXSETTING = 65;
  
  public static final int C_OPCODES_TaxTotalPut = 66;
  
  public static final int C_OPCODE_STATDICE = 67;
  
  public static final int C_OPCODE_SMITH_FINAL = 68;
  
  public static final int C_OPCODE_SMITH = 69;
  
  public static final int C_OPCODE_TIMELEFTOK = 70;
  
  public static final int C_OPCODES_TaxTotalAdd = 71;
  
  public static final int C_OPCODE_PARTYKIN = 71;
  
  public static final int C_OPCODE_CLANINFO = 72;
  
  public static final int C_OPCODE_SKILLBUY = 73;
  
  public static final int C_OPCODE_SKILLBUYOK = 74;
  
  public static final int C_OPCODES_CallWarTimeSetting = 76;
  
  public static final int C_OPCODE_ACITON = 76;
  
  public static final int C_OPCODES_SetWarTimeSetting = 77;
  
  public static final int C_OPCODE_POTALOK = 78;
  
  public static final int C_OPCODE_TargetSelect = 79;
  
  public static final int C_OPCODE_BOARDWRITE = 84;
  
  public static final int C_OPCODE_BOARDNEXT = 85;
  
  public static final int C_OPCODE_PKCOUNT = 85;
  
  public static final int C_OPCODE_BOARDREAD = 86;
  
  public static final int C_OPCODE_USERSHOPLIST = 86;
  
  public static final int C_OPCODE_BOARDDELETE = 87;
  
  public static final int C_OPCODE_PARTY = 93;
  
  public static final int C_OPCODE_PARTY_BAN = 94;
  
  public static final int C_OPCODE_PARTYOUT = 95;
  
  public static final int C_OPCODE_PARTYLIST = 96;
  
  public static final int C_OPCODE_BOARDCLICK = 105;
  
  public static final int C_OPCODE_CHECKPK = 108;
  
  public static final int C_OPCODE_NEWCHAR_STAT = 112;
  
  public static final int C_OPCODE_POTAL_POINTER = 115;
  
  public static final int C_OPCODE_DELETEINVENTORYITEM = 118;
  
  public static final int C_OPCODE_REQUESTCHATGLOBAL = 119;
  
  public static final int C_OPCODE_RESTART = 120;
  
  public static final int C_OPCODE_NOTICEOK = 122;
  
  public static final int C_OPCODE_FRIENDLIST = 109;
  
  public static final int C_LOGIN_VERIFICATION = 200;
  
  public static final int S_OPCODE_ITEMGFXCHANGE = 0;
  
  public static final int S_OPCODE_UpdateInvenItemRemainTime = 0;
  
  public static final int S_OPCODE_OPCODE_SERVERRESTART = 0;
  
  public static final int S_OPCODE_SERVERVERSION = 0;
  
  public static final int S_OPCODE_LOGINFAILS = 2;
  
  public static final int S_OPCODE_CHARAMOUNT = 3;
  
  public static final int S_OPCODE_CHARINFO = 4;
  
  public static final int S_OPCODE_NEWCHARPACK = 5;
  
  public static final int S_OPCODE_DETELECHAROK = 6;
  
  public static final int S_OPCODE_UNKNOWN1 = 7;
  
  public static final int S_OPCODE_BlindPotion = 10;
  
  public static final int S_OPCODE_CHARPACK = 11;
  
  public static final int S_OPCODE_OWNCHARSTATUS = 12;
  
  public static final int S_OPCODE_HPUPDATE = 13;
  
  public static final int S_OPCODE_ItemBressChange = 14;
  
  public static final int S_OPCODE_GLOBALCHAT = 15;
  
  public static final int S_OPCODE_SERVERMSG = 16;
  
  public static final int S_OPCODE_RESTORE = 17;
  
  public static final int S_OPCODE_MOVEOBJECT = 18;
  
  public static final int S_OPCODE_ITENMAPSHOW = 18;
  
  public static final int S_OPCODE_NORMALCHAT = 19;
  
  public static final int S_OPCODE_WHISPERCHAT = 20;
  
  public static final int S_OPCODE_DELETEOBJECT = 21;
  
  public static final int S_OPCODE_ITEMADD = 22;
  
  public static final int S_OPCODE_ITEMDELETE = 23;
  
  public static final int S_OPCODE_ITEMEQUIP = 24;
  
  public static final int S_OPCODE_BLUEMESSAGE2 = 25;
  
  public static final int S_OPCODE_ITEMCOUNT = 26;
  
  public static final int S_OPCODE_OBJLIGHT = 27;
  
  public static final int S_OPCODE_CHANGEHEADING = 28;
  
  public static final int S_OPCODE_OBJECTMODE = 29;
  
  public static final int S_OPCODE_SKILLINV = 30;
  
  public static final int S_OPCODE_DeleteSkill = 31;
  
  public static final int S_OPCODE_DOACTION = 32;
  
  public static final int S_OPCODE_GAMETIME = 33;
  
  public static final int S_OPOCDES_ATTRIBUTE = 34;
  
  public static final int S_OPCODE_AttackPacket = 35;
  
  public static final int S_OPCODE_YES_NO = 36;
  
  public static final int S_OPCODE_SetClientLock = 37;
  
  public static final int S_OPCODE_ABILITY = 38;
  
  public static final int S_OPCODE_POLY = 39;
  
  public static final int S_OPCODE_MAPID = 40;
  
  public static final int S_OPCODE_SKILLHASTE = 41;
  
  public static final int S_OPCODE_SHOWHTML = 42;
  
  public static final int S_OPCODE_SHOPBUY = 43;
  
  public static final int S_OPCODE_TRUETARGET = 43;
  
  public static final int S_OPCODE_SHOPSELL = 44;
  
  public static final int S_OPCODE_SHOTSAY = 45;
  
  public static final int S_OPCODE_LOCK2 = 45;
  
  public static final int S_OPCODE_TITLECHANGE = 47;
  
  public static final int S_OPCODE_BOOKMARKS = 48;
  
  public static final int S_OPCODE_WAREHOUSE = 49;
  
  public static final int S_OPCODE_CRIMINAL = 49;
  
  public static final int S_OPCODE_SendPoisonAndLock = 50;
  
  public static final int S_OPCODE_NOTICE = 50;
  
  public static final int S_OPCODE_SERVERSTAT = 51;
  
  public static final int S_OPCODE_OBJECTINVISA = 52;
  
  public static final int S_OPCODE_PAPERSYSTEM = 52;
  
  public static final int S_OPCODE_DownLoadMark = 53;
  
  public static final int S_OPCODE_BASERESET = 53;
  
  public static final int S_OPCODE_CLANWAR = 54;
  
  public static final int S_OPCODE_EFFECT = 55;
  
  public static final int S_OPCODE_BLUEMESSAGE1 = 56;
  
  public static final int S_OPCODE_MagicAttackPacket = 57;
  
  public static final int S_OPCODE_LOCK = 57;
  
  public static final int S_OPCODE_TRADE = 60;
  
  public static final int S_OPCODE_TRADEADDITEM = 61;
  
  public static final int S_OPCODE_TRADESTATUS = 62;
  
  public static final int S_OPCODE_ITEMIDENTIFY = 63;
  
  public static final int S_OPCODE_ITEMLIST = 65;
  
  public static final int S_OPCODE_KINGDOMGUARDSPAWN = 66;
  
  public static final int S_OPCODE_CASTLETAXRATIO = 69;
  
  public static final int S_OPCODE_CASTLETAXOUT = 70;
  
  public static final int S_OPCODE_CASTLEMASTER = 71;
  
  public static final int S_OPCODE_STATDICE = 72;
  
  public static final int S_OPCODE_SMITH = 73;
  
  public static final int S_OPCODE_SOUNDEFFECT = 74;
  
  public static final int S_OPCODE_TIMELEFT = 75;
  
  public static final int S_OPCODE_CASTLETAXIN = 76;
  
  public static final int S_OPCODE_MPUPDATE = 77;
  
  public static final int S_OPCODE_SKILLBUY = 78;
  
  public static final int S_OPCODE_SUMMON_OWN_CHANGE = 79;
  
  public static final int S_OPCODE_EXP = 81;
  
  public static final int S_OPCODE_EFFECTLOC = 83;
  
  public static final int S_OPCODE_CASTLEWARTIME = 84;
  
  public static final int S_OPCODE_POTAL = 85;
  
  public static final int S_OPCODE_ChangeSpMr = 86;
  
  public static final int S_OPCODE_KINGDOMGUARDPET = 86;
  
  public static final int S_OPCODE_PromptTargetSelect = 87;
  
  public static final int S_OPCODE_HYPERTEXTINPUT = 87;
  
  public static final int S_OPCODE_SetObjectName = 88;
  
  public static final int S_OPCODE_DISPOSITION = 89;
  
  public static final int S_OPCODE_NEWCHAROK = 89;
  
  public static final int S_OPCODE_INPUT_AMOUNT = 91;
  
  public static final int S_OPCODE_LETTERREAD = 94;
  
  public static final int S_OPCODE_BOARDLIST = 95;
  
  public static final int S_OPCODE_OBJECTSTATUS = 95;
  
  public static final int S_OPCODE_BOARDREAD = 96;
  
  public static final int S_OPCODE_STATUS_AC = 97;
  
  public static final int S_OPCODE_SKILLBRAVE = 98;
  
  public static final int S_OPCODE_DISCONNECT = 102;
  
  public static final int S_OPCODE_HITRATIO = 104;
  
  public static final int S_OPCODE_PINKNAME = 106;
  
  public static final int S_OPCODE_MAGICSTR = 107;
  
  public static final int S_OPCODE_MAGICDEX = 108;
  
  public static final int S_OPCODE_SHIELD = 109;
  
  public static final int S_OPCODE_USEMAP = 110;
  
  public static final int S_OPCODE_ITEMSTATUS = 111;
  
  public static final int S_OPCODE_Agit_List = 115;
  
  public static final int S_OPCODE_Agit_Map = 116;
  
  public static final int S_OPCODE_AQUABREATH = 119;
  
  public static final int S_OPCODE_RetreivePravateShop = 122;
  
  public static final int S_OPCODE_PACKETBOX = 123;
  
  public static final int UI_C_OPCODE_CLIENTVERSION = 222;
  
  public static final int UI_S_OPCODE_GLOBALCHAT = 221;
  
  public static final int UI_S_OPCODE_CLIENTVERSION = 222;
  
  public static int test = 0;
  
  static {
    put(Integer.valueOf(222), (C_BasePacket)new UI_C_ClientVersion());
    put(Integer.valueOf(1), (C_BasePacket)new C_Logins());
    put(Integer.valueOf(2), (C_BasePacket)new C_LoginsOut());
    put(Integer.valueOf(4), null);
    put(Integer.valueOf(5), (C_BasePacket)new C_LineageWorldJoin());
    put(Integer.valueOf(6), (C_BasePacket)new C_ChangAccount());
    put(Integer.valueOf(7), (C_BasePacket)new C_CharacterDelete());
    put(Integer.valueOf(8), null);
    put(Integer.valueOf(9), (C_BasePacket)new C_ChangeHead());
    put(Integer.valueOf(10), (C_BasePacket)new C_Moving());
    put(Integer.valueOf(11), (C_BasePacket)new C_ItemPickup());
    put(Integer.valueOf(12), (C_BasePacket)new C_ItemDrop());
    put(Integer.valueOf(13), (C_BasePacket)new C_ClientOption());
    put(Integer.valueOf(14), (C_BasePacket)new C_DoorClick());
    put(Integer.valueOf(15), (C_BasePacket)new C_QuitGame());
    put(Integer.valueOf(16), (C_BasePacket)new C_RequestCharselete());
    put(Integer.valueOf(17), (C_BasePacket)new C_GiveItem());
    put(Integer.valueOf(19), (C_BasePacket)new C_Chatting());
    put(Integer.valueOf(20), (C_BasePacket)new C_Magic());
    put(Integer.valueOf(22), (C_BasePacket)new C_ClanOut());
    put(Integer.valueOf(23), (C_BasePacket)new C_Attack());
    put(Integer.valueOf(24), (C_BasePacket)new C_AttackBow());
    put(Integer.valueOf(26), (C_BasePacket)new C_Who());
    put(Integer.valueOf(27), (C_BasePacket)new C_ChattingWhisper());
    put(Integer.valueOf(28), (C_BasePacket)new C_ItemClick());
    put(Integer.valueOf(29), null);
    put(Integer.valueOf(31), null);
    put(Integer.valueOf(32), (C_BasePacket)new C_ClientPing());
    put(Integer.valueOf(33), (C_BasePacket)new C_ClanCreate());
    put(Integer.valueOf(34), (C_BasePacket)new C_ClanJoin());
    put(Integer.valueOf(35), (C_BasePacket)new C_Ask());
    put(Integer.valueOf(37), null);
    put(Integer.valueOf(39), (C_BasePacket)new C_NpcTalkAction());
    put(Integer.valueOf(40), (C_BasePacket)new C_Shop());
    put(Integer.valueOf(41), (C_BasePacket)new C_NpcTalk());
    put(Integer.valueOf(43), (C_BasePacket)new C_CharacterTitle());
    put(Integer.valueOf(44), (C_BasePacket)new C_BookAdd());
    put(Integer.valueOf(45), (C_BasePacket)new C_BookRemove());
    put(Integer.valueOf(47), (C_BasePacket)new C_ClanMarkUpload());
    put(Integer.valueOf(48), (C_BasePacket)new C_ClanMarkDownload());
    put(Integer.valueOf(49), (C_BasePacket)new C_ClanWar());
    put(Integer.valueOf(50), (C_BasePacket)new C_ClanKin());
    put(Integer.valueOf(52), (C_BasePacket)new C_ItemTrade());
    put(Integer.valueOf(54), (C_BasePacket)new C_ItemTradeCancel());
    put(Integer.valueOf(55), (C_BasePacket)new C_ItemTradeOk());
    put(Integer.valueOf(56), (C_BasePacket)new C_ItemTradeAdd());
    put(Integer.valueOf(65), (C_BasePacket)new C_KingdomTaxSetting());
    put(Integer.valueOf(66), (C_BasePacket)new C_KingdomTaxGet());
    put(Integer.valueOf(67), (C_BasePacket)new C_StatDice());
    put(Integer.valueOf(68), (C_BasePacket)new C_SmithFinal());
    put(Integer.valueOf(69), (C_BasePacket)new C_Smith());
    put(Integer.valueOf(71), (C_BasePacket)new C_KingdomTaxPut());
    put(Integer.valueOf(72), (C_BasePacket)new C_ClanInfo());
    put(Integer.valueOf(73), (C_BasePacket)new C_SkillBuy());
    put(Integer.valueOf(74), (C_BasePacket)new C_SkillBuyOk());
    put(Integer.valueOf(76), (C_BasePacket)new C_KingdomWarTimeSetting());
    put(Integer.valueOf(77), (C_BasePacket)new C_KingdomWarTimeSettingFinal());
    put(Integer.valueOf(78), (C_BasePacket)new C_ObjectPotalOk());
    put(Integer.valueOf(79), (C_BasePacket)new C_ObjectSelect());
    put(Integer.valueOf(84), (C_BasePacket)new C_BoardWrite());
    put(Integer.valueOf(85), (C_BasePacket)new C_BoardNext());
    put(Integer.valueOf(86), (C_BasePacket)new C_BoardRead());
    put(Integer.valueOf(87), (C_BasePacket)new C_BoardDelete());
    put(Integer.valueOf(93), (C_BasePacket)new C_Party());
    put(Integer.valueOf(95), (C_BasePacket)new C_PartyOut());
    put(Integer.valueOf(96), (C_BasePacket)new C_PartyList());
    put(Integer.valueOf(105), (C_BasePacket)new C_BoardList());
    put(Integer.valueOf(108), (C_BasePacket)new C_CheckPK());
    put(Integer.valueOf(112), (C_BasePacket)new C_CharacterCreate());
    put(Integer.valueOf(115), (C_BasePacket)new C_ObjectPotalPointer());
    put(Integer.valueOf(118), (C_BasePacket)new C_DeleteInventoryItem());
    put(Integer.valueOf(119), (C_BasePacket)new C_ChatGlobal());
    put(Integer.valueOf(94), (C_BasePacket)new C_PartyBan());
    put(Integer.valueOf(200), (C_BasePacket)new LoginVerification());
    put(Integer.valueOf(120), (C_BasePacket)new C_ToRestart());
    put(Integer.valueOf(110), (C_BasePacket)new C_FriendAdd());
    put(Integer.valueOf(111), (C_BasePacket)new C_FriendDel());
    put(Integer.valueOf(109), (C_BasePacket)new C_FriendList());
  }
  
  private static void put(Integer key, C_BasePacket value) {
    if (C_LIST.get(key) == null) {
      C_LIST.put(key, value);
    } else {
      log.error("重複的客戶端封包：" + key + " " + value.getType());
    } 
  }
}
