package net;

import java.util.Scanner;
import net.database.AccountTable;
import net.network.server.S_BasePacket;
import net.network.server.S_CloseClient;
import net.world.WorldInstance;
import net.world.instance.PcInstance;
import net.world.time.KingdomUpdateTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConsoleProcess extends Thread {
  final Logger log = LoggerFactory.getLogger(ConsoleProcess.class);
  
  private final Scanner userInput = new Scanner(System.in);
  
  private boolean onStarup = true;
  
  private static final boolean STILL_RUN = true;
  
  public ConsoleProcess() {
    System.out.println("→提示: 互动指令听取中...\n>");
  }
  
  private void execute(String cmd) {
    if ("restart".equalsIgnoreCase(cmd)) {
      shutdown();
    } else {
      System.out.println("错误, 请输入CMD指令.");
    } 
  }
  
  public void run() {
    while (this.onStarup) {
      String action = this.userInput.nextLine();
      String[] word = action.split(" ");
      if (word.length == 1)
        execute(word[0]); 
    } 
    System.out.println("→提示: 互动指令听取中...\n>");
  }
  
  private void shutdown() {
    try {
      try {
        savePlayer();
      } catch (Exception e) {
        this.log.error("保用戶信息異常： " + e.toString());
      } 
      try {
        Config.save();
      } catch (Exception e) {
        this.log.error("保存設置異常： " + e.toString());
      } 
      try {
        KingdomUpdateTimer.getInstance().save();
      } catch (Exception e) {
        this.log.error("保存世界異常： " + e.toString());
      } 
      this.log.info("手动关闭服务器。");
      System.out.println("资料保存完毕，服务器开始重启。");
      System.exit(0);
    } catch (Exception e) {
      this.log.error(e.getLocalizedMessage(), e);
    } 
  }
  
  private void savePlayer() {
    byte b;
    int i;
    PcInstance[] arrayOfPcInstance;
    for (i = (arrayOfPcInstance = WorldInstance.getInstance().getPc()).length, b = 0; b < i; ) {
      PcInstance pc = arrayOfPcInstance[b];
      if (pc != null) {
        pc.toSave(false);
        pc.Message("\\fV服务器由管理员手动重启。");
        pc.SendPacket((S_BasePacket)new S_CloseClient(0));
        AccountTable.getInstance().LoginsOut(pc.getClient());
      } 
      b++;
    } 
  }
}
