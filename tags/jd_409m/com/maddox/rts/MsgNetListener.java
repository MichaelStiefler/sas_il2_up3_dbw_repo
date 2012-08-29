package com.maddox.rts;

public abstract interface MsgNetListener
{
  public abstract void msgNet(NetMsgInput paramNetMsgInput);

  public abstract void msgNetNewChannel(NetChannel paramNetChannel);

  public abstract void msgNetDelChannel(NetChannel paramNetChannel);
}