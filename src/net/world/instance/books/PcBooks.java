package net.world.instance.books;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import net.database.DatabaseConnection;
import net.network.server.S_BasePacket;
import net.network.server.S_BooksAdd;
import net.network.server.S_ServerMessage;
import net.world.function.AgitSystem;
import net.world.instance.PcInstance;
import net.world.instance.books.bean.Book;
import net.world.object.L1Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PcBooks extends Books {
  final Logger log = LoggerFactory.getLogger(PcBooks.class);
  
  private PcInstance pc;
  
  private int[] Tmap = new int[] { 0, 3, 4 };
  
  private int[][] Tloc = new int[][] { { 33089, 33219, 32717, 32827, 4 }, { 32755, 32870, 32790, 32920, 66 }, { 34007, 34162, 33172, 33332, 4 }, { 32888, 33070, 32839, 32953, 320 }, { 33559, 33686, 32615, 32755, 4 }, { 33458, 33583, 33315, 33490, 4 }, { 32750, 32850, 32250, 32350, 4 }, { 32571, 32721, 33350, 33460, 4 } };
  
  public static final int[][] FORBID_BOOKMARK_AREA = new int[][] { { 33520, 33777, 32200, 32450, 4 } };
  
  public PcBooks(PcInstance pc) {
    this.pc = pc;
    read();
  }
  
  public void remove(String location) {
    byte b;
    int i;
    Book[] arrayOfBook;
    for (i = (arrayOfBook = getAll()).length, b = 0; b < i; ) {
      Book book = arrayOfBook[b];
      if (book.getLocation().equalsIgnoreCase(location)) {
        remove(book);
        break;
      } 
      b++;
    } 
  }
  
  public void add(String location) {
    if (addCheck() || this.pc.isGm()) {
      Book b = new Book();
      b.setBook_id(getNextID());
      b.setLocation(location);
      b.setLocX(this.pc.getX());
      b.setLocY(this.pc.getY());
      b.setLocMAP(this.pc.getMap());
      add(b);
      this.pc.SendPacket((S_BasePacket)new S_BooksAdd(b));
    } else {
      this.pc.SendPacket((S_BasePacket)new S_ServerMessage(214));
    } 
  }
  
  public void save() {
    Connection con = null;
    PreparedStatement pstm = null;
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("DELETE FROM characters_books WHERE char_id='");
      sb.append(this.pc.getObjectId());
      sb.append("'");
      DatabaseConnection.getInstance().query_delete(sb.toString());
      con = DatabaseConnection.getInstance().getConnection();
      pstm = con.prepareStatement("INSERT INTO characters_books SET book_id=?, char_id=?, location=?, loc_x=?, loc_y=?, loc_map=? ");
      Collections.sort(this.list);
      int bookId = 1;
      for (Book b : this.list) {
        pstm.setInt(1, bookId++);
        pstm.setInt(2, this.pc.getObjectId());
        pstm.setString(3, b.getLocation());
        pstm.setInt(4, b.getLocX());
        pstm.setInt(5, b.getLocY());
        pstm.setInt(6, b.getLocMAP());
        pstm.execute();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      this.log.error(e.getLocalizedMessage(), e);
    } finally {
      DatabaseConnection.getInstance().close(con, pstm);
    } 
  }
  
  public void read() {
    Connection con = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      pstm = con.prepareStatement("select * from characters_books where char_id=?");
      pstm.setInt(1, this.pc.getObjectId());
      rs = pstm.executeQuery();
      Book b = null;
      while (rs.next()) {
        b = new Book();
        b.setBook_id(rs.getInt("book_id"));
        b.setLocation(rs.getString("location"));
        b.setLocX(rs.getInt("loc_x"));
        b.setLocY(rs.getInt("loc_y"));
        b.setLocMAP(rs.getInt("loc_map"));
        add(b);
      } 
    } catch (Exception localException) {
      localException.printStackTrace();
      this.log.error(localException.getLocalizedMessage(), localException);
    } finally {
      DatabaseConnection.getInstance().close(con, pstm, rs);
    } 
  }
  
  public void sendList() {
    Collections.sort(this.list);
    byte b;
    int i;
    Book[] arrayOfBook;
    for (i = (arrayOfBook = getAll()).length, b = 0; b < i; ) {
      Book book = arrayOfBook[b];
      this.pc.SendPacket((S_BasePacket)new S_BooksAdd(book));
      b++;
    } 
  }
  
  private boolean addCheck() {
    return (checkMap() && !checkKingdomLocation() && !AgitSystem.getInstance().checkAgitLocation((L1Object)this.pc) && !checkInForbidBookMarkArea());
  }
  
  public boolean checkInForbidBookMarkArea() {
    byte b;
    int i;
    int[][] arrayOfInt;
    for (i = (arrayOfInt = FORBID_BOOKMARK_AREA).length, b = 0; b < i; ) {
      int[] arrayOfInt1 = arrayOfInt[b];
      if (arrayOfInt1[0] <= this.pc.getX() && arrayOfInt1[1] >= this.pc.getX() && arrayOfInt1[2] <= this.pc.getY() && arrayOfInt1[3] >= this.pc.getY() && arrayOfInt1[4] == this.pc.getMap())
        return true; 
      b++;
    } 
    return false;
  }
  
  private boolean checkMap() {
    byte b;
    int i;
    int[] arrayOfInt;
    for (i = (arrayOfInt = this.Tmap).length, b = 0; b < i; ) {
      int j = arrayOfInt[b];
      if (j == this.pc.getMap())
        return true; 
      b++;
    } 
    return false;
  }
  
  private boolean checkKingdomLocation() {
    byte b;
    int i;
    int[][] arrayOfInt;
    for (i = (arrayOfInt = this.Tloc).length, b = 0; b < i; ) {
      int[] arrayOfInt1 = arrayOfInt[b];
      if (arrayOfInt1[0] <= this.pc.getX() && arrayOfInt1[1] >= this.pc.getX() && arrayOfInt1[2] <= this.pc.getY() && arrayOfInt1[3] >= this.pc.getY() && arrayOfInt1[4] == this.pc.getMap())
        return true; 
      b++;
    } 
    return false;
  }
  
  public void delete() {
    this.pc = null;
    super.delete();
  }
  
  public Book get(int idx) {
    byte b;
    int i;
    Book[] arrayOfBook;
    for (i = (arrayOfBook = getAll()).length, b = 0; b < i; ) {
      Book book = arrayOfBook[b];
      if (book.getBook_id() == idx)
        return book; 
      b++;
    } 
    return null;
  }
}
