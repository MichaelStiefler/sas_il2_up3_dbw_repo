// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GSize.java

package com.maddox.gwindow;


public class GSize
{

    public void add(float f, float f1)
    {
        dx += f;
        dy += f1;
    }

    public void add(com.maddox.gwindow.GSize gsize)
    {
        dx += gsize.dx;
        dy += gsize.dy;
    }

    public void sub(float f, float f1)
    {
        dx -= f;
        dy -= f1;
    }

    public void sub(com.maddox.gwindow.GSize gsize)
    {
        dx -= gsize.dx;
        dy -= gsize.dy;
    }

    public void set(float f, float f1)
    {
        dx = f;
        dy = f1;
    }

    public void set(com.maddox.gwindow.GSize gsize)
    {
        dx = gsize.dx;
        dy = gsize.dy;
    }

    public GSize()
    {
        dx = 0.0F;
        dy = 0.0F;
    }

    public GSize(float f, float f1)
    {
        dx = 0.0F;
        dy = 0.0F;
        set(f, f1);
    }

    public GSize(com.maddox.gwindow.GSize gsize)
    {
        dx = 0.0F;
        dy = 0.0F;
        set(gsize);
    }

    public float dx;
    public float dy;
}
