// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUISeparate.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.il2.engine.Config;

public class GUISeparate extends com.maddox.gwindow.GWindow
{

    private static void init()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(tex != null)
                return;
            tex = new GTexRegion("GUI/game/basicelements.mat", 0.0F, 0.0F, 1.0F, 1.0F);
        }
    }

    public static void draw(com.maddox.gwindow.GWindow gwindow, com.maddox.gwindow.GColor gcolor, float f, float f1, float f2, float f3)
    {
        com.maddox.il2.gui.GUISeparate.init();
        gwindow.setCanvasColor(gcolor.color);
        gwindow.draw(f, f1, f2, f3, tex);
    }

    public static void draw(com.maddox.gwindow.GWindow gwindow, int i, float f, float f1, float f2, float f3)
    {
        com.maddox.il2.gui.GUISeparate.init();
        gwindow.setCanvasColor(i);
        gwindow.draw(f, f1, f2, f3, tex);
    }

    public void render()
    {
        setCanvasColor(color.color);
        draw(0.0F, 0.0F, win.dx, win.dy, tex);
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

    public GUISeparate(com.maddox.gwindow.GWindow gwindow, int i, int j, int k)
    {
        com.maddox.il2.gui.GUISeparate.init();
        color = new GColor(i, j, k);
        doNew(gwindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
    }

    static com.maddox.gwindow.GTexRegion tex;
    public com.maddox.gwindow.GColor color;
}
