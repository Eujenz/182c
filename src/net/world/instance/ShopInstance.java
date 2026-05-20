package net.world.instance;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import net.database.DatabaseConnection;
import net.database.ItemsTable;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.network.server.S_ShopBuyList;
import net.network.server.S_ShopSellList;
import net.network.server.S_ShowHtml;
import net.world.instance.bean.Shop;
import net.world.object.L1Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShopInstance extends L1Object {
  final Logger log = LoggerFactory.getLogger(ShopInstance.class);
  
  private int npcId;
  
  public ShopInstance(int npcId) {
    this.npcId = npcId;
  }
  
  public void ShopBuy(C_BasePacket data, PcInstance pc) {
    int Count = data.readH();
    if (Integer.MAX_VALUE >= Count && Count > 0 && Count <= 100)
      for (int i = 0; i < Count; i++) {
        int item_idx = data.readD();
        long item_count = data.readD();
        if (item_count > 0L && item_count <= 1000L) {
          Shop s = getShopItem(item_idx);
          if (s != null) {
            ItemInstance temp = pc.getInventory().isItem(s.getId(), 1);
            if (pc.getInventory().getCount() >= 180 && temp == null) {
              pc.SendPacket((S_BasePacket)new S_ServerMessage(263));
              break;
            } 
            if (!pc.getInventory().isWeight((int)(s.getItem().getWeight() * item_count))) {
              pc.SendPacket((S_BasePacket)new S_ServerMessage(82));
              break;
            } 
            if (this.npcId != 70449) {
              if (pc.getInventory().Aden(s.getPrice() * item_count, true)) {
                addTaxTotal(s.getTaxPrice() * (int)item_count);
                if (s.getCount() > 0)
                  item_count *= s.getCount(); 
                if (temp == null) {
                  if (s.getItem().isPiles()) {
                    temp = ItemsTable.getInstance().newItem(s.getId(), true, true);
                    temp.setCount(item_count);
                    pc.getInventory().add(temp);
                  } else {
                    for (int j = 0; j < item_count; j++) {
                      temp = ItemsTable.getInstance().newItem(s.getId(), true, true);
                      pc.getInventory().add(temp);
                    } 
                  } 
                } else {
                  temp.setCount(pc, temp.getCount() + item_count);
                } 
              } else {
                pc.SendPacket((S_BasePacket)new S_ServerMessage(189));
                break;
              } 
            } else if (pc.getInventory().Money(s.getPrice() * item_count, true)) {
              if (s.getCount() > 0)
                item_count *= s.getCount(); 
              if (temp == null) {
                if (s.getItem().isPiles()) {
                  temp = ItemsTable.getInstance().newItem(s.getId(), true, true);
                  temp.setCount(item_count);
                  pc.getInventory().add(temp);
                } else {
                  for (int j = 0; j < item_count; j++) {
                    temp = ItemsTable.getInstance().newItem(s.getId(), true, true);
                    pc.getInventory().add(temp);
                  } 
                } 
              } else {
                temp.setCount(pc, temp.getCount() + item_count);
              } 
            } else {
              pc.Message("商城幣不足。");
              break;
            } 
          } 
        } 
      }  
  }
  
  public void ShopSell(C_BasePacket data, PcInstance pc) {
    int Count = data.readH();
    if (Integer.MAX_VALUE >= Count && Count > 0 && Count <= 180)
      for (int i = 0; i < Count; i++) {
        int inv_id = data.readD();
        long count = data.readD();
        ItemInstance temp = pc.getInventory().getItemInvId(inv_id);
        if (temp == null) {
          this.log.error("ShopSell異常 temp 為空 inv_id：" + inv_id + "，玩家名：" + pc.getName());
          return;
        } 
        Shop s = getShopItemId(temp.getItem().getItemId());
        if (s != null && temp != null && 2147483647L >= count && count > 0L && temp.getCount() >= count && temp.getItem().isSell()) {
          temp.setCount(pc, temp.getCount() - count);
          ItemInstance aden = pc.getInventory().getAden();
          if (aden == null) {
            aden = ItemsTable.getInstance().newItem(5, false, true);
            aden.setCount(0L);
            pc.getInventory().add(aden);
          } 
          aden.setCount(pc, aden.getCount() + s.getPrice() * count);
          addTaxTotal(s.getTaxPrice() * (int)count);
        } 
      }  
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if (!getDistance((L1Object)pc, 4)) {
      pc.Message("距離太遠.");
      return;
    } 
    if (text1.equalsIgnoreCase("buy")) {
      pc.SendPacket((S_BasePacket)new S_ShopBuyList(this));
    } else if (text1.equalsIgnoreCase("sell")) {
      int count = 0;
      ByteArrayOutputStream _bao = new ByteArrayOutputStream();
      Connection con = null;
      PreparedStatement st = null;
      ResultSet rs = null;
      try {
        con = DatabaseConnection.getInstance().getConnection();
        st = con.prepareStatement("SELECT * FROM npc_shop WHERE npcid=?");
        st.setInt(1, getNpcId());
        rs = st.executeQuery();
        while (rs.next()) {
          List<ItemInstance> list = pc.getInventory().getItemDbId(rs.getInt("itemid"));
          if (list != null)
            for (ItemInstance item : list) {
              if (item != null && !item.isEquipped() && item.getItem().isSell() && item.getEnLevel() == 0) {
                count++;
                _bao.write(item.getInvID() & 0xFF);
                _bao.write(item.getInvID() >> 8 & 0xFF);
                _bao.write(item.getInvID() >> 16 & 0xFF);
                _bao.write(item.getInvID() >> 24 & 0xFF);
                int price = getPrice(rs.getInt("price"), false);
                _bao.write(price & 0xFF);
                _bao.write(price >> 8 & 0xFF);
                _bao.write(price >> 16 & 0xFF);
                _bao.write(price >> 24 & 0xFF);
              } 
            }  
        } 
      } catch (Exception localException) {
      
      } finally {
        DatabaseConnection.getInstance().close(con, st, rs);
      } 
      if (count > 0) {
        pc.SendPacket((S_BasePacket)new S_ShopSellList(this, _bao, count));
      } else {
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "nosell", getName()));
      } 
    } else {
      pc.Message("text1:" + text1 + " text2:" + text2);
    } 
  }
  
  public int getNpcId() {
    return this.npcId;
  }
  
  public int getPrice(int price, boolean buy) {
    if (!buy) {
      price /= 2;
    } else {
      price += getTax(price, false);
    } 
    if (price <= 0)
      price = 0; 
    return price;
  }
  
  private Shop getShopItem(int uid) {
    Shop s = null;
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM npc_shop WHERE uid=? AND npcid=?");
      st.setInt(1, uid);
      st.setInt(2, getNpcId());
      rs = st.executeQuery();
      if (rs.next()) {
        s = new Shop();
        s.setUid(rs.getInt("uid"));
        s.setNpcid(rs.getInt("npcid"));
        s.setId(rs.getInt("itemid"));
        s.setCount(rs.getInt("itemcount"));
        s.setPrice(getPrice(rs.getInt("price"), true));
        s.setTaxPrice(getTax(rs.getInt("price"), true));
        s.setShop((rs.getInt("shop") == 1));
        s.setItem(ItemsTable.getInstance().getTemplate(s.getId()));
      } 
    } catch (Exception localException) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
    return s;
  }
  
  private Shop getShopItemId(int id) {
    Shop s = null;
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM npc_shop WHERE itemid=? AND npcid=?");
      st.setInt(1, id);
      st.setInt(2, getNpcId());
      rs = st.executeQuery();
      if (rs.next()) {
        s = new Shop();
        s.setUid(rs.getInt("uid"));
        s.setNpcid(rs.getInt("npcid"));
        s.setId(rs.getInt("itemid"));
        s.setCount(rs.getInt("itemcount"));
        s.setPrice(getPrice(rs.getInt("price"), false));
        s.setTaxPrice(getTax(rs.getInt("price"), true));
        s.setShop((rs.getInt("shop") == 1));
      } 
    } catch (Exception localException) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
    return s;
  }
  
  public int getTax() {
    return 0;
  }
  
  public void addTaxTotal(int aden) {}
  
  private int getTax(int price, boolean kingdom) {
    price = (int)(price * getTax() * 0.01D);
    if (kingdom)
      price = (int)(price * getTax() * 0.01D); 
    return price;
  }
}
