package net;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneralThreadPool {
  private final Logger log = LoggerFactory.getLogger(GeneralThreadPool.class);
  
  private static final int SCHEDULED_CORE_POOL_SIZE = 100;
  
  private Executor _executor;
  
  private final ScheduledExecutorService _scheduler;
  
  private final ScheduledExecutorService _pcScheduler;
  
  private static class Holder {
    static GeneralThreadPool instance = new GeneralThreadPool();
  }
  
  public static GeneralThreadPool getInstance() {
    return Holder.instance;
  }
  
  private static class PriorityThreadFactory implements ThreadFactory {
    private final int _prio;
    
    private final String _name;
    
    private final AtomicInteger _threadNumber = new AtomicInteger(1);
    
    private final ThreadGroup _group;
    
    public PriorityThreadFactory(String name, int prio) {
      this._prio = prio;
      this._name = name;
      this._group = new ThreadGroup(this._name);
    }
    
    public Thread newThread(Runnable r) {
      Thread t = new Thread(this._group, r);
      t.setName(this._name + "-" + this._threadNumber.getAndIncrement());
      t.setPriority(this._prio);
      return t;
    }
  }
  
  private final int _pcSchedulerPoolSize = 1 + Config.MAX_LINK_AMOUNT / 10;
  
  private GeneralThreadPool() {
    this._executor = Executors.newCachedThreadPool();
    this._scheduler = Executors.newScheduledThreadPool(100, new PriorityThreadFactory("GerenalSTPool", 5));
    this._pcScheduler = Executors.newScheduledThreadPool(this._pcSchedulerPoolSize, new PriorityThreadFactory("PcMonitorSTPool", 5));
  }
  
  public void execute(Runnable r) {
    if (this._executor == null) {
      Thread t = new Thread(r);
      t.start();
    } else {
      this._executor.execute(r);
    } 
  }
  
  public void execute(Thread t) {
    t.start();
  }
  
  public ScheduledFuture<?> schedule(Runnable r, long delay) {
    try {
      if (delay <= 0L) {
        this._executor.execute(r);
        return null;
      } 
      return this._scheduler.schedule(r, delay, TimeUnit.MILLISECONDS);
    } catch (RejectedExecutionException e) {
      return null;
    } 
  }
  
  public ScheduledFuture<?> scheduleAtFixedRate(Runnable r, long initialDelay, long period) {
    return this._scheduler.scheduleAtFixedRate(r, initialDelay, period, TimeUnit.MILLISECONDS);
  }
  
  public void cancel(ScheduledFuture<?> future, boolean mayInterruptIfRunning) {
    try {
      future.cancel(mayInterruptIfRunning);
    } catch (RejectedExecutionException e) {
      this.log.error(e.getLocalizedMessage(), e);
    } 
  }
}
