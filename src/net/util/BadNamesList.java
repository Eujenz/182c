package net.util;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

public class BadNamesList {
  private static Logger _log = Logger.getLogger(BadNamesList.class.getName());
  
  private final List<String> _nameList = new ArrayList<String>();
  
  private static class Holder {
    static BadNamesList instance = new BadNamesList();
  }
  
  public static BadNamesList getInstance() {
    return Holder.instance;
  }
  
  private BadNamesList() {
    System.out.print("[SERVER] 加载 无效角色名 列表.");
    LineNumberReader lnr = null;
    try {
      InputStreamReader isr = new InputStreamReader(new FileInputStream("data/badnames.txt"), "UTF-8");
      lnr = new LineNumberReader(isr);
      String line = null;
      while ((line = lnr.readLine()) != null) {
        if (line.trim().length() == 0 || line.trim().startsWith("#"))
          continue; 
        StringTokenizer st = new StringTokenizer(line, ";");
        while (st.hasMoreTokens())
          this._nameList.add(st.nextToken().toLowerCase()); 
      } 
      _log.config("加载 " + this._nameList.size() + " bad names");
    } catch (FileNotFoundException e) {
      _log.warning("badnames.txt 数据文件夹丢失.");
    } catch (Exception e) {
      _log.warning("error while loading bad names list : " + e);
    } finally {
      StreamUtil.close(new Closeable[] { lnr });
    } 
    System.out.println("数量:" + this._nameList.size());
  }
  
  public String[] getAllBadNames() {
    return this._nameList.<String>toArray(new String[this._nameList.size()]);
  }
  
  public boolean isBadName(String name) {
    for (String badName : this._nameList) {
      if (name.toLowerCase().contains(badName))
        return true; 
    } 
    return false;
  }
}
