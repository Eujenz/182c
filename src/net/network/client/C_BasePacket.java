package net.network.client;

import net.Config;
import net.LineageClient;

public class C_BasePacket {
  private byte[] _decrypt = null;
  
  private int size = 0;
  
  private int _off = 0;
  
  public int readC() {
    int i = 0;
    try {
      if (this._decrypt == null)
        return i; 
      if (this.size < this._off + 1)
        return i; 
      i = this._decrypt[this._off++] & 0xFF;
      return i;
    } catch (Exception exception) {
      return 0;
    } 
  }
  
  public int readH() {
    int i = 0;
    try {
      if (this._decrypt == null)
        return i; 
      if (this.size < this._off + 2)
        return i; 
      i = this._decrypt[this._off++] & 0xFF;
      i |= this._decrypt[this._off++] << 8 & 0xFF00;
      return i;
    } catch (Exception exception) {
      return 0;
    } 
  }
  
  public int readD() {
    int i = 0;
    try {
      if (this._decrypt == null)
        return i; 
      if (this.size < this._off + 4)
        return i; 
      i = this._decrypt[this._off++] & 0xFF;
      i |= this._decrypt[this._off++] << 8 & 0xFF00;
      i |= this._decrypt[this._off++] << 16 & 0xFF0000;
      i |= this._decrypt[this._off++] << 24 & 0xFF000000;
      return i;
    } catch (Exception exception) {
      return 0;
    } 
  }
  
  public double readF() {
    try {
      if (this._decrypt == null)
        return 0.0D; 
      if (this.size < this._off + 8)
        return 0.0D; 
      long l = (this._decrypt[this._off++] & 0xFF);
      l |= (this._decrypt[this._off++] << 8 & 0xFF00);
      l |= (this._decrypt[this._off++] << 16 & 0xFF0000);
      l |= (this._decrypt[this._off++] << 24 & 0xFF000000);
      //this._decrypt[this._off++];
      this._off++;
      l = (int)(l | 0L);
      //this._decrypt[this._off++];
      this._off++;
      l = (int)(l | 0L);
      //this._decrypt[this._off++];
      this._off++;
      l = (int)(l | 0L);
      //this._decrypt[this._off++];
      this._off++;
      l = (int)(l | 0L);
      return Double.longBitsToDouble(l);
    } catch (Exception exception) {
      return 0.0D;
    } 
  }
  public double readFa() {
      try {
          if (this._decrypt == null) {
              return 0.0;
          }
          if (this.size < this._off + 8) {
              return 0.0;
          }
          long l = this._decrypt[this._off++] & 0xFF;
          l |= (this._decrypt[this._off++] << 8 & 0xFF00);
          l |= (this._decrypt[this._off++] << 16 & 0xFF0000);
          final long n;
          l = (n = (l | (long)(this._decrypt[this._off++] << 24 & 0xFF000000)));
          final byte b = this._decrypt[this._off++];
          final long n2;
          l = (n2 = (int)(n | (long)0));
          final byte b2 = this._decrypt[this._off++];
          final long n3;
          l = (n3 = (int)(n2 | (long)0));
          final byte b3 = this._decrypt[this._off++];
          final long n4;
          l = (n4 = (int)(n3 | (long)0));
          final byte b4 = this._decrypt[this._off++];
          l = (int)(n4 | (long)0);
          return Double.longBitsToDouble(l);
      }
      catch (Exception ex) {
          return 0.0;
      }
  }
  
  public String readS() {
    String s = null;
    try {
      if (this._decrypt == null)
        return s; 
      s = new String(this._decrypt, this._off, this._decrypt.length - this._off, Config.CLIENT_LANGUAGE_CODE);
      //s = s.substring(0, s.indexOf(false));
      s = s.substring(0, s.indexOf(0));
      this._off += (s.getBytes(Config.CLIENT_LANGUAGE_CODE)).length + 1;
      return s;
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public String readSS() {
    String text = null;
    int loc = 0;
    int start = 0;
    try {
      start = this._off;
      while (readH() != 0)
        loc += 2; 
      StringBuffer test = new StringBuffer();
      do {
        if ((this._decrypt[start] & 0xFF) >= 127 || (this._decrypt[start + 1] & 0xFF) >= 127) {
          byte[] t = new byte[2];
          t[0] = this._decrypt[start + 1];
          t[1] = this._decrypt[start];
          test.append(new String(t, 0, 2, Config.CLIENT_LANGUAGE_CODE));
        } else {
          test.append(new String(this._decrypt, start, 1, Config.CLIENT_LANGUAGE_CODE));
        } 
        start += 2;
        loc -= 2;
      } while (loc > 0);
      text = test.toString();
    } catch (Exception e) {
      text = null;
    } 
    return text;
  }
  
  public byte[] readB() {
    byte[] result = null;
    try {
      result = new byte[this.size - this._off];
      int size = this.size - this._off;
      System.arraycopy(this._decrypt, this._off, result, 0, size);
      this._off += size + 1;
      return result;
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public void read(LineageClient lc, byte[] data) {
    this._decrypt = data;
    this._off = 1;
    if (this._decrypt != null)
      this.size = this._decrypt.length; 
  }
  
  public String getType() {
    return "[C] " + getClass().getSimpleName();
  }
}
