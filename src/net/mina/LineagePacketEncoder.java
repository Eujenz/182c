package net.mina;

import net.Config;
import net.LineageClient;
import net.network.server.S_BasePacket;
import net.util.Util;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class LineagePacketEncoder implements ProtocolEncoder {
  private static final Object o = new Object();
  
  public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
    if (Config.shutdown)
      return; 
    try {
      synchronized (o) {
        LineageClient client = (LineageClient)session.getAttribute("LineageClient Key");
        if (message != null) {
          S_BasePacket data = (S_BasePacket)message;
          if (data != null) {
            byte[] d = data.getBytes();
            int size = d.length;
            if (size > 7) {
              if (Config.DEBUG)
                Util.printPacket("[server]" + Integer.valueOf(d[0] & 0xFF) + "\r\n", d); 
              test(client, out, d, size);
              encrypt(d, size, client);
              out.write(buffer(d, size + 2));
              data.clear();
            } 
          } 
        } 
      } 
    } catch (Exception exception) {}
  }
  
  public void dispose(IoSession client) throws Exception {}
  
  private void test(LineageClient client, ProtocolEncoderOutput out, byte[] d, int size) {
    if (size <= 0)
      return; 
    switch (d[0] & 0xFF) {
      case 50:
        if (client.packet_S_total_size % 256L == 8L)
          test(client, out); 
        break;
      case 42:
        if (client.packet_S_total_size % 256L == 16L)
          test(client, out); 
        break;
      case 34:
        if (client.packet_S_total_size % 256L == 24L)
          test(client, out); 
        break;
      case 111:
        if (client.packet_S_total_size % 256L == 32L)
          test(client, out); 
        break;
      case 28:
        if (client.packet_S_total_size % 256L == 38L)
          test(client, out); 
        break;
      case 18:
        if (client.packet_S_total_size % 256L == 40L)
          test(client, out); 
        break;
      case 106:
        if (client.packet_S_total_size % 256L == 80L)
          test(client, out); 
        break;
      case 98:
        if (client.packet_S_total_size % 256L == 88L)
          test(client, out); 
        break;
      case 74:
        if (client.packet_S_total_size % 256L == 112L)
          test(client, out); 
        break;
    } 
  }
  
  private void test(LineageClient client, ProtocolEncoderOutput out) {
    byte[] dd = new byte[8];
    dd[0] = 51;
    dd[1] = 0;
    dd[2] = 0;
    dd[3] = 0;
    dd[4] = 0;
    dd[5] = 0;
    dd[6] = 0;
    dd[7] = 0;
    encrypt(dd, 8, client);
    out.write(buffer(dd, 10));
  }
  
  private IoBuffer buffer(byte[] data, int length) {
    byte[] size = new byte[2];
    size[0] = (byte)(size[0] | length & 0xFF);
    size[1] = (byte)(size[1] | length >> 8 & 0xFF);
    IoBuffer buffer = IoBuffer.allocate(length, false);
    buffer.put(size);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }
  
  private void encrypt(byte[] data, int size, LineageClient client) {
    byte[] size_temp = getByte(client.packet_S_total_size);
    byte[] temp = (byte[])data.clone();
    int idx = size_temp[0];
    for (int i = 0; i < size; i++) {
      if (i > 0 && i % 8 == 0) {
        int j;
        for (j = 0; j < i; j++)
          data[i] = (byte)(data[i] ^ temp[j]); 
        if (i % 16 == 0) {
          byte[] abyte0;
          int l = (abyte0 = size_temp).length;
          for (int k = 0; k < l; k++) {
            byte st = abyte0[k];
            data[i] = (byte)(data[i] ^ st);
          } 
        } 
        for (j = 1; j < 4; j++)
          data[i] = (byte)(data[i] ^ size_temp[j]); 
        for (j = 1; j < 4; j++) {
          if (i + j < size)
            data[i + j] = (byte)(data[i + j] ^ size_temp[j]); 
        } 
      } else {
        data[i] = (byte)(data[i] ^ idx);
        if (i == 0)
          for (int j = 1; j < 4; j++) {
            if (i + j < size)
              data[i + j] = (byte)(data[i + j] ^ size_temp[j]); 
          }  
      } 
      idx = data[i];
    } 
    client.packet_S_total_size += size;
  }
  
  private byte[] getByte(long s_size) {
    byte[] data = new byte[4];
    data[0] = (byte)(int)(data[0] | s_size & 0xFFL);
    data[1] = (byte)(int)(data[1] | s_size >> 8L & 0xFFL);
    data[2] = (byte)(int)(data[2] | s_size >> 16L & 0xFFL);
    data[3] = (byte)(int)(data[3] | s_size >> 24L & 0xFFL);
    return data;
  }
}
