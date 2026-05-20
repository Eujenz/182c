package net.world.instance.inventory.function;

import net.Config;
import net.database.bean.Item;
import net.network.server.S_BasePacket;
import net.network.server.S_InventoryStatus;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Enchant extends ItemInstance {
  final Logger log = LoggerFactory.getLogger(Enchant.class);
  
  protected short rnd = 1;
  
  static final String ENCHANT_LOG = "[%s] 強化{%s} 由 %s 强化为 %s 物品ID：%s 結果[%s]";
  
  public Enchant(Item _item) {
    super(_item);
  }
  
  private boolean isChance(ItemInstance item) {
    return (item.getEnLevel() >= item.getItem().get_safenchant() && getBless() != 2);
  }
  
  public boolean enchant(PcInstance pc, ItemInstance temp) {
    boolean chance = isChance(temp);
    boolean enchant = false;
    boolean double_enchant = false;
    int enchant_chance = 0;
    String[] EnMsg = new String[3];
    EnMsg[0] = temp.toString();
    EnMsg[2] = "$247";
    this.rnd = 1;
    if (chance) {
      if (temp instanceof net.world.instance.ItemWeaponInstance) {
        EnMsg[1] = "$245";
        enchant_chance = 30;
        for (int i = 6; i < temp.getEnLevel(); i++)
          enchant_chance /= 2; 
        if (getBless() == 0 && 
          temp.getEnLevel() < 8)
          double_enchant = (Util.rand(0, 50) >= Util.rand(0, 100)); 
      } else {
        EnMsg[1] = "$252";
        if (temp.getItem().get_name().indexOf("精灵") > -1) {
          enchant_chance = 15;
          for (int i = 6; i < temp.getEnLevel(); i++)
            enchant_chance /= 2; 
          if (getBless() == 0 && 
            temp.getEnLevel() < 9)
            double_enchant = (Util.rand(0, 50) >= Util.rand(0, 100)); 
        } else if (temp.getItem().get_material() == 9) {
          enchant_chance = 30;
          for (int i = 0; i < temp.getEnLevel(); i++)
            enchant_chance /= 2; 
        } else {
          enchant_chance = 30;
          for (int i = 4; i < temp.getEnLevel(); i++)
            enchant_chance /= 2; 
          if (getBless() == 0 && 
            temp.getEnLevel() < 7)
            double_enchant = (Util.rand(0, 50) >= Util.rand(0, 100)); 
        } 
      } 
      if (temp.getEnLevel() >= 0) {
        enchant = (Util.rand(0, enchant_chance * Config.RATE_EN) >= Util.rand(0, 100));
      } else {
        enchant = true;
      } 
    } else {
      enchant = true;
      if (temp instanceof net.world.instance.ItemWeaponInstance) {
        EnMsg[1] = "$245";
      } else {
        EnMsg[1] = "$252";
      } 
      if (getBless() == 0) {
        double_enchant = (Util.rand(0, 50) >= Util.rand(0, 100));
      } else if (getBless() == 2) {
        EnMsg[1] = "$246";
        this.rnd = -1;
      } 
    } 
    if (double_enchant) {
      EnMsg[2] = "$248";
      this.rnd = 2;
    } 
    int oldEnLevel = temp.getEnLevel();
    if (enchant) {
      temp.setEnLevel((short)(temp.getEnLevel() + this.rnd));
      pc.SendPacket((S_BasePacket)new S_InventoryStatus(temp));
      pc.SendPacket((S_BasePacket)new S_ServerMessage(161, EnMsg));
    } else {
      this.rnd = 0;
      EnMsg = new String[2];
      EnMsg[0] = temp.toString();
      EnMsg[1] = " 红色的光茫 ";
      pc.SendPacket((S_BasePacket)new S_ServerMessage(164, EnMsg));
    } 
    this.log.info(String.format("[%s] 強化{%s} 由 %s 强化为 %s 物品ID：%s 結果[%s]", new Object[] { pc.getName(), temp.getItem().get_name(), Integer.valueOf(oldEnLevel), Integer.valueOf(temp.getEnLevel()), Integer.valueOf(temp.getObjectId()), Boolean.valueOf(enchant) }));
    return enchant;
  }
}
