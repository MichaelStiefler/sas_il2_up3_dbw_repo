// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetMsgFiltered.java

package com.maddox.rts;

import java.io.IOException;
import java.util.List;

// Referenced classes of package com.maddox.rts:
//            NetMsgOutput, NetChannel, NetEnv, NetMsgInput

public class NetMsgFiltered extends com.maddox.rts.NetMsgOutput
{

    public void setFilterArg(java.lang.Object obj)
        throws java.io.IOException
    {
        checkLock();
        filterArg = obj;
    }

    public java.lang.Object filterArg()
    {
        return filterArg;
    }

    public void setIncludeTime(boolean flag)
        throws java.io.IOException
    {
        checkLock();
        bIncludeTime = flag;
    }

    public boolean isIncludeTime()
    {
        return bIncludeTime;
    }

    public void setPrior(float f)
        throws java.io.IOException
    {
        checkLock();
        if(f < 0.0F)
            f = 0.0F;
        if(f > 1.0F)
            f = 1.0F;
        prior = f;
    }

    public float prior()
    {
        return prior;
    }

    public float lastDinamicPrior()
    {
        return _prior;
    }

    public void unLock()
    {
        if(!isLocked())
            return;
        com.maddox.rts.NetEnv.cur();
        java.util.List list = com.maddox.rts.NetEnv.channels();
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)list.get(j);
            if(netchannel.unLockMessage(this))
                return;
        }

    }

    public void unLock(com.maddox.rts.NetChannel netchannel)
    {
        if(!isLocked())
            return;
        if(netchannel != null && netchannel.unLockMessage(this))
        {
            return;
        } else
        {
            unLock();
            return;
        }
    }

    public void unLockAndClear()
        throws java.io.IOException
    {
        unLock();
        clear();
    }

    public void unLockAndSet(com.maddox.rts.NetMsgInput netmsginput, int i)
        throws java.io.IOException
    {
        unLock();
        clear();
        writeMsg(netmsginput, i);
    }

    public NetMsgFiltered()
    {
        bIncludeTime = false;
        prior = 0.5F;
    }

    public NetMsgFiltered(byte abyte0[])
    {
        super(abyte0);
        bIncludeTime = false;
        prior = 0.5F;
    }

    public NetMsgFiltered(int i)
    {
        super(i);
        bIncludeTime = false;
        prior = 0.5F;
    }

    public NetMsgFiltered(com.maddox.rts.NetMsgInput netmsginput, int i)
        throws java.io.IOException
    {
        super(netmsginput, i);
        bIncludeTime = false;
        prior = 0.5F;
    }

    protected long _time;
    protected long _timeStamp;
    protected float _prior;
    private boolean bIncludeTime;
    protected float prior;
    private java.lang.Object filterArg;
}
