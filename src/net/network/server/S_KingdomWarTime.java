package net.network.server;

import java.util.Calendar;
import net.util.Util;
import net.world.function.ClanSystem;
import net.world.instance.PcInstance;
import net.world.kingdom.Kingdom;

public class S_KingdomWarTime extends S_BasePacket {
  public S_KingdomWarTime(PcInstance cha) {
    Kingdom k = ClanSystem.getInstance().getKingdom(cha);
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(k.getWarDayLast() + 345600000L);
    int year = cal.get(1);
    int month = cal.get(2) + 1;
    int date = cal.get(5);
    writeC(84);
    writeH(6);
    writeS("KST");
    int i = 0;
    for (int j = 6; i < 6; j += 3) {
      writeC(i);
      writeD(Time(year, month, date, j, 0));
      cal.set(year, month - 1, date, j, 0);
      k.set_warTime(i, cal.getTimeInMillis());
      i++;
    } 
  }
  
  private int Time(int year, int month, int date, int hour, int minute) {
    int t = 0;
    t += 360 * minute;
    t += 21600 * (hour - 17);
    t += 518400 * MonthDate(year, month, date);
    return t;
  }
  
  private int MonthDate(int year, int month, int date) {
    int[] arr = { 
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 
        30, 31 };
    if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
      arr[1] = 29;
    } else {
      arr[1] = 28;
    } 
    int m = 0;
    for (int i = 0; i < month - 1; i++)
      m += arr[i]; 
    return m + date - 1;
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
