// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIPocket.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogControl;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.il2.engine.Config;

public class GUIPocket extends com.maddox.gwindow.GWindowDialogControl
{

    private static void init()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(texture != null)
                return;
            texture = com.maddox.gwindow.GTexture.New("GUI/game/staticelements.mat");
            bevel = new GBevel();
            bevel.set(new GRegion(0.0F, 0.0F, 256F, 32F), new GRegion(9F, 0.0F, 240F, 32F));
            bevelEmpty = new GBevel();
            bevelEmpty.set(new GRegion(0.0F, 224F, 256F, 32F), new GRegion(9F, 224F, 240F, 32F));
        }
    }

    public void render()
    {
        setCanvasColorWHITE();
        if(bEnable)
        {
            lookAndFeel().drawBevel(this, 0.0F, 0.0F, win.dx, win.dy, bevel, texture);
            lookAndFeel().renderTextDialogControl(this, 9F, 0.0F, win.dx - 9F - 7F, win.dy, color, false);
        } else
        {
            lookAndFeel().drawBevel(this, 0.0F, 0.0F, win.dx, win.dy, bevelEmpty, texture);
        }
    }

    public GUIPocket(com.maddox.gwindow.GWindow gwindow, java.lang.String s)
    {
        super(gwindow);
        com.maddox.il2.gui.GUIPocket.init();
        cap = new GCaption(s);
        toolTip = toolTip;
        align = 1;
    }

    static com.maddox.gwindow.GTexture texture;
    static com.maddox.gwindow.GBevel bevel;
    static com.maddox.gwindow.GBevel bevelEmpty;
}
