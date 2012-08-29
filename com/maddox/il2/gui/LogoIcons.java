// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LogoIcons.java

package com.maddox.il2.gui;

import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;

public class LogoIcons extends com.maddox.il2.engine.Render
{

    public void preRender()
    {
        matIcon1.preRender();
        matIcon2.preRender();
    }

    public void render()
    {
        com.maddox.il2.engine.Render.prepareStates();
        float f = getViewPortWidth();
        float f1 = (float)getViewPortWidth() / 10F;
        float f2 = (float)getViewPortHeight() / 7.5F;
        com.maddox.il2.engine.Render.drawTile(f - f1 - 0.1F * f1, 0.1F * f2, f1, f2, 0.0F, matIcon1, -1, 0.0F, 1.0F, 1.0F, -1F);
        com.maddox.il2.engine.Render.drawTile(f - f1 - 0.1F * f1, 0.2F * f2 + f2, f1, f2, 0.0F, matIcon2, -1, 0.0F, 1.0F, 1.0F, -1F);
    }

    public LogoIcons(float f, int i)
    {
        super(f);
        size = i;
        useClearDepth(false);
        useClearColor(false);
        com.maddox.il2.engine.CameraOrtho2D cameraortho2d = new CameraOrtho2D();
        cameraortho2d.setName("cameraLogoIcons");
        cameraortho2d.set(0.0F, getViewPortWidth(), 0.0F, getViewPortHeight());
        setCamera(cameraortho2d);
        setShow(true);
        setName("renderLogoIcons");
        matIcon1 = com.maddox.il2.engine.Mat.New("3do/gui/LogoIcons/maddox.mat");
        matIcon2 = com.maddox.il2.engine.Mat.New("3do/gui/LogoIcons/il2.mat");
    }

    private static final boolean bFixedSize = false;
    private com.maddox.il2.engine.Mat matIcon1;
    private com.maddox.il2.engine.Mat matIcon2;
    private int size;
}
