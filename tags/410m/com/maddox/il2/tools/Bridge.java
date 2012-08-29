// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Bridge.java

package com.maddox.il2.tools;


public class Bridge
{

    public Bridge()
    {
    }

    public java.lang.String toString()
    {
        return "((" + x1 + "," + y1 + "),(" + x2 + "," + y2 + ")," + TypeString(type) + ")";
    }

    private java.lang.String TypeString(int i)
    {
        if((i & 0x80) != 0)
            return "HIGHWAY";
        if((i & 0x40) != 0)
            return "RAIL   ";
        if((i & 0x20) != 0)
            return "ROAD   ";
        else
            return "************* ERROR *************";
    }

    public static final int HIGHWAY = 128;
    public static final int RAIL = 64;
    public static final int ROAD = 32;
    public int x1;
    public int y1;
    public int x2;
    public int y2;
    public int type;
}
