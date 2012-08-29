package com.maddox.rts;

public abstract interface NetChannelCallbackStream
{
  public abstract boolean netChannelCallback(NetChannelOutStream paramNetChannelOutStream, NetMsgGuaranted paramNetMsgGuaranted);

  public abstract void netChannelCallback(NetChannelInStream paramNetChannelInStream, NetMsgGuaranted paramNetMsgGuaranted);

  public abstract boolean netChannelCallback(NetChannelInStream paramNetChannelInStream, NetMsgInput paramNetMsgInput);
}