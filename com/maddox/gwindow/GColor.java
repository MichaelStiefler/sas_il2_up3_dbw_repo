// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GColor.java

package com.maddox.gwindow;


public class GColor
{

    public int r()
    {
        return color & 0xff;
    }

    public int g()
    {
        return color >> 8 & 0xff;
    }

    public int b()
    {
        return color >> 16 & 0xff;
    }

    public void get(com.maddox.gwindow.GColor gcolor)
    {
        gcolor.color = color;
    }

    public void setWhite()
    {
        color = 0xffffff;
    }

    public void setBlack()
    {
        color = 0;
    }

    public void set(int i, int j, int k)
    {
        color = i & 0xff | (j & 0xff) << 8 | (k & 0xff) << 16;
    }

    public void set(com.maddox.gwindow.GColor gcolor)
    {
        color = gcolor.color;
    }

    public GColor(int i, int j, int k)
    {
        color = 0xffffff;
        set(i, j, k);
    }

    public GColor(com.maddox.gwindow.GColor gcolor)
    {
        color = 0xffffff;
        set(gcolor);
    }

    public GColor()
    {
        color = 0xffffff;
    }

    public static final com.maddox.gwindow.GColor Brass = new GColor(199, 179, 148);
    public static final com.maddox.gwindow.GColor White = new GColor(255, 255, 255);
    public static final com.maddox.gwindow.GColor Line = new GColor(168, 164, 0);
    public static final com.maddox.gwindow.GColor Gray = new GColor(207, 208, 208);
    public static final com.maddox.gwindow.GColor Black = new GColor(0, 0, 0);
    public static final com.maddox.gwindow.GColor Red = new GColor(255, 0, 0);
    public static final com.maddox.gwindow.GColor Green = new GColor(0, 255, 0);
    public static final com.maddox.gwindow.GColor Blue = new GColor(0, 0, 255);
    public static final com.maddox.gwindow.GColor Yellow = new GColor(255, 255, 0);
    public static final int WHITE = 0xffffff;
    public static final int BLACK = 0;
    public int color;

}
