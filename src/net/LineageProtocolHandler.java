package net;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.StringTokenizer;
import net.database.BanListTable;
import net.network.server.S_ClientVersion;
import net.world.function.GmCommand;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineageProtocolHandler extends IoHandlerAdapter {
  private static final Logger _log = LoggerFactory.getLogger(LineageProtocolHandler.class);
  
  private HashMap<String, ip> _list;
  
  private static final String FI_LOG = "客戶端最大連接數超過限制 數量[%s]";
  
  private static final String FI_COUNT_LOG = "非法客戶端  IP[%s]  原因[%s] 處理[%s]";
  
  private static final String FI_RESTRICTION_LOG = "同IP連接數超過限制 IP[%s]    連接數[%s]";
  
  private static class Holder {
    static LineageProtocolHandler instance = new LineageProtocolHandler();
  }
  
  public static LineageProtocolHandler getInstance() {
    return Holder.instance;
  }
  
  private LineageProtocolHandler() {
    this._list = new HashMap<String, ip>();
  }
  
  public void sessionCreated(IoSession session) {
    int linkSize = this._list.size();
    if (linkSize >= Config.MAX_LINK_AMOUNT) {
      _log.info(String.format("客戶端最大連接數超過限制 數量[%s]", new Object[] { Integer.valueOf(linkSize) }));
      return;
    } 
    try {
      String clientIP = ((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress();
      StringTokenizer st = new StringTokenizer(session.toString(), ":");
      st.nextToken();
      String c_ip = st.nextToken();
      String c_port = st.nextToken().split(" ")[0];
      st = new StringTokenizer(c_ip, "/");
      st.nextToken();
      c_ip = st.nextToken();
      st = new StringTokenizer(session.getLocalAddress().toString(), ":");
      st.nextToken();
      String s_port = st.nextToken();
      session.setAttribute("IP", clientIP);
      session.setAttribute("C_PORT", c_port);
      session.setAttribute("S_PORT", s_port);
      if (c_port == null || c_port.isEmpty() || c_port.equalsIgnoreCase("")) {
        if (!BanListTable.getInstance().isBanList(clientIP)) {
          GmCommand.getInstance().BanTable("1", clientIP);
          _log.info(String.format("非法客戶端  IP[%s]  原因[%s] 處理[%s]", new Object[] { clientIP, "空端口", "已踢下線並封鎖IP" }));
        } 
        session.close(true);
      } else if (c_port.startsWith("0")) {
        session.close(true);
      } 
    } catch (Exception exception) {}
  }
  
  public void sessionOpened(IoSession session) {
    int linkSize = this._list.size();
    if (linkSize >= Config.MAX_LINK_AMOUNT)
      return; 
    try {
      if (!Config.shutdown && !session.isClosing()) {
        String c_ip = (String)session.getAttribute("IP");
        if (!BanListTable.getInstance().isBanList(c_ip)) {
          ip IP = this._list.get(c_ip);
          if (IP == null) {
            IP = new ip();
            IP.ip = c_ip;
            IP.time = System.currentTimeMillis();
            this._list.put(IP.ip, IP);
          } else if (IP.block) {
            session.close(true);
          } else {
            if (System.currentTimeMillis() < IP.time + 1000L) {
              if (IP.count > 3) {
                String clIP = IP.ip;
                if (!BanListTable.getInstance().isBanList(clIP)) {
                  GmCommand.getInstance().BanTable("1", clIP);
                  _log.info(String.format("非法客戶端  IP[%s]  原因[%s] 處理[%s]", new Object[] { clIP, "1秒內超過3次連接", "已踢下線並封鎖IP" }));
                } 
                IP.block = true;
                session.close(IP.block);
                return;
              } 
              IP.count++;
            } else {
              IP.count = 0;
            } 
            IP.time = System.currentTimeMillis();
          } 
          String c_port = (String)session.getAttribute("C_PORT");
          if (c_port == null || c_port.isEmpty() || c_port.equalsIgnoreCase("")) {
            if (!BanListTable.getInstance().isBanList(c_ip)) {
              GmCommand.getInstance().BanTable("1", c_ip);
              _log.info(String.format("非法客戶端  IP[%s]  原因[%s] 處理[%s]", new Object[] { c_ip, "空端口(2)", "已踢下線並封鎖IP" }));
            } 
            session.close(true);
            return;
          } 
          if (c_port.startsWith("0")) {
            session.close(true);
            return;
          } 
          String ipCheck = IP.ip;
          if (this._list.containsKey(ipCheck)) {
            IP.ipCount++;
            if (IP.ipCount > 10) {
              IP.block = true;
              session.close(IP.block);
              _log.info(String.format("同IP連接數超過限制 IP[%s]    連接數[%s]", new Object[] { ipCheck, Integer.valueOf(IP.ipCount) }));
              return;
            } 
          } 
          LineageClient lc = new LineageClient(session);
          session.setAttribute("LineageClient Key", lc);
          session.setAttribute("packet", Boolean.valueOf(false));
          session.setAttribute("size", Integer.valueOf(0));
          session.write(new S_ClientVersion());
        } else {
          session.close(true);
        } 
      } 
    } catch (Exception exception) {}
  }
  
  public void sessionClosed(IoSession session) throws Exception {
    try {
      String c_ip = (String)session.getAttribute("IP");
      ip IP = this._list.get(c_ip);
      String ip = IP.ip;
      if (this._list.containsKey(ip))
        if (IP.ipCount <= 1) {
          this._list.remove(ip);
        } else {
          IP.ipCount--;
        }  
      LineageClient client = (LineageClient)session.getAttribute("LineageClient Key");
      if (client != null) {
        client.close();
      } else {
        session.close(true);
      } 
    } catch (Exception exception) {}
  }
  
  public void exceptionCaught(IoSession session, Throwable cause) {
    try {
      String c_ip = (String)session.getAttribute("IP");
      ip IP = this._list.get(c_ip);
      String ip = IP.ip;
      if (this._list.containsKey(ip))
        if (IP.ipCount <= 1) {
          this._list.remove(ip);
        } else {
          IP.ipCount--;
        }  
      LineageClient client = (LineageClient)session.getAttribute("LineageClient Key");
      if (client != null) {
        client.close();
      } else {
        session.close(true);
      } 
    } catch (Exception exception) {}
  }
  
  class ip {
    public String ip;
    
    public int count;
    
    public long time;
    
    public boolean block;
    
    public int ipCount;
  }
}
