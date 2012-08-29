package com.maddox.rts;

public abstract interface MsgBackgroundTaskListener
{
  public abstract void msgBackgroundTaskStarted(BackgroundTask paramBackgroundTask);

  public abstract void msgBackgroundTaskStep(BackgroundTask paramBackgroundTask);

  public abstract void msgBackgroundTaskStoped(BackgroundTask paramBackgroundTask);
}