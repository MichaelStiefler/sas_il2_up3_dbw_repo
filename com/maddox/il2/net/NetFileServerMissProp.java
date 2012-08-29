// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetFileServerMissProp.java

package com.maddox.il2.net;

import com.maddox.rts.net.NetFileServerDef;

public class NetFileServerMissProp extends com.maddox.rts.net.NetFileServerDef
{

    public int compressMethod()
    {
        return 2;
    }

    public int compressBlockSize()
    {
        return 32768;
    }

    public java.lang.String primaryPath()
    {
        return "missions";
    }

    public java.lang.String alternativePath()
    {
        return "missions/Net/Cache";
    }

    public NetFileServerMissProp(int i)
    {
        super(i);
    }
}
