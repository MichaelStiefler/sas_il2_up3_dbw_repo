package com.maddox.opengl;

import com.maddox.rts.Message;

public class MsgGLContext extends Message
{
  public static final int CREATED = 1;
  public static final int DESTROYED = 2;
  public static final int RESIZED = 4;
  public static final int RECREATED = 8;
  protected int id;

  protected void Send(int paramInt, Object paramObject)
  {
    this.id = paramInt;
    send(paramObject);
  }

  public boolean invokeListener(Object paramObject)
  {
    if ((paramObject instanceof MsgGLContextListener)) {
      ((MsgGLContextListener)paramObject).msgGLContext(this.id);
      return true;
    }
    return false;
  }
}