// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NumberTokenizer.java

package com.maddox.util;

import java.util.StringTokenizer;

public class NumberTokenizer extends java.util.StringTokenizer
{

    public NumberTokenizer(java.lang.String s, java.lang.String s1, boolean flag)
    {
        super(s, s1, flag);
    }

    public NumberTokenizer(java.lang.String s, java.lang.String s1)
    {
        super(s, s1);
    }

    public NumberTokenizer(java.lang.String s)
    {
        super(s);
    }

    public java.lang.String next()
    {
        return nextToken();
        java.lang.Exception exception;
        exception;
        return null;
    }

    public java.lang.String next(java.lang.String s)
    {
        return nextToken();
        java.lang.Exception exception;
        exception;
        return s;
    }

    public int nextInteger()
    {
        return java.lang.Integer.parseInt(nextToken());
    }

    public int next(int i)
    {
        return java.lang.Integer.parseInt(nextToken());
        java.lang.Exception exception;
        exception;
        return i;
    }

    public int next(int i, int j, int k)
    {
        int l = next(i);
        if(l < j)
            l = j;
        if(l > k)
            l = k;
        return l;
    }

    public float nextFloat()
    {
        return java.lang.Float.parseFloat(nextToken());
    }

    public float next(float f)
    {
        return java.lang.Float.parseFloat(nextToken());
        java.lang.Exception exception;
        exception;
        return f;
    }

    public float next(float f, float f1, float f2)
    {
        float f3 = next(f);
        if(f3 < f1)
            f3 = f1;
        if(f3 > f2)
            f3 = f2;
        return f3;
    }

    public double nextDouble()
    {
        return java.lang.Double.parseDouble(nextToken());
    }

    public double next(double d)
    {
        return java.lang.Double.parseDouble(nextToken());
        java.lang.Exception exception;
        exception;
        return d;
    }

    public double next(double d, double d1, double d2)
    {
        double d3 = next(d);
        if(d3 < d1)
            d3 = d1;
        if(d3 > d2)
            d3 = d2;
        return d3;
    }

    public boolean nextBoolean()
    {
        return nextInteger() != 0;
    }

    public boolean next(boolean flag)
    {
        return next(flag ? 1 : 0) != 0;
    }
}
