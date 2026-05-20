package net.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeLine {
  final Logger log = LoggerFactory.getLogger(TimeLine.class);
  
  private long nano_time;
  
  private long time;
  
  public void start(boolean nano) {
    if (nano) {
      this.nano_time = System.nanoTime();
    } else {
      this.time = System.currentTimeMillis();
    } 
  }
  
  public void end() {
    if (this.nano_time > 0L) {
      this.nano_time = System.nanoTime() - this.nano_time;
      //this.log.debug(this.nano_time);
      this.log.debug(new StringBuilder().append(this.nano_time).toString());
    } else {
      this.time = System.currentTimeMillis() - this.time;
      //this.log.debug(this.time);
      this.log.debug(new StringBuilder().append(this.time).toString());
    } 
    clear();
  }
  /*public void end() {
      if (this.nano_time > 0L) {
          this.nano_time = System.nanoTime() - this.nano_time;
          this.log.debug(new StringBuilder().append(this.nano_time).toString());
      }
      else {
          this.time = System.currentTimeMillis() - this.time;
          this.log.debug(new StringBuilder().append(this.time).toString());
      }
      this.clear();
  }*/
  
  private void clear() {
    this.nano_time = 0L;
    this.time = 0L;
  }
}
