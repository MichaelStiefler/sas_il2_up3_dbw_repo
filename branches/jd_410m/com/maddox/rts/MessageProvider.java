package com.maddox.rts;

public class MessageProvider
  implements MessageProxy
{
  private Object listener;

  public Object getListener()
  {
    return this.listener;
  }

  public void setListener(Object paramObject)
  {
    this.listener = paramObject;
  }

  public Object getListener(Message paramMessage)
  {
    if ((this.listener != null) && ((this.listener instanceof Destroy)) && 
      (((Destroy)this.listener).isDestroyed()))
      this.listener = null;
    return this.listener;
  }

  public MessageProvider(Object paramObject)
  {
    this.listener = paramObject;
  }

  public MessageProvider()
  {
    this.listener = null;
  }
}