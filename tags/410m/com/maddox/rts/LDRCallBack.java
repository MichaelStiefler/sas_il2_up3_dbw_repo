// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LDRCallBack.java

package com.maddox.rts;

import java.io.InputStream;

public abstract class LDRCallBack
{

    public LDRCallBack()
    {
    }

    protected byte[] load(java.lang.String s)
    {
        return null;
    }

    protected java.io.InputStream open(java.lang.String s)
    {
        return null;
    }
}
