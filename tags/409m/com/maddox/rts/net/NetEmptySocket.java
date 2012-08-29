// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetEmptySocket.java

package com.maddox.rts.net;

import com.maddox.rts.NetSocket;

public class NetEmptySocket extends com.maddox.rts.NetSocket
{

    public NetEmptySocket()
    {
    }

    public java.lang.Class addressClass()
    {
        return com.maddox.rts.net.NetEmptyAddress.class;
    }
}
