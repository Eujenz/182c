package net.mina;

import java.net.InetSocketAddress;
import net.database.BanListTable;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.firewall.ConnectionThrottleFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineageConnectionThrottleFilter extends ConnectionThrottleFilter {
  private static final Logger log = LoggerFactory.getLogger(LineageConnectionThrottleFilter.class);
  
  public static String NAME = "connectionThrottleFilter";
  
  public LineageConnectionThrottleFilter() {}
  
  public LineageConnectionThrottleFilter(long allowedInterval) {
    super(allowedInterval);
  }
  
  public void sessionCreated(IoFilter.NextFilter nextFilter, IoSession session) throws Exception {
    if (!isConnectionOk(session)) {
      InetSocketAddress address = (InetSocketAddress)session.getRemoteAddress();
      log.warn("创建新连接的频率过快，IP：" + address);
      BanListTable.getInstance().banIP(address.getAddress().getHostAddress());
      LineageBlackListFilter blacker = (LineageBlackListFilter)session.getFilterChain().get(LineageBlackListFilter.NAME);
      blacker.block(address.getAddress().getHostAddress());
      session.close(true);
    } else {
      nextFilter.sessionCreated(session);
    } 
  }
}
