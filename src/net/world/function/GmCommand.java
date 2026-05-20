package net.world.function;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import net.Config;
import net.database.BanListTable;
import net.database.ChineseCommand;
import net.database.DatabaseConnection;
import net.database.ExpTable;
import net.database.ItemsTable;
import net.database.MonsterSpawnTable;
import net.database.MonsterTable;
import net.database.NpcSpawnTable;
import net.database.NpcTable;
import net.database.SkillTable;
import net.database.bean.Npc;
import net.network.server.S_BasePacket;
import net.network.server.S_CloseClient;
import net.network.server.S_ObjectAction;
import net.network.server.S_ObjectAdd;
import net.network.server.S_ObjectEffect;
import net.network.server.S_ObjectHitratio;
import net.network.server.S_ObjectMode;
import net.network.server.S_ObjectPoly;
import net.network.server.S_PinkName;
import net.network.server.S_ServerMessage;
import net.util.TimeLine;
import net.world.WorldInstance;
import net.world.ai.MonAi;
import net.world.instance.ItemInstance;
import net.world.instance.MonsterInstance;
import net.world.instance.PcInstance;
import net.world.instance.books.PcBooks;
import net.world.instance.skill.Magic;
import net.world.object.Character;
import net.world.object.L1Object;
import net.world.time.BuffTimerInstance;
import net.world.time.ShutdownTimer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GmCommand extends TimeLine {
  final Logger log = LoggerFactory.getLogger(GmCommand.class);
  
  private Map<String, CMD> list;
  
  private int efplus_idx = 0;
  
  private String commander;
  
  static final String CMD_LOG = "玩家：%s，使用指令：%s";
  
  static int[] buffList = new int[] { 2, 17, 27, 28, 36, 148, 151 };
  
  private static class Holder {
    static GmCommand instance = new GmCommand();
  }
  
  public static GmCommand getInstance() {
    return Holder.instance;
  }
  
  private enum CMD {
    gm, reload, item, shutdown, gfx, npc, loc, topc, call, move, level, test, ef, version, ban, bao, speed, buff, chatclose, worldclean, autopickup, monster, mode, action, del, search, hpbar, chattingclose, chattingopen, checkspeed, set, summon, hp, mp, callcheck, unban, skill;
  }
  
  private GmCommand() {
    this.list = new HashMap<String, CMD>();
    for (CMD c : CMD.values())
      this.list.put(c.toString(), c); 
  }
  
  public void cmd(PcInstance pc, String cmd) {
    this.log.debug(String.format("玩家：%s，使用指令：%s", new Object[] { pc.getName(), cmd }));
    StringTokenizer st = new StringTokenizer(cmd.substring(1, cmd.length()));
    if (!st.hasMoreTokens())
      return; 
    String cmdKey = st.nextToken();
    for (int i = 0; i < ChineseCommand.chinese_list.size(); i++) {
      if (((String)ChineseCommand.chinese_list.get(i)).equals(cmdKey)) {
        cmdKey = ChineseCommand.command_list.get(i);
        break;
      } 
    } 
    CMD temp = this.list.get(cmdKey);
    if (temp == null) {
      if (pc.isGm())
        pc.Message("無效的GM命令:" + cmdKey); 
      return;
    } 
    if (pc.isGm()) {
      pc.Message(this.commander);
      switch (temp.ordinal()) {
        case 0:
          gm(pc, st);
          break;
        case 1:
          reload(pc, st);
          break;
        case 2:
          item(pc, st);
          break;
        case 3:
          shutdown(pc, st);
          break;
        case 4:
          gfx(pc, st);
          break;
        case 5:
          npc(pc, st);
          break;
        case 6:
          loc(pc, st);
          break;
        case 7:
          topc(pc, st);
          break;
        case 8:
          call(pc, st);
          break;
        case 9:
          move(pc, st);
          break;
        case 10:
          level(pc, st);
          break;
        case 11:
          test(pc, st);
          break;
        case 12:
          ef(pc, st);
          break;
        case 13:
          version(pc, st);
          break;
        case 14:
          ban(pc, st);
          break;
        case 15:
          bao(pc, st);
          break;
        case 16:
          speed(pc, st);
          break;
        case 17:
          buff(pc, st);
          break;
        case 18:
          chatclose(pc, st);
          break;
        case 19:
          worldclean();
          break;
        case 20:
          autopickup(pc);
          break;
        case 21:
          monster(pc, st);
          break;
        case 22:
          mode(pc, st);
          break;
        case 23:
          action(pc, st);
          break;
        case 24:
          del(pc, cmd);
          break;
        case 25:
          search(pc, st);
          break;
        case 26:
          hpbar(pc, st);
          break;
        case 27:
          chattingclose(pc, st);
          break;
        case 28:
          chattingopen(pc, st);
          break;
        case 29:
          checkSpeed(pc, st);
          break;
        case 30:
          set(pc, st);
          break;
        case 31:
          summon(pc, st);
          break;
        case 32:
          hp(pc, st);
          break;
        case 33:
          mp(pc, st);
          break;
        case 34:
          callcheck(pc, st);
          break;
        case 35:
          unban(pc, st);
          break;
        case 36:
          skill(pc, st);
          break;
      } 
    } else {
      switch (temp.ordinal()) {
        case 13:
          version(pc, st);
          break;
        case 24:
          del(pc, cmd);
          break;
      } 
    } 
  }
  
  private void skill(PcInstance pc, StringTokenizer st) {
    int i;
    switch (pc.getClassType()) {
      case 0:
        for (i = 1; i < 11; i++)
          pc.getSkill().add(SkillTable.getInstance().getTemplate((Character)pc, i)); 
        break;
      case 1:
        for (i = 1; i < 6; i++)
          pc.getSkill().add(SkillTable.getInstance().getTemplate((Character)pc, i)); 
        break;
      case 2:
        for (i = 1; i < 31; i++)
          pc.getSkill().add(SkillTable.getInstance().getTemplate((Character)pc, i)); 
        for (i = 129; i < 132; i++)
          pc.getSkill().add(SkillTable.getInstance().getTemplate((Character)pc, i)); 
        for (i = 137; i < 139; i++)
          pc.getSkill().add(SkillTable.getInstance().getTemplate((Character)pc, i)); 
        for (i = 145; i < 169; i++)
          pc.getSkill().add(SkillTable.getInstance().getTemplate((Character)pc, i)); 
        break;
      case 3:
        for (i = 1; i < 51; i++)
          pc.getSkill().add(SkillTable.getInstance().getTemplate((Character)pc, i)); 
        break;
    } 
    pc.getSkill().sendList();
    pc.Message("全技能開啟。");
  }
  
  private void del(PcInstance pc, String cmd) {
    try {
      String location = cmd.substring(cmd.indexOf(" ")).trim();
      PcBooks pb = (PcBooks)pc.getBooks();
      pb.remove(location);
      pc.Message("[" + location + "]已被?除，小退生效。");
    } catch (Exception e) {
      pc.Message(".del 坐?名");
    } 
  }
  
  private void hpbar(PcInstance pc, StringTokenizer st) {
    boolean isOn = true;
    if (st.hasMoreTokens())
      isOn = "on".equalsIgnoreCase(st.nextToken()); 
    pc.setHpBar(isOn);
    for (L1Object obj : pc.getObjectList()) {
      if (obj instanceof Character)
        pc.SendPacket((S_BasePacket)new S_ObjectHitratio(obj, isOn)); 
    } 
  }
  
  private void buff(PcInstance pc, StringTokenizer st) {
    String name = null;
    List<PcInstance> targetList = new ArrayList<PcInstance>();
    if (st.hasMoreTokens()) {
      name = st.nextToken();
      if ("*".equals(name)) {
        for (PcInstance _pc : WorldInstance.getInstance().getPc())
          targetList.add(_pc); 
      } else {
        PcInstance target = WorldInstance.getInstance().getPc(name);
        if (target == null) {
          pc.Message("找不到玩家 " + name);
          return;
        } 
        targetList.add(target);
      } 
    } else {
      targetList.add(pc);
    } 
    Magic m = null;
    for (PcInstance target : targetList) {
      for (int i : buffList) {
        m = SkillTable.getInstance().getTemplate((Character)target, i);
        m.setGMBuff(true);
        m.toMagic(target.getObjectId());
      } 
      if (!target.isGm())
        target.Message("GM給你加狀態！"); 
    } 
    targetList = null;
  }
  
  private void speed(PcInstance pc, StringTokenizer st) {
    PcInstance target;
    if (st.countTokens() == 1) {
      String name = st.nextToken();
      target = WorldInstance.getInstance().getPc(name);
      if (target == null)
        pc.Message("找不到玩家" + name); 
    } else {
      target = pc;
    } 
    if (!target.isGm())
      target.Message("GM給你加速！"); 
  }
  
  private void bao(PcInstance pc, StringTokenizer st) {
    try {
      String name = st.nextToken();
      Connection con = null;
      PreparedStatement stt = null;
      ResultSet rs = null;
      try {
        con = DatabaseConnection.getInstance().getConnection();
        stt = con.prepareStatement("SELECT a.NAME AS aname, a.UID AS mid, c.NAME AS cname, c.ITEM_ID AS bid, b.chance AS bchance FROM  monster a, monster_item_drop b, items c WHERE a.uid = b.monid AND b.itemid = c.item_id AND (a.NAME LIKE ? or C.NAME LIKE ?)");
        if (pc.isGm()) {
          stt.setString(1, "%" + name + "%");
          stt.setString(2, "%" + name + "%");
        } else {
          stt.setString(1, name);
          stt.setString(2, name);
        } 
        rs = stt.executeQuery();
        if (pc.isGm()) {
          pc.Message("怪物(ID) - 物品(ID) - 萬分機率");
          while (rs.next())
            pc.Message(rs.getString("aname") + "(" + rs.getInt("mid") + ") - " + rs.getString("cname") + "(" + rs.getInt("BID") + ") - " + rs.getString("bchance")); 
        } else {
          pc.Message("怪物 - 物品 - 萬分機率");
          while (rs.next())
            pc.Message(rs.getString("aname") + " - " + rs.getString("cname") + " - " + rs.getString("bchance")); 
        } 
      } catch (Exception localException4) {
        this.log.error("方法 bao異常：" + localException4.getMessage());
      } finally {
        DatabaseConnection.getInstance().close(con, stt, rs);
      } 
    } catch (Exception e) {
      pc.Message(".bao 怪物名或者物品名");
    } 
    pc.Message("搜索完成.");
  }
  
  private void version(PcInstance pc, StringTokenizer st) {
    pc.Message("20131028號：修復海音地監3樓出現4樓怪，4樓雜貨商人^巴克休(32799,32896)，感謝回報者：Taco");
    pc.Message("20131029號：添加 .bao 指令，用於顯示掉寶相關信息. ");
    pc.Message("    用法如：.bao 漂浮之眼 ");
    pc.Message("    修復新手裝備重登陸後不顯示技能圖標問題.");
    pc.Message("20131030號：添加新GM指令 .buff .speed");
    pc.Message("    修復交易部分錯誤,添加遊戲跟蹤日誌");
    pc.Message("20131031號：修復遊戲時間錯誤,增強遊戲跟蹤日誌");
    pc.Message("20131104號：遷移文件到數據庫中 失敗");
    pc.Message("    修改1、寵物幫打怪物品掉落身上2、角色超過怪物4格以上，物品掉落在地面3、多人打怪，物品掉落地面。");
    pc.Message("    修改角色下線寵物不下線問題。");
    pc.Message("20131105號：修復物品重覆問題，感謝回報者：青瓜蛋");
    pc.Message("    法師出生就有光箭");
    pc.Message("20131106號：修復記憶座標編號重覆問題，感謝回報者：雨果");
    pc.Message("    修復寵物下線後經驗被清空問題，感謝回報者：奔奔");
    pc.Message("20131107號：修復隱身顯示燈光問題");
    pc.Message("20131109號：怪物死後分取經驗，未判斷全錯誤，回報者：眾玩家");
    pc.Message("20131111號：加入加速檢測");
    pc.Message("20131112號：修改法師瑪那任務為15級，狗狗死亡後消失時間為10分鐘");
    pc.Message("20131113~17號：修改加速檢測");
    pc.Message("20131118號：放開等級限制");
    pc.Message("20131118號：加速檢測不提示，修復交易錯誤");
    pc.Message("20131118號：再次調整加速檢測，補充帳號登陸登出日誌");
    pc.Message("20131122號：變形卷軸 正確使用才有數量減1");
    pc.Message("    物品使用變更有最低最高級別");
    pc.Message("20131124號：限制玩家取奇怪的名字");
    pc.Message("    添加強化綠色藥水物品");
    pc.Message("20131125號：添加.del指令方便玩家刪除無用的記憶座標，調整法師召喚");
    pc.Message("    修復地圖顯示問題，感謝 JRWZ");
    pc.Message("當前遊戲  經驗倍率:" + Config.RATE_EXP + " 金幣掉落率:" + Config.RATE_ADEN);
  }
  
  private void test(PcInstance pc, StringTokenizer st) {
    int elf1 = Integer.valueOf(st.nextToken()).intValue();
    int elf2 = Integer.valueOf(st.nextToken()).intValue();
    if (elf1 == 0) {
      pc.addSkillEffect(elf2);
    } else if (elf1 == 1) {
      pc.delSkillEffect(elf2);
    } else {
      System.out.println("技能效果：" + pc.isSkillEffect(elf2) + " 剩余??：" + pc.getSkillBuffTime(elf2));
    } 
  }
  
  private void reload(PcInstance pc, StringTokenizer st) {
    try {
      Config.reload();
      pc.Message("重載配置完成");
    } catch (Exception e) {
      String msg = e.getLocalizedMessage();
      msg = msg.substring(0, (msg.length() > 50) ? 49 : (msg.length() - 1));
      pc.Message("重新加載配置時異常！\n" + msg);
      e.printStackTrace();
    } 
  }
  
  private void gm(PcInstance pc, StringTokenizer st) {
    pc.setGm(false);
  }
  
  private void gfx(PcInstance pc, StringTokenizer st) {
    pc.setGfx(Integer.valueOf(st.nextToken()).intValue());
    pc.SendPacket((S_BasePacket)new S_ObjectPoly((L1Object)pc), true);
  }
  
  private void mode(PcInstance pc, StringTokenizer st) {
    pc.SendPacket((S_BasePacket)new S_ObjectMode((L1Object)pc, Integer.valueOf(st.nextToken()).intValue()), true);
  }
  
  private void action(PcInstance pc, StringTokenizer st) {
    pc.SendPacket((S_BasePacket)new S_ObjectAction((L1Object)pc, Integer.valueOf(st.nextToken()).intValue()), true);
  }
  
  private void npc(PcInstance pc, StringTokenizer st) {
    int id = 0;
    try {
      id = Integer.valueOf(st.nextToken()).intValue();
      Npc n = NpcTable.getInstance().getNpcTemplate(id);
      if (n != null) {
        pc.SendPacket((S_BasePacket)new S_ObjectAdd(n, pc));
        NpcSpawnTable.getInstance().insertNpcSpawn(n, pc.getX() - 1, pc.getY(), pc.getMap(), 0);
      } 
    } catch (Exception e) {
      pc.Message(".npc npcid");
    } 
  }
  
  class PinkThread implements Runnable {
    PcInstance pc;
    
    public void setPc(PcInstance pc) {
      this.pc = pc;
    }
    
    public void run() {
      for (int i = 0; i < 500; i++) {
        System.out.println("次?：" + i);
        if (this.pc.getClient().isConnected()) {
          this.pc.setPinkNameTime(10);
          this.pc.SendPacket((S_BasePacket)new S_PinkName((L1Object)this.pc), true);
        } else {
          System.out.println("??中??程：" + i);
          break;
        } 
        try {
          Thread.sleep(500L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } 
      } 
    }
  }
  
  private void loc(PcInstance pc, StringTokenizer st) {
    pc.Message("當前位置：(" + pc.getX() + ", " + pc.getY() + ", " + pc.getMap() + ")");
  }
  
  private void move(PcInstance pc, StringTokenizer st) {
    try {
      if (st.countTokens() == 3) {
        int x = Integer.valueOf(st.nextToken()).intValue();
        int y = Integer.valueOf(st.nextToken()).intValue();
        int map = Integer.valueOf(st.nextToken()).intValue();
        pc.toTeleport(x, y, map);
      } else if (st.countTokens() == 2) {
        int x = Integer.valueOf(st.nextToken()).intValue();
        int y = Integer.valueOf(st.nextToken()).intValue();
        int map = pc.getMap();
        pc.toTeleport(x, y, map);
      } else if (st.countTokens() == 1) {
        int x = pc.getX();
        int y = pc.getY();
        int map = pc.getMap();
        String str = st.nextToken();
        if ("T".equalsIgnoreCase(str)) {
          x += 10;
          y -= 10;
        } else if ("B".equalsIgnoreCase(str)) {
          x -= 10;
          y += 10;
        } else if ("L".equalsIgnoreCase(str)) {
          x -= 10;
          y -= 10;
        } else if ("R".equalsIgnoreCase(str)) {
          x += 10;
          y += 10;
        } 
        pc.toTeleport(x, y, map);
      } 
    } catch (Exception e) {
      pc.Message(".move x y mapid");
      pc.Message(".move 上T 下B 左L 右R");
    } 
  }
  
  private void topc(PcInstance pc, StringTokenizer st) {
    String name = st.nextToken();
    PcInstance pcInstance = WorldInstance.getInstance().getPc(name);
    if (pcInstance != null) {
      pc.toTeleport(pcInstance.getX(), pcInstance.getY(), pcInstance.getMap(), true);
    } else {
      pc.Message("找不到玩家." + name);
    } 
  }
  
  private void call(PcInstance pc, StringTokenizer st) {
    String name = st.nextToken();
    PcInstance pcInstance = WorldInstance.getInstance().getPc(name);
    if (pcInstance != null) {
      pcInstance.toTeleport(pc.getX(), pc.getY(), pc.getMap());
    } else {
      pc.Message("找不到玩家." + name);
    } 
  }
  
  private void ef(PcInstance pc, StringTokenizer st) {
    try {
      if (st.hasMoreTokens()) {
        pc.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)pc, Integer.valueOf(st.nextToken()).intValue()), true);
      } else {
        pc.SendPacket((S_BasePacket)new S_ObjectEffect((L1Object)pc, this.efplus_idx++), true);
      } 
    } catch (NumberFormatException e) {
      pc.Message(".ef 索引值");
      e.printStackTrace();
    } 
  }
  
  private void ban(PcInstance pc, StringTokenizer st) {
    try {
      String name = st.nextToken();
      PcInstance use = WorldInstance.getInstance().getPc(name);
      if (use != null) {
        BanTable("account", use.getClient().getID());
        BanTable("character", use.getName());
        BanTable("ip", use.getClient().getIP());
        use.Message("您的帳戶和 IP 已被阻止.");
        use.SendPacket((S_BasePacket)new S_CloseClient(10));
        pc.Message("帳戶已被成功阻止.");
      } else {
        pc.Message("找不到玩家." + name);
      } 
    } catch (Exception e) {
      pc.Message(".ban name");
    } 
  }
  
  private void unban(PcInstance pc, StringTokenizer st) {
    try {
      String ip = st.nextToken();
      BanListTable.getInstance().unBanIP(ip);
    } catch (Exception e) {
      pc.Message(".unban ip");
    } 
  }
  
  private void chatclose(PcInstance pc, StringTokenizer st) {
    try {
      String name = st.nextToken();
      PcInstance use = WorldInstance.getInstance().getPc(name);
      int millisecond = Integer.valueOf(st.nextToken()).intValue();
      if (use != null) {
        use.SendPacket((S_BasePacket)new S_ServerMessage(286, String.valueOf(millisecond)), false);
        pc.SendPacket((S_BasePacket)new S_ServerMessage(287, use.getName()), false);
      } else {
        pc.Message("找不到玩家." + name);
      } 
    } catch (Exception e) {
      pc.Message(".chatclose name ms");
    } 
  }
  
  private void worldclean() {
    WorldInstance.getInstance().CleanWorldItems();
  }
  
  private void monster(PcInstance pc, StringTokenizer st) {
    try {
      int id = Integer.valueOf(st.nextToken()).intValue();
      int count = 1;
      if (st.hasMoreTokens())
        count = Integer.valueOf(st.nextToken()).intValue(); 
      for (int i = 0; i < count; i++) {
        MonsterInstance mon = MonsterSpawnTable.getInstance().newMonster(id);
        if (mon != null) {
          mon.setSummon(true);
          mon.setHeading(pc.getHeading());
          mon.setHomeX(pc.getX());
          mon.setHomeY(pc.getY());
          mon.setHomeMap(pc.getMap());
          mon.toTeleport(pc.getX(), pc.getY(), pc.getMap());
          MonAi.getInstance().addMon(mon);
        } else {
          pc.Message("怪物不存在." + id);
          break;
        } 
      } 
    } catch (Exception e) {
      pc.Message(".monster id count");
    } 
  }
  
  private void shutdown(PcInstance pc, StringTokenizer st) {
    if (!Config.shutdown) {
      int second = 10;
      if (st.hasMoreTokens())
        try {
          second = Integer.valueOf(st.nextToken()).intValue();
        } catch (Exception e) {
          pc.Message(".shutdown second");
        }  
      Calendar cal = Calendar.getInstance();
      cal.add(13, second);
      ShutdownTimer.getInstance().setShutdownTime(cal);
    } 
  }
  
  private void item(PcInstance pc, StringTokenizer st) {
    try {
      int id = Integer.valueOf(st.nextToken()).intValue();
      int count = 1;
      int en = 0;
      int bless = 1;
      try {
        count = Integer.valueOf(st.nextToken()).intValue();
        en = Integer.valueOf(st.nextToken()).intValue();
        bless = Integer.valueOf(st.nextToken()).intValue();
      } catch (Exception localException1) {}
      ItemInstance item = ItemsTable.getInstance().newItem(id, true, true);
      if (item != null) {
        item.setCount(count);
        item.setEnLevel(en);
        item.setBless(bless);
        pc.getInventory().insert(item, item.getCount());
      } 
    } catch (Exception e) {
      pc.Message(".item id 數量 強化數 祝福狀態(012)");
    } 
  }
  
  private void search(PcInstance pc, StringTokenizer st) {
    pc.Message("將開始搜索...");
    try {
      String type = st.nextToken();
      if ("bb".equalsIgnoreCase(type)) {
        try {
          int id = Integer.valueOf(st.nextToken()).intValue();
          File f = new File("db/");
          for (File ff : f.listFiles()) {
            for (File fff : ff.listFiles()) {
              for (File ffff : fff.listFiles()) {
                if ("inventory.db".equalsIgnoreCase(ffff.getName())) {
                  boolean find = false;
                  BufferedInputStream bis = new BufferedInputStream(new FileInputStream(ffff.toString()));
                  byte[] data = new byte[bis.available()];
                  bis.read(data, 0, data.length);
                  bis.close();
                  StringTokenizer stt = new StringTokenizer(new String(data), "\r\n");
                  stt.nextToken();
                  while (stt.hasMoreTokens()) {
                    StringTokenizer sttt = new StringTokenizer(stt.nextToken(), "\t");
                    int item_id = Integer.valueOf(sttt.nextToken()).intValue();
                    int count = Integer.valueOf(sttt.nextToken()).intValue();
                    int en = Integer.valueOf(sttt.nextToken()).intValue();
                    if (item_id == id) {
                      if (!find) {
                        find = true;
                        pc.Message(" " + ffff.toString());
                      } 
                      if (en >= 0) {
                        pc.Message(" +" + en + " " + item_id + " (" + count + ")");
                        continue;
                      } 
                      pc.Message(" -" + en + " " + item_id + " (" + count + ")");
                    } 
                  } 
                } 
              } 
            } 
          } 
        } catch (Exception e) {
          pc.Message(".search bb id");
        } 
      } else if ("ck".equalsIgnoreCase(type)) {
        try {
          int id = Integer.valueOf(st.nextToken()).intValue();
          int find_id = 0;
          Connection con = null;
          PreparedStatement stt = null;
          ResultSet rs = null;
          try {
            con = DatabaseConnection.getInstance().getConnection();
            stt = con.prepareStatement("SELECT * FROM warehouse WHERE id=?");
            stt.setInt(1, id);
            rs = stt.executeQuery();
            while (rs.next()) {
              if (find_id == 0 || find_id != rs.getInt("account_uid")) {
                find_id = rs.getInt("account_uid");
                pc.Message("  account_uid: " + rs.getInt("account_uid") + " en:" + rs.getInt("en"));
              } 
            } 
          } catch (Exception localException5) {
          
          } finally {
            DatabaseConnection.getInstance().close(con, stt, rs);
          } 
          try {
            con = DatabaseConnection.getInstance().getConnection();
            stt = con.prepareStatement("SELECT * FROM warehouse_clan WHERE id=?");
            stt.setInt(1, id);
            rs = stt.executeQuery();
            while (rs.next()) {
              if (find_id == 0 || find_id != rs.getInt("clan_id")) {
                find_id = rs.getInt("clan_id");
                pc.Message("  clan_id: " + rs.getInt("clan_id") + " en:" + rs.getInt("en"));
              } 
            } 
          } catch (Exception localException6) {
          
          } finally {
            DatabaseConnection.getInstance().close(con, stt, rs);
          } 
        } catch (Exception e) {
          pc.Message(".search ck id");
        } 
      } else {
        Connection con = null;
        PreparedStatement stt = null;
        ResultSet rs = null;
        try {
          con = DatabaseConnection.getInstance().getConnection();
          stt = con.prepareStatement("SELECT * FROM items WHERE name LIKE '%" + type + "%'");
          rs = stt.executeQuery();
          while (rs.next())
            pc.Message("  " + rs.getString("name") + ": " + rs.getInt("item_id")); 
        } catch (Exception localException4) {
        
        } finally {
          DatabaseConnection.getInstance().close(con, stt, rs);
        } 
      } 
    } catch (Exception e) {
      pc.Message("搜索物品：");
      pc.Message("1:存放地(bb背包 ck倉庫) 物品ID，如 .search bb 1");
      pc.Message("2:物品ID，如 .search 1");
    } 
    pc.Message("搜索完成.");
  }
  
  private void chattingopen(PcInstance pc, StringTokenizer st) {
    try {
      String name = st.nextToken();
      PcInstance use = WorldInstance.getInstance().getPc(name);
      if (use != null) {
        BuffTimerInstance.getInstance().remove((L1Object)use, 10000);
      } else {
        pc.Message("找不到該用戶." + name);
      } 
    } catch (Exception e) {
      pc.Message(".chattingopen name");
    } 
  }
  
  private void checkSpeed(PcInstance pc, StringTokenizer st) {
    try {
      int id = Integer.valueOf(st.nextToken()).intValue();
      int count = Config.CHECK_STRICTNESS;
      try {
        count = Integer.valueOf(st.nextToken()).intValue();
      } catch (Exception localException1) {}
      Config.CHECK_SPEED_TYPE = id;
      if (id == 2)
        Config.CHECK_STRICTNESS = count; 
      String msg = null;
      if (id == 1) {
        msg = "原先";
      } else if (id == 2) {
        msg = "新型";
      } 
      pc.Message("加速器類型已切換到：" + msg);
    } catch (Exception e) {
      pc.Message(".checkspeed type (1.原先的加速判斷 2.新的加速判斷)");
    } 
  }
  
  private void set(PcInstance pc, StringTokenizer st) {
    int id = Integer.valueOf(st.nextToken()).intValue();
    Config.STATUS = id;
    pc.Message("當前狀態為：" + Config.STATUS);
  }
  
  private void summon(PcInstance pc, StringTokenizer st) {
    try {
      int npcid = Integer.valueOf(st.nextToken()).intValue();
      int count = Integer.valueOf(st.nextToken()).intValue();
      if (MonsterTable.getInstance().getMonster(npcid) != null) {
        SummonSystem.getInstance().addGmSummonMonster((Character)pc, 33, 3600, npcid, count);
      } else {
        pc.Message("沒有此怪物。");
      } 
    } catch (Exception e) {
      pc.Message("請輸入：npcid 數量");
    } 
  }
  
  private void chattingclose(PcInstance pc, StringTokenizer st) {
    try {
      String name = st.nextToken();
      int time_m = Integer.valueOf(st.nextToken()).intValue();
      SkillTable.getInstance().getTemplate((Character)pc, 10000).toMagic((L1Object)WorldInstance.getInstance().getPc(name), time_m);
    } catch (Exception e) {
      pc.Message(".chattingclose name min");
    } 
  }
  
  private void autopickup(PcInstance pc) {
    pc.setAutoPickup(!pc.isAutoPickup());
    if (pc.isAutoPickup()) {
      pc.Message("autopickup : ON");
    } else {
      pc.Message("autopickup : OFF");
    } 
  }
  
  private void level(PcInstance pc, StringTokenizer st) {
    try {
      if (st.countTokens() == 1) {
        int level = Integer.valueOf(st.nextToken()).intValue();
        level = (level < 2) ? 1 : (level - 1);
        pc.setExp(ExpTable.getInstance().getTemplate(level).get_bonus());
        pc.expChange();
      } else if (st.countTokens() == 2) {
        int level = Integer.valueOf(st.nextToken()).intValue();
        level = (level < 2) ? 1 : (level - 1);
        String name = st.nextToken();
        PcInstance use = WorldInstance.getInstance().getPc(name);
        if (use != null) {
          use.setExp(ExpTable.getInstance().getTemplate(level).get_bonus());
          use.expChange();
        } else {
          pc.Message("Player not found：" + use);
        } 
      } 
    } catch (Exception e) {
      pc.Message(".level num [name] ");
    } 
  }
  
  public void BanTable(String type, String name) {
    StringBuffer sb = new StringBuffer();
    if ("account".equalsIgnoreCase(type)) {
      sb.append("UPDATE account SET block_date='");
      sb.append(new Timestamp(System.currentTimeMillis()));
      sb.append("' WHERE id='");
      sb.append(name);
      sb.append("'");
      DatabaseConnection.getInstance().query_update(sb.toString());
    } else if ("character".equalsIgnoreCase(type)) {
      sb.append("UPDATE characters SET block_date='");
      sb.append(new Timestamp(System.currentTimeMillis()));
      sb.append("' WHERE name='");
      sb.append(name);
      sb.append("'");
      DatabaseConnection.getInstance().query_update(sb.toString());
    } else {
      BanListTable.getInstance().banIP(name);
    } 
  }
  
  private void hp(PcInstance pc, StringTokenizer st) {
    try {
      if (st.countTokens() == 1) {
        int currentHp = Integer.valueOf(st.nextToken()).intValue();
        pc.setCurrentHp(currentHp);
      } else if (st.countTokens() == 2) {
        String name = st.nextToken();
        int currentHp = Integer.valueOf(st.nextToken()).intValue();
        PcInstance use = WorldInstance.getInstance().getPc(name);
        if (use != null) {
          use.setCurrentHp(currentHp);
        } else {
          pc.Message("Player not found：" + use);
        } 
      } 
    } catch (Exception e) {
      pc.Message(".hp [玩家名] 血量  ");
    } 
  }
  
  private void mp(PcInstance pc, StringTokenizer st) {
    try {
      if (st.countTokens() == 1) {
        int currentHp = Integer.valueOf(st.nextToken()).intValue();
        pc.setCurrentMp(currentHp);
      } else if (st.countTokens() == 2) {
        String name = st.nextToken();
        int currentHp = Integer.valueOf(st.nextToken()).intValue();
        PcInstance use = WorldInstance.getInstance().getPc(name);
        if (use != null) {
          use.setCurrentMp(currentHp);
        } else {
          pc.Message("Player not found：" + use);
        } 
      } 
    } catch (Exception e) {
      pc.Message(".mp [玩家名] 魔量  ");
    } 
  }
  
  private void callcheck(PcInstance pc, StringTokenizer st) {
    String name = st.nextToken();
    PcInstance o = WorldInstance.getInstance().getPc(name);
    if (o != null) {
      o.toTeleport(pc.getX(), pc.getY(), pc.getMap());
      if (o.getCurrentHp() >= 50) {
        pc.Message("[外挂自?加血??]玩家(" + name + ") " + "???始。");
        callcheck3(o);
        pc.Message("???束。");
      } else {
        pc.Message("[外挂自?加血??]玩家(" + name + ") " + "血量低于50 自?加?血并????。");
        o.setCurrentHp(o.getMaxHp());
        callcheck3(o);
        pc.Message("???束。");
      } 
    } else {
      pc.Message("找不到玩家." + name);
    } 
  }
  
  private void callcheck2(PcInstance opc) {
    long time = System.currentTimeMillis();
    while (opc != null) {
      if (opc.getCurrentHp() < 50)
        break; 
      long nowTime = System.currentTimeMillis();
      if (nowTime - time > 500L) {
        opc.setCurrentHp(opc.getCurrentHp() - 20);
        time = System.currentTimeMillis();
      } 
    } 
  }
  
  private void callcheck3(PcInstance opc) {
    opc.setCurrentHp(50);
  }
}
