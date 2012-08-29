package com.maddox.rts;

public abstract interface MessageListener
{
  public abstract Object getParentListener(Message paramMessage);
}