// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PaintSchemeBMPar03.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintScheme

public class PaintSchemeBMPar03 extends com.maddox.il2.objects.air.PaintScheme
{

    public PaintSchemeBMPar03()
    {
    }

    public java.lang.String typedNameNum(java.lang.Class class1, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        if(regiment.country() == countryGermany)
        {
            int l = regiment.gruppeNumber() - 1;
            if(k < 1)
                k = 1;
            return "" + regiment.id() + " + " + (char)(65 + (k % 26 - 1)) + psGermanBomberLetter[l][i];
        }
        if(regiment.country() == countryNetherlands)
            return "" + k;
        if(regiment.country() == countryFinland)
            return psFinnishFighterString[1][i] + k;
        if(regiment.country() == countryFrance)
            return "o " + k;
        if(regiment.country() == countryBritain)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
        }
        if(regiment.country() == countryBritain)
            return "" + regiment.id() + " + " + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == countryItaly)
            return "" + k;
        if(regiment.country() == countryJapan)
            return "" + k;
        if(regiment.country() == countryPoland)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == countryRomania)
            return "+ " + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == countryRussia)
            return "* " + psRussianBomberString[i] + " " + k;
        if(regiment.country() == countryNewZealand)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
        }
        if(regiment.country() == countrySlovakia)
            return "" + k + " +";
        if(regiment.country() == countryUSA)
            return "" + (k < 10 ? "0" + k : "" + k) + "*";
        else
            return super.typedNameNum(class1, regiment, i, j, k);
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        super.prepareNum(class1, hiermesh, regiment, i, j, k);
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == countryGermany)
        {
            k = clampToLiteral(k);
            changeMat(hiermesh, "Overlay1", "psBM03GERREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "psBM03GERREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBM03GERCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "German/" + (char)(65 + (k - 1)) + ".tga", "German/" + psGermanBomberLetter[l][i] + ".tga", psGermanBomberColor[i][0], psGermanBomberColor[i][1], psGermanBomberColor[i][2], 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psBM03GERCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "German/" + (char)(65 + (k - 1)) + ".tga", "German/" + psGermanBomberLetter[l][i] + ".tga", psGermanBomberColor[i][0], psGermanBomberColor[i][1], psGermanBomberColor[i][2], 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "balken0", "German/balken0.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "balken1", "German/balken1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "haken3", "German/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "haken3.tga" : "hakenfake.tga"), 0.945F, 0.929F, 0.886F);
            return;
        }
        if(regiment.country() == countryNetherlands)
        {
            changeMat(class1, hiermesh, "Overlay6", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay1", "psBM00DUTCNUM" + (k >= 10 ? "" + k : "0" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psBM00DUTCNUM" + (k >= 10 ? "" + k : "0" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            return;
        }
        if(regiment.country() == countryFinland)
        {
            char c = (char)(48 + k % 10);
            changeMat(class1, hiermesh, "Overlay6", "FAFhaken", "Finnish/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "FAFhaken", "Finnish/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
            if(k < 10)
                changeMat(class1, hiermesh, "Overlay8", "psBM03FINANUM" + l + i + "0" + k, "Finnish/0" + k + ".tga", psFinnishFighterColor[i][0], psFinnishFighterColor[i][1], psFinnishFighterColor[i][2]);
            else
                changeMat(hiermesh, "Overlay8", "psBM03FINCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", psFinnishFighterColor[i][0], psFinnishFighterColor[i][1], psFinnishFighterColor[i][2], psFinnishFighterColor[i][0], psFinnishFighterColor[i][1], psFinnishFighterColor[i][2]);
            java.lang.String s = getFAFACCode(class1, i);
            changeMat(hiermesh, "Overlay2", "psBM03FINACOD" + s + c, "Finnish/" + s + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            changeMat(hiermesh, "Overlay3", "psBM03FINACOD" + s + c, "Finnish/" + s + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            return;
        }
        if(regiment.country() == countryFrance)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay2", "psFB03FRALNUM" + l + i + k, "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFB03FRARNUM" + l + i + k, "null.tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psFB03FRACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFB03FRACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
        }
        if(regiment.country() == countryBritain)
        {
            if("ra".equals(regiment.branch()) || "rz".equals(regiment.branch()) || "rn".equals(regiment.branch()))
            {
                k = clampToLiteral(k);
                changeMat(hiermesh, "Overlay1", "psBM03BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2], psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2]);
                changeMat(hiermesh, "Overlay4", "psBM03BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2], psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2]);
                changeMat(hiermesh, "Overlay2", "psBM03BRINAVYLNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psBM03BRINAVYRNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2]);
                changeMat(class1, hiermesh, "Overlay6", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay7", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
            } else
            {
                k = clampToLiteral(k);
                changeMat(hiermesh, "Overlay1", "psBM03BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", psBritishSkyColor[0], psBritishSkyColor[1], psBritishSkyColor[2], psBritishSkyColor[0], psBritishSkyColor[1], psBritishSkyColor[2]);
                changeMat(hiermesh, "Overlay4", "psBM03BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", psBritishSkyColor[0], psBritishSkyColor[1], psBritishSkyColor[2], psBritishSkyColor[0], psBritishSkyColor[1], psBritishSkyColor[2]);
                changeMat(hiermesh, "Overlay2", "psBM03BRILNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", psBritishSkyColor[0], psBritishSkyColor[1], psBritishSkyColor[2], 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psBM03BRIRNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, psBritishSkyColor[0], psBritishSkyColor[1], psBritishSkyColor[2]);
                changeMat(class1, hiermesh, "Overlay6", "britishroundel2c", "British/roundel2c.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay7", "britishroundel4cthin", "British/roundel4cthin.tga", 1.0F, 1.0F, 1.0F);
            }
            return;
        }
        if(regiment.country() == countryHungary)
        {
            changeMat(hiermesh, "Overlay1", "psBM03HUNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "psBM03HUNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBM03HUNCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psBM03HUNCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "hungarianbalkennewer", "Hungarian/balkennewer.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "hungarianbalkennewer", "Hungarian/balkennewer.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == countryItaly)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay2", "psFB03ITALNUM" + l + i + k, "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFB03ITARNUM" + l + i + k, "null.tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psFB03ITACNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFB03ITACNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
        }
        if(regiment.country() == countryJapan)
        {
            changeMat(hiermesh, "Overlay2", "psBM03JAPCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", psRussianBomberColor[0][0], psRussianBomberColor[0][1], psRussianBomberColor[0][2], psRussianBomberColor[0][0], psRussianBomberColor[0][1], psRussianBomberColor[0][2]);
            changeMat(hiermesh, "Overlay3", "psBM03JAPCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", psRussianBomberColor[0][0], psRussianBomberColor[0][1], psRussianBomberColor[0][2], psRussianBomberColor[0][0], psRussianBomberColor[0][1], psRussianBomberColor[0][2]);
            changeMat(class1, hiermesh, "Overlay6", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
        }
        if(regiment.country() == countryPoland)
        {
            changeMat(hiermesh, "Overlay1", "psBM03POLCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "psBM03POLCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == countryRomania)
        {
            changeMat(hiermesh, "Overlay8", "psFB03ROMCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == countryRussia)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay2", "psBM03RUSLNUM" + l + i + "0" + k, "Russian/1" + k + ".tga", psRussianBomberColor[i][0], psRussianBomberColor[i][1], psRussianBomberColor[i][2]);
                changeMat(hiermesh, "Overlay3", "psBM03RUSRNUM" + l + i + "0" + k, "null.tga", "Russian/1" + k + ".tga", 1.0F, 1.0F, 1.0F, psRussianBomberColor[i][0], psRussianBomberColor[i][1], psRussianBomberColor[i][2]);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psBM03RUSCNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", psRussianBomberColor[i][0], psRussianBomberColor[i][1], psRussianBomberColor[i][2], psRussianBomberColor[i][0], psRussianBomberColor[i][1], psRussianBomberColor[i][2]);
                changeMat(hiermesh, "Overlay3", "psBM03RUSCNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", psRussianBomberColor[i][0], psRussianBomberColor[i][1], psRussianBomberColor[i][2], psRussianBomberColor[i][0], psRussianBomberColor[i][1], psRussianBomberColor[i][2]);
            }
            changeMat(class1, hiermesh, "Overlay7", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == countryNewZealand)
        {
            k = clampToLiteral(k);
            changeMat(hiermesh, "Overlay1", "psBM00RZREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2], psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2]);
            changeMat(hiermesh, "Overlay4", "psBM00RZREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2], psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2]);
            changeMat(hiermesh, "Overlay2", "psBM00RZLNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "psBM00RZRNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2]);
            changeMat(class1, hiermesh, "Overlay6", "newzealand6", "NewZealand/newzealand6.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "newzealand7", "NewZealand/newzealand7.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == countrySlovakia)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay4", "psBM03SLVKLNUM" + l + i + "0" + k, "Finnish/" + k + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay1", "psBM03SLVKRNUM" + l + i + "0" + k, "null.tga", "Finnish/" + k + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay1", "psBM03SLVKCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay4", "psBM03SLVKCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "slovakiancross1", "Slovakian/cross1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "slovakiancross2", "Slovakian/cross2.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == countryUSA)
        {
            changeMat(hiermesh, "Overlay1", "psBM00USACNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "psBM00USACNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
            return;
        } else
        {
            return;
        }
    }
}
