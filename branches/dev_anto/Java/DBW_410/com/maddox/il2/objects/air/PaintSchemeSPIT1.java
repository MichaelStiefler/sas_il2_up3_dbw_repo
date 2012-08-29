// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 25/07/2010 1:37:52 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   PaintSchemeSPIT1.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintScheme

public class PaintSchemeSPIT1 extends PaintScheme
{

    public PaintSchemeSPIT1()
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

    public String typedNameNum(Class class1, Regiment regiment, int i, int j, int k)
    {
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == PaintScheme.countryGermany)
            if(k <= 1 && i == 0)
                return "< + " + PaintScheme.psGermanFighterGruppeChar[1][l];
            else
                return PaintScheme.psGermanFighterString[1][i] + k + " + " + PaintScheme.psGermanFighterGruppeChar[1][l];
        if(regiment.country() == PaintScheme.countryNetherlands)
            return "" + k;
        if(regiment.country() == PaintScheme.countryFinland)
            if(i == 3)
                return PaintScheme.psFinnishFighterString[0][i] + (char)(65 + (k % 10 - 1));
            else
                return PaintScheme.psFinnishFighterString[0][i] + k;
        if(regiment.country() == PaintScheme.countryFrance)
            return "o " + k;
        if(regiment.country() == PaintScheme.countryBritain)
        {
            k = clampToReality(k);
            return "" + regiment.id() + " - " + (char)k;
        }
        if(regiment.country() == PaintScheme.countryItaly)
            return "" + k;
        if(regiment.country() == PaintScheme.countryJapan)
            return "" + k;
        if(regiment.country() == PaintScheme.countryPoland)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryRomania)
            return "+ " + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryRussia)
            return "* " + (k >= 10 ? "" + k : "0" + k);
        if(regiment.country() == PaintScheme.countryNewZealand)
        {
            k = clampToLiteral(k);
            return "" + (char)(65 + (k - 1));
        }
        if(regiment.country() == PaintScheme.countrySlovakia)
            return "" + k + " +";
        if(regiment.country() == PaintScheme.countryUSA)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
        } else
        {
            return super.typedNameNum(class1, regiment, i, j, k);
        }
    }

    public void prepareNum(Class class1, HierMesh hiermesh, Regiment regiment, int i, int j, int k)
    {
        super.prepareNum(class1, hiermesh, regiment, i, j, k);
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == PaintScheme.countryGermany)
        {
            int i1 = i + 1;
            if(i1 == 4)
                i1 = 0;
            int j1 = regiment.gruppeNumber();
            changeMat(class1, hiermesh, "Overlay6", "balken2", "German/balken2.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "balken2", "German/balken2.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "haken2", "German/" + (World.cur().isHakenAllowed() ? "haken2.tga" : "hakenfake.tga"), 1.0F, 1.0F, 1.0F);
            if(k <= 1 && i == 0)
            {
                changeMat(class1, hiermesh, "Overlay1", "psFM04GERCOML0000", "German/00c.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay4", "psFM04GERCOMR0000", "German/00c2.tga", 1.0F, 1.0F, 1.0F);
                if(j1 == 1 || j1 == 5)
                {
                    changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
                } else
                {
                    changeMat(class1, hiermesh, "Overlay2", "psFM04GERCOMCGID" + j1, "German/00cG" + j1 + ".tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay3", "psFM04GERCOMCGID" + j1, "German/00cG" + j1 + ".tga", 1.0F, 1.0F, 1.0F);
                }
            } else
            {
                if(k < 10)
                {
                    changeMat(class1, hiermesh, "Overlay1", "psFM04GERLNUM" + l + i + "0" + k, "German/1" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                    changeMat(hiermesh, "Overlay4", "psFM04GERRNUM" + l + i + "0" + k, "null.tga", "German/1" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                } else
                {
                    changeMat(hiermesh, "Overlay1", "psFM04GERCNUM" + l + i + k, "German/1" + i1 + k / 10 + ".tga", "German/1" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    changeMat(hiermesh, "Overlay4", "psFM04GERCNUM" + l + i + k, "German/1" + i1 + k / 10 + ".tga", "German/1" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                }
                if(j1 == 1 || j1 == 5)
                {
                    changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
                } else
                {
                    changeMat(class1, hiermesh, "Overlay2", "psFM04GERGRUPP" + j1 + "S" + i1, "German/1" + i1 + "0G" + j1 + ".tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay3", "psFM04GERGRUPP" + j1 + "S" + i1, "German/1" + i1 + "0G" + j1 + ".tga", 1.0F, 1.0F, 1.0F);
                }
            }
        } else
        if(regiment.country() == PaintScheme.countryNetherlands)
        {
            changeMat(class1, hiermesh, "Overlay6", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay1", "psBM00DUTCNUM" + (k >= 10 ? "" + k : "0" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psBM00DUTCNUM" + (k >= 10 ? "" + k : "0" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
        } else
        if(regiment.country() == PaintScheme.countryFinland)
        {
            char c = (char)(48 + k % 10);
            changeMat(class1, hiermesh, "Overlay6", "FAFhaken", "Finnish/" + (World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "FAFhaken", "Finnish/" + (World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
            if(i == 3)
                changeMat(class1, hiermesh, "Overlay8", "psFM04FINCNUM" + l + i + "_" + (char)(65 + (k % 10 - 1)), "Finnish/" + (char)(65 + (k % 10 - 1)) + ".tga", PaintScheme.psFinnishFighterColor[i][0], PaintScheme.psFinnishFighterColor[i][1], PaintScheme.psFinnishFighterColor[i][2]);
            else
            if(k < 10)
                changeMat(class1, hiermesh, "Overlay8", "psFM04FINCNUM" + l + i + "0" + k, "Finnish/0" + k + ".tga", PaintScheme.psFinnishFighterColor[i][0], PaintScheme.psFinnishFighterColor[i][1], PaintScheme.psFinnishFighterColor[i][2]);
            else
                changeMat(hiermesh, "Overlay8", "psFM04FINCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", PaintScheme.psFinnishFighterColor[i][0], PaintScheme.psFinnishFighterColor[i][1], PaintScheme.psFinnishFighterColor[i][2], PaintScheme.psFinnishFighterColor[i][0], PaintScheme.psFinnishFighterColor[i][1], PaintScheme.psFinnishFighterColor[i][2]);
            String s = getFAFACCode(class1, i);
            changeMat(hiermesh, "Overlay2", "psFM04FINACID" + s + c, "Finnish/" + s + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            changeMat(hiermesh, "Overlay3", "psFM04FINACID" + s + c, "Finnish/" + s + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        } else
        {
            if(regiment.country() == PaintScheme.countryFrance)
            {
                if(k < 10)
                {
                    changeMat(class1, hiermesh, "Overlay2", "psFM04FRALNUM" + l + i + k, "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                    changeMat(hiermesh, "Overlay3", "psFM04FRARNUM" + l + i + k, "null.tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                } else
                {
                    changeMat(hiermesh, "Overlay2", "psFM04FRACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    changeMat(hiermesh, "Overlay3", "psFM04FRACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                }
                changeMat(class1, hiermesh, "Overlay6", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay7", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
            }
            if(regiment.country() == PaintScheme.countryBritain)
            {
                if("ra".equals(regiment.branch()) || "rz".equals(regiment.branch()) || "rn".equals(regiment.branch()))
                {
                    k = clampToLiteral(k);
                    changeMat(hiermesh, "Overlay1", "psSPIT1BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);
                    changeMat(hiermesh, "Overlay4", "psSPIT1BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);
                    changeMat(hiermesh, "Overlay2", "psSPIT1BRINAVYLNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);
                    changeMat(hiermesh, "Overlay3", "psSPIT1BRINAVYRNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);
                    changeMat(class1, hiermesh, "Overlay6", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay7", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
                } else
                {
                    k = clampToReality(k);
                    changeMat(hiermesh, "Overlay1", "psSPIT1BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.7765F, 0.7843F, 0.7059F, 0.7765F, 0.7843F, 0.7059F);
                    changeMat(hiermesh, "Overlay3", "psSPIT1BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.7765F, 0.7843F, 0.7059F, 0.7765F, 0.7843F, 0.7059F);
                    changeMat(hiermesh, "Overlay2", "psSPIT1BRILNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)k + ".tga", "null.tga", 0.7765F, 0.7843F, 0.7059F, 1.0F, 1.0F, 1.0F);
                    changeMat(hiermesh, "Overlay4", "psSPIT1BRIRNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)k + ".tga", "null.tga", 0.7765F, 0.7843F, 0.7059F, 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay6", "britishroundel2c", "British/roundel2c.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay7", "britishroundel4c", "British/roundel4c.tga", 1.0F, 1.0F, 1.0F);
                }
            } else
            if(regiment.country() == PaintScheme.countryHungary)
            {
                changeMat(hiermesh, "Overlay1", "psFM04HUNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
                changeMat(hiermesh, "Overlay3", "psFM04HUNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
                changeMat(hiermesh, "Overlay2", "psFM04HUNCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
                changeMat(hiermesh, "Overlay4", "psFM04HUNCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
                changeMat(class1, hiermesh, "Overlay6", "hungarianbalkennewer", "Hungarian/balkennewer.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay7", "hungarianbalkennewer", "Hungarian/balkennewer.tga", 1.0F, 1.0F, 1.0F);
            } else
            {
                if(regiment.country() == PaintScheme.countryItaly)
                {
                    if(k < 10)
                    {
                        changeMat(class1, hiermesh, "Overlay2", "psFM04ITALNUM" + l + i + k, "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                        changeMat(hiermesh, "Overlay3", "psFM04ITARNUM" + l + i + k, "null.tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    } else
                    {
                        changeMat(hiermesh, "Overlay2", "psFM04ITACNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                        changeMat(hiermesh, "Overlay3", "psFM04ITACNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    }
                    changeMat(class1, hiermesh, "Overlay6", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
                }
                if(regiment.country() == PaintScheme.countryJapan)
                {
                    changeMat(hiermesh, "Overlay2", "psFM04JAPCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2], PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2]);
                    changeMat(hiermesh, "Overlay3", "psFM04JAPCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2], PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2]);
                    changeMat(class1, hiermesh, "Overlay6", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay7", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
                }
                if(regiment.country() == PaintScheme.countryPoland)
                {
                    changeMat(hiermesh, "Overlay1", "psFM04POLCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    changeMat(hiermesh, "Overlay4", "psFM04POLCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay7", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay8", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
                } else
                if(regiment.country() == PaintScheme.countryRomania)
                {
                    changeMat(hiermesh, "Overlay8", "psFM04ROMCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay6", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay7", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
                } else
                if(regiment.country() == PaintScheme.countryRussia)
                {
                    changeMat(hiermesh, "Overlay2", "psFM04RUSCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    changeMat(hiermesh, "Overlay3", "psFM04RUSCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay6", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay7", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay8", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
                } else
                if(regiment.country() == PaintScheme.countryNewZealand)
                {
                    k = clampToLiteral(k);
                    changeMat(hiermesh, "Overlay2", "psFM00RZLNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);
                    changeMat(hiermesh, "Overlay3", "psFM00RZRNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);
                    changeMat(class1, hiermesh, "Overlay6", "newzealand6", "NewZealand/newzealand6.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay7", "newzealand7", "NewZealand/newzealand7.tga", 1.0F, 1.0F, 1.0F);
                } else
                if(regiment.country() == PaintScheme.countrySlovakia)
                {
                    if(k < 10)
                    {
                        changeMat(class1, hiermesh, "Overlay4", "psFM04SLVKLNUM" + l + i + "0" + k, "Slovakian/" + k + ".tga", 1.0F, 1.0F, 1.0F);
                        changeMat(hiermesh, "Overlay1", "psFM04SLVKRNUM" + l + i + "0" + k, "null.tga", "Slovakian/" + k + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    } else
                    {
                        changeMat(hiermesh, "Overlay1", "psFM04SLVKCNUM" + l + i + k, "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                        changeMat(hiermesh, "Overlay4", "psFM04SLVKCNUM" + l + i + k, "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                    }
                    changeMat(class1, hiermesh, "Overlay6", "slovakiancross2", "Slovakian/cross2.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay7", "slovakiancross2", "Slovakian/cross2.tga", 1.0F, 1.0F, 1.0F);
                } else
                if(regiment.country() == PaintScheme.countryUSA)
                {
                    k = clampToLiteral(k);
                    changeMat(hiermesh, "Overlay1", "psSPIT1USAREGI" + regiment.id(), "States/" + regiment.aid()[0] + ".tga", "States/" + regiment.aid()[1] + ".tga", 0.2F, 0.2F, 0.2F, 0.2F, 0.2F, 0.2F);
                    changeMat(hiermesh, "Overlay4", "psSPIT1USAREGI" + regiment.id(), "States/" + regiment.aid()[0] + ".tga", "States/" + regiment.aid()[1] + ".tga", 0.2F, 0.2F, 0.2F, 0.2F, 0.2F, 0.2F);
                    changeMat(hiermesh, "Overlay2", "psSPIT1USALNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "States/" + (char)((65 + k) - 1) + ".tga", 0.2F, 0.2F, 0.2F, 0.2F, 0.2F, 0.2F);
                    changeMat(hiermesh, "Overlay3", "psSPIT1USARNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "States/" + (char)((65 + k) - 1) + ".tga", "null.tga", 0.2F, 0.2F, 0.2F, 0.2F, 0.2F, 0.2F);
                }
            }
        }
    }
}