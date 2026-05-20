package net.world.function;

import java.util.HashMap;
import java.util.Map;

import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.network.server.S_ServerMessageYesNo;
import net.network.server.S_TradeAddItem;
import net.network.server.S_TradeStart;
import net.network.server.S_TradeStatus;
import net.world.function.bean.Trade;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.object.Character;
import net.world.object.L1Object;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradeSystem {
  final Logger log = LoggerFactory.getLogger(TradeSystem.class);
  
  private Object cancel = new Object();
  
  private Object ok = new Object();
  
  private Map<Integer, Trade> list;
  
  private static class Holder {
    static TradeSystem instance = new TradeSystem();
  }
  
  public static TradeSystem getInstance() {
    return Holder.instance;
  }
  
  private TradeSystem() {
    this.list = new HashMap<Integer, Trade>();
  }
  
  public void addItem(PcInstance pc, int inv_id, long count) {
    Trade a = null;
    Trade b = null;
    a = getTrade(pc);
    if (a != null) {
      b = this.list.get(Integer.valueOf(a.getUse().getObjectId()));
      ItemInstance item = pc.getInventory().getItemInvId(inv_id);
      if (item != null && 0L < count && count <= item.getCount() && count <= 2147483647L && !item.isEquipped() && item.getItem().isTrade())
        if (a.getCha().getObjectId() == pc.getObjectId()) {
          if (a.getCha_list().size() < 12)
            if (a.getUse().getInventory().isWeight((int)(item.getItem().getWeight() * count))) {
              if (count == item.getCount()) {
                pc.getInventory().remove(item);
              } else {
                item.setCount((Character)pc, item.getCount() - count);
                ItemInstance temp = item.clone();
                temp.setCount(count);
                item = temp;
              } 
              a.addCha_list(item);
              a.getCha().SendPacket((S_BasePacket)new S_TradeAddItem(item, 0));
              b.getUse().SendPacket((S_BasePacket)new S_TradeAddItem(item, 1));
            } else {
              a.getCha().SendPacket((S_BasePacket)new S_ServerMessage(271));
            }  
        } else if (b.getUse_list().size() < 12) {
          if (b.getCha().getInventory().isWeight((int)(item.getItem().getWeight() * count))) {
            if (count == item.getCount()) {
              pc.getInventory().remove(item);
            } else {
              item.setCount((Character)pc, item.getCount() - count);
              ItemInstance temp = item.clone();
              temp.setCount(count);
              item = temp;
            } 
            b.addUse_list(item);
            a.getCha().SendPacket((S_BasePacket)new S_TradeAddItem(item, 1));
            b.getUse().SendPacket((S_BasePacket)new S_TradeAddItem(item, 0));
          } else {
            b.getUse().SendPacket((S_BasePacket)new S_ServerMessage(271));
          } 
        }  
    } 
  }
  
  static String TRADE_OK = "[%s] 与 [%s] 确?交易";
  
  static String TRADE_OK_ITEM_DETAIL = "[%s] 向 [%s] 交易成功,%s";
  
  static String TRADE_OK_ITEM_END = "[%s] 向 [%s] 交易?束";
  
  public void tradeOk(PcInstance pc) {
    synchronized (this.ok) {
      Trade a = null;
      Trade b = null;
      a = getTrade(pc);
      if (a != null) {
        if (userFind(a.getCha()) == null || a.getUse().getObjectId() != userFind(a.getCha()).getObjectId()) {
          tradeCancel(a.getCha());
          return;
        } 
        b = this.list.get(Integer.valueOf(a.getUse().getObjectId()));
        if (a.getCha().getObjectId() == pc.getObjectId()) {
          a.setCha_ok(true);
          b.setCha_ok(true);
        } 
        if (b.getUse().getObjectId() == pc.getObjectId()) {
          a.setUse_ok(true);
          b.setUse_ok(true);
        } 
        if (a.isCha_ok() && b.isUse_ok()) {
          this.log.info(String.format(TRADE_OK, new Object[] { a.getCha().getName(), a.getUse().getName() }));
          for (ItemInstance item : a.getCha_list()) {
            this.log.info(String.format(TRADE_OK_ITEM_DETAIL, new Object[] { a.getCha().getName(), a.getUse().getName(), item.logString() }));
            if (!a.getUse().getInventory().insert(item, item.getCount()))
              item.toTeleport(a.getUse().getX(), a.getUse().getY(), a.getUse().getMap()); 
          } 
          for (ItemInstance item : b.getUse_list()) {
            this.log.info(String.format(TRADE_OK_ITEM_DETAIL, new Object[] { b.getUse().getName(), b.getCha().getName(), item.logString() }));
            if (!b.getCha().getInventory().insert(item, item.getCount()))
              item.toTeleport(b.getCha().getX(), b.getCha().getY(), b.getCha().getMap()); 
          } 
          a.getCha().SendPacket((S_BasePacket)new S_TradeStatus(a.isCha_ok()));
          b.getUse().SendPacket((S_BasePacket)new S_TradeStatus(b.isUse_ok()));
          this.list.remove(Integer.valueOf(a.getCha().getObjectId()));
          this.list.remove(Integer.valueOf(b.getUse().getObjectId()));
          this.log.info(String.format(TRADE_OK_ITEM_END, new Object[] { a.getCha().getName(), a.getUse().getName() }));
          a.clear();
          a = null;
          b = null;
        } 
      } 
    } 
    pc.getInventory().save();
  }
  
  static String TRADE_CANCEL_END = "[%s] <取消交易>?束,原交易?起者  [%s]，交易?象 [%S] ";
  
  static final String TRADE_NEW = "[%s]向 [%s]?起新交易.";
  
  public void tradeCancel(PcInstance pc) {
    synchronized (this.cancel) {
      boolean islog = false;
      Trade a = null;
      Trade b = null;
      String aName = "";
      String bName = "";
      a = getTrade(pc);
      if (a != null) {
        aName = a.getCha().getName();
        b = this.list.get(Integer.valueOf(a.getUse().getObjectId()));
        if (b != null && b.getCha() != null) {
          bName = b.getUse().getName();
          islog = true;
        } 
      } 
      if (a != null) {
        this.list.remove(Integer.valueOf(a.getCha().getObjectId()));
        for (ItemInstance item : a.getCha_list()) {
          if (!a.getCha().getInventory().insert(item, item.getCount()))
            item.toTeleport(a.getCha().getX(), a.getCha().getY(), a.getCha().getMap()); 
        } 
        a.getCha().SendPacket((S_BasePacket)new S_TradeStatus(false));
        a = null;
      } 
      if (b != null) {
        this.list.remove(Integer.valueOf(b.getUse().getObjectId()));
        for (ItemInstance item : b.getUse_list()) {
          if (!b.getUse().getInventory().insert(item, item.getCount()))
            item.toTeleport(b.getUse().getX(), b.getUse().getY(), b.getUse().getMap()); 
        } 
        b.getUse().SendPacket((S_BasePacket)new S_TradeStatus(false));
        b = null;
      } 
      if (islog)
        this.log.info(String.format(TRADE_CANCEL_END, new Object[] { pc.getName(), aName, bName })); 
    } 
  }
  
  public void tradeStart(PcInstance pc) {
    Trade a = this.list.get(Integer.valueOf(pc.getObjectId()));
    if (a != null) {
      a.getCha().SendPacket((S_BasePacket)new S_ServerMessage(254, a.getUse().getName()));
      a.getUse().SendPacket((S_BasePacket)new S_ServerMessage(254, a.getCha().getName()));
      a.getCha().SendPacket((S_BasePacket)new S_TradeStart(a.getUse().getName()));
      a.getUse().SendPacket((S_BasePacket)new S_TradeStart(a.getCha().getName()));
    } 
  }
  
  public void trade(PcInstance pc) {
    if (!pc.isDead()) {
      PcInstance use = userFind(pc);
      if (use != null) {
        Trade a = this.list.get(Integer.valueOf(pc.getObjectId()));
        Trade b = this.list.get(Integer.valueOf(use.getObjectId()));
        if (a == null && b == null) {
          a = new Trade();
          b = a;
          a.setCha(pc);
          a.setUse(use);
          this.list.put(Integer.valueOf(pc.getObjectId()), a);
          this.list.put(Integer.valueOf(use.getObjectId()), b);
          use.SendPacket((S_BasePacket)new S_ServerMessageYesNo(252, pc.getName()));
          this.log.info(String.format("[%s]向 [%s]?起新交易.", new Object[] { pc.getName(), use.getName() }));
        } else if (a != null) {
          pc.SendPacket((S_BasePacket)new S_ServerMessage(258));
        } else {
          pc.SendPacket((S_BasePacket)new S_ServerMessage(259));
        } 
      } 
    } 
  }
  
  public PcInstance userFind(PcInstance pc) {
    int locx = pc.getX();
    int locy = pc.getY();
    switch (pc.getHeading()) {
      case 0:
        locy--;
        break;
      case 1:
        locx++;
        locy--;
        break;
      case 2:
        locx++;
        break;
      case 3:
        locx++;
        locy++;
        break;
      case 4:
        locy++;
        break;
      case 5:
        locx--;
        locy++;
        break;
      case 6:
        locx--;
        break;
      case 7:
        locx--;
        locy--;
        break;
    } 
    for (L1Object o : pc.getObjectList()) {
      if (o instanceof PcInstance && locx == o.getX() && locy == o.getY()) {
        switch (pc.getHeading()) {
          case 0:
            if (o.getHeading() == 4)
              return (PcInstance)o; 
            break;
          case 1:
            if (o.getHeading() == 5)
              return (PcInstance)o; 
            break;
          case 2:
            if (o.getHeading() == 6)
              return (PcInstance)o; 
            break;
          case 3:
            if (o.getHeading() == 7)
              return (PcInstance)o; 
            break;
          case 4:
            if (o.getHeading() == 0)
              return (PcInstance)o; 
            break;
          case 5:
            if (o.getHeading() == 1)
              return (PcInstance)o; 
            break;
          case 6:
            if (o.getHeading() == 2)
              return (PcInstance)o; 
            break;
          case 7:
            if (o.getHeading() == 3)
              return (PcInstance)o; 
            break;
        } 
        pc.SendPacket((S_BasePacket)new S_ServerMessage(91, o.getName()));
        return null;
      } 
    } 
    pc.SendPacket((S_BasePacket)new S_ServerMessage(260));
    return null;
  }
  
  private Trade getTrade(PcInstance pc) {
    Trade a = this.list.get(Integer.valueOf(pc.getObjectId()));
    if (a != null && a.getCha().getObjectId() != pc.getObjectId())
      a = this.list.get(Integer.valueOf(a.getCha().getObjectId())); 
    return a;
  }
}
