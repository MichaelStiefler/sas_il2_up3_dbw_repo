package com.maddox.rts;

public final class NetControlLock extends NetObj
{
  private NetChannel lockChannel;

  public NetChannel channel()
  {
    return this.lockChannel;
  }
  public void destroy() {
    if (NetEnv.cur().control == this)
      NetEnv.cur().control = null;
    super.destroy();
  }

  public NetControlLock(NetChannel paramNetChannel) {
    super(null);
    if (NetEnv.cur().control != null) {
      super.destroy();
      throw new NetException("net control slot alredy used");
    }
    NetEnv.cur().control = this;
    this.lockChannel = paramNetChannel;
  }
}