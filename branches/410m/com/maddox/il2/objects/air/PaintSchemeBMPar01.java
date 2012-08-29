// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PaintSchemeBMPar01.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintScheme

public class PaintSchemeBMPar01 extends com.maddox.il2.objects.air.PaintScheme
{

    public PaintSchemeBMPar01()
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
            return "+ " + k;
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
            changeMat(hiermesh, "Overlay1", "psBM01GERREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "psBM01GERREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBM01GERCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "German/" + (char)(65 + (k - 1)) + ".tga", "German/" + psGermanBomberLetter[l][i] + ".tga", psGermanBomberColor[i][0], psGermanBomberColor[i][1], psGermanBomberColor[i][2], 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psBM01GERCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "German/" + (char)(65 + (k - 1)) + ".tga", "German/" + psGermanBomberLetter[l][i] + ".tga", psGermanBomberColor[i][0], psGermanBomberColor[i][1], psGermanBomberColor[i][2], 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "balken0", "German/balken0.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "balken1", "German/balken1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "haken1", "German/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "haken1.tga" : "hakenfake.tga"), 1.0F, 1.0F, 1.0F);
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
                changeMat(class1, hiermesh, "Overlay8", "psBM01FINANUM" + l + i + "0" + k, "Finnish/0" + k + ".tga", psFinnishFighterColor[i][0], psFinnishFighterColor[i][1], psFinnishFighterColor[i][2]);
            else
                changeMat(hiermesh, "Overlay8", "psBM01FINCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", psFinnishFighterColor[i][0], psFinnishFighterColor[i][1], psFinnishFighterColor[i][2], psFinnishFighterColor[i][0], psFinnishFighterColor[i][1], psFinnishFighterColor[i][2]);
            java.lang.String s = getFAFACCode(class1, i);
            changeMat(hiermesh, "Overlay2", "psBM01FINACOD" + s + c, "Finnish/" + s + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            changeMat(hiermesh, "Overlay3", "psBM01FINACOD" + s + c, "Finnish/" + s + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            return;
        }
        if(regiment.country() == countryFrance)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay2", "psFB01FRALNUM" + l + i + k, "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFB01FRARNUM" + l + i + k, "null.tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psFB01FRACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFB01FRACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
        }
        if(regiment.country() == countryBritain)
        {
            if("ra".equals(regiment.branch()) || "rz".equals(regiment.branch()) || "rn".equals(regiment.branch()))
            {
                k = clampToLiteral(k);
                changeMat(hiermesh, "Overlay1", "psBM01BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2], psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2]);
                changeMat(hiermesh, "Overlay4", "psBM01BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2], psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2]);
                changeMat(hiermesh, "Overlay2", "psBM01BRINAVYLNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psBM01BRINAVYRNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2]);
                changeMat(class1, hiermesh, "Overlay6", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay7", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
            } else
            {
                k = clampToLiteral(k);
                changeMat(hiermesh, "Overlay1", "psBM01BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", psBritishRedColor[0], psBritishRedColor[1], psBritishRedColor[2], psBritishRedColor[0], psBritishRedColor[1], psBritishRedColor[2]);
                changeMat(hiermesh, "Overlay4", "psBM01BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", psBritishRedColor[0], psBritishRedColor[1], psBritishRedColor[2], psBritishRedColor[0], psBritishRedColor[1], psBritishRedColor[2]);
                changeMat(hiermesh, "Overlay2", "psBM01BRILNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", psBritishRedColor[0], psBritishRedColor[1], psBritishRedColor[2], 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psBM01BRIRNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, psBritishRedColor[0], psBritishRedColor[1], psBritishRedColor[2]);
                changeMat(class1, hiermesh, "Overlay6", "britishroundel2c", "British/roundel2c.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay7", "britishroundel4c", "British/roundel4c.tga", 1.0F, 1.0F, 1.0F);
            }
            return;
        }
        if(regiment.country() == countryHungary)
        {
            changeMat(hiermesh, "Overlay1", "psBM01HUNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "psBM01HUNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBM01HUNCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psBM01HUNCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "hungarianbalkenolder", "Hungarian/balkenolder.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "hungarianbalkenolder", "Hungarian/balkenolder.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == countryItaly)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay2", "psFB01ITALNUM" + l + i + k, "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFB01ITARNUM" + l + i + k, "null.tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psFB01ITACNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFB01ITACNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
        }
        if(regiment.country() == countryJapan)
        {
            changeMat(hiermesh, "Overlay2", "psBM01JAPCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", psRussianBomberColor[0][0], psRussianBomberColor[0][1], psRussianBomberColor[0][2], psRussianBomberColor[0][0], psRussianBomberColor[0][1], psRussianBomberColor[0][2]);
            changeMat(hiermesh, "Overlay3", "psBM01JAPCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", psRussianBomberColor[0][0], psRussianBomberColor[0][1], psRussianBomberColor[0][2], psRussianBomberColor[0][0], psRussianBomberColor[0][1], psRussianBomberColor[0][2]);
            changeMat(class1, hiermesh, "Overlay6", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
        }
        if(regiment.country() == countryPoland)
        {
            changeMat(hiermesh, "Overlay1", "psBM01POLCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "psBM01POLCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == countryRomania)
        {
            changeMat(hiermesh, "Overlay8", "psFB01ROMCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == countryRussia)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay2", "psBM01RUSLNUM" + l + i + "0" + k, "Russian/0" + k + ".tga", psRussianBomberColor[i][0], psRussianBomberColor[i][1], psRussianBomberColor[i][2]);
                changeMat(hiermesh, "Overlay3", "psBM01RUSRNUM" + l + i + "0" + k, "null.tga", "Russian/0" + k + ".tga", 1.0F, 1.0F, 1.0F, psRussianBomberColor[i][0], psRussianBomberColor[i][1], psRussianBomberColor[i][2]);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psBM01RUSCNUM" + l + i + k, "Russian/0" + k / 10 + ".tga", "Russian/0" + k % 10 + ".tga", psRussianBomberColor[i][0], psRussianBomberColor[i][1], psRussianBomberColor[i][2], psRussianBomberColor[i][0], psRussianBomberColor[i][1], psRussianBomberColor[i][2]);
                changeMat(hiermesh, "Overlay3", "psBM01RUSCNUM" + l + i + k, "Russian/0" + k / 10 + ".tga", "Russian/0" + k % 10 + ".tga", psRussianBomberColor[i][0], psRussianBomberColor[i][1], psRussianBomberColor[i][2], psRussianBomberColor[i][0], psRussianBomberColor[i][1], psRussianBomberColor[i][2]);
            }
            changeMat(class1, hiermesh, "Overlay6", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
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
                changeMat(class1, hiermesh, "Overlay2", "psBM01SLVKLNUM" + l + i + "0" + k, "Finnish/" + k + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psBM01SLVKRNUM" + l + i + "0" + k, "null.tga", "Finnish/" + k + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psBM01SLVKCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psBM01SLVKCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "slovakiancross1", "Slovakian/cross1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "slovakiancross1", "Slovakian/cross1.tga", 1.0F, 1.0F, 1.0F);
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
