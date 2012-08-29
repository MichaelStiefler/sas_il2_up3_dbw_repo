// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GMesh.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GSize

public abstract class GMesh
{
    public static class Loader
    {

        public com.maddox.gwindow.GMesh load(java.lang.String s)
        {
            return null;
        }

        public com.maddox.gwindow.GMesh loadShared(java.lang.String s)
        {
            return null;
        }

        public Loader()
        {
        }
    }


    public GMesh()
    {
    }

    public static com.maddox.gwindow.GMesh New(java.lang.String s)
    {
        return loader.load(s);
    }

    public static com.maddox.gwindow.GMesh NewShared(java.lang.String s)
    {
        return loader.loadShared(s);
    }

    public com.maddox.gwindow.GSize size;
    public static com.maddox.gwindow.Loader loader;
}
