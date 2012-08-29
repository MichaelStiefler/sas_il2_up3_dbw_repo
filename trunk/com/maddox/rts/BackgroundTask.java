package com.maddox.rts;

import java.io.PrintStream;

public class BackgroundTask
{
  protected boolean bRun = false;
  protected boolean bComplete = false;
  protected float percentComplete = 0.0F;
  protected String messageComplete;
  protected boolean bRequestCanceling = false;
  protected String messageCancel;

  public boolean isRun()
  {
    return this.bRun;
  }
  public boolean isComplete() { return this.bComplete; } 
  public float percentComplete() {
    return this.percentComplete;
  }
  public String messageComplete() { return this.messageComplete; }

  public boolean isRequestCanceling() {
    return this.bRequestCanceling;
  }
  public String messageCancel() { return this.messageCancel; }

  public static void addListener(Object paramObject)
  {
    RTSConf.cur.backgroundLoop.listeners.addListener(paramObject);
  }

  public static void removeListener(Object paramObject) {
    RTSConf.cur.backgroundLoop.listeners.removeListener(paramObject);
  }

  protected void beforeStartRun()
  {
    this.bRequestCanceling = false;
    this.bComplete = false;
    this.percentComplete = 0.0F;
    this.messageComplete = null;
    this.bRun = true;
  }

  protected void beforeRun()
  {
  }

  protected void afterRun() {
    this.bComplete = (!this.bRequestCanceling);
    this.bRun = false;
  }

  protected void run()
    throws Exception
  {
  }

  public static boolean step(float paramFloat, String paramString)
  {
    BackgroundTask localBackgroundTask = RTSConf.cur.backgroundLoop.curTask;
    if (localBackgroundTask == null) return true;
    return localBackgroundTask._step(paramFloat, paramString);
  }

  protected boolean _step(float paramFloat, String paramString) {
    this.percentComplete = paramFloat;
    this.messageComplete = paramString;
    MsgBackgroundTask.post(RTSConf.cur.backgroundLoop.listeners.get(), 1, this);

    RTSConf.cur.backgroundLoop.step();
    return !this.bRequestCanceling;
  }

  public static void execute(BackgroundTask paramBackgroundTask)
  {
    if (isExecuted()) {
      throw new RTSException("background task alredy executed");
    }
    RTSConf.cur.backgroundLoop.curTask = paramBackgroundTask;
    paramBackgroundTask.beforeStartRun();
  }

  public static void cancel(String paramString)
  {
    BackgroundTask localBackgroundTask = RTSConf.cur.backgroundLoop.curTask;
    if (localBackgroundTask == null) return;
    localBackgroundTask.bRequestCanceling = true;
    localBackgroundTask.messageCancel = paramString;
  }

  public static boolean isExecuted()
  {
    return RTSConf.cur.backgroundLoop.curTask != null;
  }

  public static BackgroundTask executed()
  {
    return RTSConf.cur.backgroundLoop.curTask;
  }

  public static void doRun()
  {
    if (!isExecuted()) {
      throw new RTSException("background task not found");
    }
    BackgroundTask localBackgroundTask = RTSConf.cur.backgroundLoop.curTask;
    localBackgroundTask.beforeRun();
    MsgBackgroundTask.post(RTSConf.cur.backgroundLoop.listeners.get(), 0, localBackgroundTask);

    RTSConf.cur.backgroundLoop.step();
    try
    {
      localBackgroundTask.run();
    } catch (Exception localException) {
      if (!localBackgroundTask.isRequestCanceling())
        cancel(localException.getMessage());
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }

    RTSConf.cur.backgroundLoop.curTask = null;
    localBackgroundTask.afterRun();
    MsgBackgroundTask.post(RTSConf.cur.backgroundLoop.listeners.get(), 2, localBackgroundTask);

    RTSConf.cur.backgroundLoop.step();
  }
}