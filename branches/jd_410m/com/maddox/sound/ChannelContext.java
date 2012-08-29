package com.maddox.sound;

import com.maddox.netphone.MixChannel;
import com.maddox.netphone.NetMixer;

public class ChannelContext
{
  MixChannel mc;

  ChannelContext(NetMixer paramNetMixer, boolean paramBoolean)
  {
    this.mc = paramNetMixer.newChannel(paramBoolean);
    setActive(false);
  }

  public boolean isActive()
  {
    return this.mc.isActive();
  }

  public void setActive(boolean paramBoolean)
  {
    this.mc.setActive(paramBoolean);
  }

  public void destroy()
  {
    if (this.mc != null) {
      this.mc.destroy();
      this.mc = null;
    }
  }
}