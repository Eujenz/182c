package net.util;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StreamUtil {
  private static Logger _log = Logger.getLogger(StreamUtil.class.getName());
  
  public static void close(Closeable... closeables) {
    byte b;
    int i;
    Closeable[] arrayOfCloseable;
    for (i = (arrayOfCloseable = closeables).length, b = 0; b < i; ) {
      Closeable c = arrayOfCloseable[b];
      try {
        if (c != null)
          c.close(); 
      } catch (IOException e) {
        _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
      } 
      b++;
    } 
  }
}
