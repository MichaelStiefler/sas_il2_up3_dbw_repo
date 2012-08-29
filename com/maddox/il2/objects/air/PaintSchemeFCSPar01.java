// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PaintSchemeFCSPar01.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintSchemeFMPar01

public class PaintSchemeFCSPar01 extends com.maddox.il2.objects.air.PaintSchemeFMPar01
{

    public PaintSchemeFCSPar01()
    {
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        super.prepareNum(class1, hiermesh, regiment, i, j, k);
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == countryItaly)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay2", "psFCS01ITALNUM" + l + i + k, "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFCS01ITARNUM" + l + i + k, "null.tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psFCS01ITACNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFCS01ITACNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay7", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay8", "italian1", "Italian/roundel1.tga", 1.0F, 1.0F, 1.0F);
        }
        if(regiment.country() == countryPoland)
        {
            if(k < 10)
                changeMat(class1, hiermesh, "Overlay1", "psFCS01POLCNUM" + l + i + "0" + k, "Russian/1" + k + ".tga", 1.0F, 1.0F, 1.0F);
            else
                changeMat(hiermesh, "Overlay1", "psFCS01POLCNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == countryRussia)
        {
            if(k < 10)
                changeMat(class1, hiermesh, "Overlay1", "psFCS01RUSCNUM" + l + i + "0" + k, "Russian/0" + k + ".tga", 1.0F, 1.0F, 1.0F);
            else
                changeMat(hiermesh, "Overlay1", "psFCS01RUSCNUM" + l + i + k, "Russian/0" + k / 10 + ".tga", "Russian/0" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay2", "psAVGRUSMARKcolor" + i, "mark.tga", psRussianBomberColor[i][0], psRussianBomberColor[i][1], psRussianBomberColor[i][2]);
            return;
        }
        if(regiment.country() == countryUSA)
        {
            changeMat(class1, hiermesh, "Overlay1", "psFCS01USACNUM" + k % 10, "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
            return;
        } else
        {
            return;
        }
    }

    public void prepareNumOff(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        if(regiment.country() == countryRussia)
            changeMat(class1, hiermesh, "Overlay2", "psAVGRUSMARKcolor" + i, "mark.tga", psRussianBomberColor[i][0], psRussianBomberColor[i][1], psRussianBomberColor[i][2]);
    }
}
