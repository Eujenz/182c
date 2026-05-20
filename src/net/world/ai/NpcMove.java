package net.world.ai;

import net.world.WorldMap;
import net.world.instance.NpcInstance;
import net.world.instance.SummonInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NpcMove {
  private static final Logger _log = LoggerFactory.getLogger(NpcMove.class);
  
  private static final byte[] HEADING_RD = new byte[] { 4, 5, 6, 7, 1, 2, 3 };
  
  private static final int[] _heading2 = new int[] { 7, 1, 2, 3, 4, 5, 6 };
  
  private static final int[] _heading3 = new int[] { 1, 2, 3, 4, 5, 6, 7 };
  
  private static final byte[] HEADING_TABLE_X = new byte[] { 0, 1, 1, 1, -1, -1, -1 };
  
  private static final byte[] HEADING_TABLE_Y = new byte[] { -1, -1, 1, 1, 1, -1 };
  
  private final NpcInstance _npc;
  
  public NpcMove(NpcInstance npc) {
    this._npc = npc;
  }
  
  public int targetReverseDirection(int tx, int ty) {
    int dir = _targetDirection(this._npc.getHeading(), this._npc.getX(), this._npc.getY(), tx, ty);
    return HEADING_RD[dir];
  }
  
  private static int _targetDirection(int h, int x, int y, int tx, int ty) {
    try {
      float dis_x = Math.abs(x - tx);
      float dis_y = Math.abs(y - ty);
      float dis = Math.max(dis_x, dis_y);
      if (dis == 0.0F)
        return h; 
      int avg_x = (int)Math.floor((dis_x / dis + 0.59F));
      int avg_y = (int)Math.floor((dis_y / dis + 0.59F));
      int dir_x = 0;
      int dir_y = 0;
      if (x < tx)
        dir_x = 1; 
      if (x > tx)
        dir_x = -1; 
      if (y < ty)
        dir_y = 1; 
      if (y > ty)
        dir_y = -1; 
      if (avg_x == 0)
        dir_x = 0; 
      if (avg_y == 0)
        dir_y = 0; 
      switch (dir_x) {
        case -1:
          switch (dir_y) {
            case -1:
              return 7;
            case 0:
              return 6;
            case 1:
              return 5;
          } 
          break;
        case 0:
          switch (dir_y) {
            case -1:
              return 0;
            case 1:
              return 4;
          } 
          break;
        case 1:
          switch (dir_y) {
            case -1:
              return 1;
            case 0:
              return 2;
            case 1:
              return 3;
          } 
          break;
      } 
    } catch (Exception e) {
      _log.error(e.getLocalizedMessage(), e);
    } 
    return h;
  }
  
  public int checkObject(int h) {
    if (h >= 0 && h <= 7) {
      int x = this._npc.getX();
      int y = this._npc.getY();
      int h2 = _heading2[h];
      int h3 = _heading3[h];
      if (WorldMap.getInstance().IsThroughObject(x, y, this._npc.getMap(), h))
        return h; 
      if (WorldMap.getInstance().IsThroughObject(x, y, this._npc.getMap(), h2))
        return h2; 
      if (WorldMap.getInstance().IsThroughObject(x, y, this._npc.getMap(), h3))
        return h3; 
    } 
    return -1;
  }
  
  public void setDirectionMove(int dir) {
    if (dir >= 0) {
      int locx = this._npc.getX();
      int locy = this._npc.getY();
      locx += HEADING_TABLE_X[dir];
      locy += HEADING_TABLE_Y[dir];
      if (this._npc instanceof SummonInstance) {
        SummonInstance sum = (SummonInstance)this._npc;
        sum.StartMove(locx, locy);
      } 
    } 
  }
}
