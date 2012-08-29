// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgTimerParam.java

package com.maddox.rts;


public class MsgTimerParam
{

    public MsgTimerParam()
    {
        id = 0;
        tickPos = 0;
        startTime = 0L;
        countPost = -1;
        stepTime = 100;
        bSkip = false;
        bSkipBegin = true;
    }

    public MsgTimerParam(int i, int j, long l, int k, int i1, boolean flag, 
            boolean flag1)
    {
        id = 0;
        tickPos = 0;
        startTime = 0L;
        countPost = -1;
        stepTime = 100;
        bSkip = false;
        bSkipBegin = true;
        id = i;
        tickPos = j;
        startTime = l;
        countPost = k;
        stepTime = i1;
        bSkip = flag;
        bSkipBegin = flag1;
    }

    public int id;
    public int tickPos;
    public long startTime;
    public int countPost;
    public int stepTime;
    public boolean bSkip;
    public boolean bSkipBegin;
    public long nextTime;
    public int curCount;
}
