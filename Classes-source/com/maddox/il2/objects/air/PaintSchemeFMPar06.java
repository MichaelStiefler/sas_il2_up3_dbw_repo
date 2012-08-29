// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PaintSchemeFMPar06.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintSchemeControlFM, PaintScheme

public class PaintSchemeFMPar06 extends com.maddox.il2.objects.air.PaintSchemeControlFM
{

    public PaintSchemeFMPar06()
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
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritain)
            return "" + regiment.id() + " + " + (k >= 10 ? "" + k : "0" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryItaly)
            return "" + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryJapan)
            return "" + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPoland)
            return "" + (k >= 10 ? "" + k : "0" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRomania)
            return "+ " + (k >= 10 ? "" + k : "0" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussia)
            return "" + (k < 10 ? "0" + k : "" + k) + " *";
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
            int i2 = regiment.gruppeNumber();
            changeMat(class1, hiermesh, "Overlay6", "balken4", "German/balken4.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "balken5", "German/balken5.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "opthaken3", "German/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "optionhaken3.tga" : "hakenfake.tga"), 1.0F, 1.0F, 1.0F);
            if(k <= 1 && i == 0)
            {
                changeMat(class1, hiermesh, "Overlay1", "psFM06GERCOML0000", "German/00c.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay4", "psFM06GERCOMR0000", "German/00c2.tga", 1.0F, 1.0F, 1.0F);
                if(i2 == 1 || i2 == 5)
                {
                    changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
                } else
                {
                    changeMat(class1, hiermesh, "Overlay2", "psFM06GERCOMCGID" + i2, "German/00cG" + i2 + ".tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay3", "psFM06GERCOMCGID" + i2, "German/00cG" + i2 + ".tga", 1.0F, 1.0F, 1.0F);
                }
                return;
            }
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay1", "psFM06GERLNUM" + l + i + "0" + k, "German/1" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay4", "psFM06GERRNUM" + l + i + "0" + k, "null.tga", "German/1" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay1", "psFM06GERCNUM" + l + i + k, "German/1" + i1 + k / 10 + ".tga", "German/1" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay4", "psFM06GERCNUM" + l + i + k, "German/1" + i1 + k / 10 + ".tga", "German/1" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            if(i2 == 1 || i2 == 5)
            {
                changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(class1, hiermesh, "Overlay2", "psFM06GERGRUPP" + i2 + "S" + i1, "German/1" + i1 + "0G" + i2 + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay3", "psFM06GERGRUPP" + i2 + "S" + i1, "German/1" + i1 + "0G" + i2 + ".tga", 1.0F, 1.0F, 1.0F);
            }
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNetherlands)
        {
            changeMat(class1, hiermesh, "Overlay6", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay1", "psBMIUDUTCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psBMIUDUTCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryIndonesia)
        {
            changeMat(hiermesh, "Overlay4", "psBM00DOGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay1", "psBM00DOGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "IndonesiaWing", "Indonesia/DOWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "IndonesiaRoundel", "Indonesia/DORoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "IndonesiaTail", "Indonesia/DOTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryMalaysia)
        {
            changeMat(hiermesh, "Overlay4", "psBM00MYGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay1", "psBM00MYGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "MalaysiaWing", "Malaysia/MYWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "MalaysiaRoundel", "Malaysia/MYRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "MalaysiaTail", "Malaysia/MYTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySingapore)
        {
            changeMat(hiermesh, "Overlay4", "psBM00SIGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay1", "psBM00SIGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "SingaporeWing", "Singapore/SIWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "SingaporeRoundel", "Singapore/SIRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "SingaporeTail", "Singapore/SITail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryVietnamSouth)
        {
            changeMat(hiermesh, "Overlay1", "psBM0JVNACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psBM0JVNACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "VietnamSouthWing", "VietnamSouth/VNWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "VietnamSouthRoundel", "VietnamSouth/VNRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "VietnamSouthTail", "VietnamSouth/VNTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFinland)
        {
            char c = (char)(48 + k % 10);
            changeMat(class1, hiermesh, "Overlay6", "FAFhaken", "Finnish/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "FAFhaken", "Finnish/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
            if(i == 3)
                changeMat(class1, hiermesh, "Overlay8", "psFM06FINCNUM" + l + i + "_" + (char)(65 + (k % 10 - 1)), "Finnish/" + (char)(65 + (k % 10 - 1)) + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            else
            if(k < 10)
                changeMat(class1, hiermesh, "Overlay8", "psFM06FINCNUM" + l + i + "0" + k, "Finnish/0" + k + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            else
                changeMat(hiermesh, "Overlay8", "psFM06FINCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            java.lang.String s1 = getFAFACCode(class1, i);
            changeMat(hiermesh, "Overlay2", "psFM06FINACID" + s1 + c, "Finnish/" + s1 + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            changeMat(hiermesh, "Overlay3", "psFM06FINACID" + s1 + c, "Finnish/" + s1 + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFrance)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay2", "psFM06FRALNUM" + l + i + k, "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM06FRARNUM" + l + i + k, "null.tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psFM06FRACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM06FRACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritain)
        {
            if("ra".equals(regiment.branch()) || "rz".equals(regiment.branch()) || "rn".equals(regiment.branch()))
            {
                k = clampToLiteral(k);
                changeMat(hiermesh, "Overlay1", "psFM06BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM06BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay2", "psFM06BRINAVYLNUM" + l + i + (k < 10 ? "0" + k : "" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay4", "psFM06BRINAVYRNUM" + l + i + (k < 10 ? "0" + k : "" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay6", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay7", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
            } else
            {
                k = clampToLiteral(k);
                changeMat(hiermesh, "Overlay1", "psFM06BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM06BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay2", "psFM06BRILNUM" + l + i + (k < 10 ? "0" + k : "" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay4", "psFM06BRIRNUM" + l + i + (k < 10 ? "0" + k : "" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay6", "britishroundel2c", "British/roundel2c.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay7", "britishroundel4cthin", "British/roundel4cthin.tga", 1.0F, 1.0F, 1.0F);
            }
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritainBlue)
        {
            k = clampToLiteral(k);
            changeMat(hiermesh, "Overlay1", "ps3FM06BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[2]);
            changeMat(hiermesh, "Overlay3", "ps3FM06BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[2]);
            changeMat(hiermesh, "Overlay2", "ps3FM06BRILNUM" + l + i + (k < 10 ? "0" + k : "" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[2], 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "ps3FM06BRIRNUM" + l + i + (k < 10 ? "0" + k : "" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishGrayColor[2]);
            changeMat(class1, hiermesh, "Overlay6", "britishroundelTypeD", "British/roundelTypeD.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "britishroundelTypeD", "British/roundelTypeD.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryHungary)
        {
            changeMat(hiermesh, "Overlay1", "psFM06HUNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "psFM06HUNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psFM06HUNCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psFM06HUNCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "hungarianbalkennewer", "Hungarian/balkennewer.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "hungarianbalkennewer", "Hungarian/balkennewer.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryItaly)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay2", "psFM06ITALNUM" + l + i + k, "Italian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM06ITARNUM" + l + i + k, "null.tga", "Italian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psFM06ITACNUM" + l + i + k, "Italian/1" + k / 10 + ".tga", "Italian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM06ITACNUM" + l + i + k, "Italian/1" + k / 10 + ".tga", "Italian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryJapan)
        {
            changeMat(hiermesh, "Overlay2", "psFM06JAPCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2]);
            changeMat(hiermesh, "Overlay3", "psFM06JAPCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2]);
            changeMat(class1, hiermesh, "Overlay6", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPoland)
        {
            changeMat(hiermesh, "Overlay1", "psFM06POLCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(hiermesh, "Overlay4", "psFM06POLCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(class1, hiermesh, "Overlay7", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRomania)
        {
            changeMat(hiermesh, "Overlay8", "psFM06ROMCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussia)
        {
            changeMat(hiermesh, "Overlay1", "psFM06RUSCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(hiermesh, "Overlay4", "psFM06RUSCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(class1, hiermesh, "Overlay7", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNewZealand)
        {
            k = clampToLiteral(k);
            changeMat(hiermesh, "Overlay1", "psFM00RZLNUM" + l + i + (k < 10 ? "0" + k : "" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "psFM00RZRNUM" + l + i + (k < 10 ? "0" + k : "" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2]);
            changeMat(class1, hiermesh, "Overlay6", "newzealand6", "NewZealand/newzealand6.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "newzealand7", "NewZealand/newzealand7.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySlovakia)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay4", "psFM06SLVKLNUM" + l + i + "0" + k, "Slovakian/" + k + ".tga", 0.1F, 0.1F, 0.1F);
                changeMat(hiermesh, "Overlay1", "psFM06SLVKRNUM" + l + i + "0" + k, "null.tga", "Slovakian/" + k + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            } else
            {
                changeMat(hiermesh, "Overlay1", "psFM06SLVKCNUM" + l + i + k, "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
                changeMat(hiermesh, "Overlay4", "psFM06SLVKCNUM" + l + i + k, "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            }
            changeMat(class1, hiermesh, "Overlay6", "slovakiancross2", "Slovakian/cross2.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "slovakiancross2", "Slovakian/cross2.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryUSA)
        {
            changeMat(hiermesh, "Overlay1", "psFM06USACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psFM06USACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryUSABlue)
        {
            changeMat(hiermesh, "Overlay1", "psFM06USACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psFM06USACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySwitzerland)
        {
            changeMat(class1, hiermesh, "Overlay6", "CHWings", "CH/CHWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "CHWings", "CH/CHWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "CHWings", "CH/CHWings.tga", 1.0F, 1.0F, 1.0F);
            java.lang.String s = getCHFMCode(class1);
            changeMat(hiermesh, "Overlay2", "CHCode_" + s + (k >= 10 ? "" + k : "0" + k), "Swiss/" + s + ".tga", "Swiss/" + k % 10 + ".tga", 0.95F, 0.95F, 0.95F, 0.95F, 0.95F, 0.95F);
            changeMat(hiermesh, "Overlay3", "CHCode_" + s + (k >= 10 ? "" + k : "0" + k), "Swiss/" + s + ".tga", "Swiss/" + k % 10 + ".tga", 0.95F, 0.95F, 0.95F, 0.95F, 0.95F, 0.95F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryChina)
        {
            changeMat(hiermesh, "Overlay1", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "CNRoundel", "CN/CNRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "CNRoundel", "CN/CNRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySpainRep)
        {
            if(k > 9)
            {
                if(k > 19)
                {
                    changeMat(class1, hiermesh, "Overlay2", "ESNum_" + regiment.id() + 17 + k % 10, "Spanish/" + regiment.id() + 17 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay2", "ESNum_" + regiment.id() + 17 + k % 10, "Spanish/" + regiment.id() + 17 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                } else
                {
                    changeMat(class1, hiermesh, "Overlay2", "ESNum_" + regiment.id() + 16 + k % 10, "Spanish/" + regiment.id() + 16 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay3", "ESNum_" + regiment.id() + 16 + k % 10, "Spanish/" + regiment.id() + 16 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                }
            } else
            {
                changeMat(class1, hiermesh, "Overlay2", "ESNum_" + regiment.id() + 15 + k, "Spanish/" + regiment.id() + 15 + k + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay3", "ESNum_" + regiment.id() + 15 + k, "Spanish/" + regiment.id() + 15 + k + ".tga", 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "ESRoundel", "ES/ESRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "ESRoundel", "ES/ESRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryIsrael)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "ISRoundel", "IS/ISRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "ISRoundel", "IS/ISRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryIsraelBlue)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "ISRoundel", "IS/ISRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "ISRoundel", "IS/ISRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryItalyAllied)
        {
            changeMat(hiermesh, "Overlay1", "ITLDefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "IA/1" + k / 10 + ".tga", "IA/1" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "ITLDefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "IA/1" + k / 10 + ".tga", "IA/1" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "ILRoundel", "IL/ILRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "ILRoundel", "IL/ILRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRomaniaAllied)
        {
            changeMat(hiermesh, "Overlay1", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(hiermesh, "Overlay4", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(class1, hiermesh, "Overlay6", "RLRoundelLate", "RL/RLRoundelLate.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "RLRoundelLate", "RL/RLRoundelLate.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "RLTail", "RL/RLRoundelLate.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFinlandAllied)
        {
            char c1 = (char)(48 + k % 10);
            changeMat(class1, hiermesh, "Overlay6", "FLRoundel", "FL/FLRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "FLRoundel", "FL/FLRoundel.tga", 1.0F, 1.0F, 1.0F);
            if(i == 3)
                changeMat(class1, hiermesh, "Overlay8", "psFM06FINCNUM" + l + i + "_" + (char)(65 + (k % 10 - 1)), "Finnish/" + (char)(65 + (k % 10 - 1)) + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            else
            if(k < 10)
                changeMat(class1, hiermesh, "Overlay8", "psFM06FINCNUM" + l + i + "0" + k, "Finnish/0" + k + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            else
                changeMat(hiermesh, "Overlay8", "psFM06FINCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            java.lang.String s2 = getFAFACCode(class1, i);
            changeMat(hiermesh, "Overlay2", "psFM06FINACID" + s2 + c1, "Finnish/" + s2 + ".tga", "Finnish/sn" + c1 + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            changeMat(hiermesh, "Overlay3", "psFM06FINACID" + s2 + c1, "Finnish/" + s2 + ".tga", "Finnish/sn" + c1 + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBelgium)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "BERoundel", "BE/BERoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "BERoundel", "BE/BERoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "BETail", "BE/BETail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBulgaria)
        {
            changeMat(hiermesh, "Overlay1", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "BGRoundel", "BG/BGRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "BGRoundel", "BG/BGRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNorway)
        {
            k = clampToLiteral(k);
            changeMat(hiermesh, "Overlay1", "NORegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "NORegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "BritishLnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "BritishRnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "NOWings", "NO/NOWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "NORoundel", "NO/NORoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGreece)
        {
            changeMat(hiermesh, "Overlay1", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "GRRoundel", "GR/GRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "GRRoundel", "GR/GRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "GRTail", "GR/GRTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBrazil)
        {
            changeMat(class1, hiermesh, "Overlay6", "BRRoundel", "BR/BRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "BRRoundel", "BR/BRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySweden)
        {
            int j1 = java.lang.Integer.parseInt(regiment.id());
            changeMat(hiermesh, "Overlay2", "BlackNum_00" + (j1 >= 10 ? "" + j1 : "0" + j1), "Slovakian/" + j1 / 10 + ".tga", "Slovakian/" + j1 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "BlackNum_00" + (j1 >= 10 ? "" + j1 : "0" + j1), "Slovakian/" + j1 / 10 + ".tga", "Slovakian/" + j1 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "SWRoundel", "SW/SWRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "SWRoundel", "SW/SWRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySouthAfrica)
        {
            k = clampToLiteral(k);
            changeMat(hiermesh, "Overlay1", "SARegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "SARegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "BritishLnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "BritishRnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "SAWings", "SA/SAWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "SARoundel", "SA/SARoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "SATail", "SA/SATail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryYugoslavia)
        {
            changeMat(hiermesh, "Overlay1", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(hiermesh, "Overlay4", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "YURoundel", "YU/YURoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "YURoundel", "YU/YURoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "YUTail", "YU/YUTailEarly.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryYugoPar)
        {
            changeMat(hiermesh, "Overlay1", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(hiermesh, "Overlay4", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "YUPRoundel", "YU/YURoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "YUPRoundel", "YU/YURoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "YUPTail", "YU/YUTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPhilippines)
        {
            changeMat(hiermesh, "Overlay1", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "PHRoundel", "PH/PHRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "PHRoundel", "PH/PHRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryCroatia)
        {
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "HRRoundel", "HR/HRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "HRRoundel", "HR/HRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "HRTail", "HR/HRTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBulgariaAllied)
        {
            changeMat(hiermesh, "Overlay1", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(hiermesh, "Overlay4", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "BLRoundelLate", "BL/BLRoundelLate.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "BLTail", "BL/BLRoundelLate.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryThailand)
        {
            int k1 = java.lang.Integer.parseInt(regiment.id());
            changeMat(hiermesh, "Overlay1", "BlackNum_00" + (k1 >= 10 ? "" + k1 : "0" + k1), "Default/1" + k1 / 10 + ".tga", "Default/1" + k1 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "BlackNum_00" + (k1 >= 10 ? "" + k1 : "0" + k1), "Default/1" + k1 / 10 + ".tga", "Default/1" + k1 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "THRoundelEarly", "TH/THRoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "THRoundelEarly", "TH/THRoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "THTail", "TH/THWings.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryThailandBlue)
        {
            int l1 = java.lang.Integer.parseInt(regiment.id());
            changeMat(hiermesh, "Overlay1", "BlackNum_00" + (l1 >= 10 ? "" + l1 : "0" + l1), "Default/1" + l1 / 10 + ".tga", "Default/1" + l1 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "BlackNum_00" + (l1 >= 10 ? "" + l1 : "0" + l1), "Default/1" + l1 / 10 + ".tga", "Default/1" + l1 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "THRoundel", "TH/THRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "THRoundel", "TH/THRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "THTail", "TH/THWings.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGDR)
        {
            changeMat(hiermesh, "Overlay1", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "DDRoundel", "DD/DDRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "DDRoundel", "DD/DDRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "DDRoundel", "DD/DDRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryHungaryAllied)
        {
            changeMat(hiermesh, "Overlay1", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(hiermesh, "Overlay4", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "HYRoundelLate", "HY/HYRoundelLate.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "HYRoundelLate", "HY/HYRoundelLate.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "HYTailLate", "HY/HYRoundelLate.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryEgypt)
        {
            changeMat(hiermesh, "Overlay1", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "EGRoundel", "EG/EGRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "EGRoundel", "EG/EGRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "EGTail", "EG/EGTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryEgyptRed)
        {
            changeMat(hiermesh, "Overlay1", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "EGRoundel", "EG/EGRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "EGRoundel", "EG/EGRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "EGTail", "EG/EGTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryCzechoslovakia)
        {
            changeMat(hiermesh, "Overlay1", "DefaultcNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(hiermesh, "Overlay4", "DefaultcNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(class1, hiermesh, "Overlay6", "CZRoundel", "CZ/CZRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "CZTail", "CZ/CZTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryIran)
        {
            changeMat(hiermesh, "Overlay1", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "IRRoundel", "IR/IRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "IRRoundel", "IR/IRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "IRTail", "IR/IRTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryChinaRed)
        {
            changeMat(hiermesh, "Overlay1", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "CCRoundel", "CC/CCRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "CCRoundel", "CC/CCRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNorthKorea)
        {
            changeMat(hiermesh, "Overlay8", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "KPRoundel", "KP/KPRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "KPRoundel", "KP/KPRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySouthKorea)
        {
            changeMat(hiermesh, "Overlay1", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "KRRoundel", "KR/KRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "KRRoundel", "KR/KRRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNorthKoreaRed)
        {
            changeMat(hiermesh, "Overlay8", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "KPRoundel", "KP/KPRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "KPRoundel", "KP/KPRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySouthKoreaBlue)
        {
            changeMat(hiermesh, "Overlay1", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "KRRoundel", "KR/KRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "KRRoundel", "KR/KRRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryItalyANR)
        {
            changeMat(hiermesh, "Overlay1", "ANRDefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "IA/1" + k / 10 + ".tga", "IA/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "ANRDefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "IA/1" + k / 10 + ".tga", "IA/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "IAWings", "IA/IAWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "IARoundel", "IA/IARoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "IATail2", "IA/IATail.tga", 1.0F, 1.0F, 1.0F);
            return;
        } else
        {
            return;
        }
    }
}
