package net.check;

import java.util.EnumMap;
import net.Config;
import net.database.SprTable;
import net.network.server.S_BasePacket;
import net.network.server.S_ObjectAdd;
import net.network.server.S_ObjectPoison;
import net.world.instance.PcInstance;
import net.world.object.L1Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CheckSpeed {
  private static final Logger log = LoggerFactory.getLogger(CheckSpeed.class);
  
  private final PcInstance _pc;
  
  private int _injusticeCount;
  
  private int _justiceCount;
  
  private static final int INJUSTICE_COUNT_LIMIT = 10;
  
  private static final int JUSTICE_COUNT_LIMIT = 4;
  
  private static final double HASTE_RATE = 0.75D;
  
  private final EnumMap<ACT_TYPE, Long> _actTimers = new EnumMap<ACT_TYPE, Long>(ACT_TYPE.class);
  
  private final EnumMap<ACT_TYPE, Long> _checkTimers = new EnumMap<ACT_TYPE, Long>(ACT_TYPE.class);
  
  private static final String CHECK_LOG = "(2號)检测到加速: 角色[%s]  外形GFX[%s]   账号[%s]  IP[%s] 类型[%s]";
  
  public static final int R_OK = 0;
  
  public static final int R_DETECTED = 1;
  
  public static final int R_DISPOSED = 2;
  
  public enum ACT_TYPE {
    MOVE, ATTACK, SPELL_DIR, SPELL_NODIR;
  }
  
  public CheckSpeed(PcInstance pc) {
    this._pc = pc;
    this._injusticeCount = 0;
    this._justiceCount = 0;
    long now = System.currentTimeMillis();
    byte b;
    int i;
    ACT_TYPE[] arrayOfACT_TYPE;
    for (i = (arrayOfACT_TYPE = ACT_TYPE.values()).length, b = 0; b < i; ) {
      ACT_TYPE each = arrayOfACT_TYPE[b];
      this._actTimers.put(each, Long.valueOf(now));
      this._checkTimers.put(each, Long.valueOf(now));
      b++;
    } 
  }
  
  public int checkInterval(ACT_TYPE type) {
    int result = 0;
    long now = System.currentTimeMillis();
    long interval = now - ((Long)this._actTimers.get(type)).longValue();
    int rightInterval = getRightInterval(type);
    interval = (long)(interval * (Config.CHECK_STRICTNESS - 5) / 100.0D);
    if (0L < interval && interval < rightInterval) {
      this._injusticeCount++;
      this._justiceCount = 0;
      if (this._injusticeCount >= 10) {
        doPunishment(type, Config.PUNISHMENT);
        return 2;
      } 
      result = 1;
    } else if (interval >= rightInterval) {
      this._justiceCount++;
      if (this._justiceCount >= 4) {
        this._injusticeCount = 0;
        this._justiceCount = 0;
      } 
    } 
    this._actTimers.put(type, Long.valueOf(now));
    return result;
  }
  
  private int getRightInterval(ACT_TYPE type) {
    int interval = 0;
    switch (type) {
      case MOVE:
        interval = SprTable.getInstance().getMoveSpeed(this._pc.getGfx(), this._pc.getGfxMode());
        break;
      case SPELL_DIR:
        interval = SprTable.getInstance().getDirSpellSpeed(this._pc.getGfx(), 18);
        break;
      case SPELL_NODIR:
        interval = SprTable.getInstance().getNodirSpellSpeed(this._pc.getGfx(), 19);
        break;
      default:
        return interval = SprTable.getInstance().getAttackSpeed(this._pc.getGfx(), this._pc.getGfxMode() + 1);
    } 
    if (this._pc.isSpeed())
      interval = (int)(interval * 0.75D); 
    if (this._pc.isSlow())
      interval = (int)(interval / 0.75D); 
    if (this._pc.isBrave())
      interval = (int)(interval * 0.75D); 
    return interval;
  }
  
  private void doPunishment(ACT_TYPE type, int punishmaent) {
    if (!this._pc.isGm()) {
      int x, y, map;
      String typeMsg = "";
      switch (type) {
        case MOVE:
          typeMsg = "移动";
          break;
        case SPELL_DIR:
          typeMsg = "攻击魔法";
          break;
        case SPELL_NODIR:
          typeMsg = "辅助魔法";
          break;
        default:
            typeMsg = "攻击";
            break;
      } 
      if (Config.SPEED_MESSAGE)
        this._pc.Message("檢測到加速。"); 
      if (Config.DEBUG_SPEED_PRINT)
        System.out.println(String.format("(2號)检测到加速: 角色[%s]  外形GFX[%s]   账号[%s]  IP[%s] 类型[%s]", new Object[] { this._pc.getName(), Integer.valueOf(this._pc.getGfx()), this._pc.getClient().getID(), this._pc.getClient().getIP(), typeMsg })); 
      log.info(String.format("(2號)检测到加速: 角色[%s]  外形GFX[%s]   账号[%s]  IP[%s] 类型[%s]", new Object[] { this._pc.getName(), Integer.valueOf(this._pc.getGfx()), this._pc.getClient().getID(), this._pc.getClient().getIP(), typeMsg }));
      switch (punishmaent) {
        case 0:
          freeze(this._pc);
          break;
        case 1:
          if (!this._pc.isSpeedStatusTeleport()) {
            this._pc.setTempX(this._pc.getX());
            this._pc.setTempY(this._pc.getY());
            this._pc.setTempMap(this._pc.getMap());
            this._pc.setSpeedStatusTeleport(true);
          } 
          x = this._pc.getTempX();
          y = this._pc.getTempY();
          map = this._pc.getTempMap();
          this._pc.toTeleport(x, y, map);
          break;
        case 2:
          this._pc.getClient().close();
          break;
      } 
    } else {
      this._pc.Message("GM檢測加速中。");
      this._injusticeCount = 0;
    } 
  }
  
  private void freeze(PcInstance pc) {
    if (pc.isLockFreeze())
      return; 
    this._pc.Freeze = new L1Object();
    this._pc.Freeze.setObjectId(Config.getObjectID_ETC());
    this._pc.Freeze.setGfx(176);
    this._pc.Freeze.toTeleport(this._pc.getX(), this._pc.getY(), this._pc.getMap());
    this._pc.SendPacket((S_BasePacket)new S_ObjectAdd(this._pc.Freeze), true);
    this._pc.setLock(true);
    this._pc.setLockFreeze(true);
    this._pc.setLockFreezeObject(this._pc.Freeze);
    this._pc.SendPacket((S_BasePacket)new S_ObjectPoison(this._pc.getObjectId(), this._pc.isPoison(), this._pc.isLock()), true);
    this._pc.SendPacket((S_BasePacket)new S_ObjectPoison(12));
    this._pc.setTempX(this._pc.getX());
    this._pc.setTempY(this._pc.getY());
  }
}
