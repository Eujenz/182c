package net.database.bean;

public final class L1Account {
  private int uid;
  
  private String account;
  
  private String pw;
  
  private boolean status;
  
  private int level;
  
  private long register_date;
  
  private long logins_date;
  
  private long block_date;
  
  private String last_ip;
  
  private long month_cards;
  
  public int getUid() {
    return this.uid;
  }
  
  public void setUid(int uid) {
    this.uid = uid;
  }
  
  public String getAccount() {
    return this.account;
  }
  
  public String getPw() {
    return this.pw;
  }
  
  public void setPw(String pw) {
    this.pw = pw;
  }
  
  public void setAccount(String account) {
    this.account = account;
  }
  
  public boolean isStatus() {
    return this.status;
  }
  
  public void setStatus(boolean status) {
    this.status = status;
  }
  
  public int getLevel() {
    return this.level;
  }
  
  public void setLevel(int level) {
    this.level = level;
  }
  
  public long getRegister_date() {
    return this.register_date;
  }
  
  public void setRegister_date(long register_date) {
    this.register_date = register_date;
  }
  
  public long getLogins_date() {
    return this.logins_date;
  }
  
  public void setLogins_date(long logins_date) {
    this.logins_date = logins_date;
  }
  
  public long getBlock_date() {
    return this.block_date;
  }
  
  public void setBlock_date(long block_date) {
    this.block_date = block_date;
  }
  
  public String getLast_ip() {
    return this.last_ip;
  }
  
  public void setLast_ip(String last_ip) {
    this.last_ip = last_ip;
  }
  
  public long getMonth_cards() {
    return this.month_cards;
  }
  
  public void setMonth_cards(long month_cards) {
    this.month_cards = month_cards;
  }
}
