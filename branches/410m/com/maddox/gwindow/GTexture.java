// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GTexture.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GSize

public abstract class GTexture
{
    public static class Loader
    {

        public com.maddox.gwindow.GTexture load(java.lang.String s)
        {
            return null;
        }

        public Loader()
        {
        }
    }


    public GTexture()
    {
    }

    public static com.maddox.gwindow.GTexture New(java.lang.String s)
    {
        return loader.load(s);
    }

    public com.maddox.gwindow.GSize size;
    public static com.maddox.gwindow.Loader loader;
}
