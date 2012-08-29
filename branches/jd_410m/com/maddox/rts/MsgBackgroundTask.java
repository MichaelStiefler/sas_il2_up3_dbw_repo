package com.maddox.rts;

public class MsgBackgroundTask extends Message
{
  public static final int START = 0;
  public static final int STEP = 1;
  public static final int STOP = 2;
  protected int id;

  public static void post(Object[] paramArrayOfObject, int paramInt, BackgroundTask paramBackgroundTask)
  {
    if (paramArrayOfObject == null) return;
    MsgBackgroundTask localMsgBackgroundTask = new MsgBackgroundTask();
    localMsgBackgroundTask.id = paramInt;
    localMsgBackgroundTask.post(64, paramArrayOfObject, Time.currentReal(), paramBackgroundTask);
  }

  public boolean invokeListener(Object paramObject) {
    if ((paramObject instanceof MsgBackgroundTaskListener)) {
      switch (this.id) {
      case 0:
        ((MsgBackgroundTaskListener)paramObject).msgBackgroundTaskStarted((BackgroundTask)this._sender);
        break;
      case 1:
        ((MsgBackgroundTaskListener)paramObject).msgBackgroundTaskStep((BackgroundTask)this._sender);
        break;
      case 2:
        ((MsgBackgroundTaskListener)paramObject).msgBackgroundTaskStoped((BackgroundTask)this._sender);
        break;
      }

      return true;
    }
    return false;
  }
}