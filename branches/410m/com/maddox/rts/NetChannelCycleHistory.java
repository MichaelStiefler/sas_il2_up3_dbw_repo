// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetChannel.java

package com.maddox.rts;


class NetChannelCycleHistory
{

    private void expand()
    {
        int ai[] = seq;
        long al[] = time;
        int i = seq.length - 1;
        seq = new int[ai.length * 2];
        time = new long[ai.length * 2];
        int j = tail;
        head = tail = 0;
        while(i-- >= 0) 
        {
            int k = head++ & seq.length - 1;
            int l = j++ & ai.length - 1;
            seq[k] = ai[l];
            time[k] = al[l];
        }
    }

    public double speed(long l, long l1, double d)
    {
        if(head == tail)
            return d;
        int i = head;
        long l2 = l1;
        int j = 0;
        for(boolean flag = true; --i >= tail && flag;)
        {
            if(time[i & seq.length - 1] < l)
                flag = false;
            l2 = time[i & seq.length - 1];
            j += seq[i & seq.length - 1] >> 16;
        }

        l2 = l1 - l2;
        if(l2 <= 0L)
            return d;
        else
            return (double)j / (double)l2;
    }

    public void put(int i, int j, long l)
    {
        if(head - tail == seq.length - 1)
            expand();
        int k = head++ & seq.length - 1;
        seq[k] = i & 0x3fff | (j & 0xffff) << 16;
        time[k] = l;
    }

    public int getIndex(int i)
    {
        i &= 0x3fff;
        for(int j = head; --j >= tail;)
            if((seq[j & seq.length - 1] & 0x3fff) == i)
            {
                tail = j;
                return j & seq.length - 1;
            }

        return -1;
    }

    public long getTime(int i)
    {
        return time[i];
    }

    public NetChannelCycleHistory(int i)
    {
        seq = new int[i];
        time = new long[i];
    }

    public int seq[];
    public long time[];
    public int head;
    public int tail;
}
