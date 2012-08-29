// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetAddress.java

package com.maddox.rts;

import java.net.UnknownHostException;

public abstract class NetAddress
{

    public NetAddress()
    {
    }

    public java.lang.String getHostName()
    {
        return null;
    }

    public byte[] getAddress()
    {
        return null;
    }

    public java.lang.String getHostAddress()
    {
        return null;
    }

    public java.lang.String toString()
    {
        return getHostAddress();
    }

    public void create(java.lang.String s)
        throws java.net.UnknownHostException
    {
    }

    public com.maddox.rts.NetAddress getLocalHost()
        throws java.net.UnknownHostException
    {
        return null;
    }

    public com.maddox.rts.NetAddress getByName(java.lang.String s)
        throws java.net.UnknownHostException
    {
        return null;
    }

    public com.maddox.rts.NetAddress[] getAllByName(java.lang.String s)
        throws java.net.UnknownHostException
    {
        return null;
    }
}
