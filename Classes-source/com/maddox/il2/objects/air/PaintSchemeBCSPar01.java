// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PaintSchemeBCSPar01.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintSchemeBMPar01, PaintScheme

public class PaintSchemeBCSPar01 extends com.maddox.il2.objects.air.PaintSchemeBMPar01
{

    public PaintSchemeBCSPar01()
    {
    }

    public java.lang.String typedNameNum(java.lang.Class class1, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritain)
            return "" + k % 10 + regiment.aid()[1];
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritainBlue)
            return "" + k % 10 + regiment.aid()[1];
        else
            return super.typedNameNum(class1, regiment, i, j, k);
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        super.prepareNum(class1, hiermesh, regiment, i, j, k);
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritain)
            changeMat(hiermesh, "Overlay1", "psBCS01BRINUM" + k % 10 + regiment.aid()[1], "British/" + k % 10 + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[2]);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritainBlue)
            changeMat(hiermesh, "Overlay1", "psbBCS01BRINUM" + k % 10 + regiment.aid()[1], "British/" + k % 10 + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryJapan)
        {
            changeMat(hiermesh, "Overlay1", "psBCS01JAPREDNUM" + l + i + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.6901961F, 0.1568628F, 0.1098039F, 0.6901961F, 0.1568628F, 0.1098039F);
            changeMat(hiermesh, "Overlay2", "psBCS01JAPWHTNUM" + l + i + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.95F, 0.95F, 0.95F, 0.95F, 0.95F, 0.95F);
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussia)
        {
            changeMat(hiermesh, "Overlay1", "psBCS01RUSCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][2], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][2]);
            changeMat(class1, hiermesh, "Overlay2", "psAVGRUSMARKcolor" + i, "mark.tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][2]);
            changeMat(class1, hiermesh, "Overlay6", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryChina)
        {
            changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySpainRep)
        {
            changeMat(class1, hiermesh, "Overlay6", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            return;
        } else
        {
            return;
        }
    }

    public void prepareNumOff(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussia)
            changeMat(class1, hiermesh, "Overlay2", "psAVGRUSMARKcolor" + i, "mark.tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][2]);
    }
}
