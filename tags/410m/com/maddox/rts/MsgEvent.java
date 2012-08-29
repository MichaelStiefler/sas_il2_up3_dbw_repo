// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   KeyRecord.java

package com.maddox.rts;


class MsgEvent
{

    public MsgEvent()
    {
        arg0 = null;
        arg1 = null;
        p1 = p2 = p3 = p4 = p5 = p6 = p7 = p8 = p9 = -1;
    }

    public MsgEvent(long l, int i, int j, int k)
    {
        arg0 = null;
        arg1 = null;
        time = l;
        id = i;
        p1 = j;
        p2 = k;
        p3 = p4 = p5 = p6 = p7 = p8 = p9 = -1;
    }

    public MsgEvent(long l, int i, int j, int k, int i1)
    {
        arg0 = null;
        arg1 = null;
        time = l;
        id = i;
        p1 = j;
        p2 = k;
        p3 = i1;
        p4 = p5 = p6 = p7 = p8 = p9 = -1;
    }

    public MsgEvent(long l, int i, int j, int k, int i1, int j1, 
            int k1, int l1, int i2, int j2, int k2)
    {
        arg0 = null;
        arg1 = null;
        time = l;
        id = i;
        p1 = j;
        p2 = k;
        p3 = i1;
        p4 = j1;
        p5 = k1;
        p6 = l1;
        p7 = i2;
        p8 = j2;
        p9 = k2;
    }

    public MsgEvent(long l, int i, int j, int k, int i1, int j1, 
            int k1, int l1, int i2, int j2)
    {
        arg0 = null;
        arg1 = null;
        time = l;
        id = i;
        p1 = j;
        p2 = k;
        p3 = i1;
        p4 = j1;
        p5 = k1;
        p6 = l1;
        p7 = i2;
        p8 = j2;
        p9 = -1;
    }

    public MsgEvent(long l, int i, int j, int k, int i1, int j1)
    {
        arg0 = null;
        arg1 = null;
        time = l;
        id = i;
        p1 = j;
        p2 = k;
        p3 = i1;
        p4 = j1;
        p5 = p6 = p7 = p8 = p9 = -1;
    }

    public MsgEvent(long l, int i, int j)
    {
        arg0 = null;
        arg1 = null;
        time = l;
        id = i;
        p1 = j;
        p2 = 0;
        p3 = p4 = p5 = p6 = p7 = p8 = p9 = -1;
    }

    public MsgEvent(long l, int i, int ai[], int j)
    {
        arg0 = null;
        arg1 = null;
        time = l;
        p1 = p2 = p3 = p4 = p5 = p6 = p7 = p8 = p9 = -1;
        id = i;
        if(j <= 0)
            return;
        p1 = ai[0];
        if(j <= 1)
            return;
        p2 = ai[1];
        if(j <= 2)
            return;
        p3 = ai[2];
        if(j <= 3)
            return;
        p4 = ai[3];
        if(j <= 4)
            return;
        p5 = ai[4];
        if(j <= 5)
            return;
        p6 = ai[5];
        if(j <= 6)
            return;
        p7 = ai[6];
        if(j <= 7)
            return;
        p8 = ai[7];
        if(j <= 8)
        {
            return;
        } else
        {
            p9 = ai[8];
            return;
        }
    }

    public static final int FINGER = 0;
    public long time;
    public int id;
    public int p1;
    public int p2;
    public int p3;
    public int p4;
    public int p5;
    public int p6;
    public int p7;
    public int p8;
    public int p9;
    public java.lang.String arg0;
    public java.lang.String arg1;
}
