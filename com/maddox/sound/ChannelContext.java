// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ChannelContext.java

package com.maddox.sound;

import com.maddox.netphone.MixChannel;
import com.maddox.netphone.NetMixer;

public class ChannelContext
{

    ChannelContext(com.maddox.netphone.NetMixer netmixer, boolean flag)
    {
        mc = netmixer.newChannel(flag);
        setActive(false);
    }

    public boolean isActive()
    {
        return mc.isActive();
    }

    public void setActive(boolean flag)
    {
        mc.setActive(flag);
    }

    public void destroy()
    {
        if(mc != null)
        {
            mc.destroy();
            mc = null;
        }
    }

    com.maddox.netphone.MixChannel mc;
}
