package net.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
//import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import net.database.bean.UserBehavior;
import net.world.instance.PcInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserBehaviorTable {
  final Logger log = LoggerFactory.getLogger(UserBehaviorTable.class);
  
  private static final String DB_SAVE_SQL = "INSERT INTO `user_behavior` (`ip`, `account`, `operID`, `operType`, `targetID`, `locX`, `locY`, `locMap`, `operTime`, `remark`)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
  
  private static final Map<String, List<UserBehavior>> cacheMap = new Hashtable<String, List<UserBehavior>>();
  
  private static class Holder {
    static UserBehaviorTable instance = new UserBehaviorTable();
  }
  
  public static UserBehaviorTable getInstance() {
    return Holder.instance;
  }
  
  private static UserBehavior pc2ub(PcInstance pc) {
    UserBehavior ub = new UserBehavior();
    ub.setIp(pc.getClient().getIP());
    ub.setAccount(pc.getAccount());
    ub.setOperID(pc.getObjectId());
    ub.setLocX(pc.getX());
    ub.setLocY(pc.getY());
    ub.setLocMap(pc.getMap());
    return ub;
  }
  
  public static void putDate(PcInstance pc, String operType) {
    putDate(pc, operType, null);
  }
  
  public static void putDate(PcInstance pc, String operType, String targetID) {
    if (pc == null)
      return; 
    UserBehavior ub = pc2ub(pc);
    ub.setOperType(operType);
    ub.setTargetID(targetID);
    //ub.setOperTime(new Date());
    ub.setOperTime(new java.util.Date());
    add(ub);
  }
  
  public static void add(UserBehavior ub) {
    List<UserBehavior> ubList = cacheMap.get(ub.getIp());
    if (ubList != null) {
      ubList.add(ub);
    } else {
      ubList = new ArrayList<UserBehavior>();
      ubList.add(ub);
      cacheMap.put(ub.getIp(), ubList);
    } 
    save(ub);
  }
  
  private static void save(UserBehavior ub) {
    Connection con = null;
    PreparedStatement st = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("INSERT INTO `user_behavior` (`ip`, `account`, `operID`, `operType`, `targetID`, `locX`, `locY`, `locMap`, `operTime`, `remark`)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
      st.setString(1, ub.getIp());
      st.setString(2, ub.getAccount());
      st.setInt(3, ub.getOperID());
      st.setString(4, ub.getOperType());
      st.setString(5, ub.getTargetID());
      st.setInt(6, ub.getLocX());
      st.setInt(7, ub.getLocY());
      st.setInt(8, ub.getLocMap());
      st.setDate(9, new Date(ub.getOperTime().getTime()));
      st.setString(10, ub.getRemark());
      st.execute();
    } catch (Exception localException) {
      try {
        st.close();
      } catch (Exception exception) {}
      try {
        con.close();
      } catch (Exception exception) {}
    } finally {
      try {
        st.close();
      } catch (Exception exception) {}
      try {
        con.close();
      } catch (Exception exception) {}
    } 
  }
  
  private static void batchSave(List<UserBehavior> usList) {
    Connection con = null;
    PreparedStatement st = null;
    try {
      con = DatabaseConnection.getInstance().getConnection();
      st = con.prepareStatement("INSERT INTO `user_behavior` (`ip`, `account`, `operID`, `operType`, `targetID`, `locX`, `locY`, `locMap`, `operTime`, `remark`)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
      for (UserBehavior ub : usList) {
        st.setString(1, ub.getIp());
        st.setString(2, ub.getAccount());
        st.setInt(3, ub.getOperID());
        st.setString(4, ub.getOperType());
        st.setString(5, ub.getTargetID());
        st.setInt(6, ub.getLocX());
        st.setInt(7, ub.getLocY());
        st.setInt(8, ub.getLocMap());
        st.setDate(9, new Date(ub.getOperTime().getTime()));
        st.setString(10, ub.getRemark());
        st.addBatch();
      } 
      st.executeBatch();
    } catch (Exception localException) {
      try {
        st.close();
      } catch (Exception exception) {}
      try {
        con.close();
      } catch (Exception exception) {}
    } finally {
      try {
        st.close();
      } catch (Exception exception) {}
      try {
        con.close();
      } catch (Exception exception) {}
    } 
  }
}
