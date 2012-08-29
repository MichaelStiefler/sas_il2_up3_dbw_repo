// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MixBase.java

package com.maddox.netphone;

import java.io.PrintStream;

public class MixBase
{

    public MixBase()
    {
    }

    protected void error(java.lang.String s)
    {
        if(enMsg)
            java.lang.System.out.println("ERROR : " + s);
    }

    protected void print(java.lang.String s)
    {
        if(enMsg)
            java.lang.System.out.println(s);
    }

    public static boolean enMsg = false;

}
