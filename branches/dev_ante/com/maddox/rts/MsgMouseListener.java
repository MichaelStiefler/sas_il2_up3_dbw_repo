package com.maddox.rts;

public abstract interface MsgMouseListener
{
  public abstract void msgMouseButton(int paramInt, boolean paramBoolean);

  public abstract void msgMouseMove(int paramInt1, int paramInt2, int paramInt3);

  public abstract void msgMouseAbsMove(int paramInt1, int paramInt2, int paramInt3);
}