// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GFont.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GSize

public abstract class GFont
{
    public static class Loader
    {

        public com.maddox.gwindow.GFont load(java.lang.String s)
        {
            return null;
        }

        public Loader()
        {
        }
    }


    public GFont()
    {
        height = 10F;
        descender = -2F;
    }

    public void size(java.lang.String s, com.maddox.gwindow.GSize gsize)
    {
    }

    public void size(java.lang.String s, int i, int j, com.maddox.gwindow.GSize gsize)
    {
    }

    public void size(char ac[], int i, int j, com.maddox.gwindow.GSize gsize)
    {
    }

    public com.maddox.gwindow.GSize size(java.lang.String s)
    {
        size(s, textSize);
        return textSize;
    }

    public com.maddox.gwindow.GSize size(java.lang.String s, int i, int j)
    {
        size(s, i, j, textSize);
        return textSize;
    }

    public com.maddox.gwindow.GSize size(char ac[], int i, int j)
    {
        size(ac, i, j, textSize);
        return textSize;
    }

    public int len(java.lang.String s, float f, boolean flag, boolean flag1)
    {
        return 0;
    }

    public int len(java.lang.String s, int i, int j, float f, boolean flag, boolean flag1)
    {
        return 0;
    }

    public int len(char ac[], int i, int j, float f, boolean flag, boolean flag1)
    {
        return 0;
    }

    public void resolutionChanged()
    {
    }

    public static com.maddox.gwindow.GFont New(java.lang.String s)
    {
        return loader.load(s);
    }

    public float height;
    public float descender;
    private static com.maddox.gwindow.GSize textSize = new GSize();
    public static com.maddox.gwindow.Loader loader;

}
