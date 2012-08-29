// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SquareLabels.java

package com.maddox.il2.gui;

import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;

public class SquareLabels
{

    public SquareLabels()
    {
    }

    public static void draw(com.maddox.il2.engine.CameraOrtho2D cameraortho2d, double d)
    {
        com.maddox.il2.gui.SquareLabels.draw(cameraortho2d, 0.0D, 0.0D, d);
    }

    public static void draw(com.maddox.il2.engine.CameraOrtho2D cameraortho2d, double d, double d1, double d2)
    {
        if(_squareLabelsFont == null)
            _squareLabelsFont = com.maddox.il2.engine.TTFont.font[0];
        if(_squareLabelsMat == null)
            _squareLabelsMat = com.maddox.il2.engine.Mat.New("icons/empty.mat");
        int i = 10000;
        int j = (int)((cameraortho2d.worldXOffset + d) / (double)i + 0.5D);
        int k = (int)((cameraortho2d.worldYOffset + d1) / (double)i + 0.5D);
        int l = (int)((double)(cameraortho2d.right - cameraortho2d.left) / cameraortho2d.worldScale / (double)i) + 2;
        int i1 = (int)((double)(cameraortho2d.top - cameraortho2d.bottom) / cameraortho2d.worldScale / (double)i) + 2;
        float f = (float)((((double)(j * i) + 0.5D * (double)i) - cameraortho2d.worldXOffset - d) * cameraortho2d.worldScale + 0.5D);
        float f1 = (float)((((double)(k * i) + 0.5D * (double)i) - cameraortho2d.worldYOffset - d1) * cameraortho2d.worldScale + 0.5D);
        float f2 = (float)((double)i * cameraortho2d.worldScale);
        boolean flag = d2 / (double)i + 1.0D > 26D;
        float f3 = _squareLabelsFont.height() - _squareLabelsFont.descender();
        float f4 = 0.0F;
        for(int j1 = 0; j1 < 2; j1++)
        {
            for(int k1 = 0; k1 <= l; k1++)
            {
                float f5 = f + (float)k1 * f2;
                java.lang.String s;
                if(flag)
                {
                    int i2 = j + k1;
                    s = "" + (char)(65 + i2 / 26) + (char)(65 + i2 % 26);
                } else
                {
                    s = "" + (char)(65 + (j + k1));
                }
                float f7 = _squareLabelsFont.width(s);
                if(k1 <= 0 || f5 - f7 / 2.0F >= f4)
                {
                    f4 = f5 + f7 / 2.0F + 2.0F;
                    float f8 = (cameraortho2d.top - cameraortho2d.bottom - f3) + (float)_squareLabelsFont.descender();
                    if(j1 == 0)
                        com.maddox.il2.engine.Render.drawTile(f5 - f7, f8 + (float)_squareLabelsFont.descender(), 2.0F * f7, f3, 0.0F, _squareLabelsMat, 0xffc0c0c0, 0.0F, 0.0F, 1.0F, 1.0F);
                    else
                        _squareLabelsFont.output(0xff000000, f5 - f7 / 2.0F, f8, 0.0F, s);
                }
            }

            for(int l1 = 0; l1 <= i1; l1++)
            {
                float f6 = (f1 + (float)l1 * f2) - f3 / 2.0F;
                java.lang.String s1 = "" + (k + l1 + 1);
                float f9 = _squareLabelsFont.width(s1);
                if(l1 <= 0 || f6 >= f4)
                {
                    f4 = f6 + f3;
                    float f10 = (cameraortho2d.right - cameraortho2d.left - 2.0F - f9) + (float)_squareLabelsFont.descender();
                    if(j1 == 0)
                        com.maddox.il2.engine.Render.drawTile(f10 - f9 / 2.0F, f6 + (float)_squareLabelsFont.descender(), 2.0F * f9, f3, 0.0F, _squareLabelsMat, 0xffc0c0c0, 0.0F, 0.0F, 1.0F, 1.0F);
                    else
                        _squareLabelsFont.output(0xff000000, f10, f6, 0.0F, s1);
                }
            }

            if(j1 == 0)
                com.maddox.il2.engine.Render.drawEnd();
        }

    }

    private static com.maddox.il2.engine.TTFont _squareLabelsFont;
    private static com.maddox.il2.engine.Mat _squareLabelsMat;
}
