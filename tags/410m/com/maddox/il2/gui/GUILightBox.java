// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUILightBox.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCheckBox;

public class GUILightBox extends com.maddox.gwindow.GWindowCheckBox
{

    public void render()
    {
        setCanvasColorWHITE();
        com.maddox.gwindow.GTexRegion gtexregion = texDOWN;
        if(bChecked)
            gtexregion = texUP;
        draw(0.0F, 0.0F, win.dx, win.dy, gtexregion);
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GSize gsize)
    {
        gsize.dx = win.dx;
        gsize.dy = win.dy;
        return gsize;
    }

    public void setPosC(float f, float f1)
    {
        super.setPos(f - win.dx / 2.0F, f1 - win.dy / 2.0F);
    }

    public void resolutionChanged()
    {
        win.dx = x1024(texUP.dx);
        win.dy = y1024(texUP.dy);
    }

    public void created()
    {
        metricWin = null;
        bEnable = false;
        resolutionChanged();
    }

    public GUILightBox(com.maddox.gwindow.GWindow gwindow, com.maddox.gwindow.GTexture gtexture, float f, float f1, float f2, float f3)
    {
        texDOWN = new GTexRegion(gtexture, f, f1, f2, f3);
        texUP = new GTexRegion(gtexture, f + f2, f1, f2, f3);
        doNew(gwindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
    }

    com.maddox.gwindow.GTexRegion texUP;
    com.maddox.gwindow.GTexRegion texDOWN;
}
