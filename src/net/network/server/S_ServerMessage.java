package net.network.server;

import net.util.Util;

public class S_ServerMessage extends S_BasePacket {
  private String log_number;
  
  public S_ServerMessage(int number) {
    this.log_number = String.valueOf(number);
    writeC(16);
    writeH(number);
    writeC(0);
  }
  
  public S_ServerMessage(int number, String text) {
    this.log_number = String.valueOf(number);
    writeC(16);
    writeH(number);
    writeC(1);
    writeS(text);
  }
  
  public S_ServerMessage(int number, String text, String text2) {
    this.log_number = String.valueOf(number);
    writeC(16);
    writeH(number);
    writeC(2);
    writeS(text);
    writeS(text2);
  }
  
  public S_ServerMessage(int number, String[] text) {
    this.log_number = String.valueOf(number);
    writeC(16);
    writeH(number);
    writeC(text.length);
    byte b;
    int i;
    String[] arrayOfString;
    for (i = (arrayOfString = text).length, b = 0; b < i; ) {
      String t = arrayOfString[b];
      writeS(t);
      b++;
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
      sb.append(this.log_number);
    } catch (Exception exception) {}
    return sb.toString();
  }
}
