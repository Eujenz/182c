package net.world.slimerace;

import java.io.ByteArrayOutputStream;
import java.util.List;
import net.Config;
import net.database.DatabaseConnection;
import net.database.ItemsTable;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.network.server.S_ShopBuyList;
import net.network.server.S_ShopSellList;
import net.network.server.S_ShowHtml;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.instance.inventory.function.SlimeRaceTicket;
import net.world.object.Character;
import net.world.object.L1Object;

public class SlimeraceShopInstance extends L1Object {
  public void ShopBuy(C_BasePacket data, PcInstance pc) {
    switch ((SlimeRaceSystem.getInstance()).status.ordinal()) {
      case 0:
      case 1:
      case 2:
        return;
    } 
    int Count = data.readH();
    if (Integer.MAX_VALUE >= Count && Count > 0 && Count <= 100)
      for (int i = 0; i < Count; i++) {
        int item_idx = data.readD();
        long item_count = data.readD();
        if (item_count > 0L && item_count <= 1000L) {
          SlimeRaceTicket srt = SlimeRaceSystem.getInstance().getSlimeRaceTicket(item_idx);
          ItemInstance temp = pc.getInventory().isItem((ItemInstance)srt);
          if (pc.getInventory().getCount() >= 180 && temp == null) {
            pc.SendPacket((S_BasePacket)new S_ServerMessage(263));
            break;
          } 
          if (pc.getInventory().Aden(100L * item_count, true)) {
            if (temp == null) {
              if (srt != null) {
                srt.setCount(item_count);
                pc.getInventory().add((ItemInstance)srt);
              } 
            } else {
              temp.setCount((Character)pc, temp.getCount() + item_count);
            } 
          } else {
            pc.SendPacket((S_BasePacket)new S_ServerMessage(189));
            break;
          } 
        } 
      }  
  }
  
  public void ShopSell(C_BasePacket data, PcInstance pc) {
    switch ((SlimeRaceSystem.getInstance()).status.ordinal()) {
      case 0:
      case 1:
        return;
    } 
    int Count = data.readH();
    if (Count > 0 && Count <= 180)
      for (int i = 0; i < Count; i++) {
        int inv_id = data.readD();
        long count = data.readD();
        ItemInstance temp = pc.getInventory().getItemInvId(inv_id);
        if (temp != null && 2147483647L >= count && count > 0L && temp.getCount() >= count && temp.getItem().isSell() && 
          temp instanceof SlimeRaceTicket) {
          SlimeRaceTicket srt = (SlimeRaceTicket)temp;
          srt.setCount((Character)pc, srt.getCount() - count);
          if (isWinner(srt)) {
            ItemInstance aden = pc.getInventory().getAden();
            if (aden == null) {
              aden = ItemsTable.getInstance().newItem(5, false, true);
              aden.setCount(0L);
              pc.getInventory().add(aden);
            } 
            aden.setCount((Character)pc, aden.getCount() + 200L * count);
          } 
        } 
      }  
  }
  
  public void Talk(PcInstance pc) {
    if (!Config.SLIME) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "gora1"));
      return;
    } 
    switch ((SlimeRaceSystem.getInstance()).status.ordinal()) {
      case 2:
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "gora5"));
        break;
      case 3:
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "goraev1"));
        break;
      case 0:
      case 1:
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "gora3"));
        break;
    } 
  }
  
  public void Talk(PcInstance pc, String text1, String text2) {
    if ("status".equalsIgnoreCase(text1)) {
      pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "gora4", SlimeRaceSystem.getInstance().getSlimeStatus()));
    } else if ("buy".equalsIgnoreCase(text1)) {
      pc.SendPacket((S_BasePacket)new S_ShopBuyList(this));
    } else {
      int count = 0;
      ByteArrayOutputStream _bao = new ByteArrayOutputStream();
      List<ItemInstance> list = pc.getInventory().getItemDbId(344);
      if (list != null) {
        count = list.size();
        for (ItemInstance item : list) {
          int price = 0;
          if (isWinner((SlimeRaceTicket)item))
            price = 200; 
          _bao.write(item.getInvID() & 0xFF);
          _bao.write(item.getInvID() >> 8 & 0xFF);
          _bao.write(item.getInvID() >> 16 & 0xFF);
          _bao.write(item.getInvID() >> 24 & 0xFF);
          _bao.write(price & 0xFF);
          _bao.write(price >> 8 & 0xFF);
          _bao.write(price >> 16 & 0xFF);
          _bao.write(price >> 24 & 0xFF);
        } 
      } 
      if (count > 0) {
        pc.SendPacket((S_BasePacket)new S_ShopSellList(this, _bao, count));
      } else {
        pc.SendPacket((S_BasePacket)new S_ShowHtml(getObjectId(), "nosell", getName()));
      } 
    } 
  }
  
  private boolean isWinner(SlimeRaceTicket srt) {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT * FROM slimerace_log WHERE uid='");
    sb.append(srt.getSlimeRaceUid());
    sb.append("' AND search_item='");
    sb.append(srt.getSlimeRacerIdx());
    sb.append("'");
    return DatabaseConnection.getInstance().query_select(sb.toString());
  }
}
