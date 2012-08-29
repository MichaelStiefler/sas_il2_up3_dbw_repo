// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Geom.java

package com.maddox.JGP;


public class Geom
{

    public Geom()
    {
    }

    public static final float DEG2RAD(float f)
    {
        return f * 0.01745329F;
    }

    public static final float RAD2DEG(float f)
    {
        return f * 57.29578F;
    }

    public static final float sin(float f)
    {
        return (float)java.lang.Math.sin(f);
    }

    public static final float sinDeg(float f)
    {
        return (float)java.lang.Math.sin(com.maddox.JGP.Geom.DEG2RAD(f));
    }

    public static final float cos(float f)
    {
        return (float)java.lang.Math.cos(f);
    }

    public static final float cosDeg(float f)
    {
        return (float)java.lang.Math.cos(com.maddox.JGP.Geom.DEG2RAD(f));
    }

    public static final float tan(float f)
    {
        return (float)java.lang.Math.tan(f);
    }

    public static final float tanDeg(float f)
    {
        return (float)java.lang.Math.tan(com.maddox.JGP.Geom.DEG2RAD(f));
    }

    public static final float interpolate(float f, float f1, float f2)
    {
        return f + (f1 - f) * f2;
    }

    public static final float PI = 3.141593F;
}
