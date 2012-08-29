// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PaintSchemeFCSPar02.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintSchemeFMPar02, PaintScheme

public class PaintSchemeFCSPar02 extends com.maddox.il2.objects.air.PaintSchemeFMPar02
{

    public PaintSchemeFCSPar02()
    {
    }

    public java.lang.String typedNameNum(java.lang.Class class1, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFrance)
            return "" + (k >= 10 ? "" + k : "0" + k) + " *";
        else
            return super.typedNameNum(class1, regiment, i, j, k);
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        super.prepareNum(class1, hiermesh, regiment, i, j, k);
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFinland)
        {
            char c = (char)(48 + k % 10);
            changeMat(hiermesh, "Overlay6", "FAFhaken", "Finnish/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay7", "FAFhaken", "Finnish/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
            if(i == 3)
            {
                changeMat(hiermesh, "Overlay1", "psFCS02FINCNUM" + l + i + "_" + (char)(65 + (k % 10 - 1)), "Finnish/" + (char)(65 + (k % 10 - 1)) + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
                changeMat(hiermesh, "Overlay4", "psFCS02FINCNUM" + l + i + "_" + (char)(65 + (k % 10 - 1)), "Finnish/" + (char)(65 + (k % 10 - 1)) + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            } else
            if(k < 10)
            {
                changeMat(hiermesh, "Overlay1", "psFCS02FINLNUM" + l + i + "0" + k, "null.tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterPrefix[0][i] + k + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay4", "psFCS02FINRNUM" + l + i + "0" + k, com.maddox.il2.objects.air.PaintScheme.psFinnishFighterPrefix[0][i] + k + ".tga", "null.tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay1", "psFCS02FINCNUM" + l + i + k, com.maddox.il2.objects.air.PaintScheme.psFinnishFighterPrefix[0][i] + k / 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterPrefix[0][i] + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay4", "psFCS02FINCNUM" + l + i + k, com.maddox.il2.objects.air.PaintScheme.psFinnishFighterPrefix[0][i] + k / 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterPrefix[0][i] + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            java.lang.String s = getFAFACCode(class1, i);
            changeMat(hiermesh, "Overlay2", "psFM05FINACID" + s + c, "Finnish/" + s + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            changeMat(hiermesh, "Overlay3", "psFM05FINACID" + s + c, "Finnish/" + s + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFrance)
        {
            changeMat(hiermesh, "Overlay1", "psFCS02FRACNUM" + l + i + k, "Russian/0" + k / 10 + ".tga", "Russian/0" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay6", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay7", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay2", "psAVGRUSMARKcolor" + i, "mark.tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][2]);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryItaly)
        {
            if(k < 10)
            {
                changeMat(hiermesh, "Overlay2", "psFCS02ITALNUM" + l + i + k, "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psGermanBomberColor[1][0], com.maddox.il2.objects.air.PaintScheme.psGermanBomberColor[1][1], com.maddox.il2.objects.air.PaintScheme.psGermanBomberColor[1][2]);
                changeMat(hiermesh, "Overlay3", "psFCS02ITARNUM" + l + i + k, "null.tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, com.maddox.il2.objects.air.PaintScheme.psGermanBomberColor[1][0], com.maddox.il2.objects.air.PaintScheme.psGermanBomberColor[1][1], com.maddox.il2.objects.air.PaintScheme.psGermanBomberColor[1][2]);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psFCS02ITACNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psGermanBomberColor[1][0], com.maddox.il2.objects.air.PaintScheme.psGermanBomberColor[1][1], com.maddox.il2.objects.air.PaintScheme.psGermanBomberColor[1][2], com.maddox.il2.objects.air.PaintScheme.psGermanBomberColor[1][0], com.maddox.il2.objects.air.PaintScheme.psGermanBomberColor[1][1], com.maddox.il2.objects.air.PaintScheme.psGermanBomberColor[1][2]);
                changeMat(hiermesh, "Overlay3", "psFCS02ITACNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psGermanBomberColor[1][0], com.maddox.il2.objects.air.PaintScheme.psGermanBomberColor[1][1], com.maddox.il2.objects.air.PaintScheme.psGermanBomberColor[1][2], com.maddox.il2.objects.air.PaintScheme.psGermanBomberColor[1][0], com.maddox.il2.objects.air.PaintScheme.psGermanBomberColor[1][1], com.maddox.il2.objects.air.PaintScheme.psGermanBomberColor[1][2]);
            }
            changeMat(hiermesh, "Overlay6", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay7", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay8", "italian1", "Italian/roundel1.tga", 1.0F, 1.0F, 1.0F);
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryJapan)
        {
            changeMat(hiermesh, "Overlay1", "psFCS02JAPCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[1][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[1][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[1][2], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[1][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[1][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[1][2]);
            changeMat(hiermesh, "Overlay4", "psFCS02JAPCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[1][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[1][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[1][2], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[1][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[1][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[1][2]);
            changeMat(hiermesh, "Overlay6", "JAR2", "Japanese/JAR2.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay7", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussia)
        {
            changeMat(hiermesh, "Overlay1", "psFCS02RUSCNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay6", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay7", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay2", "psAVGRUSMARKcolor" + i, "mark.tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][2]);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryUSA)
        {
            changeMat(hiermesh, "Overlay2", "psFCS02USABLNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "psFCS02USAYELNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 0.960784F, 0.745098F, 0.145098F, 0.960784F, 0.745098F, 0.145098F);
            changeMat(hiermesh, "Overlay4", "psFCS02USARUSNUM" + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            return;
        } else
        {
            return;
        }
    }
}
