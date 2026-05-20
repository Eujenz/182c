package net.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConnection {
  private final Logger log = LoggerFactory.getLogger(DatabaseConnection.class);
  
  private ComboPooledDataSource _source;
  
  private Object connection = new Object();
  
  private Object delete = new Object();
  
  private Object update = new Object();
  
  private Object insert = new Object();
  
  private Object select = new Object();
  
  private static class Holder {
    static DatabaseConnection instance = new DatabaseConnection();
  }
  
  public static DatabaseConnection getInstance() {
    return Holder.instance;
  }
  
  private DatabaseConnection() {
    try {
      this._source = new ComboPooledDataSource();
      this._source.setAutoCommitOnClose(true);
      this._source.setInitialPoolSize(10);
      this._source.setMinPoolSize(5);
      this._source.setMaxPoolSize(20);
      this._source.setAcquireRetryAttempts(0);
      this._source.setAcquireRetryDelay(500);
      this._source.setCheckoutTimeout(0);
      this._source.setAcquireIncrement(5);
      this._source.setIdleConnectionTestPeriod(60);
      this._source.setMaxIdleTime(0);
      this._source.setMaxStatementsPerConnection(100);
      this._source.setBreakAfterAcquireFailure(false);
      this._source.setDriverClass(Config.DRIVER);
      this._source.setJdbcUrl(Config.URL);
      this._source.setUser(Config.USER);
      this._source.setPassword(Config.PASS);
      this._source.getConnection().close();
    } catch (Exception e) {
      this.log.error(e.getLocalizedMessage(), e);
    } 
  }
  
  public Connection getConnection() throws Exception {
    synchronized (this.connection) {
      Connection con = null;
      while (true) {
        con = this._source.getConnection();
        if (con != null)
          return con; 
      } 
    } 
  }
  
  public int query_select_count(String q) {
    synchronized (this.select) {
      Connection con = null;
      PreparedStatement st = null;
      ResultSet rs = null;
      try {
        con = getConnection();
        st = con.prepareStatement(q);
        rs = st.executeQuery();
        if (rs.next()) {
          int i = rs.getInt(1);
          close(con, st, rs);
          return i;
        } 
      } catch (Exception e) {
        this.log.error(e.getLocalizedMessage(), e);
      } finally {
        close(con, st, rs);
      } 
      close(con, st, rs);
      return 0;
    } 
  }
  
  public String query_select_string(String q) {
    synchronized (this.select) {
      Connection con = null;
      PreparedStatement st = null;
      ResultSet rs = null;
      try {
        con = getConnection();
        st = con.prepareStatement(q);
        rs = st.executeQuery();
        if (rs.next()) {
          String str = rs.getString(1);
          close(con, st, rs);
          return str;
        } 
      } catch (Exception e) {
        this.log.error(e.getLocalizedMessage(), e);
      } finally {
        close(con, st, rs);
      } 
      close(con, st, rs);
      return null;
    } 
  }
  
  public boolean query_select(String q) {
    synchronized (this.select) {
      Connection con = null;
      PreparedStatement st = null;
      ResultSet rs = null;
      try {
        con = getConnection();
        st = con.prepareStatement(q);
        rs = st.executeQuery();
        boolean bool = rs.next();
        close(con, st, rs);
        return bool;
      } catch (Exception e) {
        this.log.error(e.getLocalizedMessage(), e);
      } finally {
        close(con, st, rs);
      } 
      return false;
    } 
  }
  
  public void query_insert(String q) {
    synchronized (this.insert) {
      Connection con = null;
      PreparedStatement st = null;
      try {
        con = getConnection();
        st = con.prepareStatement(q);
        st.executeUpdate();
      } catch (Exception e) {
        this.log.error(e.getLocalizedMessage(), e);
      } finally {
        close(con, st);
      } 
    } 
  }
  
  public boolean query_update(String q) {
    synchronized (this.update) {
      Connection con = null;
      PreparedStatement st = null;
      try {
        con = getConnection();
        st = con.prepareStatement(q);
        st.executeUpdate();
      } catch (Exception e) {
        close(con, st);
        this.log.error(e.getLocalizedMessage(), e);
        return false;
      } finally {
        close(con, st);
      } 
    } 
    return true;
  }
  
  public void query_delete(String q) {
    synchronized (this.delete) {
      Connection con = null;
      PreparedStatement st = null;
      try {
        con = getConnection();
        st = con.prepareStatement(q);
        st.executeUpdate();
      } catch (Exception e) {
        this.log.error(e.getLocalizedMessage(), e);
      } finally {
        close(con, st);
      } 
    } 
  }
  
  public void close(Connection con, PreparedStatement st, ResultSet rs) {
    close(rs);
    close(st);
    close(con);
  }
  
  public void close(Connection con, PreparedStatement st) {
    close(st);
    close(con);
  }
  
  public void close(Connection con) {
    if (con == null)
      return; 
    try {
      con.close();
    } catch (Exception e) {
      this.log.error(e.getLocalizedMessage(), e);
    } 
  }
  
  public void close(PreparedStatement st) {
    if (st == null)
      return; 
    try {
      st.close();
    } catch (Exception e) {
      this.log.error(e.getLocalizedMessage(), e);
    } 
  }
  
  public void close(ResultSet rs) {
    if (rs == null)
      return; 
    try {
      rs.close();
    } catch (Exception e) {
      this.log.error(e.getLocalizedMessage(), e);
    } 
  }
}
