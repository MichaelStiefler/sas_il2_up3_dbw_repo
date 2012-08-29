package com.maddox.il2.net;

import com.maddox.rts.NetChannel;

public abstract interface NetChannelListener
{
  public abstract void netChannelCanceled(String paramString);

  public abstract void netChannelCreated(NetChannel paramNetChannel);

  public abstract void netChannelDestroying(NetChannel paramNetChannel, String paramString);

  public abstract void netChannelRequest(String paramString);
}