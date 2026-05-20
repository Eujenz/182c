package net.util;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ServerBasePacketPooling {
  private List<ByteArrayOutputStream> list;
  
  private int MAXPOOL = 1000;
  
  private static class Holder {
    static ServerBasePacketPooling instance = new ServerBasePacketPooling();
  }
  
  public static ServerBasePacketPooling getInstance() {
    return Holder.instance;
  }
  
  private ServerBasePacketPooling() {
    this.list = new ArrayList<ByteArrayOutputStream>();
    for (int i = 0; i < this.MAXPOOL; i++)
      this.list.add(new ByteArrayOutputStream()); 
  }
  
  public void remove(ByteArrayOutputStream baos) {
    synchronized (this.list) {
      this.list.add(baos);
    } 
  }
  
  public ByteArrayOutputStream get() {
    synchronized (this.list) {
      ByteArrayOutputStream baos = null;
      if (this.list.size() > 0) {
        baos = this.list.get(0);
        this.list.remove(0);
      } else {
        baos = new ByteArrayOutputStream();
      } 
      baos.reset();
      return baos;
    } 
  }
}
