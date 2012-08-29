package com.maddox.rts;

public class MsgMainWindow extends Message
{
  public static final int CREATED = 1;
  public static final int DESTROYED = 2;
  public static final int RESIZED = 4;
  public static final int MOVED = 8;
  public static final int FOCUSCHANGED = 16;
  protected int id;

  protected void Send(int paramInt, Object paramObject)
  {
    this.id = paramInt;
    send(paramObject);
  }

  public boolean invokeListener(Object paramObject)
  {
    if ((paramObject instanceof MsgMainWindowListener)) {
      ((MsgMainWindowListener)paramObject).msgMainWindow(this.id);
      return true;
    }
    return false;
  }
}