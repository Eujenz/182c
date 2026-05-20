package net.world.instance;

import net.database.DatabaseConnection;
import net.network.server.S_BasePacket;
import net.network.server.S_BoardList;
import net.network.server.S_BoardRead;
import net.network.server.S_ServerMessage;
import net.util.Util;
import net.world.object.L1Object;

public class BoardInstance extends L1Object {
  private int BOARDTYPE;
  
  public void toClick(L1Object o) {
    o.board_idx = 0;
    o.SendPacket((S_BasePacket)new S_BoardList(this, o));
  }
  
  public void toTeleport(int x, int y, int map) {
    super.toTeleport(x, y, map);
    if (x == 32578 && y == 32941 && map == 0)
      this.BOARDTYPE = 0; 
    if (x == 33421 && y == 32806 && map == 4)
      this.BOARDTYPE = 1; 
    if (x == 33584 && y == 33266 && map == 4)
      this.BOARDTYPE = 2; 
  }
  
  public int getBoardType() {
    return this.BOARDTYPE;
  }
  
  public void WriteDB(PcInstance pc, String subject, String contents) {
    if (pc.getInventory().Aden(300L, true)) {
      StringBuffer sb = new StringBuffer();
      sb.append("INSERT INTO board SET type='");
      sb.append(this.BOARDTYPE);
      sb.append("', name='");
      sb.append(pc.getName());
      sb.append("', days='");
      sb.append(Util.YearMonthDate(false));
      sb.append("', subject='");
      sb.append(subject);
      sb.append("', memo='");
      sb.append(contents);
      sb.append("'");
      DatabaseConnection.getInstance().query_insert(sb.toString());
    } else {
      pc.SendPacket((S_BasePacket)new S_ServerMessage(189));
    } 
  }
  
  public void ReadDB(PcInstance pc, int uid) {
    pc.SendPacket((S_BasePacket)new S_BoardRead(this, uid));
  }
  
  public void NextDB(PcInstance pc, int uid) {
    pc.board_idx++;
    pc.SendPacket((S_BasePacket)new S_BoardList(this, (L1Object)pc));
  }
  
  public void DeleteDB(PcInstance pc, int uid) {
    StringBuffer sb = new StringBuffer();
    sb.append("DELETE FROM board WHERE type='");
    sb.append(this.BOARDTYPE);
    sb.append("' AND id='");
    sb.append(uid);
    sb.append("' AND name='");
    sb.append(pc.getName());
    sb.append("'");
    DatabaseConnection.getInstance().query_delete(sb.toString());
    pc.board_idx = -1;
  }
}
