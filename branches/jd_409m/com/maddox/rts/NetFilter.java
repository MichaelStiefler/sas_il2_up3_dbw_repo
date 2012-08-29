package com.maddox.rts;

public abstract interface NetFilter
{
  public abstract float filterNetMessage(NetChannel paramNetChannel, NetMsgFiltered paramNetMsgFiltered);

  public abstract void filterNetMessagePosting(NetChannel paramNetChannel, NetMsgFiltered paramNetMsgFiltered);

  public abstract boolean filterEnableAdd(NetChannel paramNetChannel, NetFilter paramNetFilter);
}