package com.maddox.rts;

public abstract interface MsgJoyListener
{
  public abstract void msgJoyButton(int paramInt1, int paramInt2, boolean paramBoolean);

  public abstract void msgJoyMove(int paramInt1, int paramInt2, int paramInt3);

  public abstract void msgJoyPov(int paramInt1, int paramInt2);

  public abstract void msgJoyPoll();
}