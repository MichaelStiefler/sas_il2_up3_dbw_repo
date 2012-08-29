package com.maddox.rts;

public abstract interface MsgPropertyListener
{
  public abstract void msgPropertyAdded(Property paramProperty);

  public abstract void msgPropertyRemoved(Property paramProperty);

  public abstract void msgPropertyChanged(Property paramProperty);
}