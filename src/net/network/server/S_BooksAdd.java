package net.network.server;

import net.util.Util;
import net.world.instance.books.bean.Book;

public class S_BooksAdd extends S_BasePacket {
  private String log_loc;
  
  private String log_map;
  
  private String log_id;
  
  public S_BooksAdd(Book b) {
    this.log_loc = b.getLocation();
    this.log_map = String.valueOf(b.getLocMAP());
    this.log_id = String.valueOf(b.getBook_id());
    writeC(48);
    writeS(b.getLocation());
    writeH(b.getLocMAP());
    writeD(b.getBook_id());
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    try {
      sb.append(this.log_loc);
      sb.append(" , ");
      sb.append(this.log_map);
      sb.append(" , ");
      sb.append(this.log_id);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
