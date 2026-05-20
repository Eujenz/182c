package net.network.server;

import java.util.List;
import net.util.Util;

public class S_ShowHtml extends S_BasePacket {
  public S_ShowHtml(int objId, String html) {
    writeC(42);
    writeD(objId);
    writeS(html);
  }
  
  public S_ShowHtml(int objId, String html, String html1) {
    writeC(42);
    writeD(objId);
    writeS(html);
    writeS(null);
    writeH(1);
    writeS(html1);
  }
  
  public S_ShowHtml(int objId, String html, String html1, String html2) {
    writeC(42);
    writeD(objId);
    writeS(html);
    writeS(null);
    writeH(2);
    writeS(html1);
    writeS(html2);
  }
  
  public S_ShowHtml(int objId, String html, String html1, String html2, String html3) {
    writeC(42);
    writeD(objId);
    writeS(html);
    writeS(null);
    writeH(3);
    writeS(html1);
    writeS(html2);
    writeS(html3);
  }
  
  public S_ShowHtml(int objId, String html, List<String> list) {
    writeC(42);
    writeD(objId);
    writeS(html);
    writeS(null);
    writeH(list.size());
    for (String h : list)
      writeS(h); 
  }
  
  public S_ShowHtml(int objId, String html, String[] html1) {
    writeC(42);
    writeD(objId);
    writeS(html);
    writeS(null);
    writeH(html1.length);
    byte b;
    int i;
    String[] arrayOfString;
    for (i = (arrayOfString = html1).length, b = 0; b < i; ) {
      String h = arrayOfString[b];
      writeS(h);
      b++;
    } 
  }
  
  public S_ShowHtml(int objId, String html, int teleport) {
    writeC(42);
    writeD(objId);
    writeS(html);
    writeS(null);
    writeH(1);
    writeS(String.valueOf(teleport));
  }
  
  public S_ShowHtml(int objId, String html, int[] teleport) {
    writeC(42);
    writeD(objId);
    writeS(html);
    writeS(null);
    writeH(teleport.length);
    byte b;
    int i, arrayOfInt[];
    for (i = (arrayOfInt = teleport).length, b = 0; b < i; ) {
      int tel = arrayOfInt[b];
      writeS(String.valueOf(tel));
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
    return sb.toString();
  }
}
