// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PaintSchemeFMPar02f.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintScheme

public class PaintSchemeFMPar02f extends com.maddox.il2.objects.air.PaintScheme
{

    public PaintSchemeFMPar02f()
    {
    }

    public java.lang.String typedNameNum(java.lang.Class class1, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGermany)
            if(k <= 1 && i == 0)
                return "< + " + com.maddox.il2.objects.air.PaintScheme.psGermanFighterGruppeChar[1][l];
            else
                return com.maddox.il2.objects.air.PaintScheme.psGermanFighterString[0][i] + k + " + " + com.maddox.il2.objects.air.PaintScheme.psGermanFighterGruppeChar[0][l];
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNetherlands)
            return "" + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFinland)
            if(i == 3)
                return com.maddox.il2.objects.air.PaintScheme.psFinnishFighterString[0][i] + (char)(65 + (k % 10 - 1));
            else
                return com.maddox.il2.objects.air.PaintScheme.psFinnishFighterString[0][i] + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFrance)
            return "o " + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritain)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritain)
            return "" + regiment.id() + " + " + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryItaly)
            return "" + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryJapan)
            return "" + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPoland)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRomania)
            return "+ " + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussia)
            return "* " + com.maddox.il2.objects.air.PaintScheme.psRussianBomberString[i] + " " + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNewZealand)
        {
            k = clampToLiteral(k);
            return "" + (char)(65 + (k - 1));
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySlovakia)
            return "+ " + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryUSA)
            return "" + (k < 10 ? "0" + k : "" + k) + "*";
        else
            return super.typedNameNum(class1, regiment, i, j, k);
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        super.prepareNum(class1, hiermesh, regiment, i, j, k);
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGermany)
        {
            int i1 = i + 1;
            if(i1 == 4)
                i1 = 0;
            int j1 = regiment.gruppeNumber();
            changeMat(class1, hiermesh, "Overlay6", "balken0", "German/balken0.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "balken1", "German/balken1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "haken1", "German/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "haken1.tga" : "hakenfake.tga"), 1.0F, 1.0F, 1.0F);
            if(k <= 1 && i == 0)
            {
                changeMat(class1, hiermesh, "Overlay1", "psFM02GERCOML0000", "German/00c.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay4", "psFM02GERCOMR0000", "German/00c2.tga", 1.0F, 1.0F, 1.0F);
                if(j1 > 1 && j1 < 5)
                {
                    changeMat(class1, hiermesh, "Overlay2", "psFM02GERCOMCGID" + j1, "German/00cG" + j1 + ".tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay3", "psFM02GERCOMCGID" + j1, "German/00cG" + j1 + ".tga", 1.0F, 1.0F, 1.0F);
                }
            } else
            {
                if(k < 10)
                {
                    changeMat(class1, hiermesh, "Overlay1", "psFM02GERLNUM" + l + i + k, "German/0" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                    changeMat(hiermesh, "Overlay4", "psFM02GERRNUM" + l + i + k, "null.tga", "German/0" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                } else
                {
                    changeMat(hiermesh, "Overlay1", "psFM02GERCNUM" + l + i + k, "German/0" + i1 + k / 10 + ".tga", "German/0" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    changeMat(hiermesh, "Overlay4", "psFM02GERCNUM" + l + i + k, "German/0" + i1 + k / 10 + ".tga", "German/0" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                }
                if(j1 > 1 && j1 < 5)
                {
                    changeMat(class1, hiermesh, "Overlay2", "psFM02GERGRUPP" + j1 + "S" + i1, "German/0" + i1 + "0G" + j1 + ".tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay3", "psFM02GERGRUPP" + j1 + "S" + i1, "German/0" + i1 + "0G" + j1 + ".tga", 1.0F, 1.0F, 1.0F);
                }
            }
        } else
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNetherlands)
        {
            changeMat(class1, hiermesh, "Overlay6", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay1", "psBM00DUTCNUM" + (k >= 10 ? "" + k : "0" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psBM00DUTCNUM" + (k >= 10 ? "" + k : "0" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
        } else
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFinland)
        {
            char c = (char)(48 + k % 10);
            changeMat(class1, hiermesh, "Overlay6", "FAFhaken", "Finnish/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "FAFhaken", "Finnish/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
            if(i == 3)
                changeMat(class1, hiermesh, "Overlay8", "psFM02FINCNUM" + l + i + "_" + (char)(65 + (k % 10 - 1)), "Finnish/" + (char)(65 + (k % 10 - 1)) + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            else
            if(k < 10)
                changeMat(class1, hiermesh, "Overlay8", "psFM02FINCNUM" + l + i + "0" + k, "Finnish/0" + k + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            else
                changeMat(hiermesh, "Overlay8", "psFM02FINCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            java.lang.String s = getFAFACCode(class1, i);
            changeMat(hiermesh, "Overlay2", "psFM02FINACID" + s + c, "Finnish/" + s + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            changeMat(hiermesh, "Overlay3", "psFM02FINACID" + s + c, "Finnish/" + s + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        } else
        {
            if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFrance)
            {
                if(k < 10)
                {
                    changeMat(class1, hiermesh, "Overlay2", "psFM02FRALNUM" + l + i + k, "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                    changeMat(hiermesh, "Overlay3", "psFM02FRARNUM" + l + i + k, "null.tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                } else
                {
                    changeMat(hiermesh, "Overlay2", "psFM02FRACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    changeMat(hiermesh, "Overlay3", "psFM02FRACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                }
                changeMat(class1, hiermesh, "Overlay6", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay7", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
            }
            if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritain)
            {
                if("ra".equals(regiment.branch()) || "rz".equals(regiment.branch()) || "rn".equals(regiment.branch()))
                {
                    k = clampToLiteral(k);
                    changeMat(hiermesh, "Overlay1", "psFM02BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2]);
                    changeMat(hiermesh, "Overlay4", "psFM02BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2]);
                    if(regiment.aid()[0] != '_')
                    {
                        changeMat(hiermesh, "Overlay2", "psFM02BRINAVYLNUM" + regiment.id() + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + regiment.aid()[0] + "f.tga", "British/" + (char)((65 + k) - 1) + ".tga", 0.11F, 0.11F, 0.11F, 0.11F, 0.11F, 0.11F);
                        changeMat(hiermesh, "Overlay3", "psFM02BRINAVYRNUM" + regiment.id() + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + regiment.aid()[0] + "f.tga", "British/" + (char)((65 + k) - 1) + ".tga", 0.11F, 0.11F, 0.11F, 0.11F, 0.11F, 0.11F);
                    } else
                    {
                        changeMat(class1, hiermesh, "Overlay2", "psFM02BRINAVYLNUM" + regiment.id() + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", 0.11F, 0.11F, 0.11F);
                        changeMat(hiermesh, "Overlay3", "psFM02BRINAVYRNUM" + regiment.id() + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + regiment.aid()[0] + "f.tga", "British/" + (char)((65 + k) - 1) + ".tga", 0.11F, 0.11F, 0.11F, 0.11F, 0.11F, 0.11F);
                    }
                    changeMat(class1, hiermesh, "Overlay6", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay7", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
                } else
                {
                    k = clampToLiteral(k);
                    changeMat(hiermesh, "Overlay1", "psFM02BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2]);
                    changeMat(hiermesh, "Overlay3", "psFM02BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2]);
                    changeMat(hiermesh, "Overlay2", "psFM02BRILNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2], 1.0F, 1.0F, 1.0F);
                    changeMat(hiermesh, "Overlay4", "psFM02BRIRNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2]);
                    changeMat(class1, hiermesh, "Overlay6", "britishroundel2c", "British/roundel2c.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay7", "britishroundel4c", "British/roundel4c.tga", 1.0F, 1.0F, 1.0F);
                }
            } else
            if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryHungary)
            {
                changeMat(hiermesh, "Overlay1", "psFM02HUNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
                changeMat(hiermesh, "Overlay3", "psFM02HUNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
                changeMat(hiermesh, "Overlay2", "psFM02HUNCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
                changeMat(hiermesh, "Overlay4", "psFM02HUNCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
                changeMat(class1, hiermesh, "Overlay6", "hungarianbalkenolder", "Hungarian/balkenolder.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay7", "hungarianbalkenolder", "Hungarian/balkenolder.tga", 1.0F, 1.0F, 1.0F);
            } else
            {
                if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryItaly)
                {
                    if(k < 10)
                    {
                        changeMat(class1, hiermesh, "Overlay2", "psFM02ITALNUM" + l + i + k, "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                        changeMat(hiermesh, "Overlay3", "psFM02ITARNUM" + l + i + k, "null.tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    } else
                    {
                        changeMat(hiermesh, "Overlay2", "psFM02ITACNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                        changeMat(hiermesh, "Overlay3", "psFM02ITACNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    }
                    changeMat(class1, hiermesh, "Overlay6", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
                }
                if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryJapan)
                {
                    changeMat(hiermesh, "Overlay2", "psFM02JAPCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2]);
                    changeMat(hiermesh, "Overlay3", "psFM02JAPCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2]);
                    changeMat(class1, hiermesh, "Overlay6", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay7", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
                }
                if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPoland)
                {
                    changeMat(hiermesh, "Overlay1", "psFM02POLCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    changeMat(hiermesh, "Overlay4", "psFM02POLCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay7", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay8", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
                } else
                if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRomania)
                {
                    changeMat(hiermesh, "Overlay8", "psFM02ROMCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay6", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay7", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
                } else
                if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussia)
                {
                    if(k < 10)
                    {
                        changeMat(class1, hiermesh, "Overlay2", "psFM02RUSLNUM" + l + i + "0" + k, "Russian/0" + k + ".tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][2]);
                        changeMat(hiermesh, "Overlay3", "psFM02RUSRNUM" + l + i + "0" + k, "null.tga", "Russian/0" + k + ".tga", 1.0F, 1.0F, 1.0F, com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][2]);
                    } else
                    {
                        changeMat(hiermesh, "Overlay2", "psFM02RUSCNUM" + l + i + k, "Russian/0" + k / 10 + ".tga", "Russian/0" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][2], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][2]);
                        changeMat(hiermesh, "Overlay3", "psFM02RUSCNUM" + l + i + k, "Russian/0" + k / 10 + ".tga", "Russian/0" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][2], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][2]);
                    }
                    changeMat(class1, hiermesh, "Overlay7", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay8", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
                } else
                if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNewZealand)
                {
                    k = clampToLiteral(k);
                    changeMat(hiermesh, "Overlay2", "psFM00RZLNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);
                    changeMat(hiermesh, "Overlay3", "psFM00RZRNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2]);
                    changeMat(class1, hiermesh, "Overlay6", "newzealand6", "NewZealand/newzealand6.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay7", "newzealand7", "NewZealand/newzealand7.tga", 1.0F, 1.0F, 1.0F);
                } else
                if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySlovakia)
                {
                    if(k < 10)
                    {
                        changeMat(class1, hiermesh, "Overlay2", "psFM02SLVKLNUM" + l + i + "0" + k, "Finnish/" + k + ".tga", 1.0F, 1.0F, 1.0F);
                        changeMat(hiermesh, "Overlay3", "psFM02SLVKRNUM" + l + i + "0" + k, "null.tga", "Finnish/" + k + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    } else
                    {
                        changeMat(hiermesh, "Overlay2", "psFM02SLVKCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                        changeMat(hiermesh, "Overlay3", "psFM02SLVKCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    }
                    changeMat(class1, hiermesh, "Overlay6", "slovakiancross1", "Slovakian/cross1.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay7", "slovakiancross2", "Slovakian/cross2.tga", 1.0F, 1.0F, 1.0F);
                } else
                if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryUSA)
                {
                    changeMat(hiermesh, "Overlay1", "psBM00USACNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    changeMat(hiermesh, "Overlay4", "psBM00USACNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay6", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay7", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }
}
