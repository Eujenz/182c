package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.world.function.MonthCardsSystem;
import net.world.instance.ItemInstance;
import net.world.object.Character;

public final class MonthCards extends ItemInstance {
  public MonthCards(Item item) {
    super(item);
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    String account = cha.getAccount();
    if (MonthCardsSystem.getInstance().updateTimeOfMonthCards(cha, account)) {
      setCount(cha, getCount() - 1L);
    } else {
      cha.Message("充值失敗。");
    } 
  }
}
