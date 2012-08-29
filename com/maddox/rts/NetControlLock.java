// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetControlLock.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            NetObj, NetException, NetEnv, NetChannel

public final class NetControlLock extends com.maddox.rts.NetObj
{

    public com.maddox.rts.NetChannel channel()
    {
        return lockChannel;
    }

    public void destroy()
    {
        if(com.maddox.rts.NetEnv.cur().control == this)
            com.maddox.rts.NetEnv.cur().control = null;
        super.destroy();
    }

    public NetControlLock(com.maddox.rts.NetChannel netchannel)
    {
        super(null);
        if(com.maddox.rts.NetEnv.cur().control != null)
        {
            super.destroy();
            throw new NetException("net control slot alredy used");
        } else
        {
            com.maddox.rts.NetEnv.cur().control = this;
            lockChannel = netchannel;
            return;
        }
    }

    private com.maddox.rts.NetChannel lockChannel;
}
