// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PaintSchemeFMPar04.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintSchemeControlFM, PaintScheme

public class PaintSchemeFMPar04 extends com.maddox.il2.objects.air.PaintSchemeControlFM
{

    public PaintSchemeFMPar04()
    {
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
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryItaly)
            return "" + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryJapan)
            return "" + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPoland)
            return "" + (k >= 10 ? "" + k : "0" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRomania)
            return "+ " + (k >= 10 ? "" + k : "0" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussia)
            return "* " + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNewZealand)
        {
            k = clampToLiteral(k);
            return "" + (char)(65 + (k - 1));
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySlovakia)
            return "" + k + " +";
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryUSA)
            return "" + (k >= 10 ? "" + k : "0" + k) + "*";
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFinlandAllied)
            if(i == 3)
                return com.maddox.il2.objects.air.PaintScheme.psFinnishFighterString[0][i] + (char)(65 + (k % 10 - 1));
            else
                return com.maddox.il2.objects.air.PaintScheme.psFinnishFighterString[0][i] + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNorway)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
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
            changeMat(class1, hiermesh, "Overlay6", "balken0", "German/balken0.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "balken1", "German/balken1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "haken2", "German/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "haken2.tga" : "hakenfake.tga"), 1.0F, 1.0F, 1.0F);
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
                return;
            }
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
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNetherlands)
        {
            changeMat(class1, hiermesh, "Overlay6", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay1", "psBM0NDUTCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psBM0NDUTCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFinland)
        {
            char c = (char)(48 + k % 10);
            changeMat(class1, hiermesh, "Overlay6", "FAFhaken", "Finnish/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "FAFhaken", "Finnish/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
            if(i == 3)
                changeMat(class1, hiermesh, "Overlay8", "psFM04FINCNUM" + l + i + "_" + (char)(65 + (k % 10 - 1)), "Finnish/" + (char)(65 + (k % 10 - 1)) + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            else
            if(k < 10)
                changeMat(class1, hiermesh, "Overlay8", "psFM04FINCNUM" + l + i + "0" + k, "Finnish/0" + k + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            else
                changeMat(hiermesh, "Overlay8", "psFM04FINCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            java.lang.String s = getFAFACCode(class1, i);
            changeMat(hiermesh, "Overlay2", "psFM04FINACID" + s + c, "Finnish/" + s + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            changeMat(hiermesh, "Overlay3", "psFM04FINACID" + s + c, "Finnish/" + s + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFrance)
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
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritain)
        {
            if("ra".equals(regiment.branch()) || "rz".equals(regiment.branch()) || "rn".equals(regiment.branch()))
            {
                k = clampToLiteral(k);
                changeMat(hiermesh, "Overlay1", "psFM04BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2]);
                changeMat(hiermesh, "Overlay4", "psFM04BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2]);
                changeMat(hiermesh, "Overlay2", "psFM04BRINAVYLNUM" + l + i + (k < 10 ? "0" + k : "" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM04BRINAVYRNUM" + l + i + (k < 10 ? "0" + k : "" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2]);
                changeMat(class1, hiermesh, "Overlay6", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay7", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
            } else
            {
                k = clampToLiteral(k);
                changeMat(hiermesh, "Overlay1", "psFM04BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[2]);
                changeMat(hiermesh, "Overlay3", "psFM04BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[2]);
                changeMat(hiermesh, "Overlay2", "psFM04BRILNUM" + l + i + (k < 10 ? "0" + k : "" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[2], 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay4", "psFM04BRIRNUM" + l + i + (k < 10 ? "0" + k : "" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[2]);
                changeMat(class1, hiermesh, "Overlay6", "britishroundel2c", "British/roundel2c.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay7", "britishroundel4cthin", "British/roundel4cthin.tga", 1.0F, 1.0F, 1.0F);
            }
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritainBlue)
        {
            k = clampToLiteral(k);
            changeMat(class1, hiermesh, "Overlay8", "BBRitTail", "British/CATail.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay1", "psBBBBRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "psBBBBRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBBBBRILNUM" + l + i + (k < 10 ? "0" + k : "" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psBBBBRIRNUM" + l + i + (k < 10 ? "0" + k : "" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "britishroundel2c", "British/roundel2c.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "britishroundel4cthin", "British/roundel4cthin.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryHungary)
        {
            changeMat(hiermesh, "Overlay1", "psFM04HUNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "psFM04HUNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psFM04HUNCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psFM04HUNCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "hungarianbalkennewer", "Hungarian/balkennewer.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "hungarianbalkennewer", "Hungarian/balkennewer.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryItaly)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay2", "psFM04ITALNUM" + l + i + k, "Italian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM04ITARNUM" + l + i + k, "null.tga", "Italian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psFM04ITACNUM" + l + i + k, "Italian/1" + k / 10 + ".tga", "Italian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM04ITACNUM" + l + i + k, "Italian/1" + k / 10 + ".tga", "Italian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryJapan)
        {
            changeMat(hiermesh, "Overlay2", "psFM04JAPCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2]);
            changeMat(hiermesh, "Overlay3", "psFM04JAPCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2]);
            changeMat(class1, hiermesh, "Overlay6", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPoland)
        {
            changeMat(hiermesh, "Overlay1", "psFM04POLCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "psFM04POLCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRomania)
        {
            changeMat(hiermesh, "Overlay8", "psFM04ROMCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussia)
        {
            changeMat(hiermesh, "Overlay2", "psFM04RUSCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "psFM04RUSCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            if(class1.isAssignableFrom(com.maddox.il2.objects.air.P_39N.class) || class1.isAssignableFrom(com.maddox.il2.objects.air.P_39Q1.class) || class1.isAssignableFrom(com.maddox.il2.objects.air.P_39Q10.class))
                changeMat(class1, hiermesh, "Overlay6", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNewZealand)
        {
            k = clampToLiteral(k);
            changeMat(hiermesh, "Overlay2", "psFM00RZLNUM" + l + i + (k < 10 ? "0" + k : "" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "psFM00RZRNUM" + l + i + (k < 10 ? "0" + k : "" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2]);
            changeMat(class1, hiermesh, "Overlay6", "newzealand6", "NewZealand/newzealand6.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "newzealand7", "NewZealand/newzealand7.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySlovakia)
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
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryUSA)
        {
            changeMat(hiermesh, "Overlay1", "psBM0NUSACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "psBM0NUSACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryUSABlue)
        {
            changeMat(hiermesh, "Overlay1", "psBM0NUSACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "psBM0NUSACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFinlandAllied)
        {
            char c1 = (char)(48 + k % 10);
            changeMat(class1, hiermesh, "Overlay6", "FLRoundel", "FL/FLRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "FLRoundel", "FL/FLRoundel.tga", 1.0F, 1.0F, 1.0F);
            if(i == 3)
                changeMat(class1, hiermesh, "Overlay8", "psFM04FINCNUM" + l + i + "_" + (char)(65 + (k % 10 - 1)), "Finnish/" + (char)(65 + (k % 10 - 1)) + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            else
            if(k < 10)
                changeMat(class1, hiermesh, "Overlay8", "psFM04FINCNUM" + l + i + "0" + k, "Finnish/0" + k + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            else
                changeMat(hiermesh, "Overlay8", "psFM04FINCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            java.lang.String s1 = getFAFACCode(class1, i);
            changeMat(hiermesh, "Overlay2", "psFM04FINACID" + s1 + c1, "Finnish/" + s1 + ".tga", "Finnish/sn" + c1 + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            changeMat(hiermesh, "Overlay3", "psFM04FINACID" + s1 + c1, "Finnish/" + s1 + ".tga", "Finnish/sn" + c1 + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBulgaria)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "BGRoundel", "BG/BGRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "BGRoundel", "BG/BGRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNorway)
        {
            k = clampToLiteral(k);
            changeMat(hiermesh, "Overlay1", "NORegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2]);
            changeMat(hiermesh, "Overlay3", "NORegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2]);
            changeMat(hiermesh, "Overlay2", "BritishLnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2], 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "BritishRnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2]);
            changeMat(class1, hiermesh, "Overlay6", "NOWings", "NO/NOWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "NORoundel", "NO/NORoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPhilippines)
        {
            changeMat(hiermesh, "Overlay1", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "PHRoundel", "PH/PHRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "PHRoundel", "PH/PHRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryThailand)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay4", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "THRoundelEarly", "TH/THRoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "THTailEarly", "TH/THRoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryThailandBlue)
        {
            changeMat(class1, hiermesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay4", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "THRoundel", "TH/THRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "THTail", "TH/THWings.tga", 1.0F, 1.0F, 1.0F);
            return;
        } else
        {
            return;
        }
    }
}
