package net.mina;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.database.BanListTable;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineageBlackListFilter extends IoFilterAdapter {
  private static final Logger log = LoggerFactory.getLogger(LineageBlackListFilter.class);
  
  public static String NAME = "blacklistFilter";
  
  public void init() throws Exception {
    setBlacklist(BanListTable.getInstance().getList());
  }
  
  private final List<String> blacklist = new CopyOnWriteArrayList<String>();
  
  public void setBlacklist(Iterable<String> addresses) {
    if (addresses == null)
      throw new IllegalArgumentException("IP地址列表不能为空"); 
    this.blacklist.clear();
    for (String address : addresses)
      block(address); 
  }
  
  public void block(String hostAddress) {
    if (StringUtils.isBlank(hostAddress))
      throw new IllegalArgumentException("IP地址不能为空"); 
    this.blacklist.add(hostAddress);
    BanListTable.getInstance().banIP(hostAddress);
  }
  
  public void unblock(String hostAddress) {
    if (StringUtils.isBlank(hostAddress))
      throw new IllegalArgumentException("IP地址不能为空"); 
    this.blacklist.remove(hostAddress);
  }
  
  public void sessionCreated(IoFilter.NextFilter nextFilter, IoSession session) {
    if (!isBlocked(session)) {
      nextFilter.sessionCreated(session);
    } else {
      blockSession(session);
    } 
  }
  
  public void sessionOpened(IoFilter.NextFilter nextFilter, IoSession session) throws Exception {
    if (!isBlocked(session)) {
      nextFilter.sessionOpened(session);
    } else {
      blockSession(session);
    } 
  }
  
  public void sessionClosed(IoFilter.NextFilter nextFilter, IoSession session) throws Exception {
    if (!isBlocked(session)) {
      nextFilter.sessionClosed(session);
    } else {
      blockSession(session);
    } 
  }
  
  public void sessionIdle(IoFilter.NextFilter nextFilter, IoSession session, IdleStatus status) throws Exception {
    if (!isBlocked(session)) {
      nextFilter.sessionIdle(session, status);
    } else {
      blockSession(session);
    } 
  }
  
  public void messageReceived(IoFilter.NextFilter nextFilter, IoSession session, Object message) {
    if (!isBlocked(session)) {
      nextFilter.messageReceived(session, message);
    } else {
      blockSession(session);
    } 
  }
  
  public void messageSent(IoFilter.NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
    if (!isBlocked(session)) {
      nextFilter.messageSent(session, writeRequest);
    } else {
      blockSession(session);
    } 
  }
  
  private void blockSession(IoSession session) {
    log.warn("被封锁的IP; 关闭中...");
    session.close(true);
  }
  
  private boolean isBlocked(IoSession session) {
    SocketAddress remoteAddress = session.getRemoteAddress();
    if (remoteAddress instanceof InetSocketAddress) {
      InetAddress address = ((InetSocketAddress)remoteAddress).getAddress();
      return this.blacklist.contains(address.getHostAddress());
    } 
    return false;
  }
}
