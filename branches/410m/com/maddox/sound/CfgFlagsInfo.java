// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CfgFlagsInfo.java

package com.maddox.sound;


public class CfgFlagsInfo
{

    public CfgFlagsInfo(java.lang.String s, int i, boolean flag, boolean flag1)
    {
        name = s;
        code = i;
        value = flag;
        isDefault = flag1;
    }

    java.lang.String name;
    int code;
    boolean value;
    boolean isDefault;
}
