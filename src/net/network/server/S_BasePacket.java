package net.network.server;

import java.io.ByteArrayOutputStream;
import net.Config;
import net.util.ServerBasePacketPooling;

public class S_BasePacket {
  ByteArrayOutputStream _bao;
  
  public S_BasePacket() {
    this._bao = ServerBasePacketPooling.getInstance().get();
  }
  
  public S_BasePacket(byte[] data) {
    try {
      this._bao = ServerBasePacketPooling.getInstance().get();
      this._bao.write(data);
    } catch (Exception exception) {}
  }
  
  public void clear() {
    ServerBasePacketPooling.getInstance().remove(this._bao);
  }
  
  public S_BasePacket clone() {
    return new S_BasePacket(this._bao.toByteArray());
  }
  
  public byte[] getBytes() {
    int gab = this._bao.size() % 8;
    if (gab != 0)
      for (int i = gab; i < 8; i++)
        this._bao.write(0);  
    return this._bao.toByteArray();
  }
  
  protected void writeD(int value) {
    this._bao.write(value & 0xFF);
    this._bao.write(value >> 8 & 0xFF);
    this._bao.write(value >> 16 & 0xFF);
    this._bao.write(value >> 24 & 0xFF);
  }
  
  protected void writeH(int value) {
    this._bao.write(value & 0xFF);
    this._bao.write(value >> 8 & 0xFF);
  }
  
  protected void writeC(int value) {
    this._bao.write(value & 0xFF);
  }
  
  protected void writeL(long value) {
    this._bao.write((int)(value & 0xFFL));
  }
  
  protected void writeF(double org) {
    long value = Double.doubleToRawLongBits(org);
    this._bao.write((int)(value & 0xFFL));
    this._bao.write((int)(value >> 8L & 0xFFL));
    this._bao.write((int)(value >> 16L & 0xFFL));
    this._bao.write((int)(value >> 24L & 0xFFL));
    this._bao.write((int)(value >> 32L & 0xFFL));
    this._bao.write((int)(value >> 40L & 0xFFL));
    this._bao.write((int)(value >> 48L & 0xFFL));
    this._bao.write((int)(value >> 56L & 0xFFL));
  }
  
  protected void writeS(String text) {
    try {
      if (text != null)
        this._bao.write(text.getBytes(Config.CLIENT_LANGUAGE_CODE)); 
    } catch (Exception exception) {}
    this._bao.write(0);
  }
  
  protected void writeSS(String text) {
    try {
      if (text != null) {
        byte[] test = text.getBytes(Config.CLIENT_LANGUAGE_CODE);
        int size = test.length;
        for (int i = 0; i < size; ) {
          if ((test[i] & 0xFF) >= 127) {
            this._bao.write(test[i + 1]);
            this._bao.write(test[i]);
            i += 2;
            continue;
          } 
          this._bao.write(test[i]);
          this._bao.write(0);
          i++;
        } 
      } 
    } catch (Exception exception) {}
    this._bao.write(0);
    this._bao.write(0);
  }
  
  protected void writeB(byte[] data) {
    if (data != null)
      this._bao.write(data, 0, data.length); 
  }
  
  protected void writeB(byte[] data, int size) {
    if (data != null)
      this._bao.write(data, 0, size); 
  }
}
