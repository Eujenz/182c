package net.network.server;

import net.util.Util;

public class S_LoginFail extends S_BasePacket {
  public static final int REASON_OK = 2;
  
  public static final int REASON_OK_UPDATE_PASSWORD = 4;
  
  public static final int REASON_ALREADY_EXSISTS = 6;
  
  public static final int REASON_INVALID_NAME = 9;
  
  public static final int REASON_WRONG_AMOUNT = 21;
  
  public static final int REASON_WRONG_CLASS = 23;
  
  public static final int LOGIN_USER_OR_PASS_WRONG = 8;
  
  public static final int LOGIN_USER_ON = 22;
  
  public static final int LOGIN_USER_OR_ID_AND_PASS_WRONG = 26;
  
  public static final int REASON_ACCESS_END = 28;
  
  public static final int REASON_ACCESS_OK = 51;
  
  public static final int REASON_IP_FREETIME_OUT = 36;
  
  public S_LoginFail(int type) {
    writeC(2);
    writeC(type);
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(Util.Time());
    sb.append("] ");
    sb.append(getClass().toString());
    sb.append(" :: ");
    return sb.toString();
  }
}
