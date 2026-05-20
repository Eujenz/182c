package net.mina;

import net.Config;
import net.LineageClient;
import net.Opcodes;
import net.network.client.C_BasePacket;
import net.util.Util;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineagePacketDecoder extends CumulativeProtocolDecoder {
  private static final Object o = new Object();
  
  private static final Logger log = LoggerFactory.getLogger(LineagePacketDecoder.class);
  
  private static final String DECODER_LOG = "非法客户端   IP[%s] 账号[%s]   角色[%s]  原因[%s]  处理[%s]";
  
  protected boolean doDecode(IoSession session, IoBuffer buffer, ProtocolDecoderOutput out) throws Exception {
    if (Config.shutdown)
      return false; 
    try {
      synchronized (o) {
        if (session == null)
          return false; 
        LineageClient client = (LineageClient)session.getAttribute("LineageClient Key");
        if (client != null) {
          if (buffer == null)
            return false; 
          if (out == null)
            return false; 
          if (((Boolean)session.getAttribute("packet")).booleanValue()) {
            if (buffer.remaining() >= ((Integer)session.getAttribute("size")).intValue()) {
              packet(client, ((Integer)session.getAttribute("size")).intValue(), buffer);
              session.setAttribute("packet", Boolean.valueOf(false));
              session.setAttribute("size", Integer.valueOf(0));
              if (buffer.remaining() > 0)
                doDecode(session, buffer, out); 
            } 
          } else {
            int size = getSize(buffer);
            if (size > 0 && buffer.remaining() >= size) {
              packet(client, size, buffer);
              if (buffer.remaining() > 0)
                doDecode(session, buffer, out); 
            } else {
              session.setAttribute("packet", Boolean.valueOf(true));
              session.setAttribute("size", Integer.valueOf(size));
            } 
          } 
        } 
      } 
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
    } 
    return false;
  }
  
  private int getSize(IoBuffer buffer) {
    try {
      byte[] data = new byte[2];
      buffer.get(data);
      int data_size = data[0] & 0xFF;
      data_size |= data[1] << 8 & 0xFF00;
      data_size -= 2;
      return data_size;
    } catch (Exception exception) {
      return 0;
    } 
  }
  
  private void packet(LineageClient client, int size, IoBuffer buffer) {
    client.setCodeCount(client.getCodeCount() + 1);
    if (client.getCodeCount() >= 80) {
      log.info(String.format("非法客户端   IP[%s] 账号[%s]   角色[%s]  原因[%s]  处理[%s]", new Object[] { client.getIP(), client.getID(), client.getPc().getName(), "每秒封包发送次数超过 " + client.getCodeCount(), "踢下线" }));
      client.close();
      return;
    } 
    byte[] data = new byte[size];
    if (data != null) {
      buffer.get(data);
      decrypt(data, size, client);
      if (Config.DEBUG)
        Util.printPacket("[client]" + Integer.valueOf(data[0] & 0xFF) + "\r\n", data); 
      C_BasePacket bp = null;
      try {
        if (data.length > 1440) {
          log.info(String.format("非法客户端   IP[%s] 账号[%s]   角色[%s]  原因[%s]  处理[%s]", new Object[] { client.getIP(), client.getID(), client.getPc().getName(), "单次封包长度异常 " + data.length, "无" }));
          return;
        } 
        if (data.length > 0) {
          int key = data[0] & 0xFF;
          bp = (C_BasePacket)Opcodes.C_LIST.get(Integer.valueOf(key));
          if (bp != null && data != null) {
            bp.read(client, data);
            if (Config.DEBUG)
              log.debug(bp.toString()); 
          } 
        } 
      } catch (Exception e) {
        log.error(e.getLocalizedMessage(), e);
      } finally {
        bp = null;
      } 
    } 
  }
  
  private void decrypt(byte[] data, int size, LineageClient client) {
    byte[] size_temp = getByte(client.packet_C_total_size);
    byte[] temp = (byte[])data.clone();
    int idx = size_temp[0];
    for (int i = 0; i < size; i++) {
      if (i > 0 && i % 8 == 0) {
        int j;
        for (j = 0; j < i; j++)
          data[i] = (byte)(data[i] ^ data[j]); 
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
      idx = temp[i];
    } 
    client.packet_C_total_size += size;
  }
  
  private byte[] getByte(long c_size) {
    byte[] data = new byte[4];
    data[0] = (byte)(int)(data[0] | c_size & 0xFFL);
    data[1] = (byte)(int)(data[1] | c_size >> 8L & 0xFFL);
    data[2] = (byte)(int)(data[2] | c_size >> 16L & 0xFFL);
    data[3] = (byte)(int)(data[3] | c_size >> 24L & 0xFFL);
    return data;
  }
}
