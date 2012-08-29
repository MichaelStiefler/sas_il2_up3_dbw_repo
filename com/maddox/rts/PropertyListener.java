// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Property.java

package com.maddox.rts;

import java.lang.ref.WeakReference;

class PropertyListener extends java.lang.ref.WeakReference
{

    public boolean isRealTime()
    {
        return (flags & 1) != 0;
    }

    public boolean isSend()
    {
        return (flags & 2) != 0;
    }

    public java.lang.Object listener()
    {
        return get();
    }

    public PropertyListener(java.lang.Object obj, boolean flag, boolean flag1)
    {
        super(obj);
        flags = (flag ? 1 : 0) | (flag1 ? 2 : 0);
    }

    private static final int REAL_TIME = 1;
    private static final int SEND = 2;
    private int flags;
}
