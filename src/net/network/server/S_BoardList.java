package net.network.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.database.DatabaseConnection;
import net.util.Util;
import net.world.instance.BoardInstance;
import net.world.object.L1Object;

public class S_BoardList extends S_BasePacket {
  public S_BoardList(BoardInstance board, L1Object o) {
    writeC(95);
    writeD(board.getObjectId());
    writeC(255);
    writeC(255);
    writeC(255);
    writeC(127);
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT id, name, days, subject FROM board WHERE type=? ORDER BY id DESC LIMIT ?,8");
      st.setInt(1, board.getBoardType());
      st.setInt(2, 8 * o.board_idx);
      rs = st.executeQuery();
      rs.last();
      int count = rs.getRow();
      rs.first();
      writeH(count);
      writeH(300);
      do {
        writeD(rs.getInt(1));
        writeS(rs.getString(2));
        writeS(rs.getString(3));
        writeS(rs.getString(4));
      } while (rs.next());
    } catch (Exception exception) {
    
    } finally {
      DatabaseConnection.getInstance().close(con, st, rs);
    } 
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    return sb.toString();
  }
}
