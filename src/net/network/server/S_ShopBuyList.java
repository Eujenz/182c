package net.network.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.database.DatabaseConnection;
import net.database.ItemsTable;
import net.database.bean.Item;
import net.util.Util;
import net.world.instance.ShopInstance;
import net.world.slimerace.SlimeRaceSystem;
import net.world.slimerace.SlimeraceShopInstance;

public class S_ShopBuyList extends S_Inventory {
  public S_ShopBuyList(SlimeraceShopInstance npc) {
    writeC(43);
    writeD(npc.getObjectId());
    writeH(5);
    for (int i = 0; i < 5; i++) {
      writeD(i);
      writeH(143);
      writeD(100);
      writeS(SlimeRaceSystem.getInstance().SlimeRaceTicketName(i));
      writeC(6);
      writeC(23);
      writeC(5);
      writeD(0);
    } 
  }
  
  public S_ShopBuyList(ShopInstance npc) {
    writeC(43);
    writeD(npc.getObjectId());
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT * FROM npc_shop WHERE npcid=? AND shop='1' ORDER BY uid");
      st.setInt(1, npc.getNpcId());
      rs = st.executeQuery();
      rs.last();
      writeH(rs.getRow());
      rs.first();
      rs.previous();
      while (rs.next()) {
        Item temp = ItemsTable.getInstance().getTemplate(rs.getInt("itemid"));
        int count = rs.getInt("itemcount");
        int price = npc.getPrice(rs.getInt("price"), true);
        writeD(rs.getInt("uid"));
        switch (temp.getType1()) {
          case 0:
            etc(temp, count, price);
          case 1:
            writeH(temp.get_gfxid());
            writeD(price);
            if (count > 0) {
              writeS(String.valueOf(temp.get_nameid()) + " (" + count + ")");
            } else {
              writeS(temp.get_nameid());
            } 
            weapon(temp);
          case 2:
            writeH(temp.get_gfxid());
            writeD(price);
            if (count > 0) {
              writeS(String.valueOf(temp.get_nameid()) + " (" + count + ")");
            } else {
              writeS(temp.get_nameid());
            } 
            armor(temp);
          case 3:
            etc(temp, count, price);
        } 
      } 
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
    writeC(7);
    writeC(0);
  }
  
  private void etc(Item temp, int count, int price) {
    writeH(temp.get_gfxid());
    writeD(price);
    if (count > 0) {
      writeS(String.valueOf(temp.get_nameid()) + " (" + count + ")");
    } else {
      writeS(temp.get_nameid());
    } 
    writeC(6);
    writeC(23);
    writeC(temp.get_material());
    writeD(temp.getWeight());
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    return sb.toString();
  }
}
