package com.maddox.rts;

public class State
  implements MessageListener
{
  public static final int UNKNOWN = -1;
  public static final int DESTROYED = -2;
  private Object parentListener;

  public Object superObj()
  {
    return this.parentListener;
  }

  public Object getParentListener(Message paramMessage)
  {
    return this.parentListener;
  }

  public void begin(int paramInt)
  {
  }

  public void end(int paramInt)
  {
  }

  protected void setParentListener(Object paramObject)
  {
    this.parentListener = paramObject;
  }

  public State()
  {
    this.parentListener = null;
  }

  public State(Object paramObject)
  {
    this.parentListener = paramObject;
  }
}