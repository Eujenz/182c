package net.util;

import net.Config;
import net.world.instance.PcInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpeedHackChecker {
  final Logger log = LoggerFactory.getLogger(SpeedHackChecker.class);
  
  int arraySize;
  
  int _frame;
  
  int idx;
  
  int type;
  
  int _threshold = 0;
  
  int _threshold_2 = 0;
  
  int threshold;
  
  PcInstance pc;
  
  long[] time = null;
  
  StringBuilder sb;
  
  private String CHECK_LOG = "角色[%s] 变身号[%s] 基础[%s] 实际[%.0f] 相差比例[%.2f] 账号[%s] IP[%s] 次数[%s]";
  
  String PUN_1 = "角色[%s] 变身号[%s] 账号[%s] IP[%s] 次数[%s] 【瞬移处罚】";
  
  String PUN_2 = "角色[%s] 变身号[%s] 账号[%s] IP[%s] 【断线处罚】";
  
  public SpeedHackChecker(PcInstance pc, int arraySize, int type, int threshold) {
    if (arraySize < 2)
      arraySize = 2; 
    this.pc = pc;
    this.arraySize = arraySize;
    this._frame = 0;
    this.type = type;
    this.threshold = threshold;
    this.time = new long[arraySize];
    this.idx = 0;
    String typeStr = "";
    switch (type) {
      case 0:
        typeStr = "<攻击>";
        break;
      case 1:
        typeStr = "<行走>";
        break;
      case 2:
        typeStr = "<魔法>";
        break;
    } 
    this.CHECK_LOG = String.valueOf(typeStr) + this.CHECK_LOG;
    this.PUN_1 = String.valueOf(typeStr) + this.PUN_1;
    this.PUN_2 = String.valueOf(typeStr) + this.PUN_2;
  }
  
  public void check() {
    int frame = 0;
    switch (this.type) {
      case 0:
        frame = getAttSpeed();
        break;
      case 1:
        frame = getMoveSpeed();
        break;
      case 2:
        frame = 200;
        break;
    } 
    if (frame != this._frame) {
      this.time = new long[this.arraySize];
      this.idx = 0;
      this.time[this.idx] = System.currentTimeMillis();
      this._frame = frame;
      return;
    } 
    if (this.idx == this.arraySize - 1) {
      for (int i = 0; i < this.arraySize - 1; i++)
        this.time[i] = this.time[i + 1]; 
      this.time[this.idx] = System.currentTimeMillis();
      long total = this.time[this.idx] - this.time[0];
      long frameCount = (frame * (this.idx + 1));
      double tmpFrame = (total / (this.idx + 1));
      double rate = frame / tmpFrame;
      if (Config.DEBUG_SPEED_PRINT)
        System.out.println(String.format(this.CHECK_LOG, new Object[] { this.pc.getName(), Integer.valueOf(this.pc.getGfx()), Integer.valueOf(frame), Double.valueOf(tmpFrame), Double.valueOf(rate), this.pc.getClient().getID(), this.pc.getClient().getIP(), Integer.valueOf(this._threshold) })); 
      if (total < frameCount) {
        this._threshold++;
        if (rate >= Config.SPEED_2 || rate == 0.0D)
          this._threshold_2++; 
        this.log.info(String.format(this.CHECK_LOG, new Object[] { this.pc.getName(), Integer.valueOf(this.pc.getGfx()), Integer.valueOf(frame), Double.valueOf(tmpFrame), Double.valueOf(rate), this.pc.getClient().getID(), this.pc.getClient().getIP(), Integer.valueOf(this._threshold) }));
        if ((rate >= Config.SPEED_2 || rate == 0.0D) && this._threshold_2 > Config.SPEED_HACK) {
          this.pc.Message("惡意加速");
          this.log.info(String.format(this.PUN_2, new Object[] { this.pc.getName(), Integer.valueOf(this.pc.getGfx()), this.pc.getClient().getID(), this.pc.getClient().getIP() }));
          if (!this.pc.isGm() && Config.SPEED_2_PUN)
            this.pc.getClient().close(); 
        } else if ((this._threshold > this.threshold || rate > Config.SPEED_1) && 
          Config.SPEED_1_PUN) {
          this.pc.Message("檢測到多次加速或者網絡延遲");
          this.log.info(String.format(this.PUN_1, new Object[] { this.pc.getName(), Integer.valueOf(this.pc.getGfx()), this.pc.getClient().getID(), this.pc.getClient().getIP(), Integer.valueOf(this._threshold) }));
          this.pc.toTeleport(this.pc.getX(), this.pc.getY(), this.pc.getMap());
        } 
      } else {
        this._threshold = (this._threshold > 0) ? --this._threshold : 0;
        this._threshold_2 = (this._threshold_2 > 0) ? --this._threshold_2 : 0;
      } 
    } else {
      if (this.idx + 1 < this.arraySize)
        this.idx++; 
      this.time[this.idx] = System.currentTimeMillis();
    } 
  }
  
  public int getAttSpeed() {
    int frame = 200;
    switch (this.pc.getGfx()) {
      case 48:
        frame = 360;
        return frame;
      case 61:
        frame = 340;
        return frame;
      case 0:
        frame = 390;
        return frame;
      case 1:
        frame = 370;
        return frame;
      case 138:
        frame = 310;
        return frame;
      case 37:
        frame = 290;
        return frame;
      case 734:
      case 1186:
        frame = 430;
        return frame;
      case 1110:
        frame = 325;
        return frame;
      case 1108:
        frame = 340;
        return frame;
      case 784:
        frame = 400;
        return frame;
      case 786:
        frame = 400;
        return frame;
      case 788:
        frame = 450;
        return frame;
      case 30:
        frame = 355;
        return frame;
      case 1106:
        frame = 340;
        return frame;
      case 152:
        frame = 325;
        return frame;
      case 951:
        frame = 400;
        return frame;
      case 1047:
        frame = 310;
        return frame;
      case 894:
        frame = 325;
        return frame;
      case 1052:
        frame = 400;
        return frame;
      case 1128:
        frame = 450;
        return frame;
      case 173:
      case 183:
      case 185:
      case 187:
        frame = 575;
        return frame;
      case 1125:
        frame = 745;
        return frame;
      case 934:
        frame = 280;
        return frame;
      case 979:
        frame = 310;
        return frame;
      case 240:
        frame = 230;
        return frame;
      case 929:
        frame = 560;
        return frame;
      case 1180:
        frame = 370;
        return frame;
      case 1202:
        frame = 450;
        return frame;
      case 945:
      case 947:
        frame = 99999;
        return frame;
      case 56:
        frame = 510;
        return frame;
      case 57:
        frame = 0;
        return frame;
      case 1022:
        frame = 355;
        return frame;
      case 1059:
        frame = 420;
        return frame;
      case 54:
        frame = 575;
        return frame;
      case 94:
        frame = 0;
        return frame;
      case 49:
        frame = 1030;
        return frame;
      case 29:
        frame = 670;
        return frame;
      case 1096:
        frame = 435;
        return frame;
      case 1104:
        frame = 325;
        return frame;
      case 95:
        frame = 340;
        return frame;
      case 146:
        frame = 325;
        return frame;
      case 1011:
        frame = 325;
        return frame;
      case 96:
        frame = 510;
        return frame;
      case 931:
        frame = 340;
        return frame;
      case 936:
        frame = 370;
        return frame;
      case 938:
        frame = 310;
        return frame;
      case 53:
        frame = 325;
        return frame;
      case 52:
        frame = 400;
        return frame;
      case 144:
        frame = 280;
        return frame;
      case 145:
        frame = 355;
        return frame;
      case 255:
        frame = 250;
        return frame;
      case 1020:
        frame = 510;
        return frame;
      case 1098:
        frame = 495;
        return frame;
      case 32:
        frame = 695;
        return frame;
    } 
    frame = 230;
    return frame;
  }
  
  public int getMoveSpeed() {
    int frame = 200;
    switch (this.pc.getGfx()) {
      case 0:
      case 1:
      case 30:
      case 37:
      case 48:
      case 61:
      case 138:
      case 152:
      case 173:
      case 183:
      case 185:
      case 187:
      case 240:
      case 734:
      case 784:
      case 786:
      case 788:
      case 894:
      case 929:
      case 934:
      case 951:
      case 979:
      case 1047:
      case 1052:
      case 1106:
      case 1108:
      case 1110:
      case 1125:
      case 1128:
      case 1180:
      case 1186:
      case 1204:
        frame = 270;
        return frame;
      case 1202:
        frame = 440;
        return frame;
      case 947:
        frame = 550;
        return frame;
      case 945:
        frame = 830;
        return frame;
      case 56:
        frame = 340;
        return frame;
      case 57:
        frame = 0;
        return frame;
      case 1022:
        frame = 320;
        return frame;
      case 1059:
        frame = 550;
        return frame;
      case 54:
        frame = 240;
        return frame;
      case 94:
        frame = 0;
        return frame;
      case 49:
        frame = 550;
        return frame;
      case 29:
        frame = 420;
        return frame;
      case 1096:
        frame = 350;
        return frame;
      case 53:
      case 95:
      case 96:
      case 146:
      case 931:
      case 936:
      case 938:
      case 1011:
      case 1104:
        frame = 200;
        return frame;
      case 52:
        frame = 870;
        return frame;
      case 144:
        frame = 560;
        return frame;
      case 145:
        frame = 350;
        return frame;
      case 255:
        frame = 460;
        return frame;
      case 32:
      case 1020:
      case 1098:
        frame = 410;
        return frame;
    } 
    frame = 200;
    return frame;
  }
}
