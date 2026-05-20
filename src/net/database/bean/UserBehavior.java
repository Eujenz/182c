package net.database.bean;

import java.util.Date;

public class UserBehavior {
  private String id;
  
  private String ip;
  
  private String account;
  
  private int operID;
  
  private String operType;
  
  private String targetID;
  
  private int locX;
  
  private int locY;
  
  private int locMap;
  
  private Date operTime;
  
  private String remark;
  
  public String getIp() {
    return this.ip;
  }
  
  public void setIp(String ip) {
    this.ip = ip;
  }
  
  public String getId() {
    return this.id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getAccount() {
    return this.account;
  }
  
  public void setAccount(String account) {
    this.account = account;
  }
  
  public int getOperID() {
    return this.operID;
  }
  
  public void setOperID(int operID) {
    this.operID = operID;
  }
  
  public String getOperType() {
    return this.operType;
  }
  
  public void setOperType(String operType) {
    this.operType = operType;
  }
  
  public String getTargetID() {
    return this.targetID;
  }
  
  public void setTargetID(String targetID) {
    this.targetID = targetID;
  }
  
  public int getLocX() {
    return this.locX;
  }
  
  public void setLocX(int locX) {
    this.locX = locX;
  }
  
  public int getLocY() {
    return this.locY;
  }
  
  public void setLocY(int locY) {
    this.locY = locY;
  }
  
  public int getLocMap() {
    return this.locMap;
  }
  
  public void setLocMap(int locMap) {
    this.locMap = locMap;
  }
  
  public Date getOperTime() {
    return this.operTime;
  }
  
  public void setOperTime(Date operTime) {
    this.operTime = operTime;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public String toString() {
    return "UserBehavior [id=" + this.id + ", ip=" + this.ip + ", account=" + this.account + ", operID=" + this.operID + ", operType=" + this.operType + ", targetID=" + this.targetID + ", locX=" + this.locX + 
      ", locY=" + this.locY + ", locMap=" + this.locMap + ", operTime=" + this.operTime + ", remark=" + this.remark + "]";
  }
}
