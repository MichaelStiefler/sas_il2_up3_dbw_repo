// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GCanvas.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GColor, GPoint, GSize, GRegion, 
//            GFont, GTexture, GMesh

public abstract class GCanvas
{

    public boolean preRender(com.maddox.gwindow.GTexture gtexture, float f, float f1)
    {
        return false;
    }

    public boolean draw(com.maddox.gwindow.GTexture gtexture, float f, float f1, float f2, float f3, float f4, float f5)
    {
        return false;
    }

    public boolean preRender(com.maddox.gwindow.GMesh gmesh, float f, float f1)
    {
        return false;
    }

    public boolean draw(com.maddox.gwindow.GMesh gmesh, float f, float f1)
    {
        return false;
    }

    public boolean draw(java.lang.String s)
    {
        return false;
    }

    public boolean draw(java.lang.String s, int i, int j)
    {
        return false;
    }

    public boolean draw(char ac[], int i, int j)
    {
        return false;
    }

    public void copyToClipboard(java.lang.String s)
    {
    }

    public java.lang.String pasteFromClipboard()
    {
        return "";
    }

    protected GCanvas(com.maddox.gwindow.GSize gsize)
    {
        color = new GColor();
        alpha = 255;
        cur = new GPoint();
        org = new GPoint();
        size = new GSize(gsize);
        clip = new GRegion(0.0F, 0.0F, gsize.dx, gsize.dy);
    }

    public com.maddox.gwindow.GSize size;
    public com.maddox.gwindow.GFont font;
    public com.maddox.gwindow.GColor color;
    public int alpha;
    public com.maddox.gwindow.GPoint cur;
    public com.maddox.gwindow.GPoint org;
    public com.maddox.gwindow.GRegion clip;
}
