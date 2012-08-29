package com.maddox.rts;

public abstract interface MsgNetAskNakListener
{
  public abstract void msgNetAsk(NetMsgGuaranted paramNetMsgGuaranted, NetChannel paramNetChannel);

  public abstract void msgNetNak(NetMsgGuaranted paramNetMsgGuaranted, NetChannel paramNetChannel);
}