package com.maddox.rts;

public abstract interface NetConnect
{
  public abstract void bindEnable(boolean paramBoolean);

  public abstract boolean isBindEnable();

  public abstract void join(NetSocket paramNetSocket, NetAddress paramNetAddress, int paramInt);

  public abstract void joinBreak();

  public abstract boolean isJoinProcess();

  public abstract void msgRequest(String paramString);

  public abstract void channelCreated(NetChannel paramNetChannel);

  public abstract void channelNotCreated(NetChannel paramNetChannel, String paramString);

  public abstract void channelDestroying(NetChannel paramNetChannel, String paramString);
}