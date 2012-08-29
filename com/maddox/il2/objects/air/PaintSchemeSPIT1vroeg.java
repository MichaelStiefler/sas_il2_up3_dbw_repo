// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   PaintSchemeSPIT1vroeg.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintScheme

public class PaintSchemeSPIT1vroeg extends com.maddox.il2.objects.air.PaintScheme
{

    public PaintSchemeSPIT1vroeg()
    {
    }

    protected int clampToReality(int i)
    {
        if(i <= 1)
            return 69;
        if(i == 2)
            return 82;
        if(i == 3)
            return 78;
        if(i == 4)
            return 83;
        if(i == 5)
            return 70;
        if(i == 6)
            return 76;
        if(i == 7)
            return 89;
        if(i == 8)
            return 75;
        if(i == 9)
            return 86;
        if(i == 10)
            return 81;
        if(i == 11)
            return 65;
        if(i == 12)
            return 71;
        if(i == 13)
            return 80;
        if(i == 14)
            return 85;
        if(i == 15)
            return 84;
        if(i == 16)
            return 67;
        if(i == 17)
            return 74;
        if(i == 18)
            return 72;
        if(i == 19)
            return 87;
        if(i == 20)
            return 68;
        if(i == 21)
            return 88;
        if(i == 22)
            return 73;
        if(i == 23)
            return 79;
        if(i == 24)
            return 66;
        if(i == 25)
            return 77;
        if(i >= 26)
            return 90;
        else
            return i;
    }

