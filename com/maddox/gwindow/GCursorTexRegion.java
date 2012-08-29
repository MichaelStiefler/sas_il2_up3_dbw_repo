// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GCursorTexRegion.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GCursor, GWindowRoot, GCanvas, GPoint, 
//            GColor, GTexture

public class GCursorTexRegion extends com.maddox.gwindow.GCursor
{

    public void preRender(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        gwindowroot.C.cur.x = gwindowroot.mousePos.x - hotX;
        gwindowroot.C.cur.y = gwindowroot.mousePos.y - hotY;
        gwindowroot.C.preRender(tex, dx, dy);
    }

    public void render(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        gwindowroot.C.cur.x = gwindowroot.mousePos.x - hotX;
        gwindowroot.C.cur.y = gwindowroot.mousePos.y - hotY;
        gwindowroot.C.color.setWhite();
        gwindowroot.C.draw(tex, dx, dy, x, y, dx, dy);
    }

    public GCursorTexRegion(com.maddox.gwindow.GTexture gtexture, float f, float f1, float f2, float f3, float f4, float f5, 
            int i)
    {
        tex = gtexture;
        hotX = f4;
        hotY = f5;
        x = f;
        y = f1;
        dx = f2;
        dy = f3;
        nativeCursor = i;
    }

    public GCursorTexRegion(java.lang.String s, float f, float f1, float f2, float f3, float f4, float f5, 
            int i)
    {
        this(com.maddox.gwindow.GTexture.New(s), f, f1, f2, f3, f4, f5, i);
    }

    public com.maddox.gwindow.GTexture tex;
    public float x;
    public float y;
    public float dx;
    public float dy;
    public float hotX;
    public float hotY;
}
