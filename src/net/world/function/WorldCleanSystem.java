package net.world.function;

import net.Config;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectChatting;
import net.world.WorldInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorldCleanSystem implements Runnable {
  final Logger log = LoggerFactory.getLogger(WorldCleanSystem.class);
  
  private static class Holder {
    static WorldCleanSystem instance = new WorldCleanSystem();
  }
  
  public static WorldCleanSystem getInstance() {
    return Holder.instance;
  }
  
  public void initialize() {
    (new Thread(getInstance())).start();
  }
  
  public void run() {
    this.log.debug("开始运行地面清理线程...");
    int time = Config.ALT_ITEM_DELETION_TIME * 60 * 1000 - 10000;
    while (true) {
      try {
        Thread.sleep(time);
      } catch (Exception exception) {
        this.log.warn("L1DeleteItemOnGround error: " + exception);
        break;
      } 
      WorldInstance.getInstance().SendPacket((S_BasePacket)new S_ObjectChatting(null, "\\fU->>30秒后自动清理地面...", 20));
      this.log.debug("30秒后自动清理地面");
      try {
        Thread.sleep(20000L);
      } catch (Exception exception) {
        this.log.warn("L1DeleteItemOnGround error: " + exception);
        break;
      } 
      WorldInstance.getInstance().SendPacket((S_BasePacket)new S_ObjectChatting(null, "\\fU->>10秒后自动清理地面...", 20));
      this.log.debug("10秒后自动清理地面");
      try {
        Thread.sleep(10000L);
      } catch (Exception exception) {
        this.log.warn("L1DeleteItemOnGround error: " + exception);
        break;
      } 
      WorldInstance.getInstance().CleanWorldItems();
      WorldInstance.getInstance().SendPacket((S_BasePacket)new S_ObjectChatting(null, "\\fU->>地面清理完成...", 20));
      this.log.debug("地面清理完成。");
    } 
  }
  
  private WorldCleanSystem() {}
}
