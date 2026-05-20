package net.world.instance.books;

import java.util.ArrayList;
import java.util.List;
import net.world.instance.books.bean.Book;

public class Books {
  protected List<Book> list = new ArrayList<Book>();
  
  public Book[] getAll() {
    return this.list.<Book>toArray(new Book[this.list.size()]);
  }
  
  public int getNextID() {
    int cur = 0;
    for (Book b : this.list)
      cur = (cur < b.getBook_id()) ? b.getBook_id() : cur; 
    return ++cur;
  }
  
  public int getCount() {
    return this.list.size();
  }
  
  public void add(Book b) {
    this.list.add(b);
  }
  
  public void remove(Book b) {
    this.list.remove(b);
  }
  
  public void delete() {
    this.list.clear();
    this.list = null;
  }
  
  public void sendList() {}
  
  public void save() {}
  
  public void read() {}
  
  public Book get(int idx) {
    return null;
  }
}
