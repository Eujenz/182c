package net.online;

class WriteHTMLPc extends DocumentLiteracy {
  private String content;
  
  private String url;
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public String getContentWrite() {
    return this.content;
  }
  
  public String getUrlWrite() {
    return this.url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
}