    public java.lang.String typedNameNum(java.lang.Class class1, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGermany)
            if(k <= 1 && i == 0)
                return "< + " + com.maddox.il2.objects.air.PaintScheme.psGermanFighterGruppeChar[1][l];
            else
                return com.maddox.il2.objects.air.PaintScheme.psGermanFighterString[1][i] + k + " + " + com.maddox.il2.objects.air.PaintScheme.psGermanFighterGruppeChar[1][l];
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNetherlands)
            return ((java.lang.String) (k));
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFinland)
            if(i == 3)
                return com.maddox.il2.objects.air.PaintScheme.psFinnishFighterString[0][i] + (char)(65 + (k % 10 - 1));
            else
                return com.maddox.il2.objects.air.PaintScheme.psFinnishFighterString[0][i] + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFrance)
            return "o " + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritain)
        {
            k = clampToReality(k);
            return regiment.id() + " - " + (char)k;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryItaly)
            return ((java.lang.String) (k));
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryJapan)
            return ((java.lang.String) (k));
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPoland)
            return ((java.lang.String) (k >= 10 ? k : "0" + k));
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRomania)
            return "+ " + (k >= 10 ? k : "0" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussia)
            return "* " + (k < 10 ? "0" + k : k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNewZealand)
        {
            k = ((com.maddox.il2.objects.air.PaintScheme)this).clampToLiteral(k);
            return ((java.lang.String) ((char)(65 + (k - 1))));
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySlovakia)
            return k + " +";
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryUSA)
        {
            k = ((com.maddox.il2.objects.air.PaintScheme)this).clampToLiteral(k);
            return regiment.id() + " - " + (char)(65 + (k - 1));
        } else
        {
            return super.typedNameNum(class1, regiment, i, j, k);
        }
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
            ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay6", "balken2", "German/balken2.tga", 1.0F, 1.0F, 1.0F);
            ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay7", "balken2", "German/balken2.tga", 1.0F, 1.0F, 1.0F);
            ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay8", "haken2", "German/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "haken2.tga" : "hakenfake.tga"), 1.0F, 1.0F, 1.0F);
            if(k <= 1 && i == 0)
            {
                ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay1", "psFM04GERCOML0000", "German/00c.tga", 1.0F, 1.0F, 1.0F);
                ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay4", "psFM04GERCOMR0000", "German/00c2.tga", 1.0F, 1.0F, 1.0F);
                if(j1 == 1 || j1 == 5)
                {
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
                } else
                {
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay2", "psFM04GERCOMCGID" + j1, "German/00cG" + j1 + ".tga", 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay3", "psFM04GERCOMCGID" + j1, "German/00cG" + j1 + ".tga", 1.0F, 1.0F, 1.0F);
                }
            } else
            {
                if(k < 10)
                {
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay1", "psFM04GERLNUM" + l + i + "0" + k, "German/1" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay4", "psFM04GERRNUM" + l + i + "0" + k, "null.tga", "German/1" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                } else
                {
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay1", "psFM04GERCNUM" + l + i + k, "German/1" + i1 + k / 10 + ".tga", "German/1" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay4", "psFM04GERCNUM" + l + i + k, "German/1" + i1 + k / 10 + ".tga", "German/1" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                }
                if(j1 == 1 || j1 == 5)
                {
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
                } else
                {
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay2", "psFM04GERGRUPP" + j1 + "S" + i1, "German/1" + i1 + "0G" + j1 + ".tga", 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay3", "psFM04GERGRUPP" + j1 + "S" + i1, "German/1" + i1 + "0G" + j1 + ".tga", 1.0F, 1.0F, 1.0F);
                }
            }
        } else
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNetherlands)
        {
            ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay6", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
            ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay7", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
            ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay1", "psBM00DUTCNUM" + (k < 10 ? "0" + k : k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay4", "psBM00DUTCNUM" + (k < 10 ? "0" + k : k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
        } else
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFinland)
        {
            char c = (char)(48 + k % 10);
            ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay6", "FAFhaken", "Finnish/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
            ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay7", "FAFhaken", "Finnish/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
            if(i == 3)
                ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay8", "psFM04FINCNUM" + l + i + "_" + (char)(65 + (k % 10 - 1)), "Finnish/" + (char)(65 + (k % 10 - 1)) + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            else
            if(k < 10)
                ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay8", "psFM04FINCNUM" + l + i + "0" + k, "Finnish/0" + k + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            else
                ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay8", "psFM04FINCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            java.lang.String s = ((com.maddox.il2.objects.air.PaintScheme)this).getFAFACCode(class1, i);
            ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay2", "psFM04FINACID" + s + c, "Finnish/" + s + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay3", "psFM04FINACID" + s + c, "Finnish/" + s + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        } else
        {
            if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFrance)
            {
                if(k < 10)
                {
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay2", "psFM04FRALNUM" + l + i + k, "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay3", "psFM04FRARNUM" + l + i + k, "null.tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                } else
                {
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay2", "psFM04FRACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay3", "psFM04FRACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                }
                ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay6", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
                ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay7", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
            }
            if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritain)
            {
                if("ra".equals(((java.lang.Object) (regiment.branch()))) || "rz".equals(((java.lang.Object) (regiment.branch()))) || "rn".equals(((java.lang.Object) (regiment.branch()))))
                {
                    k = ((com.maddox.il2.objects.air.PaintScheme)this).clampToLiteral(k);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay1", "psSPIT1BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2]);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay4", "psSPIT1BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2]);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay2", "psSPIT1BRINAVYLNUM" + l + i + (k < 10 ? "0" + k : k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay3", "psSPIT1BRINAVYRNUM" + l + i + (k < 10 ? "0" + k : k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2]);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay6", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay7", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
                } else
                {
                    k = clampToReality(k);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay1", "psSPIT1BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.7765F, 0.7843F, 0.7059F, 0.7765F, 0.7843F, 0.7059F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay3", "psSPIT1BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.7765F, 0.7843F, 0.7059F, 0.7765F, 0.7843F, 0.7059F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay2", "psSPIT1BRILNUM" + l + i + (k < 10 ? "0" + k : k), "British/" + (char)k + ".tga", "null.tga", 0.7765F, 0.7843F, 0.7059F, 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay4", "psSPIT1BRIRNUM" + l + i + (k < 10 ? "0" + k : k), "British/" + (char)k + ".tga", "null.tga", 0.7765F, 0.7843F, 0.7059F, 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay6", "britishroundel2c", "British/roundel2c.tga", 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay7", "britishroundel4c", "British/roundel4c.tga", 1.0F, 1.0F, 1.0F);
                }
            } else
            if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryHungary)
            {
                ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay1", "psFM04HUNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
                ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay3", "psFM04HUNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
                ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay2", "psFM04HUNCNUM" + l + i + (k < 10 ? "0" + k : k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
                ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay4", "psFM04HUNCNUM" + l + i + (k < 10 ? "0" + k : k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
                ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay6", "hungarianbalkennewer", "Hungarian/balkennewer.tga", 1.0F, 1.0F, 1.0F);
                ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay7", "hungarianbalkennewer", "Hungarian/balkennewer.tga", 1.0F, 1.0F, 1.0F);
            } else
            {
                if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryItaly)
                {
                    if(k < 10)
                    {
                        ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay2", "psFM04ITALNUM" + l + i + k, "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                        ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay3", "psFM04ITARNUM" + l + i + k, "null.tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    } else
                    {
                        ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay2", "psFM04ITACNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                        ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay3", "psFM04ITACNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    }
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay6", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
                }
                if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryJapan)
                {
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay2", "psFM04JAPCNUM" + l + i + (k < 10 ? "0" + k : k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2]);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay3", "psFM04JAPCNUM" + l + i + (k < 10 ? "0" + k : k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2]);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay6", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay7", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
                }
                if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPoland)
                {
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay1", "psFM04POLCNUM" + l + i + (k < 10 ? "0" + k : k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay4", "psFM04POLCNUM" + l + i + (k < 10 ? "0" + k : k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay7", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay8", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
                } else
                if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRomania)
                {
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay8", "psFM04ROMCNUM" + l + i + (k < 10 ? "0" + k : k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay6", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay7", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
                } else
                if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussia)
                {
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay2", "psFM04RUSCNUM" + l + i + (k < 10 ? "0" + k : k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay3", "psFM04RUSCNUM" + l + i + (k < 10 ? "0" + k : k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay6", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay7", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay8", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
                } else
                if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNewZealand)
                {
                    k = ((com.maddox.il2.objects.air.PaintScheme)this).clampToLiteral(k);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay2", "psFM00RZLNUM" + l + i + (k < 10 ? "0" + k : k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay3", "psFM00RZRNUM" + l + i + (k < 10 ? "0" + k : k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2]);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay6", "newzealand6", "NewZealand/newzealand6.tga", 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay7", "newzealand7", "NewZealand/newzealand7.tga", 1.0F, 1.0F, 1.0F);
                } else
                if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySlovakia)
                {
                    if(k < 10)
                    {
                        ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay4", "psFM04SLVKLNUM" + l + i + "0" + k, "Slovakian/" + k + ".tga", 1.0F, 1.0F, 1.0F);
                        ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay1", "psFM04SLVKRNUM" + l + i + "0" + k, "null.tga", "Slovakian/" + k + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    } else
                    {
                        ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay1", "psFM04SLVKCNUM" + l + i + k, "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                        ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay4", "psFM04SLVKCNUM" + l + i + k, "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    }
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay6", "slovakiancross2", "Slovakian/cross2.tga", 1.0F, 1.0F, 1.0F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(class1, hiermesh, "Overlay7", "slovakiancross2", "Slovakian/cross2.tga", 1.0F, 1.0F, 1.0F);
                } else
                if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryUSA)
                {
                    k = ((com.maddox.il2.objects.air.PaintScheme)this).clampToLiteral(k);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay1", "psSPIT1USAREGI" + regiment.id(), "States/" + regiment.aid()[0] + ".tga", "States/" + regiment.aid()[1] + ".tga", 0.2F, 0.2F, 0.2F, 0.2F, 0.2F, 0.2F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay4", "psSPIT1USAREGI" + regiment.id(), "States/" + regiment.aid()[0] + ".tga", "States/" + regiment.aid()[1] + ".tga", 0.2F, 0.2F, 0.2F, 0.2F, 0.2F, 0.2F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay2", "psSPIT1USALNUM" + l + i + (k < 10 ? "0" + k : k), "null.tga", "States/" + (char)((65 + k) - 1) + ".tga", 0.2F, 0.2F, 0.2F, 0.2F, 0.2F, 0.2F);
                    ((com.maddox.il2.objects.air.PaintScheme)this).changeMat(hiermesh, "Overlay3", "psSPIT1USARNUM" + l + i + (k < 10 ? "0" + k : k), "States/" + (char)((65 + k) - 1) + ".tga", "null.tga", 0.2F, 0.2F, 0.2F, 0.2F, 0.2F, 0.2F);
                }
            }
        }
    }
}
