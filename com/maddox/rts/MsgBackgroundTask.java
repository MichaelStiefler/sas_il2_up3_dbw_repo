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
        ((MsgBackgroundTaskListener)paramObject).msgBackgroundTaskStarted((BackgroundTask)this.jdField__sender_of_type_JavaLangObject);
        break;
      case 1:
        ((MsgBackgroundTaskListener)paramObject).msgBackgroundTaskStep((BackgroundTask)this.jdField__sender_of_type_JavaLangObject);
        break;
      case 2:
        ((MsgBackgroundTaskListener)paramObject).msgBackgroundTaskStoped((BackgroundTask)this.jdField__sender_of_type_JavaLangObject);
        break;
      }

      return true;
    }
    return false;
  }
}