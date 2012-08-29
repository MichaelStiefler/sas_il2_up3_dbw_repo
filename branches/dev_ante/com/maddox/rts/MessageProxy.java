package com.maddox.rts;

public abstract interface MessageProxy
{
  public abstract Object getListener(Message paramMessage);
}