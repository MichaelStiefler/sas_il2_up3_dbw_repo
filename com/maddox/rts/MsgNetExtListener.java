package com.maddox.rts;

public abstract interface MsgNetExtListener
{
  public abstract void msgNetExt(byte[] paramArrayOfByte, NetSocket paramNetSocket, NetAddress paramNetAddress, int paramInt);
}