// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIButton.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogControl;
import com.maddox.gwindow.GWindowLookAndFeel;

public class GUIButton extends com.maddox.gwindow.GWindowDialogControl
{

    public boolean _notify(int i, int j)
    {
        if(i == 2)
            lAF().soundPlay("clickButton");
        return super._notify(i, j);
    }

    public void mouseClick(int i, float f, float f1)
    {
        super.mouseClick(i, f, f1);
    }

    public void render()
    {
        setCanvasColorWHITE();
        com.maddox.gwindow.GTexRegion gtexregion = texUP;
        if(bDown)
            gtexregion = texDOWN;
        draw(0.0F, 0.0F, win.dx, win.dy, gtexregion);
    }

    public void setPosSize(float f, float f1, float f2, float f3)
    {
        com.maddox.gwindow.GTexRegion gtexregion = texUP;
        float f4 = 1.0F;
        float f5;
        for(f5 = gtexregion.dx; f5 / f4 > f2; f4++);
        f5 /= f4;
        f4 = 1.0F;
        float f6;
        for(f6 = gtexregion.dy; f6 / f4 > f3; f4++);
        f6 /= f4;
        win.x = java.lang.Math.round(f);
        win.y = java.lang.Math.round(f1);
        win.dx = java.lang.Math.round(f2);
        win.dy = java.lang.Math.round(f3);
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
        resolutionChanged();
    }

    public GUIButton(com.maddox.gwindow.GWindow gwindow)
    {
        doNew(gwindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
    }

    public com.maddox.il2.gui.GUIButton Clone()
    {
        com.maddox.il2.gui.GUIButton guibutton = new GUIButton(parentWindow);
        guibutton.texUP = texUP;
        guibutton.texDOWN = texDOWN;
        return guibutton;
    }

    public GUIButton(com.maddox.gwindow.GWindow gwindow, com.maddox.gwindow.GTexture gtexture, float f, float f1, float f2, float f3)
    {
        texUP = new GTexRegion(gtexture, f, f1, f2, f3);
        texDOWN = new GTexRegion(gtexture, f + f2, f1, f2, f3);
        doNew(gwindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
    }

    com.maddox.gwindow.GTexRegion texUP;
    com.maddox.gwindow.GTexRegion texDOWN;
}
