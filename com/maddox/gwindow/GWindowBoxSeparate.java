// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowBoxSeparate.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindow, GWindowLookAndFeel

public class GWindowBoxSeparate extends com.maddox.gwindow.GWindow
{

    public void render()
    {
        lookAndFeel().render(this);
    }

    public boolean isMousePassThrough(float f, float f1)
    {
        return true;
    }

    public void created()
    {
        bAlwaysBehind = true;
        bAcceptsKeyFocus = false;
        bTransient = true;
    }

    public GWindowBoxSeparate(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2, float f3)
    {
        border = 0.0F;
        doNew(gwindow, f, f1, f2, f3, true);
    }

    public com.maddox.gwindow.GWindow exclude;
    public float border;
}
