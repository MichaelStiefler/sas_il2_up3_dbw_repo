// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GCursorTexture.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GCursor, GWindowRoot, GCanvas, GPoint, 
//            GTexture, GSize, GColor

public class GCursorTexture extends com.maddox.gwindow.GCursor
{

    public void preRender(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        gwindowroot.C.cur.x = gwindowroot.mousePos.x - hotX;
        gwindowroot.C.cur.y = gwindowroot.mousePos.y - hotY;
        gwindowroot.C.preRender(tex, tex.size.dx, tex.size.dy);
    }

    public void render(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        gwindowroot.C.cur.x = gwindowroot.mousePos.x - hotX;
        gwindowroot.C.cur.y = gwindowroot.mousePos.y - hotY;
        gwindowroot.C.color.setWhite();
        gwindowroot.C.draw(tex, tex.size.dx, tex.size.dy, 0.0F, 0.0F, tex.size.dx, tex.size.dy);
    }

    public GCursorTexture(com.maddox.gwindow.GTexture gtexture, float f, float f1, int i)
    {
        tex = gtexture;
        hotX = f;
        hotY = f1;
        nativeCursor = i;
    }

    public GCursorTexture(java.lang.String s, float f, float f1, int i)
    {
        this(com.maddox.gwindow.GTexture.New(s), f, f1, i);
    }

    public com.maddox.gwindow.GTexture tex;
    public float hotX;
    public float hotY;
}
