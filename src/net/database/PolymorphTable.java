package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.database.bean.Poly;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.object.Character;
import net.world.object.L1Object;

public class PolymorphTable {
  private List<Poly> list;
  
  private static class Holder {
    static PolymorphTable instance = new PolymorphTable();
  }
  
  public static PolymorphTable getInstance() {
    return Holder.instance;
  }
  
  private PolymorphTable() {
    System.out.print("[SQL] 加载变身信息.");
    this.list = new ArrayList<Poly>();
    try {
      Connection con = DatabaseConnection.getInstance().getConnection();
      PreparedStatement st = con.prepareStatement("SELECT * FROM polymorph");
      ResultSet rs = st.executeQuery();
      while (rs.next()) {
        Poly polys = new Poly();
        polys.setId(rs.getInt(1));
        polys.setName(rs.getString(2));
        polys.setDb(rs.getString(3));
        polys.setPolyid(rs.getInt(4));
        polys.setMinlvl(rs.getInt(5));
        polys.setWeapon(rs.getInt(6));
        polys.setHelm((rs.getInt(7) == 1));
        polys.setEarring((rs.getInt(8) == 1));
        polys.setNecklace((rs.getInt(9) == 1));
        polys.setT((rs.getInt(10) == 1));
        polys.setArmor((rs.getInt(11) == 1));
        polys.setCloak((rs.getInt(12) == 1));
        polys.setRing((rs.getInt(13) == 1));
        polys.setBelt((rs.getInt(14) == 1));
        polys.setGlove((rs.getInt(15) == 1));
        polys.setShield((rs.getInt(16) == 1));
        polys.setBoots((rs.getInt(17) == 1));
        this.list.add(polys);
      } 
      System.out.println(" 数量:" + this.list.size());
      rs.close();
      st.close();
      con.close();
    } catch (Exception exception) {}
  }
  
  public Poly getTemplate(String id) {
    for (Poly p : this.list) {
      if (p.getDb().equalsIgnoreCase(id))
        return p; 
    } 
    return null;
  }
  
  public Poly getPoly(int i) {
    for (Poly p : this.list) {
      if (p.getId() == i)
        return p; 
    } 
    return null;
  }
  
  public Poly getTemplate(int gfx) {
    for (Poly p : this.list) {
      if (p.getPolyid() == gfx)
        return p; 
    } 
    return null;
  }
  
  public int getSize() {
    return this.list.size();
  }
  
  public void polyEquipped(L1Object temp, Poly poly) {
    if (temp instanceof PcInstance) {
      PcInstance cha = (PcInstance)temp;
      for (int i = 0; i < 13; i++) {
        ItemInstance slot = cha.getInventory().getSlot(i);
        if (slot != null && slot.isEquipped() && !polyEquipped((Character)cha, slot, poly)) {
          slot.absoluteEquipped((Character)cha, false);
          cha.getInventory().SetItemSetting(slot);
        } 
      } 
    } 
  }
  
  public boolean polyEquipped(Character cha, ItemInstance item, Poly poly) {
    if (poly == null)
      poly = getTemplate(cha.getGfx()); 
    if (poly != null)
      if (item instanceof net.world.instance.ItemWeaponInstance) {
        switch (poly.getWeapon()) {
          case 0:
            return false;
          case 1:
            return !(item.getItem().getType() != 5 && item.getItem().getType() != 8 && item.getItem().getType() != 24);
          case 2:
            return !item.getItem().isTohand();
          case 3:
            return (item.getItem().isTohand() && item.getItem().getType() != 3);
          case 4:
            return (item.getItem().getType() == 3);
          case 5:
            return (item.getItem().getType() == 4);
          case 6:
            return (item.getItem().getType() == 6);
          case 7:
            return !(item.getItem().getType() != 2 && item.getItem().getType() != 4 && item.getItem().getType() != 6 && item.getItem().getType() != 5 && 
              item.getItem().getType() != 24);
        } 
      } else if (item instanceof net.world.instance.ItemArmorInstance) {
        switch (item.getItem().getType()) {
          case 16:
            return poly.isArmor();
          case 17:
            return poly.isCloak();
          case 20:
            return poly.isGlove();
          case 12:
            return poly.isHelm();
          case 18:
            return poly.isRing();
          case 15:
            return poly.isT();
          case 21:
            return poly.isShield();
          case 23:
            return poly.isBoots();
        } 
      }  
    return true;
  }
}
