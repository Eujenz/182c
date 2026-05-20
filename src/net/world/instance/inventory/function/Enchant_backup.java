package net.world.instance.inventory.function;

import net.Config;
import net.database.bean.Item;
import net.network.server.S_BasePacket;
import net.network.server.S_InventoryBress;
import net.network.server.S_InventoryStatus;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;

public class Enchant_backup extends ItemInstance {
  protected short rnd = 0;
  
  private static int[] chance_normal = new int[] { 
      10000, 10000, 10000, 10000, 3000, 1500, 700, 300, 100, 50, 
      20, 10, 5, 2, 1 };
  
  private static int[] chance_normal_bon = new int[] { 
      3000, 1500, 700, 300, 100, 50, 20, 10, 5, 2, 
      1 };
  
  private static int[] chance_bless = new int[] { 10000, 5000, 5000, 5000, 3000, 1500, 700, 300, 100 };
  
  public Enchant_backup(Item _item) {
    super(_item);
  }
  
  private boolean isEn(ItemInstance item) {
    return (item.getEnLevel() >= item.getItem().get_safenchant() && getBless() != 2);
  }
  
  private boolean isEnChance(ItemInstance item) {
    if (item.getItem().get_material() == 9) {
      if (chance_normal_bon.length < item.getEnLevel())
        return (Util.rand(0, 10000) <= chance_normal_bon[chance_normal_bon.length - 1] * Config.RATE_EN); 
      return (Util.rand(0, 10000) <= chance_normal_bon[item.getEnLevel()] * Config.RATE_EN);
    } 
    if (chance_normal.length < item.getEnLevel())
      return (Util.rand(0, 10000) <= chance_normal[chance_normal.length - 1] * Config.RATE_EN); 
    return (Util.rand(0, 10000) <= chance_normal[item.getEnLevel()] * Config.RATE_EN);
  }
  
  private boolean isEnBonusChance(ItemInstance item) {
    if (item.getItem().get_material() == 9)
      return false; 
    if (chance_bless.length < item.getEnLevel())
      return (Util.rand(0, 10000) <= chance_bless[chance_bless.length - 1] * Config.RATE_EN); 
    return (Util.rand(0, 10000) <= chance_bless[item.getEnLevel()] * Config.RATE_EN);
  }
  
  public boolean enchant(PcInstance pc, ItemInstance temp) {
    String[] EnMsg = new String[3];
    boolean r = true;
    if (isEn(temp)) {
      if (isEnChance(temp)) {
        switch (getBless()) {
          case 0:
            EnMsg[0] = temp.toString();
            if (isEnBonusChance(temp)) {
              EnMsg[2] = "$248";
              this.rnd = 2;
              break;
            } 
            EnMsg[2] = "$247";
            this.rnd = 1;
            break;
          case 1:
            EnMsg[0] = temp.toString();
            this.rnd = 1;
            EnMsg[2] = "$247";
            break;
        } 
        if (temp instanceof net.world.instance.ItemWeaponInstance) {
          EnMsg[1] = "$245";
        } else {
          EnMsg[1] = "$252";
        } 
        temp.setEnLevel((short)(temp.getEnLevel() + this.rnd));
        pc.SendPacket((S_BasePacket)new S_ServerMessage(161, EnMsg));
        pc.SendPacket((S_BasePacket)new S_InventoryStatus(temp));
      } else {
        EnMsg = new String[2];
        EnMsg[0] = temp.toString();
        EnMsg[1] = "붉게";
        pc.SendPacket((S_BasePacket)new S_ServerMessage(164, EnMsg));
        r = false;
      } 
    } else {
      switch (getBless()) {
        case 0:
          EnMsg[0] = temp.toString();
          if (temp instanceof net.world.instance.ItemWeaponInstance) {
            EnMsg[1] = "$245";
          } else {
            EnMsg[1] = "$252";
          } 
          if (isEnBonusChance(temp)) {
            EnMsg[2] = "$248";
            this.rnd = 2;
            break;
          } 
          EnMsg[2] = "$247";
          this.rnd = 1;
          break;
        case 1:
          this.rnd = 1;
          if (temp instanceof net.world.instance.ItemWeaponInstance) {
            EnMsg[1] = "$245";
          } else {
            EnMsg[1] = "$252";
          } 
          EnMsg[2] = "$247";
          EnMsg[0] = temp.toString();
          break;
        case 2:
          this.rnd = -1;
          EnMsg[1] = "$246";
          EnMsg[2] = "$247";
          EnMsg[0] = temp.toString();
          break;
      } 
      temp.setEnLevel((short)(temp.getEnLevel() + this.rnd));
      pc.SendPacket((S_BasePacket)new S_ServerMessage(161, EnMsg));
      pc.SendPacket((S_BasePacket)new S_InventoryStatus(temp));
    } 
    if (r && getBless() == 0 && temp.getBless() != 0 && 
      Util.rand(0, 10000) <= 10) {
      temp.setBless(0);
      pc.SendPacket((S_BasePacket)new S_InventoryBress(temp));
    } 
    return r;
  }
}
