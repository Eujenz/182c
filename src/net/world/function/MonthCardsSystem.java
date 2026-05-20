package net.world.function;

import net.database.AccountTable;
import net.world.WorldInstance;
import net.world.instance.ItemInstance;
import net.world.instance.PcInstance;
import net.world.object.Character;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MonthCardsSystem {
  private final Logger log = LoggerFactory.getLogger(MonthCardsSystem.class);
  
  private static class Holder {
    static MonthCardsSystem instance = new MonthCardsSystem();
  }
  
  public static MonthCardsSystem getInstance() {
    return Holder.instance;
  }
  
  public boolean updateTimeOfMonthCards(Character cha, String account) {
    long nowTime = System.currentTimeMillis() / 1000L;
    boolean flag = false;
    if (AccountTable.getInstance().select_id(account)) {
      AccountTable.getInstance().updateMonthCards(account, (nowTime + 2678400L) * 1000L);
      AccountTable.getInstance().put(account, nowTime + 2678400L);
      String time = AccountTable.getInstance().getMonthCards(account);
      cha.Message("充值成功。到期時間為：" + time);
      this.log.info(String.valueOf(cha.getName()) + " 为自己的帐号充了一张月卡。到期时间为：" + time);
      flag = true;
    } else {
      cha.Message("該帳號不存在，請核對後再試。");
    } 
    return flag;
  }
  
  public void updateTimeOfMonthCardsOther(PcInstance pc, String account) {
    long nowTime = System.currentTimeMillis() / 1000L;
    if (!pc.getInventory().isItem(356)) {
      pc.Message("你身上並沒有月卡。");
      return;
    } 
    AccountTable.getInstance().updateMonthCards(account, (nowTime + 2678400L) * 1000L);
    AccountTable.getInstance().put(account, nowTime + 2678400L);
    String time = AccountTable.getInstance().getMonthCards(account);
    pc.Message("充值成功。帳號為：" + account + " 到期時間為：" + time);
    PcInstance tgpc = WorldInstance.getInstance().getOnlinePcOfAccount(account);
    if (tgpc != null)
      tgpc.Message(String.valueOf(pc.getName()) + " 為你充了一張月卡 到期時間為：" + time); 
    String acc = "";
    if (account.equalsIgnoreCase(pc.getAccount())) {
      acc = "自己的";
    } else {
      acc = "其他的";
    } 
    this.log.info(String.valueOf(pc.getName()) + " 为" + acc + "帐号(" + account + ")充了一张个人月卡。到期时间为：" + time);
    byte b;
    int i;
    ItemInstance[] arrayOfItemInstance;
    for (i = (arrayOfItemInstance = pc.getInventory().getAll()).length, b = 0; b < i; ) {
      ItemInstance item = arrayOfItemInstance[b];
      if (item.getItem().getItemId() == 356)
        item.setCount((Character)pc, item.getCount() - 1L); 
      b++;
    } 
  }
}
