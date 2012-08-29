package com.maddox.rts;

public abstract interface CfgInt extends Cfg
{
  public abstract int firstState();

  public abstract int countStates();

  public abstract int defaultState();

  public abstract String nameState(int paramInt);

  public abstract boolean isEnabledState(int paramInt);

  public abstract int get();

  public abstract void set(int paramInt);
}