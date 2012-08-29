// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIInfoTop.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.il2.engine.Config;

public class GUIInfoTop extends com.maddox.gwindow.GWindowDialogClient
{

    private static void init()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(texture != null)
                return;
            texture = com.maddox.gwindow.GTexture.New("GUI/game/basicelements.mat");
            bevel = new GBevel();
            bevel.set(new GRegion(48F, 240F, 16F, 16F), new GRegion(55F, 247F, 2.0F, 2.0F));
        }
    }

    public GUIInfoTop()
    {
        com.maddox.il2.gui.GUIInfoTop.init();
    }

    public float M(float f)
    {
        return lookAndFeel().metric(f);
    }

    public void render()
    {
        setCanvasColorWHITE();
        lookAndFeel().drawBevel(this, 0.0F, 0.0F, win.dx, win.dy, bevel, texture);
    }

    public void setPosSize()
    {
        set1024PosSize(0.0F, 0.0F, 1024F, 32F);
    }

    public void resolutionChanged()
    {
        setPosSize();
        super.resolutionChanged();
    }

    static com.maddox.gwindow.GTexture texture;
    static com.maddox.gwindow.GBevel bevel;
}
