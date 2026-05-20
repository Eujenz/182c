package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.object.Character;

public final class MonthCardsOther extends ItemInstance {
  public MonthCardsOther(Item item) {
    super(item);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    if (cha instanceof PcInstance) {
      PcInstance pc = (PcInstance)cha;
      pc.setMonthCardsStat(true);
      pc.Message("請輸入要充值的帳號名。");
    } 
  }
}
