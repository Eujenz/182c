package net.world.function;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import net.Config;
import net.database.AccountTable;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectChatting;
import net.network.server.S_ServerMessage;
import net.world.WorldInstance;
import net.world.function.bean.Party;
import net.world.instance.PcInstance;
import net.world.object.L1Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChattingSystem {
  final Logger log = LoggerFactory.getLogger(ChattingSystem.class);
  
  private static Map<Integer, String> typeMap = new HashMap<Integer, String>();
  
  private Map<String, Integer> _chatUserMap = new ConcurrentHashMap<String, Integer>();
  
  private static ChattingTimer timer;
  
  static final String CHATTING_LOG = "[%s] [%s] :%s";
  
  private static class Holder {
    static ChattingSystem instance = new ChattingSystem();
  }
  
  public static ChattingSystem getInstance() {
    return Holder.instance;
  }
  
  private ChattingSystem() {
    typeMap.put(Integer.valueOf(0), "说话");
    typeMap.put(Integer.valueOf(2), "大喊");
    typeMap.put(Integer.valueOf(3), "全体");
    typeMap.put(Integer.valueOf(4), "血盟");
    typeMap.put(Integer.valueOf(11), "组队");
    timer = new ChattingTimer();
    timer.start();
  }
  
  public void Chatting(PcInstance pc, int type, String msg) {
    if (pc == null)
      return; 
    timer.check(pc);
    if (pc.isCloseChat()) {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(242));
      return;
    } 
    this.log.info(String.format("[%s] [%s] :%s", new Object[] { pc.getName(), typeMap.get(Integer.valueOf(type)), msg }));
    switch (type) {
      case 0:
        normal(pc, msg);
        break;
      case 1:
      case 2:
        shouting(pc, msg);
        break;
      case 3:
        global(pc, msg);
        break;
      case 4:
        clan(pc, msg);
        break;
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
        party(pc, msg);
        break;
    } 
  }
  
  public void whisper(PcInstance pc, String name, String msg) {
    if (pc == null)
      return; 
    if (pc.isCloseChat()) {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(242));
      return;
    } 
    PcInstance pcInstance = WorldInstance.getInstance().getPc(name);
    if (pcInstance != null) {
      if (pc.getLevel() >= 7) {
        if (pcInstance.isWhisperChat() || pc.isGm()) {
          pc.SendPacket((S_BasePacket)new S_ObjectChatting((L1Object)pcInstance, msg, 9));
          pcInstance.SendPacket((S_BasePacket)new S_ObjectChatting((L1Object)pc, msg, 8));
        } else {
          pc.SendPacket((S_BasePacket)new S_ServerMessage(205, name));
        } 
      } else {
        pc.SendPacket((S_BasePacket)new S_ServerMessage(404, String.valueOf(7)));
      } 
    } else {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(73, name));
    } 
  }
  
  private void party(PcInstance pc, String msg) {
    Party p = PartySystem.getInstance().get(pc.getPartyId());
    if (p != null)
      p.SendPacket((S_BasePacket)new S_ObjectChatting((L1Object)pc, msg, 11)); 
  }
  
  private void normal(PcInstance pc, String msg) {
    if (msg.startsWith(".")) {
      GmCommand.getInstance().cmd(pc, msg);
    } else if (pc.isMonthCardsStat()) {
      if (!AccountTable.getInstance().select_id(msg)) {
        pc.Message("該帳號不存在，請核對後再試。");
        pc.setMonthCardsStat(false);
        return;
      } 
      pc.setMonthCardsStatTempMessage(msg);
      pc.Message("請再次輸入要充值的帳號名。");
      pc.setMonthCardsStatOK(true);
      pc.setMonthCardsStat(false);
    } else if (pc.isMonthCardsStatOK()) {
      if (!msg.equalsIgnoreCase(pc.getMonthCardsStatTempMessage())) {
        pc.Message("兩次輸入的帳號不一致，請核對後再試。");
        pc.setMonthCardsStatTempMessage(null);
        pc.setMonthCardsStatOK(false);
        return;
      } 
      pc.setMonthCardsStatTempMessage(null);
      MonthCardsSystem.getInstance().updateTimeOfMonthCardsOther(pc, msg);
      pc.setMonthCardsStatOK(false);
    } else {
      pc.SendPacket((S_BasePacket)new S_ObjectChatting((L1Object)pc, msg, 0), true);
    } 
  }
  
  private void shouting(PcInstance pc, String msg) {
    if (pc.getFood() < 1)
      pc.Message("你太饿了以致于说不出话来！"); 
    pc.setFood(pc.getFood() - 1);
    pc.SendPacket((S_BasePacket)new S_ObjectChatting((L1Object)pc, msg, 2), true);
  }
  
  private void global(PcInstance pc, String msg) {
    if (pc.getLevel() >= Config.WORLD_ALLCHATING || pc.isGm()) {
      byte b;
      int i;
      PcInstance[] arrayOfPcInstance;
      for (i = (arrayOfPcInstance = WorldInstance.getInstance().getPc()).length, b = 0; b < i; ) {
        PcInstance use = arrayOfPcInstance[b];
        if (use.isGlobalChat() || pc.isGm())
          use.SendPacket((S_BasePacket)new S_ObjectChatting((L1Object)pc, msg, 3)); 
        b++;
      } 
    } else {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(195, String.valueOf(Config.WORLD_ALLCHATING)));
    } 
  }
  
  private void clan(PcInstance pc, String msg) {
    ClanSystem.getInstance().SendPacket(pc, (S_BasePacket)new S_ObjectChatting((L1Object)pc, msg, 4));
  }
  
  private class ChattingTimer extends TimerTask {
    private Timer timer;
    
    private ChattingTimer() {}
    
    public void start() {
      this.timer = new Timer(false);
      this.timer.schedule(new ChattingTimer(), 5000L, 1000L);
    }
    
    public void run() {
      for (String key : ChattingSystem.this._chatUserMap.keySet()) {
        Integer count = (Integer)ChattingSystem.this._chatUserMap.get(key);
        count = Integer.valueOf(count.intValue() - 3);
        count = Integer.valueOf((count.intValue() < 1) ? 0 : count.intValue());
        ChattingSystem.this._chatUserMap.put(key, count);
      } 
    }
    
    public void check(PcInstance pc) {
      if (!ChattingSystem.this._chatUserMap.containsKey(pc.getName())) {
        ChattingSystem.this._chatUserMap.put(pc.getName(), Integer.valueOf(1));
      } else {
        ChattingSystem.this._chatUserMap.put(pc.getName(), Integer.valueOf(((Integer)ChattingSystem.this._chatUserMap.get(pc.getName())).intValue() + 1));
      } 
      if (((Integer)ChattingSystem.this._chatUserMap.get(pc.getName())).intValue() > 20) {
        pc.Message("故意刷屏，系统踢出.");
        pc.getClient().close();
      } else if (((Integer)ChattingSystem.this._chatUserMap.get(pc.getName())).intValue() > 4) {
        pc.Message("你说话太快了,系统忙不过来.");
      } 
    }
  }
}
