package net.world.instance.books.bean;

public class Book implements Comparable<Book> {
  private int book_id;
  
  private String location;
  
  private int locX;
  
  private int locY;
  
  private int locMAP;
  
  public int getBook_id() {
    return this.book_id;
  }
  
  public void setBook_id(int book_id) {
    this.book_id = book_id;
  }
  
  public String getLocation() {
    return this.location;
  }
  
  public void setLocation(String location) {
    this.location = location;
  }
  
  public int getLocX() {
    return this.locX;
  }
  
  public void setLocX(int locX) {
    this.locX = locX;
  }
  
  public int getLocY() {
    return this.locY;
  }
  
  public void setLocY(int locY) {
    this.locY = locY;
  }
  
  public int getLocMAP() {
    return this.locMAP;
  }
  
  public void setLocMAP(int locMAP) {
    this.locMAP = locMAP;
  }
  
  public int compareTo(Book o) {
    return this.location.compareTo(o.getLocation());
  }
  
  public String toString() {
    return this.location;
  }
}
