package net.network.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.database.DatabaseConnection;
import net.util.Util;
import net.world.instance.BoardInstance;

public class S_BoardRead extends S_BasePacket {
  private String log_boardtype;
  
  private String log_idx;
  
  private String log_name;
  
  private String log_subject;
  
  private String log_date;
  
  private String log_content;
  
  public S_BoardRead(BoardInstance board, int idx) {
    writeC(96);
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("SELECT name, days, subject, memo FROM board WHERE type=? AND id=?");
      st.setInt(1, board.getBoardType());
      st.setInt(2, idx);
      rs = st.executeQuery();
      if (rs.next()) {
        this.log_boardtype = String.valueOf(board.getBoardType());
        this.log_idx = String.valueOf(idx);
        this.log_name = rs.getString(1);
        this.log_subject = rs.getString(3);
        this.log_date = rs.getString(2);
        this.log_content = rs.getString(4);
        writeD(idx);
        writeS(rs.getString(1));
        writeS(rs.getString(3));
        writeS(rs.getString(2));
        writeS(rs.getString(4));
      } 
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
    sb.append(" :: ");
    try {
      sb.append(this.log_boardtype);
      sb.append(" , ");
      sb.append(this.log_idx);
      sb.append(" , ");
      sb.append(this.log_name);
      sb.append(" , ");
      sb.append(this.log_subject);
      sb.append(" , ");
      sb.append(this.log_date);
      sb.append(" , ");
      sb.append(this.log_content);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
