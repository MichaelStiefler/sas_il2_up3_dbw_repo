// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIDialogClient.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.il2.engine.Config;

public class GUIDialogClient extends com.maddox.gwindow.GWindowDialogClient
{

    private static void init()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(texture != null)
                return;
            texture = com.maddox.gwindow.GTexture.New("GUI/game/basicelements.mat");
            detail = com.maddox.gwindow.GTexture.New("GUI/game/detail.mat");
            com.maddox.gwindow.GTexture gtexture = com.maddox.gwindow.GTexture.New("GUI/game/staticelements.mat");
            for(int i = 0; i < 8; i++)
                bolt[i] = new GTexRegion(gtexture, i * 16, 32F, 16F, 16F);

            bevel = new GBevel();
            bevel.set(new GRegion(224F, 192F, 32F, 32F), new GRegion(239F, 207F, 2.0F, 2.0F));
        }
    }

    public GUIDialogClient()
    {
        com.maddox.il2.gui.GUIDialogClient.init();
    }

    public float M(float f)
    {
        return lookAndFeel().metric(f);
    }

    public void render()
    {
        setCanvasColorWHITE();
        lookAndFeel().drawBevel(this, 0.0F, 0.0F, win.dx, win.dy, bevel, texture);
        draw(3F, 3F, win.dx - 6F, win.dy - 6F, detail, 0.0F, 0.0F, win.dx - 6F, win.dy - 6F);
        region.set(bevel.L.dx - bolt[0].dx * 0.5F, bevel.T.dy - bolt[0].dy * 0.5F, (win.dx - bevel.L.dx - bevel.R.dx) + bolt[0].dx * 0.25F, (win.dy - bevel.T.dy - bevel.B.dy) + bolt[0].dy * 0.25F);
        int i = java.lang.Math.round(region.dx / 156F);
        if(i < 1)
            i = 1;
        float f = region.dx / (float)i;
        int j = java.lang.Math.round(region.dy / 156F);
        if(j < 1)
            j = 1;
        float f1 = region.dy / (float)j;
        int i1 = 0;
        for(int k = 0; k <= i; k++)
        {
            draw(java.lang.Math.round(region.x + (float)k * f), java.lang.Math.round(region.y), bolt[i1]);
            i1 = (i1 + 1) % 8;
            draw(java.lang.Math.round(region.x + (float)k * f), java.lang.Math.round(region.y + region.dy), bolt[i1]);
            i1 = (i1 + 1) % 8;
        }

        for(int l = 1; l < j; l++)
        {
            draw(java.lang.Math.round(region.x), java.lang.Math.round(region.y + (float)l * f1), bolt[i1]);
            i1 = (i1 + 1) % 8;
            draw(java.lang.Math.round(region.x + region.dx), java.lang.Math.round(region.y + (float)l * f1), bolt[i1]);
            i1 = (i1 + 1) % 8;
        }

    }

    public void setPosSize()
    {
        set1024Pos(20F, 20F);
        set1024Size(984F, 728F);
    }

    public void resolutionChanged()
    {
        super.resolutionChanged();
        setPosSize();
    }

    static com.maddox.gwindow.GTexture texture;
    static com.maddox.gwindow.GTexture detail;
    static com.maddox.gwindow.GTexRegion bolt[] = new com.maddox.gwindow.GTexRegion[8];
    static com.maddox.gwindow.GBevel bevel;
    static com.maddox.gwindow.GRegion region = new GRegion();

}
