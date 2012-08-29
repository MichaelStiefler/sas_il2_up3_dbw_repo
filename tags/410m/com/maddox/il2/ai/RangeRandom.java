// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RangeRandom.java

package com.maddox.il2.ai;

import java.util.Random;

public class RangeRandom extends java.util.Random
{

    public RangeRandom(long l)
    {
        super(l);
        countAccess = 0;
    }

    public RangeRandom()
    {
        super(1L);
        countAccess = 0;
    }

    public int nextInt(int i, int j)
    {
        int k = j - i;
        if(k < 0)
            k = -k;
        return i + nextInt(k + 1);
    }

    public long nextLong(long l, long l1)
    {
        long l2 = l1 - l;
        if(l2 < 0L)
            l2 = -l2;
        return l + nextLong() % (l2 + 1L);
    }

    public double nextDouble(double d, double d1)
    {
        return d + (d1 - d) * nextDouble();
    }

    public float nextFloat(float f, float f1)
    {
        return f + (f1 - f) * nextFloat();
    }

    public float nextFloat_Dome(float f, float f1)
    {
        float f2 = nextFloat();
        float f3 = nextFloat();
        float f4 = nextFloat();
        float f5 = (f2 + f3 + f4) * 0.3333333F;
        return f + (f1 - f) * f5;
    }

    public float nextFloat_DomeInv(float f, float f1)
    {
        float f2 = nextFloat();
        float f3 = nextFloat();
        float f4 = nextFloat();
        float f5 = (f2 + f3 + f4) * 0.3333333F;
        if(f5 >= 0.5F)
            f5 -= 0.5F;
        else
            f5 += 0.5F;
        return f + (f1 - f) * f5;
    }

    public float nextFloat_Fade(float f, float f1)
    {
        float f2 = nextFloat();
        float f3 = nextFloat();
        float f4 = f2 * f3;
        return f + (f1 - f) * f4;
    }

    public int countAccess()
    {
        return countAccess;
    }

    protected synchronized int next(int i)
    {
        countAccess++;
        return super.next(i);
    }

    private int countAccess;
}
