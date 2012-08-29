// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetChannelListener.java

package com.maddox.il2.net;

import com.maddox.rts.NetChannel;

public interface NetChannelListener
{

    public abstract void netChannelCanceled(java.lang.String s);

    public abstract void netChannelCreated(com.maddox.rts.NetChannel netchannel);

    public abstract void netChannelDestroying(com.maddox.rts.NetChannel netchannel, java.lang.String s);

    public abstract void netChannelRequest(java.lang.String s);
}
