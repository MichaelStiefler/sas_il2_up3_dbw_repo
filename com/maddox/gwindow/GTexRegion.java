// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GTexRegion.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GRegion, GTexture, GPoint, GSize

public class GTexRegion extends com.maddox.gwindow.GRegion
{

    public void set(com.maddox.gwindow.GTexture gtexture, float f, float f1, float f2, float f3)
    {
        x = f;
        y = f1;
        dx = f2;
        dy = f3;
        texture = gtexture;
    }

    public void set(com.maddox.gwindow.GTexture gtexture, com.maddox.gwindow.GPoint gpoint, com.maddox.gwindow.GSize gsize)
    {
        super.set(gpoint, gsize);
        texture = gtexture;
    }

    public void set(com.maddox.gwindow.GTexRegion gtexregion)
    {
        super.set(gtexregion);
        texture = gtexregion.texture;
    }

    public GTexRegion()
    {
    }

    public GTexRegion(com.maddox.gwindow.GTexture gtexture, float f, float f1, float f2, float f3)
    {
        set(gtexture, f, f1, f2, f3);
    }

    public GTexRegion(com.maddox.gwindow.GTexture gtexture, com.maddox.gwindow.GPoint gpoint, com.maddox.gwindow.GSize gsize)
    {
        set(gtexture, gpoint, gsize);
    }

    public GTexRegion(java.lang.String s, float f, float f1, float f2, float f3)
    {
        set(f, f1, f2, f3);
        texture = com.maddox.gwindow.GTexture.New(s);
    }

    public GTexRegion(java.lang.String s, com.maddox.gwindow.GPoint gpoint, com.maddox.gwindow.GSize gsize)
    {
        set(gpoint, gsize);
        texture = com.maddox.gwindow.GTexture.New(s);
    }

    public GTexRegion(com.maddox.gwindow.GTexRegion gtexregion)
    {
        set(gtexregion);
    }

    public com.maddox.gwindow.GTexture texture;
}
