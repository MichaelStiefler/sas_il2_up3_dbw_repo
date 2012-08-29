// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GPoint.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GSize

public class GPoint
{

    public void add(float f, float f1)
    {
        x += f;
        y += f1;
    }

    public void add(com.maddox.gwindow.GSize gsize)
    {
        x += gsize.dx;
        y += gsize.dy;
    }

    public void sub(float f, float f1)
    {
        x -= f;
        y -= f1;
    }

    public void sub(com.maddox.gwindow.GSize gsize)
    {
        x -= gsize.dx;
        y -= gsize.dy;
    }

    public void size(com.maddox.gwindow.GPoint gpoint, com.maddox.gwindow.GSize gsize)
    {
        gsize.dx = x - gpoint.x;
        gsize.dy = y - gpoint.y;
    }

    public void set(float f, float f1)
    {
        x = f;
        y = f1;
    }

    public void set(com.maddox.gwindow.GPoint gpoint)
    {
        x = gpoint.x;
        y = gpoint.y;
    }

    public GPoint()
    {
        x = 0.0F;
        y = 0.0F;
    }

    public GPoint(float f, float f1)
    {
        x = 0.0F;
        y = 0.0F;
        set(f, f1);
    }

    public GPoint(com.maddox.gwindow.GPoint gpoint)
    {
        x = 0.0F;
        y = 0.0F;
        set(gpoint);
    }

    public float x;
    public float y;
}
