package net.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;
import net.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
  static final Logger log = LoggerFactory.getLogger(Util.class);
  
  private static Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
  
  public static synchronized String StringToken(String text) {
    if (text == null)
      return null; 
    if (text.isEmpty())
      return null; 
    StringTokenizer st = new StringTokenizer(text, "#' ");
    if (st.hasMoreTokens()) {
      String str = st.nextToken();
      return str;
    } 
    return null;
  }
  
  public static synchronized int rand(int lbound, int ubound) {
    return (int)(Math.random() * (ubound - lbound + 1) + lbound);
  }
  
  public static synchronized double rand(double lbound, double ubound) {
    return Math.random() * (ubound - lbound + 1.0D) + lbound;
  }
  
  public static synchronized int rand_axis(int x, int size) {
    return (int)(Math.random() * size + x);
  }
  
  public static synchronized String YearMonthDate(boolean token) {
    StringBuilder sb = new StringBuilder();
    int Y = YEAR();
    int M = Month();
    int D = Date();
    if (Y < 10)
      sb.append("0"); 
    sb.append(Y);
    if (token)
      sb.append("-"); 
    if (M < 10)
      sb.append("0"); 
    sb.append(M);
    if (token)
      sb.append("-"); 
    if (D < 10)
      sb.append("0"); 
    sb.append(D);
    return sb.toString();
  }
  
  private Calendar timestampToCalendar(Timestamp ts) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(ts.getTime());
    return cal;
  }
  
  public static synchronized long YearMonthDateKingdom() {
    Calendar ca = (Calendar)cal.clone();
    ca.add(5, 4);
    ca.set(ca.get(1), ca.get(2), ca.get(5), 20, 0, 0);
    return ca.getTimeInMillis();
  }
  
  public static int YEAR() {
    return cal.get(1);
  }
  
  public static int Year() {
    return cal.get(1) - 2000;
  }
  
  public static int Month() {
    return cal.get(2) + 1;
  }
  
  public static int Date() {
    return cal.get(5);
  }
  
  public static int Hour() {
    return cal.get(11);
  }
  
  public static int Minute() {
    return cal.get(12);
  }
  
  public static String Time() {
    int h = cal.get(10);
    int m = cal.get(12);
    int s = cal.get(13);
    StringBuffer sb = new StringBuffer();
    if (h < 10)
      sb.append("0"); 
    sb.append(h);
    sb.append(":");
    if (m < 10)
      sb.append("0"); 
    sb.append(m);
    sb.append(":");
    if (s < 10)
      sb.append("0"); 
    sb.append(s);
    return sb.toString();
  }
  
  public static int WorldTimeToHour() {
    SimpleDateFormat sdf = new SimpleDateFormat("HH");
    Date dtToday = new Date();
    dtToday.setTime((Config.WORLDTIME * 1000));
    return Integer.valueOf(sdf.format(dtToday)).intValue();
  }
  
  public static int WorldTimeToMinute() {
    SimpleDateFormat sdf = new SimpleDateFormat("mm");
    Date dtToday = new Date();
    dtToday.setTime((Config.WORLDTIME * 1000));
    return Integer.valueOf(sdf.format(dtToday)).intValue();
  }
  
  public static void printPacket(String msg, byte[] data) {
    System.out.println(String.valueOf(msg) + printData(data, data.length));
    log.debug(String.valueOf(msg) + printData(data, data.length));
  }
  
  private static String printData(byte[] data, int len) {
    StringBuffer result = new StringBuffer();
    int counter = 0;
    for (int i = 0; i < len; i++) {
      if (counter % 16 == 0)
        result.append(String.valueOf(fillHex(i, 4)) + ": "); 
      result.append(String.valueOf(fillHex(data[i] & 0xFF, 2)) + " ");
      counter++;
      if (counter == 16) {
        result.append("   ");
        int charpoint = i - 15;
        for (int a = 0; a < 16; a++) {
          int t1 = data[charpoint++];
          if (t1 > 31 && t1 < 128) {
            result.append((char)t1);
          } else {
            result.append('.');
          } 
        } 
        result.append("\n");
        counter = 0;
      } 
    } 
    int rest = len % 16;
    if (rest > 0) {
      for (int j = 0; j < 17 - rest; j++)
        result.append("   "); 
      int charpoint = len - rest;
      for (int a = 0; a < rest; a++) {
        int t1 = data[charpoint++];
        if (t1 > 31 && t1 < 128) {
          result.append((char)t1);
        } else {
          result.append('.');
        } 
      } 
      result.append("\n");
    } 
    return result.toString();
  }
  
  private static String fillHex(int data, int digits) {
    String number = Integer.toHexString(data);
    for (int i = number.length(); i < digits; i++)
      number = "0" + number; 
    return number;
  }
  
  public static Long timeStrTran1970Seconds(String timeStr) {
    Date stat = null;
    try {
      stat = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).parse(timeStr);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    if (stat != null)
      return Long.valueOf(stat.getTime() / 1000L); 
    return Long.valueOf(0L);
  }
}
