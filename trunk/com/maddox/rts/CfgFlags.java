package com.maddox.rts;

public abstract interface CfgFlags extends Cfg
{
  public abstract int firstFlag();

  public abstract int countFlags();

  public abstract boolean defaultFlag(int paramInt);

  public abstract String nameFlag(int paramInt);

  public abstract boolean isPermanentFlag(int paramInt);

  public abstract boolean isEnabledFlag(int paramInt);

  public abstract boolean get(int paramInt);

  public abstract void set(int paramInt, boolean paramBoolean);

  public abstract int apply(int paramInt);

  public abstract int applyStatus(int paramInt);
}